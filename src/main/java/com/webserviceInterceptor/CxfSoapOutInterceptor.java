/*
 * The Kuali Financial System, a comprehensive financial management system for higher education.
 *
 * Copyright 2005-2014 The Kuali Foundation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.webserviceInterceptor;

import java.io.OutputStream;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

public class CxfSoapOutInterceptor extends LoggingOutInterceptor {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CxfSoapOutInterceptor.class);


    public CxfSoapOutInterceptor() {
        super(Phase.PRE_STREAM);
    }

//    @Override
//    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
//        String soapXmlPayload = loggingMessage.getPayload() != null ? loggingMessage.getPayload().toString() : null;
//        LOGGER.info(soapXmlPayload);
//        return super.formatLoggingMessage(loggingMessage);
//    }

    @Override
    public void handleMessage(Message message) throws Fault {
        OutputStream out = message.getContent(OutputStream.class);
        final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(out);
        message.setContent(OutputStream.class, newOut);
        newOut.registerCallback(new LoggingCallback());
    }

	public class LoggingCallback implements CachedOutputStreamCallback {
		public void onFlush(CachedOutputStream cos) {
		}

		public void onClose(CachedOutputStream cos) {
			try {
				StringBuilder builder = new StringBuilder();
				cos.writeCacheTo(builder);
				// here comes my xml:
				String soapXml = builder.toString();
				LOGGER.info(soapXml);
			}
			catch (Exception e) {
			}

			// try {
			// if (cos != null) {
			// System.out.println("Response XML in out Interceptor : " + IOUtils.toString(cos.getInputStream()));
			// }
			//
			// }
			// catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
	}

}