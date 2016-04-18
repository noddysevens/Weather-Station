package weatherstation;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.json.JsonObject;
import javax.swing.JTextField;
import weatherstation.panels.MainDashboardPanel;
import weatherstation.utilities.CollectInput;


/**
 * Program info: 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 
 */
public class MainPanelDriver implements ActionListener{
    CollectInput input = new CollectInput();

    public MainDashboardPanel mainPanel;
     
    private String lastCard = "";
    private JTextField lastJTextField;
    private int inputCode = 0;
    private int outputCode = 1;
    
    public int sortOrder = 0;
    public int air = 1;
    public int apparentTemp = 2;
    public int dewPoint = 3;
    public int relativeHumidity = 4;
    public int deltaT = 5;
    public int windDirection = 6;
    public int windSpeedKmh = 7;
    public int windGustsKmh = 8;
    public int windSpeedKnots = 9;
    public int windGustsKnots = 10;
    public int pressQnh = 11;
    public int pressMsl = 12;
    public int rainSince = 13;
    public int dateTime = 14;
    //public int topLabel = 14;
    
    //replace with multi dimensional array
    //convert all to string then back
    public static String[][] bomData;
    LocalDate date = LocalDate.now();
    int dow = date.getDayOfMonth();
    
    public static int numberOfRecordsSinceMidnight;

    public MainPanelDriver() throws IOException {
        mainPanel = new MainDashboardPanel(this);
        initializeArrays();
        addActionListeners();
        displayOutputToLabel();
    }
    
    private void addActionListeners(){
        for(int index = 0; index < mainPanel.NUMBER_OF_BUTTONS; index++){
            mainPanel.button[index].addActionListener(this);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == mainPanel.button[mainPanel.closeButton]){
            System.exit(0);
        }
        else if(ae.getSource() == mainPanel.button[mainPanel.nextButton]){
            CardLayout cl = (CardLayout)(WeatherStation.cards.getLayout());
            cl.show(WeatherStation.cards, "card2");
        }
    }
    private void initializeArrays() throws IOException{
       input.getInput();

       int numberOfEntries = input.results.size();
       int index = 0;
       
       //todaysAirTemps = new ArrayList<Double>();
       
       bomData = new String[numberOfEntries][15]; 
       /*
       sortOrder = new int[numberOfEntries];
       dateTime = new String[numberOfEntries];
       airTemp = new double[numberOfEntries];
       apparentTemp = new double[numberOfEntries];
       dewPoint = new double[numberOfEntries];
       relativeHumidity = new double[numberOfEntries];
       deltaT = new double[numberOfEntries];
       windDirection = new String[numberOfEntries];
       windSpeedKmh = new double[numberOfEntries];
       windGustsKmh = new double[numberOfEntries];
       windSpeedKnots = new double[numberOfEntries];
       windGustsKnots = new double[numberOfEntries];
       pressQnh = new double[numberOfEntries];
       pressMsl = new double[numberOfEntries];
       rainSince = new String[numberOfEntries];
        */
       for(JsonObject result : input.results.getValuesAs(JsonObject.class)){
           /*
           sortOrder[index] = result.getInt("sort_order");
           dateTime[index] = result.getString("local_date_time");
           airTemp[index] = result.getJsonNumber("air_temp").doubleValue();
           apparentTemp[index] = result.getJsonNumber("apparent_t").doubleValue();
           dewPoint[index] = result.getJsonNumber("dewpt").doubleValue();
           relativeHumidity[index] = result.getJsonNumber("rel_hum").doubleValue();
           deltaT[index] = result.getJsonNumber("delta_t").doubleValue();
           windDirection[index] = result.getString("wind_dir");
           windSpeedKmh[index] = result.getJsonNumber("wind_spd_kmh").doubleValue();
           windGustsKmh[index] = result.getJsonNumber("gust_kmh").doubleValue();
           windSpeedKnots[index] = result.getJsonNumber("wind_spd_kt").doubleValue();
           windGustsKnots[index] = result.getJsonNumber("gust_kt").doubleValue();
           pressQnh[index] = result.getJsonNumber("press_qnh").doubleValue();
           pressMsl[index] = result.getJsonNumber("press_msl").doubleValue();
           rainSince[index] = result.getString("rain_trace");
            */
    
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
           
           //assign int variables to each name to use as index variables. s
       }
/*
       boolean stillToday = true;
       int i = 0;

       System.out.println("todays date is " + dow);
       String s = String.valueOf(dow - 1);
       
       while(stillToday){
           int indexOfyesterday = dateTime[i].indexOf(s);
           System.out.println(dateTime[i]);
           System.out.println("index of 12" + indexOfyesterday);
           if(indexOfyesterday == 0){
               stillToday = false;
               System.out.println("i yesterday = " + (i - 1));
               numberOfRecordsSinceMidnight = i;
           }
           i++;
           
       }
       */
       displayOutputToLabel();
    }
    private void displayOutputToLabel(){
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

    }
}
