package com.teaas.utils;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.output.XMLOutputter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;

public class XMLUtilsTest extends TestCase {
	
	private static final Logger logger = LoggerFactory.getLogger(XMLUtilsTest.class);

	@Test
	public void testgenerateTemplateTest() throws Exception {
		final String inputXML = "<employee id ='1'><name>Tester</name><company><name>UHG</name><address></address></company></employee>";
		final String template = XMLUtils.generateTemplate(inputXML);
		final String expected = "<employee id =''><name></name><company><name></name><address></address></company></employee>";
		assertEquals(template, XMLUtils.generateTemplate(expected));
		System.out.println(template);
	}

	@Test
	public void testgetFillTemplateTest() throws Exception {
		final String inputXML = "<employee id ='2'><name>developer</name><company><name></name><address></address></company></employee>";
		final Document document = XMLUtils.getStringAsDocument(inputXML);
		final Map<String, String> xpathsMap = new HashMap<String, String>();
		xpathsMap.put("/employee/@id", "1");
		xpathsMap.put("/employee/name", "Tester");
		xpathsMap.put("/employee/company/name", "UHG");
		final Document filledDocument = XMLUtils.fillTemplate(document, xpathsMap);
		String expected = "<employee id='1'><name>Tester</name><company><name>UHG</name><address/></company></employee>";
		String expectedDoc = XMLUtils.getDocumentAsString(XMLUtils.getStringAsDocument(expected)); 
		assertEquals(XMLUtils.getDocumentAsString(filledDocument), expectedDoc);
		logger.debug(new XMLOutputter().outputString(filledDocument));
	}

	@Test
	public void getFillTemplateTest_WithArrayelement() throws Exception {
		final String inputXML = "<employee id ='2'><name>developer</name><company><name></name><address></address></company></employee>";
		final Document document = XMLUtils.getStringAsDocument(inputXML);
		final Map<String, String> xpathsMap = new HashMap<String, String>();
		xpathsMap.put("/employee/@id", "1");
		xpathsMap.put("/employee/name", "Tester");
		xpathsMap.put("/employee/company[1]/address", "UHG-HYD");
		final Document filledDocument = XMLUtils.fillTemplate(document, xpathsMap);
		String expected = "<employee id='1'><name>Tester</name><company><name /><address>UHG-HYD</address></company></employee>";
		String expectedDoc = XMLUtils.getDocumentAsString(XMLUtils.getStringAsDocument(expected)); 
		assertEquals(XMLUtils.getDocumentAsString(filledDocument), expectedDoc);
		logger.debug(new XMLOutputter().outputString(filledDocument));
	}
	
	@Test
	public void testGetXpathsTest() throws Exception {
		final String inputXML = "<?xml version='1.0' encoding='UTF-8'?>" +
				"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>" +
				"	<soapenv:Body>" +
				"		<ovat:readMemberDetail xmlns:ovat='http://upm.uhc.com/ovationsmember'>" +
				"			<arg0>" +
				"				<requestHeader>" +
				"					<applicationName>ILEAD</applicationName>" +
				"					<applicationInstanceName>ILEAD</applicationInstanceName>" +
				"					<logLevel></logLevel>" +
				"					<serviceOption>" +
				"						<key>SOAP_FAULTS_AS_STATUS_MESSAGE_BEANS</key>" +
				"						<value>true</value>" +
				"					</serviceOption>" +
				"				</requestHeader>" +
				"				<individualId>12345678</individualId>" +
				"				<gpsSystemParameters>" +
				"					<clientId>ILEAD</clientId>" +
				"					<userId>ktest</userId>" +
				"				</gpsSystemParameters>" +
				"			</arg0>" +
				"		</ovat:readMemberDetail>" +
				"	</soapenv:Body>" +
				"</soapenv:Envelope>" ;
		
		
		String inputXML2 = THFileUtils.getFileSystemFileAsString("P://CoaService//fault.XML");
		
		
		final Document document = XMLUtils.getStringAsDocument(inputXML2);
		final Map<String, String> xpathsMap = new HashMap<String, String>();
		XMLUtils.generateXpaths(document.getRootElement(), xpathsMap);
		for (Iterator<String> iterator = xpathsMap.keySet().iterator(); iterator.hasNext();) {
			String type = iterator.next();
			System.out.println(type + " : "+xpathsMap.get(type));
			
		}
		
		//assertTrue(xpathsMap.size() == 8);

	}
	

	@Test
	public void compareXMLsForSpecificXPathsTestFalse() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name></name><address></address></company></employee>";
		final String inputXML2 = "<employee id ='2'><name>tester</name><company><name></name><address></address></company></employee>";
		final Document document1 = XMLUtils.getStringAsDocument(inputXML1);
		final Document document2 = XMLUtils.getStringAsDocument(inputXML2);
		final List<String> xpaths = new ArrayList<String>();
		xpaths.add("/employee/@id");
		xpaths.add("/employee/name");
		final boolean sameXMLs = XMLUtils.compareXMLsForSpecificXPaths(document1, document2, xpaths);
		assertFalse(sameXMLs);
		logger.debug("Is documents same : "+sameXMLs);
	}
	
	@Test
	public void compareXMLsForSpecificXPathsTestTrue1() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
		final String inputXML2 = "<employee id ='2'><name>developer</name><company><name>GSD</name><address>HYD</address></company></employee>";
		final Document document1 = XMLUtils.getStringAsDocument(inputXML1);
		final Document document2 = XMLUtils.getStringAsDocument(inputXML2);
		final List<String> xpaths = new ArrayList<String>();
		xpaths.add("/employee/@id");
		xpaths.add("/employee/name");
		final boolean sameXMLs = XMLUtils.compareXMLsForSpecificXPaths(document1, document2, xpaths);
		assertTrue(sameXMLs);
		logger.debug("Is documents same : "+sameXMLs);
	}
	
	@Test
	public void compareXMLsForSpecificXPathsTestTrue2() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
		final String inputXML2 = "<employee id ='2'><name>tester</name><company><name>UHG</name><address>HYD</address></company></employee>";
		final Document document1 = XMLUtils.getStringAsDocument(inputXML1);
		final Document document2 = XMLUtils.getStringAsDocument(inputXML2);
		final List<String> xpaths = new ArrayList<String>();
		xpaths.add("/employee/@id");
		xpaths.add("/employee/company/name");
		final boolean sameXMLs = XMLUtils.compareXMLsForSpecificXPaths(document1, document2, xpaths);
		assertTrue(sameXMLs);
		logger.debug("Is documents same : "+sameXMLs);
	}
	
	@Test
	public void compareXMLsForSpecificXPathsTestString() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
		final String inputXML2 = "<employee id ='2'><name>tester</name><company><name>UHG</name><address>HYD</address></company></employee>";
		final List<String> xpaths = new ArrayList<String>();
		xpaths.add("/employee/@id");
		xpaths.add("/employee/company/name");
		final boolean sameXMLs = XMLUtils.compareXMLsForSpecificXPaths(inputXML1, inputXML2, xpaths);
		assertTrue(sameXMLs);
		logger.debug("Is documents same : "+sameXMLs);
	}
	
	@Test
	public void compareFullXMLSfalse() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
		final String inputXML2 = "<employee id ='2'><name>tester</name><company><name>UHG</name><address>US</address></company></employee>";

		final boolean sameXMLs = XMLUtils.compareFullXMLs(inputXML1, inputXML2);
		logger.debug("Is documents same : "+sameXMLs);
		assertFalse(sameXMLs);
		
	}
	@Test
	public void compareFullXMLS() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
		final String inputXML2 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";

		final boolean sameXMLs = XMLUtils.compareFullXMLs(inputXML1, inputXML2);
		logger.debug("Is documents same : "+sameXMLs);
		assertTrue(sameXMLs);
		
	}
	
	@Test
	public void testGetStringAsDocumentException() throws Exception {
		final String inputXML1 = "<employee id ='2'/><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
		try {
			XMLUtils.getStringAsDocument(inputXML1);
			fail("fail with JDOM exception");
		} catch (Exception e) {
			
		}		
		
	}
	
	@Test
	public void testGetStringAsDocumentIOException() throws Exception {
		final String inputXML1 = new File("/test.xml").getPath();
		try {
			XMLUtils.getStringAsDocument(inputXML1);
			fail("fail with IO exception");
		} catch (Exception e) {

		}		
		
	}
	
	
	@Test
	public void testPrettyPrint() throws Exception {
		final String inputXML1 = "<employee id ='2'><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
			final String formatted = XMLUtils.prettyPrint(inputXML1);
			assertNotNull(formatted);
			assertNotSame(inputXML1, formatted);
	}
	
	@Test
	public void testPrettyPrintFail() throws Exception {
		final String inputXML1 = "<employee id ='2'/><name>developer</name><company><name>UHG</name><address>US</address></company></employee>";
			final String formatted = XMLUtils.prettyPrint(inputXML1);
			assertNotNull(formatted);
			assertSame(inputXML1, formatted);
	}

	
	@Test
	public void removeEmptyNodesTest() throws Exception {
		final String inputXML = "<?xml version='1.0' encoding='UTF-8'?>" +
				"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>" +
				"	<soapenv:Body>" +
				"		<ovat:readMemberDetail xmlns:ovat='http://upm.uhc.com/ovationsmember'>" +
				"			<arg0 id='1' test='' test3='' test4='' test5='11'>" +
				"				<requestHeader>" +
				"					<applicationName>ILEAD</applicationName>" +
				"					<applicationInstanceName>ILEAD</applicationInstanceName>" +
				"					<logLevel></logLevel>" +
				"					<serviceOption>" +
				"						<key>SOAP_FAULTS_AS_STATUS_MESSAGE_BEANS</key>" +
				"						<value>true</value>" +
				"					</serviceOption>" +
				"				</requestHeader>" +
				"				<individualId>12345678</individualId>" +
				"				<gpsSystemParameters>" +
				"					<clientId></clientId>" +
				"					<userId>ktest</userId>" +
				"				</gpsSystemParameters>" +
				"			</arg0>" +
				"		</ovat:readMemberDetail>" +
				"	</soapenv:Body>" +
				"</soapenv:Envelope>" ;
		
		final Document document = XMLUtils.getStringAsDocument(inputXML);
		String documentAsString = XMLUtils.getDocumentAsString(XMLUtils.removeEmptyNodes(document));
		System.out.println(XMLUtils.prettyPrint(documentAsString));
	}
	
	@Test
	public void removeEmptyNodesTestEmpty() throws Exception {
		final String inputXML = "<?xml version='1.0' encoding='UTF-8'?>" +
				"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>" +
				"	<soapenv:Body>" +
				"		<ovat:readMemberDetail xmlns:ovat='http://upm.uhc.com/ovationsmember'>" +
				"			<arg0>" +
				"				<individualId id='1' test=''>11</individualId>" +
				"				<gpsSystemParameters>" +
				"					<clientId></clientId>" +
				"					<userId></userId>" +
				"				</gpsSystemParameters>" +
				"			</arg0>" +
				"		</ovat:readMemberDetail>" +
				"	</soapenv:Body>" +
				"</soapenv:Envelope>" ;
		
		final Document document = XMLUtils.getStringAsDocument(inputXML);
		String documentAsString = XMLUtils.getDocumentAsString(XMLUtils.removeEmptyNodes(document));
		System.out.println(XMLUtils.prettyPrint(documentAsString));
	}
	
	@Test
	public void replaceValuesXMLTest() throws Exception {
		final String inputXML = "<?xml version='1.0' encoding='UTF-8'?>" +
				"<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'>" +
				"	<soapenv:Body>" +
				"		<ovat:readMemberDetail xmlns:ovat='http://upm.uhc.com/ovationsmember'>" +
				"			<arg0 id='1' test='' test3='' test4='' test5='11'>" +
				"				<requestHeader>" +
				"					<applicationName>ILEAD</applicationName>" +
				"					<applicationInstanceName>ILEAD</applicationInstanceName>" +
				"					<logLevel></logLevel>" +
				"					<serviceOption>" +
				"						<key>SOAP_FAULTS_AS_STATUS_MESSAGE_BEANS</key>" +
				"						<value>true</value>" +
				"					</serviceOption>" +
				"				</requestHeader>" +
				"				<individualId>12345678</individualId>" +
				"				<gpsSystemParameters>" +
				"					<clientId></clientId>" +
				"					<userId>ktest</userId>" +
				"				</gpsSystemParameters>" +
				"			</arg0>" +
				"		</ovat:readMemberDetail>" +
				"	</soapenv:Body>" +
				"</soapenv:Envelope>" ;
		//System.out.println(inputXML);
		System.out.println((XMLUtils.getDocumentAsString(XMLUtils.getStringAsDocument(inputXML))));
		final Document document = XMLUtils.replaceValuesInXML(XMLUtils.getStringAsDocument(inputXML), "ILEAD", "ITEST");
		
		System.out.println(XMLUtils.getDocumentAsString((document)));
	}
	
	
	
}
