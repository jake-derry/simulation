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
    //These Tags are automatically added to the parameters
    private static final String CELL_SIZE_TAG = "cellSize";
    private static final String OUTLINE_TAG = "outline";
    private static final String WINDOW_DIMENSION_TAG = "windowDimension";
    private static final String DELAY_TAG = "delay";

    //Tags used within XML styling file
    private static final String CELL_COLOR_TAG = "color";
    private static final String CELL_IDENTIFIER_TAG = "state";
    private static final String OPTION_IDENTIFIER_TAG = "option";
    private static final String OPTION_KEY_TAG = "type";
    private static final String OPTION_VALUE_TAG = "value";
    private static final String COLOR_MAP_TAG = "colorMap";


    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";

    private Element mainElement;
    private Element defaultElement;
    private Map<String, Object> colorMap;
    private Map<String, Object> myStyling;

    /**
     * Constructor for the StylingLoader Class
     *
     * @param defaultStyle Element, taken from XML, of the default file for state colors
     * @param mainDocumentElement Element, taken from XML, of the style file to be read from
     */
    StylingLoader(Element defaultStyle, Element mainDocumentElement){
        defaultElement = defaultStyle;
        mainElement = mainDocumentElement;
        colorMap = new HashMap<>();
        myStyling = new HashMap<>();
    }

    /**
     * Creates a Map of state colors and optional styling parameters
     *
     * @return Map containing the style parameters, as read in the XML file
     */
    public Map<String, Object> getStyling(){
        addElements(defaultElement);
        addElements(mainElement);
        myStyling.put(COLOR_MAP_TAG, colorMap);
        return myStyling;
    }

    /**
     * Adds nodes within the Element to the style Map
     */
    private void addElements(Element myElement){
        NodeList styleList = myElement.getChildNodes();
        for(int i = 0 ; i < styleList.getLength(); i++){
            if(styleList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element myStyle = (Element) styleList.item(i);
                if (myStyle.getTagName().equals(CELL_COLOR_TAG)) {
                    addColor(myStyle);
                } else if (myStyle.getTagName().equals(OPTION_IDENTIFIER_TAG)) {
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

}
