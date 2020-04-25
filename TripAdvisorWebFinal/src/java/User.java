
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ashish
 */
public class User 
{
    private String userId;
    private String pswd;
    private DataStorage data;
    private String sinput;
    private String vinput;
    private ArrayList<String> search= new ArrayList<>();

    public User(String userId, String pswd) 
    {
        this.userId = userId;
        this.pswd = pswd;
    }
    
    public String search()
    {
        search = data.search(sinput);
        return "search";
    }
    
    public String view(String att)
    {
        vinput = att;
        return "view";
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

    public DataStorage getData() {
        return data;
    }

    public void setData(DataStorage data) {
        this.data = data;
    }

    public String getSinput() {
        return sinput;
    }

    public void setSinput(String sinput) {
        this.sinput = sinput;
    }

    public ArrayList<String> getSearch() {
        return search;
    }

    public void setSearch(ArrayList<String> search) {
        this.search = search;
    }

    public String getVinput() {
        return vinput;
    }

    public void setVinput(String vinput) {
        this.vinput = vinput;
    }
    

}
