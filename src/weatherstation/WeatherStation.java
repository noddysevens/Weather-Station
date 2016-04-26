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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
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
    
    public static JButton navigationButton = new JButton("Graphs");
    JButton minimize = new JButton("-");
    final JButton close = new JButton("X");
    
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
        //Make textField get the focus whenever frame is activated.
        
        
        
    }
    private void addCardsToDeck(MainPanelDriver driver){

        cards.add(driver.mainPanel, "Main");
        cards.add(graphPanel, "Graph");
    }
    private void initializeButtons(){
        navigationButton.setBackground(darkBlue);
        navigationButton.setForeground(Color.WHITE);
        navigationButton.setBorderPainted(false);
        navigationButton.setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        navigationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(navigationButton.getText().equals("Back")){
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "Main");
                    navigationButton.grabFocus();
                    navigationButton.requestFocus();
                    navigationButton.setText("Graph");
                } else {
                    CardLayout cl = (CardLayout)(cards.getLayout());
                    cl.show(cards, "Graph");
                    navigationButton.grabFocus();
                    navigationButton.requestFocus();
                    navigationButton.setText("Back");
                }
                
            }
        });
        
        minimize.setBackground(darkBlue);
        minimize.setForeground(Color.WHITE);
        minimize.setBorderPainted(false);
        minimize.setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM_LARGE.value));
        minimize.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(frame.ICONIFIED);
            }
        });
        
        close.setBackground(darkBlue);
        close.setForeground(Color.WHITE);
        close.setBorderPainted(false);
        close.setFont(new Font(FONT_FACE, FONT_STYLE, FONT_SIZE.MEDIUM.value));
        close.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    private void addComponentToPane(Container pane) throws IOException {
        initializeButtons(); 
        
        timer = new Timer(60000, this);
        timer.setInitialDelay(1000);
        timer.start(); 
        
        driver = new MainPanelDriver();
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck(driver);

        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                driver.mainPanel.button[driver.mainPanel.nextButton].grabFocus();
                driver.mainPanel.button[driver.mainPanel.nextButton].requestFocus();
            }
        });
        
        JPanel topSub = new JPanel();
        topSub.setLayout(new BorderLayout());
        topSub.setBackground(WeatherStation.BACKGROUND_COLOUR);
        topSub.add(driver.mainPanel.labelPanel[driver.mainPanel.dateTime], BorderLayout.CENTER);
        topSub.add(minimize, BorderLayout.LINE_END);
        
        JPanel controls = new JPanel();
        controls.setLayout(new BorderLayout());
        controls.setBackground(gioBlue);
        controls.add(topSub, BorderLayout.CENTER);
        controls.add(close, BorderLayout.LINE_END);
        
        JPanel navigationPanel = new JPanel();
        navigationPanel.add(navigationButton);
        navigationPanel.setBackground(WeatherStation.BACKGROUND_COLOUR);
        
        pane.add(controls , BorderLayout.NORTH);
        //pane.add(driver.mainPanel.labelPanel[driver.mainPanel.dateTime], BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
        pane.add(navigationPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            driver.initializeArrays();
            driver.displayOutputToLabel();
        } catch (IOException ex) {}
    }
}