package com.teaas.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.output.DOMOutputter;
import org.jdom2.output.EscapeStrategy;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.XPathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLUtils {
	private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);

	/**
	 * This method receives a JDOM2 Document and a map which contains xpaths and
	 * their corresponding values.The document will be filled with values for
	 * given xpaths and the same document will be returned to calling unit.
	 * 
	 * 
	 * @param document
	 * @param xpathNvalues
	 * @return JDOM2 Document
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document fillTemplate(final Document document, final Map<String, String> xpathNvalues) throws Exception, IOException {
		Document result = document.clone();
		for (Map.Entry<String, String> entry : xpathNvalues.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			result = replaceElementValue(document, key, value);
		}
		return result;

	}
	
	/**
	 * Generates XML string based on xpath mapping and template xml.
	 * @param inputXML
	 * @param xpathsMap
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 */
	public static String getFilledXML(String inputXML, final Map<String, String> xpathsMap) throws Exception, IOException {
		String filledXML = "";
		try {
			
			Document document = XMLUtils.getStringAsDocument(inputXML);
			Document filledDocument = XMLUtils.fillTemplate(document, xpathsMap);
			filledXML = XMLUtils.getDocumentAsString(filledDocument);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return filledXML;
	}


	public static Document generateTemplate(final Document document) throws Exception {
		final XPathExpression<Element> xpath = XPathFactory.instance().compile("//node()", Filters.element());
		final List<Element> elements = xpath.evaluate(document);

		try {
			for (final Element content : elements) {
				if (!content.getText().trim().equals("")) {
					content.setText(null);
				}
				final List<Attribute> attributes = content.getAttributes();
				for (final Attribute attribute : attributes) {
					String attr = attribute.getValue();
					if (attribute.getValue() != null && attribute.getValue().length() > 0) {
						attribute.setValue("");
					}

				}
			}
		} catch (Exception e) {
			System.out.println("exception with gen template");
		}
		return document;
	}

	public static String generateTemplate(final String inputXML) throws Exception {
		final Document document = getStringAsDocument(inputXML);
		return getDocumentAsString(generateTemplate(document));
	}

	/**
	 * Populates a mapping of xpath and values based on a supplied Element
	 * 
	 * @param node
	 *            The xml document as an element
	 * @param xmlAsMap
	 *            The supplied map key = xpath, value = element value.
	 */
	public static void generateXpaths(final Element node, final Map<String, String> xmlAsMap) {
		// get attributes
		for (final Attribute attr : node.getAttributes()) {
			String absolutePath = XPathHelper.getAbsolutePath(attr);
			String nsprefix = attr.getNamespacePrefix();
			// don't add this if it is XSI:nil or type
			//xsi:nil is typically an attribute like  nillable="true"
			//type attributes are typically    type="xs:string"
			if (absolutePath.contains("/@*")) {
				//Attribute has namespace. extra testing.
				if (attr.getName().equalsIgnoreCase("nillable") || attr.getName().equalsIgnoreCase("type") || attr.getName().startsWith("nil") 
						|| (attr.getName().startsWith("nil") && nsprefix.equals("xsi")) ) {
					//exclude nillable, type, nil
				} else {
					//clear to add.
					/*
					 * The SAXBuilder will build xml and convert any escape values
					 * to unicode (ie.. &lt to <) Some services have xml entities
					 * (&lt etc )within a value so we need to maintain this.
					 * StringEscapeUtils does exactly this.
					 */
					xmlAsMap.put(absolutePath, StringEscapeUtils.escapeXml(attr.getValue()));
				}
			} else {
				//The attribute does not have a namespace.
				xmlAsMap.put(absolutePath, StringEscapeUtils.escapeXml(attr.getValue()));
			}
		}

		// process children nodes
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			for (final Element childNode : node.getChildren())
				generateXpaths(childNode, xmlAsMap);
			return;
		}

		// get value
		if (node.getValue() != null) {
			/*
			 * The SAXBuilder will build xml and convert any escape values to
			 * unicode (ie.. &lt to <) Some services have xml entities (&lt etc
			 * )within a value so we need to maintain this. StringEscapeUtils
			 * does exactly this.
			 */
			xmlAsMap.put(XPathHelper.getAbsolutePath(node), StringEscapeUtils.escapeXml(node.getValue()));
		}
		return;
	}

	public static void generateXpaths(final Element node, final List<String> xpaths) {
		// get attributes
		for (final Attribute attr : node.getAttributes()) {
			String absolutePath = XPathHelper.getAbsolutePath(attr);
			String nsprefix = attr.getNamespacePrefix();
			// don't add this if it is XSI:nil or type
			//xsi:nil is typically an attribute like  nillable="true"
			//type attributes are typically    type="xs:string"
			if (absolutePath.contains("/@*")) {
				//Attribute has namespace. extra testing.
				if (attr.getName().equalsIgnoreCase("nillable") || attr.getName().equalsIgnoreCase("type") || attr.getName().startsWith("nil") 
						|| (attr.getName().startsWith("nil") && nsprefix.equals("xsi")) ) {
					//exclude nillable, type, nil
				} else {
					//clear to add.
					/*
					 * The SAXBuilder will build xml and convert any escape values
					 * to unicode (ie.. &lt to <) Some services have xml entities
					 * (&lt etc )within a value so we need to maintain this.
					 * StringEscapeUtils does exactly this.
					 */
					xpaths.add(absolutePath);
				}
			} else {
				//The attribute does not have a namespace.
				xpaths.add(absolutePath);
			}
		}

		// process children nodes
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			for (final Element childNode : node.getChildren())
				generateXpaths(childNode, xpaths);
			return;
		}

		// get value
		if (node.getValue() != null) {
			/*
			 * The SAXBuilder will build xml and convert any escape values to
			 * unicode (ie.. &lt to <) Some services have xml entities (&lt etc
			 * )within a value so we need to maintain this. StringEscapeUtils
			 * does exactly this.
			 */
			xpaths.add(XPathHelper.getAbsolutePath(node));
		}
		return;
	}
	
	public static int getNumberOfNodes(final Element node) {
		int nodes = 0;
		for (final Attribute attr : node.getAttributes()) {
			String absolutePath = XPathHelper.getAbsolutePath(attr);
			String nsprefix = attr.getNamespacePrefix();
			if (absolutePath.contains("/@*")) {
				if (attr.getName().equalsIgnoreCase("nillable") || attr.getName().equalsIgnoreCase("type") || attr.getName().startsWith("nil") 
						|| (attr.getName().startsWith("nil") && nsprefix.equals("xsi")) ) {
				} else {
					nodes++;
				}
			} else {
				nodes++;
			}
		}
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			for (final Element childNode : node.getChildren()) {
				nodes += getNumberOfNodes(childNode);
			}
			return nodes;
		}
		if (node.getValue() != null) {
			nodes++;
		}
		return nodes;
	}
	
	public static void generateXpathData(final Element node, final Map<String, XpathData> xmlAsMap) {
		String elemvalue = "";
		// get attributes
		List<XpathData> attributes = new ArrayList<XpathData>();
		for (final Attribute attr : node.getAttributes()) {
			String absolutePath = XPathHelper.getAbsolutePath(attr);
			String nsprefix = attr.getNamespacePrefix();
			elemvalue = attr.getValue();
			String shortxpath = createXpathShortName(attr);

			XpathData xdata = new XpathData();
			xdata.setValue(StringEscapeUtils.escapeXml(elemvalue));
			xdata.setXpath(absolutePath);
			xdata.setXpathShort(shortxpath);
			xdata.setName(attr.getName());
			
			// don't add this if it is XSI:nil or type
			//xsi:nil is typically an attribute like  nillable="true"
			//type attributes are typically    type="xs:string"
			if (absolutePath.contains("/@*")) {
				//Attribute has namespace. extra testing.
				if (attr.getName().equalsIgnoreCase("nillable") || attr.getName().equalsIgnoreCase("type") || attr.getName().startsWith("nil") 
						|| (attr.getName().startsWith("nil") && nsprefix.equals("xsi")) ) {
					//exclude nillable, type, nil
				} else {
					//clear to add.
					/*
					 * The SAXBuilder will build xml and convert any escape values
					 * to unicode (ie.. &lt to <) Some services have xml entities
					 * (&lt etc )within a value so we need to maintain this.
					 * StringEscapeUtils does exactly this.
					 */
					xmlAsMap.put(absolutePath, xdata);
					attributes.add(xdata);
				}
			} else {
				//The attribute does not have a namespace.
				xmlAsMap.put(absolutePath, xdata);
				attributes.add(xdata);
			}
		}

		// process children nodes
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			for (final Element childNode : node.getChildren())
				generateXpathData(childNode, xmlAsMap);
			return;
		}

		// get value
		if (node.getValue() != null) {
			elemvalue = node.getValue();
			String shortxpath = createXpathShortName(node);

			XpathData xdata = new XpathData();
			xdata.setValue(StringEscapeUtils.escapeXml(elemvalue));
			xdata.setXpath(XPathHelper.getAbsolutePath(node));
			xdata.setXpathShort(shortxpath);
			xdata.setAttributes(attributes);
			xdata.setName(node.getName());
			/*
			 * The SAXBuilder will build xml and convert any escape values to
			 * unicode (ie.. &lt to <) Some services have xml entities (&lt etc
			 * )within a value so we need to maintain this. StringEscapeUtils
			 * does exactly this.
			 */
			xmlAsMap.put(XPathHelper.getAbsolutePath(node), xdata);
//			xmlAsMap.put(XPathHelper.getAbsolutePath(node), StringEscapeUtils.escapeXml(elemvalue));
		}
		return;
	}
	
	public static String createXpathShortName(Element element) {
		StringBuffer returnValue = new StringBuffer();
		try {
			// can be null if no parent or parent is root
			Element parent = element.getParentElement(); 
			if (!THUtils.isNull(parent)) {
				returnValue.append(parent.getName());
				returnValue.append("/");
				returnValue.append(element.getName());
			} else {
				returnValue.append(element.getName());
			}
		} catch (Exception e) {
			// something bad happened, just log.
			logger.error("Exception creating short name: " + e.getStackTrace());
		}
		return returnValue.toString();
	}
	
	public static String createXpathShortName(Attribute attr) {
		StringBuffer returnValue = new StringBuffer();
		try {
			Element attrParentElement = attr.getParent();
			// can be null if no parent or parent is root
			Element parent = attrParentElement.getParentElement(); 
			if (!THUtils.isNull(parent)) {
				returnValue.append(parent.getName());
				returnValue.append("/");
				returnValue.append(attrParentElement.getName());
				returnValue.append("@"); // using the @ since it's how xpath
											// denotes an attribute
				returnValue.append(attr.getName());
			} else {
				returnValue.append(attrParentElement.getName());
				returnValue.append(" ");
				returnValue.append(attr.getName());
			}
		} catch (Exception e) {
			// something bad happened, just log.
			logger.error("Exception creating short name: " + e.getStackTrace());
		}
		return returnValue.toString();
	}

	
	
	public static String generateXpathForElement(final Element node) {
		return XPathHelper.getAbsolutePath(node);
	}

	public static String getDocumentAsString(final Document document) {
		final XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat().setOmitDeclaration(true).setExpandEmptyElements(true));

		return xmlOutputter.outputString(document);
	}

	public static String getDocumentAsStringWithDecl(final Document document) {
		final XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat().setOmitDeclaration(false).setExpandEmptyElements(true));

		return xmlOutputter.outputString(document);
	}

	public static String getDocumentAsStringNoEscapeStrategy(final Document document) {

		final XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat().setOmitDeclaration(false).setExpandEmptyElements(true));

		xmlOutputter.getFormat().setEscapeStrategy(new EscapeStrategy() {

			public boolean shouldEscape(char ch) {
				// TODO Auto-generated method stub
				return false;
			}
		});

		return xmlOutputter.outputString(document);
	}

	public static String getElementAsString(final Element document) {
		final XMLOutputter xmlOutputter = new XMLOutputter();
		return xmlOutputter.outputString(document);
	}

	public static String getElementAsStringTrimWhitSpace(final Element element) {
		final XMLOutputter xmlOutputter = new XMLOutputter(Format.getCompactFormat().setOmitDeclaration(true).setExpandEmptyElements(true).setTextMode(TextMode.TRIM));

		return xmlOutputter.outputString(element);
	}

	public static Document getStringAsDocument(final String inputString) throws Exception {
		Document document;
		try {
			document = JdomHelper.getSAXBuilder().build(new StringReader(inputString));
		} catch (JDOMException e) {
			throw new Exception("JDOMException in creating a document ", e);
		} catch (IOException e) {
			throw new Exception("IOException in creating a document ", e);
		} catch (Exception e) {
			throw new Exception("Error processing the document : " + inputString, e);
		}
		return document;
	}

	public static Document getStringAsDocumentWithoutNamespaces(final String inputString) throws Exception {
		Document document;
		try {
			document = JdomHelper.getNoNamespaceSAXBuilder().build(new StringReader(inputString));
		} catch (JDOMException e) {
			throw new Exception("JDOMException in creating a document ", e);
		} catch (IOException e) {
			throw new Exception("IOException in creating a document ", e);
		} catch (Exception e) {
			throw new Exception("Error processing the document : " + inputString, e);
		}
		return document;
	}
	
	public static Document replaceElementValue(final Document newDoc, final String targetElementXPath, final String newValue) throws Exception {
		try {
			XPathExpression<Element> xpath = XPathFactory.instance().compile(targetElementXPath, Filters.element());
			Element elementToReplace = xpath.evaluateFirst(newDoc);
			if (elementToReplace != null) {
				elementToReplace.setText(newValue);
			} else {
				XPathExpression<Attribute> attrXpath = XPathFactory.instance().compile(targetElementXPath, Filters.attribute());
				Attribute attrToReplace = attrXpath.evaluateFirst(newDoc);
				if (attrToReplace != null) {
					//if attribute and found, replace
					attrToReplace.setValue(newValue);
				}
//				if (attrToReplace == null) {
//					throw new Exception("Target content is not an Element/Attribute object. " //$NON-NLS-1$
//							+ "XPath must resolve to an Element or the value of the Elemnet cannot be replaced."); //$NON-NLS-1$
//
//				}
			}
		} catch (Exception e) {
			throw new Exception("exception in selecting single node", e);
		}
		return newDoc;
	}

	public static String getElementValue(final Document document, final String targetElementXPath) throws Exception {
		try {
			XPathExpression<Element> xpath = XPathFactory.instance().compile(targetElementXPath, Filters.element());
			Element element = xpath.evaluateFirst(document);
			if (element != null) {
				return element.getText();
			} else {
				XPathExpression<Attribute> attrXpath = XPathFactory.instance().compile(targetElementXPath, Filters.attribute());
				Attribute attrToReplace = attrXpath.evaluateFirst(document);
				if (attrToReplace != null) {
					return attrToReplace.getValue();
				}
			}
		} catch (Exception e) {
			throw new Exception("exception in selecting single node", e);
		}
		return null;
	}

	/*
	 * returns the first element matched for the give XPATH input
	 */
	public static Element getFirstElementMatched(final Object ctx, final String targetElementXPath) {
		try {
			XPathExpression<Element> xpath = XPathFactory.instance().compile(targetElementXPath, Filters.element());
			return xpath.evaluateFirst(ctx);
		} catch (Exception e) {
			// TODO log here
			return null;
		}
	}

	/*
	 * returns the first element matched for the give XPATH input
	 */
	public static String getFirstElementContent(final Object ctx, final String targetElementXPath) {
		try {
			Element element = getFirstElementMatched(ctx, targetElementXPath);
			if (element != null) {
				return element.getText();
			}
			return null;
		} catch (Exception e) {
			// TODO log here
			return null;
		}
	}

	/*
	 * returns the first element matched for the give XPATH input
	 */
	public static String getFirstElementAttributeContent(final Object ctx, final String targetElementXPath, String attrName) {
		String returnValue = "";
		try {
			Element element = getFirstElementMatched(ctx, targetElementXPath);
			if (element != null) {
				// get attributes
				for (final Attribute attr : element.getAttributes()) {
					if (attr.getName().equals(attrName)) {
						returnValue = attr.getValue();
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error getting attribute " + attrName +" off xpath " + targetElementXPath);
		}
		return returnValue;
	}
	
	/*
	 * returns the first element matched for the give XPATH input
	 */
	public static Object getFirstMatchElement(final Object document, final String targetElementXPath) {
		try {
			XPathExpression<Object> xpath = XPathFactory.instance().compile(targetElementXPath);
			return xpath.evaluateFirst(document);
		} catch (Exception e) {
			// TODO log here
			return null;
		}
	}

	/*
	 * returns the first element matched for the give XPATH input
	 */
	public static Object getFirstObjectMatched(final Element node, final String targetElementXPath) {
		try {
			XPathExpression<Object> xpath = XPathFactory.instance().compile(targetElementXPath);
			return xpath.evaluateFirst(node);
		} catch (Exception e) {
			// TODO log here
			return null;
		}
	}

	/*
	 * returns the element matched for the give XPATH input
	 */
	public static List<Element> getAllElementsMatched(final Document document, final String targetElementXPath) {
		try {
			XPathExpression<Element> xpath = XPathFactory.instance().compile(targetElementXPath, Filters.element());
			return xpath.evaluate(document);
		} catch (Exception e) {
			// TODO log here
			return null;
		}
	}

	/*
	 * returns the first element matched for the give XPATH input
	 */
	public static List<Element> getAllElementsMatched(final Element node, final String targetElementXPath) {
		try {
			XPathExpression<Element> xpath = XPathFactory.instance().compile(targetElementXPath, Filters.element());
			return xpath.evaluate(node);
		} catch (Exception e) {
			// TODO log here
			return null;
		}
	}

	public static String getElementName(String xpath) {
		return xpath.substring(xpath.lastIndexOf("/") + 1, xpath.length());
	}

	/**
	 * This method compares 2 JDOM documents for given XPATHs. this method short
	 * circuits by returning false when ever it finds different values
	 * 
	 * @param document1
	 * @param document2
	 * @param xpaths
	 * @return boolean true if the both the documents have same values for given
	 *         XPATHS else false.
	 */
	public static boolean compareXMLsForSpecificXPaths(final Document document1, final Document document2, final Collection<String> xpaths) throws Exception, IOException {
		boolean result = true;
		for (String xpath : xpaths) {
			String valueOfXpathInDoc1 = getElementValue(document1, xpath);
			String valueOfXpathInDoc2 = getElementValue(document2, xpath);
			if ((valueOfXpathInDoc1 == null && valueOfXpathInDoc2 != null) || (valueOfXpathInDoc1 != null && valueOfXpathInDoc2 == null)) {
				return false;
			} else if (valueOfXpathInDoc1 != null && valueOfXpathInDoc2 != null && !valueOfXpathInDoc1.equals(valueOfXpathInDoc2)) {
				return false;
			}
		}
		logger.debug("Is documents same : " + result);
		return result;
	}

	/**
	 * This method compares 2 XML documents for given XPATHs. this method short
	 * circuits by returning false when ever it finds different values
	 * 
	 * @param document1
	 * @param document2
	 * @param xpaths
	 * @return boolean true if the both the documents have same values for given
	 *         XPATHS else false.
	 */
	public static boolean compareXMLsForSpecificXPaths(final String document1, final String document2, final Collection<String> xpaths) throws Exception, IOException {
		return compareXMLsForSpecificXPaths(getStringAsDocument(document1), getStringAsDocument(document2), xpaths);
	}

	public static boolean compareFullXMLs(final String inputXML, final String compareXML) throws Exception, IOException {
		final Document document1 = XMLUtils.getStringAsDocument(inputXML);
		final Document document2 = XMLUtils.getStringAsDocument(compareXML);

		Map<String, String> xmlAsMap1 = new TreeMap<String, String>();
		XmlMergeUtils.generateAllXpaths(document1.getRootElement(), xmlAsMap1);
		Map<String, String> xmlAsMap2 = new TreeMap<String, String>();
		XmlMergeUtils.generateAllXpaths(document2.getRootElement(), xmlAsMap2);

		for (Map.Entry<String, String> entry : xmlAsMap1.entrySet()) {
			String key = entry.getKey();
			String valueOfXpathInDoc1 = entry.getValue();

			if (xmlAsMap2.containsKey(key)) {
				String valueOfXpathInDoc2 = xmlAsMap2.get(key);
				if ((valueOfXpathInDoc1 == null && valueOfXpathInDoc2 != null) || (valueOfXpathInDoc1 != null && valueOfXpathInDoc2 == null)) {
					return false;
				} else if (valueOfXpathInDoc1 != null && valueOfXpathInDoc2 != null && !valueOfXpathInDoc1.equals(valueOfXpathInDoc2)) {
					logger.debug(key + " : " + valueOfXpathInDoc1 + " : " + valueOfXpathInDoc2);
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Formats an xml string with line feeds and indents. Acts similar to the
	 * pretty-print in XmlSpy.
	 * 
	 * @param String
	 *            of XML
	 * @return formatted XML. If exception, then passed XML will be returned.
	 */
	public static String prettyPrint(String inXml) {
		final XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat().setOmitDeclaration(true).setExpandEmptyElements(true));
		try {
			return xmlOutputter.outputString(getStringAsDocument(inXml));
		} catch (Exception e) {
			return inXml;
		}
	}

	/**
	 * Gets a Document object based on a given xpath.
	 * 
	 * @param documentRootElement
	 * @param targetElementXPath
	 * @return Object of a Document. It would typically be either an Element or
	 *         Attribute
	 * @throws Exception
	 */
	public static Object getXpathObject(Element documentRootElement, final String targetElementXPath) throws Exception {
		Object returnValue = null;
		try {
			// XPathExpression<Element> xpath =
			// XPathFactory.instance().compile(targetElementXPath,
			// Filters.element());
			XPathExpression<Object> xpath = XPathFactory.instance().compile(targetElementXPath);
			returnValue = xpath.evaluateFirst(documentRootElement);
		} catch (Exception e) {
			throw new Exception("exception in selecting single node", e);
		}
		return returnValue;
	}

	/**
	 * Creates a short xpath of 2 nodes in the document - the parent and the
	 * passed node. The return would be either 2 Elements as
	 * "parentElement/passedElement" or a parent node, passed node and
	 * attribute: "parentElement/passedElement attribute"
	 * 
	 * @param documentRootElement
	 * @param targetElementXPath
	 * @return
	 */
	public static String createXpathShortName(Element documentRootElement, final String targetElementXPath) {
		String returnValue = "";
		try {
			Object xpathObj = XMLUtils.getXpathObject(documentRootElement, targetElementXPath);
			if (xpathObj instanceof Element) {
				Element element = (Element) xpathObj;
				returnValue = createXpathShortName(element);
			}
			if (xpathObj instanceof Attribute) {
				Attribute attr = (Attribute) xpathObj;
				returnValue = createXpathShortName(attr);
			}
		} catch (Exception e) {
			// something bad happened, just log.
			logger.error("Exception creating short name: " + e.getStackTrace());
		}
		return returnValue;
	}

	/**
	 * Creates a short xpath of 2 nodes in the document - the parent and the
	 * passed node. The return would be either 2 Elements as
	 * "parentElement/passedElement" or a parent node, passed node and
	 * attribute: "parentElement/passedElement attribute"
	 * 
	 * @param documentRootElement
	 * @param targetElementXPath
	 * @return
	 */
	public static String getRelativePathWithoutNamespaces(final Element fromElement, final Object toElementORAttribute) {

		StringBuilder pathString = new StringBuilder("");

		Object toElement = toElementORAttribute;

		try {
			int i = 0;
			String toObjName = getDomObjName(toElement);
			// May have to consider Namespace too
			while (!THUtils.isNull(toElement) && !fromElement.getName().equals((toObjName))) {

				if (i != 0) {
					pathString.insert(0, "/");
				}

				pathString.insert(0, toObjName);

				if (toElement instanceof Attribute) {
					pathString.insert(0, "@");
				}

				toElement = getParent(toElement);
				toObjName = getDomObjName(toElement);
				++i;

			}
		} catch (Exception e) {
			// something bad happened, just log.
			logger.error("Exception creating short name: " + e.getStackTrace());
		}
		return pathString.toString();
	}

	public static String getDomObjName(Object domObj) {

		if (!THUtils.isNull(domObj)) {
			if (domObj instanceof Attribute) {
				return ((Attribute) domObj).getName();
			}

			if (domObj instanceof Element) {
				return ((Element) domObj).getName();
			}
		}

		return "";

	}

	public static Element getParent(Object domObj) {

		if (!THUtils.isNull(domObj)) {
			if (domObj instanceof Attribute) {
				return ((Attribute) domObj).getParent();
			}

			if (domObj instanceof Element) {
				return ((Element) domObj).getParentElement();
			}
		}

		return null;
	}

	public static Document removeEmptyNodes(Document document) throws Exception {
		Document documentClone = document.clone();
		final XPathExpression<Element> xpath = XPathFactory.instance().compile("//node()", Filters.element());
		final List<Element> elements = xpath.evaluate(documentClone);

		try {
			for (final Element element : elements) {
				boolean isAttrDitached = false;
				final List<Attribute> attributes = element.getAttributes();
				for (Iterator<Attribute> iterator = attributes.iterator(); iterator.hasNext();) {
					Attribute attribute = (Attribute) iterator.next();
					logger.debug(attribute.getName() + " : " + attribute.getValue());
					if (attribute.getValue() == null || attribute.getValue().trim().equals("")) {
						iterator.remove();
						attribute.detach();
						isAttrDitached = true;
					}
				}
				logger.debug(element.getName() + " : " + element.getValue());
				if (!isAttrDitached && (element.getValue() == null || element.getValue().trim().equals(""))) {
					element.detach();
				}
			}
		} catch (Exception e) {
			logger.debug("exception in removing Empty Nodes");
			e.printStackTrace();
		}
		// If everything removed, it is already empty, Just return the original;
		if (documentClone == null || !documentClone.hasRootElement()) {
			return document;
		}
		document = documentClone;
		return documentClone;
	}

	public static Document replaceValuesInXML(final Document document, final String from, final String to) throws Exception {
		final XPathExpression<Element> xpath = XPathFactory.instance().compile("//node()", Filters.element());
		final List<Element> elements = xpath.evaluate(document);

		try {
			for (final Element content : elements) {
				if (content.getText().trim().equals(from)) {
					content.setText(to);
				}
				final List<Attribute> attributes = content.getAttributes();
				for (final Attribute attribute : attributes) {
					if (attribute.getValue() != null && attribute.getValue().trim().equals(from)) {
						attribute.setValue(to);
					}

				}
			}
		} catch (Exception e) {
			logger.debug("exception with gen template");
		}
		return document;
	}

	/**
	 * Removes any node attributes with xmlns:
	 * @param document
	 */
	public static void removeNamespaceAttributes(Document document) {
		Element root = document.getRootElement();

		if (root.getChildren() != null && root.getChildren().size() > 0) {
			for (Element child : root.getChildren()) {
				removeElementNameSpaces(child);
			}
		}

		// replace the root element so that all namespaces are removed.
		Element newroot = new Element(root.getName());
		newroot.addContent(root.cloneContent());
		document.setRootElement(newroot);
	}

	/**
	 * removes element attribute prefix xmlns:
	 * @param element
	 */
	public static void removeElementNameSpaces(Element element) {
		element.setNamespace(null);
		if (element.getAttributes() != null && element.getAttributes().size() > 0) {
			for (Attribute attr : element.getAttributes()) {
				if (attr.getName().startsWith("xmlns:")) {
					attr.detach();
				}
			}
		}
		if (element.getChildren() != null && element.getChildren().size() > 0) {
			for (Element child : element.getChildren()) {
				removeElementNameSpaces(child);
			}
		}
	}
	
	public static String removeNamespaces(String xml) {
		String returnValue = "";
		try {
			Document doc = XMLUtils.getStringAsDocumentWithoutNamespaces(xml);
			returnValue = XMLUtils.getDocumentAsString(doc);
		} catch (Exception e) {
			returnValue = xml;
		}
		return returnValue;
	}
	
	public static Document removeNamespaces(Document document) {
		Document returnValue = null;
		try {
			String docstring = XMLUtils.getDocumentAsString(document);
			returnValue = XMLUtils.getStringAsDocumentWithoutNamespaces(docstring);
		} catch (Exception e) {
			returnValue = document;
		}
		return returnValue;
	}

	public static void addAttribute(Element element, String attrName, String attrValue) {
		if (element != null) {
			element.setAttribute(attrName, attrValue);
		} else {
			logger.warn("Element is NULL");
		}
	}

	public static void removeElement(Document document, String xpath) {

		Element elementMatched = getFirstElementMatched(document, xpath);

		if (elementMatched != null) {
			elementMatched.detach();
		} else {
			logger.warn("Element is NULL");
		}
	}

	public static boolean isValidXML(String inXml) {
		try {
			XMLUtils.getStringAsDocument(inXml);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Replaces all values for a specific node name.
	 * 
	 * @param document
	 * @param nodename
	 *            name of the node (NOT the xpath, just node name)
	 * @param to
	 *            value to set.
	 * @return
	 * @throws Exception
	 */
	public static Document replaceNodeValuesInXML(final Document document, String nodename, final String to) throws Exception {
		final XPathExpression<Element> xpath = XPathFactory.instance().compile("//node()", Filters.element());
		final List<Element> elements = xpath.evaluate(document);

		try {
			for (final Element content : elements) {
				if (content.getName().equalsIgnoreCase(nodename)) {
					content.setText(to);
				}
			}
		} catch (Exception e) {
			logger.debug("exception with setting node value." + e.getMessage());
		}
		return document;
	}

	/**
	 * Ensures the input text is xml.
	 * 
	 * @param inText
	 *            Accepts both xml and json string
	 * @return xml string or empty string if not valid.
	 */
	public static String transformXml(String inText) {
		String returnValue = "";

		if (XMLUtils.isValidXML(inText)) {
			returnValue = inText;
		} else {
			if (JSONUtils.isJSONValid(inText)) {
				returnValue = JSONUtils.jsonToXml(inText);
			}
		}
		return returnValue;
	}

	/**
	 * @return new Document with root element <root>
	 */
	public static Document createEmptyDocument() {
		Element company = new Element("root");
		Document doc = new Document(company);
		return doc;
	}

	/**
	 * @param rootName
	 * @return new Document with the specified root element.
	 */
	public static Document createEmptyDocument(String rootName) {
		Element rootElem = new Element(rootName);
		Document doc = new Document(rootElem);
		return doc;
	}

	/**
	 * Removes all attributes matching the supplied name.
	 * 
	 * @param document
	 * @param attributeName
	 * @return
	 */
	public static Document removeAttributes(final Document document, final String attributeName) {
		final XPathExpression<Element> xpath = XPathFactory.instance().compile("//node()", Filters.element());
		final List<Element> elements = xpath.evaluate(document);
		try {
			for (final Element content : elements) {
				content.removeAttribute(attributeName);
			}
		} catch (Exception e) {
			logger.error("Error removing attributes: " + e.getMessage());
		}
		return document;
	}

	/**
	 * Removes all attributes
	 * 
	 * @param document
	 * @return
	 */
	public static Document removeAttributes(final Document document) {
		final XPathExpression<Element> xpath = XPathFactory.instance().compile("//node()", Filters.element());
		final List<Element> elements = xpath.evaluate(document);
		try {
			for (final Element content : elements) {

				for (final Attribute attr : content.getAttributes()) {
					content.removeAttribute(attr.getName());
				}
			}
		} catch (Exception e) {
			logger.error("Error removing attributes: " + e.getMessage());
		}
		return document;
	}

	/**
	 * converts JDOM to DOM
	 * @param document
	 * @return
	 */
	public static org.w3c.dom.Document toDOMDocument(Document document) {
		org.w3c.dom.Document domdoc = null;
		try {
			DOMOutputter dout = new DOMOutputter();
			domdoc = dout.output(document);		
		} catch (JDOMException e) {
			logger.error("Error converting jdom to dom : " + e.getMessage());
		}
		return domdoc;
	}
	
}
