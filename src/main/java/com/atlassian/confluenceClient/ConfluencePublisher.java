package com.atlassian.confluenceClient;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentBody;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.confluence.api.model.content.ContentStatus;
import com.atlassian.confluence.api.model.content.ContentType;
import com.atlassian.confluence.api.model.content.Space;
import com.atlassian.confluence.api.model.pagination.PageResponse;
import com.atlassian.confluence.api.service.exceptions.ServiceException;

/**
 * 
 * Demo code that can show what is possible...never tested.
 *
 */
public class ConfluencePublisher {
	
	private static final Logger LOGGER = Logger.getLogger(ConfluencePublisher.class.getName());
	
	private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private  final String spaceName;
    private  final String pageName;
    private boolean replaceAttachments;
    private long parentId;
    private ConfluenceSite confluenceSite;

    public ConfluencePublisher( ConfluenceSite confluenceSite, final  String spaceName, final  String pageName, final long parentId, boolean replacementAttachments) {
    	this.confluenceSite = confluenceSite;
        this.spaceName = spaceName;
        this.pageName = pageName;
        this.replaceAttachments = replacementAttachments;
        setParentId(parentId);
    }
    
    public void performPublish() throws InterruptedException, IOException {
        boolean result = true;
        ConfluenceSite site = this.getConfluenceSite();

        if (site == null) {
            log("Not publishing because no Confluence Site could be found. " +
                    "Check your Confluence configuration in system settings.");
            return;
        }

        ConfluenceSession confluence = site.createSession();

        String spaceName = this.spaceName;
        String pageName = this.pageName;
        long parentId = this.parentId;

        log("ParentId: " + parentId);

        Content pageContent;
        
        try {
            String spaceAndPageNames = String.format("%s/%s", spaceName, pageName);
            pageContent = confluence.getContent(spaceName, pageName, true).orElseThrow(() -> new ServiceException(String.format("Page at \"%s\" not found!", spaceAndPageNames)));
        } catch (ServiceException e) {
            // Still shouldn't fail the job, so just dump this to the console and keep going (true).
            log( e.getMessage());
            log("Unable to locate page: " + spaceName + "/" + pageName + ".  Attempting to create the page now...");

            try {
                // if we haven't specified a parent, assign the Space home page as the parent
                if (parentId == 0L) {
                    Space space = confluence.getSpace(spaceName).getOrNull();
                    if (space != null) {
                        parentId = space.getId();
                    }
                }

                pageContent = this.createPage(confluence, spaceName, pageName, parentId);

            } catch (ServiceException exc) {
                log("Page could not be created!  Aborting edits...");
                exc.printStackTrace();
                return;
            }
        }

        // Perform attachment uploads
        List<Content> remoteAttachments = null;
        try {
            remoteAttachments = this.performAttachments(confluence, pageContent);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Perform page replacements
        result &= this.performPageReplacements(confluence,
                pageContent, remoteAttachments);

        //At end, add comment and finish.
        if (result) {
            try {
                result &= performEditComment(confluence, pageContent);
            } catch (ServiceException se) {
                log(se.getMessage());
            }
        }

    }
    
    protected List<Content> performAttachments(ConfluenceSession confluence,
                                               final Content pageContent) throws IOException, InterruptedException {

        final long pageId = pageContent.getId().asLong();
        final List<Content> remoteAttachments = new ArrayList<>();

        log("Uploading attachments to Confluence page: " + pageContent.getTitle());

        
        /**
         * Its possible to get a listing of files based on a file path.
         */
        //final List<File> files = findArtifacts();
        final List<File> files = new ArrayList<File>();

        String attachmentComment = "Publishing test.";

        log("Uploading " + files.size() + " file(s) to Confluence...");

        boolean shouldRemoveExistingAttachments = false;
        List<Content> existingAttachments = new ArrayList<>();
        if (isReplaceAttachments()) {
            List<Content> attachments = confluence.getAttachments(pageId);
            if (attachments != null && attachments.size() > 0) {
                existingAttachments.addAll(attachments);
                shouldRemoveExistingAttachments = true;
            }
        }

        for (File file : files) {
            final String fileName = file.getName();

            if (shouldRemoveExistingAttachments) {
                for (Content remoteAttachment : existingAttachments) {
                    if (remoteAttachment.getTitle().equals(fileName)) {
                        try {
                            confluence.removeAttachment(remoteAttachment);
                            existingAttachments.remove(remoteAttachment);
                            log( "Deleted existing " + remoteAttachment.getTitle() + " from Confluence before upload new...");
                            break;
                        } catch (ServiceException e) {
                            log( "Deleting error: " + e.toString());
                            throw e;
                        }
                    }
                }
            }


            String contentType = URLConnection.guessContentTypeFromName(fileName);

            if (StringUtils.isEmpty(contentType)) {
                // Confluence does not allow an empty content type
                contentType = DEFAULT_CONTENT_TYPE;
            }

            log(" - Uploading file: " + fileName + " (" + contentType + ")");

            try {
                final PageResponse<Content> result = confluence.addAttachment(pageId, file,
                        contentType, attachmentComment);
                remoteAttachments.addAll(result.getResults());
                log("   done: " + result.getResults().stream()
                        .map(Content::getTitle).collect(Collectors.joining(", ")));
            } catch (ServiceException se) {
                log("Unable to upload file...");
                se.printStackTrace();
            }
        }
        log( "Done");

        return remoteAttachments;
    }

    /**
     * Creates a new Page in Confluence.
     *
     * @param confluence
     * @param spaceName
     * @param pageName
     * @return The resulting Page
     * @throws RemoteException
     */
    private Content createPage(ConfluenceSession confluence, String spaceName, String pageName, long parentId)
            throws ServiceException {
        Content parentContent = confluence.getContent(String.valueOf(parentId))
                .orElseThrow(() -> new ServiceException("Can't find parent content with Id:" + parentId));
        Content.ContentBuilder newPage = Content.builder()
                .title(pageName)
                .type(ContentType.PAGE)
                .space(spaceName)
                .body(ContentBody.contentBodyBuilder().build())
                .parent(parentContent);
        return confluence.createContent(newPage.build());
    }


    private boolean performPageReplacements(ConfluenceSession confluence,
                                            Content pageContent, List<Content> remoteAttachments) {

        boolean isUpdated = false;
        //Ugly Hack, though required here. DO NOT REMOVE, otherwise  Content.ContentBuilder.build() will fail.
        Consumer<Map<ContentType, PageResponse<Content>>> SANITIZE_NESTED_CONTENT_MAP = (m) ->
                m.entrySet().stream().filter(e -> e.getValue() == null).map(Map.Entry::getKey)
                        .collect(Collectors.toList()).forEach(m::remove);

        SANITIZE_NESTED_CONTENT_MAP.accept(pageContent.getChildren());
        SANITIZE_NESTED_CONTENT_MAP.accept(pageContent.getDescendants());

        // Get current content and edit.
        String originContent = pageContent.getBody().get(ContentRepresentation.STORAGE).getValue();

        
        /**
         * Can add logic to edit the STORAGE (page text) if we want.
         */
        String contentEdited = originContent;
        //String contentEdited = performEdits(originContent, remoteAttachments);
        
        
        //XHTML -> HTML self closing tag adjustment
        contentEdited = contentEdited.replaceAll(" /", "/");

        // Now set the replacement contentBody
        ContentBody contentBody = ContentBody.contentBodyBuilder()
                .representation(ContentRepresentation.STORAGE)
                .value(contentEdited)
                .build();
        List<Content> ancestors = pageContent.getAncestors();
        Content updatedContent = Content.builder(pageContent)
                .version(pageContent.getVersion().nextBuilder().build())
                .body(contentBody)
                .parent(ancestors.get(ancestors.size() - 1))
                .build();

        //post updated content.
        Content results = confluence.updateContent(updatedContent);

        //Check if remote content is updated.
        Optional<Content> remoteResults =
                confluence.getContent(pageContent.getSpace().getKey(), pageContent.getTitle(), true);
        if (remoteResults.isPresent()) {
            isUpdated = remoteResults.get().getVersion().getNumber() == results.getVersion().getNumber();
        }

        return isUpdated;
    }

    private boolean performEditComment(ConfluenceSession confluence, Content pageContent)
            throws IOException, InterruptedException, ServiceException {
        boolean isUpdated = false;
        final String editComment = "Edit Comment.";

        Optional<Content> previousComment = Optional.empty();
        List<Content> cl = new ArrayList<>();

        Optional.ofNullable(pageContent.getChildren())
                .flatMap(cn -> Optional.ofNullable(cn.get(ContentType.COMMENT)).flatMap(cm -> Optional.ofNullable(cm.getResults())))
                .ifPresent(cl::addAll);

        if (!cl.isEmpty()) {
            previousComment = cl.stream()
                    .filter(c -> c.getBody().get(ContentRepresentation.STORAGE).getValue().contains(editComment.split(":")[0]))
                    .min(Comparator.comparing(c -> c.getVersion().getNumber()));
        }

        if (previousComment.isPresent()) {
            //Edit comment Content
            Content comment = Content.builder()
                    .type(ContentType.COMMENT)
                    .version(previousComment.get().getVersion().nextBuilder().build())
                    .id(previousComment.get().getId())
                    .container(pageContent)
                    .title("Re: " + pageContent.getTitle())
                    .extension("location", "footer")
                    .status(ContentStatus.CURRENT)
                    .body(ContentBody.contentBodyBuilder()
                            .representation(ContentRepresentation.STORAGE)
                            .value(editComment)
                            .build())
                    .build();
            confluence.updateContent(comment);
        } else {
            //Post new comment.
            createComment(confluence, pageContent, editComment);

        }
        //Check if remote content is updated.
        Optional<Content> remoteResults =
                confluence.getContent(pageContent.getSpace().getKey(), pageContent.getTitle(), true);
        if (remoteResults.isPresent()) {
            isUpdated = remoteResults.get().getChildren().get(ContentType.COMMENT)
                    .getResults().stream().map(r -> r.getBody().get(ContentRepresentation.STORAGE).getValue())
                    .collect(Collectors.toList())
                    .contains(editComment);
        }
        return isUpdated;
    }

    /**
     * Creates a new Comment to Confluence page.
     *
     * @param confluence
     * @param parentContent
     * @param commentText
     * @return The resulting comment Content
     * @throws RemoteException
     */
    private Content createComment(ConfluenceSession confluence, Content parentContent, String commentText)
            throws ServiceException {
        Content.ContentBuilder newComment = Content.builder()
                .title("Re: " + parentContent.getTitle())
                .body(ContentBody.contentBodyBuilder()
                        .representation(ContentRepresentation.STORAGE)
                        .value(commentText)
                        .build())
                .container(parentContent)
                .type(ContentType.COMMENT);
        return confluence.createContent(newComment.build());
    }

    /**
     * Recursively scan a directory, returning all files encountered
     *
     * @param artifactsDir
     * @return
     */
    private List<File> findArtifacts(File artifactsDir) {
        ArrayList<File> files = new ArrayList<>();

        if (artifactsDir != null && artifactsDir.isDirectory()) {
            File[] listed = artifactsDir.listFiles();

            if (listed != null) {
                for (File f : listed) {
                    if (f == null) {
                        continue;
                    }

                    if (f.isDirectory()) {
                        files.addAll(findArtifacts(f));
                    } else if (f.isFile()) {
                        files.add(f);
                    }
                }
            }
        }

        return files;
    }

    /**
     * Log helper
     *
     * @param listener
     * @param message
     */
    protected void log(String message) {
    	LOGGER.info("[confluence] " + message);
    }
    
    /**
     * @return the replaceAttachments
     */
    public boolean isReplaceAttachments() {
        return replaceAttachments;
    }


	public ConfluenceSite getConfluenceSite() {
		return confluenceSite;
	}


	public void setConfluenceSite(ConfluenceSite confluenceSite) {
		this.confluenceSite = confluenceSite;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getSpaceName() {
		return spaceName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setReplaceAttachments(boolean replaceAttachments) {
		this.replaceAttachments = replaceAttachments;
	}

 
}
