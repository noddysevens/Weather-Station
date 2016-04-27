package weatherstation.utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.swing.Timer;
import weatherstation.MainPanelDriver;
import weatherstation.WeatherStation;


/**
 * Program info: This class collects the data from the BOM website
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 26-Apr-2016
 */

public class CollectInput{
    static InputStream inputStream;
    static JsonReader rdr;
    static JsonObject obj;
    static JsonObject obj1;
    public static JsonArray results;
    static URL url;
    public static ArrayList<String> validWMO = new ArrayList<>();
    public static String stationName = "";
    public static String stateCode = "IDQ60801";
    
    
    public void getInput() {
        
        
        String wmoCode = "";
        if(validWMO.size() == 1){
            wmoCode = validWMO.get(0);
        } else {
            System.out.println("Error: incorrect number of WMOs");
           wmoCode = "95551";
        }
        try
        {
            url = new URL("http://www.bom.gov.au/fwo/" + stateCode + "/" + stateCode + "." + wmoCode + ".json");
        }catch(MalformedURLException ex){};
        try {
            inputStream = url.openStream();
        } catch(IOException ex){}
        rdr = Json.createReader(inputStream);
        obj = rdr.readObject();
        obj1 = obj.getJsonObject("observations");
        results = obj1.getJsonArray("data");
        
        
    }
    
    public static void getPostcodeInfo(String postcode) {
        try
        {
            url = new URL("http://v0.postcodeapi.com.au/suburbs/" + postcode + ".json");
        }catch(MalformedURLException ex){};
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            inputStream = httpcon.getInputStream();
            //inputStream = url.openStream();
        } catch(IOException ex){
            System.out.println(ex);
        }
        rdr = Json.createReader(inputStream);
        results = rdr.readArray();
        
        ArrayList<JsonString> jstring = new ArrayList<>();
        ArrayList<String> nameString = new ArrayList<>();
        
        boolean valueFound = false;
        boolean wmoFound = false;
        for(int i = 0; i < results.size(); i++){
            obj = results.getJsonObject(i);
            jstring.add(obj.getJsonString("name"));
            nameString.add(jstring.get(i).getString());
        }
        for(String s : nameString){
            for(ArrayList<String> list : WeatherStation.stationDataRows){
                String value = list.get(1);
                CharSequence ch = s.toUpperCase().subSequence(0, s.length()-1);
                if(value.equalsIgnoreCase(s) || value.contains(ch)){
                    valueFound = true; //not needed
                    String WMO = list.get(10);
                    if(!WMO.equalsIgnoreCase("..")){
                        wmoFound = true; //not needed
                        System.out.println(WMO);
                        String state = list.get(7);
                        System.out.println(state);
                        String sta = checkState(postcode);
                        if(sta.equals(state)){
                            validWMO.add(WMO);
                            stationName = list.get(1);
                            System.out.println(stationName);
                        }
                    }               
                }
            }
        }
        url = null;
        inputStream = null;
        rdr = null;
        obj = null;
        results = null;
      
        //System.out.println("");
    }
    private static String checkState(String postcode){
        String state = "";
        int code = Integer.parseInt(postcode);
        
        //nsw
        if(1000 < code && code < 2599){
            state = "NSW";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.nsw];
        } else if(2620 < code && code < 2899){
            state = "NSW";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.nsw];
        } else if(2921 < code && code < 2999){
            state = "NSW";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.nsw];
        }
        
        //qld
        if(4000 < code && code < 4999){
            state = "QLD";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.qld];
        } else if(9000 < code && code < 9999){
            state = "QLD";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.qld];
        } 
        
        //sa
        if(5000 < code && code < 5999){
            state = "SA";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.sa];
        } 
        
        //tas
        if(7000 < code && code < 7999){
            state = "TAS";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.tas];
        } 
        
        //wa
        if(6000 < code && code < 6999){
            state = "WA";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.wa];
        }
        
        //act
        if(200 < code && code < 299){
            state = "ACT";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.act];
        } else if(2600 < code && code < 2619){
            state = "ACT";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.act];
        } else if(2900 < code && code < 2920){
            state = "ACT";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.act];
        }
        
        //vic
        if(3000 < code && code < 3999){
            state = "VIC";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.vic];
        } else if(8000 < code && code < 8999){
            state = "VIC";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.vic];
        }
        
        //nt
        if(800 < code && code < 999){
            state = "NT";
            stateCode = WeatherStation.stateJSONcodes[WeatherStation.nt];
        }
        
        return state;
    }
    public static void main(String[] args){
        getPostcodeInfo("3066");
    }
}
