/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.util;

import flw.business.store.ArticleDynamics;
import flw.business.store.ConveyorDynamics;
import flw.business.store.CustomerJobDynamics;
import flw.business.store.EmployeeDynamics;
import flw.business.store.OrderDynamics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tilu
 */
public class Copier<T> {

    public List<T> copyToLinkedList(List<T> src) {
        List<T> dest = new ArrayList<T>();

        for (T t : src) {
            dest.add(t);
        }

        return dest;
    }

    public ArrayList<T> copyToArrayList(List<T> src) {
        ArrayList<T> dest = new ArrayList<T>(src);
        return dest;
    }

    public List<EmployeeDynamics> copyToLinkedListED(List<EmployeeDynamics> src) {
        List<EmployeeDynamics> dest = new ArrayList<EmployeeDynamics>();



        for (EmployeeDynamics ed : src) {

            
            EmployeeDynamics employeeDynamics = new EmployeeDynamics(ed.getQualifications(), ed.getSalary());
            
            employeeDynamics.setFpRound(ed.getFpRound());
            employeeDynamics.setQmRound(ed.getQmRound());
            employeeDynamics.setSecRound(ed.getSecRound());
            employeeDynamics.setEmployee(ed.getEmployee());
            employeeDynamics.setMotivationPercentage(ed.getMotivationPercentage());
            employeeDynamics.setProcess(ed.getProcess());
            //employeeDynamics.setState(ds);
            dest.add(employeeDynamics);
            employeeDynamics.setId(null);

        }

        return dest;
    }

    public List<ArticleDynamics> copyToLinkedListAD(List<ArticleDynamics> src) {

        List<ArticleDynamics> dest = new ArrayList<ArticleDynamics>();

        for (ArticleDynamics ad : src) {
            ArticleDynamics articleDynamics = new ArticleDynamics(null, null, null, ad.getEstimatedRange(), ad.getAvgConsumption(), ad.getOptimalOrderAmount(), ad.getOrderedPallets(), ad.getOrderCosts());
            articleDynamics.setId(null);

            List<SerializableInteger> oldSIList = ad.getPalletsOfArticleInProcess();
            List<SerializableInteger> newSIList = new ArrayList<SerializableInteger>();

            for (SerializableInteger oldSI : oldSIList) {
                SerializableInteger newSI = new SerializableInteger(oldSI.getValue());
                newSI.setArticleDynamics(ad);
                newSIList.add(newSI);
            }

            articleDynamics.setPalletsOfArticleInProcess(newSIList);

            articleDynamics.setArticle(ad.getArticle());
            dest.add(articleDynamics);


        }

        return dest;

    }

    public List<ConveyorDynamics> copyToLinkedListCD(List<ConveyorDynamics> src) {
        List<ConveyorDynamics> dest = new ArrayList<ConveyorDynamics>();



        for (ConveyorDynamics cd : src) {

            ConveyorDynamics conveyorDynamics = new ConveyorDynamics(cd.getOverhaul_costs(), cd.getProcess());

            conveyorDynamics.setConditionPercentage(cd.getConditionPercentage());
            conveyorDynamics.setConveyor(cd.getConveyor());
            conveyorDynamics.setCurrentValue(cd.getCurrentValue());
            conveyorDynamics.setMaintenanceEnabled(cd.isMaintenanceEnabled());
            conveyorDynamics.setPeriodBought(cd.getPeriodBought());
            //-> overhaul alway false after new round
            //conveyorDynamics.setOverhaul(cd.isOverhaul());
            conveyorDynamics.setStatus(cd.getStatus());
            conveyorDynamics.setId(null);
            conveyorDynamics.setSold(cd.isSold());
            dest.add(conveyorDynamics);


        }

        return dest;
    }

    public List<CustomerJobDynamics> copyToLinkedListCJD(List<CustomerJobDynamics> src) {
        List<CustomerJobDynamics> dest = new ArrayList<CustomerJobDynamics>();

        for (CustomerJobDynamics cjd : src) {
            
            if(!cjd.isFinished()){
                CustomerJobDynamics customerJobDynamics = new CustomerJobDynamics(cjd.getToDeliver(), cjd.getRequestedAmount());
                customerJobDynamics.setCustomerJob(cjd.getCustomerJob());
                customerJobDynamics.setId(null);
                customerJobDynamics.setArticleNumber(cjd.getArticleNumber());
                customerJobDynamics.setFinished(cjd.isFinished());
                dest.add(customerJobDynamics);
            }
        }
        return dest;
    }

    public List<OrderDynamics> copyToLinkedListOD(List<OrderDynamics> src) {
        List<OrderDynamics> dest = new ArrayList<OrderDynamics>();

        for (OrderDynamics od : src) {
            //if (!od.isComplete()) {
                OrderDynamics orderDynamics = new OrderDynamics(od.getArticleNumber());
                orderDynamics.setOrderno(od.getOrderno());
                orderDynamics.setDeliveryAmount(od.getDeliveryAmount());
                orderDynamics.setId(null);
                orderDynamics.setOrder(od.getOrder());
                orderDynamics.setDeliveryAmount(od.getDeliveryAmount());
                orderDynamics.setComplete(od.isComplete());
                orderDynamics.setAlreadyCalc(od.isAlreadyCalc());
                orderDynamics.setNewOrder(od.isNewOrder());
                dest.add(orderDynamics);

            //}
        }
        return dest;
    }
/*
    public LinkedList<PalletsInProgressDynamics> copyToLinkedListPIPD(List<PalletsInProgressDynamics> src) {
        LinkedList<PalletsInProgressDynamics> dest = new LinkedList<PalletsInProgressDynamics>();

        for (PalletsInProgressDynamics pd : src) {
            
            if(pd.getCurrentProcess()<4){
                PalletsInProgressDynamics progressDynamics = new PalletsInProgressDynamics(pd.getDemandRound(), pd.getCurrentProcess(), pd.getOrderno());
                progressDynamics.setPalletsInProgress(pd.getPalletsInProgress());
                progressDynamics.setError(pd.getError());
                progressDynamics.setId(null);
                progressDynamics.setStored(pd.isStored());
                progressDynamics.setStockgroundId(pd.getStockgroundId());
                progressDynamics.setUnitLoadSafetyDevices(pd.isUnitLoadSafetyDevices());
                dest.add(progressDynamics);
            }
        }
        return dest;
    }*/
}

/*
 *  private double conditionPercentage = 100d;
 private int periodBought = -1;
 private int status = DoloresConst.CONVEYOR_STATE_OK;
 private int process = 0;
 private int overhaul_costs;
 private double currentValue;
 private boolean maintenanceEnabled = false;
 private boolean overhaul = false;
 private Long conveyorId;
 */
