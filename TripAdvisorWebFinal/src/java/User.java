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
public class User 
{
    private String userId;
    private String pswd;
    private DataStorage data;
    private String sinput;
    private String vinput;
    private String name;
    private String city;
    private String state;
    private String description;
    private String tag;
    private Float score;
    private String comment;

    public User(String userId, String pswd) 
    {
        this.userId = userId;
        this.pswd = pswd;
    }
    
    public String search()
    {
        return "search";
    }
    
    public String view(String att)
    {
        vinput = att;
        return "view";
    }
    
    public String catt ()
    {
        data.newAttraction(name, city, state, description, tag, userId);
        return "createAttraction";
    }
    
    public String signOut()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }
    
    public void post()
    {
        data.postReview(vinput, userId, score, comment);
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

    public String getVinput() {
        return vinput;
    }

    public void setVinput(String vinput) {
        this.vinput = vinput;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    

}
