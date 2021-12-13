package com.boot.actuator.logview;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * The logview endpoint is part of the spring boot actuator framework.
 * The endpoint here has an id of "logview" which would act as a new actuator endpoint.
 * Actuator endpoints must be enabled for use in the application.properties file
 * 
 * management.endpoints.web.exposure.include=health,info,logview
 * 
 * Since we are adding a new custom endpoing, all endpoints you want exposed need to be included like above.
 *
 * @see LogViewEndpointAutoconfig for how to create the Bean endpoint along with additional endpoints.
 */
@RestControllerEndpoint(id = "logview")
public class LogViewEndpoint {
	
    private final List<FileProvider> fileProviders;
    private final Configuration freemarkerConfig;
    private final String loggingPath;
    
    private final List<String> stylesheets = asList("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css",
            "https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css");
    

    public LogViewEndpoint(String loggingPath) {
        this.loggingPath = loggingPath;
        
        fileProviders = asList(new FileSystemFileProvider(),
                new ZipArchiveFileProvider(),
                new TarGzArchiveFileProvider());

        freemarkerConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    @RequestMapping
    public void redirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("logview/");
    }

    
    @GetMapping("/list")
    @ResponseBody
    public String list(
                       @RequestParam(required = false, defaultValue = "FILENAME") SortBy sortBy,
                       @RequestParam(required = false, defaultValue = "false") boolean desc,
                       @RequestParam(required = false) String base) throws IOException, TemplateException {
        Path currentFolder = loggingPath(base);
        securityCheck(currentFolder, null);

        ModelMap model = new ModelMap();

        
        List<FileEntry> files = getFileProvider(currentFolder).getFileEntries(currentFolder);
        List<FileEntry> sortedFiles = sortFiles(files, sortBy, desc);

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("desc", desc);
        model.addAttribute("currentFolder", replaceSlashes(currentFolder.toAbsolutePath().toString()));
        
        model.addAttribute("base", base != null ? URLEncoder.encode(base, "UTF-8") : "");
        model.addAttribute("parent", replaceSlashes(getParent(currentFolder)));
        model.addAttribute("stylesheets", stylesheets);

        System.out.println(model);
        
        model.addAttribute("files", sortedFiles);

        return FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("logview.ftl"), model);
    }    
    
    @GetMapping("/view")
    public void view(@RequestParam String filename,
                     @RequestParam(required = false) String base,
                     @RequestParam(required = false) Integer tailLines,
                     HttpServletResponse response) throws IOException {

        Path path = loggingPath(base);
        securityCheck(path, filename);
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        FileProvider fileProvider = getFileProvider(path);
        if (tailLines != null) {
            fileProvider.tailContent(path, filename, response.getOutputStream(), tailLines);
        }
        else {
            fileProvider.streamContent(path, filename, response.getOutputStream());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String filename,
                     @RequestParam(required = false) String base,
                     @RequestParam(required = false) Integer tailLines,
                     HttpServletResponse response) throws IOException {

        Path path = loggingPath(base);
    	File downloadFile = Paths.get(path.toString(), filename).toFile();
    	
    	InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadFile));

    	HttpHeaders header = new HttpHeaders();
    	header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + downloadFile.getName() + "\"");
    	
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(downloadFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    	
    }
    
    @GetMapping("/search")
    public void search(@RequestParam String term, HttpServletResponse response) throws IOException {
        Path folder = loggingPath(null);
        List<FileEntry> files = getFileProvider(folder).getFileEntries(folder);
        List<FileEntry> sortedFiles = sortFiles(files, SortBy.MODIFIED, false);

        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        ServletOutputStream outputStream = response.getOutputStream();

    	outputStream.write("Beginning Search Results ---".getBytes());
    	outputStream.write(System.lineSeparator().getBytes());        
        
        sortedFiles.stream()
                .filter(file -> file.getFileType().equals(FileType.FILE))
                .forEach(file -> searchAndStreamFile(file, term, outputStream));
        
    	outputStream.write("Ending Search Results ---".getBytes());
    	outputStream.write(System.lineSeparator().getBytes());        
    }

    protected FileProvider getFileProvider(Path folder) {
        return fileProviders.stream()
                .filter(provider -> provider.canHandle(folder))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("no file provider found for " + folder.toString()));
    }

    protected String getParent(Path loggingPath) {
        Path basePath = loggingPath(null);
        String parent = "";
        if (!basePath.toString().equals(loggingPath.toString())) {
            parent = loggingPath.getParent().toString();
            if (parent.startsWith(basePath.toString())) {
                parent = parent.substring(basePath.toString().length());
            }
        }
        return parent;
    }

    protected Path loggingPath(String base) {
        return base != null ? Paths.get(loggingPath, base) : Paths.get(loggingPath);
    }

    protected List<FileEntry> sortFiles(List<FileEntry> files, SortBy sortBy, boolean desc) {
        Comparator<FileEntry> comparator = null;
        switch (sortBy) {
            case FILENAME:
                comparator = (a, b) -> a.getFilename().compareTo(b.getFilename());
                break;
            case SIZE:
                comparator = (a, b) -> Long.compare(a.getSize(), b.getSize());
                break;
            case MODIFIED:
                comparator = (a, b) -> Long.compare(a.getModified().toMillis(), b.getModified().toMillis());
                break;
        }
        List<FileEntry> sortedFiles = files.stream().sorted(comparator).collect(toList());

        if (desc) {
            Collections.reverse(sortedFiles);
        }
        return sortedFiles;
    }

    protected void searchAndStreamFile(FileEntry fileEntry, String term, OutputStream outputStream) {
        Path folder = loggingPath(null);
        try {
            List<String> lines = IOUtils.readLines(new FileInputStream(new File(folder.toFile().toString(), fileEntry.getFilename())))
                    .stream()
                    .filter(line -> line.contains(term))
                    .map(line -> "[" + fileEntry.getFilename() + "] " + line)
                    .collect(toList());
            for (String line : lines) {
                outputStream.write(line.getBytes());
                outputStream.write(System.lineSeparator().getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("error reading file", e);
        }
    }

    protected void securityCheck(Path base, String filename) {
        try {
            String canonicalLoggingPath = (filename != null ? new File(base.toFile().toString(), filename) : new File(base.toFile().toString())).getCanonicalPath();
            String baseCanonicalPath = new File(loggingPath).getCanonicalPath();
            String errorMessage = "File " + base.toString() + "/" + filename + " may not be located outside base path " + loggingPath;
            Assert.isTrue(canonicalLoggingPath.startsWith(baseCanonicalPath), errorMessage);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    
    protected String replaceSlashes (String str) {
    	return str.replace("\\", "/");
    }

}
