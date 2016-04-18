package weatherstation.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
 * Program info: This Program contains a suite of calculators 
 * for SUN and GIO staff.
 * Author: David Gillett (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 13th of November 2015
 */
public class MainDashboardPanel extends JPanel{
    //WeatherStation station = new WeatherStation();
    
    private Color darkBlue;
    private Color gioBlue;
    private Color lightGrayBackground;
    private Color mainTextColor;
    private Color TEXT_BOX_COLOUR;
    private Color TEXT_BOX__TEXT_COLOUR;
    private Color BACKGROUND_COLOUR;
    
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
    private final int NUMBER_OF_LABELS = 14;
    private final int NUMBER_OF_LABELS2 = 15;
    private final int NUMBER_OF_LABELS3 = 2;
    
    private JPanel[] panel = new JPanel[NUMBER_OF_PANELS];
    public JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    public JButton nextButton = new JButton("Next");
    public JButton closeButton = new JButton("Close");
    
    private JLabel[] label = new JLabel[NUMBER_OF_LABELS];

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

    public JLabel[] label2 = new JLabel[NUMBER_OF_LABELS2];

    private JLabel[] label3 = new JLabel[NUMBER_OF_LABELS3];
    private JLabel topLabel = new JLabel("Weather conditions at:");
    
    private JPanel[] labelPanel = new JPanel[NUMBER_OF_LABEL_PANELS];
    private JPanel sortOrderPanel = new JPanel();
    private JPanel airPanel = new JPanel();
    private JPanel apparentTempPanel = new JPanel();
    private JPanel dewPointPanel = new JPanel();
    private JPanel relativeHumidityPanel = new JPanel();
    private JPanel deltaTPanel = new JPanel();
    private JPanel windDirectionPanel = new JPanel();
    private JPanel windSpeedKmhPanel = new JPanel();
    private JPanel windGustsKmhPanel = new JPanel();
    private JPanel windSpeedKnotsPanel = new JPanel();
    private JPanel windGustsKnotsPanel = new JPanel();
    private JPanel pressQnhPanel = new JPanel();
    private JPanel pressMslPanel = new JPanel();
    private JPanel rainSincePanel = new JPanel();
    public JPanel dateTimePanel = new JPanel();
    
    private JPanel[] buttonPanel = new JPanel[NUMBER_OF_BUTTON_PANELS];
    private JPanel closeButtonPanel1 = new JPanel();
    private JPanel closeButtonPanel2 = new JPanel();
    private JPanel closeButtonPanel3 = new JPanel();
    private JPanel nextButtonPanel1 = new JPanel();
    private JPanel nextButtonPanel2 = new JPanel();

    public MainDashboardPanel(MainPanelDriver driver) throws IOException {
        darkBlue = WeatherStation.darkBlue;
        gioBlue = WeatherStation.gioBlue;
        lightGrayBackground = WeatherStation.lightGrayBackground;
        mainTextColor = WeatherStation.mainTextColor;
        TEXT_BOX_COLOUR = WeatherStation.TEXT_BOX_COLOUR;
        TEXT_BOX__TEXT_COLOUR = darkBlue;
        BACKGROUND_COLOUR = lightGrayBackground;
        
        initialiseComponents();
    }
    
    private void setPanelBackColor(Color backColor){
        /*
        for(int i = 0; i < NUMBER_OF_PANELS; i++){
            panel[i].setBackground(backColor);
            this.setBackground(backColor);
        }
        convertPanel.setBackground(backColor);
        */
    }
    private void setButtonColors(Color fontColor, Color buttonColor){
            nextButton.setBackground(buttonColor);
            closeButton.setBackground(buttonColor);
            
            nextButton.setForeground(fontColor);
            closeButton.setForeground(fontColor);
    }
    public void setLabelText(String text){
        //label.setText(text);
    }
    public void setLabelColor(Color labelColor){
        //label.setForeground(labelColor);
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

        for(int index = 0; index < NUMBER_OF_LABELS; index++){
            label[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.SMALL.value));
            label[index].setForeground(WeatherStation.darkBlue);
        }
        for(int index = 0; index < NUMBER_OF_LABELS2; index++){
            label2[index] = new JLabel("NULL");
            label2[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
            label2[index].setForeground(WeatherStation.mainTextColor);
        }
        topLabel.setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        topLabel.setForeground(Color.WHITE);
        
        label2[dateTime].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
        label2[dateTime].setForeground(Color.WHITE);
        
        label2[air].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.LARGE.value));
        label2[air].setForeground(WeatherStation.mainTextColor);
        label2[apparentTemp].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
        label2[apparentTemp].setForeground(WeatherStation.mainTextColor);
        
    }
    private void initializeButtons(){
        button[0] = nextButton;
        button[1] = closeButton;
        
        for(int index = 0; index < NUMBER_OF_BUTTONS; index++){
            button[index].setBorderPainted(false);
            button[index].setBackground(darkBlue);
            button[index].setForeground(Color.WHITE);
        }
    }
    public void createPanels(){
    
        labelPanel[sortOrder] = sortOrderPanel;
        labelPanel[air] = airPanel;
        labelPanel[apparentTemp] = apparentTempPanel;
        labelPanel[dewPoint] = dewPointPanel;
        labelPanel[relativeHumidity] = relativeHumidityPanel;
        labelPanel[deltaT] = deltaTPanel;
        labelPanel[windDirection] = windDirectionPanel;
        labelPanel[windSpeedKmh] = windSpeedKmhPanel;
        labelPanel[windGustsKmh] = windGustsKmhPanel;
        labelPanel[windSpeedKnots] = windSpeedKnotsPanel;
        labelPanel[windGustsKnots] = windGustsKnotsPanel;
        labelPanel[pressQnh] = pressQnhPanel;
        labelPanel[pressMsl] = pressMslPanel;
        labelPanel[rainSince] = rainSincePanel;
        labelPanel[dateTime] = dateTimePanel;
        //labelPanel[15] = topLabelPanel;
        
        buttonPanel[0] = closeButtonPanel1;
        buttonPanel[1] = closeButtonPanel2;
        buttonPanel[2] = closeButtonPanel3;
        buttonPanel[3] = nextButtonPanel1;
        buttonPanel[4] = nextButtonPanel2;
        
        for(int index = 0; index < NUMBER_OF_LABEL_PANELS; index++){
            labelPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        for(int index = 0; index < NUMBER_OF_BUTTON_PANELS; index++){
            buttonPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        
        labelPanel[dateTime].setBackground(WeatherStation.darkBlue);
        labelPanel[apparentTemp].setBackground(WeatherStation.BACKGROUND_COLOUR);
                
        setBackground(WeatherStation.BACKGROUND_COLOUR);
        
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
        labelPanel[dateTime].add(label2[dateTime]);

        nextButtonPanel1.add(nextButton);
        closeButtonPanel1.add(closeButton);
        
        
        //create a JPanel array and cycle thourgh
        /*
        JPanel panel1 = new JPanel();
        panel1.setBackground(BACKGROUND_COLOUR);
        panel1.setLayout(new FlowLayout());
        //panel1.add(topLabelPanel);
        panel1.add(dateTimePanel);
        */
        JPanel[] mainPanel = new JPanel[5];
        for (int i = 0; i < 5; i++){
            mainPanel[i].setBackground(BACKGROUND_COLOUR);
            mainPanel[i].setLayout(new FlowLayout());
        }
        
        JPanel panel2A = new JPanel();
        panel2A.setBackground(BACKGROUND_COLOUR);
        panel2A.setLayout(new FlowLayout());
        panel2A.add(labelPanel[air]);
        //panel2A.add(apparentTempPanel);
        
        JPanel panel2B = new JPanel();
        panel2B.setBackground(BACKGROUND_COLOUR);
        panel2B.setLayout(new FlowLayout());
        panel2B.add(labelPanel[apparentTemp]);
        panel2B.add(labelPanel[dewPoint]);
        
        JPanel panel2C = new JPanel();
        panel2C.setBackground(BACKGROUND_COLOUR);
        panel2C.setLayout(new FlowLayout());
        panel2C.add(labelPanel[relativeHumidity]);
        panel2C.add(labelPanel[deltaT]);
        
        JPanel panel2D = new JPanel();
        panel2D.setBackground(BACKGROUND_COLOUR);
        panel2D.setLayout(new FlowLayout());
        panel2D.add(labelPanel[windDirection]);
        panel2D.add(labelPanel[windSpeedKmh]);
        panel2D.add(labelPanel[windGustsKmh]);
        
        JPanel panel2E = new JPanel();
        panel2E.setBackground(BACKGROUND_COLOUR);
        panel2E.setLayout(new FlowLayout());
        panel2E.add(labelPanel[windSpeedKnots]);
        panel2E.add(labelPanel[windGustsKnots]);
        
        JPanel panel2F = new JPanel();
        panel2F.setBackground(BACKGROUND_COLOUR);
        panel2F.setLayout(new FlowLayout());
        panel2F.add(labelPanel[pressQnh]);
        panel2F.add(labelPanel[pressMsl]);
        panel2F.add(labelPanel[rainSince]);
        
        /*
        JPanel panel3A = new JPanel();
        panel3A.setBackground(BACKGROUND_COLOUR);
        panel3A.setLayout(new FlowLayout());
        //panel3A.add(windDirectionPanel);
        //panel3A.add(windSpeedKmhPanel);
        //panel3A.add(windGustsKmhPanel);
        
        JPanel panel3B = new JPanel();
        panel3B.setBackground(BACKGROUND_COLOUR);
        panel3B.setLayout(new FlowLayout());
        //panel3B.add(windSpeedKnotsPanel);
        //panel3B.add(windGustsKnotsPanel);
        */
        
        //this panerl needs to be it's own panel.
        JPanel panel4 = new JPanel();
        //panel4.setBackground(BACKGROUND_COLOUR);
        panel4.setLayout(new FlowLayout());
        WeatherStation.drawingPanel.setBackground(Color.white);
        panel4.add(WeatherStation.drawingPanel);

        //panel4.add(pressQnhPanel);
        //panel4.add(pressMslPanel);
        //panel4.add(rainSincePanel);
        
        JPanel buttonsPanelMain = new JPanel();
        buttonsPanelMain.setBackground(BACKGROUND_COLOUR);
        //buttonsPanelMain.setLayout(new BorderLayout());
        buttonsPanelMain.add(nextButtonPanel1);
        buttonsPanelMain.add(closeButtonPanel1);
        
        JPanel buttonsPanelCard1 = new JPanel();
        buttonsPanelCard1.setBackground(BACKGROUND_COLOUR);
        buttonsPanelCard1.add(nextButtonPanel2);
        //buttonsPanelCard1.add(closeButtonPanel1);
        
        JPanel buttonsPanelCard2 = new JPanel();
        buttonsPanelCard2.setBackground(BACKGROUND_COLOUR);
        buttonsPanelCard2.add(closeButtonPanel2);
        
        JPanel textBoxMain = new JPanel();
        textBoxMain.setBackground(BACKGROUND_COLOUR);
        textBoxMain.setLayout(new GridLayout(7, 1));
        textBoxMain.add(panel2A);
        //textBoxMain.add(dateTimePanel);
        textBoxMain.add(panel2B);
        textBoxMain.add(panel2C);
        textBoxMain.add(panel2D);
        textBoxMain.add(panel2E);
        textBoxMain.add(panel2F);
        
        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOUR);
        //this.add(panel1, BorderLayout.NORTH);
        this.add(textBoxMain, BorderLayout.NORTH);
        this.add(buttonsPanelMain ,BorderLayout.CENTER);
        
        /*
        JPanel card1Labels = new JPanel();
        card1Labels.setBackground(BACKGROUND_COLOUR);
        card1Labels.setLayout(new GridLayout(2, 1));
        card1Labels.add(panel3A);
        card1Labels.add(panel3B);
        */
        
        
        
        /*
        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOUR);
        this.add(card1Labels, BorderLayout.NORTH);
        this.add(buttonsPanelCard1 ,BorderLayout.CENTER);
        */
        
        
    }
}
