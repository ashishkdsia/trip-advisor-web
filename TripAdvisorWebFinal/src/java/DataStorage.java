
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
public interface DataStorage 
{
    String registerAccount(String id, String name, String password, String tag1,String tag2);
    String login(String userId, String password);
    ArrayList<String> search(String input);
    ArrayList<String> view(String attraction);
    ArrayList<String> adminRReqs();
    void adminAReqs(String att);
    void newAttraction(String Attraction_Name,String City,String State, String Description, String Tag, String user_id);
    ArrayList<String> viewFavourite(String userid);
    void myFavoriteAttraction(String Userid, String attraction);
    ArrayList<String> youMayLike(String user_id);
    String reviewCheck(String attraction, String user_id);
    void postReview(String attraction, String user_id, float score, String comment);
    void AverageScore(String attraction);
    void adminRReqs(String att);
    ArrayList<String> rRreview(String attraction);
}
