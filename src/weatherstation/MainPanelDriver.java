package weatherstation;

import java.io.IOException;
import javax.json.JsonObject;
import weatherstation.panels.MainDashboardPanel;
import weatherstation.utilities.CollectInput;

/**
 * Class info: This class drives the MainDashboardPanel
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 26-Apr-2016
 */
public class MainPanelDriver{
    static CollectInput input = new CollectInput();

    public static MainDashboardPanel mainPanel;
    
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

    public MainPanelDriver() throws IOException {
        mainPanel = new MainDashboardPanel(this);
    }
    
    public static void initializeArrays() throws IOException{
       input.getInput();

       int numberOfEntries = input.results.size();
       int index = 0;
       
       bomData = new String[numberOfEntries][15]; 
        
       for(JsonObject result : input.results.getValuesAs(JsonObject.class)){
            String time = String.valueOf(result.getString("local_date_time"));
            String sub = time.substring(7, 8);
            if(sub.equals("0")){
                bomData[index][sortOrder] = String.valueOf(result.getInt("sort_order"));
                bomData[index][air] = String.valueOf(result.getJsonNumber("air_temp").doubleValue());
                bomData[index][apparentTemp] = String.valueOf(result.getJsonNumber("apparent_t").doubleValue());
                bomData[index][dewPoint] = String.valueOf(result.getJsonNumber("dewpt").doubleValue());
                bomData[index][relativeHumidity] = String.valueOf(result.getJsonNumber("rel_hum").doubleValue());
                bomData[index][deltaT] = String.valueOf(result.getJsonNumber("delta_t").doubleValue());
                bomData[index][windDirection] = String.valueOf(result.getString("wind_dir"));
                bomData[index][windSpeedKmh] = String.valueOf(result.getJsonNumber("wind_spd_kmh").doubleValue());
                bomData[index][windGustsKmh] = String.valueOf(result.getJsonNumber("gust_kmh").doubleValue());
                bomData[index][windSpeedKnots] = String.valueOf(result.getJsonNumber("wind_spd_kt").doubleValue());
                bomData[index][windGustsKnots] = String.valueOf(result.getJsonNumber("gust_kt").doubleValue());
                bomData[index][pressQnh] = String.valueOf(result.getJsonNumber("press_qnh").doubleValue());
                bomData[index][pressMsl] = String.valueOf(result.getJsonNumber("press_msl").doubleValue());
                bomData[index][rainSince] = String.valueOf(result.getString("rain_trace"));
                bomData[index][dateTime] = String.valueOf(result.getString("local_date_time"));
            index++;
            }
        }
       displayOutputToLabel();
    }
    
    public static void displayOutputToLabel(){
        String time = String.valueOf(bomData[0][dateTime]);
        
        mainPanel.label2[sortOrder].setText(String.valueOf(bomData[0][sortOrder]));
        mainPanel.label2[dateTime].setText(time.substring(3));
        mainPanel.label2[air].setText(String.valueOf(bomData[0][air]) + "°C");
        mainPanel.label2[apparentTemp].setText(String.valueOf(bomData[0][apparentTemp]) + "°C");
        mainPanel.label2[dewPoint].setText(String.valueOf(bomData[0][dewPoint]));
        mainPanel.label2[relativeHumidity].setText(String.valueOf(bomData[0][relativeHumidity]) + "%");
        mainPanel.label2[deltaT].setText(String.valueOf(bomData[0][deltaT]));
        mainPanel.label2[windDirection].setText(String.valueOf(bomData[0][windDirection]));
        mainPanel.label2[windSpeedKmh].setText(String.valueOf(bomData[0][windSpeedKmh]));
        mainPanel.label2[windGustsKmh].setText(String.valueOf(bomData[0][windGustsKmh]));
        mainPanel.label2[windSpeedKnots].setText(String.valueOf(bomData[0][windSpeedKnots]));
        mainPanel.label2[windGustsKnots].setText(String.valueOf(bomData[0][windGustsKnots]));
        mainPanel.label2[pressQnh].setText(String.valueOf(bomData[0][pressQnh]));
        mainPanel.label2[pressMsl].setText(String.valueOf(bomData[0][pressMsl]));
        mainPanel.label2[rainSince].setText(String.valueOf(bomData[0][rainSince]));
        System.out.println("displayed");
    }
    
}
