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
import static weatherstation.WeatherStation.progressPanel;
import weatherstation.utilities.CollectInput;
import weatherstation.utilities.HomePostCodeStorage;

/**
 * Class info: This class is the main dashboard panel
 * Author: David (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 */
public class MainDashboardPanel extends JPanel{
    
    public final int SORT_ORDER_INDEX = 0;
    public final int AIR_TEMP_INDEX = 1;
    public final int APPARENT_TEMP_INDEX = 2;
    public final int DEW_POINT_INDEX = 3;
    public final int REL_HUMIDITY_INDEX = 4;
    public final int DELTAT_INDEX = 5;
    public final int WIND_DIR_INDEX = 6;
    public final int WIND_SPEED_KMH_INDEX = 7;
    public final int WIND_GUSTS_KMH_INDEX = 8;
    public final int WIND_SPEED_KNOTS_INDEX = 9;
    public final int WIND_GUSTS_KNOTS_INDEX = 10;
    public final int PRESS_QNH_INDEX = 11;
    public final int PRESS_MSL_INDEX = 12;
    public final int RAIN_SINCE_INDEX = 13;
    public final int DATE_TIME_INDEX = 14;
    private final int IMAGE_BUTTON_INDEX = 0;
    private final int CHANGE_HOMECODE_INDEX = 1;
    private final int VIEW_NEWCODE_INDEX = 2;
    public final static int TOPLABEL_INDEX = 14;
    
    private final int FONT_STYLE = Font.BOLD;
    private final String FONT_FACE = "verdana";
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MED_MED_LARGE(25), 
            MEDIUM_LARGE(30), LARGE(45);
        private int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    private ImageIcon menu;
    public static JPopupMenu popupMenu;
    
    private static final int NUMBER_OF_LABELS = 15;
    private static final int NUMBER_OF_BUTTONS = 3;
    private static final int NUMBER_OF_LABELS2 = 15;
    private static final int NUMBER_OF_LABEL_PANELS = 15;
    
    public static JLabel[] label = new JLabel[NUMBER_OF_LABELS];
    public static JLabel[] label2 = new JLabel[NUMBER_OF_LABELS2];
    public static JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    public static JPanel[] labelPanel = new JPanel[NUMBER_OF_LABEL_PANELS];

    public MainDashboardPanel(MainPanelDriver driver) throws IOException {
        initialiseComponents();
    }

    private void initialiseComponents(){
        initializeLabels();
        initializeButtons();
        createPanels();
    }
    public void initializeLabels(){
        label[SORT_ORDER_INDEX] = new JLabel("Sort Order:");
        label[AIR_TEMP_INDEX] = new JLabel("Air Temp:");
        label[APPARENT_TEMP_INDEX] = new JLabel("Apparent Temp:");
        label[DEW_POINT_INDEX] = new JLabel("Dew Point:");
        label[REL_HUMIDITY_INDEX] = new JLabel("Relative Humidity:");
        label[DELTAT_INDEX] = new JLabel("Delta T:");
        label[WIND_DIR_INDEX] = new JLabel("Wind Dir:");
        label[WIND_SPEED_KMH_INDEX] = new JLabel("Wind Speed(kmh):");
        label[WIND_GUSTS_KMH_INDEX] = new JLabel("Wind Gusts(kmh):");
        label[WIND_SPEED_KNOTS_INDEX] = new JLabel("Wind Speed(knots):");
        label[WIND_GUSTS_KNOTS_INDEX] = new JLabel("Wind Gusts(knots):");
        label[PRESS_QNH_INDEX] = new JLabel("Press QNH:");
        label[PRESS_MSL_INDEX] = new JLabel("Press MSL:");
        label[RAIN_SINCE_INDEX] = new JLabel("Rain since 9am mm:");
        label[TOPLABEL_INDEX] = new JLabel("Weather conditions at:");

        for(int index = 0; index < NUMBER_OF_LABELS; index++){
            label[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.SMALL.value));
            label[index].setForeground(WeatherStation.DARK_BLUE);
        }
        
        for(int index = 0; index < NUMBER_OF_LABELS2; index++){
            label2[index] = new JLabel("NULL");
            label2[index].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MED_MED_LARGE.value));
            label2[index].setForeground(WeatherStation.MAIN_TEXT_COLOUR);
        }
        
        label[TOPLABEL_INDEX].setForeground(Color.WHITE);
        label[TOPLABEL_INDEX].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        
        label2[DATE_TIME_INDEX].setForeground(Color.WHITE);
        label2[AIR_TEMP_INDEX].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.LARGE.value));
        
    }
    private void initializeButtons() {
        button[IMAGE_BUTTON_INDEX] = new JButton();
        button[CHANGE_HOMECODE_INDEX] = new JButton("Set this station as home");
        button[VIEW_NEWCODE_INDEX] = new JButton("View different Station");
        
        for (int i = 0; i < NUMBER_OF_BUTTONS; i++){
            button[i].setBackground(WeatherStation.DARK_BLUE);
            button[i].setForeground(Color.WHITE);
            button[i].addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(e.getSource() == button[VIEW_NEWCODE_INDEX]){
                        CardLayout cl = (CardLayout)(cards.getLayout());
                        cl.show(cards, "Postcode");
                        CollectInput.validWMO.clear();
                        CollectInput.stationName.clear();
                        WeatherStation.navigationPanel.setVisible(false);
                        MainDashboardPanel.labelPanel[DATE_TIME_INDEX].setVisible(false);
                        popupMenu.setVisible(false);
                        PostcodePanel.postcodeInputField.setText("Postcode: eg. 3066");
                        PostcodePanel.goButton.grabFocus();
                        PostcodePanel.goButton.requestFocus();
                    }
                    if(e.getSource() == button[CHANGE_HOMECODE_INDEX]){
                        if(PostcodePanel.postcodeInputField.getText().equals("Postcode: eg. 3066")){
                            HomePostCodeStorage.setHomePostcode(HomePostCodeStorage.getHomePostcode());
                        } else {
                            HomePostCodeStorage.setHomePostcode(HomePostCodeStorage.getCurrentPostcode());
                            HomePostCodeStorage.setHomeStationName(HomePostCodeStorage.getCurrentStationName());
                            HomePostCodeStorage.setHomeWMO(HomePostCodeStorage.getCurrentWMO());
                        }
                        MainDashboardPanel.popupMenu.setVisible(false);
                        WeatherStation.navigationPanel.setVisible(false);
                        MainDashboardPanel.labelPanel[DATE_TIME_INDEX].setVisible(false);
                        
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
        button[IMAGE_BUTTON_INDEX].setIcon(menu);
        button[IMAGE_BUTTON_INDEX].setMargin(new Insets(0,0,0,0));
        button[IMAGE_BUTTON_INDEX].setContentAreaFilled(false);
        button[IMAGE_BUTTON_INDEX].addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                showPopupMenu(button[IMAGE_BUTTON_INDEX]);
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
        
        labelPanel[DATE_TIME_INDEX].setBackground(WeatherStation.DARK_BLUE);
        
        labelPanel[SORT_ORDER_INDEX].add(label[SORT_ORDER_INDEX]);
        labelPanel[AIR_TEMP_INDEX].add(label[AIR_TEMP_INDEX]);
        labelPanel[APPARENT_TEMP_INDEX].add(label[APPARENT_TEMP_INDEX]);
        labelPanel[DEW_POINT_INDEX].add(label[DEW_POINT_INDEX]);
        labelPanel[REL_HUMIDITY_INDEX].add(label[REL_HUMIDITY_INDEX]);
        labelPanel[DELTAT_INDEX].add(label[DELTAT_INDEX]);
        labelPanel[WIND_DIR_INDEX].add(label[WIND_DIR_INDEX]);
        labelPanel[WIND_SPEED_KMH_INDEX].add(label[WIND_SPEED_KMH_INDEX]);
        labelPanel[WIND_GUSTS_KMH_INDEX].add(label[WIND_GUSTS_KMH_INDEX]);
        labelPanel[WIND_SPEED_KNOTS_INDEX].add(label[WIND_SPEED_KNOTS_INDEX]);
        labelPanel[WIND_GUSTS_KNOTS_INDEX].add(label[WIND_GUSTS_KNOTS_INDEX]);
        labelPanel[PRESS_QNH_INDEX].add(label[PRESS_QNH_INDEX]);
        labelPanel[PRESS_MSL_INDEX].add(label[PRESS_MSL_INDEX]);
        labelPanel[RAIN_SINCE_INDEX].add(label[RAIN_SINCE_INDEX]);

        labelPanel[SORT_ORDER_INDEX].add(label2[SORT_ORDER_INDEX]);
        labelPanel[AIR_TEMP_INDEX].add(label2[AIR_TEMP_INDEX]);
        labelPanel[APPARENT_TEMP_INDEX].add(label2[APPARENT_TEMP_INDEX]);
        labelPanel[DEW_POINT_INDEX].add(label2[DEW_POINT_INDEX]);
        labelPanel[REL_HUMIDITY_INDEX].add(label2[REL_HUMIDITY_INDEX]);
        labelPanel[DELTAT_INDEX].add(label2[DELTAT_INDEX]);
        labelPanel[WIND_DIR_INDEX].add(label2[WIND_DIR_INDEX]);
        labelPanel[WIND_SPEED_KMH_INDEX].add(label2[WIND_SPEED_KMH_INDEX]);
        labelPanel[WIND_GUSTS_KMH_INDEX].add(label2[WIND_GUSTS_KMH_INDEX]);
        labelPanel[WIND_SPEED_KNOTS_INDEX].add(label2[WIND_SPEED_KNOTS_INDEX]);
        labelPanel[WIND_GUSTS_KNOTS_INDEX].add(label2[WIND_GUSTS_KNOTS_INDEX]);
        labelPanel[PRESS_QNH_INDEX].add(label2[PRESS_QNH_INDEX]);
        labelPanel[PRESS_MSL_INDEX].add(label2[PRESS_MSL_INDEX]);
        labelPanel[RAIN_SINCE_INDEX].add(label2[RAIN_SINCE_INDEX]);
        
        labelPanel[DATE_TIME_INDEX].add(button[IMAGE_BUTTON_INDEX]);
        labelPanel[DATE_TIME_INDEX].add(label[TOPLABEL_INDEX]);
        labelPanel[DATE_TIME_INDEX].add(label2[DATE_TIME_INDEX]);

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
        
        mainPanel[panelA].add(labelPanel[AIR_TEMP_INDEX]);
        
        mainPanel[panelB].add(labelPanel[APPARENT_TEMP_INDEX]);
        mainPanel[panelB].add(labelPanel[DEW_POINT_INDEX]);
        
        mainPanel[panelC].add(labelPanel[REL_HUMIDITY_INDEX]);
        mainPanel[panelC].add(labelPanel[DELTAT_INDEX]);
        
        mainPanel[panelD].add(labelPanel[WIND_DIR_INDEX]);
        mainPanel[panelD].add(labelPanel[WIND_SPEED_KMH_INDEX]);
        mainPanel[panelD].add(labelPanel[WIND_GUSTS_KMH_INDEX]);
        
        mainPanel[panelE].add(labelPanel[WIND_SPEED_KNOTS_INDEX]);
        mainPanel[panelE].add(labelPanel[WIND_GUSTS_KNOTS_INDEX]);
        
        mainPanel[panelF].add(labelPanel[PRESS_QNH_INDEX]);
        mainPanel[panelF].add(labelPanel[PRESS_MSL_INDEX]);
        
        JPanel dataLabels = new JPanel();
        dataLabels.setBackground(WeatherStation.BACKGROUND_COLOUR);
        dataLabels.setLayout(new GridLayout(7, 1));
        dataLabels.add(mainPanel[panelA]);
        dataLabels.add(mainPanel[panelB]);
        dataLabels.add(mainPanel[panelC]);
        dataLabels.add(mainPanel[panelD]);
        dataLabels.add(mainPanel[panelE]);
        dataLabels.add(mainPanel[panelF]);
        dataLabels.add(labelPanel[RAIN_SINCE_INDEX]);
        
        this.setLayout(new BorderLayout());
        this.setBackground(WeatherStation.BACKGROUND_COLOUR);
        this.add(dataLabels, BorderLayout.NORTH);
    }
    private void showPopupMenu(JButton invoker) {
    popupMenu = new JPopupMenu();
    popupMenu.setLayout(new GridLayout(2, 1));
    popupMenu.add(button[CHANGE_HOMECODE_INDEX]);
    popupMenu.add(button[VIEW_NEWCODE_INDEX]);
    popupMenu.setBackground(WeatherStation.DARK_BLUE);
    popupMenu.show(invoker, 0, invoker.getHeight());
    popupMenu.addMouseListener(new MouseAdapter(){
        public void mouseEntered(java.awt.event.MouseEvent evt){
            showPopupMenu(button[IMAGE_BUTTON_INDEX]);
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