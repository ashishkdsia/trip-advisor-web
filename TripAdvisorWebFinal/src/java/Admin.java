
import java.util.ArrayList;
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
public class Admin 
{
    
    public Admin() 
    {
        
    }
    private DataStorage data;
    private String attraction;
    
    
    public ArrayList<String> recievedReqs()
    {
        return data.adminRReqs();
    }
    
    public void acceptReqs(String att)
    {
        data.adminAReqs(att);
    }
    
    public String view(String att)
    {
        attraction = att;
        return "aview";
    }
    
    
    public String signOut()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }

    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }

    public String getAttraction() {
        return attraction;
    }

    public void setAttraction(String attraction) {
        this.attraction = attraction;
    }
    
    
    
}
