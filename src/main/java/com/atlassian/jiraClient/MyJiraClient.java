package com.atlassian.jiraClient;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.BasicProject;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Project;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInputBuilder;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;

import io.atlassian.util.concurrent.Promise;

public class MyJiraClient {

	private String username;
    private String password;
    private String jiraUrl;
    private JiraRestClient restClient;

    private MyJiraClient(String username, String password, String jiraUrl) {
        this.username = username;
        this.password = password;
        this.jiraUrl = jiraUrl;
        this.restClient = getJiraRestClient();
    }

    public static void main(String[] args) throws IOException {

    	String jiraId = "";
    	String jiraPw = "";
    	
    	if (StringUtils.isAnyBlank(jiraId,jiraPw)) {
    		System.out.println("You forgot to code your id and password.");
    		return;
    	}
    	
    	
        MyJiraClient myJiraClient = new MyJiraClient(jiraId, jiraPw, "https://jira.uconn.edu");

        
        /**
         * Sample of creating an issue.
         */
        
        //final String issueKey = myJiraClient.createIssue("ABCD", 1L, "Issue created from JRJC");
        //myJiraClient.updateIssueDescription(issueKey, "This is description from my Jira Client");
        
        /**
         * Lookup up a specific issue
         */
        
        String issueKey = "KPS-1571";
        Issue issue = myJiraClient.getIssue(issueKey);
        System.out.println(issue.getDescription());

        /**
         * Sample to add comment
         */
        
        //myJiraClient.addComment(issue, "This is comment from my Jira Client");


        /**
         * Get all comments.
         */
        
        List<Comment> comments = myJiraClient.getAllComments(issueKey);
        comments.forEach(c -> System.out.println(c.getBody()));

        /**
         * Get all projects
         */
        Iterable<BasicProject> projects = myJiraClient.restClient.getProjectClient().getAllProjects().claim();

        
        /**
         * Get specific project
         */
        Project kfsProject = myJiraClient.restClient.getProjectClient().getProject("KPS").claim();

        
        /**
         * Get all issues as part of a specific version.
         */
        
        Promise<SearchResult> searchJqlPromiseTest = myJiraClient.restClient.getSearchClient().searchJql("fixVersion=7.0.43"); //default max results = 50
        for (Issue issueResult : searchJqlPromiseTest.claim().getIssues()) {
            System.out.println(issueResult.getKey());
        }
        
        System.out.println("here");
        
        

        myJiraClient.restClient.close();
    }

    private String createIssue(String projectKey, Long issueType, String issueSummary) {

        IssueRestClient issueClient = restClient.getIssueClient();

        IssueInput newIssue = new IssueInputBuilder(projectKey, issueType, issueSummary).build();

        return issueClient.createIssue(newIssue).claim().getKey();
    }

    private Issue getIssue(String issueKey) {
        return restClient.getIssueClient().getIssue(issueKey).claim();
    }

    private void addComment(Issue issue, String commentBody) {
        restClient.getIssueClient().addComment(issue.getCommentsUri(), Comment.valueOf(commentBody));
    }

    private List<Comment> getAllComments(String issueKey) {
        return StreamSupport.stream(getIssue(issueKey).getComments().spliterator(), false)
          .collect(Collectors.toList());
    }

    private void updateIssueDescription(String issueKey, String newDescription) {
        IssueInput input = new IssueInputBuilder().setDescription(newDescription).build();
        restClient.getIssueClient().updateIssue(issueKey, input).claim();
    }

    private void deleteIssue(String issueKey, boolean deleteSubtasks) {
        restClient.getIssueClient().deleteIssue(issueKey, deleteSubtasks).claim();
    }

    private JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
          .createWithBasicHttpAuthentication(getJiraUri(), this.username, this.password);
    }

    private URI getJiraUri() {
        return URI.create(this.jiraUrl);
    }
	
}
