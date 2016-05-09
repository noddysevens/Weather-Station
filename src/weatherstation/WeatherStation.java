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
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import weatherstation.panels.CircularProgressBar;
import weatherstation.panels.GraphPanel;
import weatherstation.panels.MainDashboardPanel;
import static weatherstation.panels.MainDashboardPanel.topLabel;
import weatherstation.panels.MultiPostCodeSelectPanel;
import weatherstation.panels.PostcodePanel;
import weatherstation.utilities.CollectInput;
import weatherstation.utilities.HomePostCodeStorage;
import weatherstation.utilities.PrepareStationData;
import weatherstation.utilities.StationBlacklist;
import weatherstation.utilities.StationDeserializer;
import weatherstation.utilities.StationSerializer;
import weatherstation.utilities.ZipReader;

/**
 * Program Info: This program collects BOM data for toowoomba airport
 * and displays it in a dashboard and as 24hr graphs
 * Class info: This class is the main Driver for the Program
 * Author: David (NoddySevens) Java Developer
 * E-mail Address: noddysevens@gmail.com
 */
public class WeatherStation implements ActionListener{
    
    private final int CLOSE = 2;
    private final int MINIMIZE = 1;
    private static final int WIDTH = 650;
    private static final int HEIGHT = 600;
    public final static int NAVIGATION_BUTTON = 0;
    private final static int NUMBER_OF_BUTTONS = 3;
    private final static String USER_DIR = System.getProperty("user.dir");
    
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        public int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public final static Color DARK_BLUE = new Color(0,86,150);
    public final static Color LIGHT_BLUE = new Color(102,204,255);
    public final static Color MAIN_TEXT_COLOUR = new Color(30,56,91);
    public final static Color BACKGROUND_COLOUR = new Color(236,244,250);
        
    public static JPanel cards;
    public static JPanel navigationPanel;
    public static JPanel conditionsTimePanel;
    public static DrawingPanel drawingPanel = new DrawingPanel();
    public static PostcodePanel postCodePanel = new PostcodePanel();
    public static MultiPostCodeSelectPanel postCodeSelectPanel;
    
    private GraphPanel graphPanel = new GraphPanel();
    
    MainPanelDriver driver;
    StationBlacklist blacklist;
    public static CircularProgressBar progressPanel;
    
    private static Timer timer;
    private static Point point; 
    public static JFrame frame = new JFrame("Oz Weather");
    
    public static JButton[] button = new JButton[NUMBER_OF_BUTTONS];
    public static String[] dataUnits = {"","°C","°C","","%","","","","","","","","","mm",""};

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
    
    public WeatherStation() throws IOException{
        point = new Point();
        driver = new MainPanelDriver();
        blacklist = new StationBlacklist();
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
        frame.setBackground(LIGHT_BLUE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(HEIGHT,WIDTH));
        
        WeatherStation calcSuite = new WeatherStation();
        
        File file = new File(USER_DIR + "\\stationData.sav");
        if(file.exists()){
            StationDeserializer.retrieveData();
        } else {
            prepareData();
        }
        
        calcSuite.addComponentToPane(frame.getContentPane());
        
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        frame.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                PostcodePanel.goButton.grabFocus();
                PostcodePanel.goButton.requestFocus();
            }
        });
    }
    private static void prepareData(){
        try {
            ZipReader.readZip();
            
            PrepareStationData.removeNthLine(USER_DIR + "\\stations.txt", 20118);
            PrepareStationData.parseWords();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    private void addComponentToPane(Container pane) throws IOException {
        initializeButtons(); 
        
        timer = new Timer(60000, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainPanelDriver.initializeArrays();
                }
            });
        timer.start(); 
        
        conditionsTimePanel = new JPanel();
        conditionsTimePanel.setBackground(DARK_BLUE);
        
        navigationPanel = new JPanel();
        navigationPanel.add(button[NAVIGATION_BUTTON]);
        navigationPanel.setBackground(BACKGROUND_COLOUR);
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck();

        JPanel topSub = new JPanel();
        topSub.setLayout(new BorderLayout());
        topSub.setBackground(BACKGROUND_COLOUR);
        topSub.add(conditionsTimePanel, BorderLayout.CENTER);
        topSub.add(button[MINIMIZE], BorderLayout.LINE_END);
        
        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout());
        controls.setBackground(LIGHT_BLUE);
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
            button[i].setBackground(DARK_BLUE);
            button[i].setForeground(Color.WHITE);
            button[i].setBorderPainted(false);
            button[i].addActionListener(this);
            button[i].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        }
        button[MINIMIZE].setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
    }
    
    private void addCardsToDeck(){
        if(HomePostCodeStorage.getHomePostcode().equals("none")){
            cards.add(postCodePanel, "Postcode");
            cards.add(MainPanelDriver.mainPanel, "Main");
            cards.add(graphPanel, "Graph");
            postCodeSelectPanel = new MultiPostCodeSelectPanel();
            cards.add(postCodeSelectPanel, "postCodeSelect");
        } else { 
            cards.add(MainPanelDriver.mainPanel, "Main");
            cards.add(graphPanel, "Graph");
            cards.add(postCodePanel, "Postcode");
            postCodeSelectPanel = new MultiPostCodeSelectPanel();
            cards.add(postCodeSelectPanel, "postCodeSelect");
            conditionsTimePanel.add(MainDashboardPanel.labelPanel[dateTime]);
            frame.getContentPane().add(navigationPanel, BorderLayout.SOUTH);
            
            CollectInput.checkState(HomePostCodeStorage.getHomePostcode());
            CollectInput.getObservations(HomePostCodeStorage.getHomeWMO());
            String homeStationName = HomePostCodeStorage.getHomeStationName();
            
            MainPanelDriver.initializeArrays();
            
            if(homeStationName.length() > 20){
                homeStationName = homeStationName.substring(0, 20);
            }

            MainDashboardPanel.label[topLabel].setText(homeStationName + " at ");

            PostcodePanel.firstRun = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button[CLOSE]){
            StationSerializer.saveData();
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
