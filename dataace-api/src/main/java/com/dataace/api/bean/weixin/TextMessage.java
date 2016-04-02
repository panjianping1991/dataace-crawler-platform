package com.dataace.api.bean.weixin;

public class TextMessage extends WeixinMessage {

	 private String content;
	 private String msgId;
	 
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	 
	 
}
