package game.Configurer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

import static game.Configurer.XMLReaders.getFirstElementString;
import static game.Configurer.XMLReaders.getFirstElementInteger;

/**
 * This class handles loading in style parameters from the XML file. It contains default values for each of the parameters,
 * which is set when the specified values in the XML are not supported.
 *
 * @author Jonah Knapp
 */
public class StylingLoader {
    //Tags used within XML styling file
    private static final String CELL_SIZE_TAG = "cellSize";
    private static final String OUTLINE_TAG = "outline";
    private static final String CELL_COLOR_TAG = "color";
    private static final String CELL_IDENTIFIER_TAG = "state";
    private static final String OPTION_IDENTIFIER_TAG = "option";
    private static final String OPTION_KEY_TAG = "type";
    private static final String OPTION_VALUE_TAG = "value";
    private static final String COLOR_MAP_TAG = "colorMap";

    //Tags used within default color file

    //Default Values

    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";

    private Element mainElement;
    private Element defaultElement;
    private Map<String, Object> colorMap;
    private Map<String, Object> myStyling;

    StylingLoader(Element defaultStyle, Element mainDocumentElement){
        defaultElement = defaultStyle;
        mainElement = mainDocumentElement;
        colorMap = new HashMap<>();
        myStyling = new HashMap<>();
    }

    public Map<String, Object> getStyling(){
        addElements(defaultElement);
        addElements(mainElement);
        myStyling.put(COLOR_MAP_TAG, colorMap);
        return myStyling;
    }

    private void addElements(Element myElement){
        System.out.println("NEW");
        NodeList styleList = myElement.getChildNodes();
        for(int i = 0 ; i < styleList.getLength(); i++){
            System.out.println(styleList.item(i).getNodeType());
            if(styleList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element myStyle = (Element) styleList.item(i);
                if (myStyle.getTagName() == CELL_COLOR_TAG) {
                    System.out.println("NEW COLOR");
                    addColor(myStyle);
                } else if (myStyle.getTagName() == OPTION_IDENTIFIER_TAG) {
                    addStyle(myStyle);
                }
            }
        }
    }
    /**
     * Adds a color to the colormap
     */
    private void addColor(Element myColor) {
        String myState = getFirstElementString(myColor, CELL_IDENTIFIER_TAG);
        String myValue = getFirstElementString(myColor, CELL_COLOR_TAG);
        colorMap.put(myState, myValue);
    }

    /**
     * Adds a default value to the style map
     */
    private void addStyle(Element myStyle){
        String myState = getFirstElementString(myStyle, OPTION_KEY_TAG);
        int myValue = getFirstElementInteger(myStyle, OPTION_VALUE_TAG);
        if(myValue > 0) {myStyling.put(myState,myValue);}
    }

    /**
     * Reads all nodes within the style file and adds them to the Map to be sent back to Visualization.
     */
    private Map<String, Object> readStyling(){
        Map<String, Object> myStyling = new HashMap<>();
        return null;
    }
}
