package com.teaas.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.jdom2.xpath.XPathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlMergeUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(XmlMergeUtils.class);

	public static void generateAllXpaths(final Element node, final Map<String, String> xmlAsMap) {
		xmlAsMap.put(XPathHelper.getAbsolutePath(node), node.getTextTrim());
		// get attributes
		for (final Attribute attr : node.getAttributes())
			xmlAsMap.put(XPathHelper.getAbsolutePath(attr), attr.getValue());

		// process children nodes
		if (node.getChildren() != null && node.getChildren().size() > 0) {
			for (final Element childNode : node.getChildren())
				generateAllXpaths(childNode, xmlAsMap);
			return;
		}

		return;
	}

	public static Set<String> getDifferencialXpaths(final String inputXML, final String compareXML) throws Exception {
		final Document document1 = XMLUtils.getStringAsDocument(inputXML);
		final Document document2 = XMLUtils.getStringAsDocument(compareXML);
		Map<String, String> xmlAsMap1 = new TreeMap<String, String>();
		generateAllXpaths(document1.getRootElement(), xmlAsMap1);
		Map<String, String> xmlAsMap2 = new TreeMap<String, String>();
		generateAllXpaths(document2.getRootElement(), xmlAsMap2);
		xmlAsMap2.keySet().removeAll(xmlAsMap1.keySet());
		Set<String> differentialXPaths = new TreeSet<String>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (o1.length() >= o2.length()) {
					return 1;
				} else
					return -1;
				
			}
		});
		differentialXPaths.addAll(xmlAsMap2.keySet());
		return differentialXPaths;
	}

	/**
	 * This method merges two XMl files. the first document id treated as base
	 * and the extra elements in second document will be copied into the base
	 * document.
	 * 
	 * @param inputXML - the primary xml in which data is being merged into
	 * @param compareXML - the xml that data is merging from.
	 * @return
	 * @throws Exception
	 */
	public static Document mergeAll(final String inputXML, final String compareXML) throws Exception {
		final Set<String> diffXpaths = getDifferencialXpaths(inputXML, compareXML);
		for (String xpath : diffXpaths) {
			logger.debug("diffXpath: " + xpath);			
		}
		Document base = XMLUtils.getStringAsDocument(inputXML);
		if (diffXpaths != null && diffXpaths.size() > 0) {
			final Document other = XMLUtils.getStringAsDocument(compareXML);
			for (final String xpath : diffXpaths) {
				base = merge(xpath, base, other);
			}
		}
		return base;
	}

	private static Document merge(String xpath, Document base, Document toBeMerged) throws Exception {
		try {
			XPathExpression<Element> xpathElement = XPathFactory.instance().compile(xpath, Filters.element());

			// get the element from merged XML
			Document other = toBeMerged.clone();
			Namespace otherns = other.getRootElement().getNamespace();
			Element element = xpathElement.evaluateFirst(other);
			
			// If it is it an element - it should evaluate to attribute.
			if (element != null) {
				Element baseElement = xpathElement.evaluateFirst(base);
				if (baseElement == null) {
					//if the element doesnt exist in the base, we will add it.
//					String parentXPath = xpath.substring(0, xpath.lastIndexOf("/"));
					String parentXPath = XPathHelper.getAbsolutePath(element.getParentElement());
					XPathExpression<Element> parentElement = XPathFactory.instance().compile(parentXPath, Filters.element());
					baseElement = parentElement.evaluateFirst(base);
					element.detach();
					baseElement.getChildren().add(element);
				}

			} else {
				// If it is it an attribute - get the parent element and attach.
				XPathExpression<Attribute> attrXpath = XPathFactory.instance().compile(xpath, Filters.attribute());
				Attribute attrToReplace = attrXpath.evaluateFirst(other);
				if (attrToReplace != null) {
					String parentXPath = xpath.substring(0, xpath.lastIndexOf("/@"));
					XPathExpression<Element> parentElement = XPathFactory.instance().compile(parentXPath, Filters.element());
					Element baseElement = parentElement.evaluateFirst(base);
					attrToReplace.detach();
					baseElement.getAttributes().add(attrToReplace);
				}
			}
		} catch (Exception e) {
			throw new Exception("exception in merging documents", e);
		}
		return base;
	}

}
