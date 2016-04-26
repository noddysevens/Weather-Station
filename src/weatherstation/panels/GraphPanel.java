package weatherstation.panels;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import weatherstation.WeatherStation;

/**
 * Class info: This class drives the drawing panel
 * Author: David (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 26 - April - 2016
 */

public class GraphPanel extends JPanel implements ActionListener, PopupMenuListener{
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        private int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public static DrawingPanel drawingPanel = new DrawingPanel();

    private final int NUMBER_OF_COMBOBOXES = 1;
    private final int NUMBER_OF_PANELS = 2;
    private final int NUMBER_OF_LABEL_PANELS = 1;
    private final int NUMBER_OF_COMBOBOX_PANELS = 1;
    private final int NUMBER_OF_LABELS = 1;
    
    private JPanel[] panel = new JPanel[NUMBER_OF_PANELS];
    public JPanel[] labelPanel = new JPanel[NUMBER_OF_LABEL_PANELS];
    public JPanel[] comboBoxPanel = new JPanel[NUMBER_OF_COMBOBOX_PANELS];
    
    public JLabel[] label = new JLabel[NUMBER_OF_LABELS];
    
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
    
    public int dataSelectBox = 0;
    
    public String graphHeading2 = "air temp";
    
    public GraphPanel(){
        initialiseComponents();
    }

    private void initialiseComponents(){
        initializeLabels();
        initializeComboBoxes();
        createPanels();
    }
    public void initializeLabels(){
        label[graphHeading] = new JLabel("Past 24 hours of:");

        for(int index = 0; index < NUMBER_OF_LABELS; index++){
            label[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
            label[index].setForeground(WeatherStation.mainTextColor);
        }
    }
    
    private void initializeComboBoxes(){
        comboBox[dataSelectBox] = new JComboBox(WeatherStation.weatherMeasurements);
        models[0] = new DefaultComboBoxModel(new String[]{
            "Air Temp","Apparent Temp","Dew Point","Relative Humidity","Delta T"
            ,"Wind Speed(km/h)", "Wind Gusts(km/h)", "Wind Speed(knots)"
            , "Wind Gusts(knots)", "Pressure(Qnh)", "Pressure(MSL)","Rain Since"
        });
 
        for(int index = 0; index < NUMBER_OF_COMBOBOXES; index++){
            comboBox[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
            comboBox[index].addActionListener(this);
            comboBox[index].addPopupMenuListener(this);
            comboBox[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
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
        
        labelPanel[graphHeading].add(label[graphHeading]);
        labelPanel[graphHeading].add(comboBoxPanel[dataSelectBox]);
        comboBoxPanel[dataSelectBox].add(comboBox[dataSelectBox]);
        
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(WeatherStation.BACKGROUND_COLOUR);
        headingPanel.add(labelPanel[graphHeading]);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(WeatherStation.BACKGROUND_COLOUR);
        mainPanel.add(headingPanel);
        mainPanel.add(drawingPanel);

        this.setLayout(new BorderLayout());
        this.setBackground(WeatherStation.BACKGROUND_COLOUR);
        this.add(headingPanel, BorderLayout.NORTH);
        this.add(drawingPanel, BorderLayout.CENTER);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == comboBox[dataSelectBox]){
            drawingPanel.selectedLabel = comboBox[dataSelectBox].getSelectedItem().toString();
            drawingPanel.repaint();
        }
    }
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        drawingPanel.repaint();
    }
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {}
}
