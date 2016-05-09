package weatherstation.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import weatherstation.MainPanelDriver;
import static weatherstation.MainPanelDriver.dateTime;
import weatherstation.WeatherStation;
import static weatherstation.WeatherStation.cards;
import static weatherstation.WeatherStation.postCodePanel;
import static weatherstation.WeatherStation.progressPanel;
import static weatherstation.panels.MainDashboardPanel.topLabel;
import weatherstation.utilities.CollectInput;
import static weatherstation.utilities.CollectInput.validWMO;
import weatherstation.utilities.HomePostCodeStorage;

/**
 * Class info: This class allows the user to choose which station when there is
 * more than one option
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class MultiPostCodeSelectPanel extends JPanel {
    private JButton jButton1;
    public static JComboBox jComboBox1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private int selectedIndex = 0;
    public MultiPostCodeSelectPanel() {
        initComponents();
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
        
        selectedIndex = WeatherStation.postCodeSelectPanel.jComboBox1.getSelectedIndex();
        CollectInput.getObservations(validWMO.get(selectedIndex));
        HomePostCodeStorage.setCurrentPostcode(postCodePanel.postcodeInputField.getText());
        HomePostCodeStorage.setCurrentWMO(validWMO.get(selectedIndex));
        HomePostCodeStorage.setCurrentStationName(CollectInput.stationName.get(selectedIndex));
        
        MainPanelDriver.initializeArrays();

        if(HomePostCodeStorage.getCurrentStationName().length() > 20){
            HomePostCodeStorage.setCurrentStationName(HomePostCodeStorage.getCurrentStationName().substring(0, 20));
        }
        
        MainDashboardPanel.label[topLabel].setText(HomePostCodeStorage.getCurrentStationName() + " at ");
    }                                        

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        jButton1ActionPerformed(evt);
    }                       
}

