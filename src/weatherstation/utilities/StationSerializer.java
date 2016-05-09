package weatherstation.utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import weatherstation.WeatherStation;

/**
 * Program info: 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 
 */
public class StationSerializer {

    private static StationData sd = new StationData();
    
    private static void createObject(){
        sd.homePostCode = HomePostCodeStorage.getHomePostcode();
        sd.homeStationName = HomePostCodeStorage.getHomeStationName();
        sd.homeWMO = HomePostCodeStorage.getHomeWMO();
        
        for(int item : StationBlacklist.getBlacklist()){
            sd.blackList.add(String.valueOf(item));
        }
        
        for(String item : CollectInput.stationName){
            sd.stationName.add(item);
        }
        
        for(String item : CollectInput.validWMO){
            sd.validWMO.add(item);
        }
        
        for(ArrayList<String> list : WeatherStation.stationDataRows){
            sd.stationDataRows.add(list);
        }
        
    }
    
    public static void saveData(){
        createObject();
        
        try {
            FileOutputStream fileOut = new FileOutputStream("stationData.sav");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(sd);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in stationData.sav");
        } catch(IOException i){
            System.out.println(i);
            i.printStackTrace();
        }
        
    }
    public static void main(String[] Args){
        
        StationData sd = new StationData();
        sd.homePostCode = "4350";
        sd.blackList.add("99093");
        sd.stationName.add("TOOWOOMBA AIRPORT");
        sd.validWMO.add("95551");
        
        try {
            FileOutputStream fileOut = new FileOutputStream("stationData.sav");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(sd);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in stationData.sav");
        } catch(IOException i){
            System.out.println(i);
        }
    }
}
