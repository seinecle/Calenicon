/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMPEditor;

/**
 *
 * @author C. Levallois
 */
public class XMPCloser {

    public String close(String XMP) {
        XMP = XMP
                + "</rdf:Description>"
                + " </rdf:RDF>"
                + " </x:xmpmeta>";

        return XMP;

    }
}
