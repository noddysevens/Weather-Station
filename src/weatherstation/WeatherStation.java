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
import javax.json.JsonObject;
import javax.swing.JFrame;
import javax.swing.JPanel;
import weatherstation.utilities.CollectInput;

/**
 *
 * @author David
 */
public class WeatherStation {
    private static int HEIGHT = 600;
    private static int WIDTH = 650;

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
    public static Color BACKGROUND_COLOUR = lightGrayBackground;
        
    public static JPanel cards;
    
    public static DrawingPanel drawingPanel = new DrawingPanel();
    
    static JFrame frame = new JFrame("Json Test");
    
    private static Point point = new Point();
    
    //public static JPanel card1 = new JPanel();
    //public static JPanel card2 = new JPanel();
    //private JPanel main = new JPanel();
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

    private void addCardsToDeck(MainPanelDriver driver){
        cards.add(driver.mainPanel, "Main");
    }
    public void addComponentToPane(Container pane) throws IOException {
        MainPanelDriver driver = new MainPanelDriver();
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck(driver);
        
        pane.add(driver.mainPanel.labelPanel[driver.mainPanel.dateTime], BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
    }
}