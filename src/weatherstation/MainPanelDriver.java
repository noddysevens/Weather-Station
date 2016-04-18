package weatherstation;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
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
    
    //replace with multi dimensional array
    //convert all to string then back
    String[][] bomData;
    int[] sortOrder; //0
    String[] dateTime; //1
    public static double[] airTemp; //2
    public static ArrayList<Double> todaysAirTemps; //3
    double[] apparentTemp; //4
    double[] dewPoint; //5
    double[] relativeHumidity; //6
    double[] deltaT; //7
    String[] windDirection; //8
    double[] windSpeedKmh; //9
    double[] windGustsKmh; //10
    double[] windSpeedKnots; //11
    double[] windGustsKnots; //12
    double[] pressQnh; //13
    double[] pressMsl; //14
    String[] rainSince; //15
    
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
        if(ae.getSource() == mainPanel.closeButton){
            System.exit(0);
        }
        else if(ae.getSource() == mainPanel.nextButton){
            CardLayout cl = (CardLayout)(WeatherStation.cards.getLayout());
            cl.show(WeatherStation.cards, "card2");
        }
    }
    private void initializeArrays() throws IOException{
       input.getInput();

       int numberOfEntries = input.results.size();
       int index = 0;
       
       
       todaysAirTemps = new ArrayList<Double>();
       
       bomData = new String[numberOfEntries][15]; 
       
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
       
       
       
       for(JsonObject result : input.results.getValuesAs(JsonObject.class)){
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
           
           bomData[index][0] = String.valueOf(result.getInt("sort_order"));
           bomData[index][1] = String.valueOf(result.getString("local_date_time"));
           bomData[index][2] = String.valueOf(result.getJsonNumber("air_temp").doubleValue());
           bomData[index][3] = String.valueOf(result.getJsonNumber("apparent_t").doubleValue());
           bomData[index][4] = String.valueOf(result.getJsonNumber("dewpt").doubleValue());
           bomData[index][5] = String.valueOf(result.getJsonNumber("rel_hum").doubleValue());
           bomData[index][6] = String.valueOf(result.getJsonNumber("delta_t").doubleValue());
           bomData[index][7] = String.valueOf(result.getString("wind_dir"));
           bomData[index][8] = String.valueOf(result.getJsonNumber("wind_spd_kmh").doubleValue());
           bomData[index][9] = String.valueOf(result.getJsonNumber("gust_kmh").doubleValue());
           bomData[index][10] = String.valueOf(result.getJsonNumber("wind_spd_kt").doubleValue());
           bomData[index][11] = String.valueOf(result.getJsonNumber("gust_kt").doubleValue());
           bomData[index][12] = String.valueOf(result.getJsonNumber("press_qnh").doubleValue());
           bomData[index][13] = String.valueOf(result.getJsonNumber("press_msl").doubleValue());
           bomData[index][14] = String.valueOf(result.getString("rain_trace"));
           index++;
           
           //assign int variables to each name to use as index variables. s
       }
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

       displayOutputToLabel();
    }
    private void displayOutputToLabel(){
        String time = String.valueOf(dateTime[0]);
        
        mainPanel.label2[mainPanel.sortOrder].setText(String.valueOf(sortOrder[0]));
        mainPanel.label2[mainPanel.dateTime].setText(time.substring(3));
        mainPanel.label2[mainPanel.air].setText(String.valueOf(airTemp[0]) + "°C");
        mainPanel.label2[mainPanel.apparentTemp].setText(String.valueOf(apparentTemp[0]) + "°C");
        mainPanel.label2[mainPanel.dewPoint].setText(String.valueOf(dewPoint[0]));
        mainPanel.label2[mainPanel.relativeHumidity].setText(String.valueOf(relativeHumidity[0]) + "%");
        mainPanel.label2[mainPanel.deltaT].setText(String.valueOf(deltaT[0]));
        mainPanel.label2[mainPanel.windDirection].setText(String.valueOf(windDirection[0]));
        mainPanel.label2[mainPanel.windSpeedKmh].setText(String.valueOf(windSpeedKmh[0]));
        mainPanel.label2[mainPanel.windGustsKmh].setText(String.valueOf(windGustsKmh[0]));
        mainPanel.label2[mainPanel.windSpeedKnots].setText(String.valueOf(windSpeedKnots[0]));
        mainPanel.label2[mainPanel.windGustsKnots].setText(String.valueOf(windGustsKnots[0]));
        mainPanel.label2[mainPanel.pressQnh].setText(String.valueOf(pressQnh[0]));
        mainPanel.label2[mainPanel.pressMsl].setText(String.valueOf(pressMsl[0]));
        mainPanel.label2[mainPanel.rainSince].setText(String.valueOf(rainSince[0]));

    }
}
