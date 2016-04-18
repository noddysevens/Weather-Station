package weatherstation.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import weatherstation.MainPanelDriver;
import weatherstation.WeatherStation;

/**
 * Class info: This class is the main dashboard panel
 * Author: David Gillett (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 18 - April - 2016
 */
public class MainDashboardPanel extends JPanel{
    
    private Color darkBlue;
    private Color gioBlue;
    private Color lightGrayBackground;
    private Color mainTextColor;
    
    //Font settings
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        private int value;

        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    private final int NUMBER_OF_PANELS = 3;
    public final int NUMBER_OF_BUTTONS = 2;
    private final int NUMBER_OF_LABEL_PANELS = 15;
    private final int NUMBER_OF_BUTTON_PANELS = 5;
    private final int NUMBER_OF_LABELS = 15;
    private final int NUMBER_OF_LABELS2 = 15;
    
    private JPanel[] panel = new JPanel[NUMBER_OF_PANELS];
    public JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    //public JButton nextButton = new JButton("Next");
    //public JButton closeButton = new JButton("Close");
    
    public JLabel[] label = new JLabel[NUMBER_OF_LABELS];

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
    public int topLabel = 14;

    public JLabel[] label2 = new JLabel[NUMBER_OF_LABELS2];
    
    public JPanel[] labelPanel = new JPanel[NUMBER_OF_LABEL_PANELS];
    
    private JPanel[] buttonPanel = new JPanel[NUMBER_OF_BUTTON_PANELS];
    public int closeButton = 0;
    public int nextButton = 1;
    
    public MainDashboardPanel(MainPanelDriver driver) throws IOException {
        darkBlue = WeatherStation.darkBlue;
        gioBlue = WeatherStation.gioBlue;
        lightGrayBackground = WeatherStation.lightGrayBackground;
        mainTextColor = WeatherStation.mainTextColor;
        
        initialiseComponents();
    }

    private void initialiseComponents(){
        initializeLabels();
        initializeButtons();
        createPanels();
    }
    public void initializeLabels(){
        label[sortOrder] = new JLabel("Sort Order:");
        label[air] = new JLabel("Air Temp:");
        label[apparentTemp] = new JLabel("Apparent Temp:");
        label[dewPoint] = new JLabel("Dew Point:");
        label[relativeHumidity] = new JLabel("Relative Humidity:");
        label[deltaT] = new JLabel("Delta T:");
        label[windDirection] = new JLabel("Wind Dir:");
        label[windSpeedKmh] = new JLabel("Wind Speed(kmh):");
        label[windGustsKmh] = new JLabel("Wind Gusts(kmh):");
        label[windSpeedKnots] = new JLabel("Wind Speed(knots):");
        label[windGustsKnots] = new JLabel("Wind Gusts(knots):");
        label[pressQnh] = new JLabel("Press QNH:");
        label[pressMsl] = new JLabel("Press MSL:");
        label[rainSince] = new JLabel("Rain since 9am mm:");
        label[topLabel] = new JLabel("Weather conditions at:");

        for(int index = 0; index < NUMBER_OF_LABELS; index++){
            label[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.SMALL.value));
            label[index].setForeground(WeatherStation.darkBlue);
        }
        
        for(int index = 0; index < NUMBER_OF_LABELS2; index++){
            label2[index] = new JLabel("NULL");
            label2[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
            label2[index].setForeground(WeatherStation.mainTextColor);
        }
        
        label[topLabel].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        label[topLabel].setForeground(Color.WHITE);
        label2[dateTime].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
        label2[dateTime].setForeground(Color.WHITE);
        label2[air].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.LARGE.value));
        label2[air].setForeground(WeatherStation.mainTextColor);
        label2[apparentTemp].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
        label2[apparentTemp].setForeground(WeatherStation.mainTextColor);
        
    }
    private void initializeButtons(){
        button[closeButton] = new JButton("Close");
        button[nextButton] = new JButton("Next");
        
        for(int index = 0; index < NUMBER_OF_BUTTONS; index++){
            button[index].setBorderPainted(false);
            button[index].setBackground(darkBlue);
            button[index].setForeground(Color.WHITE);
        }
    }
    public void createPanels(){
        setBackground(WeatherStation.BACKGROUND_COLOUR);
        
        for(int index = 0; index < NUMBER_OF_LABEL_PANELS; index++){
            labelPanel[index] = new JPanel();
            labelPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        for(int index = 0; index < NUMBER_OF_BUTTON_PANELS; index++){
            buttonPanel[index] = new JPanel();
            buttonPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        
        labelPanel[dateTime].setBackground(WeatherStation.darkBlue);
        labelPanel[apparentTemp].setBackground(WeatherStation.BACKGROUND_COLOUR);
        
        labelPanel[sortOrder].add(label[sortOrder]);
        labelPanel[air].add(label[air]);
        labelPanel[apparentTemp].add(label[apparentTemp]);
        labelPanel[dewPoint].add(label[dewPoint]);
        labelPanel[relativeHumidity].add(label[relativeHumidity]);
        labelPanel[deltaT].add(label[deltaT]);
        labelPanel[windDirection].add(label[windDirection]);
        labelPanel[windSpeedKmh].add(label[windSpeedKmh]);
        labelPanel[windGustsKmh].add(label[windGustsKmh]);
        labelPanel[windSpeedKnots].add(label[windSpeedKnots]);
        labelPanel[windGustsKnots].add(label[windGustsKnots]);
        labelPanel[pressQnh].add(label[pressQnh]);
        labelPanel[pressMsl].add(label[pressMsl]);
        labelPanel[rainSince].add(label[rainSince]);
        //labelPanel[dateTime] = dateTimePanel;

        labelPanel[sortOrder].add(label2[sortOrder]);
        labelPanel[air].add(label2[air]);
        labelPanel[apparentTemp].add(label2[apparentTemp]);
        labelPanel[dewPoint].add(label2[dewPoint]);
        labelPanel[relativeHumidity].add(label2[relativeHumidity]);
        labelPanel[deltaT].add(label2[deltaT]);
        labelPanel[windDirection].add(label2[windDirection]);
        labelPanel[windSpeedKmh].add(label2[windSpeedKmh]);
        labelPanel[windGustsKmh].add(label2[windGustsKmh]);
        labelPanel[windSpeedKnots].add(label2[windSpeedKnots]);
        labelPanel[windGustsKnots].add(label2[windGustsKnots]);
        labelPanel[pressQnh].add(label2[pressQnh]);
        labelPanel[pressMsl].add(label2[pressMsl]);
        labelPanel[rainSince].add(label2[rainSince]);
        labelPanel[dateTime].add(label[topLabel]);
        labelPanel[dateTime].add(label2[dateTime]);

        buttonPanel[closeButton].add(button[closeButton]);
        buttonPanel[nextButton].add(button[nextButton]);

        JPanel[] mainPanel = new JPanel[6];
        for (int i = 0; i < 6; i++){
            mainPanel[i] = new JPanel();
            mainPanel[i].setBackground(WeatherStation.BACKGROUND_COLOUR);
            mainPanel[i].setLayout(new FlowLayout());
        }
        int panelA = 0;
        int panelB = 1;
        int panelC = 2;
        int panelD = 3;
        int panelE = 4;
        int panelF = 5;
        
        mainPanel[panelA].add(labelPanel[air]);
        
        mainPanel[panelB].add(labelPanel[apparentTemp]);
        mainPanel[panelB].add(labelPanel[dewPoint]);
        
        mainPanel[panelC].add(labelPanel[relativeHumidity]);
        mainPanel[panelC].add(labelPanel[deltaT]);
        
        mainPanel[panelD].add(labelPanel[windDirection]);
        mainPanel[panelD].add(labelPanel[windSpeedKmh]);
        mainPanel[panelD].add(labelPanel[windGustsKmh]);
        
        mainPanel[panelE].add(labelPanel[windSpeedKnots]);
        mainPanel[panelE].add(labelPanel[windGustsKnots]);
        
        mainPanel[panelF].add(labelPanel[pressQnh]);
        mainPanel[panelF].add(labelPanel[pressMsl]);
        mainPanel[panelF].add(labelPanel[rainSince]);
        
        //this panerl needs to be it's own panel.
        JPanel panel4 = new JPanel();
        //panel4.setBackground(BACKGROUND_COLOUR);
        panel4.setLayout(new FlowLayout());
        WeatherStation.drawingPanel.setBackground(Color.white);
        panel4.add(WeatherStation.drawingPanel);
        
        JPanel buttonsPanelMain = new JPanel();
        buttonsPanelMain.setBackground(WeatherStation.BACKGROUND_COLOUR);
        buttonsPanelMain.add(buttonPanel[nextButton]);
        buttonsPanelMain.add(buttonPanel[closeButton]);
        
        JPanel dataLabels = new JPanel();
        dataLabels.setBackground(WeatherStation.BACKGROUND_COLOUR);
        dataLabels.setLayout(new GridLayout(7, 1));
        dataLabels.add(mainPanel[panelA]);
        dataLabels.add(mainPanel[panelB]);
        dataLabels.add(mainPanel[panelC]);
        dataLabels.add(mainPanel[panelD]);
        dataLabels.add(mainPanel[panelE]);
        dataLabels.add(mainPanel[panelF]);
        
        this.setLayout(new BorderLayout());
        this.setBackground(WeatherStation.BACKGROUND_COLOUR);
        this.add(dataLabels, BorderLayout.NORTH);
        this.add(buttonsPanelMain ,BorderLayout.CENTER);
    }
}
