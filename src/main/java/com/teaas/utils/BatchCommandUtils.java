package com.teaas.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchCommandUtils {

	protected static Logger logger = LoggerFactory.getLogger(BatchCommandUtils.class);
	
	private static String OS = System.getProperty("os.name").toLowerCase();

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
 	}
 
	public static boolean isMac() {
 		return (OS.indexOf("mac") >= 0);
 	}
 
	public static boolean isUnix() {
 		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 	}
 
	public static boolean isSolaris() {
 		return (OS.indexOf("sunos") >= 0);
 	}
	
	private static void setSystemVariables(ProcessBuilder pb) {
		Map<String, String> env = pb.environment();
		env.put("LISA_MORE_VM_PROPS", "-Dlisa.tmpdir=/tas/tmp");
	}
	
	/**
	 * Execute a single command.
	 * @param cmd
	 * @return
	 */
	public static BatchCommandResult executeCommand(String cmd){
		BatchCommandResult batchResult = new BatchCommandResult();
		final ProcessBuilder pb = (isWindows()?  new ProcessBuilder("cmd", "/c", cmd) : new ProcessBuilder("sh", "-c", cmd));
		batchResult.getMessageHandler().addMessage(TransactionMessageType.INFO, "BatchCommandUtils: " + "Executing batch command, " + cmd);
		logger.info(" executing cmd : " + cmd);
		batchResult = executeCommand(pb);
		return batchResult;
	}
	
	/**
	 * Execute multiple commands in succession.
	 * @param commands
	 * @return
	 */
	public static BatchCommandResult executeCommand(List<String> commands){
		BatchCommandResult batchResult = new BatchCommandResult();
		
		List<String> pbcommand = new ArrayList<String>();
		if (isWindows()) {
			pbcommand.add("cmd");
			pbcommand.add("/c");
			
		} else {
			pbcommand.add("sh");
			pbcommand.add("-c");
		}
		Iterator<String> iter = commands.iterator();
		while (iter.hasNext()) {
			pbcommand.add(iter.next());
			if (iter.hasNext()) {
				pbcommand.add(" && "); //chain multiple commands.
			}
		}
		final ProcessBuilder pb = new ProcessBuilder(pbcommand);
		batchResult.getMessageHandler().addMessage(TransactionMessageType.INFO, "BatchCommandUtils: " + "Executing batch command, " + pbcommand);
		logger.info(" executing cmd : " + pbcommand);
		batchResult = executeCommand(pb);
		return batchResult;
	}
	
	private static BatchCommandResult executeCommand(ProcessBuilder pb){
		BatchCommandResult batchResult = new BatchCommandResult();
		try {
			setSystemVariables(pb);
			Process process = pb.start();
			
			StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");
			StreamGobbler outputGobbler = new StreamGobbler(process.getInputStream(), "OUTPUT");

			errorGobbler.start();
			outputGobbler.start();
			
			int status = process.waitFor();
			
			logger.debug("lisa script finish.........."+status);
			
			batchResult.setProcessStatus(status);
			batchResult.setOutputLog(outputGobbler.getOutputBuffer().toString());
			batchResult.setErrorLog(errorGobbler.getOutputBuffer().toString());
			
			for (String outmsg : outputGobbler.getOutputMessages()) {
				batchResult.getMessageHandler().addMessage(TransactionMessageType.INFO, "BatchCommandUtils: " + outmsg);
			}
			for (String outmsg : errorGobbler.getOutputMessages()) {
				batchResult.getMessageHandler().addMessage(TransactionMessageType.ERROR, "BatchCommandUtils: " + outmsg);
			}
			
		} catch (IOException e) {
			batchResult.setProcessStatus(-1);
			batchResult.getMessageHandler().addMessage(TransactionMessageType.ERROR, "BatchCommandUtils: " + "IOException: " + e.getMessage());
		} catch (InterruptedException e) {
			batchResult.setProcessStatus(-1);
			batchResult.getMessageHandler().addMessage(TransactionMessageType.ERROR, "BatchCommandUtils: " + "InterruptedException: " + e.getMessage());
		}
		return batchResult;
	}
}
