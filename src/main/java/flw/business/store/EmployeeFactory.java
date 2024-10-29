/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package flw.business.store;

import flw.business.util.CsvObject;
import flw.business.util.DoloresConst;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author tilu
 */
public class EmployeeFactory {

    private List<FirstNameObject> lFirstNames;
    private List<String> lLastNames;
    private final Random rand;
    private static EmployeeFactory singleton;

    private EmployeeFactory() {
        long seed = 5;

        rand = new Random(seed);
        fillFirstNames();
        fillLastNames();
    }

    public static Employee createEmployee() {


        EmployeeFactory factory = getInstance();
        FirstNameObject firstName = factory.getRandomFirstName();
        String lastName = factory.getRandomLastName();
        int age = factory.getRandomAge();

        Employee toReturn = new Employee(firstName.name, lastName, firstName.gender, age);
        return toReturn;
    }

    public static EmployeeDynamics createEmployeeDynamics(int age) {
        EmployeeFactory factory = getInstance();

        int qual = factory.getRandomQualifications();
        int salary = factory.getSalary(qual);
        EmployeeDynamics toReturn = new EmployeeDynamics(qual, salary);
        return toReturn;

    }

    private static EmployeeFactory getInstance() {
        if (singleton == null) {
            singleton = new EmployeeFactory();
        }
        return singleton;
    }

    private FirstNameObject getRandomFirstName() {
        return lFirstNames.get(rand.nextInt(lFirstNames.size()));
    }

    private String getRandomLastName() {
        return lLastNames.get(rand.nextInt(lLastNames.size()));
    }

    private int getRandomAge() {
        return (16 + rand.nextInt(50));
    }

    private int getRandomQualifications() {
        int count = 0;
        // TODO !! allow dynamical qualifications
        while (count == 0 || count == 2 || count == 6) {
            count = rand.nextInt(8);
        }


        return count;
    }

    /*
     * 1 = FP
     * 2 = Security <- not possible
     * 3 = FP + Security
     * 4 = QM
     * 5 = FP + QM
     * 6 = Security + QM <- not possible
     * 7 = FP + QM + Security
     */
    public static int getSalary(int qualification) {
        int salary = 0;

        switch (qualification) {
            case 0:
                salary = DoloresConst.SALARY_NORMAL;
                break;
            case 1:
                salary = DoloresConst.SALARY_WITH_FORKLIFT_PERMIT;
                break;
            case 3:
                salary = DoloresConst.SALARY_WITH_FP_AND_SECURITY_TRAINING;
                break;
            case 4:
                salary = DoloresConst.SALARY_WITH_QMSEMINAR;
                break;
            case 5:
                salary = DoloresConst.SALARY_WITH_QMSEMINAR_AND_FP;
                break;
            case 7:
                salary = DoloresConst.SALARY_WITH_QSEMINAR_FP_AND_SECURITY_TRAINING;
                break;
        }
        return salary;


    }

    /*
     public static final int SALARY_NORMAL = 85;
     public static final int SALARY_WITH_FORKLIFT_PERMIT = 125;
     public static final int SALARY_WITH_FP_AND_SECURITY_TRAINING = 145;
     public static final int SALARY_WITH_QMSEMINAR = 110;
     public static final int SALARY_WITH_QMSEMINAR_AND_FP = 150;
     public static final int SALARY_WITH_QSEMINAR_FP_AND_SECURITY_TRAINING = 170;
     */
    private void fillLastNames() {
        try {
            // TOP100 lastnames taken from
            // http://nachname.gofeminin.de/w/nachnamen/208-nordrhein-westfalen/haeufigste-nachnamen.html
            CsvObject co = new CsvObject(loadResource("employee_lastnames.csv"));
            lLastNames = new ArrayList<String>();
            List<String[]> lData = co.getTableData();
            for (String[] arrStr : lData) {
                if (arrStr.length == 1) {
                    lLastNames.add(arrStr[0]);
                }
            }
        } catch (Exception ex) {
        }
    }

    private void fillFirstNames() {
        try {
            // TOP100 first names taken from
            // http://www.beliebte-vornamen.de/jahrgang/j2009/top500-2009
            CsvObject co = new CsvObject(loadResource("employee_firstnames.csv"));
            lFirstNames = new ArrayList<FirstNameObject>();
            List<String[]> lData = co.getTableData();
            for (String[] arrStr : lData) {
                if (arrStr.length == 2) {
                    lFirstNames.add(new FirstNameObject(arrStr[0], Boolean.valueOf(arrStr[1])));
                }
            }
        } catch (Exception ex) {
        }
    }

    private class FirstNameObject {

        private String name;
        /**
         * <code>true</code> stands for "male"
         */
        private boolean gender;

        public FirstNameObject(String name, boolean gender) {
            this.name = name;
            this.gender = gender;
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
            System.out.println("Error loading file");
            throw ex;
        }
        input.close();
        return toReturn;
    }
}
