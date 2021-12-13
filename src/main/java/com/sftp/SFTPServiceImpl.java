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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;


public class SFTPServiceImpl implements SFTPService {

    private static int PORT = 22;
	private static final Logger LOG = LoggerFactory.getLogger(SFTPServiceImpl.class.getName());

    
    private Channel channel = null;
    private ChannelSftp c = null;
    private JSch jsch = new JSch();
    private Session session = null;

    private final int SESSION_TIMEOUT = 60000; //60 seconds

    
    public static void main(String[] args) {
    	boolean successful = false;

    	// Check how many arguments were passed in
        if(args.length == 0)
        {
            System.out.println("Proper Usage is: javaProgram <filename to ftp>");
            System.exit(0);
        } else  {
        	String filename = args[0];
        	String chmod = args[1];
        	
        	System.out.println("starting program.");
        	SFTPServiceImpl sftpservice = new SFTPServiceImpl();
        	
            //add private key
            if (!sftpservice.addPrivateKey()) {
                System.exit(0);
            }

            //TODO add username and url to connect to.
        	if (!sftpservice.setupSession("<username>", "<url>")) {
                System.exit(0);
        	}

            //put the file on the sftp server
            successful = sftpservice.putfile(filename, "/appsdev/uconn_files/CONCUR/InputFiles/", chmod);
            
            //Can also put a file with an exec.
            boolean execFtp = false;
            if (execFtp) {
                //test the connect and execute command
        		String command = "echo \"Sit down, relax, mix yourself a drink and enjoy the show...\" >> /appsdev/uconn_files/CONCUR/InputFiles/test.out";
        		try {
        			sftpservice.session.connect();
        			sftpservice.channel = sftpservice.session.openChannel("exec");

        			//test with uptime command
        			//((ChannelExec) sftpservice.channel).setCommand("uptime");
        			
        			//test with a put file
        			((ChannelExec) sftpservice.channel).setCommand(command);

        			((ChannelExec) sftpservice.channel).setPty(false);
        			
        			sftpservice.channel.connect();
        			sftpservice.channel.disconnect();
        			sftpservice.session.disconnect();
        		}
        		catch (JSchException e) {
        			System.out.println("Error durring SSH command execution. Command: " + command);
                    e.printStackTrace();
        		}
            }
        	
        }

        System.out.println("ending program.");
        System.exit(0);
        
    }
    
    
    
    
    /**
     *
     * Sends a file to the sftp server
     *
     * @param sftpUrl
     * @param username sftp username to sign into the ftp server as
     * @param password sftp password for the user signing into the sftp server
     * @param fileToSend full path to the file that will be sent to the ftp
     * server
     * @param destPath path to upload to on the sftp server including the
     * trailing /
     * @return boolean if the transfer of the file was successful or not
     */
    @Override
    public boolean sendFile(String sftpUrl, String username, String password, String fileToSend, String destPath, String permissions) {
        boolean successful = false;

        //setup the session object
        if (!this.setupSession(username, sftpUrl)) {
            return false;
        }
        //set the password because password is being passed
        session.setPassword(password);

        //put the file on the sftp server
        successful = this.putfile(fileToSend, destPath, permissions);
        return successful;
    }

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
     * "/"
     * @return boolean if the transfer of the file was successful or not
     */
    @Override
    public boolean sendFile(String sftpUrl, String username, String fileToSend, String destPath, String permissions) {
        boolean successful = false;

        if (!addPrivateKey()) {
            return false;
        }

        //setup the session object
        if (!this.setupSession(username, sftpUrl)) {
            return false;
        }

        //put the file on the sftp server
        successful = this.putfile(fileToSend, destPath, permissions);
        session.disconnect();
        return successful;
    }

    // KPS-844
    @Override
    public boolean recieveAllFiles(String sftpUrl, String username, String password, String sftpDirectory, String destDirectory) {
        boolean successful = false;

        //setup the session object
        if (!this.setupSession(username, sftpUrl)) {
            return false;
        }
        //set the password because password is being passed
        session.setPassword(password);
        successful = getAllFiles(sftpDirectory, destDirectory);
        return successful;
    }

    @Override
    public boolean recieveAllFiles(String sftpUrl, String username, String sftpDirectory, String destDirectory) {
        boolean successful = false;

        if (!addPrivateKey()) {
            return false;
        }

        //setup the session object
        if (!this.setupSession(username, sftpUrl)) {
            return false;
        }
        successful = getAllFiles(sftpDirectory, destDirectory);
        return successful;
    }

    @Override
    public boolean moveAllFiles(String sftpUrl, String username, String password, String sftpDirectory, String destDirectory) {
        boolean successful = false;

        //setup the session object
        if (!this.setupSession(username, sftpUrl)) {
            return false;
        }
        //set the password because password is being passed
        session.setPassword(password);
        successful = mvAllFiles(sftpDirectory, destDirectory);
        return successful;
    }

    @Override
    public boolean moveAllFiles(String sftpUrl, String username, String sftpDirectory, String destDirectory) {
        boolean successful = false;
        if (!addPrivateKey()) {
            return false;
        }
        //setup the session object
        if (!this.setupSession(username, sftpUrl)) {
            return false;
        }
        successful = mvAllFiles(sftpDirectory, destDirectory);
        return successful;
    }

    /**
     *
     * This method does the work of sending the file
     *
     * @param fileToSend
     * @param destPath
     * @return
     */
    private boolean putfile(String fileToSend, String destPath, String chmodPermissions) {
        boolean successful = false;
        try {
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;
            LOG.info("pwd: " + c.pwd());
            File f = new File(fileToSend);
            c.put(new FileInputStream(f), destPath + f.getName());

            if (StringUtils.isNotBlank(chmodPermissions)) {
                c.chmod(Integer.parseInt(chmodPermissions,8), destPath + f.getName());
            }

            c.quit();
            successful = true;
        } catch (JSchException | SftpException | IOException ex) {
            ex.printStackTrace();
            successful = false;
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
        return successful;
    }

    private boolean getfile(String sftpDirectory, String downloadFile, String destPath) {
        boolean successful = false;
        try {
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;
            c.cd(sftpDirectory);
            File file = new File(destPath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            c.get(downloadFile, fileOutputStream);
            fileOutputStream.close();
            fileOutputStream = null;
        } catch (JSchException | SftpException | IOException ex) {
            ex.printStackTrace();
            successful = false;
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }

        return successful;
    }

    // KPS-844
    private boolean getAllFiles(String sftpDirectory, String targetDirectory) {
        boolean successful = true;
        try {
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;
            c.cd(sftpDirectory);
            Vector vct = c.ls(".");
            if (!vct.isEmpty()) {
                for (Iterator itr = vct.iterator(); itr.hasNext();) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) itr.next();
                    SftpATTRS attrs = entry.getAttrs();
                    String sftpFile = entry.getFilename();
                    if (StringUtils.isNotBlank(sftpFile) && !attrs.isDir() && !attrs.isLink() && !sftpFile.equals(".") && !sftpFile.equals("..")) {
                        String newpath = FilenameUtils.concat(targetDirectory, sftpFile);
                        LOG.info("client file location: " + newpath);
                        FileOutputStream out = new FileOutputStream(new File(newpath));
                        c.get(sftpFile, out);
                        out.flush();
                        out.close();
                        LOG.info("Retrieved " + sftpFile + " to " + newpath);
                    }
                }
            }

        } catch (JSchException | SftpException | IOException ex) {
            ex.printStackTrace();
            successful = false;
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }

        return successful;
    }

    private boolean mvAllFiles(String sftpDirectory, String targetDirectory) {
        boolean successful = true;
        try {
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;
            c.cd(sftpDirectory);
            Vector vct = c.ls(".");
            List<String> filenames = new ArrayList<String>();
            if (!vct.isEmpty()) {
                for (Iterator itr = vct.iterator(); itr.hasNext();) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) itr.next();
                    String sftpFile = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (StringUtils.isNotBlank(sftpFile) && !attrs.isDir() && !attrs.isLink() && !sftpFile.equals(".") && !sftpFile.equals("..")) {
                        filenames.add(sftpFile);
                    }
                }
                for (String filename : filenames) {
                    String newpath = targetDirectory + "/" + filename;
                    LOG.info("Attempting Rename of host file " + filename + " to " + newpath);
                    c.rename(filename, newpath);
                    LOG.info("Renamed host file " + filename + " to " + newpath);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            successful = false;
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
        return successful;
    }

    /**
     *
     * This method sets up the session object
     *
     * @param username
     * @param sftpUrl
     * @return
     */
    private boolean setupSession(String username, String sftpUrl) {
        boolean successful = false;
        try {
            session = jsch.getSession(username, sftpUrl, PORT);
//            session.setTimeout(SESSION_TIMEOUT);
            setupLogger();
            session.setConfig(setupSessionConfig());
            successful = true;
        } catch (JSchException ex) {
            LOG.error("setupSession exception, " + ex);
            ex.printStackTrace();
            successful = false;
        }
        return successful;
    }

    private java.util.Properties setupSessionConfig() {
        java.util.Properties config = new java.util.Properties();
        //added so that the UnknownHostKey exception does not occur and there won't be a need to add the host to the known_hosts file
        config.put("StrictHostKeyChecking", "no");
        config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");
        return config;
    }

    //KPS-957 Added logger for debugging of ftp transactions.
    private void setupLogger() {
        try {
            JSch.setLogger(new CustomSftpLogger());
        } catch (Exception e) {
            LOG.error("error setting jsch logger, " + e);
        }
    }

    public class CustomSftpLogger implements com.jcraft.jsch.Logger {

        @Override
        public boolean isEnabled(int level) {
            return true;
        }

        @Override
        public void log(int level, String message) {
            LOG.info("JSCH LOGGING level: " + level + " msg: " + message);
        }
    }

    private boolean addPrivateKey() {
        boolean result = true;
        try {
            //adds an identity with the private key from the user's home directory
            LOG.info("adding identity, " + System.getProperty("user.home") + "/.ssh/id_rsa");
            jsch.addIdentity(System.getProperty("user.home") + "/.ssh/id_rsa");
            LOG.info("added identities: " + jsch.getIdentityNames());
        } catch (JSchException ex) {
            ex.printStackTrace();
            try {
                //adds an identity with the private key from the user's home directory
                LOG.info("adding identity, " + System.getProperty("user.home") + "/.ssh/id_dsa");
                jsch.addIdentity(System.getProperty("user.home") + "/.ssh/id_dsa");
                LOG.info("added identities: " + jsch.getIdentityNames());
            } catch (JSchException ex2) {
                ex2.printStackTrace();
                result = false;
            }
        }
        return result;
    }

}
