package com.dataace.api.bean.weixin;

public class Music {

	private String title;//音乐标题
	private String description;//音乐描述 
	private String musicUrl;//音乐链接
	private String hqMusicUrl;//高质量音乐链接，WIFI环境优先使用该链接播放音乐 
	private String thumbMediaId;//缩略图的媒体id，通过上传多媒体文件，得到的id 
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMusicUrl() {
		return musicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	public String getThumbMediaId() {
		return thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}
	
	
}
