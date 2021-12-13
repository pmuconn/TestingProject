package com.boot.actuator.logview;

import java.nio.file.Path;

import org.ocpsoft.prettytime.PrettyTime;

public abstract class AbstractFileProvider implements FileProvider {
    protected static final PrettyTime prettyTime = new PrettyTime();

    protected boolean isArchive(Path path) {
        return isZip(path) || isTarGz(path);
    }

    protected boolean isTarGz(Path path) {
        return !path.toFile().isDirectory() && path.getFileName().toString().endsWith(".tar.gz");
    }

    protected boolean isZip(Path path) {
        return !path.toFile().isDirectory() && path.getFileName().toString().endsWith(".zip");
    }
}
