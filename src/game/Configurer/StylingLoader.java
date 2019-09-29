package game.Configurer;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

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
    private static final String COLOR_DEC_TAG = "colorDeclaration";
    private static final String CELL_COLOR_TAG = "color";
    private static final String CELL_IDENTIFIER_TAG = "cellState";

    //Tags used within default color file
    private static final String DEFAULT_COLOR_TAG = "default";

    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";

    private Element mainElement;
    private Element colorElement;
    private Map<String, String> colorMap;

    StylingLoader(Element defaultColors, Element mainDocumentElement){
        colorElement = defaultColors;
        mainElement = mainDocumentElement;
    }

    public Map<String, Object> getStyling(){
        loadDefaults();
        return new HashMap<String, Object>();
    }

    private void loadDefaults() {
        NodeList colorList = colorElement.getElementsByTagName(DEFAULT_COLOR_TAG);
    }

}
