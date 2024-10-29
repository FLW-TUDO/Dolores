/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.calcs;

import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresState;
import flw.business.store.ArticleDynamics;
import flw.business.store.CustomerJob;
import flw.business.util.DoloresConst;
import java.util.List;
import java.util.Map;
import java.util.Random;
import flw.business.store.CustomerJobDynamics;
import flw.business.util.CsvObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author tilu
 */
public class JobCalculator extends AbstractCalculator {

    //private double pmax = 0d;	//Maximum number of customer-jobs the player can handle in current periode
    private Map<String, Double> mDoubles = new TreeMap<String, Double>();
    private List<CustomerJob> jobsToPersist;

    public JobCalculator(List<AbstractCalculator> lCalculators) {
        super(lCalculators);
    }

    @Override
    public void calculate(DoloresGameInfo gameInfo) {
        jobsToPersist = new ArrayList<CustomerJob>();

        double pmax = this.calculatePMax(gameInfo);

        int amount_classification_sum = 0;
        for (int i : DoloresConst.AMOUNT_CLASSIFICATION) {
            amount_classification_sum += i;
        }
        double last_amount_probability = 0;
        for (int i = 0; i < DoloresConst.AMOUNT_CLASSIFICATION.length; i++) {
            last_amount_probability = DoloresConst.AMOUNT_CLASSIFICATION[i] * 1d / amount_classification_sum + last_amount_probability;
            mDoubles.put(new StringBuilder("amount_probability_").append(i).toString(), last_amount_probability);
        }



        List<ArticleDynamics> articleDynamics = gameInfo.getCurrentState().getArticleDynamics();

        int index = 1;
        int ansprechkennziffer_summe = 0; //German, because the semantic is not known currently
        for (ArticleDynamics aD : articleDynamics) {
            aD.setState(gameInfo.getCurrentState());
            ansprechkennziffer_summe += index;
            index++;
        }
        double last_calling_probability = 0d;
        for (int i = 0; i < articleDynamics.size(); i++) {
            double y = i + 1;
            last_calling_probability = (y / ansprechkennziffer_summe) + last_calling_probability;
            mDoubles.put(new StringBuilder("calling_probability_").append(i).toString(), last_calling_probability);
        }

        int palette_sum = 0;
        Random r = new Random();
        DoloresState currentState = gameInfo.getCurrentState();
        int aktround = Integer.parseInt(currentState.getValue(DoloresConst.DOLORES_GAME_ROUND_NUMBER));

        if (aktround != 10) {
            while (palette_sum < pmax) {
                int articleid = 0;
                double randomnum = r.nextDouble();
                while (randomnum > mDoubles.get(new StringBuilder("calling_probability_").append(articleid).toString()) && articleid < articleDynamics.size() - 1) {
                    articleid++;
                }

                randomnum = r.nextDouble();
                int amountid = 0;
                while (randomnum > mDoubles.get(new StringBuilder("amount_probability_").append(amountid).toString()) && amountid < DoloresConst.AMOUNT_CLASSIFICATION.length - 1) {
                    amountid++;
                }
                ArticleDynamics currentArticle = articleDynamics.get(articleid);

                int palette_amount = (amountid + 1);
                double jobvalue = palette_amount * currentArticle.getSalePrice();


                // -> Neue bestellung hier!

                CustomerJobDynamics customerJobDynamics = new CustomerJobDynamics(palette_amount, 0);
                customerJobDynamics.setArticleDynamics(currentArticle);
                CustomerJob customerJob = new CustomerJob(jobvalue, palette_amount, aktround + 1);


                //-> only to Export PalletDynamics (CustomerJobs = 0)
                  /*
                 CustomerJobDynamics customerJobDynamics = new CustomerJobDynamics(0, 0);
                 customerJobDynamics.setArticleDynamics(currentArticle);
                 CustomerJob customerJob = new CustomerJob(jobvalue, 0, aktround+1);
                 */



                customerJobDynamics.setCustomerJob(customerJob);

                jobsToPersist.add(customerJob);



                currentState.addCustomerJobDynamics(customerJobDynamics);

                //currentState.addCustomerJob(new CustomerJob(jobvalue, palette_amount, currentArticle, aktround));


                palette_sum += palette_amount;
            }
        } else {
            loadOldDynamics(gameInfo);
        }
        int newJobs = jobsToPersist.size();
        gameInfo.getCurrentState().setValue("new_jobs", String.valueOf(newJobs));
    }

    public double calculatePMax(DoloresGameInfo gameInfo) {
        double pmaxx = Double.valueOf(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_PMAX));

        if (gameInfo.getCurrentState().getRoundNumber() != 10) {
            double current_customer_satisfaction = Double.parseDouble(gameInfo.getCurrentState().getValue(DoloresConst.DOLORES_KEY_CUSTOMER_SATISFACTION));
            double current_satisfaction_factor = -1;
            for (int i = 0; i < DoloresConst.CUSTOMER_SATISFACTION_LEVEL.length; i++) {
                if (current_customer_satisfaction <= DoloresConst.CUSTOMER_SATISFACTION_LEVEL[i]) {
                    current_satisfaction_factor = DoloresConst.CUSTOMER_SATISFACTION_LEVEL_FACTOR[i];
                    break;
                }
            }

            if (current_satisfaction_factor == -1) {
                current_satisfaction_factor = DoloresConst.CUSTOMER_SATISFACTION_LEVEL_FACTOR[DoloresConst.CUSTOMER_SATISFACTION_LEVEL.length];
            }
            pmaxx = pmaxx + Math.round(DoloresConst.PALLET_INCREASE * current_satisfaction_factor);
            pmaxx = (pmaxx < 20 ? 20 : pmaxx);
            gameInfo.getCurrentState().setValue(DoloresConst.DOLORES_KEY_PMAX, String.valueOf(pmaxx));
        }
        return pmaxx;
    }

    public List<CustomerJob> getJobsToPersist() {
        return jobsToPersist;
    }

    @Override
    public void prepareNextRound(DoloresGameInfo gameInfo) {
    }

    @Override
    public List<Object> getToUpdate() {
        return new ArrayList<Object>(); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadOldDynamics(DoloresGameInfo gameInfo) {
        try {
            CsvObject co = new CsvObject(loadResource("oldorders.csv"));
            List<String[]> lData = co.getTableData();
            for (String[] arrStr : lData) {
                if (arrStr.length == 8) {
                    int articleNumber = Integer.parseInt(arrStr[2]);
                    int amount = Integer.parseInt(arrStr[4]);
                    int jobValue = Integer.parseInt(arrStr[5]);

                    CustomerJobDynamics jobDyn = new CustomerJobDynamics(amount, 0);

                    for (ArticleDynamics ad : gameInfo.getCurrentState().getArticleDynamics()) {
                        if (ad.getArticleNumber() == articleNumber) {
                            jobDyn.setArticleDynamics(ad);
                        }
                    }
                    CustomerJob customerJob = new CustomerJob(jobValue, amount, 11);
                    jobDyn.setCustomerJob(customerJob);
                    gameInfo.getCurrentState().addCustomerJobDynamics(jobDyn);

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
            System.out.println("error beim laden von file");
            throw ex;
        }
        input.close();
        return toReturn;
    }
}
