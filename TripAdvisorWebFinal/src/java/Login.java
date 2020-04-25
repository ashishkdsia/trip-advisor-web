/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ashish
 */

@ManagedBean
@SessionScoped
public class Login implements Serializable
{
    private String userId;
    private String pswd;
    private User useracc;
    private Admin admin;
    
    public String tryLogin()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ("internalError");
        }
        DataStorage data = new SQL_Database();
        String ltype = data.login(userId, pswd);
        if(ltype.equals("user"))
        {
            useracc = new User(userId,pswd);
            useracc.setData(data);
            return "user";
        }
        else if(ltype.equals("admin"))
        {
            admin = new Admin();
            admin.setData(data);
            return "admin";
        }
        else
        {
            return ltype;
        }
    }
    
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public User getUseracc() {
        return useracc;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
    
    
    
}
