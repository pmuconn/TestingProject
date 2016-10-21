package com.teaas.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StreamGobbler extends Thread {
	InputStream is;
	String type = "";

	//StrinBuilder is faster than StringBuilder when synchronization isn't needed.
	//Since this is its own thread, we will not need synchronization.
	StringBuilder outputBuffer = new StringBuilder();
	List<String> outputMessages = new ArrayList<String>();

	public StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(type + ">" + line);
				outputBuffer.append(line).append("\n");
				outputMessages.add(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * @return the outputBuffer
	 */
	public StringBuilder getOutputBuffer() {
		return outputBuffer;
	}

	/**
	 * @return the outputMessages
	 */
	public List<String> getOutputMessages() {
		return outputMessages;
	}
}
