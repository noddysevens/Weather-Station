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
 * Program info: This class processes the Stations.txt file into an arraylist 
 * for further processing. This allows the stations with JSON files to be 
 * extracted in the next update.
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 
 */
public class PrepareStationData {

    static ArrayList<String> stationDataColumns = new ArrayList<>();

    
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
        
        br.close();
        bw.close();

        File oldFile = new File(f);

        if (oldFile.delete()){
            tmp.renameTo(oldFile);
        }

        System.out.println("removed nth ");
        }
    
    public static void parseWords(){
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("C:/Users/David/Downloads/stations.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
        ArrayList<String> values = new ArrayList<>();

        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                values.add(s2.next());
            }
            try{
            //Process "Site" column
            stationDataColumns.add(values.get(0));
            values.remove(0);

            //Process "Dist" column
            boolean twoDigitAndAlpha = false;
            String current = values.get(0);
            if(current.length() == 3 && Character.isDigit(current.charAt(0)) 
                    && Character.isDigit(current.charAt(1)) 
                    && Character.isAlphabetic(current.charAt(2))){ 
                twoDigitAndAlpha = true;
            }

            boolean twoDigitAndTwoAlpha = false;
            if(current.length() == 4 && Character.isDigit(current.charAt(0)) 
                    && Character.isDigit(current.charAt(1)) 
                    && Character.isAlphabetic(current.charAt(2)) 
                    && Character.isAlphabetic(current.charAt(3))){ 
                twoDigitAndTwoAlpha = true;
            }

            boolean twoDigitAndNextIsAlpha = false;
            if(current.length() == 2 && Character.isDigit(current.charAt(0)) 
                    && Character.isDigit(current.charAt(1)) 
                    && Character.isAlphabetic(values.get(1).charAt(0))){ 
                twoDigitAndNextIsAlpha = true;
            }


            if(twoDigitAndTwoAlpha || twoDigitAndAlpha || twoDigitAndNextIsAlpha){
                values.remove(0);
            }

            //Process "Station Name" column
            boolean firstWord = true;
            boolean notNumeric = true;
            int i = 0;
            while(notNumeric){
                while(i < values.get(0).length()){
                    if(Character.isDigit(values.get(0).charAt(i))){
                        notNumeric = false;
                        if(values.get(0).length() < 4){
                            notNumeric = true;
                            break;
                        } else if(values.get(0).indexOf(".") > 0){
                            notNumeric = true;
                            break;
                        } else if(values.get(0).charAt(0) == '(' 
                                || values.get(0).charAt(values.get(0).length() - 1) == ')'){
                            notNumeric = true;
                            break;
                        } else if(values.get(0).length() == 4){
                            int j = 0;
                            while(j < values.get(0).length()){
                                if(Character.isAlphabetic(values.get(0).charAt(j))){
                                    notNumeric = true;
                                    break;
                                } 
                                j++;
                            }
                            if(!notNumeric && Integer.parseInt(values.get(0)) < 1750){
                                notNumeric = true;
                                break;
                            }
                        } else if(values.get(0).length() > 4){
                            notNumeric = true;
                            break;
                        }
                    }
                    i++;
                }
                i = 0;
                if(notNumeric && firstWord){
                    stationDataColumns.add(values.get(0));
                    values.remove(0);
                    firstWord = false;
                } else if(notNumeric) {
                    stationDataColumns.set(stationDataColumns.size() - 1
                            , stationDataColumns.get(stationDataColumns.size() - 1) 
                            + " " + values.get(0));
                    values.remove(0);
                }

            }

            //Process "Start" column
            if(Integer.parseInt(values.get(0)) > 1750){
                stationDataColumns.add(values.get(0));
                values.remove(0);
            }

            //Process "End" column
            notNumeric = true;
            i = 0;
            while(notNumeric && i < values.get(0).length()){
                if(Character.isDigit(values.get(0).charAt(i))){
                    notNumeric = false;
                    if(values.get(0).length() < 4){
                        notNumeric = true;
                    }
                }
                i++;
            }

            if(notNumeric){
                stationDataColumns.add(values.get(0));
                values.remove(0);
            } else if(Integer.parseInt(values.get(0)) > 1750){
                stationDataColumns.add(values.get(0));
                values.remove(0);
            }


            //Process "Lat" column
            stationDataColumns.add(values.get(0));
            values.remove(0);

            //Process "Lon" column
            stationDataColumns.add(values.get(0));
            values.remove(0);

            //Process "Source" column
            //check for and remove MAP and DEM values
            if(values.get(0).equals("MAP")){
                for(i = 0; i < 3; i++){
                    values.remove(0);
                }
                stationDataColumns.add("...");
            } else if(values.get(0).equals("DEM")){
                for(i = 0; i < 4; i++){
                    values.remove(0);
                }
                stationDataColumns.add("...");
            }  else {
                stationDataColumns.add(values.get(0));
                values.remove(0);
            }
            
            //Process "State" column
            stationDataColumns.add(values.get(0));
            values.remove(0);

            //Process "Height" column
            stationDataColumns.add(values.get(0));
            values.remove(0);

            //Process "Bar_ht" column
            stationDataColumns.add(values.get(0));
            values.remove(0);

            //Process "WMO" column
            stationDataColumns.add(values.get(0));
            values.remove(0);
            
            //add column array to row and clear
            WeatherStation.stationDataRows.add(new ArrayList<String>(stationDataColumns));
            stationDataColumns.clear();

            } catch(Exception e){
                System.out.println(e + " " + WeatherStation.stationDataRows.size()); 
                System.exit(0);
            }
        }
        System.out.println("complete");
    }
}

