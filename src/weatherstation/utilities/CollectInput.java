package weatherstation.utilities;

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
import weatherstation.WeatherStation;


/**
 * Program info: This class collects the data from the BOM website
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 26-Apr-2016
 */

public class CollectInput{
    private static URL url;
    private static JsonReader rdr;
    private static JsonObject obj;
    private static InputStream inputStream;
    
    public static JsonArray results;
    public static ArrayList<String> validWMO;
    public static ArrayList<String> stationName;
    public static String stateCode = "IDQ60801";
    
    private static final String[] STATE_JSON_CODES = {"IDN60801","IDV60801","IDQ60801", 
            "IDW60801","IDS60801","IDT60801","IDN60801","IDD60801"};
    private static final int NSW = 0;
    private static final int VIC = 1;
    private static final int QLD = 2;
    private static final int WA = 3;
    private static final int SA = 4;
    private static final int TAS = 5;
    private static final int ACT = 6;
    private static final int NT = 7;
    
    public CollectInput(){
        validWMO = new ArrayList<>();
        stationName = new ArrayList<>();
    }
    
    public static void getPostcodeInfo(String postcode) {
        try {
            url = new URL("http://v0.postcodeapi.com.au/suburbs/" + postcode + ".json");
        } catch(MalformedURLException ex){
            ex.printStackTrace();
        }
        
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            inputStream = httpcon.getInputStream();

        } catch(IOException ex){
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        rdr = Json.createReader(inputStream);
        results = rdr.readArray();
        
        ArrayList<JsonString> jstring = new ArrayList<>();
        ArrayList<String> nameString = new ArrayList<>();
        
        for(int i = 0; i < results.size(); i++){
            obj = results.getJsonObject(i);
            jstring.add(obj.getJsonString("name"));
            nameString.add(jstring.get(i).getString());
        }
        for(String s : nameString){
            for(ArrayList<String> list : PrepareStationData.stationDataRows){
                String value = list.get(1);
                CharSequence ch = s.toUpperCase().subSequence(0, s.length()-1);
                if(value.equalsIgnoreCase(s) || value.contains(ch)){
                    String WMO = list.get(10);
                    if(!WMO.equalsIgnoreCase("..")){
                        String state = list.get(7);
                        String sta = checkState(postcode);
                        if(sta.equals(state)){
                            if(StationBlacklist.isOnBlacklist(Integer.parseInt(WMO))){
                                System.out.println("This station is on the blacklist");
                            } else {
                                if(isValidWMO(WMO)){
                                    validWMO.add(WMO);
                                    stationName.add(list.get(1));
                                    System.out.println(stationName.get(stationName.size() - 1));
                                }
                            }
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
    }
    public static String checkState(String postcode){
        String state = "";
        int code = Integer.parseInt(postcode);
        
        //nsw
        if(1000 <= code && code <= 2599){
            state = "NSW";
            stateCode = STATE_JSON_CODES[NSW];
        } else if(2620 <= code && code <= 2899){
            state = "NSW";
            stateCode = STATE_JSON_CODES[NSW];
        } else if(2921 <= code && code <= 2999){
            state = "NSW";
            stateCode = STATE_JSON_CODES[NSW];
        }
        
        //qld
        if(4000 <= code && code <= 4999){
            state = "QLD";
            stateCode = STATE_JSON_CODES[QLD];
        } else if(9000 <= code && code <= 9999){
            state = "QLD";
            stateCode = STATE_JSON_CODES[QLD];
        } 
        
        //sa
        if(5000 <= code && code <= 5999){
            state = "SA";
            stateCode = STATE_JSON_CODES[SA];
        } 
        
        //tas
        if(7000 <= code && code <= 7999){
            state = "TAS";
            stateCode = STATE_JSON_CODES[TAS];
        } 
        
        //wa
        if(6000 <= code && code <= 6999){
            state = "WA";
            stateCode = STATE_JSON_CODES[WA];
        }
        
        //act
        if(200 <= code && code <= 299){
            state = "ACT";
            stateCode = STATE_JSON_CODES[ACT];
        } else if(2600 <= code && code <= 2619){
            state = "ACT";
            stateCode = STATE_JSON_CODES[ACT];
        } else if(2900 <= code && code <= 2920){
            state = "ACT";
            stateCode = STATE_JSON_CODES[ACT];
        }
        
        //vic
        if(3000 <= code && code <= 3999){
            state = "VIC";
            stateCode = STATE_JSON_CODES[VIC];
        } else if(8000 <= code && code <= 8999){
            state = "VIC";
            stateCode = STATE_JSON_CODES[VIC];
        }
        
        //nt
        if(800 <= code && code <= 999){
            state = "NT";
            stateCode = STATE_JSON_CODES[NT];
        }
        
        return state;
    }
    private static boolean containsNullObservations(JsonArray observations){
        boolean containsNull = false;
        for(JsonObject result : observations.getValuesAs(JsonObject.class)){
            try {
                result.getJsonNumber("air_temp");
            } catch (Exception e){
                e.printStackTrace();
                containsNull = true;
                break;
            }

        }
        return containsNull;
    }
    
    public static void getObservations(String WMOCode){
        try {
            url = new URL("http://www.bom.gov.au/fwo/" + stateCode + "/" 
                    + stateCode + "." + WMOCode + ".json");
        }catch(MalformedURLException ex){
            System.out.println("");
            ex.printStackTrace();
        }
        
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            inputStream = httpcon.getInputStream();

            JsonReader reader = Json.createReader(inputStream);
            JsonObject object = reader.readObject();
            JsonObject object1 = object.getJsonObject("observations");
            results = object1.getJsonArray("data");
        } catch(IOException ex){
            System.out.println("");            
            validWMO.remove(WMOCode);
            stationName.remove(0);
            StationBlacklist.addToBlacklist(Integer.parseInt(WMOCode));
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
    
    private static boolean isValidWMO(String WMOCode){
        boolean validity = true;
        //check observations
        try {
            url = new URL("http://www.bom.gov.au/fwo/" + stateCode + "/" 
                    + stateCode + "." + WMOCode + ".json");
        }catch(MalformedURLException ex){
            System.out.println("");
            ex.printStackTrace();
        }
        
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            inputStream = httpcon.getInputStream();
            
            JsonReader reader = Json.createReader(inputStream);
            JsonObject object = reader.readObject();
            JsonObject object1 = object.getJsonObject("observations");
            results = object1.getJsonArray("data");
        } catch(IOException ex){          
            StationBlacklist.addToBlacklist(Integer.parseInt(WMOCode));
            System.out.println(ex);
            ex.printStackTrace();
            validity = false;
        }
        if(containsNullObservations(results)){
            StationBlacklist.addToBlacklist(Integer.parseInt(WMOCode));
            validity = false;
        } 
        return validity;
    }
    
    
}
