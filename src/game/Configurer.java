package game;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * The purpose of the Configurer class is to take an XML file as an input and return a simulation
 * as its output. This simulation will be based on the contents of the XML file.
 */
public class Configurer{
    //Tags used within XML file
    private static final String COLUMN_TAG = "col";
    private static final String ROW_TAG = "row";
    private static final String STATE_TAG = "state";
    /**
     *Basic functionality of reading XML based on online example
     * @param myFile XML to be read
     * @return Simulation created based on XML file
     */
    public static Simulation readFile(String myFile){
        try{
            File simFile = new File("data/" + myFile);
            DocumentBuilder simDocumentBuilder = createDocumentBuilder();
            Document simDoc = simDocumentBuilder.parse(simFile);
            System.out.println("Root element :" + simDoc.getDocumentElement().getNodeName());
            System.out.println("Root element :" + simDoc.getElementsByTagName("author")
                    .item(0).getTextContent());
            NodeList nList = simDoc.getElementsByTagName("cell");
            for(int i = 0 ; i < nList.getLength(); i++){
                Element myCell = (Element) nList.item(i);
                System.out.println("Cell" + i);
                System.out.println("Row :" + myCell.getElementsByTagName(ROW_TAG)
                        .item(0).getTextContent());
                System.out.println("Col :" + myCell.getElementsByTagName(COLUMN_TAG)
                        .item(0).getTextContent());
                System.out.println("State :" + myCell.getElementsByTagName(STATE_TAG)
                        .item(0).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorAlert(e.getMessage());

    }
        return null;
    }

    private static DocumentBuilder createDocumentBuilder(){
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            errorAlert(e.getMessage());
            return null;
        }
    }

    private static void errorAlert(String myErrorMessage){
        new Alert(AlertType.ERROR, myErrorMessage).showAndWait();
    }

    /**
     *
     * @param myDoc Document to extract element from
     * @param tag Tag to search for within the document
     * @return single element that corresponds to a tag
     */
    private static String getElement(Document myDoc, String tag){
        return myDoc.getElementsByTagName("author").item(0).getTextContent();
    }




    //Config will eventually: create array of cells with state initialized. This will be passed into Simulation


}
