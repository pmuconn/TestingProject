package org.kuali.test;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.kuali.kfs.core.v5_0.AccountingLineType;
import org.kuali.kfs.core.v5_0.ObjectFactory;
import org.kuali.kfs.core.v5_0.RequisitionItemType;
import org.kuali.kfs.core.v5_0.UnifierDelivery;
import org.kuali.kfs.core.v5_0.UnifierNote;
import org.kuali.kfs.core.v5_0.UnifierReceiving;
import org.kuali.kfs.core.v5_0.UnifierRequisitionRequest;
import org.kuali.kfs.core.v5_0.UnifierRequisitionResponse;
import org.kuali.kfs.core.v5_0.UnifierVendor;
import org.kuali.kfs.kfs.v5_0.Exception;
import org.kuali.kfs.kfs.v5_0.FileToByteString;
import org.kuali.kfs.kfs.v5_0.PurapRequisitionWebService;
import org.kuali.kfs.kfs.v5_0.RequisitionWebServiceInterface;


public class TestClient {

	public static void main(String[] args) {
//		String urlstring = "http://localhost:8080/kfs-dev/remoting/purapRequisitionWebService?wsdl";
		String urlstring = "https://kualinp.uconn.edu/kfs-dev/remoting/purapRequisitionWebService?wsdl";
		
		try {
			URL url = new URL(urlstring);
			//test and accept all certs
			isUrlLive(urlstring);
			
			UnifierRequisitionRequest req = buildRequest();		
			PurapRequisitionWebService service = new PurapRequisitionWebService(url);
			RequisitionWebServiceInterface server = service.getRequisitionWebServiceInterfacePort();
			
			System.out.println("Started executing web service...");
			UnifierRequisitionResponse res = server.createRequisition(req);
			System.out.println("doc id: "+ res.getUconnDocumentID());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Finished executing web service...");
		}
	}
	
	private static UnifierRequisitionRequest buildRequest() {
		System.out.println("Started create elements for web service...");
		ObjectFactory ojb = new ObjectFactory();
		
		UnifierRequisitionRequest req = ojb.createUnifierRequisitionRequest();
		req.setUconnNetID("pam16105");
		req.setDocumentDescription("Test KPS-370 void cxml po & double void.");
		req.setExplanation("No need to explain");
		req.setOrganizationDocumentNumber("1234567890");
		req.setChartCode("UC");
		req.setOrganizationCode("1549");
		req.setReceivingRequiredInd("Y");
		req.setPreqPositiveApprovalInd("Y");
		req.setContactName("Rich Contact");
		req.setContactPhoneNumber("888-123-4567");
		req.setContactEmailAddress("contact@ritchie.com");
		
		UnifierDelivery ud = ojb.createUnifierDelivery();
		ud.setCampusCode("01");
		ud.setUconnNetID("btp98001");
		ud.setBuildingCode("0239");
		ud.setRoom("105AA");
		
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date = null;
		try {
			date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch (DatatypeConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ud.setRequiredDate(date);
		
		ud.setRequiredDateReasonCode("BUY");
		ud.setInstructions("Do not break anything");
		req.setDelivery(ud);
		
		UnifierReceiving ur =ojb.createUnifierReceiving();
		ur.setUseReceivingAddressInd("Y");
		req.setReceiving(ur);
		
		UnifierVendor uv = ojb.createUnifierVendor();
		uv.setVendorID("12351-0");
		uv.setNotesToVendor("Please Note");
		uv.setContractName("");
		req.setVendor(uv);
		
		RequisitionItemType item = ojb.createRequisitionItemType();
		item.setLineNumber(1);
		item.setType("ITEM");
		item.setQuantity("1");
		item.setUnitOfMeasure("EA");
		item.setCatalogNumber("A8365053");
		item.setCommodityCode("70");
		item.setDescription("Targus Meridian II Backpack - Fits Laptops of Screen Size Up to 15.6-inch");
		item.setUnitPrice("56.7500");
		item.setRestrictedInd("N");
		
		//ojb.createRequisitionItem(item);
		AccountingLineType alt = ojb.createAccountingLineType();
		alt.setChartCode("UC");
		alt.setAccountNumber(2925000);
		alt.setSubAccount("");
		alt.setObjectCode(6775);
		alt.setSubObject("");
		alt.setProject("");
		alt.setOriginalReferenceID("");
		alt.setPercent(new BigDecimal(100.00));

		item.getAccountingLines().add(alt);
		req.getItems().add(item);
		
		UnifierNote n = ojb.createUnifierNote();
		n.setText("Text Note");
		n.setAttachmentName("test.txt");
		n.setAttachment(FileToByteString.getEncodedByte("C:\\paul\\test.txt"));
		req.getNotes().add(n);
//
//		UnifierNote n2 = ojb.createUnifierNote();
//		n2.setText("Zip Note");
//		n2.setAttachmentName("test.zip");
//		n2.setAttachment(FileToByteString.getEncodedByte("C:\\paul\\test1meg.zip"));
//		req.getNotes().add(n2);
		
//		UnifierNote n3 = ojb.createUnifierNote();
//		n3.setText("Word Note");
//		n3.setAttachmentName("testdoc.docx");
//		n3.setAttachment(FileToByteString.getEncodedByte("C:\\paul\\testdoc.docx"));
//		req.getNotes().add(n3);
		
		req.setUnifierUID("1234567890|123|1234567");
		req.setReference1("ref1");
		req.setReference2("ref2");
		req.setReference3("ref3");

		return req;
	}
	
	/**
	 * @param urlToCheck String represention of a url
	 * @return true if the url returned a good connection (active and live), false otherwise.
	 */
	private static boolean isUrlLive(String urlToCheck) {
		boolean returnValue = false;
		try { 
			URL url = new URL(urlToCheck);
			int code = 0;
			if (url.getProtocol().equalsIgnoreCase("https")) {
				TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
					public java.security.cert.X509Certificate[] getAcceptedIssuers() {
						return null;
					}

					public void checkClientTrusted(X509Certificate[] certs, String authType) {
					}

					public void checkServerTrusted(X509Certificate[] certs, String authType) {
					}

				} };
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				};
				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

				HttpsURLConnection huc = (HttpsURLConnection) url.openConnection();
				code = huc.getResponseCode();
//				print_https_cert(huc);
//				print_content(huc);
			} else {
				HttpURLConnection huc =  ( HttpURLConnection )  url.openConnection (); 
				huc.setRequestMethod ("GET");
				huc.connect () ; 
				code = huc.getResponseCode() ;
			}
			System.out.println(code);		
			if (200 == code) {
				returnValue = true;
			} else {
				returnValue = false;
			}
		} catch (Throwable e) {
			returnValue = false;
		}
		return returnValue;
	}
	
}
