package com.boot.actuator.logview;

import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;

/**
 *
 * This is an example on how to create an additional endpoint using a different log location.
 * The logview endpoint is part of the spring boot actuator framework.
 * The endpoint here has an id of "logview" which would act as a new actuator endpoint.
 * Remember, any new actuator endpoints must be enabled for use in the application.properties file
 * 
 * management.endpoints.web.exposure.include=health,info,smplelogview
 * 
 * @see LogViewEndpointAutoconfig for how to create the Bean endpoint along with additional endpoints.
 * 
 */
@RestControllerEndpoint(id = "samplelogview")
public class SampleAdditionalLogViewEndpoint extends LogViewEndpoint {
	
    public SampleAdditionalLogViewEndpoint(String loggingPath) {
    	super (loggingPath);
    }

}
