package com.atlassian.confluenceClient;

import java.net.URL;
import java.util.Optional;

import com.atlassian.confluence.api.model.content.Content;

public class ConfluenceTest {

	public static void main(String[] args) {

		try {
			URL confluenceUrl = new URL("https://confluence.uconn.edu");
			
			ConfluenceSite confluenceSite = new ConfluenceSite(confluenceUrl, "someusername", "somepassword");
			
			ConfluenceSession confluenceSession = confluenceSite.createSession();
			Optional<Content> content = confluenceSession.getContent("EFS", "Kuali Security Audit", true);
			
			if (content.isPresent()) {
				Content pageContent = content.get();
				
				System.out.println(pageContent.getId());
			}

			//The security audit page had no actual children BUT the EFS/2021 page has the Security Audit page as a parent.
			
			content = confluenceSession.getContent("EFS", "2021", true);
			
			if (content.isPresent()) {
				Content pageContent = content.get();

				//could loop over ancestors to see if the Kuali Security Audit is its parent.
				pageContent.getAncestors();
				
				
				System.out.println(pageContent.getId());
			}
			
			confluenceSession.endSession();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("done.");

	}

}
