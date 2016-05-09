package weatherstation.utilities;

import java.util.ArrayList;

/**
 * Class info: This class creates and maintains a list of stations that have 
 * Null observations and are not suited to use in this program. 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class StationBlacklist {

    static ArrayList<Integer> list;
    
    public StationBlacklist() {
        list = new ArrayList<>();
    }
    
    public static ArrayList<Integer> getBlacklist(){
        return list;
    }
    public static void addToBlacklist(int WMO){
        list.add(WMO);
    }
    public static boolean isOnBlacklist(int WMO){
        boolean isOnList = false;
        for(int item : list){
            if(item == WMO){
                isOnList = true;
            }
        }
        return isOnList;
    }
}
