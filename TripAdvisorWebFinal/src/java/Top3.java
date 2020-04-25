/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Comparator;

/**
 *
 * @author kvs
 */
public class Top3 implements Comparable
{
    private String Attraction_Name;
    private int Avg_Score;

    public Top3(String Attraction_Name, int Avg_Score) {
        this.Attraction_Name = Attraction_Name;
        this.Avg_Score = Avg_Score;
    }
    
    @Override
    public int compareTo(Object avgscore) {
        int compareavg=((Top3)avgscore).getAvg_Score();
        
        return  (compareavg-this.Avg_Score);
    }

    public String getAttraction_Name() {
        return Attraction_Name;
    }

    public void setAttraction_Name(String Attraction_Name) {
        this.Attraction_Name = Attraction_Name;
    }

    public int getAvg_Score() {
        return Avg_Score;
    }

    public void setAvg_Score(int Avg_Score) {
        this.Avg_Score = Avg_Score;
    }
    
    
}
