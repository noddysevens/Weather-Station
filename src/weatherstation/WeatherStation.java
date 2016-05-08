package weatherstation;

import weatherstation.panels.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import static weatherstation.MainPanelDriver.dateTime;
import weatherstation.panels.CircularProgressBar;
import weatherstation.panels.GraphPanel;
import weatherstation.panels.MainDashboardPanel;
import static weatherstation.panels.MainDashboardPanel.topLabel;
import weatherstation.panels.MultiPostCodeSelectPanel;
import weatherstation.panels.PostcodePanel;
import static weatherstation.panels.PostcodePanel.postcodeInputField;
import weatherstation.utilities.CollectInput;
import static weatherstation.utilities.CollectInput.validWMO;
import weatherstation.utilities.HomePostCodeStorage;
import weatherstation.utilities.PrepareStationData;
import weatherstation.utilities.ZipReader;

/**
 * Program Info: This program collects BOM data for toowoomba airport
 * and displays it in a dashboard and as 24hr graphs
 * Class info: This class is the main Driver for the Program
 * Author: David (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 */
public class WeatherStation implements ActionListener{
    private static int HEIGHT = 600;
    private static int WIDTH = 650;
    private final static int NUMBER_OF_BUTTONS = 3;
    public static final int NAVIGATION_BUTTON = 0;
    private final int MINIMIZE = 1;
    private final int CLOSE = 2;
    
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        public int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public static Color darkBlue = new Color(0,86,150);
    public static Color gioBlue = new Color(102,204,255);
    public static Color BACKGROUND_COLOUR = new Color(236,244,250);
    public static Color mainTextColor = new Color(30,56,91);
        
    public static JPanel cards;
    public static DrawingPanel drawingPanel = new DrawingPanel();
    public static PostcodePanel postCodePanel = new PostcodePanel();
    public static MultiPostCodeSelectPanel postCodeSelectPanel;
    private GraphPanel graphPanel = new GraphPanel();
    MainPanelDriver driver;
    public static CircularProgressBar progressPanel;
    
    
    public static JFrame frame = new JFrame("Oz Weather");
    
    private static Point point = new Point();
    
    public static String[] weatherMeasurements = {"Sort Order", "Air Temp","Apparent Temp"
            ,"Dew Point","Relative Humidity","Delta T","Wind Direction"
            ,"Wind Speed(km/h)", "Wind Gusts(km/h)", "Wind Speed(knots)"
            , "Wind Gusts(knots)", "Pressure(Qnh)", "Pressure(MSL)","Rain Since"
            ,"Date Time"};
    private String[] dataIndexes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
    public static String[] dataUnits = {"","°C","°C","","%","","","","","","","","","mm",""};
    public static String[] stateJSONcodes = {"IDN60801","IDV60801","IDQ60801", "IDW60801","IDS60801","IDT60801","IDN60801","IDD60801"};
    
    public static int nsw = 0;
    public static int vic = 1;
    public static int qld = 2;
    public static int wa = 3;
    public static int sa = 4;
    public static int tas = 5;
    public static int act = 6;
    public static int nt = 7;

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
    
    static Timer timer;
    
    public static JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    
    public static JPanel navigationPanel;
    public static JPanel conditionsTimePanel;
    
    public static HomePostCodeStorage postcodeStore;
    
    public static ArrayList<ArrayList<String>> stationDataRows;
    
    public WeatherStation() throws IOException{
        postcodeStore = new HomePostCodeStorage();
        stationDataRows = new ArrayList<>();
    }
     
    public static void main(String[] Args) throws IOException{
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = frame.getLocation();
                frame.setLocation(p.x + e.getX() - point.x,
                        p.y + e.getY() - point.y);
            }
        });
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException ex) {}
            }
        });
    }
    private static void createAndShowGUI() throws IOException {
        frame.setUndecorated(true);
        frame.setBackground(gioBlue);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(HEIGHT,WIDTH));
        WeatherStation calcSuite = new WeatherStation();
        prepareData();
        calcSuite.addComponentToPane(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                postCodePanel.goButton.grabFocus();
                postCodePanel.goButton.requestFocus();
            }
        });
    }
    private static void prepareData(){
        //run the zip reader
        try {
            ZipReader.readZip();
            PrepareStationData.removeNthLine("C:/Users/David/Downloads/stations.txt", 20118);
            PrepareStationData.parseWords();
            System.out.println("Parsing complete");
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void addComponentToPane(Container pane) throws IOException {
        initializeButtons(); 
        
        driver = new MainPanelDriver();
        
        timer = new Timer(60000, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainPanelDriver.initializeArrays();
                }
            });
        timer.start(); 
        
        conditionsTimePanel = new JPanel();
        conditionsTimePanel.setBackground(darkBlue);
        
        navigationPanel = new JPanel();
        navigationPanel.add(button[NAVIGATION_BUTTON]);
        navigationPanel.setBackground(BACKGROUND_COLOUR);
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck(driver);

        JPanel topSub = new JPanel();
        topSub.setLayout(new BorderLayout());
        topSub.setBackground(BACKGROUND_COLOUR);
        topSub.add(conditionsTimePanel, BorderLayout.CENTER);
        topSub.add(button[MINIMIZE], BorderLayout.LINE_END);
        
        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout());
        controls.setBackground(gioBlue);
        controls.add(topSub, BorderLayout.CENTER);
        controls.add(button[CLOSE], BorderLayout.LINE_END);
        
        pane.add(controls , BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
    }
    
    private void initializeButtons(){
        button[NAVIGATION_BUTTON] = new JButton("Graphs");
        button[MINIMIZE] = new JButton("-");
        button[CLOSE] = new JButton("X");
        
        for(int i = 0; i < NUMBER_OF_BUTTONS; i++){
            button[i].setBackground(darkBlue);
            button[i].setForeground(Color.WHITE);
            button[i].setBorderPainted(false);
            button[i].addActionListener(this);
            button[i].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        }
        button[MINIMIZE].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
    }
    
    
    private void addCardsToDeck(MainPanelDriver driver){
        if(postcodeStore.getHomePostcode().equals("none")){
            cards.add(postCodePanel, "Postcode");
            cards.add(driver.mainPanel, "Main");
            cards.add(graphPanel, "Graph");
            postCodeSelectPanel = new MultiPostCodeSelectPanel();
            cards.add(postCodeSelectPanel, "postCodeSelect");
        } else {
            cards.add(driver.mainPanel, "Main");
            cards.add(graphPanel, "Graph");
            cards.add(postCodePanel, "Postcode");
            postCodeSelectPanel = new MultiPostCodeSelectPanel();
            cards.add(postCodeSelectPanel, "postCodeSelect");
            conditionsTimePanel.add(MainDashboardPanel.labelPanel[dateTime]);
            frame.getContentPane().add(navigationPanel, BorderLayout.SOUTH);
            
            String postCode = postcodeStore.getHomePostcode();
        
            int codeUp = Integer.parseInt(postCode);
            int codeDown = codeUp;
            CollectInput.getPostcodeInfo(postCode);
            while(CollectInput.validWMO.isEmpty()){
                codeUp++;
                CollectInput.getPostcodeInfo(String.valueOf(codeUp));
                codeDown--;
                CollectInput.getPostcodeInfo(String.valueOf(codeDown));
            }
            if(validWMO.size() > 1){
                CardLayout cl = (CardLayout)(cards.getLayout());
                
                cl.show(cards, "postCodeSelect");
                WeatherStation.navigationPanel.setVisible(false);
                MainDashboardPanel.labelPanel[dateTime].setVisible(false);
            } else {

                CollectInput.getObservations(validWMO.get(0));
                MainPanelDriver.initializeArrays();

                if(CollectInput.stationName.get(0).length() > 20){
                    CollectInput.stationName.set(0, CollectInput.stationName.get(0).substring(0, 20));
                }

                MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(0) + " at ");

                postCodePanel.firstRun = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button[CLOSE]){
            System.exit(0);
        } else if(e.getSource() == button[MINIMIZE]){
            frame.setState(frame.ICONIFIED);
        } else if(e.getSource() == button[NAVIGATION_BUTTON]){
            if(button[NAVIGATION_BUTTON].getText().equals("Back")){
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "Main");
                    button[NAVIGATION_BUTTON].grabFocus();
                    button[NAVIGATION_BUTTON].requestFocus();
                    button[NAVIGATION_BUTTON].setText("Graph");
                } else {
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "Graph");
                    button[NAVIGATION_BUTTON].grabFocus();
                    button[NAVIGATION_BUTTON].requestFocus();
                    button[NAVIGATION_BUTTON].setText("Back");
                }
        } 
    }
}
