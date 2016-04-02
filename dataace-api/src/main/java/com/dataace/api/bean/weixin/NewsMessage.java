package com.dataace.api.bean.weixin;

import java.util.List;

public class NewsMessage extends WeixinMessage {

	private List<ArticleItem> items;

	public List<ArticleItem> getItems() {
		return items;
	}

	public void setItems(List<ArticleItem> items) {
		this.items = items;
	}
	
	
}
