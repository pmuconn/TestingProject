package com.eesPropSetup;



import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PropertiesSetup extends JPanel
                             implements ActionListener {
	
    private JFileChooser fc;
    
    private JTextArea log, siteCopy;

    private JButton selectButton, closeButton;
    
    static private final String newline = "\n";
    
    private int filesCopied;
    
    private int exceptionsFound;
    
    /**
     * Private class which implements the Comparator interface
     */ 
    private class AlphaThenDirectoriesLast implements Comparator<File> {
        /**
         * To sort files alphabetically with directories after files.
         * 
         * Comparator interface requires defining compare method.
         * 
         * @param File
         * @param File
         * 
         */
        public int compare(File filea, File fileb) {
            //... Sort directories after files,
            //    otherwise alphabetical ignoring case.
            if (filea.isDirectory() && !fileb.isDirectory()) {
                return 1;

            } else if (!filea.isDirectory() && fileb.isDirectory()) {
                return -1;

            } else {
                return filea.getName().compareToIgnoreCase(fileb.getName());
            }
        }
    }	    
    
    /**
     * Create the GUI and show it. For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the main window.
        JFrame frame = new JFrame("Employer eServices Properties Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the window.
        frame.add(new PropertiesSetup());

        // Display the window.
        frame.pack();
        frame.setVisible(true);
        
        // Size the window
        frame.setSize(1000, 500);
        
        // Position the window
        frame.setLocation(15, 150);
    }
    
    /**
     * The main method used to run the application
     * @param String[]
     */
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                createAndShowGUI();
            }
        });
    }

    /**
     * No argument constructor
     */
    public PropertiesSetup() {
        super(new BorderLayout());

        // Create the log pane first, because the action listeners
        // need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        log.setEnabled(true);
        JScrollPane logScrollPane = new JScrollPane(log);

        // Create a file chooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setCurrentDirectory(new File("C:\\"));
        fc.setDialogTitle("Navigate to your main workspace directory and click 'Start' to begin setup.");
        
        // Create the select button.  
        // We use the image from the JLF Graphics Repository (but we extracted it from the jar).
        selectButton = new JButton("Select a Workspace Directory...", createImageIcon("Open16.gif"));
        selectButton.addActionListener(this);

        //Create the close button.(no image)  
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        
        // Create the Site Copy text area
        StringBuffer message = new StringBuffer("Use this tool to create your local 'properties' directories and to" + newline);
        message.append("populate those directories from your workspace directories." + newline);
        message.append("Click the 'Select a Workspace Directory...' button to naviagate" + newline);
        message.append("to the main directory of your local workspace branch or trunk." + newline);
        
        siteCopy = new JTextArea(message.toString());
        //siteCopy.setMargin(new Insets(5,5,5,5));
        siteCopy.setEditable(false);
        Font font = new Font("Verdana", Font.PLAIN, 14);
        siteCopy.setFont(font);
        siteCopy.add(selectButton);
        
        // Create the header panel area to include the site copy and select button
        JPanel headerPanel = new JPanel(); //use BorderLayout
        headerPanel.add(siteCopy);
        headerPanel.add(selectButton);
        siteCopy.setBackground(headerPanel.getBackground());
        
        // Create the footer panel area to include the close button
        JPanel footerPanel = new JPanel(); //use FlowLayout
        footerPanel.add(closeButton);
                
        // Add the site copy, buttons and the log to this panel.
        this.add(headerPanel, BorderLayout.PAGE_START); //use BorderLayout
        this.add(logScrollPane, BorderLayout.CENTER); //use BorderLayout
        this.add(footerPanel, BorderLayout.SOUTH); //use BorderLayout
    }
    
    /**
     * Determine what action to perform.
     * @param ActionEvent
     */
    public void actionPerformed(ActionEvent e) {

        // Handle select button action.
        if (e.getSource() == selectButton) {
        	// Using the generic showDialog method which allows customization
            int returnVal = fc.showDialog(PropertiesSetup.this, "Start");

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Main Workspace Directory Selected: " + file.getPath() + newline); 
                log.update(log.getGraphics()); 
                
                // Verify that the directories exist or create them.
                log.append(newline + "------ Create Main Directories ------" + newline); 
                createDirectories();
                log.update(log.getGraphics());
                log.scrollRectToVisible(new Rectangle(0,log.getHeight()-2,1,1));
                
        	    // Copy the entire directory from the workspace to the local file system.
                log.append(newline + "------ Copy Directories/Files ------" + newline); 
            	log.append("Copy Directories/Files from: " + file.getPath() + "\\ees-model-conf" + " to: " + "C:\\epprod\\eesdist\\dev-local\\config\\ees-model" + newline);
                copyDirectoryFiles(file.getPath() + "\\ees-model-conf", "C:\\epprod\\eesdist\\dev-local\\config\\ees-model");
            	log.append("Copy Directories/Files from: " + file.getPath() + "\\eesa-model-conf" + " to: " + "C:\\epprod\\eesdist\\dev-local\\config\\eesa-model" + newline);
                copyDirectoryFiles(file.getPath() + "\\eesa-model-conf", "C:\\epprod\\eesdist\\dev-local\\config\\eesa-model");
            	log.append("Copy Directories/Files from: " + file.getPath() + "\\ees-model-conf\\properties\\dev1" + " to: " + "C:\\epprod\\eesdist\\dev-local\\config\\ees-model\\properties\\dev-local" + newline);
                copyDirectoryFiles(file.getPath() + "\\ees-model-conf\\properties\\dev1", "C:\\epprod\\eesdist\\dev-local\\config\\ees-model\\properties\\dev-local");
                log.update(log.getGraphics());
                log.scrollRectToVisible(new Rectangle(0,log.getHeight()-2,1,1));
                
                // Copy individual files from the workspace to the local file system.
                log.append("------ Copy Specific Files ------" + newline);
                filesCopied = 0;
                copyFile(file.getPath() + "\\ees\\classes12.zip", "C:\\epprod\\eesdist\\dev-local\\lib\\classes12.zip");
                //filesCopied++;
                copyFile(file.getPath() + "\\ees\\db2java.zip", "C:\\epprod\\eesdist\\dev-local\\lib\\db2java.zip");
                //filesCopied++;
                copyFile(file.getPath() + "\\ees\\log4j-1.2.15.jar", "C:\\epprod\\eesdist\\dev-local\\lib\\log4j-1.2.15.jar");
                //filesCopied++;
                copyFile(file.getPath() + "\\ees\\uht-exception-2.0.2.jar", "C:\\epprod\\eesdist\\dev-local\\lib\\uht-exception-2.0.2.jar");
                //filesCopied++;
                log.append("Files Copied: " + filesCopied + newline + newline);
                
                if (exceptionsFound == 0){
                	log.append("Completed !" + newline);
                } else {
                	log.append("Completed with " + exceptionsFound + " exceptions encountered !!" + newline);
                }
                
            } else { // Default to CANCEL_OPTION
                log.append("Select command cancelled by user." + newline);
            }
            
            // Position the cursor
            //log.setCaretPosition(log.getDocument().getLength()); // Old example code
            log.scrollRectToVisible(new Rectangle(0,log.getHeight()-2,1,1));

        // Handle Close button action.
        } else if (e.getSource() == closeButton) {
            System.exit(0);
        }
    }
    
    /**
     * Copy all files and sub-directories from the source to the destination.
     * @param String
     * @param String
     */
    private void copyDirectoryFiles(String workspaceDirectory, String propertiesDirectory) {
     
    	//log.append("Copy Directories/Files from: " + workspaceDirectory + " to: " + propertiesDirectory + newline);
    	
    	//int filesCopied = 0;
    	boolean displayTotals = true;
    	
		try {
			File workspaceDir = new File(workspaceDirectory);
			File workspacefilelist[] = workspaceDir.listFiles();
			
			// Sort the directory listing so that the sub-directories are after the files.
			Comparator<File> byAlphaThenDirectoriesLast = new AlphaThenDirectoriesLast();
			Arrays.sort(workspacefilelist, byAlphaThenDirectoriesLast);
			
			if (workspacefilelist != null && workspacefilelist.length > 0) {
	
				// Reset for each directory
		    	filesCopied = 0; 
		    	displayTotals = true;
		    	
		    	// Loop through the list of files in the Directory
				for (File workspacefile : workspacefilelist) {
					
					// Do not copy certain files based on the name
					if (!skipFile(workspacefile.getName())) {
						
						// Prefix the path to the filename for the File methods to work correctly.
						File l_baseFile = new File(workspacefile.getPath());
						
						// Check for a sub-directory.
						if (l_baseFile.isDirectory()){
							// Log the file totals processed prior to encountering the directories if not already logged
							if (displayTotals){
								log.append("Files Copied: " + filesCopied + newline + newline);
								log.update(log.getGraphics()); 
								log.scrollRectToVisible(new Rectangle(0,log.getHeight()-2,1,1));
							}
							displayTotals = false;
							
							// Create the sub-directory first
							log.append(createDirectory(propertiesDirectory + "\\" + l_baseFile.getName()));
							// Copy all files to the new sub-directory.
							copyDirectoryFiles(workspaceDirectory + "\\" + l_baseFile.getName(),
											  propertiesDirectory + "\\" + l_baseFile.getName());
							
						} else {
							// Copy just the file
							copyFile(workspaceDirectory + "\\" + l_baseFile.getName(),
								    propertiesDirectory + "\\" + l_baseFile.getName());
							//filesCopied++;
						}
					} else {
						log.append("Skip file: " + workspacefile.getName() + newline);
					}
				} // End of workspacefilelist. 
				
				// Log the totals for this directory if not already logged.
				if (displayTotals){
					log.append("Files Copied: " + filesCopied + newline + newline);
					log.update(log.getGraphics());
					log.scrollRectToVisible(new Rectangle(0,log.getHeight()-2,1,1));
				}
			} else {
				log.append(workspaceDirectory + " is empty" + newline);				
			}
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getCause() + " - " + ex + newline);
			log.append(newline + "**************************************************************" + newline);
			log.append("Exception: " + ex.getCause() + " - " + ex + newline);
			log.append("**************************************************************" + newline + newline);
			exceptionsFound++;
		}
	}
    
    
    /**
     * Copy the specific file from the source to the destination.
     * @param String
     * @param String
     */    
    public void copyFile(String srFile, String dtFile) {
		try {
			File f1 = new File(srFile);
			InputStream in = new FileInputStream(f1);

			File f2 = new File(dtFile);
			OutputStream out = new FileOutputStream(f2); //For Overwrite the file.

			log.append("Copy file: " + f1.getName() + newline);
			filesCopied++;
			
 			byte[] buf = new byte[1024];
 			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			
/*			int c;
		    while ((c = in.read()) != -1){
			      out.write(c);
			}*/
		    
		    in.close();
			out.close();

		} catch (FileNotFoundException ex) {
			System.out.println("FileNotFoundException: " + ex.getMessage() + " copying " + srFile + " to " + dtFile);
			log.append(newline + "**************************************************************" + newline);
			log.append("FileNotFoundException: " + ex.getMessage() + " copying " + srFile + " to " + dtFile + newline);
			log.append("**************************************************************" + newline + newline);
			exceptionsFound++;
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage() + " copying " + srFile + " to " + dtFile);
			log.append(newline + "**************************************************************" + newline);
			log.append("IOException: " + e.getMessage() + " copying " + srFile + " to " + dtFile + newline);
			log.append("**************************************************************" + newline + newline);
			exceptionsFound++;
		}
	}
    
    /**
     * Verify the local directories and create them if they don't exist.
     */
    private void createDirectories() {
    	
    	log.append(createDirectory("C:\\epprod"));
    	log.append(createDirectory("C:\\epprod\\eesdist"));
    	log.append(createDirectory("C:\\epprod\\eesdist\\dev-local"));
    	log.append(createDirectory("C:\\epprod\\eesdist\\dev-local\\config"));
    	log.append(createDirectory("C:\\epprod\\eesdist\\dev-local\\config\\ees-model"));
    	log.append(createDirectory("C:\\epprod\\eesdist\\dev-local\\config\\eesa-model"));
    	log.append(createDirectory("C:\\epprod\\eesdist\\dev-local\\lib"));

    	log.append(createDirectory("C:\\epprod\\logs"));
    	log.append(createDirectory("C:\\epprod\\logs\\ees"));
   
    	log.update(log.getGraphics());
    }
    
    /**
     * Create the local directories if they don't exist.
     * @param String
     * @return String
     */
    private String createDirectory(String strOutputDir) {
    	String returnValue;
 	    File file = null;
	
	    // Create the local directory only if it doesn't exist.
	    file = new File(strOutputDir + "\\");
	    if (file.isDirectory()) {
	    	// Log message only
	    	returnValue = "Sub Directory " + file.getPath() + " already exists." + newline;
	    } else {
	    	// Create the local directory
	    	file.mkdir();
	    	returnValue = "Sub Directory " + strOutputDir + " created." + newline;
	    }
	    
	    return returnValue;
	}
    
    /** Returns an ImageIcon, or null if the path was invalid. 
     * @param String
     */
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = PropertiesSetup.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            log.append("Couldn't find file: " + path + newline);
            return null;
        }
    }
    
	/**
     * Determine if the file should be skipped (not copied)
     * @param String
     * @return boolean
     */
    private boolean skipFile(String fileName) {
     
    	boolean returnValue = false;
	
		if (fileName.equals("META-INF") || 
			fileName.startsWith(".")) {
			returnValue = true;
		}
		
		return returnValue;
    }
    
}

