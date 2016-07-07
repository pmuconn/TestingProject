package com.teaas.utils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.SimpleConverter;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParserException;

import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.copy.HierarchicalStreamCopier;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.XppReader;
import com.thoughtworks.xstream.io.xml.xppdom.XppFactory;

public class JSONUtils {

	private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
	public static ObjectMapper mapper = new ObjectMapper();

	public static <T> Collection<T> getGenericCollectionObjectFromJSON(String json, Class<T> class1) {

		try {
			/*
			 * public static <T> T fromJSON(final TypeReference<T> type, final
			 * String jsonPacket) { T data = null;
			 * 
			 * try { data = new ObjectMapper().readValue(jsonPacket, type); }
			 * catch (Exception e) { // Handle the problem } return data; }
			 */

			;
			// return mapper.readValue(json, new
			// TypeReference<Collection<T>>(){});
			return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(Collection.class, class1));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getJSONFromObject(Object o) {
		try {
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Performs mapping of Json string data to an associated Object.
	 * 
	 * @param json
	 * @param targetType
	 * @return
	 */
	public static <T> T getObjectFromJSON(String json, Class<T> targetType) {
		try {
			mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(json, targetType);
		} catch (JsonParseException e) {
			logger.error("Error mapping JSON object: " + targetType.getName() + ", " + e.getMessage());
			return null;
		} catch (JsonMappingException e) {
			logger.error("Error mapping JSON object: " + targetType.getName() + ", " + e.getMessage());
			return null;
		} catch (IOException e) {
			logger.error("Error mapping JSON object: " + targetType.getName() + ", " + e.getMessage());
			return null;
		}
	}

	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
			return true;
		} catch (JSONException ex) {
			return false;
		}
	}

	/**
	 * Converts the JSON to XML. <strong>Please not that the input JSON holds
	 * valid namespaces and attribute identifies or the JSON should be created
	 * using the xmlToJson method</strong>
	 * 
	 * @param json
	 * @return xml
	 */
	public static String jsonToXml(String json) {
		StringWriter buffer = new StringWriter();
		try {
			Configuration config = new Configuration();
			config.setTypeConverter(new SimpleConverter());
			HierarchicalStreamCopier copier = new HierarchicalStreamCopier();
			HierarchicalStreamDriver jsonDriver = new JettisonMappedXmlDriver(config);
			CompactWriter cw = new CompactWriter(buffer);
			HierarchicalStreamReader sr = jsonDriver.createReader(new StringReader(json));
			copier.copy(sr, cw);
		} catch (Exception e) {
			logger.error("Error converting json to xml: " + e.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * Converts the JSON to XML. <strong>Please not that the input JSON holds
	 * valid namespaces and attribute identifies or the JSON should be created
	 * using the xmlToJson method</strong>
	 * 
	 * @param json
	 * @return xml
	 */
	public static String jsonToXml(String json, String rootNodeName) {
		StringWriter buffer = new StringWriter();
		try {
			// apply root node to json string
			String prefix = "{\"" + rootNodeName + "\":";
			String suffix = "}";
			String jsonWithRoot = prefix + json + suffix;
			json = jsonWithRoot;

			Configuration config = new Configuration();
			config.setTypeConverter(new SimpleConverter());
			HierarchicalStreamCopier copier = new HierarchicalStreamCopier();
			HierarchicalStreamDriver jsonDriver = new JettisonMappedXmlDriver(config);
			CompactWriter cw = new CompactWriter(buffer);
			HierarchicalStreamReader sr = jsonDriver.createReader(new StringReader(json));
			copier.copy(sr, cw);
		} catch (Exception e) {
			logger.error("Error converting json to xml: " + e.getMessage());
		}
		return buffer.toString();
	}

//	public static String prettyPrint(String json) {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			Object json1 = mapper.readValue(json, Object.class);
//			String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json1);
//			return output;
//		} catch (Exception e) {
//			logger.error("Error converting text to json object: " + e.getMessage());
//			return json;
//		}
//	}

	/**
	 * Converts a given XML to JSON.
	 * 
	 * @param xml
	 * @return json
	 */
	public static String xmlToJson(String xml) throws Exception {
		try {
			// set up a converter so that jettison doesn't try to convert what
			// it thinks is numeric data to json numeric - without quotes
			// ie "searchID": 659447317 we want to all data to be in quotes
			// like: "searchID": "659447317"
			Configuration config = new Configuration();
			config.setTypeConverter(new SimpleConverter());
			return xmlToJson(xml, config);
		} catch (Exception e) {
			throw new Exception("Unable to convert XML to JSON", e);
		}
	}

	private static String xmlToJson(String xml, Configuration config) throws Exception {
		try {
			xml = XMLUtils.getDocumentAsString(XMLUtils.getStringAsDocument(xml));
			HierarchicalStreamCopier copier = new HierarchicalStreamCopier();
			HierarchicalStreamDriver jsonDriver = new JettisonMappedXmlDriver(config);
			HierarchicalStreamReader sourceReader = new XppReader(new StringReader(xml), XppFactory.createDefaultParser());
			StringWriter buffer = new StringWriter();
			copier.copy(sourceReader, jsonDriver.createWriter(buffer));
			return buffer.toString();
		} catch (XmlPullParserException e) {
			throw new Exception("Unable to convert XML to JSON", e);
		}
	}

	public static String xmlToJsonForPublishing(String xml) throws Exception {
		try {
			Document document = XMLUtils.getStringAsDocument(xml);
			XMLUtils.removeNamespaceAttributes(document);
			xml = XMLUtils.getDocumentAsString(document);
			// set up a converter so that jettison doesn't try to convert what
			// it thinks is numeric data to json numeric - without quotes
			// ie "searchID": 659447317 we want to all data to be in quotes
			// like: "searchID": "659447317"
			Configuration config = new Configuration();
			config.setTypeConverter(new SimpleConverter());
	        config.setSupressAtAttributes(true); //don't put the @ on any attributes
			// dropping root element for publishing.
			config.setDropRootElement(true);
			return xmlToJson(xml, config);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String xmlToSimpleJSON(String xml) throws Exception {
		try {
			Document document = XMLUtils.getStringAsDocument(xml);
			XMLUtils.removeNamespaceAttributes(document);
			xml = XMLUtils.getDocumentAsString(document);
			// set up a converter so that jettison doesn't try to convert what
			// it thinks is numeric data to json numeric - without quotes
			// ie "searchID": 659447317 we want to all data to be in quotes
			// like: "searchID": "659447317"
			Configuration config = new Configuration();
			config.setTypeConverter(new SimpleConverter());
			return xmlToJson(xml, config);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
