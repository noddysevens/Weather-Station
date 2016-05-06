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
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import weatherstation.MainPanelDriver;
import static weatherstation.MainPanelDriver.dateTime;
import weatherstation.WeatherStation;
import static weatherstation.WeatherStation.cards;
import static weatherstation.WeatherStation.frame;
import static weatherstation.WeatherStation.progressPanel;
import static weatherstation.panels.MainDashboardPanel.topLabel;
import static weatherstation.panels.MultiPostCodeSelectPanel.jComboBox1;
import weatherstation.utilities.CollectInput;
import static weatherstation.utilities.CollectInput.validWMO;

public class PostcodePanel extends JPanel {
    public static JButton goButton;
    private JLabel title;
    private JLabel instructions;
    public static JTextField postcodeInputField;                 
    private Point point = new Point();
    private int maxStringLength;
    public static boolean firstRun = true;
    
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
        WeatherStation.navigationPanel.setVisible(false);
        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl = (CardLayout)(cards.getLayout());
        progressPanel = new CircularProgressBar("Preparing Data", 10);
        cards.add(progressPanel, "Progress");
        cl.show(cards, "Progress");
        
        
        if(firstRun){
            initialisingAction();
            firstRun = false;
        } else{
            repeatingAction();
        }
    }                                           

    private void goButtonActionPerformed(ActionEvent evt) {     
        WeatherStation.navigationPanel.setVisible(false);
        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl = (CardLayout)(cards.getLayout());
        progressPanel = new CircularProgressBar("Preparing Data", 10);
        cards.add(progressPanel, "Progress");
        cl.show(cards, "Progress");
        
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
        if(validWMO.size() > 1){
            CircularProgressBar.timer.stop();
            CardLayout cl = (CardLayout)(cards.getLayout());
            //
            ArrayList<String> stationNames = new ArrayList<>();
            for(String name : CollectInput.stationName){
                stationNames.add(name);
            }

            String[] comboBoxValues = new String[stationNames.size()];

            for (int i = 0; i < stationNames.size(); i++){
                comboBoxValues[i] = stationNames.get(i);
            }
            ComboBoxModel model = new DefaultComboBoxModel(comboBoxValues);

            jComboBox1.setModel(model);
            //
            cl.show(cards, "postCodeSelect");
            WeatherStation.navigationPanel.setVisible(false);
            MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        } else {
            
            CollectInput.getObservations(validWMO.get(0));
            MainPanelDriver.initializeArrays();

            if(CollectInput.stationName.get(0).length() > 20){
                CollectInput.stationName.set(0, CollectInput.stationName.get(0).substring(0, 20));
            }

            MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(0) + " at ");
        }
        
    }
    
    private void repeatingAction(){

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
        
        if(validWMO.size() > 1){
            CircularProgressBar.timer.stop();
            CardLayout cl = (CardLayout)(cards.getLayout());
            //
            ArrayList<String> stationNames = new ArrayList<>();
            for(String name : CollectInput.stationName){
                stationNames.add(name);
            }

            String[] comboBoxValues = new String[stationNames.size()];

            for (int i = 0; i < stationNames.size(); i++){
                comboBoxValues[i] = stationNames.get(i);
            }
            ComboBoxModel model = new DefaultComboBoxModel(comboBoxValues);

            jComboBox1.setModel(model);
            //
            cl.show(cards, "postCodeSelect");
            WeatherStation.navigationPanel.setVisible(false);
            MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        } else {
            CollectInput.getObservations(validWMO.get(0));
            MainPanelDriver.initializeArrays();

            if(CollectInput.stationName.get(0).length() > 20){
                CollectInput.stationName.set(0, CollectInput.stationName.get(0).substring(0, 20));
            }

            MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(0) + " at ");
        }

    }
}
