package weatherstation;

import java.io.IOException;
import javax.json.JsonObject;
import weatherstation.panels.MainDashboardPanel;
import weatherstation.utilities.CollectInput;

/**
 * Class info: This class drives the MainDashboardPanel
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class MainPanelDriver{
    
    public static int sortOrder = 0;
    public static int air = 1;
    public static int apparentTemp = 2;
    public static int dewPoint = 3;
    public static int relativeHumidity = 4;
    public static int deltaT = 5;
    public static int windDirection = 6;
    public static int windSpeedKmh = 7;
    public static int windGustsKmh = 8;
    public static int windSpeedKnots = 9;
    public static int windGustsKnots = 10;
    public static int pressQnh = 11;
    public static int pressMsl = 12;
    public static int rainSince = 13;
    public static int dateTime = 14;

    public static String[][] bomData;
    
    private static CollectInput input;
    public static MainDashboardPanel mainPanel;

    public MainPanelDriver() throws IOException {
        mainPanel = new MainDashboardPanel(this);
        input = new CollectInput();
    }
    
    public static void initializeArrays(){
        int numberOfEntries = CollectInput.results.size();
        int index = 0;
       
        bomData = new String[numberOfEntries][15]; 
        
        for(JsonObject result : CollectInput.results.getValuesAs(JsonObject.class)){
            String time = String.valueOf(result.getString("local_date_time"));
            String sub = time.substring(7, 8);
            /*Only observations that are recorded at a time multiple of 10 eg. 
            11:20 will be stored.
            This could be refined to be ending in 30 or 00 for half hour 
            interval records only
            */
            if(sub.equals("0")){
                bomData[index][sortOrder] = String.valueOf(result.getInt("sort_order"));
                processJsonDoubleValue(index, result, "air_temp", air);
                processJsonDoubleValue(index, result, "apparent_t", apparentTemp);
                processJsonDoubleValue(index, result, "dewpt", dewPoint);
                processJsonDoubleValue(index, result, "rel_hum", relativeHumidity);
                processJsonDoubleValue(index, result, "delta_t", deltaT);
                processJsonString(index, result, "wind_dir", windDirection);
                processJsonDoubleValue(index, result, "wind_spd_kmh", windSpeedKmh);
                processJsonDoubleValue(index, result, "gust_kmh", windGustsKmh);
                processJsonDoubleValue(index, result, "wind_spd_kt", windSpeedKnots);
                processJsonDoubleValue(index, result, "gust_kt", windGustsKnots);
                processJsonDoubleValue(index, result, "press_qnh", pressQnh);
                processJsonDoubleValue(index, result, "press_msl", pressMsl);
                processJsonString(index, result, "rain_trace", rainSince);
                processJsonString(index, result, "local_date_time", dateTime);
            index++;
            }
        }
       displayOutputToLabel();
    }
    
    private static void displayOutputToLabel(){
        String time = String.valueOf(bomData[0][dateTime]);
        
        MainDashboardPanel.label2[sortOrder].setText(String.valueOf(bomData[0][sortOrder]));
        MainDashboardPanel.label2[dateTime].setText(time.substring(3));
        MainDashboardPanel.label2[air].setText(String.valueOf(bomData[0][air]) + "°C");
        MainDashboardPanel.label2[apparentTemp].setText(String.valueOf(bomData[0][apparentTemp]) + "°C");
        MainDashboardPanel.label2[dewPoint].setText(String.valueOf(bomData[0][dewPoint]));
        MainDashboardPanel.label2[relativeHumidity].setText(String.valueOf(bomData[0][relativeHumidity]) + "%");
        MainDashboardPanel.label2[deltaT].setText(String.valueOf(bomData[0][deltaT]));
        MainDashboardPanel.label2[windDirection].setText(String.valueOf(bomData[0][windDirection]));
        MainDashboardPanel.label2[windSpeedKmh].setText(String.valueOf(bomData[0][windSpeedKmh]));
        MainDashboardPanel.label2[windGustsKmh].setText(String.valueOf(bomData[0][windGustsKmh]));
        MainDashboardPanel.label2[windSpeedKnots].setText(String.valueOf(bomData[0][windSpeedKnots]));
        MainDashboardPanel.label2[windGustsKnots].setText(String.valueOf(bomData[0][windGustsKnots]));
        MainDashboardPanel.label2[pressQnh].setText(String.valueOf(bomData[0][pressQnh]));
        MainDashboardPanel.label2[pressMsl].setText(String.valueOf(bomData[0][pressMsl]));
        MainDashboardPanel.label2[rainSince].setText(String.valueOf(bomData[0][rainSince]));
    }
    
    //Checks for null values and replaces them with a zero
    private static void processJsonDoubleValue(int index, JsonObject result, String observationName, int observationIndex){
        if(result.isNull(observationName)){
            System.out.println("Warning value is null");
            bomData[index][observationIndex] = "0";
        } else {
            bomData[index][observationIndex] = String.valueOf(result.getJsonNumber(observationName).doubleValue());
        }
    }
    
    //Checks for null values and replaces them with ""
    private static void processJsonString(int index, JsonObject result, String observationName, int observationIndex){
        if(result.isNull(observationName) || result.getString(observationName).equals("-")){
            System.out.println("Warning value is null");
            bomData[index][observationIndex] = "0";
        } else {
            bomData[index][observationIndex] = String.valueOf(result.getString(observationName));
        }
    }
}
