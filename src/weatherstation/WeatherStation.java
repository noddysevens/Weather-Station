package weatherstation;

import weatherstation.panels.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.json.JsonObject;
import javax.swing.JFrame;
import javax.swing.JPanel;
import weatherstation.utilities.CollectInput;
//import static weatherstation.utilities.CollectInput.inputStream;
//import Examples.OvalButton;

/**
 *
 * @author David
 */
public class WeatherStation {
    CollectInput input = new CollectInput();
    
    private static final int BOM_CHECK_FREQUENCY = 300000; //5 mins
    
    static URL url;
    
    static int HEIGHT = 600;
    static int WIDTH = 650;
    
    //Font settings
    private final String FONT_FACE = "verdana";
    private final int FONT_STYLE = Font.BOLD;
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        private int value;

        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public static Color darkBlue = new Color(0,86,150);
    public static Color gioBlue = new Color(102,204,255);
    public static Color lightGrayBackground = new Color(236,244,250);
    public static Color mainTextColor = new Color(30,56,91);
    public static Color TEXT_BOX_COLOUR = Color.WHITE;
    public static Color TEXT_BOX__TEXT_COLOUR = darkBlue;
    public static Color BACKGROUND_COLOUR = lightGrayBackground;
        
    public static JPanel cards;
    
    private JPanel main = new JPanel();
    public static JPanel card1 = new JPanel();
    public static JPanel card2 = new JPanel();

    public static DrawingPanel drawingPanel = new DrawingPanel();
    
    static JFrame frame = new JFrame("Json Test");
    
    private static Point point = new Point();
    
    
    
    //public MainPanelDriver mainPanelDriver = new MainPanelDriver();
    
    public static void main(String[] Args) throws IOException{
        frame.addMouseListener(new MouseAdapter() {
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
        //Create and set up the window.
        frame.setUndecorated(true);
        frame.setBackground(gioBlue);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(HEIGHT,WIDTH));
        //Create and set up the content pane.
        WeatherStation calcSuite = new WeatherStation();
        calcSuite.addComponentToPane(frame.getContentPane());
        
        //frameIcon();
        //frame.setIconImage(frameIcon.getImage());
        //Display the window.
        frame.pack();
        
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
        
        
    }

    public void printToConsole(){
        for(JsonObject result : input.results.getValuesAs(JsonObject.class)){
           System.out.println("Sort Order: " + result.getInt("sort_order"));
           System.out.println("Local Date Time: " + result.getString("local_date_time"));
           System.out.println("Air temp: " + result.getJsonNumber("air_temp"));
           System.out.println("Apparent Temp: " + result.getJsonNumber("apparent_t"));
           System.out.println("Dew Point: " + result.getJsonNumber("dewpt"));
           System.out.println("Relative humidity %: " + result.getJsonNumber("rel_hum"));
           System.out.println("Delta temp: " + result.getJsonNumber("delta_t"));
           System.out.println("Wind direction: " + result.getString("wind_dir"));
           System.out.println("Wind speed (km/h): " + result.getJsonNumber("wind_spd_kmh"));
           System.out.println("Wind gust (km/h): " + result.getJsonNumber("gust_kmh"));
           System.out.println("Wind speed (knot): " + result.getJsonNumber("wind_spd_kt"));
           System.out.println("Wind gust (knots): " + result.getJsonNumber("gust_kt"));
           System.out.println("Air Pressure (QNH): " + result.getJsonNumber("press_qnh"));
           System.out.println("Air Pressure (MSL): " + result.getJsonNumber("press_msl"));
           System.out.println("Rain since 9am mm: " + result.getString("rain_trace"));
           
           //System.out.println("wmo: " + result.getInt("wmo"));
           //System.out.println("Name: " + result.getString("name"));
           //System.out.println("Histor Product: " + result.getString("history_product"));
           //System.out.println("Local Date Time Full: " + result.getString("local_date_time_full"));
           //System.out.println("UTC time: " + result.getString("aifstime_utc"));
           //System.out.println("Latitude: " + result.getJsonNumber("lat"));
           //System.out.println("Longitude: " + result.getJsonNumber("lon"));
           //System.out.println("Cloud: " + result.getString("cloud"));
           //System.out.println("Cloud base m: " + result.getJsonNumber("cloud_base_m"));
           //System.out.println("Cloud ok tas: " + result.getJsonNumber("cloud_oktas"));
           //System.out.println("Cloud type: " + result.getString("cloud_type"));
           //System.out.println(result.getInt("cloud_type_id"));
           //System.out.println("Air pressure: " + result.getJsonNumber("press"));
           //System.out.println("pressure tend: " + result.getString("press_tend"));
           //System.out.println("Sea state: " + result.getString("sea_state"));
           //System.out.println("Swell: " + result.getString("swell_dir_worded"));
           //System.out.println(result.getString("swell_height"));
           //System.out.println(result.getString("swell_period"));
           //System.out.println("Vis km: " + result.getString("vis_km"));
           //System.out.println("Weather: " + result.getString("weather"));
           System.out.println("end of line");
           System.out.println();
        }
    }
    private void initializeComponents(){
        //initializeArrays();
        //initializeButtons();
        //initializeLabels();
        //createPanels();
    }
    private void addCardsToDeck(MainPanelDriver driver){
        cards.add(driver.mainPanel, "Main");
    }
    public void addComponentToPane(Container pane) throws IOException {
        MainPanelDriver driver = new MainPanelDriver();
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck(driver);
        
        pane.add(driver.mainPanel.dateTimePanel, BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);

        //addActionListeners();
    }
}
    

