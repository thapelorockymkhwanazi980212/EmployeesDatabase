
package employeesapp;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import za.ac.tut.bl.EmployeesManagerDB;
import za.ac.tut.entity.Employee;

/**
 *
 * @author Thapelo Mkhwanazi
 */
public class EmployeesApp 
{

    public static void main(String[] args) throws SQLException, ParseException 
    {
      //declare variables I will work with
       int choice, empId;
       Employee emp;
       EmployeesManagerDB db = new EmployeesManagerDB("jdbc:derby://localhost:1527/EmployeesDB;create=true", "app", "123");
       
       choice = displayChoices();
       while(choice != 6)
       {
           switch(choice)
           {
               case 1:
                   
                   /*first get an employee, by creating a method that will prompt for employee details.
                     then getEmployee()  returns the employee added by the user and that employee is then added to the database using that add() of the 
                     EmployeesManagerDB class
                   */
                   
                   emp = getEmployee();
                   db.add(emp);
                   JOptionPane.showMessageDialog(null, "Employee added successfully");
                   break;
                   
               case 2:
                   /*
                     here we created a method called getEmployeeId() that prompt the user for the employee ID so that the employee can be deleted
                   */
                   empId = getEmployeeId();
                   db.delete(empId);
                   
                   break;
                   
               case 3:
                   /*
                        here we created a method callled get employee to update so that their salary can be updated.
                        This method will return the employee to update
                   */
                   emp = getEmployeeToUpdate();
                   db.update(emp);
                   break;
                   
               case 4:
                   /*
                    here we searching for an employeee, first get their id using the getEmployeeId() whic returns the employee Id
                    then call the get() of the dbmanager that has the parameter of int id then pass that employeeId as argunent...the get() retuns the
                    employee. Then call the display method that takes Employee as parameter then pass the return employee as argument
                   */
                   empId = getEmployeeId();
                   emp = db.get(empId);
                   
                   display(emp);
                   
                   break;
                   
               case 5:
                   /*
                    here I want to display all employees and we have saved them in the List, calling the getAll of the DB that returns a list of employees
                    then saving them in a List called emps, the call the displayAll method that takes a list as parameter then pass the emps as argument
                   */
                   List<Employee> emps = db.getAll();
                    
                   displayAll(emps);
                   break;
                   
               default:
                   System.out.println(choice + " is invalid");
                  
                
           }
           
           choice = displayChoices();
       }
    }
    
    private static int displayChoices()
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Please select one of the options:"+"\n" +
                "1. Add employee" + "\n" +
                "2. Delete employee" +  "\n" +
                "3. Update employee salary"+  "\n" +
                "4. Get employee" +  "\n" +
                "5. Get all employees" +  "\n" +
                "6. Exit" +  "\n\n" + 
                "Your choice: ");
        
        int choice = sc.nextInt();
        
        return choice;
    }
    
    private static Employee getEmployee() throws ParseException
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter employee ID no");
        int idNo = sc.nextInt();
        
        System.out.println("Enter employees name");
        String name = sc.next();
        
        System.out.println("Enter employees surname");
        String surname = sc.next();
        
        System.out.println("Enter gender");
        char gender  = sc.next().charAt(0);
        
        System.out.println("Enter employees age");
        int age = sc.nextInt();


        //the error is here, when I run the String to date convertion is not working
        
        System.out.println("Enter employees date of birth(yyyy-mm-dd):");
        String dob = sc.next();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = dateformat.parse(dob);
                
        System.out.println("Enter employees salary");
        double salary = sc.nextDouble();
        
        Employee emp = new Employee(idNo, name, surname, gender, age, date, salary);
        
        return emp;
    }
    
    private static int getEmployeeId()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter employee Id: ");
        int id = sc.nextInt();
        
        return id;
    }
    
    private static Employee getEmployeeToUpdate()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the employee ID");
        int id = sc.nextInt();
        
        System.out.println("Enter the new employee salary");
        double sal = sc.nextDouble();
        
        Employee emp = new Employee();   //default constructor so that you can update the employee salary
        emp.setIdNo(id);
        emp.setSalary(sal);
        
        return emp;
        
    }
    
    private static void display(Employee em)
    {
        System.out.println(em + "\n");
    }
    
    private static void displayAll(List<Employee> emps)
    {
        System.out.println(emps + "\n");
    }
}
