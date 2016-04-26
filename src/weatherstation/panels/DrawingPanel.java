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
 * Program info: 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 
 */
public class DrawingPanel extends JPanel {
    private final int DIVISIONS_PER_LABEL = 4;
    private final int width = 550;
    private final int height = 500;
    private final int dateTime = 14;
    private final int padding = 30;
    private final int labelPadding = 30;
    private final int pointWidth = 4;
    private final int numberYDivisions = 12;
    
    private final Color lineColor = new Color(44, 102, 230, 180);
    private final Color pointColor = new Color(100, 100, 100, 180);
    private final Color gridColor = new Color(200, 200, 200, 200);
    
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    
    public  List<Double> values;
    public  List<String> times;
    
    public int selectedIndex = 0;
    public String selectedLabel = "";
    
    public enum FONT_SIZE {SMALL(10), MEDIUM(20), MEDIUM_LARGE(30), LARGE(45);
        private int value;
        private FONT_SIZE(int value) {
                this.value = value;
        }
    };
    
    public DrawingPanel(){
        setPreferredSize(new Dimension(width, height));
        setBackground(WeatherStation.BACKGROUND_COLOUR);
        values = new ArrayList<>();
        times = new ArrayList<>();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        for(int i = 0; i < WeatherStation.weatherMeasurements.length; i++){
            if(selectedLabel.equals(WeatherStation.weatherMeasurements[i])){
                selectedIndex = i;
            }
        }

        values.clear();
        times.clear();
        for(int k = 49; k >=0; k--){
            values.add(Double.parseDouble(MainPanelDriver.bomData[k][selectedIndex]));
            times.add(MainPanelDriver.bomData[k][dateTime].substring(3));
        }
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Point> graphPoints = getGraphPoints();

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        createHashAndGridForY(g2);

        // and for x axis
        createHashAndGridForX(g2);

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

        createLinesAndPoints(g2, graphPoints);
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(width, heigth);
//    }
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
        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (values.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());
        
        List<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((getMaxScore() - values.get(i)) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }
        return graphPoints;
    }
    
    private void createHashAndGridForY(Graphics2D g2){
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;
            if (values.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + WeatherStation.dataUnits[selectedIndex];
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
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (values.size() - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = getHeight() - padding - labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((values.size() / 24.0)) + 0)) == 0) {  
                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
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
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
    }
}
