/**
 * 
 */
package com.teaas.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for keeping track of batch transaction messages.
 * @author pmorano
 *
 */
public class TransactionMessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(TransactionMessageHandler.class);
	private Map<TransactionMessageType, List<TransactionMessage>> messages = new HashMap<TransactionMessageType, List<TransactionMessage>>();
	private List<TransactionMessage> sequentialMessages = new ArrayList<TransactionMessage>();

	public TransactionMessageHandler() {}

	/**
	 * Creates a handler with existing messages.
	 * @param inMessages
	 */
	public TransactionMessageHandler(List<TransactionMessage> inMessages) {
		super();
		this.addMessages(inMessages);
	}

	/**
	 * Add a new logging message.
	 * @param inMessageType - {@link TransactionMessageType}
	 * @param inMessageText
	 */
	public void addMessage(TransactionMessageType inMessageType, String inMessageText) {
		TransactionMessage msg = new TransactionMessage();
		msg.setDesc(inMessageText);
		msg.setType(inMessageType);
		this.addMessage(msg);
	}
	
	/**
	 * Adds a message
	 * @param inMessage
	 * @return true if message was added.
	 */
	public boolean addMessage(TransactionMessage inMessage) {
		boolean returnValue = false;
		
		this.sequentialMessages.add(inMessage);
		if (this.messages.containsKey(inMessage.getType())) {
			this.messages.get(inMessage.getType()).add(inMessage);
			returnValue = true;
		} else {
			List<TransactionMessage> msgList = new ArrayList<TransactionMessage>();
			msgList.add(inMessage);
			this.messages.put(inMessage.getType(), msgList);
			returnValue = true;
		}
		logger.debug("Message added: " + inMessage);
		return returnValue;
	}
	
	/**
	 * Add multiple messages by type.
	 * @param type
	 * @param inMessages
	 */
	public void addMessages(TransactionMessageType type, List<String> inMessages) {
		for (String msg : inMessages) {
			TransactionMessage tmsg = new TransactionMessage(type, msg);
			addMessage(tmsg);
		}
	}
	
	/**
	 * Adds messages to the existing stored messages.
	 * @param inMessages
	 */
	public void addMessages(List<TransactionMessage> inMessages) {
		for (TransactionMessage msg : inMessages) {
			this.addMessage(msg);
		}
	}
	
	/**
	 * @return true if critical messages exist, false otherwise
	 */
	public boolean hasCriticalMessages() {
		return this.messages.containsKey(TransactionMessageType.CRITICAL);
	}
	/**
	 * @return true if error messages exist, false otherwise
	 */
	public boolean hasErrorMessages() {
		return this.messages.containsKey(TransactionMessageType.ERROR);
	}
	
	/**
	 * @return true if info messages exist, false otherwise
	 */
	public boolean hasInfoMessages() {
		return this.messages.containsKey(TransactionMessageType.INFO);
	}
	
	/**
	 * @return true if warning messages exist, false otherwise
	 */
	public boolean hasWarningMessages() {
		return this.messages.containsKey(TransactionMessageType.WARNING);
	}
	
	public boolean hasMessages() {
		return !this.messages.isEmpty();
	}
	
	/**
	 * @return error messages, empty list if none exist.
	 */
	public List<TransactionMessage> getErrorMessages() {
		List<TransactionMessage> returnValue = new ArrayList<TransactionMessage>();
		if (this.messages.containsKey(TransactionMessageType.ERROR)) {
			returnValue = this.messages.get(TransactionMessageType.ERROR);
		}
		return returnValue; 
	}
	/**
	 * @return info messages, empty list if none exist.
	 */
	public List<TransactionMessage> getInfoMessages() {
		List<TransactionMessage> returnValue = new ArrayList<TransactionMessage>();
		if (this.messages.containsKey(TransactionMessageType.INFO)) {
			returnValue = this.messages.get(TransactionMessageType.INFO);
		}
		return returnValue; 
	}
	/**
	 * @return warning messages, empty list if none exist.
	 */
	public List<TransactionMessage> getWarningMessages() {
		List<TransactionMessage> returnValue = new ArrayList<TransactionMessage>();
		if (this.messages.containsKey(TransactionMessageType.WARNING)) {
			returnValue = this.messages.get(TransactionMessageType.WARNING);
		}
		return returnValue; 
	}
	
	/**
	 * @return All messages regardless of message type.
	 */
	public List<TransactionMessage> getAllMessages() {
		List<TransactionMessage> returnValue = new ArrayList<TransactionMessage>();
		
		for (Map.Entry<TransactionMessageType, List<TransactionMessage>> entry : this.messages.entrySet() ) {
			returnValue.addAll(entry.getValue());
		}
		return returnValue; 
	}
	
	/**
	 * @return Message formatted as [level]: description
	 */
	public List<String> getSequentialFormattedMessages() {
		List<String> msgs = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for (TransactionMessage msg : this.sequentialMessages) {
			sb = new StringBuffer();
			sb.append("[").append(msg.getType()).append("]: ").append(msg.getDesc());
			msgs.add(sb.toString());
		}
		return msgs;
	}
	
	public void logAllMessages() {
 		for (TransactionMessage msg : this.getAllMessages()) {
 			logger.info(msg.toString());
 		}
	}

	/**
	 * @return the sequentialMessages
	 */
	public List<TransactionMessage> getSequentialMessages() {
		return sequentialMessages;
	}
}
