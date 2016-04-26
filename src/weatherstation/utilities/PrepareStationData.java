package weatherstation.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import weatherstation.WeatherStation;

/**
 * Program info: 
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 
 */
public class PrepareStationData {

    static TwoDimentionalArrayList<String> stationdata = new TwoDimentionalArrayList<String>();
    static int row = 0;
    static int column = 0;
    static String s;
    
    public static void removeNthLine(String f, int toRemove) throws IOException {
        File directory = new File("C:/Users/David/Downloads");
        File tmp = File.createTempFile("tmp", null, directory);


        BufferedReader br = new BufferedReader(new FileReader(f));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
        
        for (int i = 0; i < toRemove; i++){
            if(i < 4){
                System.out.println((String.format("%s%n", br.readLine())));
            } else {
                bw.write(String.format("%s%n", br.readLine()));
            }
            
        }

        br.readLine();
        /*
        String l;
        while (null != (l = br.readLine()))
            bw.write(String.format("%s%n", l));
        */
        br.close();
        bw.close();

        File oldFile = new File(f);

        if (oldFile.delete())
            tmp.renameTo(oldFile);
        System.out.println("removed nth ");

    }
    
        public static void parseWords(){
            Scanner sc2 = null;
            try {
                sc2 = new Scanner(new File("C:/Users/David/Downloads/stations.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();  
            }
            while (sc2.hasNextLine()) {
                    Scanner s2 = new Scanner(sc2.nextLine());
                while (s2.hasNext()) {
                    s = s2.next();
                    System.out.println(s);
                    stationdata.addToInnerArray(column, s);
                    WeatherStation.stationData[row][column] = s;
                    if(column < WeatherStation.NUMBER_OF_STATION_COLUMNS - 1){
                        column++;
                    } else {
                        column = 0;
                        row++;
                    }
                }
            }
            System.out.println("complete");
        }
}

class TwoDimentionalArrayList<T> extends ArrayList<ArrayList<T>> {
    public void addToInnerArray(int index, T element) {
        while (index >= this.size()) {
            this.add(new ArrayList<T>());
        }
        this.get(index).add(element);
    }

    public void addToInnerArray(int index, int index2, T element) {
        while (index >= this.size()) {
            this.add(new ArrayList<T>());
        }

        ArrayList<T> inner = this.get(index);
        while (index2 >= inner.size()) {
            inner.add(null);
        }

        inner.set(index2, element);
    }
}
