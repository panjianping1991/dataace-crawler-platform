package com.dataace.api.bean.weixin;

public enum MessageType {
	TEXT{//文本消息
		@Override
		public String getName() {
			return "text";
		}
	},
	IMAGE{//图片消息
		@Override
		public String getName() {
			return "image";
		}
	},
	VOICE{//语音消息
		@Override
		public String getName() {
			return "voice";
		}
	},
	VIDEO{//视频消息
		@Override
		public String getName() {
			return "video";
		}
	},
	LOCATION{//地理位置消息
		@Override
		public String getName() {
			return "location";
		}
	},
	LINK{//地理位置消息
		@Override
		public String getName() {
			return "link";
		}
	},
	EVENT {// 报告地理位置事件
		@Override
		public String getName() {
			return "event";
		}
	};
	public abstract String getName();
}
