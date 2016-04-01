package com.dataace.api.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller

public class WeixinController {
	private final Logger logger = LoggerFactory.getLogger(GlobalController.class);
	
	@RequestMapping(value = "/weixin")
	public void  service(HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		logger.info("signature:"+signature);
		logger.info("timestamp:"+timestamp);
		logger.info("nonce:"+nonce);
		logger.info("echostr:"+echostr);
		if(null!=echostr){
			PrintWriter out;
			try {
				out = resp.getWriter();
				out.print(echostr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
