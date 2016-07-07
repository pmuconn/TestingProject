/**
 * 
 */
package com.teaas.utils;

import org.jdom2.input.sax.SAXHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * This class overrides the jdom2 SAXHandler such that during parsing, namespaces are removed.
 * @author pmorano
 *
 */
public class NoNamespaceSAXHandler extends SAXHandler {
	@Override
    public void startElement(
            String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
    	//System.out.println("start element nsuri: " + namespaceURI + " localname: " + localName + " qname: " + qName + " atts: " + atts);
		
		//qName would include any namespace prefix (ie. <prefix:tagname>). Since we don't want that parsed, we will just pass localName which does not
		//include namespace prefixes.
        //super.startElement("", localName, qName, atts);
        super.startElement("", localName, localName, atts);
    }
    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
    	//this will ignore adding namespaces to internal list.
        return;
    }
}
