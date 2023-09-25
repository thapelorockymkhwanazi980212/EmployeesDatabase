
package za.ac.tut.bl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.tut.entity.Employee;

/**
 *
 * @author Thapelo Mkhwanazi
 */
public class EmployeesManagerDB implements DAOInterface<Employee> 
{
    private Connection connection;

    public EmployeesManagerDB(String dbURL, String username, String password) throws SQLException 
    {
        //get connection, step number 2 from notes
        connection = DriverManager.getConnection(dbURL, username, password);
    }
    
    

    @Override
    public Boolean add(Employee t) throws SQLException 
    {
        String sql = "INSERT INTO EMPLOYEESTBL(IDNO, NAME, SURNAME, GENDER, AGE, DOB, SALARY)" + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        //the statement VALUES(?, ?, ?, ?, ?, ?, ?) contains 7 placeholders represented by ?, meaning that the ? will be replaced at the later stage
        
        //step 3, create statement
        PreparedStatement ps = connection.prepareStatement(sql);
        
        //the below code replace the placeholders
        ps.setInt(1, t.getIdNo());
        /*
            explanation of the statement ps.setInt(1, t.getIdNo()); 
        ps.setInt>>set the value represented by the ? placeholder '?' using the setInt method of the PreparedStatement object
        1 >>  species the first parameter in the prepared statement.
        t >> is the name of the object of the method parameter, it is used to access the methods of the Employee
        */
        ps.setString(2, t.getName());   
        ps.setString(3, t.getSurname());
        ps.setString(4, t.getGender().toString());
        ps.setInt(5, t.getAge());
        ps.setDate(6, (Date) t.getDob());
        ps.setDouble(7, t.getSalary());
        
        //step 4, execute the query
        ps.executeUpdate();
        
        //step 5, close the connection
        ps.close();
        
        return true;
    }
 
    @Override
    public boolean delete(Integer id) throws SQLException 
    {
       String slq = "DELETE FROM EMPLOYEESTBL" + "WHERE IDNO = ?"; 
       
       PreparedStatement ps = connection.prepareStatement(slq);
       ps.setInt(1, id);
       ps.executeUpdate();
       ps.close();
        
       return true; 
    }

    @Override
    public boolean update(Employee t) throws SQLException 
    {
        String sql = "UPDATE EMPLOYEESTBL SET " +  "SALARY = ?" + "WHERE IDNO = ?";
        
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, t.getSalary());
        ps.setInt(2, t.getIdNo());
        ps.executeUpdate();
        ps.close();
      
        return true;
    }

    @Override
    public Employee get(Integer id) throws SQLException 
    {
        String sql = "SELECT IDNO, NAME, SURNAME, GENDER, AGE, DOB, SALARY FROM EMPLOYEESTBL" + "WHERE IDNO = ?";
        
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id); // getting the employee of this idno
        
        ResultSet rs = ps.executeQuery();  //executing the sql statement which will select the idno, name, surname, gender, age, dob, salary and 
                                            //storing the execution in the Result set rs
        
        if(rs.next())
        {
            Integer idNo = rs.getInt("IDNO");  //getting the integer value from the column name called IDNO
            String name = rs.getString("NAME");
            String surname = rs.getString("SURNAME");
            Character gender = rs.getString("GENDER").charAt(0);
            Integer age = rs.getInt("AGE");
            Date dob = rs.getDate("DOB");
            Double salary = rs.getDouble("SALARY");
            
            Employee emp = new Employee(idNo, name, surname, gender, age, dob, salary);
            ps.close();
            return emp;
        }
        else
        {
            ps.close();
            return null;
        }
    }

    @Override
    public List<Employee> getAll() throws SQLException 
    {
        //create a list that will store employees
        List<Employee> emps = new ArrayList<>();
        String sql = "SELECT * FROM EMPLOYEESTBL";
        
        PreparedStatement ps = connection.prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();  //executing the query and storing it in the result set
        
        while(rs.next())
        {
            Integer idNo = rs.getInt("IDNO");  //getting the integer value from the column name called IDNO
            String name = rs.getString("NAME");
            String surname = rs.getString("SURNAME");
            Character gender = rs.getString("GENDER").charAt(0);
            Integer age = rs.getInt("AGE");
            Date dob = rs.getDate("DOB");
            Double salary = rs.getDouble("SALARY");
            
            Employee emp = new Employee(idNo, name, surname, gender, age, dob, salary);
            emps.add(emp);
        }
        
        ps.close();
        
        return emps;
    }
    
}
