package weatherstation.utilities;

/**
 * Program info: This class stores and modifies the home and current station info
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class HomePostCodeStorage {

    static String homePostcode = "none";
    static String homeWMO;
    static String homeStationName;
    
    static String currentPostcode = "none";
    static String currentWMO;
    static String currentStationName;
    
    public static String getHomePostcode(){
        return homePostcode;
    }
    public static void setHomePostcode(String postcode){
        homePostcode = postcode;
    }
    public static String getHomeWMO(){
        return homeWMO;
    }
    public static void setHomeWMO(String WMO){
        homeWMO = WMO;
    }
    public static String getHomeStationName(){
        return homeStationName;
    }
    public static void setHomeStationName(String stationName){
        homeStationName = stationName;
    }
    
    public static String getCurrentPostcode(){
        return currentPostcode;
    }
    public static void setCurrentPostcode(String postcode){
        currentPostcode = postcode;
    }
    public static String getCurrentWMO(){
        return currentWMO;
    }
    public static void setCurrentWMO(String WMO){
        currentWMO = WMO;
    }
    public static String getCurrentStationName(){
        return currentStationName;
    }
    public static void setCurrentStationName(String stationName){
        currentStationName = stationName;
    }
}
