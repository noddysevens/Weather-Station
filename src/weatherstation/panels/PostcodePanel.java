package weatherstation.panels;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import weatherstation.MainPanelDriver;
import static weatherstation.MainPanelDriver.dateTime;
import static weatherstation.WeatherStation.cards;
import weatherstation.WeatherStation;
import static weatherstation.WeatherStation.frame;
import static weatherstation.panels.MainDashboardPanel.topLabel;
import weatherstation.utilities.CollectInput;

public class PostcodePanel extends JPanel {
    public static JButton goButton;
    private JLabel title;
    private JLabel instructions;
    public static JTextField postcodeInputField;                 
    private Point point = new Point();
    private int maxStringLength;
    public boolean firstRun = true;
    
    public PostcodePanel() {
        initComponents();
    }
                   
    private void initComponents() {

        title = new JLabel();
        postcodeInputField = new JTextField();
        goButton = new JButton();
        instructions = new JLabel();
        
        maxStringLength = 20;

        setBackground(new Color(0, 86, 150));
        setToolTipText("");
        setPreferredSize(new Dimension(650, 600));
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        title.setBackground(new Color(236, 244, 250));
        title.setFont(new Font("Verdana", 1, 30)); 
        title.setForeground(new Color(255, 255, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Australian Weather Station");

        postcodeInputField.setBackground(new Color(236, 244, 250));
        postcodeInputField.setFont(new Font("Verdana", 3, 24)); 
        postcodeInputField.setForeground(new Color(153, 153, 153));
        postcodeInputField.setText("Postcode: eg. 3066");
        postcodeInputField.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        postcodeInputField.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        postcodeInputField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        goButton.setBackground(new Color(0, 86, 150));
        goButton.setFont(new Font("Verdana", 1, 24)); 
        goButton.setForeground(new Color(255, 255, 255));
        goButton.setText("GO");
        goButton.setFocusPainted(false);
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });

        instructions.setFont(new Font("Verdana", 1, 18)); 
        instructions.setForeground(new Color(255, 255, 255));
        instructions.setText("Please Enter your Postcode");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(postcodeInputField, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(goButton, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                    .addComponent(instructions, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(115, Short.MAX_VALUE)
                .addComponent(title)
                .addGap(78, 78, 78))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(title, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
                .addComponent(instructions, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(postcodeInputField)
                    .addComponent(goButton, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                .addGap(177, 177, 177))
        );
    }                      

    private void jTextField1ActionPerformed(ActionEvent evt) {                                            
        if(firstRun){
            initialisingAction();
            firstRun = false;
        } else{
            repeatingAction();
        }
    }                                           

    private void goButtonActionPerformed(ActionEvent evt) {                                         
        if(firstRun){
            initialisingAction();
            firstRun = false;
        } else{
            repeatingAction();
        }
    }                                        

    private void jTextField1MouseClicked(MouseEvent evt) {                                         
        postcodeInputField.setText("");
    }                                        

    private void formMouseDragged(MouseEvent evt) {                                  
        Point p = frame.getLocation();
        frame.setLocation(p.x + evt.getX() - point.x,
                p.y + evt.getY() - point.y);
    }  
    
    private void formMousePressed(MouseEvent evt) {                                  
        point.x = evt.getX();
        point.y = evt.getY();
    }
    
    private void initialisingAction(){
        WeatherStation.conditionsTimePanel.add(MainDashboardPanel.labelPanel[dateTime]);
        WeatherStation.frame.getContentPane().add(WeatherStation.navigationPanel, BorderLayout.SOUTH);
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "Main");
        WeatherStation.button[WeatherStation.NAVIGATION_BUTTON].grabFocus();
        WeatherStation.button[WeatherStation.NAVIGATION_BUTTON].requestFocus();
        WeatherStation.button[WeatherStation.NAVIGATION_BUTTON].setText("Graph");
        
        String postCode = postcodeInputField.getText();
        
        int codeUp = Integer.parseInt(postCode);
        int codeDown = codeUp;
        CollectInput.getPostcodeInfo(postCode);
        while(CollectInput.validWMO.isEmpty()){
            codeUp++;
            CollectInput.getPostcodeInfo(String.valueOf(codeUp));
            codeDown--;
            CollectInput.getPostcodeInfo(String.valueOf(codeDown));
        }
        
        CollectInput.getInput();
        MainPanelDriver.initializeArrays();

        
        if(CollectInput.stationName.get(0).length() > maxStringLength){
            CollectInput.stationName.set(0, CollectInput.stationName.get(0).substring(0, maxStringLength));
        }
        
        MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(0) + " at ");
    }
    
    private void repeatingAction(){
        WeatherStation.navigationPanel.setVisible(true);
        MainDashboardPanel.labelPanel[dateTime].setVisible(true);
        
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, "Main");
        WeatherStation.button[WeatherStation.NAVIGATION_BUTTON].grabFocus();
        WeatherStation.button[WeatherStation.NAVIGATION_BUTTON].requestFocus();
        WeatherStation.button[WeatherStation.NAVIGATION_BUTTON].setText("Graph");
        
        String postCode = postcodeInputField.getText();
        
        int codeUp = Integer.parseInt(postCode);
        int codeDown = codeUp;
        CollectInput.getPostcodeInfo(postCode);
        while(CollectInput.validWMO.isEmpty()){
            codeUp++;
            CollectInput.getPostcodeInfo(String.valueOf(codeUp));
            codeDown--;
            CollectInput.getPostcodeInfo(String.valueOf(codeDown));
        }
        
        CollectInput.getInput();
        MainPanelDriver.initializeArrays();

        if(CollectInput.stationName.get(0).length() > maxStringLength){
            CollectInput.stationName.set(0, CollectInput.stationName.get(0).substring(0, maxStringLength));
        }
        
        MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(0) + " at ");
    }
}
