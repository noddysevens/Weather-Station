package weatherstation.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import weatherstation.MainPanelDriver;
import weatherstation.WeatherStation;

/**
 * Class info: This class creates and modifies the graph sub panel
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class DrawingPanel extends JPanel {
    
    private List<String> times;
    private List<Double> values;
    
    public int selectedIndex = 0;
    public String selectedLabel = "";
    
    private final int PADDING = 30;
    private final int POINT_WIDTH = 4;
    private final int PANEL_WIDTH = 550;
    private final int PANEL_HEIGHT = 500;
    private final int LABEL_PADDING = 30;
    private final int DATE_TIME_INDEX = 14;
    private final int DIVISIONS_PER_LABEL = 4;
    private final int NUMBER_OF_Y_DIVISIONS = 12;
    
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    
    private final Color LINE_COLOUR = new Color(44, 102, 230, 180);
    private final Color GRID_COLOUR = new Color(200, 200, 200, 200);
    private final Color POINT_COLOUR = new Color(100, 100, 100, 180);
    
    private String[] weatherMeasurements = {"Sort Order", "Air Temp","Apparent Temp"
            ,"Dew Point","Relative Humidity","Delta T","Wind Direction"
            ,"Wind Speed(km/h)", "Wind Gusts(km/h)", "Wind Speed(knots)"
            , "Wind Gusts(knots)", "Pressure(Qnh)", "Pressure(MSL)","Rain Since"
            ,"Date Time"};
    
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        private int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public DrawingPanel(){
        values = new ArrayList<>();
        times = new ArrayList<>();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(WeatherStation.BACKGROUND_COLOUR);
        
        for(int i = 0; i < weatherMeasurements.length; i++){
            if(selectedLabel.equals(weatherMeasurements[i])){
                selectedIndex = i;
            }
        }

        values.clear();
        times.clear();
        for(int k = 49; k >=0; k--){
            values.add(Double.parseDouble(MainPanelDriver.bomData[k][selectedIndex]));
            times.add(MainPanelDriver.bomData[k][DATE_TIME_INDEX].substring(3));
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Point> graphPoints = getGraphPoints();

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(PADDING + LABEL_PADDING, PADDING, getWidth() - (2 * PADDING) - LABEL_PADDING, getHeight() - 2 * PADDING - LABEL_PADDING);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        createHashAndGridForY(g2);

        // and for x axis
        createHashAndGridForX(g2);

        // create x and y axes 
        g2.drawLine(PADDING + LABEL_PADDING, getHeight() - PADDING - LABEL_PADDING, PADDING + LABEL_PADDING, PADDING);
        g2.drawLine(PADDING + LABEL_PADDING, getHeight() - PADDING - LABEL_PADDING, getWidth() - PADDING, getHeight() - PADDING - LABEL_PADDING);

        createLinesAndPoints(g2, graphPoints);
    }
    
    private double getMinScore() {
        double minScore = Double.MAX_VALUE;
        for (Double score : values) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMaxScore() {
        double maxScore = Double.MIN_VALUE;
        for (Double score : values) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }
    
    //convert raw data into a list of graph points
    private List<Point> getGraphPoints(){
        double xScale = ((double) getWidth() - (2 * PADDING) - LABEL_PADDING) / (values.size() - 1);
        double yScale = ((double) getHeight() - 2 * PADDING - LABEL_PADDING) / (getMaxScore() - getMinScore());
        
        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            int x1 = (int) (i * xScale + PADDING + LABEL_PADDING);
            int y1 = (int) ((getMaxScore() - values.get(i)) * yScale + PADDING);
            graphPoints.add(new Point(x1, y1));
        }
        return graphPoints;
    }
    
    private void createHashAndGridForY(Graphics2D g2){
        for (int i = 0; i < NUMBER_OF_Y_DIVISIONS + 1; i++) {
            int x0 = PADDING + LABEL_PADDING;
            int x1 = POINT_WIDTH + PADDING + LABEL_PADDING;
            int y0 = getHeight() - ((i * (getHeight() - PADDING * 2 - LABEL_PADDING)) / NUMBER_OF_Y_DIVISIONS + PADDING + LABEL_PADDING);
            int y1 = y0;
            if (values.size() > 0) {
                g2.setColor(GRID_COLOUR);
                g2.drawLine(PADDING + LABEL_PADDING + 1 + POINT_WIDTH, y0, getWidth() - PADDING, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / NUMBER_OF_Y_DIVISIONS)) * 100)) / 100.0 + WeatherStation.dataUnits[selectedIndex];
                g2.setFont(new Font("Century Gothic", Font.PLAIN, 11));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }
    }
    
    private void createHashAndGridForX(Graphics2D g2){
        int timeCounter = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.size() > 1) {
                int x0 = i * (getWidth() - PADDING * 2 - LABEL_PADDING) / (values.size() - 1) + PADDING + LABEL_PADDING;
                int x1 = x0;
                int y0 = getHeight() - PADDING - LABEL_PADDING;
                int y1 = y0 - POINT_WIDTH;
                if ((i % ((int) ((values.size() / 24.0)) + 0)) == 0) {  
                    g2.drawLine(x0, getHeight() - PADDING - LABEL_PADDING - 1 - POINT_WIDTH, x1, PADDING);
                    g2.setColor(Color.BLACK);
                    if(i % DIVISIONS_PER_LABEL == 0){    
                        String xLabel = times.get(timeCounter);
                        g2.setFont(new Font("Century Gothic", Font.PLAIN, 9));
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(xLabel);
                        g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                        if(timeCounter < 48){
                            timeCounter += 4;
                        }
                        
                    }
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }
    }
    
    private void createLinesAndPoints(Graphics2D g2, List<Point> graphPoints){
        Stroke oldStroke = g2.getStroke();
        g2.setColor(LINE_COLOUR);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(POINT_COLOUR);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - POINT_WIDTH / 2;
            int ovalW = POINT_WIDTH;
            int ovalH = POINT_WIDTH;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }
}
