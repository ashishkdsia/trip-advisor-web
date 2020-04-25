
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

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
    
    @Override
    public ArrayList<String> search(String input)
    {
        ArrayList<Top3> searchedList = new ArrayList<>();
        ArrayList<String> searchList2 = new ArrayList<>();
        try 
        {

            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from attraction "
                    + "where Tag = '"
                    + input + "' or City = '" + input +   "'");

            while (resultSet.next()) 
            {
                searchedList.add(new Top3 (resultSet.getString(2),resultSet.getInt(7)));
            }
            Collections.sort(searchedList);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                connection.close();
                statement.close();
                resultSet.close();
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        for(Top3 a : searchedList)
        {
            searchList2.add(a.getAttraction_Name());
        }
        return searchList2;
    }
    
    @Override
    public ArrayList<String> view(String attraction)
    {
        ArrayList<String> viewList = new ArrayList<>();
        try
        {
          connection = DriverManager.getConnection(DATABASE_URL, 
                  db_id, db_psw);
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from attraction "
                    + "where Attraction_Name = '" + attraction + "'");
            
            if(resultSet.next())
            {
                viewList.add("By User- " + resultSet.getString(1));
                viewList.add("Attraction Name - " + resultSet.getString(2));
                viewList.add("City - " + resultSet.getString(3));
                viewList.add("State - " + resultSet.getString(4));
                viewList.add("Description - " + resultSet.getString(5));
                viewList.add("Tag - " + resultSet.getString(6));
                viewList.add("Average Score - " + resultSet.getString(7)); 
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
        return viewList;
    }
    @Override
    public ArrayList<String> adminRReqs()
    {
        String pStatus = "inactive";
        ArrayList<String>ReceivedReqList;
        ReceivedReqList = new ArrayList<String>();
        try
        {
            connection = DriverManager.getConnection(DATABASE_URL, 
                  db_id, db_psw);
            
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery("Select * from attraction "
                    + "where status = '"
                    + pStatus +  "'");
            
            while(resultSet.next())
            {
                ReceivedReqList.add(resultSet.getString(2));
            }   
        }
        catch (SQLException e)
        {
            e.printStackTrace();
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
        if(ReceivedReqList.isEmpty())
        {
            ReceivedReqList.add("No Request Received");
        }
        return ReceivedReqList;
    }
    
    @Override
    public void adminAReqs(String att)
    {
        String aApproval = "active";
        try {
           
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            connection.setAutoCommit(false);
            
            statement = connection.createStatement();

            String sql = "UPDATE attraction "
                    + "SET status = '" + aApproval + "' WHERE Attraction_Name = '" + att + "'";
            statement.executeUpdate(sql);
            connection.commit();
            connection.setAutoCommit(true);
        }
        
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        
        finally 
        {
            
            try 
            {
                resultSet.close();
                statement.close();
                connection.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}
