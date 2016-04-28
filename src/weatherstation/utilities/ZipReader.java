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
 * Last Changed: 
 */
public class ZipReader
{
    static URL url;
    static String[] args = new String[]{"ftp://ftp.bom.gov.au/anon2/home/ncc/metadata/sitelists/stations.zip","C:/Users/David/Downloads"};
    
    public static void main(String[] args) throws Exception {
        if(args.length != 2)
        {
            System.err.println("zipreader zipfile outputdir");
            return;
        }

        // create a buffer to improve copy performance later.
        byte[] buffer = new byte[2048];
        
        try
        {
            url = new URL(args[0]);
        } catch(MalformedURLException ex){}
        
        // open the zip file stream
        InputStream theFile = url.openStream();
        ZipInputStream stream = new ZipInputStream(theFile);
        String outdir = args[1];

        try
        {

            // now iterate through each item in the stream. The get next
            // entry call will return a ZipEntry for each file in the
            // stream
            ZipEntry entry;
            while((entry = stream.getNextEntry())!=null)
            {
                String s = String.format("Entry: %s len %d added %TD",
                                entry.getName(), entry.getSize(),
                                new Date(entry.getTime()));
                System.out.println(s);

                // Once we get the entry from the stream, the stream is
                // positioned read to read the raw data, and we keep
                // reading until read returns 0 or less.
                String outpath = outdir + "/" + entry.getName();
                FileOutputStream output = null;
                try
                {
                    output = new FileOutputStream(outpath);
                    int len = 0;
                    while ((len = stream.read(buffer)) > 0)
                    {
                        output.write(buffer, 0, len);
                    }
                }
                finally
                {
                    // we must always close the output file
                    if(output!=null) output.close();
                }
            }
        }
        finally
        {
            // we must always close the zip file.
            stream.close();
        }
    }
    
    public static void readZip() throws Exception{
        main(args);
    }
}