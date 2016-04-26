package weatherstation;

import weatherstation.panels.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import weatherstation.panels.GraphPanel;

/**
 *
 * @author David
 */
public class WeatherStation implements ActionListener{
    private static int HEIGHT = 600;
    private static int WIDTH = 650;
    
    public static Color darkBlue = new Color(0,86,150);
    public static Color gioBlue = new Color(102,204,255);
    public static Color lightGrayBackground = new Color(236,244,250);
    public static Color mainTextColor = new Color(30,56,91);
    public static Color BACKGROUND_COLOUR = lightGrayBackground;
        
    public static JPanel cards;
    
    public static DrawingPanel drawingPanel = new DrawingPanel();
    GraphPanel graphPanel = new GraphPanel();
    
    static JFrame frame = new JFrame("Json Test");
    
    private static Point point = new Point();
    public static String[] weatherMeasurements = {"Sort Order", "Air Temp","Apparent Temp"
            ,"Dew Point","Relative Humidity","Delta T","Wind Direction"
            ,"Wind Speed(km/h)", "Wind Gusts(km/h)", "Wind Speed(knots)"
            , "Wind Gusts(knots)", "Pressure(Qnh)", "Pressure(MSL)","Rain Since"
            ,"Date Time"};
    private String[] dataIndexes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
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
    
    static Timer timer;
    
    MainPanelDriver driver;
    
    //public static JPanel card1 = new JPanel();
    //public static JPanel card2 = new JPanel();
    //private JPanel main = new JPanel();
    //public MainPanelDriver mainPanelDriver = new MainPanelDriver();
    
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
        calcSuite.addComponentToPane(frame.getContentPane());
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        
        
    }
    private void addCardsToDeck(MainPanelDriver driver){

        cards.add(driver.mainPanel, "Main");
        cards.add(graphPanel, "Graph");
    }
    private void addComponentToPane(Container pane) throws IOException {
        timer = new Timer(60000, this);
        timer.setInitialDelay(1000);
        timer.start(); 
        
        driver = new MainPanelDriver();
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck(driver);
        
        pane.add(driver.mainPanel.labelPanel[driver.mainPanel.dateTime], BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            driver.initializeArrays();
            driver.displayOutputToLabel();
        } catch (IOException ex) {}
    }
}