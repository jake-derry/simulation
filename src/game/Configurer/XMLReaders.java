package game.Configurer;

import game.Configurer.ExceptionHandlers.ErrorThrow;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The purpose of this class is to provide classes with common methods to retrieve parameters form an XML
 * file. Both the ParameterLoader and StylingLoader classes utilize this class to read from their XML
 * files.
 */
public class XMLReaders {

    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";
    private static final int DEFAULT_INT = -1;

    /**
     * Returns a Integer representation of the first sub-element of an element. If the specified element
     * does not contain an integer, then a default integer value is used.
     */
    public static Integer getFirstElementInteger(Element myElement, String TagName){
        Node myValue = myElement.getElementsByTagName(TagName).item(0);
        if(myValue == null){
            new ErrorThrow(NOT_FOUND, TagName);
            return DEFAULT_INT;
        }
        try{
            int x = Integer.parseInt(myValue.getTextContent());
            return x;
        } catch (Exception e){
            try{
                int x = Integer.parseInt(myValue.getTextContent().replaceAll("\\s+", ""));
                return x;
            } catch (Exception e2){
                new ErrorThrow(e2.getMessage());
            }
        }
        return DEFAULT_INT;
    }

    /**
     * Returns a String containing the name of the first sub-element of an element
     */
    public static String getFirstElementString(Element myElement, String TagName){
        Node myValue = myElement.getElementsByTagName(TagName).item(0);
        if(myValue == null){
            new ErrorThrow(NOT_FOUND, TagName);
            return "Unspecified";
        }
        return myElement.getElementsByTagName(TagName).item(0).getTextContent();
    }

    public static int[] getIntegerArray(Element myElement, String TagName){
        Node myValue = myElement.getElementsByTagName(TagName).item(0);
        if(myValue == null){
            new ErrorThrow(NOT_FOUND, TagName);
            return new int[]{0};
        }
        System.out.println(myElement.getElementsByTagName(TagName).item(0).getTextContent());
        return new int[]{0};
    }
}
