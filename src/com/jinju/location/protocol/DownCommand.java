package com.jinju.location.protocol;

import java.io.Serializable;

//{"Command":"83","NodeId":"11601811","Target":"node","ContentType":"byte", "Content":""}
public class DownCommand  implements Serializable{
	private String NodeId;
	private String Command;
	private String Target;
	private String ContentType;
	private String Content;
	public String getNodeId() {
		return NodeId;
	}
	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}
	public String getCommand() {
		return Command;
	}
	public void setCommand(String command) {
		Command = command;
	}
	public String getTarget() {
		return Target;
	}
	public void setTarget(String target) {
		Target = target;
	}
	public String getContentType() {
		return ContentType;
	}
	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
}
