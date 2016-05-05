package weatherstation.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import weatherstation.panels.MainDashboardPanel;

/**
 * Program info: This class stores and modifies the home postcode
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class HomePostCodeStorage {
    static File file;
    static String homePostcode;
    
    public HomePostCodeStorage(){
        file = new File("homePostCode.txt");
        readHomePostCode();
    }
    
    public static String getHomePostcode(){
        return homePostcode;
    }
    private void readHomePostCode(){
        try {
            if (!file.exists()) {
                file.createNewFile();
                setHomePostcode("4350");
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                homePostcode = line.trim();
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            homePostcode = "none";
        }
        
    }
    public static void setHomePostcode(String postcode){
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter(file, true));
            fileOutput.newLine();
            fileOutput.append("" + postcode);
            fileOutput.close();

        } catch (IOException ex1) {
            System.out.printf("ERROR writing to file: %s\n", ex1);
        }
        
    }
}
