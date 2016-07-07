package com;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



public class ParseXml 
{
	public static void main(String[] args) 
		{	System.out.println("JunkRun");
		
			runReadXmlPropertiesFile();
		}

	//This reads a flat .properties file into a Properties object
	public static void runReadXmlPropertiesFile()  
		{	File l_file = new File("C:\\temp\\MDBGeneratePDFResponder_global.xml");
		
			String l_key = null;
			String l_value = null;
			
	    try 
	    	{	System.out.println("Reading " + l_file.getAbsolutePath());
    	
	    		//Build a Document object from the XML file
    			DocumentBuilderFactory l_documentBuilderFactory = DocumentBuilderFactory.newInstance();

    			//Ignore the external DTD completely.
    			//Taken from site: http://xerces.apache.org/xerces-j/features.html
    			l_documentBuilderFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", new Boolean(false));

    			DocumentBuilder l_documentBuilder = l_documentBuilderFactory.newDocumentBuilder();
	    		Document l_document = l_documentBuilder.parse (l_file);

	    		//Get the initial element ("configuration")
	    		Element l_element = l_document.getDocumentElement();
	    		
	    		//Check for element name = "configuration"
	    		if ( ! (l_element.getNodeName().equals("configuration")))
	    			{ System.out.println("  non-configuration file: " + l_element.getNodeName() );
	    				return;
	    			}
	    		
	    		//Get all the "property" children Nodes, and loop thru them
	    		NodeList l_nodeList  = l_element.getElementsByTagName("property");
	    		
	    		for (int i1=0; i1 < l_nodeList.getLength(); i1++)
	    			{	//Get all the attributes for the property, and loop thru them
		    			NamedNodeMap l_namedNodeMap = l_nodeList.item(i1).getAttributes();
		    			
		    			for (int i2=0; i2 < l_namedNodeMap.getLength(); i2++)
		    				{	Node l_node2 = l_namedNodeMap.item(i2);
		    					if (l_node2.getNodeName().equals("key"))	{ l_key = l_node2.getNodeValue();}
		    					if (l_node2.getNodeName().equals("value"))	{ l_value = l_node2.getNodeValue();}
		    				}
			    		System.out.println("\t" + l_key + " = " + l_value);
	    			}
	      } 
	    catch (Exception e) 
	    	{	System.out.println("Error in runReadXmlPropertiesFile(): " + e.toString());
	      }

		}	
	
	
	
}
