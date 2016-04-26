package weatherstation.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * Program info: This class collects the data from the BOM website
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com
 * Last Changed: 26-Apr-2016
 */

public class CollectInput{
    static InputStream inputStream;
    static JsonReader rdr;
    static JsonObject obj;
    static JsonObject obj1;
    public static JsonArray results;
    static URL url;
    
    public void getInput() {
        try
        {
            url = new URL("http://www.bom.gov.au/fwo/IDQ60801/IDQ60801.95551.json");
        }catch(MalformedURLException ex){};
        try {
            inputStream = url.openStream();
        } catch(IOException ex){}
        rdr = Json.createReader(inputStream);
        obj = rdr.readObject();
        obj1 = obj.getJsonObject("observations");
        results = obj1.getJsonArray("data");
  
    }
}
