package com.vsi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

public class VSIToTestCase {

    public static String prettyFormat(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String prettyFormat(String input) {
        return prettyFormat(input, 2);
    }

    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("No path given to VSI file!");
            return;
        }
        try{
            File fXmlFile = new File(args[0]);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            parseReqResponsePairs(doc,dBuilder);
        }catch(Exception E){
            System.out.println("ERROR: " + E.getMessage());
        }
    }
    public static void parseReqResponsePairs(Document doc,DocumentBuilder dBuilder) throws Exception{
            XPathFactory xFact = XPathFactory.newInstance();
            XPath xpath = xFact.newXPath();
            XPathExpression rspExp =  xpath.compile("/serviceImage/st/t/sp/t/rs/rp/bd/text()");
            XPathExpression reqExp = xpath.compile("/serviceImage/st/t/sp/t/rq/bd/text()");
            Object result = rspExp.evaluate(doc, XPathConstants.NODESET);
            NodeList rspNodes = (NodeList)result;
            result = reqExp.evaluate(doc,XPathConstants.NODESET);
            NodeList reqNodes = (NodeList)result;

            for (int i=0; i<rspNodes.getLength();i++){
                Node parent = reqNodes.item(i).getParentNode().getParentNode();
                XPathExpression argExp = xpath.compile("ag/p");
                result = argExp.evaluate(parent,XPathConstants.NODESET);
                NodeList argNodes = (NodeList)result;
                StringReader strR = new StringReader(reqNodes.item(i).getNodeValue());
                InputSource inS = new InputSource(strR);
                Document reqDoc = dBuilder.parse(inS);
                boolean changes = true;
                String testName = "";
                while(changes){
                    XPathExpression leaves = xpath.compile("//*[not(*)]");
                    result = leaves.evaluate(reqDoc,XPathConstants.NODESET);
                    NodeList leafNodes = (NodeList)result;
                    for(int j = 0; j<leafNodes.getLength();j++){
                        leafNodes.item(j).setTextContent(null);
                    }
                    for(int j=0; j<argNodes.getLength();j++){

                        String argName = argNodes.item(j).getAttributes().getNamedItem("n").getNodeValue();
                        String argVal = argNodes.item(j).getTextContent();
                        if(argName.equals("arg0_requestHeader_applicationName")) {
                            testName =  argVal;
                        }

                        String argXPath = "//" + argName.replaceAll("_","/");
                        XPathExpression argInReqExp = xpath.compile(argXPath);
                        result = argInReqExp.evaluate(reqDoc,XPathConstants.NODESET);
                        NodeList reqArgNodes = (NodeList)result;
                        for(int k=0; k<reqArgNodes.getLength();k++){
                            reqArgNodes.item(k).setTextContent(argVal);
                        }

                    }
                    changes = false;
                    XPathExpression cleanup = xpath.compile("//*[not(child::node()) and not(ancestor::requestHeader) and not(ancestor::controlModifier)] | //comment() | //text()[normalize-space(.) = '']");
                    result = cleanup.evaluate(reqDoc,XPathConstants.NODESET);
                    NodeList cleanupNodes = (NodeList)result;
                    for(int j=0;j<cleanupNodes.getLength();j++){
                        changes = true;
                        cleanupNodes.item(j).getParentNode().removeChild(cleanupNodes.item(j));
                    }
                }

                DOMImplementationLS domImplLS = (DOMImplementationLS) reqDoc.getImplementation();
                LSSerializer serializer = domImplLS.createLSSerializer();
                String str = serializer.writeToString(reqDoc);
                FileWriter fw = new FileWriter(i + "-" + testName + "-req.xml");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(prettyFormat(str));
                bw.close();

                fw = new FileWriter(i + "-" + testName + "-rsp.xml");
                bw = new BufferedWriter(fw);
                bw.write(rspNodes.item(i).getNodeValue());
                bw.close();
            }

    }

}
