package weatherstation;

import weatherstation.panels.DrawingPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author David
 */
public class WeatherStation {
    private static int HEIGHT = 600;
    private static int WIDTH = 650;
    
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
    }
    private void addComponentToPane(Container pane) throws IOException {
        MainPanelDriver driver = new MainPanelDriver();
        
        cards = new JPanel(new CardLayout());
        addCardsToDeck(driver);
        
        pane.add(driver.mainPanel.labelPanel[driver.mainPanel.dateTime], BorderLayout.NORTH);
        pane.add(cards, BorderLayout.CENTER);
    }
}