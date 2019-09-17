package game;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * The purpose of the Configurer class is to take an XML file as an input and return a simulation
 * as its output. This simulation will be based on the contents of the XML file.
 */
public class Configurer{
    /**
     *Basic functionality of reading XML based on online example
     * @param myFile XML to be read
     * @return Simulation created based on XML file
     */
    public static Simulation readFile(String myFile){
        try{
            File myXML = new File(myFile);
            FileInputStream xmlInput = new FileInputStream(myXML);
            Properties properties = new Properties();
            properties.loadFromXML(xmlInput);
            xmlInput.close();

            Enumeration enuKeys = properties.keys();
            while(enuKeys.hasMoreElements()){
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                System.out.println(key + ": " + value);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //Config will eventually: create array of cells with state initialized. This will be passed into Simulation


}
