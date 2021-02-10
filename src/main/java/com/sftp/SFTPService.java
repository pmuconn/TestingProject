/*
 * Copyright 2011 The Kuali Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sftp;

/**
 * This class is a service that will assist in the File Transfer Protocol
 */
public interface SFTPService {

    /**
     *
     * Sends a file to the sftp server
     *
     * @param sftpUrl
     * @param username sftp username to sign into the ftp server as
     * @param password sftp password for the user signing into the sftp server
     * @param fileToSend full path to the file that will be sent to the ftp
     * server
     * @param destPath path to upload to on the sftp server with the trailing
     * @param permissions the permissions that should be set on file after ftp. ie. "775"
     * "/"
     * @return boolean if the transfer of the file was successful or not
     */
    public boolean sendFile(String sftpUrl, String username, String password, String fileToSend, String destPath, String permissions);

    /**
     *
     * This method Sends a file to the sftp server using the user name provided
     * and the private ssh key located in the user's home folder
     *
     * @param sftpUrl of the sftp server. Do not include ftp://
     * @param username sftp username to sign into the ftp server as
     * @param fileToSend full path to the file that will be sent to the ftp
     * server
     * @param destPath path to upload to on the sftp server with the trailing
     * @param permissions the permissions that should be set on file after ftp. ie. "775"
     * "/"
     * @return boolean if the transfer of the file was successful or not
     */
    public boolean sendFile(String sftpUrl, String username, String fileToSend, String destPath, String permissions);

    /**
     *
     * @param sftpUrl
     * @param username
     * @param password
     * @param sftpDirectory
     * @param destDirectory
     * @return
     */
    public boolean recieveAllFiles(String sftpUrl, String username, String password, String sftpDirectory, String destDirectory);

    /**
     *
     * @param sftpUrl
     * @param username
     * @param password
     * @param sftpDirectory
     * @param destDirectory
     * @return
     */
    boolean moveAllFiles(String sftpUrl, String username, String password, String sftpDirectory, String destDirectory);

    boolean moveAllFiles(String sftpUrl, String username, String sftpDirectory, String destDirectory);

    boolean recieveAllFiles(String sftpUrl, String username, String sftpDirectory, String destDirectory);

}
