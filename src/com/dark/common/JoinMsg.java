package com.dark.common;

public class JoinMsg extends Message {
	/**
	 * The body of this class was taken from WI_SoftwareEngineering-master.zip from Bradley Richards
	 * chatLab example
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	public JoinMsg(String name) {
		super(MessageType.Join);
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	@Override
	public String toString() {
		return type.toString() + '|' + name;
	}
}
