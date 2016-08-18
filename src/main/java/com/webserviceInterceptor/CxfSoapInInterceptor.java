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

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public class CxfSoapInInterceptor extends LoggingInInterceptor {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CxfSoapInInterceptor.class);


    public CxfSoapInInterceptor() {
        super(Phase.RECEIVE);
    }

//    @Override
//    protected String formatLoggingMessage(LoggingMessage loggingMessage) {
//        String soapXmlPayload = loggingMessage.getPayload() != null ? loggingMessage.getPayload().toString() : null;
//        LOGGER.info(soapXmlPayload);
//        return super.formatLoggingMessage(loggingMessage);
//    }

    @Override
    public void handleMessage ( Message message ) throws Fault
    {
        //get the remote address
        HttpServletRequest httpRequest = (HttpServletRequest) message.get ( AbstractHTTPDestination.HTTP_REQUEST );
        System.out.println ("Request From the address : " + httpRequest.getRemoteAddr ( ) );

        try
        {
            // now get the request xml
            InputStream is = message.getContent ( InputStream.class );
            CachedOutputStream os = new CachedOutputStream ( );
            IOUtils.copy ( is, os );
            os.flush ( );
            message.setContent (  InputStream.class, os.getInputStream ( ) );
            is.close ( );

            LOGGER.info ("The request is: " + IOUtils.toString ( os.getInputStream ( ) ));
            os.close ( );
        }

        catch ( Exception ex )
        {
            ex.printStackTrace ( );
        }

    }

}
