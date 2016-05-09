package weatherstation.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import weatherstation.WeatherStation;

/**
 * Program info: 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 
 */
public class StationDeserializer {

    private static StationData sd = new StationData();
    
    private static void deconstructObject(){
        HomePostCodeStorage.homePostcode = sd.homePostCode;
        HomePostCodeStorage.homeStationName = sd.homeStationName;
        HomePostCodeStorage.homeWMO = sd.homeWMO;
        
        for(String item : sd.blackList){
            StationBlacklist.addToBlacklist(Integer.parseInt(item));
        }
        
        for(String item : sd.stationName){
            CollectInput.stationName.add(item);
        }
        
        for(String item : sd.validWMO){
            CollectInput.validWMO.add(item);
        }
        
        for(ArrayList<String> item : sd.stationDataRows){
            WeatherStation.stationDataRows.add(item);
        }
        
    }
    
    public static void retrieveData(){
        try{
            FileInputStream fileIn = new FileInputStream("stationData.sav");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            sd = (StationData) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i){
            System.out.println(i);
        } catch (ClassNotFoundException c){
            System.out.println(c);
        }
        
        deconstructObject();
    }
    public static void main(String[] Args){
        StationData sd = null;
        try{
            FileInputStream fileIn = new FileInputStream("stationData.sav");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            sd = (StationData) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i){
            System.out.println(i);
        } catch (ClassNotFoundException c){
            System.out.println(c);
        }
        
        System.out.println("Deserialzied station data");
        System.out.println("Home PostCode: " + sd.homePostCode);
        
        System.out.println("Home station name: " + sd.homeStationName);
        
        
        System.out.println("Home WMO: " + sd.homeWMO);
        
        for(String item : sd.blackList){
            System.out.println("blacklist: " + item);
        }
        
        for(String item : sd.validWMO){
            System.out.println("validWMO: " + item);
        }
        
        for(String item : sd.stationName){
            System.out.println("stationName: " + item);
        }
    }
    
    
}
