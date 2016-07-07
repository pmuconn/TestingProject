package com.teaas.utils;

import org.jdom2.JDOMFactory;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.SAXHandler;
import org.jdom2.input.sax.SAXHandlerFactory;

public class JdomHelper {

	private static final SAXHandlerFactory FACTORY = new SAXHandlerFactory() {
        public SAXHandler createSAXHandler(JDOMFactory factory) {
            return new NoNamespaceSAXHandler() ;
        }
    };
        
    /** Get a {@code SAXBuilder} that ignores namespaces.
     * Any namespaces present in the xml input to this builder will be omitted from the resulting {@code Document}. */
    public static SAXBuilder getNoNamespaceSAXBuilder() {
        // Note: SAXBuilder is NOT thread-safe, so we instantiate a new one for every call.
        SAXBuilder saxBuilder = new SAXBuilder();
        saxBuilder.setSAXHandlerFactory(FACTORY);
        return saxBuilder;
    }

    public static SAXBuilder getSAXBuilder() {
        // Note: SAXBuilder is NOT thread-safe, so we instantiate a new one for every call.
        SAXBuilder saxBuilder = new SAXBuilder();
        return saxBuilder;
    }

}
