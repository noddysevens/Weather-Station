package weatherstation.utilities;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Program info: This class collects the station list zip file from the BOM website
 * and extracts the text file
 * Author: David (NoddySevens) Programmer
 * E-mail Address: noddysevens@gmail.com 
 */
public class ZipReader
{
    private static URL url;
    
    public static void readZip() throws Exception{
        byte[] buffer = new byte[2048];
        
        try {
            url = new URL("ftp://ftp.bom.gov.au/anon2/home/ncc/metadata/sitelists/stations.zip");
        } catch(MalformedURLException ex){
            ex.printStackTrace();
        }

        InputStream theFile = url.openStream();
        ZipInputStream stream = new ZipInputStream(theFile);
        String outdir = System.getProperty("user.dir");

        try {
            ZipEntry entry;
            while((entry = stream.getNextEntry())!=null)
            {
                String s = String.format("Entry: %s len %d added %TD",
                                entry.getName(), entry.getSize(),
                                new Date(entry.getTime()));
                
                String outpath = outdir + "/" + entry.getName();
                FileOutputStream output = null;
                try {
                    output = new FileOutputStream(outpath);
                    int len = 0;
                    while ((len = stream.read(buffer)) > 0){
                        output.write(buffer, 0, len);
                    }
                }
                finally {
                    if(output!=null){
                        output.close();
                    }
                }
            }
        }
        finally {
            stream.close();
        }
    }
    
    public static void main(String[] args) throws Exception {
        readZip();
    }
}
