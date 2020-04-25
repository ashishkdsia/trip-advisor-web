
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
    @Override
    public void newAttraction(String Attraction_Name,String City,String State, String Description, String Tag, String user_id)
    {
        float Avg_Score=0;
        String Status="inactive";
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from attraction "
                    + "where Attraction_Name = '" + Attraction_Name +"'");

            if (resultSet.next()) 
            {
                FacesContext.getCurrentInstance().addMessage("error", new FacesMessage(" Attraction already exists! Please enter another Attrction"));
            } 
            else 
            {
                int r = statement.executeUpdate("insert into attraction values"
                        + "('" + user_id + "', '" + Attraction_Name + "', '"
                        + City + "', '" + State + "', '" + Description + "', '"+Tag+"', '"+
                                Avg_Score+"', '"+Status+"')");
                FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Attraction Created,Please wait for the approval"));
            }
            connection.commit();
            connection.setAutoCommit(true);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public ArrayList<String> viewFavourite(String userid)
    {
        ArrayList<String> favList = new ArrayList<>();
        try {

            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from favourite "
                    + "where User_ID = '"
                    + userid + "'");

            while (resultSet.next()) 
            {
                favList.add(resultSet.getString(2));
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
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        if(favList.isEmpty())
            {
                FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("No favourite added!"));
            }
        return favList;
    }
    @Override
    public void myFavoriteAttraction(String Userid, String attraction)
    {
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("Select * from favourite" + " where User_ID = '" + Userid + "'and Attraction_Name = '" + attraction + "'");
            if (resultSet.next()) 
            {
                FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("This attraction is already your favourite!"));
            } 
            else 
            {
                int r = statement.executeUpdate("insert into favourite values ('"+Userid+"', '"+attraction+"')");
                FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Attraction added as your favourite"));
                connection.commit();
                connection.setAutoCommit(true);
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
    @Override
    public ArrayList<String> youMayLike(String user_id) 
    {
        String pStatus = "active";
        String tag1 = "";
        String tag2 = "";
        ArrayList<Top3> top3 = new ArrayList<>();
        ArrayList<String> liked = new ArrayList<>();
        
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);

            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from user "
                    + "where ID = '" + user_id + "'");
            if (resultSet.next()) {
                tag1 = resultSet.getString(4);
                tag2 = resultSet.getString(5);
            }

            resultSet = statement.executeQuery("Select distinct Attraction_Name, Avg_Score from attraction "
                    + "where status = '"
                    + pStatus + "' and ( Tag = '" + tag1 + "' or Tag ='" + tag2 + "')");

            while (resultSet.next()) 
            {
                top3.add(new Top3(resultSet.getString(1), resultSet.getInt(2)));
            }

            Collections.sort(top3);
            for (int i = 0; i < top3.size(); i++) 
            {
                if(i == 3)
                {
                    break;
                }
                liked.add(top3.get(i).getAttraction_Name());
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
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        return liked;
    }
    @Override
    public String reviewCheck(String attraction, String user_id)
    {
        String a ="";
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from review" + " where User_ID = '" + user_id + "'and Attraction_Name = '" + attraction + "'");
            if (resultSet.next()) 
            {
                FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Review already Posted!"));
            } 
            else 
            {
                a = "review";
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
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return a;
    }
    
    
    @Override
    public void postReview(String attraction, String user_id, float score, String comment) {

        int rid = 0;
        String dt = DateAndTime.DateTime();
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("Select Review_ID from review order by Review_ID desc limit 1");
            if (resultSet.next()) 
            {
                rid = resultSet.getInt(1);
                rid++;
            }
            int r = statement.executeUpdate("insert into review values"
                        + "('" + rid + "', '" + attraction + "', '"
                        + user_id + "', '" + score + "', '" + comment + "', '" + dt + "')");
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("Review Posted!"));
            connection.commit();
            connection.setAutoCommit(true);
            AverageScore(attraction);
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

    @Override
    public void AverageScore(String attraction) {

        Connection c = null;
        Statement s = null;
        ResultSet r = null;

        float avg_score = 0;
        float total = 0;
        ArrayList<Float> Scorelist = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(DATABASE_URL,
                    db_id, db_psw);

            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from review "
                    + "where Attraction_Name = '" + attraction + "'");
            while (resultSet.next()) 
            {
                Scorelist.add(resultSet.getFloat(4));
            }
            if (!Scorelist.isEmpty()) {
                for (Float Scorelist1 : Scorelist) 
                {
                    total = total + Scorelist1;
                }
                avg_score = total / Scorelist.size();
                statement.executeUpdate("Update attraction set Avg_Score='" + avg_score + "' where Attraction_name = '" + attraction + "'");
            }

        } 
        catch (Exception e) 
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
    }
}
