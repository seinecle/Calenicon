/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMPEditor;

import java.util.Iterator;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author C. Levallois
 */
public class VRAWriter {

    public String write(String XMP) {
        String toReturn = XMP;
        toReturn = StringUtils.substringBeforeLast(toReturn, "</x:xmpmeta>").trim();
        toReturn = StringUtils.substringBeforeLast(toReturn, "</rdf:RDF>").trim();
        toReturn = toReturn
                + "<rdf:Description rdf:about=\"\" xmlns:vrae=\"http://www.vraweb.org/vracore/4.0/essential/\">";
        VRAProperties.populate();
        Set<String> vraProperties = VRAProperties.get();
        Iterator<String> vraPropertiesIterator = vraProperties.iterator();
        String currProperty;
        while (vraPropertiesIterator.hasNext()) {
            currProperty = vraPropertiesIterator.next();
            toReturn = toReturn + "<vrae:" + currProperty + ">" + currProperty + "</vrae:" + currProperty + ">";
        }
        return toReturn;
    }
}
