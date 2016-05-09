package weatherstation.utilities;

import java.util.ArrayList;

/**
 * Program info: This class implements the Serializable interface for stationData
 * objects
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class StationData implements java.io.Serializable {
    public String homePostCode;
    public String homeWMO;
    public String homeStationName;
    public ArrayList<String> blackList;
    public ArrayList<String> validWMO;
    public ArrayList<String> stationName;
    public ArrayList<ArrayList<String>> stationDataRows;

    public StationData(){
        blackList = new ArrayList<>();
        validWMO  = new ArrayList<>();
        stationName = new ArrayList<>();
        stationDataRows = new ArrayList<>();
    }
}
