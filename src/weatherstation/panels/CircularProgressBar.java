package weatherstation.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import static weatherstation.MainPanelDriver.dateTime;
import weatherstation.WeatherStation;
import static weatherstation.WeatherStation.cards;

/**
 * Program info: This class creates the progress circle sub panel
 * Credit to: Harry Joy for the source code inspiration
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class CircularProgressBar extends JPanel{
    
    private int prgValue = 0;
    public static Timer timer;
    private String displayString;
    private static int delay = 15;
    private final int WIDTH = 200;
    private final int HEIGHT = 200;
    private static final double FRACTION = 0.9;
    private final static int MAX_PROGRESS_AMOUNT = 100;

    public CircularProgressBar() {
          timer = new Timer(delay, new MyChangeListener());
          timer.setInitialDelay(100);
          timer.start();
          displayString = "Setting Home PostCode";
    }
    
    public CircularProgressBar(String display, int delayTime) {
        delay = delayTime;
        timer = new Timer(delay, new MyChangeListener());
        timer.setInitialDelay(100);
        timer.start();
        displayString = display;
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.setBackground(new Color(0,86,150));
        g.setColor(new Color(0,86,150));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setBackground(new Color(0,86,150));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (prgValue <= MAX_PROGRESS_AMOUNT) {
            g.setColor(Color.WHITE);
            int angle = -(int) (((float) prgValue / MAX_PROGRESS_AMOUNT) * 360);
            int x = (getWidth() / 2) - (WIDTH / 2);
            int y = (getHeight() / 2) - (HEIGHT / 2);
            g.fillArc(x, y, WIDTH, HEIGHT, 90, angle);
            g2.setColor(new Color(0,86,150));
            x = (getWidth() / 2) - (int)(WIDTH * FRACTION / 2);
            y = (getHeight() / 2) - (int)(HEIGHT * FRACTION / 2);
            g.fillArc(x, y, (int)(WIDTH * FRACTION), (int)(HEIGHT * FRACTION), 92, angle);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Century Gothic", Font.BOLD, 12));
        FontMetrics metrics = g.getFontMetrics();
        int labelWidth = metrics.stringWidth(displayString);
        g.drawString(displayString, getWidth() / 2 - labelWidth / 2, getHeight() / 2 + 150);
    }
    
    private class MyChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            prgValue++;
            repaint();
            if (prgValue >= MAX_PROGRESS_AMOUNT){
                timer.stop();
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "Main");
                WeatherStation.navigationPanel.setVisible(true);
                MainDashboardPanel.labelPanel[dateTime].setVisible(true);
            }
        }
    }
}
