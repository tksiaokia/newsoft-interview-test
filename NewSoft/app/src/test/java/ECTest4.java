import android.support.annotation.Nullable;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class ECTest4 {
    /*******************
     Question 4
     *******************/
    public static void main(String[] arg) {
        //run test case below {getAllReporteesTestCase}
    }

    class Employee implements Comparable<Employee>{
        String name;
        int id;
        Employee manager;
        ArrayList<Employee> reportees;

        public Employee(String name, int id, @Nullable Employee manager) {
            this.name = name;
            this.id = id;
            this.manager = manager;
        }

        public void addReportee(Employee reportee) {
            if (reportees == null) {
                reportees = new ArrayList<>();
            }
            reportees.add(reportee);
        }

        public ArrayList<Employee> getReportees() {
            return reportees;
        }

        @Override
        public int compareTo(Employee employee) {
            return id - employee.id;
        }
    }

    /**
     * You have an Employee class
     * class Employee{†?String name;†?Integer id,†?Employee manager†?}
     * Each employee has a manager and the manager of CEO is null. Find all direct and indirect reportees of a manager
     * Eg. Say Employee e1 reports to CEO,
     * e2 and e3 reports to e1,
     * e4 and e5 reports to e2,
     * e6 reports to e3.
     * Then by giving e1 as input, output should be e2, e3, e4, e5 and e6.
     */
    @Test
    public void getAllReporteesTestCase() {
        //create available employee
        Employee ceo = new Employee("ceo", 0, null);

        Employee e1 = new Employee("e1", 1, ceo);
        ceo.addReportee(e1);

        Employee e2 = new Employee("e2", 2, e1);
        e1.addReportee(e2);
        Employee e3 = new Employee("e3", 3, e1);
        e1.addReportee(e3);

        Employee e4 = new Employee("e4", 4, e2);
        e2.addReportee(e4);
        Employee e5 = new Employee("e5", 5, e2);
        e2.addReportee(e5);

        Employee e6 = new Employee("e6", 6, e3);
        e3.addReportee(e6);

        ArrayList<Employee> allReportees = new ArrayList<>();
        getAllReportees(e1, allReportees);//perform search

        //sort for pretty print
        Collections.sort(allReportees);

        //print
        for (Employee employee : allReportees) {
            System.out.println(employee.name);
        }

    }

    //recursive function call
    private void getAllReportees(Employee employee, ArrayList<Employee> allReportees) {
        ArrayList<Employee> reportees = employee.getReportees();
        if (reportees != null) {
            for (Employee reportee : reportees) {
                getAllReportees(reportee, allReportees);
            }
            allReportees.addAll(reportees);
        }
    }

}
