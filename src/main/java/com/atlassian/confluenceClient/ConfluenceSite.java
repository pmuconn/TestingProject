package com.atlassian.confluenceClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.atlassian.confluence.rest.client.RestClientFactory;
import com.atlassian.confluence.rest.client.authentication.AuthenticatedWebResourceProvider;

public class ConfluenceSite {
	
    private static final Logger LOGGER = Logger.getLogger(ConfluenceSite.class.getName());

	
	/**
     * The base URL of the Confluence site
     */
    public final URL url;

    /**
     * The username to login as
     */
    public final String username;

    /**
     * The password for that user
     */
    public final String password;

    /**
     * Stapler constructor
     *
     * @param url
     * @param username
     * @param password
     */
    public ConfluenceSite(URL url, final String username, final String password) {
        LOGGER.log(Level.FINER, "ctor args: " + url + ", " + username + ", " + password);

        if (!url.toExternalForm().endsWith("/")) {
            try {
                url = new URL(url.toExternalForm() + "/");
            } catch (MalformedURLException e) {
                throw new AssertionError(e); // impossible
            }
        }

        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a remote access session to this Confluence site
     *
     * @return {@link ConfluenceSession}
     */
    public ConfluenceSession createSession() {
        final String restUrl = url.toExternalForm();
        LOGGER.log(Level.FINEST, "[confluence] Using Confluence base url: " + restUrl);

        AuthenticatedWebResourceProvider authenticatedWebResourceProvider = new AuthenticatedWebResourceProvider(
                RestClientFactory.newClient(),
                restUrl,
                "");
        if (username != null && password != null) {
            authenticatedWebResourceProvider.setAuthContext(username, password.toCharArray());
        }


        return new ConfluenceSession(authenticatedWebResourceProvider);
    }

    public String getName() {
        return this.url.getHost();
    }

    @Override
    public String toString() {
        return "Confluence{" + getName() + "}";
    }


}
