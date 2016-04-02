package com.dataace.api.bean.weixin;

public enum EventType {

	SUBSCRIBE {// 关注事件
		@Override
		public String getName() {
			return "subscribe";
		}
	},
	SCAN {// 二维码事件
		@Override
		public String getName() {
			return "scan";
		}
	},
	CLICK {// 自定义菜单点击事件
		@Override
		public String getName() {
			return "CLICK";
		}
	},
	LOCATION {// 报告地理位置事件
		@Override
		public String getName() {
			return "LOCATION";
		}
	};
	public abstract String getName();
}
