<html>
    <head>
        <title>Logfiles</title>
        <#list stylesheets as stylesheet>
        <link rel="stylesheet" href="${stylesheet}">
        </#list>
        <style>
            .form-group {
                margin-right: 10px;
            }
        </style>
    </head>
    <body>
            <div class="container">
                <div class="page-header">
                    <h1>Log file viewer</h1>
                </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="form-inline">
                        <form action="search" method="get" target="_blank">
                            <div class="form-group">
                                <label>Current location</label>
                                <p class="form-control-static">${currentFolder}</p>
                            </div>

                                <div class="form-group">
                                    <label for="term">Search</label>
                                    <input class="form-control" id="term" name="term" type="text"/>
                                </div>
                                <button class="btn btn-default">Search Files</button>
                            
                            <!--
                            <#if base == "">
                                <div class="form-group">
                                    <label for="term">Search</label>
                                    <input class="form-control" id="term" name="term" type="text"/>
                                </div>
                                <button class="btn btn-default">Search</button>
                            </#if>
                            -->
                        </form>
                    </div>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th><a href="?sortBy=FILENAME<#if sortBy == 'FILENAME' && desc == false>&desc=true</#if>&base=${base}">Name</a></th>
                            <th><a href="?sortBy=SIZE<#if sortBy == 'SIZE' && desc == false>&desc=true</#if>&base=${base}">Size</a></th>
                            <th><a href="?sortBy=MODIFIED<#if sortBy == 'MODIFIED' && desc == false>&desc=true</#if>&base=${base}">Modified</a></th>
                        </tr>
                    </thead>
                    <#if base != "">
                    <tr>
                        <td><i class="fa fa-folder-o"></i>&nbsp;<a href="?base=${parent}">..</a></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </#if>

                    <#list files as file>
                        <tr>
                            <td>
                                <#if file.fileType == 'FILE'>
                                    <i class="fa fa-file-o"></i>
                                </#if>
                                <#if file.fileType == 'DIRECTORY'>
                                    <i class="fa fa-folder-o"></i>
                                </#if>
                                <#if file.fileType == 'ARCHIVE'>
                                    <i class="fa fa-file-archive-o"></i>
                                </#if>
                                
                                &nbsp;
                                
                                <#if file.fileType == 'FILE'>
                                    
                                    <a href="view?filename=${file.filename}&base=${base}"  target="_blank" rel="noopener noreferrer" >${file.displayFilename}</a> &nbsp;

                                    <a href="download?filename=${file.filename}&base=${base}"  target="_blank" rel="noopener noreferrer" title="Download" ><i class="fa fa-angle-double-down"></i></a> 

									<!--
                                    <#if !base?ends_with(".tar.gz") && !base?ends_with(".zip")>
	                                <a href="view?filename=${file.filename}&base=${base}&tailLines=50" title="Download last 50 lines"><i class="fa fa-angle-double-down"></i></a>
                                    </#if>
									-->
                                    
                                </#if>
                                <#if file.fileType == 'ARCHIVE'>
	                                ${file.displayFilename}
                                	<!--
                                    <a href="?base=${base}/${file.filename}">${file.displayFilename}</a>
                                	-->
                                    <a href="download?filename=${file.filename}&base=${base}"  target="_blank" rel="noopener noreferrer" title="Download" ><i class="fa fa-angle-double-down"></i></a> 

                                </#if>
                                <#if file.fileType == 'DIRECTORY'>
                                    <a href="?base=${base}/${file.filename}">${file.displayFilename}</a>
                                </#if>
                            </td>
                            <td>
                                <#if file.fileType != 'DIRECTORY'>
                                    ${file.sizePretty}
                                </#if>
                            </td>
                            <td title="${file.modified}">${file.modifiedPretty}</td>
                        </tr>
                    </#list>
                </table>
            </div>
        </div>
    </body>
</html>