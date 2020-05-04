/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Ashish
 */
@ManagedBean
@RequestScoped
public class Signup 
{
    private String userId;
    private String password;
    private String name;
    private String tag1;
    private String tag2;
    private final String UserID_PATTERN = "(?=.*[a-zA-Z])(?=.*\\d).{3,10}";
    
    public String trySignup()
    {
        String a = "";
        if (!(userId).matches(UserID_PATTERN))
        {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("User ID does not match the requirement"));
        }
        else if( userId.matches(password))
        {
            FacesContext.getCurrentInstance().addMessage("error", new FacesMessage("User ID and Password should not match"));
        }
        else 
        {
            try 
            {
                Class.forName("com.mysql.jdbc.Driver");
            } 
            catch (Exception e) 
            {
                return ("Internal Error! Please try again later.");
            }
            DataStorage data = new SQL_Database();
             a =data.registerAccount(userId, name, password, tag1, tag2);
        }

        return a;
    }
    
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }
    
    
    
}
