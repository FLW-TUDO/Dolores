/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresState;
import flw.business.store.Article;
import flw.business.store.ArticleDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.Order;
import flw.business.store.OrderDynamics;
import flw.business.store.StockGround;
import flw.business.store.Storage;
import flw.business.util.DoloresConst;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.SlideEndEvent;


/*
 * TODO: überflüssige Methoden ausmisten!
 */
/**
 *
 * @author tilu
 */
@ManagedBean(name = "orderView")
@ViewScoped
public class OrderView implements Serializable {

    /**
     * Creates a new instance of OrderView
     */
    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    @ManagedProperty(value = "#{orderPM}")
    private OrderPM orderPM;
    private boolean firstCall = true;
    private List<StockGround> freeStocks = new ArrayList<StockGround>();
    private List<StockGround> occStocks = new ArrayList<StockGround>();
    private ArticleDynamics clickedArticleDyn;
    private int clickedArticleNumber;
    private String orderAmount;
    private String express;
    private int deliveryPeriod = 0;
    private int wishPeriod;
    private int rebateLevel;
    private int gameInfoId;
    private List<Article> articles;
    private List<ArticleDynamics> articleDynamics;
    private List<CustomerJobDynamics> jobD;
    private List<CustomerJobDynamics> jobThisPeriod;
    private List<CustomerJobDynamics> jobOldPeriod;
    private List<OrderDynamics> openOrders;
    private Map<String, Integer> deliveryTypes = new HashMap<String, Integer>();
    private String deliveryType;
    private boolean wishEnabled = false;
    private int minValue;
    private int maxValue;
    private int test;
    private List<Article> cArticle;
    private boolean init;
    private int auspuffCount, auspuffCountR;
    private int bremsenCount, bremsenCountR;
    private int rückCount, rückCountR;
    private int stossCount, stossCountR;
    private int overallCount, overallCountR;
    private double auspuffValue, auspuffValueR;
    private double bremsenValue, bremsenValueR;
    private double rückValue, rückValueR;
    private double stossValue, stossValueR;
    private double overallValue, overallValueR;
    @EJB
    private Service service;

    /**
     * Creates a new instance of OrderPM
     */
    public OrderView() {

    }

    @PostConstruct
    public void init() {
        deliveryTypes.put(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("java.normalDel"), orderPM.getClickedArticleDyn().getArticle().getNormalDelivery());
        deliveryTypes.put(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("java.fastDel"), orderPM.getClickedArticleDyn().getArticle().getExpressDelivery());
        deliveryType = "0";
        
        
    }

    public Map<String, Integer> getDeliveryTypes() {
        return deliveryTypes;
    }

    public void setDeliveryTypes(Map<String, Integer> deliveryType) {
        this.deliveryTypes = deliveryTypes;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        orderPM.setDeliveryType(deliveryType);
        this.deliveryType = deliveryType;
    }

    public boolean isWishEnabled() {
        return wishEnabled;
    }

    public void setWishEnabled(boolean wishEnabled) {
        orderPM.setWishEnabled(wishEnabled);
        this.wishEnabled = wishEnabled;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        orderPM.setMinValue(minValue);
        this.minValue = minValue;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        orderPM.setTest(test);
        this.test = test;
    }

    public /*String*/ void orderConfirm() throws Exception {

        gameInfoId = gameInfoPM.getGameId();

        DoloresState ds = gameInfoPM.getCurrentState();

        try {
            wishPeriod = Integer.valueOf(getWishPeriod());
        } catch (Exception e) {
            System.err.println("NaN in Wishperiod!");

        }

        ArticleDynamics artDynC = null;
        for (ArticleDynamics art : ds.getArticleDynamics()) {
            if (art.getArticleNumber() == clickedArticleDyn.getArticleNumber()) {
                artDynC = art;
            }
        }
        if (artDynC != null) {

            int deliverDuration;

            if (deliveryType.equals("1")) {

                wishPeriod = 0;
                deliverDuration = 1;
            } else if (deliveryType.equals("3")) {

                wishPeriod = 0;
                deliverDuration = 3;

            } else {

                deliverDuration = wishPeriod - gameInfoPM.getCurrentState().getRoundNumber();
            }

            Order order = new Order(ds.getRoundNumber(), deliverDuration, wishPeriod, artDynC.getFixCosts(), (int) getDeliveryCostPerPallet(), (int) getPalletPrice(), (int) Integer.valueOf(orderAmount));
            OrderDynamics orderDynamics = new OrderDynamics(ds, artDynC, order, artDynC.getArticleNumber());
            ////TODO Check ob überflüssig
            //service.persistOrder(order);
            ds.addOrderDynamcis(orderDynamics);
        } else {
            throw new Exception("Order not valid!");
        }

        //ds.getOrderDynamics().add(order.getDynamics());
        //return "orderConfirmation";
    }

    public ArticleDynamics getClickedArticleDyn() {
        return orderPM.getClickedArticleDyn();
    }

    public void setClickedArticleDyn(ArticleDynamics clickedArticleDyn) {
        this.clickedArticleDyn = clickedArticleDyn;
        orderPM.setClickedArticleDyn(clickedArticleDyn);
    }

    public int getDeliveryPeriod() {

        deliveryType = "2";
        if (firstCall) {
            deliveryPeriod = gameInfoPM.getInfo().getCurrentState().getRoundNumber() + 3;
            orderPM.setDeliveryPeriod(deliveryPeriod);
            firstCall = false;
        }
        return deliveryPeriod;
    }

    public void setDeliveryPeriod(int deliveryPeriod) {
        orderPM.setDeliveryPeriod(deliveryPeriod);
        this.deliveryPeriod = deliveryPeriod;
    }

    public int getWishPeriod() {
        return wishPeriod;
    }

    public void setWishPeriod(int wishPeriod) {
        orderPM.setWishPeriod(wishPeriod);
        this.wishPeriod = wishPeriod;
    }

    public int getClickedArticleNumber() {

        return orderPM.getClickedArticleNumber();

    }

    public void setClickedArticleNumber(int clickedArticleNumber) {

        this.clickedArticleNumber = clickedArticleNumber;
        orderPM.setClickedArticleNumber(clickedArticleNumber);

    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public void setOrderPM(OrderPM orderPM) {
        this.orderPM = orderPM;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public int getRebateLevel() {
        int amount = Integer.valueOf(orderAmount);

        if (amount >= clickedArticleDyn.getRebateAmount1()) {
            if (amount >= clickedArticleDyn.getRebateAmount2()) {
                rebateLevel = 2;
            } else {
                rebateLevel = 1;
            }
        } else {
            rebateLevel = 0;
        }

        return rebateLevel;
    }

    public int getDeliveryCost() {
        if (deliveryPeriod > gameInfoPM.getCurrentState().getRoundNumber() + 1) {
            return clickedArticleDyn.getArticle().getNormalDeliveryCost();
        } else {
            return clickedArticleDyn.getArticle().getExpressDeliveryCost();
        }
    }

    public void setRebateLevel(int rebateLevel) {
        orderPM.setRebateLevel(rebateLevel);
        this.rebateLevel = rebateLevel;
    }

    public double getPriceAfterRebate() {
        int rebateLevela = getRebateLevel();
        double price = 0.0;
        switch (rebateLevela) {
            case 0:
                price = clickedArticleDyn.getPricePerPallet();
                break;
            case 1:
                price = clickedArticleDyn.getRebateCost1();
                break;
            case 2:
                price = clickedArticleDyn.getRebateCost2();
                break;
            default:
                price = clickedArticleDyn.getPricePerPallet();

        }

        return price;
    }

    public double getCompletePalletPrice() {
        return getPriceAfterRebate() * Double.valueOf(getOrderAmount());
    }

    public double getPalletPrice() {
        return getPriceAfterRebate();
    }

    public double getCompleteDeliveryCost() {
        return Double.valueOf(getOrderAmount()) * getDeliveryCost();
    }

    public double getDeliveryCostPerPallet() {
        return Double.valueOf(getDeliveryCost());
    }

    public double getCompleteOrderCost() {
        return getCompleteDeliveryCost() + getCompletePalletPrice() + clickedArticleDyn.getFixCosts();
    }

    public void setOrderAmount(String orderAmount) {
        orderPM.setOrderAmount(orderAmount);
        this.orderAmount = orderAmount;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        orderPM.setExpress(express);
        this.express = express;
    }

    public void sliderMoved(ValueChangeEvent e) {
        setOrderAmount((String) e.getNewValue());

    }

    public List<ArticleDynamics> getArticleDynamics() {
        DoloresState ds = gameInfoPM.getCurrentState();
        articleDynamics = new ArrayList<ArticleDynamics>();

        for (ArticleDynamics aD : ds.getArticleDynamics()) {
            articleDynamics.add(aD);
        }

        return articleDynamics;
    }

    public void setArticleDynamics(List<ArticleDynamics> articleDynamics) {
        this.articleDynamics = articleDynamics;
    }

    public void deliveryTypeChanged() {
        int currentRoundNumber = gameInfoPM.getCurrentState().getRoundNumber();
        if (deliveryType.equals("3")) {

            minValue = currentRoundNumber + 3;
            orderPM.setMinValue(minValue);
            orderPM.setDeliveryPeriod(minValue);
            this.setDeliveryPeriod(minValue);
            
            this.setMaxValue(100);
        } else {

            minValue = currentRoundNumber + Integer.valueOf(deliveryType);
            orderPM.setMinValue(minValue);
            orderPM.setDeliveryPeriod(minValue);
            this.setDeliveryPeriod(minValue);
            

            if (deliveryType.equals("1")) {
                this.setMaxValue(currentRoundNumber + 2);
            } else {
                this.setMaxValue(currentRoundNumber + 1);
            }
        }
    }

    public void sliderChanged(SlideEndEvent event) {
        int currentRoundNumber = gameInfoPM.getCurrentState().getRoundNumber();
        //minValue = currentRoundNumber + Integer.valueOf(event.getValue());

        this.setDeliveryPeriod(event.getValue());
        orderPM.setMinValue(event.getValue());
        orderPM.setDeliveryPeriod(event.getValue());

    }

    public List<CustomerJobDynamics> getJobDynamics() {

        auspuffCount = 0;
        auspuffCountR = 0;
        bremsenCount = 0;
        bremsenCountR = 0;
        rückCount = 0;
        rückCountR = 0;
        stossCount = 0;
        stossCountR = 0;
        overallCount = 0;
        overallCountR = 0;

        auspuffValue = 0;
        auspuffValueR = 0;
        bremsenValue = 0;
        bremsenValueR = 0;
        rückValue = 0;
        rückValueR = 0;
        stossValue = 0;
        stossValueR = 0;
        overallValue = 0;
        overallValueR = 0;

        DoloresState ds = gameInfoPM.getCurrentState();

        jobD = ds.getJobDynamics();

        jobOldPeriod = new ArrayList<CustomerJobDynamics>();
        jobThisPeriod = new ArrayList<CustomerJobDynamics>();

        for (CustomerJobDynamics cjd : jobD) {

            if (cjd.getOrderPeriode() >= Integer.valueOf(ds.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER))) {

                jobThisPeriod.add(cjd);

                if (cjd.getArticleNumber() == 100101) {
                    auspuffCount = auspuffCount + cjd.getToDeliver();
                    auspuffValue = auspuffValue + cjd.getJobvalue();
                } else if (cjd.getArticleNumber() == 100102) {
                    bremsenCount = bremsenCount + cjd.getToDeliver();
                    bremsenValue = bremsenValue + cjd.getJobvalue();
                } else if (cjd.getArticleNumber() == 100103) {
                    rückCount = rückCount + cjd.getToDeliver();
                    rückValue = rückValue + cjd.getJobvalue();
                } else if (cjd.getArticleNumber() == 100104) {
                    stossCount = stossCount + cjd.getToDeliver();
                    stossValue = stossValue + cjd.getJobvalue();
                }
                overallCount = overallCount + cjd.getToDeliver();
                overallValue = overallValue + cjd.getJobvalue();
            } else {

                jobOldPeriod.add(cjd);

                if (cjd.getArticleNumber() == 100101) {
                    auspuffCountR = auspuffCountR + cjd.getToDeliver();
                    auspuffValueR = auspuffValueR + cjd.getJobvalue();
                } else if (cjd.getArticleNumber() == 100102) {
                    bremsenCountR = bremsenCountR + cjd.getToDeliver();
                    bremsenValueR = bremsenValueR + cjd.getJobvalue();
                } else if (cjd.getArticleNumber() == 100103) {
                    rückCountR = rückCountR + cjd.getToDeliver();
                    rückValueR = rückValueR + cjd.getJobvalue();
                } else if (cjd.getArticleNumber() == 100104) {
                    stossCountR = stossCountR + cjd.getToDeliver();
                    stossValueR = stossValueR + cjd.getJobvalue();
                }
                overallCountR = overallCountR + cjd.getToDeliver();
                overallValueR = overallValueR + cjd.getJobvalue();

            }
        }

        return jobD;
    }

    public List<Article> getArticles() {
        DoloresState ds = gameInfoPM.getCurrentState();
        articles = new ArrayList<Article>();

        for (ArticleDynamics aD : ds.getArticleDynamics()) {
            articles.add(aD.getArticle());
        }

        return articles;
    }

    public void setJobs(List<CustomerJobDynamics> jobs) {
        this.jobD = jobs;
    }

    public List<StockGround> getFreeStocks() {
        return freeStocks;
    }

    public void setFreeStocks(List<StockGround> freeStocks) {
        this.freeStocks = freeStocks;
    }

    /*
     public Article getClickedArticle() {
     if (clickedArticle != null) {
     clickedArticleNumber = clickedArticle.getArticleNumber();
     }

     if (clickedArticle == null && clickedArticleNumber != 0) {
     gameInfoId = gameInfoPM.getGameId();
     DoloresState ds = service.retrieveCurrentState(gameInfoId);
     LinkedList<Article> articles1 = getArticles();
     for (Article art : articles1) {
     if (art.getArticleNumber() == clickedArticleNumber) {
     clickedArticle = art;
     }
     }
     }

     return clickedArticle;
     }*/
    public List<StockGround> getOccStocks() {
        Storage storage = gameInfoPM.getCurrentState().getStorage();

        int storedPalletCount = storage.getStoredPalletCount(100101);

        return occStocks;
    }

    public void setOccStocks(List<StockGround> occStocks) {
        this.occStocks = occStocks;
    }

    public int getValue() {
        Storage storage = gameInfoPM.getCurrentState().getStorage();

        int storedPalletCount = storage.getStoredPalletCount(clickedArticleDyn.getArticleNumber());

        return (int) (storedPalletCount * clickedArticleDyn.getSalePrice());
    }

    public int showStocks() {
        Storage storage = gameInfoPM.getCurrentState().getStorage();

        int storedPalletCount = storage.getStoredPalletCount(clickedArticleDyn.getArticleNumber());
        return storedPalletCount;
    }

    public int showFreeSpace() {
        Storage storage = gameInfoPM.getCurrentState().getStorage();
        int x = storage.getFreeStockGroundCount();

        return x;
    }

    public String toStorageInfoFromMenu(int artNr) {

        for (ArticleDynamics art : gameInfoPM.getCurrentState().getArticleDynamics()) {
            if (artNr == art.getArticleNumber()) {
                clickedArticleDyn = art;
            }
        }

        return "order/storageInfo.xhtml?faces-redirect=true";
    }

    public String toStorageInfoFromMenu(long artNr) {

        for (ArticleDynamics art : gameInfoPM.getCurrentState().getArticleDynamics()) {
            if (artNr == art.getArticleNumber()) {
                clickedArticleDyn = art;
            }
        }

        return "order/storageInfo.xhtml?faces-redirect=true";
    }

    public String toStorageInfoFromFolder(long artNr) {

        for (ArticleDynamics art : gameInfoPM.getCurrentState().getArticleDynamics()) {
            if (artNr == art.getArticleNumber()) {
                clickedArticleDyn = art;
            }
        }

        return "/./order/storageInfo.xhtml?faces-redirect=true";
    }

    public String toStorageInfo(ArticleDynamics artDyn) {

        clickedArticleDyn = artDyn;

        return "order/storageInfo.xhtml?faces-redirect=true";
    }

    public String toOrderInfo(ArticleDynamics artDyn) {
        clickedArticleDyn = artDyn;

        return "order/orderInfo.xhtml?faces-redirect=true";
    }

    public String toOrderForm(ArticleDynamics artDyn) {
        clickedArticleDyn = artDyn;
        return "orderForm";
    }

    public String toOrderConfirm() {
        if(orderPM.getDeliveryPeriod() < gameInfoPM.getCurrentState().getRoundNumber()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale()).getString("growl.errorDel")));
            return "article";
        }
        return "orderConfirm?faces-redirect=true";
    }

    public String toOrderForm() {
        return "orderForm";
    }

    public List<Article> getcArticle() {
        return cArticle;
    }

    /*
     public void setcArticle(List<Article> cArticle) {
     this.cArticle = cArticle;
     }*/
    public List<CustomerJobDynamics> getJobThisPeriod() {
        this.getJobDynamics();
        return jobThisPeriod;
    }

    public List<CustomerJobDynamics> getJobOldPeriod() {
        return jobOldPeriod;
    }

    public int getAuspuffCount() {
        return auspuffCount;
    }

    public int getAuspuffCountR() {
        return auspuffCountR;
    }

    public int getBremsenCount() {
        return bremsenCount;
    }

    public int getBremsenCountR() {
        return bremsenCountR;
    }

    public int getRückCount() {
        return rückCount;
    }

    public int getRückCountR() {
        return rückCountR;
    }

    public int getStossCount() {
        return stossCount;
    }

    public int getStossCountR() {
        return stossCountR;
    }

    public int getOverallCount() {
        return overallCount;
    }

    public int getOverallCountR() {
        return overallCountR;
    }

    public double getAuspuffValue() {
        return auspuffValue;
    }

    public double getAuspuffValueR() {
        return auspuffValueR;
    }

    public double getBremsenValue() {
        return bremsenValue;
    }

    public double getBremsenValueR() {
        return bremsenValueR;
    }

    public double getRückValue() {
        return rückValue;
    }

    public double getRückValueR() {
        return rückValueR;
    }

    public double getStossValue() {
        return stossValue;
    }

    public double getStossValueR() {
        return stossValueR;
    }

    public double getOverallValue() {
        return overallValue;
    }

    public double getOverallValueR() {
        return overallValueR;
    }
}
