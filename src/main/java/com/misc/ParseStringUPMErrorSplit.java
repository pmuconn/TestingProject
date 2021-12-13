package com.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * <pre>
 * ==============================================================
 * Copyright UNITEDHEALTH GROUP CORPORATION 2010.
 * This software and documentation contain confidential and
 * proprietary information owned by United HealthCare Corporation.
 * Unauthorized use and distribution are prohibited.
 * ===============================================================
 * 
 * MODIFICATION HISTORY
 * Jan 7, 2010	pmorano	Initial version
 * 
 * ===========================================================
 *
 * Creation Date : Jan 7, 2010 9:22:00 AM
 * File : ParseString.java
 * ============================================================
 * 
 * </pre>
 * @author pmorano
 */
/**
 * @author pmorano
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ParseStringUPMErrorSplit{

	public static void main(String[] args) {
		ParseStringUPMErrorSplit parser = new ParseStringUPMErrorSplit();
		parser.parseMessage();
	}
	
	public void parseMessage() {
		String msg = "STATUS-SYSTEM = CICSSAA1, STATUS-GENERATED-BY = CDB, STATUS-OWNED-BY = CDB, STATUS-SERVICE-NAME = V6435CDI, STATUS-SERVICE-VERSION = 000001 {VR1: CODE-TYPE-DESC = RESPONSE1, CODE = 0099, CODE-DESC = CDB, ADDITIONAL-INFO = INVALID REQUEST - INVALID COVERAGE TYPE}  {VR1: CODE-TYPE-DESC = RESPONSE2, CODE = 003, CODE-DESC = PROGRAM V6435CDI, ADDITIONAL-INFO = LOCATION - VCDI200016 APPLICATION ERROR CDB-CDI-INQ-REQ}";

		List<CdbStatusMessageBean> cdbMessages = new ArrayList<CdbStatusMessageBean>();
		
		String[] items = msg.split("\\{");
		for (String item : items) {
			System.out.println(item);
			
			//for each of these vr's we make a new bean.
			if (item.startsWith("VR")) {
				CdbStatusMessageBean msgBean = this.new CdbStatusMessageBean();
				String[] codeValueLines = item.split(",");
				for (String cvline : codeValueLines) {
					cvline.trim();
					System.out.println("code value line: " +cvline);
					
					String[] codeValues = cvline.split("=");
					if (codeValues.length == 2) {
						if ("CODE".equals(codeValues[0].trim())) {
							msgBean.setCode(codeValues[1].trim());
						}
						if ("CODE-DESC".equals(codeValues[0].trim())) {
							msgBean.setSource(codeValues[1].trim());
						}
						if ("ADDITIONAL-INFO".equals(codeValues[0].trim())) {
							msgBean.setDescription(codeValues[1].trim().replace("}", ""));
						}
					}
				}
				cdbMessages.add(msgBean);
			}
		}
		
		System.out.println("done");
	}
	
	
	private class CdbStatusMessageBean {
		public CdbStatusMessageBean() {
			super();
		}
		private String code = "";
		private String source = "";
		private String description = "";
		/**
		 * @return the code
		 */
		public String getCode() {
			return code;
		}
		/**
		 * @param code the code to set
		 */
		public void setCode(String code) {
			this.code = code;
		}
		/**
		 * @return the source
		 */
		public String getSource() {
			return source;
		}
		/**
		 * @param source the source to set
		 */
		public void setSource(String source) {
			this.source = source;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		
	}

}
