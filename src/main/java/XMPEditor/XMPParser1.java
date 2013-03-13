/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XMPEditor;

import java.io.IOException;
import java.util.HashSet;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author C. Levallois
 */
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author C. Levallois
 */
public class XMPParser1 extends DefaultHandler {

    private boolean newField;
    private String value;
    private boolean newNameType;
    private boolean newMatch;
    private InputSource is;
    private boolean work;
    private boolean collection;
    private boolean image;
    private String id;
    private URL url;
    private String refid;
    private boolean newDescriptionSet;
    private StringBuilder descriptionSetBuilder;
    private String descriptionSet;
    private boolean newDescription;
    private StringBuilder descriptionBuilder;
    private String description;
    private String source;
    private String type;
    private Map<String, String> metadata;
    private String prefixString = "";
    private StringBuilder valueBuilder;

    public XMPParser1(String xmlInput) {
        this.is = new InputSource(new StringReader(xmlInput));
    }

    public Map<String, String> parse() throws IOException {


        metadata = new TreeMap();
        //get a factory
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            System.out.println("starting to parse XMP");
            //get a new instance of parser
            SAXParser sp = spf.newSAXParser();

            //parse the file and also register this class for call backs
            sp.parse(is, this);

            System.out.println("finished parsing XMP");


        } catch (SAXException se) {
            System.out.println("SAXException: " + se);
        } catch (ParserConfigurationException pce) {
            System.out.println("ParserConfigurationException: " + pce);
        } catch (IOException ie) {
            System.out.println("IOException: " + ie);
        }

        System.out.println("metada:");
        for (String key : metadata.keySet()) {
            System.out.println(key + ", " + metadata.get(key));
        }

        return metadata;


    }
    int nbAttributes;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {


        if (qName.contains("vrae:")) {
            newField = true;

            valueBuilder = new StringBuilder();
            prefixString = prefixString + qName.replace("vrae:", "") + "_";

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (newField) {
            valueBuilder.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.contains("vrae")) {
            value = valueBuilder.toString();
            metadata.put(StringUtils.removeEnd(prefixString, "_"), value);
            newField = false;
        }
        prefixString = StringUtils.removeEnd(prefixString, qName.replace("vrae:", "") + "_");

    }
}
