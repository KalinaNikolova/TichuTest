package com.dark.common;

public class ChatMsg extends Message {
/**
 * The body of this class was taken from WI_SoftwareEngineering-master.zip from Bradley Richards
 * chatLab example
 * 
 */
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String content;
	
	public ChatMsg(String name, String content) {
		super(MessageType.Chat);
		this.name = name;
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + name + '|' + sanitize(content);
	}
	
	private String sanitize(String in) {
		return in.replace('|', '/');
	}
}
