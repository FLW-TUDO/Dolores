/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.presentation;

import flw.business.business.Service;
import flw.business.core.DoloresGameInfo;
import flw.business.core.DoloresPlayer;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

/**
 *
 * @author tilu
 */
@ManagedBean(name = "statPM")
@SessionScoped
public class StatisticsPM implements Serializable {

    @ManagedProperty(value = "#{gameInfoPM}")
    private GameInfoPM gameInfoPM;
    @ManagedProperty(value = "#{orderPM}")
    private OrderPM orderPM;
    private CartesianChartModel linearModel;
    private CartesianChartModel balanceChart;
    private CartesianChartModel satisfactionChart;
    private CartesianChartModel storageChart;
    private CartesianChartModel storageInfoChart1;
    private CartesianChartModel storageInfoChart2;
    private CartesianChartModel storageInfoChart3;
    private CartesianChartModel storageInfoChart4;
    private String category;
    private String chart;
    private Map<String, String> categories = new HashMap<String, String>();
    private Map<String, Map<String, String>> chartsData = new HashMap<String, Map<String, String>>();
    private Map<String, String> charts = new HashMap<String, String>();
    private boolean readyToChart;
    private boolean firstCall = true;
    @EJB
    private Service service;
    private String language;

    public StatisticsPM() {

        init();

    }

    public CartesianChartModel getLinearModel() {

        if (null == language || new Locale(language).toString().equalsIgnoreCase(FacesContext.getCurrentInstance().getViewRoot().getLocale().toString())) {
            language = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
        } else {
            init();
            language = FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
        }

        if ((chart != null) && (firstCall)) {
            createLinearModel();
            firstCall = false;
        }
        return linearModel;
    }

    public void setLinearModel(CartesianChartModel linearModel) {
        this.linearModel = linearModel;
    }

    public void setGameInfoPM(GameInfoPM gameInfoPM) {
        this.gameInfoPM = gameInfoPM;
    }

    public void setOrderPM(OrderPM orderPM) {
        this.orderPM = orderPM;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public boolean isReadyToChart() {
        return readyToChart;
    }

    public void setReadyToChart(boolean readyToChart) {
        this.readyToChart = readyToChart;
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories = categories;
    }

    public Map<String, Map<String, String>> getGraphsData() {
        return chartsData;
    }

    public void setGraphsData(Map<String, Map<String, String>> graphsData) {
        this.chartsData = graphsData;
    }

    public Map<String, String> getCharts() {
        return charts;
    }

    public void setCharts(Map<String, String> charts) {
        this.charts = charts;
    }

    public void handleSelectionChange() {
        if (category != null && !category.equals("")) {
            charts = chartsData.get(category);
            displaySelection();
        } else {
            charts = new HashMap<String, String>();
        }
    }

    public void handleSelectionChangeListener(AjaxBehaviorEvent e) {
        if (category != null && !category.equals("")) {
            charts = chartsData.get(category);
            displaySelection();
        } else {
            charts = new HashMap<String, String>();
        }
    }

    public void displaySelection() {
        FacesMessage msg = new FacesMessage("", "Aktualisiert");

        FacesContext.getCurrentInstance().addMessage(null, msg);
        createLinearModel();
    }

    private void createLinearModel() {
        linearModel = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        if (chart != null) {
            List retrieveStats = service.retrieveStats(chart, gameInfo.getId());
            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel(getKey(chart));

            Object[] toArray = retrieveStats.toArray();
            for (Object o : toArray) {
                //series1.set(o[1], o[0]);
                Object[] totoArray = (Object[]) o;
                String x = totoArray[0].toString();
                String y = totoArray[1].toString();

                int xd = Integer.valueOf(x).intValue();
                double yd = Double.valueOf(y).doubleValue();

                series1.set(xd, yd);
            }

            linearModel.addSeries(series1);
            readyToChart = true;

        } else {
            readyToChart = false;
        }

    }

    private String getKey(String value) {
        for (String s : charts.keySet()) {
            if (charts.get(s).equals(value)) {
                return s;
            }
        }
        return null;
    }

    public CartesianChartModel getBalanceChart() {
        balanceChart = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        List retrieveStats = service.retrieveStats("ACCOUNTBALANCE", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel(getKey("ACCOUNTBALANCE"));

        Object[] toArray = retrieveStats.toArray();
        int length = toArray.length;
        //Falls Periode zurück erlaubt wurde wird somit sicher gestellt, dass in den Graphen die Werte nur bis zur aktuellen Periode angezeigt werden
        if(gameInfoPM.getCurrentState().getRoundNumber() < length) length = gameInfoPM.getCurrentState().getRoundNumber();

        if (length > 5) {
            int startIndex = length - 6;
            while (startIndex < length) {
                Object[] totoArray = (Object[]) toArray[startIndex];
                String x = totoArray[0].toString();
                String y = totoArray[1].toString();

                int xd = Integer.parseInt(x) + 1;
                double yd = Double.parseDouble(y);

                if (startIndex == length - 1) {
                    series1.set(xd, gameInfoPM.getCurrentState().getAccountBalanceAsDouble());

                } else {
                    series1.set(xd, yd);
                }

                startIndex++;
            }
        } else {

            for (Object o : toArray) {
                //series1.set(o[1], o[0]);
                Object[] totoArray = (Object[]) o;
                String x = totoArray[0].toString();
                String y = totoArray[1].toString();

                int xd = Integer.parseInt(x) + 1;
                double yd = Double.parseDouble(y);

                series1.set(xd, yd);

            }
        }

        balanceChart.addSeries(series1);

        return balanceChart;
    }

    public void setBalanceChart(CartesianChartModel balanceChart) {
        this.balanceChart = balanceChart;
    }

    public CartesianChartModel getSatisfactionChart() {
        satisfactionChart = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        List retrieveStats = service.retrieveStats("SATISFACTION", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();

        Object[] toArray = retrieveStats.toArray();
        int length = toArray.length;
        //Falls Periode zurück erlaubt wurde wird somit sicher gestellt, dass in den Graphen die Werte nur bis zur aktuellen Periode angezeigt werden
        if(gameInfoPM.getCurrentState().getRoundNumber() < length) length = gameInfoPM.getCurrentState().getRoundNumber();

        if (length > 5) {
            int startIndex = length - 6;
            while (startIndex < length) {
                Object[] totoArray = (Object[]) toArray[startIndex];
                String x = totoArray[0].toString();
                String y = totoArray[1].toString();

                int xd = Integer.parseInt(x) + 1;
                double yd = Double.parseDouble(y);

                series1.set(xd, yd);
                startIndex++;
            }
        } else {

            for (Object o : toArray) {
                //series1.set(o[1], o[0]);
                Object[] totoArray = (Object[]) o;
                String x = totoArray[0].toString();
                String y = totoArray[1].toString();

                int xd = Integer.parseInt(x) + 1;
                double yd = Double.parseDouble(y);

                series1.set(xd, yd);
            }
        }
        satisfactionChart.addSeries(series1);

        return satisfactionChart;
    }

    public void setSatisfactionChart(CartesianChartModel satisfactionChart) {
        this.satisfactionChart = satisfactionChart;
    }

    public CartesianChartModel getStorageChart() {

        storageChart = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();
        List retrieveStats = null;
        try {
            retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_" + orderPM.getClickedArticleDyn().getArticleNumber(), gameInfo.getId());
        } catch (Exception e) {

        }
        LineChartSeries series1 = new LineChartSeries();
        if (retrieveStats != null) {
            Object[] toArray = retrieveStats.toArray();
            for (Object o : toArray) {
                //series1.set(o[1], o[0]);
                Object[] totoArray = (Object[]) o;
                String x = totoArray[0].toString();
                String y = totoArray[1].toString();

                int xd = Integer.parseInt(x);
                int yd = Integer.parseInt(y);

                series1.set(xd, yd);
            }
        }

        storageChart.addSeries(series1);

        return storageChart;

    }

    public void setStorageChart(CartesianChartModel storageChart) {
        this.storageChart = storageChart;
    }

    public CartesianChartModel getStorageInfoChart1() {
        storageInfoChart1 = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100101", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();

        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {
            //series1.set(o[1], o[0]);
            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x) + 1;
            double yd = Double.valueOf(y);

            series1.set(xd, yd);
        }

        storageInfoChart1.addSeries(series1);

        return storageInfoChart1;

    }

    public void setStorageInfoChart1(CartesianChartModel storageInfoChart1) {
        this.storageInfoChart1 = storageInfoChart1;
    }

    public CartesianChartModel getStorageInfoChart2() {
        storageInfoChart2 = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100102", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();

        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {

            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x) + 1;
            double yd = Double.parseDouble(y);

            series1.set(xd, yd);
        }

        storageInfoChart2.addSeries(series1);

        return storageInfoChart2;
    }

    public void setStorageInfoChart2(CartesianChartModel storageInfoChart2) {
        this.storageInfoChart2 = storageInfoChart2;
    }

    public CartesianChartModel getStorageInfoChart3() {
        storageInfoChart3 = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100103", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();

        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {

            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x) + 1;
            double yd = Double.parseDouble(y);

            series1.set(xd, yd);
        }

        storageInfoChart3.addSeries(series1);

        return storageInfoChart3;
    }

    public void setStorageInfoChart3(CartesianChartModel storageInfoChart3) {
        this.storageInfoChart3 = storageInfoChart3;
    }

    public CartesianChartModel getStorageInfoChart4() {
        storageInfoChart4 = new CartesianChartModel();

        DoloresGameInfo gameInfo = gameInfoPM.getInfo();

        List retrieveStats = service.retrieveStats("PROCESS_PALLET_COUNT_LA_100104", gameInfo.getId());
        LineChartSeries series1 = new LineChartSeries();

        Object[] toArray = retrieveStats.toArray();
        for (Object o : toArray) {

            Object[] totoArray = (Object[]) o;
            String x = totoArray[0].toString();
            String y = totoArray[1].toString();

            int xd = Integer.parseInt(x) + 1;
            double yd = Double.valueOf(y);

            series1.set(xd, yd);
        }

        storageInfoChart4.addSeries(series1);

        return storageInfoChart4;
    }

    public void setStorageInfoChart4(CartesianChartModel storageInfoChart4) {
        this.storageInfoChart4 = storageInfoChart4;
    }

    private void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("flw.language.language", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        categories.clear();
        chartsData.clear();

        categories.put(bundle.getString("ALLE"), "all"); //NOI18N
        categories.put(bundle.getString("ÜBERGEORDNETE KENNZAHLEN"), "numbers"); //NOI18N
        categories.put(bundle.getString("BEREICH BESTELLWESEN"), "orderDep");
        categories.put(bundle.getString("BEREICH ORGANISATION"), "orgaDep");
        categories.put(bundle.getString("BEREICH PERSONAL"), "empDep");
        categories.put(bundle.getString("BEREICH TECHNIK"), "techDep");

        Map<String, String> categoriesAll = new HashMap<String, String>();
        categoriesAll.put(bundle.getString("ERTRAG"), "INCOME_ROUND");
        categoriesAll.put(bundle.getString("ERTRAG DURCH VERKAUFTE PALETTEN"), "SALES_INCOME");
        categoriesAll.put(bundle.getString("KONTOSTAND"), "ACCOUNTBALANCE");
        categoriesAll.put(bundle.getString("KOSTEN"), "COSTS_ROUND");
        categoriesAll.put(bundle.getString("KOSTEN FÜR DIE LAGERHALTUNG"), "STORAGE_COST");
        categoriesAll.put(bundle.getString("KUNDENZUFRIEDENHEIT"), "SATISFACTION");

        categoriesAll.put(bundle.getString("INVESTITIONSKOSTEN INFORMATIONSTECHNIK"), "ITCOSTS");
        categoriesAll.put(bundle.getString("INVESTITIONSKOSTEN LADEHILFSMITTEL"), "LEQ");

        categoriesAll.put(bundle.getString("AKTUELLER BESTANDSWERT"), "STOCK_VALUE");
        categoriesAll.put(bundle.getString("ANZAHL PALETTEN GELIEFERT"), "OVERALL_FINISHED_JOBS_PALLETS");
        categoriesAll.put(bundle.getString("ANZAHL PALETTEN GELIEFERT PÜNKTLICH"), "ACCURATE_DELIVERED_PALLETS");
        categoriesAll.put(bundle.getString("ANZAHL PALETTEN GELIEFERT UNPÜNKTLICH"), "LATE_FINISHED_JOBS_PALLET_COUNT");
        //categoriesOrder.put("Anzahl Kundenaufträge insgesamt", "ph");
        //categoriesOrder.put("Anzahl Kundenaufträge offen", "ph");
        categoriesAll.put(bundle.getString("ANZAHL KUNDENAUFTRÄGE RÜCKSTÄNDIG"), "LATEJOBCOUNT");
        categoriesAll.put(bundle.getString("FIXE BESTELLKOSTEN GESAMT"), "FIX_ORDER_COSTS");
        categoriesAll.put(bundle.getString("VARIABLE BESTELLKOSTEN GESAMT"), "VARIABLE_ORDER_COSTS");

        categoriesAll.put(bundle.getString("ANZAHL GEWERBLICHER MITARBEITER"), "FULLTIMEEMPLOYEECOUNT");
        categoriesAll.put(bundle.getString("ANZAHL LEIHARBEITER"), "TEMPORARYEMPLOYEECOUNT");
        categoriesAll.put(bundle.getString("ANZAHL MITARBEITER GESAMT"), "OVERALL_COUNT");
        categoriesAll.put(bundle.getString("ANZAHL MITARBEITER IM LAGER"), "COUNT_LA");
        categoriesAll.put(bundle.getString("ANZAHL MITARBEITER IM VERSAND"), "COUNT_VE");
        categoriesAll.put(bundle.getString("ANZAHL MITARBEITER IN DER ENTLADUNG"), "COUNT_EN");
        categoriesAll.put(bundle.getString("ANZAHL MITARBEITER IN DER WARENAUSGANGSKONTROLLE"), "COUNT_WK");
        categoriesAll.put(bundle.getString("ANZAHL MITARBEITER IN DER WARENVEREINNAHMUNG"), "COUNT_WV");
        //categoriesEmployee.put("Anzahl Überstunden gesamt", "ph");
        categoriesAll.put(bundle.getString("ANZAHL ÜBERSTUNDEN IM LAGER"), "OVT_LA");
        categoriesAll.put(bundle.getString("ANZAHL ÜBERSTUNDEN IM VERSAND"), "OVT_VE");
        categoriesAll.put(bundle.getString("ANZAHL ÜBERSTUNDEN IN DER ENTLADUNG"), "OVT_EN");
        categoriesAll.put(bundle.getString("ANZAHL ÜBERSTUNDEN IN DER WARENAUSGANGSKONTROLLE"), "OVT_WK");
        categoriesAll.put(bundle.getString("ANZAHL ÜBERSTUNDEN IN DER WARENVEREINNAHMUNG"), "OVT_WV");
        categoriesAll.put(bundle.getString("INVESTITIONEN IN DAS BETRIEBSKLIMA"), "WORK_CLIMATE_INVEST");
        categoriesAll.put(bundle.getString("KOSTEN FÜR QUALIFIZIERUNGSMASSNAHMEN"), "COSTS_QUALIFICATION_MEASURE");
        categoriesAll.put(bundle.getString("LOHNKOSTEN MITARBEITER"), "COSTS_EMP");
        categoriesAll.put(bundle.getString("ÜBERSTUNDENKOSTEN MITARBEITER"), "COSTS_OVERTIME");

        categoriesAll.put(bundle.getString("ANZAHL FÖRDERMITTEL GESAMT"), "CONVEYOR_COUNT");
        categoriesAll.put(bundle.getString("ANZAHL PALETTEN GESAMT"), "PROCESS_PALLET_COUNT_OVERALL");
        categoriesAll.put(bundle.getString("BESTANDSWERT FÖRDERMITTEL"), "CURRENTVALUES");
        // Redundanz
        //categoriesAll.put(bundle.getString("INVESTITION IN DAS INFORMATIONSSYSTEM LAGER"), "ITCOSTS");
        categoriesAll.put(bundle.getString("INVESTITION IN DAS LAGERVERWALTUNGSSYSTEM"), "OVERALL_MODUL_COSTS");
        categoriesAll.put(bundle.getString("KOSTEN FÜR KAUF VON FÖRDERMITTELN"), "COSTS_NEW");
        categoriesAll.put(bundle.getString("KOSTEN FÜR REPARATUR VON FÖRDERMITTELN"), "COSTS_REPAIR");
        categoriesAll.put(bundle.getString("KOSTEN FÜR WARTUNG VON FÖRDERMITTELN"), "COSTS_MAINTENANCE");
        categoriesAll.put(bundle.getString("KOSTEN FÜR ÜBERHOLUNG VON FÖRDERMITTELN"), "COSTS_OVERHAUL");
        // Äquivalent zu "KOSTEN FÜR WARTUNG VON FÖRDERMITTELN"
        //categoriesAll.put(bundle.getString("WARTUNGSKOSTEN DER FÖRDERMITTEL"), "COSTS_MAINTENANCE");

        Map<String, String> categoriesNumbers = new HashMap<String, String>();
        categoriesNumbers.put(bundle.getString("ERTRAG"), "INCOME_ROUND");
        categoriesNumbers.put(bundle.getString("ERTRAG DURCH VERKAUFTE PALETTEN"), "SALES_INCOME");
        categoriesNumbers.put(bundle.getString("KONTOSTAND"), "ACCOUNTBALANCE");
        categoriesNumbers.put(bundle.getString("KOSTEN"), "COSTS_ROUND");
        categoriesNumbers.put(bundle.getString("KOSTEN FÜR DIE LAGERHALTUNG"), "STORAGE_COST");
        categoriesNumbers.put(bundle.getString("KUNDENZUFRIEDENHEIT"), "SATISFACTION");

        Map<String, String> categoriesOrga = new HashMap<String, String>();
        categoriesOrga.put(bundle.getString("INVESTITIONSKOSTEN INFORMATIONSTECHNIK"), "ITCOSTS");
        categoriesOrga.put(bundle.getString("INVESTITIONSKOSTEN LADEHILFSMITTEL"), "LEQ");

        Map<String, String> categoriesOrder = new HashMap<String, String>();
        categoriesOrder.put(bundle.getString("AKTUELLER BESTANDSWERT"), "STOCK_VALUE");
        categoriesOrder.put(bundle.getString("ANZAHL PALETTEN GELIEFERT"), "OVERALL_FINISHED_JOBS_PALLETS");
        categoriesOrder.put(bundle.getString("ANZAHL PALETTEN GELIEFERT PÜNKTLICH"), "ACCURATE_DELIVERED_PALLETS");
        categoriesOrder.put(bundle.getString("ANZAHL PALETTEN GELIEFERT UNPÜNKTLICH"), "LATE_FINISHED_JOBS_PALLET_COUNT");
        //categoriesOrder.put("Anzahl Kundenaufträge insgesamt", "ph");
        //categoriesOrder.put("Anzahl Kundenaufträge offen", "ph");
        categoriesOrder.put(bundle.getString("ANZAHL KUNDENAUFTRÄGE RÜCKSTÄNDIG"), "LATEJOBCOUNT");
        categoriesOrder.put(bundle.getString("FIXE BESTELLKOSTEN GESAMT"), "FIX_ORDER_COSTS");
        categoriesOrder.put(bundle.getString("VARIABLE BESTELLKOSTEN GESAMT"), "VARIABLE_ORDER_COSTS");

        Map<String, String> categoriesEmployee = new HashMap<String, String>();
        categoriesEmployee.put(bundle.getString("ANZAHL GEWERBLICHER MITARBEITER"), "FULLTIMEEMPLOYEECOUNT");
        categoriesEmployee.put(bundle.getString("ANZAHL LEIHARBEITER"), "TEMPORARYEMPLOYEECOUNT");
        categoriesEmployee.put(bundle.getString("ANZAHL MITARBEITER GESAMT"), "OVERALL_COUNT");
        categoriesEmployee.put(bundle.getString("ANZAHL MITARBEITER IM LAGER"), "COUNT_LA");
        categoriesEmployee.put(bundle.getString("ANZAHL MITARBEITER IM VERSAND"), "COUNT_VE");
        categoriesEmployee.put(bundle.getString("ANZAHL MITARBEITER IN DER ENTLADUNG"), "COUNT_EN");
        categoriesEmployee.put(bundle.getString("ANZAHL MITARBEITER IN DER WARENAUSGANGSKONTROLLE"), "COUNT_WK");
        categoriesEmployee.put(bundle.getString("ANZAHL MITARBEITER IN DER WARENVEREINNAHMUNG"), "COUNT_WV");
        //categoriesEmployee.put("Anzahl Überstunden gesamt", "ph");
        categoriesEmployee.put(bundle.getString("ANZAHL ÜBERSTUNDEN IM LAGER"), "OVT_LA");
        categoriesEmployee.put(bundle.getString("ANZAHL ÜBERSTUNDEN IM VERSAND"), "OVT_VE");
        categoriesEmployee.put(bundle.getString("ANZAHL ÜBERSTUNDEN IN DER ENTLADUNG"), "OVT_EN");
        categoriesEmployee.put(bundle.getString("ANZAHL ÜBERSTUNDEN IN DER WARENAUSGANGSKONTROLLE"), "OVT_WK");
        categoriesEmployee.put(bundle.getString("ANZAHL ÜBERSTUNDEN IN DER WARENVEREINNAHMUNG"), "OVT_WV");
        categoriesEmployee.put(bundle.getString("INVESTITIONEN IN DAS BETRIEBSKLIMA"), "WORK_CLIMATE_INVEST");
        categoriesEmployee.put(bundle.getString("KOSTEN FÜR QUALIFIZIERUNGSMASSNAHMEN"), "COSTS_QUALIFICATION_MEASURE");
        categoriesEmployee.put(bundle.getString("LOHNKOSTEN MITARBEITER"), "COSTS_EMP");
        categoriesEmployee.put(bundle.getString("ÜBERSTUNDENKOSTEN MITARBEITER"), "COSTS_OVERTIME");

        Map<String, String> categoriesTech = new HashMap<String, String>();
        categoriesTech.put(bundle.getString("ANZAHL FÖRDERMITTEL GESAMT"), "CONVEYOR_COUNT");
        categoriesTech.put(bundle.getString("ANZAHL PALETTEN GESAMT"), "PROCESS_PALLET_COUNT_OVERALL");
        categoriesTech.put(bundle.getString("BESTANDSWERT FÖRDERMITTEL"), "CURRENTVALUES");
        categoriesTech.put(bundle.getString("INVESTITION IN DAS INFORMATIONSSYSTEM LAGER"), "ITCOSTS");
        categoriesTech.put(bundle.getString("INVESTITION IN DAS LAGERVERWALTUNGSSYSTEM"), "OVERALL_MODUL_COSTS");
        categoriesTech.put(bundle.getString("KOSTEN FÜR KAUF VON FÖRDERMITTELN"), "COSTS_NEW");
        categoriesTech.put(bundle.getString("KOSTEN FÜR REPARATUR VON FÖRDERMITTELN"), "COSTS_REPAIR");
        categoriesTech.put(bundle.getString("KOSTEN FÜR WARTUNG VON FÖRDERMITTELN"), "COSTS_MAINTENANCE");
        categoriesTech.put(bundle.getString("KOSTEN FÜR ÜBERHOLUNG VON FÖRDERMITTELN"), "COSTS_OVERHAUL");
        // Äquivalent zu "KOSTEN FÜR WARTUNG VON FÖRDERMITTELN"
        //categoriesTech.put(bundle.getString("WARTUNGSKOSTEN DER FÖRDERMITTEL"), "COSTS_MAINTENANCE");

        chartsData.put("all", categoriesAll);
        chartsData.put("numbers", categoriesNumbers);
        chartsData.put("orgaDep", categoriesOrga);
        chartsData.put("orderDep", categoriesOrder);
        chartsData.put("empDep", categoriesEmployee);
        chartsData.put("techDep", categoriesTech);

        chart = "INCOME_ROUND";

    }
    
}
