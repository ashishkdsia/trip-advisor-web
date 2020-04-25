
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ashish
 */
public class SQL_Database implements DataStorage
{
    final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/naidus2775?useSSL=false";
    final String db_id = "naidus2775";
    final String db_psw = "1857695";
        
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    
    @Override
    public String registerAccount(String id, String name, String password, String tag1,String tag2)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, 
                    db_id, db_psw);
          
            statement = connection.createStatement();
            
            
            resultSet = statement.executeQuery("Select * from user "
                    + "where id = '" + id + "'");
            
            if(resultSet.next())
            {
                
                return("Account creation failed");
            }
            else
            {
                
            int r = statement.executeUpdate("insert into user values"
                        + "('" + id + "', '" + name + "', '" + password + "', '"
                        + tag1+"', '"+tag2 +"')");
                return ("Account creation successful!");
                 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return("Something wrong during the creation process!");
        }
        finally
        {
             
             try
             {
                 resultSet.close();
                 statement.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
        }
        
    }
    
    @Override
    public String login(String userId, String password)
    {
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, 
                  db_id, db_psw);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from user "
                    + "where id = '" + userId + "'");
            if(resultSet.next())
            {
                if(password.equals(resultSet.getString(3)))
                {
                    if(userId.equals("admin") && password.equals("admin"))
                    {
                        return"admin";
                    }
                    else
                    {
                        return "user";
                    }
                }
                else
                {
                    return "loginFail";
                }
            }
            else
            {
                return "loginFail";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "internalError";
        }
        finally
        {
            try
            {
                connection.close();
                statement.close();
                resultSet.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
