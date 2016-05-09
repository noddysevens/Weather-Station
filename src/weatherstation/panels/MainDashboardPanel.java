package weatherstation.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import weatherstation.MainPanelDriver;
import weatherstation.WeatherStation;
import static weatherstation.WeatherStation.cards;
import static weatherstation.WeatherStation.postCodePanel;
import static weatherstation.WeatherStation.progressPanel;
import weatherstation.utilities.CollectInput;
import weatherstation.utilities.HomePostCodeStorage;

/**
 * Class info: This class is the main dashboard panel
 * Author: David (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 26 - April - 2016
 */
public class MainDashboardPanel extends JPanel{
    
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;

    
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MED_MED_LARGE(25), MEDIUM_LARGE(30), LARGE(45);
        private int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    private ImageIcon menu;
    
    private final int NUMBER_OF_PANELS = 3;
    private static final int NUMBER_OF_LABEL_PANELS = 15;
    private static final int NUMBER_OF_LABELS = 15;
    private static final int NUMBER_OF_LABELS2 = 15;
    private static final int NUMBER_OF_BUTTONS = 3;
    
    private JPanel[] panel = new JPanel[NUMBER_OF_PANELS];
    public static JPanel[] labelPanel = new JPanel[NUMBER_OF_LABEL_PANELS];
    public static JLabel[] label = new JLabel[NUMBER_OF_LABELS];
    public static JLabel[] label2 = new JLabel[NUMBER_OF_LABELS2];
    public static JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    
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
    public static int topLabel = 14;
    
    private int imageButton = 0;
    private int changeHomeCode = 1;
    private int viewNewCode = 2;

    public static JPopupMenu popupMenu;
    
    public MainDashboardPanel(MainPanelDriver driver) throws IOException {
        initialiseComponents();
        
        System.out.println("");
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
            label2[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MED_MED_LARGE.value));
            label2[index].setForeground(WeatherStation.mainTextColor);
        }
        
        label[topLabel].setForeground(Color.WHITE);
        label[topLabel].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        
        label2[dateTime].setForeground(Color.WHITE);
        label2[air].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.LARGE.value));
        
    }
    private void initializeButtons() {
        button[imageButton] = new JButton();
        button[changeHomeCode] = new JButton("Set this station as home");
        button[viewNewCode] = new JButton("View different Station");
        
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++){
            button[i].setBackground(WeatherStation.darkBlue);
            button[i].setForeground(Color.WHITE);
            button[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(e.getSource() == button[viewNewCode]){
                        CardLayout cl = (CardLayout)(cards.getLayout());
                        cl.show(cards, "Postcode");
                        CollectInput.validWMO.clear();
                        CollectInput.stationName.clear();
                        WeatherStation.navigationPanel.setVisible(false);
                        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
                        popupMenu.setVisible(false);
                        postCodePanel.postcodeInputField.setText("Postcode: eg. 3066");
                        postCodePanel.goButton.grabFocus();
                        postCodePanel.goButton.requestFocus();
                    }
                    if(e.getSource() == button[changeHomeCode]){
                        if(postCodePanel.postcodeInputField.getText().equals("Postcode: eg. 3066")){
                            HomePostCodeStorage.setHomePostcode(HomePostCodeStorage.getHomePostcode());
                        } else {
                            //this needs to set the rest
                            HomePostCodeStorage.setHomePostcode(HomePostCodeStorage.getCurrentPostcode());
                            HomePostCodeStorage.setHomeStationName(HomePostCodeStorage.getCurrentStationName());
                            HomePostCodeStorage.setHomeWMO(HomePostCodeStorage.getCurrentWMO());
                        }
                        MainDashboardPanel.popupMenu.setVisible(false);
                        WeatherStation.navigationPanel.setVisible(false);
                        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
                        
                        CardLayout cl = (CardLayout)(cards.getLayout());
                        progressPanel = new CircularProgressBar();
                        cards.add(progressPanel, "Progress");
                        cl.show(cards, "Progress");
                    }
                }
            });
        }
        try {
            menu = new ImageIcon(ImageIO.read(getResource("weatherstation/img/menu.png")));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        button[imageButton].setIcon(menu);
        button[imageButton].setMargin(new Insets(0,0,0,0));
        button[imageButton].setContentAreaFilled(false);
        button[imageButton].addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                showPopupMenu(button[imageButton]);
            }
        });
    }
    private InputStream getResource(String ref) throws IOException {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ref);
		if (in != null) {
			return in;
		}
		
		return new FileInputStream(ref);
    }
    public void createPanels(){
        for(int index = 0; index < NUMBER_OF_LABEL_PANELS; index++){
            labelPanel[index] = new JPanel();
            labelPanel[index].setBackground(WeatherStation.BACKGROUND_COLOUR);
        }
        
        labelPanel[dateTime].setBackground(WeatherStation.darkBlue);
        
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
        try{
            labelPanel[dateTime].add(button[imageButton]);
        } catch (Exception e){
            System.out.println(e);
        }
        
        labelPanel[dateTime].add(label[topLabel]);
        labelPanel[dateTime].add(label2[dateTime]);

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
        
        JPanel dataLabels = new JPanel();
        dataLabels.setBackground(WeatherStation.BACKGROUND_COLOUR);
        dataLabels.setLayout(new GridLayout(7, 1));
        dataLabels.add(mainPanel[panelA]);
        dataLabels.add(mainPanel[panelB]);
        dataLabels.add(mainPanel[panelC]);
        dataLabels.add(mainPanel[panelD]);
        dataLabels.add(mainPanel[panelE]);
        dataLabels.add(mainPanel[panelF]);
        dataLabels.add(labelPanel[rainSince]);
        
        this.setLayout(new BorderLayout());
        this.setBackground(WeatherStation.BACKGROUND_COLOUR);
        this.add(dataLabels, BorderLayout.NORTH);
    }
    private void showPopupMenu(JButton invoker) {
    popupMenu = new JPopupMenu();
    popupMenu.setLayout(new GridLayout(2, 1));
    popupMenu.add(button[changeHomeCode]);
    popupMenu.add(button[viewNewCode]);
    popupMenu.setBackground(WeatherStation.darkBlue);
    popupMenu.show(invoker, 0, invoker.getHeight());
    popupMenu.addMouseListener(new MouseAdapter(){
        public void mouseEntered(java.awt.event.MouseEvent evt){
            showPopupMenu(button[imageButton]);
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            Timer timer = new Timer(1000, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    popupMenu.setVisible(false);
                }
            });
            timer.start();
            timer.setRepeats(false);
                
        }
    });
    
}

}