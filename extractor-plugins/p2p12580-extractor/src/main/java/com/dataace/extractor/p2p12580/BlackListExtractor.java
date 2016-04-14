package com.dataace.extractor.p2p12580;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dataace.crawler.download.Request;
import com.dataace.crawler.extractor.Extractor;
import com.dataace.crawler.persist.bean.BlackName;
import com.dataace.crawler.persist.bean.Gender;
import com.dataace.crawler.persist.bean.Overdue;
import com.dataace.crawler.util.Params;


public class BlackListExtractor implements Extractor<BlackName>{

	private static final Logger logger =  LogManager.getLogger(BlackListExtractor.class);
	
	public boolean isFailed(String content, Map<String, Object> extras) {
		if(null!=content&&content.contains("blacklist_search")){
			return false;
		}
		return true;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		Elements trs =doc.select("span.title2").get(0).parent().parent().parent().parent().select("tr");
		List<BlackName> blackNames = new ArrayList<BlackName>();
		for(int i=1;i<trs.size()-2;i++){
			Element tr = trs.get(i);
			if(tr.html().contains("dotted")){
				BlackName blackName = new BlackName();
				Element ele = tr.nextElementSibling();
				String regex = "姓名：([\\S\\s]*)欠款总额：￥?([\\S\\s]*)元 逾 期 笔 数：(\\d*)笔 \\[([\\s\\S]*)\\]";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(ele.text());
				List<Overdue> overdues = new ArrayList<Overdue>();
				Overdue overdue = new Overdue();
				overdues.add(overdue);
				blackName.setOverdues(overdues);
				if(matcher.find()){
					String name = matcher.group(1).trim();
					String overdueMoney = matcher.group(2).trim().replace(",", "");
					String overdueCount = matcher.group(3).trim();
					String loanPlatform = matcher.group(4).trim();
					blackName.setName(name);
					overdue.setLoanPlatform(loanPlatform);
					overdue.setOverdueMoney(Double.parseDouble(overdueMoney));
					overdue.setOverdueCount(Integer.parseInt(overdueCount));
					logger.info("name={},overdueMoney={},overdueCount={},loanPlatform={}",name,overdueMoney,overdueCount,loanPlatform);
				}
				ele =  ele.nextElementSibling();
				logger.info(ele.text());
				regex = "性别：([\\S\\s]*)Email：([\\S\\s]*) 网站代还笔数：([\\S\\s]*)笔";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(ele.text());
				if(matcher.find()){
					String gender = matcher.group(1).trim();
					String email = matcher.group(2).trim();
					if(gender.contains("男")){
						blackName.setGender(Gender.MALE);
					}else if(gender.contains("女")){
						blackName.setGender(Gender.FEMALE);
					}
					blackName.setEmail(email);
					logger.info("gender={},email={}",gender,email);
				}
				
				ele =  ele.nextElementSibling();
				logger.info(ele.text());
				regex = "身份证号：([\\S\\s]*)电话：([\\S\\s]*)最长逾期天数：([\\S\\s]*)天";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(ele.text());
				if(matcher.find()){
					String idCard = matcher.group(1).trim();
					String mobile = matcher.group(2).trim();
					String overdueDays = matcher.group(3).trim();
					overdue.setOverdueDays(Integer.parseInt(overdueDays));
					blackName.setIdCard(idCard);
					blackName.setMobile(mobile);
					blackName.setId(idCard);
					
					logger.info("idCard={},mobile={},overdueDays={}",idCard,mobile,overdueDays);
				}
				
				ele =  ele.nextElementSibling();
				logger.info(ele.text());
				regex = "所在地：([\\S\\s]*) QQ： ([\\S\\s]*)更 新 时 间：([\\S\\s]*)";
				pattern = Pattern.compile(regex);
				matcher = pattern.matcher(ele.text());
				if(matcher.find()){
					String address = matcher.group(1).trim();
					String qq = matcher.group(2).trim();
					logger.info("address={},qq={}",address,qq);
					blackName.setAddress(address);
					blackName.setQq(qq);
				}
				
				blackNames.add(blackName);
				
				
			}
		}
		
		return blackNames;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		Document doc = Jsoup.parse(content);
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		List<Request> requests = new ArrayList<Request>();
		if(!originalUrl.contains("page=")){
			String text = doc.select("select[name=sel_page]").last().parent().text();
			String pageRegex = "[\\s\\S]*当前页1/(\\d+)[^\\d][\\s\\S]*";
			Pattern pattern = Pattern.compile(pageRegex);
			Matcher matcher = pattern.matcher(text);
			if(matcher.find()){
				int totalPage = Integer.parseInt(matcher.group(1));
				for(int i=2;i<=totalPage;i++){
					String listUrl = "http://www.p2p12580.com/blacklist_search.asp?page="+i+"&id=0&strKeyWord=";
					Request request = new Request(listUrl);
					request.setDecode("gbk");
					requests.add(request);
				}
			}
			
		}
		
		return requests;
	}

}
