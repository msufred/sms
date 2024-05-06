package org.gemseeker.sms;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public final class Settings {

    private final String path;
    private final Document doc;

    public Settings(String path) throws ParserConfigurationException, SAXException, IOException {
        this.path = path;
        File file = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(file);
        doc.getDocumentElement().normalize();
    }

    public String getDatabaseSetting(String valueTag) {
        return get("database", valueTag);
    }

    public void setDatabaseSetting(String valueTag, String value) {
        set("database", valueTag, value);
    }

    public String get(String parentTag, String valueTag) {
        Node node = doc.getElementsByTagName(parentTag).item(0);
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            return ((Element) node).getElementsByTagName(valueTag).item(0).getTextContent();
        }
        return null;
    }

    public void set(String parentTag, String valueTag, String value) {
        Node node = doc.getElementsByTagName(parentTag).item(0);
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            ((Element) node).getElementsByTagName(valueTag).item(0).setTextContent(value);
        }
    }

    public void save() throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(path));
        transformer.transform(source, result);
    }
}
