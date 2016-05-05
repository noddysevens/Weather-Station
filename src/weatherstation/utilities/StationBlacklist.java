package weatherstation.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class info: This class creates and maintains a list of stations that have 
 * Null observations and are not suited to use in this program. 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 */
public class StationBlacklist {

    static File file;
    static ArrayList<Integer> list;
    
    public StationBlacklist() {
        file = new File("blacklist.txt");
        list = new ArrayList<>();
        readBlacklist();
    }
    
    public ArrayList<Integer> getBlacklist(){
        return list;
    }
    private void readBlacklist(){
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                
                try {
                    int WMO = Integer.parseInt(line.trim());   
                    list.add(WMO);
                } catch (NumberFormatException e) {
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException ex) {
            System.err.println("ERROR reading blacklist from file");
        }
    }
    public static void addToBlacklist(int WMO){
        list.add(WMO);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter fileOutput = new BufferedWriter(new FileWriter(file, true));
            fileOutput.newLine();
            fileOutput.append("" + WMO);
            fileOutput.close();

        } catch (IOException ex1) {
            System.out.printf("ERROR writing to file: %s\n", ex1);
        }
    }
    public static boolean isOnBlacklist(int WMO){
        boolean isOnList = false;
        for(int item : list){
            if(item == WMO){
                isOnList = true;
            }
        }
        return isOnList;
    }
}
