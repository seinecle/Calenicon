/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMPEditor;

/**
 *
 * @author C. Levallois
 */
public class XMPCreator {

    public String create() {

        String xmpString = "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"Adobe XMPCore 5.0-c061 64.140949, 2010/12/07-10:57:01 \">"
                + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">";

        return xmpString;
    }
}
