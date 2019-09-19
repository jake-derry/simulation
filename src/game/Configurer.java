package game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
    private static final String COLUMN_TAG = "columns";
    private static final String ROW_TAG = "rows";
    private static final String CELL_COLUMN_TAG = "col";
    private static final String CELL_ROW_TAG = "row";
    private static final String CELL_STATE_TAG = "state";
    private static final String TYPE_TAG = "type";

    //Simulations Supported
    private static final String LIFE = "gameOfLife";
    private static final String SEGREGATION = "segregation";
    private static final String PREDATOR_PREY = "predatorPrey";
    private static final String FIRE = "fire";
    private static final String PERCOLATION = "percolation";

    /**Reads XML file. Creates a document using the DocumentBuilder class. Uses this information to create a
     * cellular array and ultimately passes this information to the Simulation class.
     *
     * @param myFile XML to be read
     * @return Simulation created based on XML file
     */
    public static Simulation getSimulation(String myFile){
        Document simDoc = readFile(myFile);
        System.out.println(simDoc.getElementsByTagName(ROW_TAG).item(0).getTextContent());
        System.out.println(simDoc.getElementsByTagName(COLUMN_TAG).item(0).getTextContent());
        int totalColumns = Integer.parseInt(simDoc.getElementsByTagName(COLUMN_TAG).item(0).getTextContent());
        int totalRows = Integer.parseInt(simDoc.getElementsByTagName(ROW_TAG).item(0).getTextContent());
        System.out.println(simDoc.getElementsByTagName(ROW_TAG).item(0).getTextContent());
        Cell[][] myCellArray = new Cell[totalRows][totalColumns];
        List<Integer> activeCells = initializeActiveCells(simDoc, totalColumns, myCellArray);
        initializeInactiveCells(totalRows, totalColumns, activeCells, myCellArray);
        switch (simDoc.getElementsByTagName(TYPE_TAG).item(0).getTextContent()){
            case LIFE:
                //Determine game-specific parameters
                //Create and return game-specific simulation
            case SEGREGATION:

            case PREDATOR_PREY:

            case FIRE:

            case PERCOLATION:

        }
        return null;
    }

    private static Document readFile(String myFile){
        try{
            File simFile = new File("data/" + myFile);
            DocumentBuilder simDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document simDoc = simDocumentBuilder.parse(simFile);
            //printActiveCells(simDoc);
            return simDoc;
        } catch (Exception e) {
            e.printStackTrace();
            errorAlert(e.getMessage());

    }
        return null;
    }

    private static List<Integer> initializeActiveCells(Document myDoc, int totalCols, Cell[][] myArray){
        NodeList nList = myDoc.getElementsByTagName("cell");
        List<Integer> activeCells = new ArrayList<>();
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myCell = (Element) nList.item(i);
            int myRow = Integer.parseInt(getFirstElement(myCell, CELL_ROW_TAG));
            int myColumn = Integer.parseInt(getFirstElement(myCell, CELL_COLUMN_TAG));
            int myState = Integer.parseInt(getFirstElement(myCell, CELL_STATE_TAG));
            //myArray[myRow][myColumn] = new Cell(myState);
            activeCells.add(myRow * totalCols + myColumn);
            System.out.println("Added");
        }
        return activeCells;
    }

    private static void initializeInactiveCells(int Rows, int Cols, List<Integer> activeCells, Cell[][] myArray){
        for(int i = 0; i< Rows; i++){
            for(int j = 0; j<Cols; j++){
                if(!(activeCells.contains(i*Cols + j))){
                    //myArray[i][j] = new Cell(0);
                    System.out.println("Yes");
                }
            }
        }
    }

    private static void printActiveCells(Document myDoc) {
        NodeList nList = myDoc.getElementsByTagName("cell");
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myCell = (Element) nList.item(i);
            System.out.println("Cell " + i + ":");
            System.out.println(CELL_ROW_TAG + " " + getFirstElement(myCell, CELL_ROW_TAG));
            System.out.println(CELL_COLUMN_TAG + " " + getFirstElement(myCell, CELL_COLUMN_TAG));
            System.out.println(CELL_STATE_TAG + " " + getFirstElement(myCell, CELL_STATE_TAG));
        }
    }


    private static String getFirstElement(Element myElement, String TagName){
        return myElement.getElementsByTagName(TagName).item(0).getTextContent();
    }



    private static void errorAlert(String myErrorMessage){
        new Alert(AlertType.ERROR, myErrorMessage).showAndWait();
    }
}
