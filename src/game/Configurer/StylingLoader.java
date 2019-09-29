package game.Configurer;

import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class StylingLoader {
    //Tags used within XML file
    private static final String CELL_SIZE_TAG = "cellSize";
    private static final String OUTLINE_TAG = "outline";
    private static final String COLOR_DEC_TAG = "colorDeclaration";
    private static final String CELL_COLOR_TAG = "color";
    private static final String CELL_IDENTIFIER_TAG = "cellState";

    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";

    private Element mainElement;

    StylingLoader(Element mainDocumentElement){
        mainElement = mainDocumentElement;
    }

    public Map<String, String> getStyling(){
        return new HashMap<String, String>();
    }

}
