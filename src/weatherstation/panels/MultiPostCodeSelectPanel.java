package weatherstation.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import weatherstation.MainPanelDriver;
import static weatherstation.MainPanelDriver.dateTime;
import weatherstation.WeatherStation;
import static weatherstation.WeatherStation.cards;
import static weatherstation.WeatherStation.progressPanel;
import static weatherstation.panels.MainDashboardPanel.topLabel;
import weatherstation.utilities.CollectInput;
import static weatherstation.utilities.CollectInput.validWMO;

/**
 *
 * @author David
 */
public class MultiPostCodeSelectPanel extends JPanel {
    private JButton jButton1;
    public static JComboBox jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private ArrayList<String> stationNames;
    public MultiPostCodeSelectPanel() {
        initComponents();
        stationNames = new ArrayList<>();
    }
    
    private void initComponents() {
        

        
        jLabel1 = new JLabel();
        jComboBox1 = new JComboBox();
        jLabel2 = new JLabel();
        jButton1 = new JButton();

        setBackground(new Color(0, 86, 150));

        jLabel1.setFont(new Font("Verdana", 1, 18));
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("There are multiple stations for this post code");

        jComboBox1.setFont(new Font("Verdana", 1, 14));
        jComboBox1.setForeground(Color.gray);
        jComboBox1.setBackground(Color.white);
        jComboBox1.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new Font("Verdana", 1, 14)); 
        jLabel2.setForeground(new Color(255, 255, 255));
        jLabel2.setText("Please select which station to view:");

        jButton1.setBackground(new Color(0, 86, 150));
        jButton1.setFont(new Font("Verdana", 1, 18));
        jButton1.setForeground(new Color(255, 255, 255));
        jButton1.setText("GO");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(74, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel1)
                .addGap(139, 139, 139)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(jComboBox1))
                .addContainerGap(272, Short.MAX_VALUE))
        );
    }                     

    private void jButton1ActionPerformed(ActionEvent evt) {                                         
        WeatherStation.navigationPanel.setVisible(false);
        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl = (CardLayout)(cards.getLayout());
        progressPanel = new CircularProgressBar("Preparing Data", 10);
        cards.add(progressPanel, "Progress");
        cl.show(cards, "Progress");
        
        int index = WeatherStation.postCodeSelectPanel.jComboBox1.getSelectedIndex();
        CollectInput.getObservations(validWMO.get(index));
        MainPanelDriver.initializeArrays();
        
        if(CollectInput.stationName.get(0).length() > 20){
            CollectInput.stationName.set(0, CollectInput.stationName.get(index).substring(0, 20));
        }
        
        MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(index) + " at ");
        
        for(String name : CollectInput.stationName){
            stationNames.add(name);
        }
        
        String[] comboBoxValues = new String[stationNames.size()];
        
        for (int i = 0; i < stationNames.size(); i++){
            comboBoxValues[i] = stationNames.get(i);
        }
        ComboBoxModel model = new DefaultComboBoxModel(comboBoxValues);
        
        jComboBox1.setModel(model);
    }                                        

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        WeatherStation.navigationPanel.setVisible(false);
        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl = (CardLayout)(cards.getLayout());
        progressPanel = new CircularProgressBar("Preparing Data", 10);
        cards.add(progressPanel, "Progress");
        cl.show(cards, "Progress");
        
        WeatherStation.navigationPanel.setVisible(false);
        MainDashboardPanel.labelPanel[dateTime].setVisible(false);
        
        int index = WeatherStation.postCodeSelectPanel.jComboBox1.getSelectedIndex();
        CollectInput.getObservations(validWMO.get(index));
        MainPanelDriver.initializeArrays();
        
        if(CollectInput.stationName.get(0).length() > 20){
            CollectInput.stationName.set(0, CollectInput.stationName.get(index).substring(0, 20));
        }
        
        MainDashboardPanel.label[topLabel].setText(CollectInput.stationName.get(index) + " at ");
        
        for(String name : CollectInput.stationName){
            stationNames.add(name);
        }
        
        String[] comboBoxValues = new String[stationNames.size()];
        
        for (int i = 0; i < stationNames.size(); i++){
            comboBoxValues[i] = stationNames.get(i);
        }
        ComboBoxModel model = new DefaultComboBoxModel(comboBoxValues);
        
        jComboBox1.setModel(model);
        
    }                       
}

