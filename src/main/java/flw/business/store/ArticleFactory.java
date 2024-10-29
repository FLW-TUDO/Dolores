/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.util.CsvObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alpl
 */
public class ArticleFactory {

    private volatile static ArticleFactory singleton;
    private List<Article> lArticles;

    private ArticleFactory() {
        lArticles = new ArrayList<Article>();
        fillArticles();
    }

    public static ArticleFactory getInstance() {
        if (singleton == null) {
            singleton = new ArticleFactory();
        }
        return singleton;
    }

    public List<Article> getArticles() {
        return lArticles;
    }

    /*
    TODO: Delete
    
    public LinkedList<ArticleDynamics> getArticleDynamics() {
        LinkedList<ArticleDynamics> aDList = new LinkedList<ArticleDynamics>();

        for (Article art : lArticles) {
            ArticleDynamics aD = new ArticleDynamics();
            aD.setArticle(art);
            aDList.add(aD);
        }

        return aDList;
    }
    
    */

    
    /*
        nur die Artieklnummer wird benutzt
    */
    private void fillArticles() {
        try {
            CsvObject co = new CsvObject(loadResource("article_base.csv"));
            List<String[]> lData = co.getTableData();
            for (String[] arrStr : lData) {
                if (arrStr.length == 13) {

                    //String name, double salePrice, int articleNumber, double pricePerPallet, int fixcosts, 
                    //int rabateAmount1, int rabateAmount2, int rabateCost1, int rebateCost2, String abc_classification

                    int x = Integer.parseInt(arrStr[9]);
                    int y = Integer.parseInt(arrStr[10]);
                    int z = Integer.parseInt(arrStr[11]);

                    Article a = new Article(arrStr[0], Double.parseDouble(arrStr[1]), Integer.parseInt(arrStr[2]),
                            Double.parseDouble(arrStr[3]), Integer.parseInt(arrStr[4]), Integer.parseInt(arrStr[5]),
                            Integer.parseInt(arrStr[6]), Integer.parseInt(arrStr[7]), Integer.parseInt(arrStr[8]), x, y, z, arrStr[12]);
                    lArticles.add(a);



                }
            }
        } catch (IOException ex) {
        }


    }

    protected String loadResource(String resourceFileName) throws IOException {
        String toReturn = null;
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("flw/business/util/resources/" + resourceFileName);



        try {
            byte[] buffer = new byte[4096];
            StringBuilder sb = new StringBuilder();
            while (input.read(buffer) > 0) {
                sb.append(new String(buffer, "UTF-8"));
            }
            toReturn = sb.toString();
        } catch (IOException ex) {
            /*logger.error(new StringBuilder(
             "Unable to load embedded resource: '").append(
             resourceFileName).append("'").toString(), ex);*/
            System.out.println("error loading file");
            throw ex;
        }
        input.close();
        return toReturn;
    }
}
