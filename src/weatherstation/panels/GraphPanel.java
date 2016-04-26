package weatherstation.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import weatherstation.WeatherStation;

/**
 * Class info: This class is the main dashboard panel
 * Author: David Gillett (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 18 - April - 2016
 */

/**
 * TODO: change "Air TEMP" label (label 2) into combo box for selecting data  
 * 
 */
public class GraphPanel extends JPanel implements ActionListener, PopupMenuListener{
    private Color darkBlue;
    private Color gioBlue;
    private Color lightGrayBackground;
    private Color mainTextColor;
    
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;
    
    
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        private int value;

        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public static DrawingPanel drawingPanel = new DrawingPanel();
    private String[] weatherMeasurements = {"Sort Order", "Air Temp","Apparent Temp"
            ,"Dew Point","Relative Humidity","Delta T","Wind Direction"
            ,"Wind Speed(km/h)", "Wind Gusts(km/h)", "Wind Speed(knots)"
            , "Wind Gusts(knots)", "Pressure(Qnh)", "Pressure(MSL)","Rain Since"
            ,"Date Time"};

    
    private final int NUMBER_OF_COMBOBOXES = 1;
    private final int NUMBER_OF_PANELS = 2;
    public final int NUMBER_OF_BUTTONS = 2;
    private final int NUMBER_OF_LABEL_PANELS = 1;
    private final int NUMBER_OF_COMBOBOX_PANELS = 1;
    private final int NUMBER_OF_BUTTON_PANELS = 5;
    private final int NUMBER_OF_LABELS = 1;
    private final int NUMBER_OF_LABELS2 = 1;
    
    private JPanel[] panel = new JPanel[NUMBER_OF_PANELS];
    public JPanel[] labelPanel = new JPanel[NUMBER_OF_LABEL_PANELS];
    public JPanel[] comboBoxPanel = new JPanel[NUMBER_OF_COMBOBOX_PANELS];
    private JPanel[] buttonPanel = new JPanel[NUMBER_OF_BUTTON_PANELS];
    
    public JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    
    public JLabel[] label = new JLabel[NUMBER_OF_LABELS];
    public JLabel[] label2 = new JLabel[NUMBER_OF_LABELS2];
    
    public JComboBox[] comboBox = new JComboBox[NUMBER_OF_COMBOBOXES];
    public ComboBoxModel[] models = new ComboBoxModel[1];

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
    public int graphHeading = 0;
    
    public int closeButton = 0;
    public int backButton = 1;
    
    public int dataSelectBox = 0;
    
    public String graphHeading2 = "air temp";
    
    public GraphPanel(){
        darkBlue = WeatherStation.darkBlue;
        gioBlue = WeatherStation.gioBlue;
        lightGrayBackground = WeatherStation.lightGrayBackground;
        mainTextColor = WeatherStation.mainTextColor;
        
        initialiseComponents();
        addActionListeners();
    }
    private void addActionListeners(){
        for(int index = 0; index < NUMBER_OF_BUTTONS; index++){
            button[index].addActionListener(this);
        }
    }
    private void initialiseComponents(){
        initializeLabels();
        initializeButtons();
        initializeComboBoxes();
        createPanels();
    }
    public void initializeLabels(){

        label[graphHeading] = new JLabel("Past 24 hours of:");
        
        label2[graphHeading] = new JLabel("Air Temp");

        for(int index = 0; index < NUMBER_OF_LABELS; index++){
            label[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
            label[index].setForeground(WeatherStation.mainTextColor);
        }
        
        for(int index = 0; index < NUMBER_OF_LABELS2; index++){
            //label2[index] = new JLabel("NULL");
            label2[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
            label2[index].setForeground(WeatherStation.mainTextColor);
        }
    }
    private void initializeButtons(){
        button[closeButton] = new JButton("Close");
        button[backButton] = new JButton("Back");
        
        for(int index = 0; index < NUMBER_OF_BUTTONS; index++){
            button[index].setBorderPainted(false);
            button[index].setBackground(darkBlue);
            button[index].setForeground(Color.WHITE);
        }
    }
    
    private void initializeComboBoxes(){
        comboBox[dataSelectBox] = new JComboBox(weatherMeasurements);
        models[0] = new DefaultComboBoxModel(new String[]{
            "Air Temp","Apparent Temp","Dew Point","Relative Humidity","Delta T"
            ,"Wind Speed(km/h)", "Wind Gusts(km/h)", "Wind Speed(knots)"
            , "Wind Gusts(knots)", "Pressure(Qnh)", "Pressure(MSL)","Rain Since"
        });
 
        for(int index = 0; index < NUMBER_OF_COMBOBOXES; index++){
            comboBox[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
            comboBox[index].addActionListener(this);
            comboBox[index].addPopupMenuListener(this);
            comboBox[index].setForeground(WeatherStation.mainTextColor);
            comboBox[index].setModel(models[0]);
            comboBox[index].setSelectedIndex(0);
        }
    }
    
    
    public void createPanels(){
        for(int index = 0; index < NUMBER_OF_LABEL_PANELS; index++){
            labelPanel[index] = new JPanel();
            labelPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        for(int index = 0; index < NUMBER_OF_COMBOBOX_PANELS; index++){
            comboBoxPanel[index] = new JPanel();
            comboBoxPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        for(int index = 0; index < NUMBER_OF_BUTTON_PANELS; index++){
            buttonPanel[index] = new JPanel();
            buttonPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        
        comboBoxPanel[dataSelectBox].add(comboBox[dataSelectBox]);
        
        labelPanel[graphHeading].add(label[graphHeading]);
        labelPanel[graphHeading].add(comboBoxPanel[dataSelectBox]);
        
        buttonPanel[closeButton].add(button[closeButton]);
        buttonPanel[backButton].add(button[backButton]);
        
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(WeatherStation.BACKGROUND_COLOUR);
        headingPanel.add(labelPanel[graphHeading]);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(WeatherStation.BACKGROUND_COLOUR);
        mainPanel.add(headingPanel);
        mainPanel.add(drawingPanel);
        
        JPanel buttonsPanelMain = new JPanel();
        buttonsPanelMain.setBackground(WeatherStation.BACKGROUND_COLOUR);
        buttonsPanelMain.add(buttonPanel[backButton]);
        buttonsPanelMain.add(buttonPanel[closeButton]);

        this.setLayout(new BorderLayout());
        this.setBackground(WeatherStation.BACKGROUND_COLOUR);
        //this.setLayout(new GridLayout(4,1));
        this.add(headingPanel, BorderLayout.NORTH);
        this.add(drawingPanel, BorderLayout.CENTER);
        this.add(buttonsPanelMain, BorderLayout.SOUTH);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == button[closeButton]){
            System.exit(0);
        }
        else if(ae.getSource() == button[backButton]){
            CardLayout cl = (CardLayout)(WeatherStation.cards.getLayout());
            cl.show(WeatherStation.cards, "Main");
            drawingPanel.values.clear();
            drawingPanel.times.clear();
        
        }
        else if(ae.getSource() == comboBox[dataSelectBox]){
            //drawingPanel.selectedIndex = comboBox[dataSelectBox].getSelectedIndex();
            drawingPanel.selectedLabel = comboBox[dataSelectBox].getSelectedItem().toString();
            drawingPanel.repaint();
        }
    }
    
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        System.out.println("pop up visible");
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        System.out.println("Pop up invisible");
        drawingPanel.repaint();
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        System.out.println("Pop up cancelled");
    }
}
