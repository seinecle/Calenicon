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
public class XMPParser extends DefaultHandler {

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

    public XMPParser(String xmlInput) {
        this.is = new InputSource(new StringReader(xmlInput));
    }

    public Map<String, String> parse() throws IOException {


        metadata = new HashMap();
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

        prefixString = prefixString + qName + "_";

        if (qName.equals("work")) {
            work = true;
        }


        if (qName.equals("collection")) {
            collection = true;
        }

        if (qName.equals("image")) {
            image = true;
        }

        if (qName.equals("descriptionSet")) {
            newDescriptionSet = true;
            descriptionSetBuilder = new StringBuilder();
        }

        if (qName.equals("description")) {
            newDescription = true;
            descriptionBuilder = new StringBuilder();
        }

        for (int i = 0; i < attributes.getLength(); i++) {
            if (attributes.getLocalName(i).equals("id")) {
                id = attributes.getValue(i);
            }
            if (attributes.getLocalName(i).equals("refid")) {
                refid = attributes.getValue(i);
                metadata.put(prefixString + "refid", refid);
            }
            if (attributes.getLocalName(i).equals("source")) {
                source = attributes.getValue(i);
                metadata.put(prefixString + "source", source);
            }
            if (attributes.getLocalName(i).equals("type")) {
                type = attributes.getValue(i);
                metadata.put(prefixString + "type", type);
            }
            if (attributes.getLocalName(i).equals("href")) {
                metadata.put(prefixString + "href", attributes.getValue(i));
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (newDescriptionSet) {
            descriptionSetBuilder.append(ch, start, length);
        }

        if (newDescription) {
            descriptionBuilder.append(ch, start, length);
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("date")) {
            System.out.println("end of date");
        }

        if (qName.equals("descriptionSet") && newDescriptionSet) {
            descriptionSet = descriptionSetBuilder.toString();
            newDescriptionSet = false;
        }

        if (qName.equals("description") && newDescription) {
            description = descriptionBuilder.toString();
            metadata.put(prefixString, description);
            newDescription = false;

        }
        prefixString = StringUtils.removeEnd(prefixString, qName + "_");

    }
}
