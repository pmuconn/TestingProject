/*
 * ParseXMLFile.java
 *
 * Created on 4. listopad 2003, 10:14
 */
package com.parsexml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/** This class represents short example how to parse XML file,
 * get XML nodes values and its values.<br><br>
 * It implements method to save XML document to XML file too
 * @author Martin Glogar
 */
public class ParseXMLFile {
    
    private String xmlFileName = "c:/paul/BookofBusiness request.xml";
    private String targetFileName = "c:/paul/example2.xml";
    
    /** Starts XML parsing example
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/BookofBusiness request.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/BookofBusiness request.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/BookofBusiness response.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/BookofBusiness response.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/retrieveRenewalQuote request.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/retrieveRenewalQuote request.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/retrieveRenewalQuote response.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/retrieveRenewalQuote response.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/retrieveAvailablePlans request.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/retrieveAvailablePlans request.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/retrieveAvailablePlans response.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/retrieveAvailablePlans response.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/getRates request.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/getRates request.csv");
//        
//        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/getRates response.xml",
//        		"H:/_UES/RWT Phase 2/Prime Service/getRates response.csv");

        //Individual runs.
        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/getRates request short.xml",
        	"H:/_UES/RWT Phase 2/Prime Service/getRates request short.csv");
        
        new ParseXMLFile("H:/_UES/RWT Phase 2/Prime Service/getRates response short.xml",
        	"H:/_UES/RWT Phase 2/Prime Service/getRates response short.csv");
        
        System.out.println("*** End of Main ***");
    }
    
    /** Creates a new instance of ParseXMLFile */
    public ParseXMLFile() {
        // parse XML file -> XML document will be build
        Document doc = parseFile(xmlFileName);
        // get root node of xml tree structure
        Node root = doc.getDocumentElement();
        // write node and its child nodes into System.out
        System.out.println("Statemend of XML document...");
        writeDocumentToOutput(root,0);
        System.out.println("... end of statement");
        // write Document into XML file
        saveXMLDocument(targetFileName, doc);
    }
    
    /** Creates a new instance of ParseXMLFile */
    public ParseXMLFile(String inputFile) {
    	
    	xmlFileName = inputFile;
    	
        // parse XML file -> XML document will be build
        Document doc = parseFile(xmlFileName);
        // get root node of xml tree structure
        Node root = doc.getDocumentElement();
        // write node and its child nodes into System.out
        System.out.println("Statemend of XML document...");
        writeDocumentToOutput(root,0);
        System.out.println("... end of statement");
        // write Document into XML file
      //  saveXMLDocument(targetFileName, doc);
    }
    
    /** Creates a new instance of ParseXMLFile */
    public ParseXMLFile(String inputFile, String outputFile) {
    	
    	xmlFileName = inputFile;
    	
        // parse XML file -> XML document will be build
        Document doc = parseFile(xmlFileName);
        // get root node of xml tree structure
        Node root = doc.getDocumentElement();
        // write node and its child nodes into System.out
        
        // open output stream where XML Document will be saved
        File xmlOutputFile = null;
        FileOutputStream fos = null;
        try {
        	if (fos == null) {
                xmlOutputFile = new File(outputFile);
                fos = new FileOutputStream(xmlOutputFile);
        	}
        	fos.write(("NodeName, NodeValue\n").getBytes());
        	fos.write((",AttributeName, AttributeValue\n").getBytes());
        	writeDocumentToOutputFile(root,0,fos);
            
            fos.close();
            
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return;
        }
        catch (IOException e) {
            System.out.println("Error occured: " + e.getMessage());
            return;
        }

        // write Document into XML file
      //  saveXMLDocument(targetFileName, doc);
    }
    
    /** Returns element value
     * @param elem element (it is XML tag)
     * @return Element value otherwise empty String
     */
    public final static String getElementValue( Node elem ) {
        Node kid;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
    
    private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
    
    private String getIndentSpacesAsCommas(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(",");
        }
        return buffer.toString();
    }
    
    /** Writes node and all child nodes into System.out
     * @param node XML node from from XML tree wrom which will output statement start
     * @param indent number of spaces used to indent output
     */
    public void writeDocumentToOutput(Node node,int indent) {
        // get element name
        String nodeName = node.getNodeName();
        // get element value
        String nodeValue = getElementValue(node);
        // get attributes of element
        NamedNodeMap attributes = node.getAttributes();
        System.out.println(getIndentSpaces(indent) + "NodeName, " + nodeName + ", NodeValue: " + nodeValue);
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.println(getIndentSpaces(indent + 2) + ",AttributeName, " + attribute.getNodeName() + ", attributeValue, " + attribute.getNodeValue());
        }
        // write all child nodes recursively
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                writeDocumentToOutput(child,indent + 2);
            }
        }
    }
    
    /** Writes node and all child nodes into System.out
     * @param node XML node from from XML tree wrom which will output statement start
     * @param indent number of spaces used to indent output
     */
    public void writeDocumentToOutputFile(Node node,int indent, FileOutputStream fos) {
    	
        try {
	        // get element name
	        String nodeName = node.getNodeName().trim();
	        // get element value
	        String nodeValue = getElementValue(node).trim();
	        // get attributes of element
	        NamedNodeMap attributes = node.getAttributes();
	
	        fos.write((getIndentSpacesAsCommas(indent) + "<"+nodeName+">" + "," + nodeValue + "\n").getBytes());
	        
	        for (int i = 0; i < attributes.getLength(); i++) {
	            Node attribute = attributes.item(i);
	            fos.write((getIndentSpacesAsCommas(indent + 1) + attribute.getNodeName() + "," + attribute.getNodeValue()+ "\n").getBytes());
	        }
	        // write all child nodes recursively
	        NodeList children = node.getChildNodes();
	        for (int i = 0; i < children.getLength(); i++) {
	            Node child = children.item(i);
	            if (child.getNodeType() == Node.ELEMENT_NODE) {
	            	writeDocumentToOutputFile(child,indent+1,fos);
	            }
	        }
        }
        catch (IOException e) {
            System.out.println("Error occured: " + e.getMessage());
            return;
        }
    }
    
    /** Saves XML Document into XML file.
     * @param fileName XML file name
     * @param doc XML document to save
     * @return <B>true</B> if method success <B>false</B> otherwise
     */    
    public boolean saveXMLDocument(String fileName, Document doc) {
        System.out.println("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        File xmlOutputFile = new File(fileName);
        FileOutputStream fos;
        Transformer transformer;
        try {
            fos = new FileOutputStream(xmlOutputFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return false;
        }
        // Use a Transformer for output
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            System.out.println("Transformer configuration error: " + e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(fos);
        // transform source into result will do save
        try {
            transformer.transform(source, result);
        }
        catch (TransformerException e) {
            System.out.println("Error transform: " + e.getMessage());
        }
        System.out.println("XML file saved.");
        return true;
    }
    
    /** Parses XML file and returns XML document.
     * @param fileName XML file to parse
     * @return XML document or <B>null</B> if error occured
     */
    public Document parseFile(String fileName) {
        System.out.println("Parsing XML file... " + fileName);
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            System.out.println("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        }
        catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("Could not read source file: " + e.getMessage());
        }
        System.out.println("XML file parsed");
        return doc;
    }
    
    
}
