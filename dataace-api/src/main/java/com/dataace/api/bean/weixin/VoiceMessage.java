package com.dataace.api.bean.weixin;

public class VoiceMessage extends WeixinMessage {

	private String mediaId;
	private String format;//语音格式
	private String recognition;//语音识别后的文字，需开通语音识别功能
	private String msgId;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	
	public String getRecognition() {
		return recognition;
	}
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	
}
