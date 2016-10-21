/**
 * 
 */
package com.teaas.utils;

/**
 * @author pmorano
 *
 */
public class BatchCommandResult {
	private Integer processStatus = 0;
	private String outputLog = "";
	private String errorLog = "";
	private TransactionMessageHandler messageHandler = new TransactionMessageHandler();

	/**
	 * @return the processStatus
	 */
	public Integer getProcessStatus() {
		return processStatus;
	}
	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	/**
	 * @return the outputLog
	 */
	public String getOutputLog() {
		return outputLog;
	}
	/**
	 * @param outputLog the outputLog to set
	 */
	public void setOutputLog(String outputLog) {
		this.outputLog = outputLog;
	}
	/**
	 * @return the errorLog
	 */
	public String getErrorLog() {
		return errorLog;
	}
	/**
	 * @param errorLog the errorLog to set
	 */
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	/**
	 * @return the messageHandler
	 */
	public TransactionMessageHandler getMessageHandler() {
		return messageHandler;
	}
	
	
}
