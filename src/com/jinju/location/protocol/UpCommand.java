package com.jinju.location.protocol;

import java.io.Serializable;

public class UpCommand implements Serializable{
	private String NodeId;
	private String Type;
	private String Content;
	private String SystemId;
	public String getNodeId() {
		return NodeId;
	}
	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}
	
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getGateId() {
		return SystemId;
	}
	public void setGateId(String gateId) {
		SystemId = gateId;
	}
}
