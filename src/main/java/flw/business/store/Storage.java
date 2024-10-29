/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.util.CsvObject;
import flw.business.util.SerializableInteger;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author alpl
 */
@Entity
@Table(name = "Storage")
public class Storage implements Serializable {
    //public static volatile Storage singleton;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "storageFree", cascade = CascadeType.ALL)
    private List<StockGround> freeStocks;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "storageOcc", cascade = CascadeType.ALL)
    private List<StockGround> occStocks;
    private boolean sortedIn;
    private boolean sortedOut;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SerializableInteger> storedAmount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PalletsInProgress> palletsNotInStorage;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @OneToOne
    private DoloresState state;

    public Storage() {
        freeStocks = new ArrayList<>();
        occStocks = new ArrayList<>();
        sortedIn = false;
        sortedOut = false;
        this.fillStockGrounds();
        palletsNotInStorage = new ArrayList<>();
    }

    public Storage(List<StockGround> freeStocks, List<StockGround> occStocks, boolean sortedIn, boolean sortedOut, List<SerializableInteger> storedAmount, List<PalletsInProgress> palletsNotInStorage) {
        this.freeStocks = freeStocks;
        this.occStocks = occStocks;
        this.sortedIn = sortedIn;
        this.sortedOut = sortedOut;
        this.storedAmount = storedAmount;
        this.palletsNotInStorage = palletsNotInStorage;
    }
    
    public void setState(DoloresState state){
        this.state = state;
    }
    
    

    public final void addStockGround(StockGround ground) {
        this.freeStocks.add(ground);
    }

    /*
     * Hier 0 (wegoptimiert) bzw 1 (willkürlich) a  nnehmen
     * und nach Bedarf Freie Lagerplätze sortieren
     */
    public StockGround getFreeStockGround(ArticleDynamics articleDynamics, int strategyIn, int strategyStorage) {

        if (strategyIn == 1 && !isSortedIn()) {
            Collections.sort(freeStocks, new StockGroundComparatorByDistSource());
            setSortedIn(true);
        }

        if (strategyIn == 0 && isSortedIn()) {
            Collections.shuffle(freeStocks);
            setSortedIn(false);
        }

        for (StockGround stockGround : freeStocks) {
            if ((stockGround.getFixedStockGround() == articleDynamics.getArticleNumber()) && strategyStorage == 1) {
                return stockGround;
            } else if ((stockGround.getAbc().equals(articleDynamics.getAbcClassification())) && strategyStorage == 2) {
                return stockGround;
            } else {
                return stockGround;
            }
        }
        return null;
    }

    public List<PalletsInProgress> getPallets() {
        List<PalletsInProgress> allPallets = new ArrayList<PalletsInProgress>();
        for (StockGround sg : occStocks) {
            allPallets.add(sg.getPallet());
        }
        allPallets.addAll(palletsNotInStorage);
        return allPallets;
    }

    public boolean stockPallet(ArticleDynamics articleDynamics, int stockGroundIndex, PalletsInProgress pallet) {
        StockGround current = new StockGround(stockGroundIndex);
        if (this.freeStocks.contains(current)) {
            current = this.freeStocks.remove(this.freeStocks.indexOf(current));
            current.setStoredArticle(articleDynamics.getArticle());
            current.setPallet(pallet);

            if (storedAmount.get(articleDynamics.getArticleNumber() % 10).getValue() != 0) {
                SerializableInteger newValue = new SerializableInteger(storedAmount.get(articleDynamics.getArticleNumber() % 10).getValue() + 1);
                newValue.setStorage(this);
                storedAmount.set((articleDynamics.getArticleNumber() % 10), newValue);
            } else {
                SerializableInteger newValue = new SerializableInteger(1);
                newValue.setStorage(this);
                storedAmount.set((articleDynamics.getArticleNumber() % 10), newValue);
            }

            occStorageAdd(current);
            
        }
        return false;
    }

    public StockGround unstockPallet(ArticleDynamics articleDynamics, int strategyOut) {

        if (strategyOut == 2 && !isSortedOut()) {
            Collections.sort(occStocks, new StockGroundComparatorByDistSource());
            setSortedOut(true);
        }

        if (strategyOut == 3 && isSortedOut()) {
            Collections.shuffle(occStocks);
            setSortedOut(false);
        }

        StockGround delivered = null;
        StockGround current = new StockGround(articleDynamics.getArticle());
        if (this.occStocks.contains(current)) {
            StockGround tmp = this.occStocks.remove(this.occStocks.indexOf(current));
            if (!palletsNotInStorage.contains(tmp.getPallet())) {
                //this.palletsNotInStorage.add(tmp.getPallet());
            }
            delivered = (StockGround) tmp.clone();
            tmp.reset();
            freeStorageAdd(tmp);
            SerializableInteger newValue = new SerializableInteger(storedAmount.get(articleDynamics.getArticleNumber() % 10).getValue() - 1);
            newValue.setStorage(this);
            storedAmount.set((articleDynamics.getArticleNumber() % 10), newValue);
            //storedAmount.put(articleDynamics.getArticleNumber(), storedAmount.get(articleDynamics.getArticleNumber()) - 1);
        } else {
            int xy = 1;
            int t = 6;
        }     
        return delivered;
    }

    public int getStoredPalletCount(int articleNumber) {

        return (storedAmount.get(articleNumber % 10).getValue());

    }

    public int getFreeStockGroundCount() {
        return this.freeStocks.size();
    }

    public List<Integer> getNotStoredOrEmptyArticles() {
        List<Integer> articles = new ArrayList<Integer>();
        for (Article article : ArticleFactory.getInstance().getArticles()) {
            if ((storedAmount.get(article.getArticleNumber() % 10).getValue()) <= 0) {
                articles.add(article.getArticleNumber());
            }
        }
        return articles;
    }

    public void fillStockGrounds() {
        storedAmount = new ArrayList<SerializableInteger>();

        for (int i = 0; i < 5; i++) {
            SerializableInteger newValue = new SerializableInteger(0);
            newValue.setStorage(this);
            storedAmount.add(newValue);
        }

        try {
            CsvObject co = new CsvObject(loadResource("storage_base.csv"));
            List<String[]> lData = co.getTableData();
            for (String[] arrStr : lData) {
                if (arrStr.length == 9) {//int stockGroundId, int shelfnum, int compartmentNum, int level, double distSource, double distDrain, double distAvg, String abc, int fixedStockGround
                    freeStorageAdd(new StockGround(Integer.parseInt(arrStr[0]), Integer.parseInt(arrStr[1]), Integer.parseInt(arrStr[2]), Integer.parseInt(arrStr[3]), Double.parseDouble(arrStr[4]), Double.parseDouble(arrStr[5]), Double.parseDouble(arrStr[6]), arrStr[7], Integer.parseInt(arrStr[8])));
                    
                }
            }
        } catch (IOException ex) {
        }

    }

    protected String loadResource(String resourceFileName) throws IOException {
        String toReturn = null;
        //InputStream is = this.getClass().getResourceAsStream(resourceFileName);
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/" + resourceFileName);
        try {
            byte[] buffer = new byte[4096];
            StringBuilder sb = new StringBuilder();
            while (is.read(buffer) > 0) {
                sb.append(new String(buffer, "UTF-8"));
            }
            toReturn = sb.toString();
        } catch (IOException ex) {
            /*logger.error(new StringBuilder(
             "Unable to load embedded resource: '").append(
             resourceFileName).append("'").toString(), ex);*/
            System.out.println("Error loading file");
            throw ex;
        }
        is.close();
        return toReturn;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<StockGround> getFreeStocks() {
        return freeStocks;
    }

    public void setFreeStocks(List<StockGround> freeStocks) {
        this.freeStocks = freeStocks;
    }

    public List<StockGround> getOccStocks() {
        return occStocks;
    }

    public void setOccStocks(List<StockGround> occStocks) {
        this.occStocks = occStocks;
    }

    public List<SerializableInteger> getStoredAmount() {
        return storedAmount;
    }

    public void setStoredAmount(List<SerializableInteger> storedAmount) {
        this.storedAmount = storedAmount;
    }

    public List<PalletsInProgress> getPalletsNotInStorage() {
        return palletsNotInStorage;
    }

    public void setPalletsNotInStorage(List<PalletsInProgress> palletsNotInStorage) {
        this.palletsNotInStorage = palletsNotInStorage;
    }
    
    public void occStorageAdd(StockGround stockground){
        this.occStocks.add(stockground);
        stockground.setStorageOcc(this);
        stockground.setStorageFree(null);
    }
    
    public void freeStorageAdd(StockGround stockGround){
        this.freeStocks.add(stockGround);
        stockGround.setStorageFree(this);
        stockGround.setStorageOcc(null);
    }
    
    public Storage copy(Storage base){
        
         Storage s = new Storage();
        
        List<StockGround> occStocksCopy = new ArrayList<>();
        for(StockGround sg : occStocks){
            StockGround clone = sg.clone();
            clone.setStorageOcc(base);
            
        }
        
       return null;
    }

    /**
     * @return the sortedIn
     */
    public boolean isSortedIn() {
        return sortedIn;
    }

    /**
     * @param sortedIn the sortedIn to set
     */
    public void setSortedIn(boolean sortedIn) {
        this.sortedIn = sortedIn;
    }

    /**
     * @return the sortedOut
     */
    public boolean isSortedOut() {
        return sortedOut;
    }

    /**
     * @param sortedOut the sortedOut to set
     */
    public void setSortedOut(boolean sortedOut) {
        this.sortedOut = sortedOut;
    }

}
