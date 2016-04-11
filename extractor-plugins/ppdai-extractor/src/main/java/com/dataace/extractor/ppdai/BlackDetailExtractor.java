package com.dataace.extractor.ppdai;

import java.util.ArrayList;
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
import com.dataace.crawler.persist.bean.Overdue;
import com.dataace.crawler.util.Params;

public class BlackDetailExtractor implements Extractor<BlackName>{

	private static final Logger logger =  LogManager.getLogger(BlackDetailExtractor.class);
	
	public boolean isFailed(String content, Map<String, Object> extras) {
		if(null!=content&&content.contains("detail_table")){
			return false;
		}
		return true;
	}

	public List<BlackName> extract(String content, Map<String, Object> extras) {		
		Document doc = Jsoup.parse(content);
		String originalUrl = extras.get(Params.ORIGINAL_URL.toString()).toString();
		String userId = originalUrl.substring(originalUrl.lastIndexOf("/")+1);
		String loanAmount = doc.select(".detail_table > tbody > tr >td").first().text();
		loanAmount = loanAmount.replace("累计借入本金：", "").replace("¥", "").replace(",", "");
		Elements eles = doc.select(".table_nav > table > tbody > tr");
		BlackName blackName = new BlackName();
		blackName.setId(userId);
		List<Overdue> overdues= new ArrayList<Overdue>();
		blackName.setOverdues(overdues);
		String remarks = doc.select(".info_tit >span").first().text();
		for(int i=1;i<eles.size();i++){
			Element ele = eles.get(i);
			Elements fields = ele.select("td");
			String loanId = fields.get(0).text();
			String termOfLoan = fields.get(1).text();
			String loanDate = fields.get(2).text();
			String overdueDays= fields.get(3).text().replaceAll("天", "").replaceAll("\\s+", "");
			String overdueMoney = fields.get(4).text().replace("¥", "").replace(",", "");;
			logger.info("loanId:{},termOfLoad:{},loanDate:{},overdueDays:{},overDueMoney:{},loanAmount:{}",loanId,termOfLoan,loanDate,overdueDays,overdueMoney,loanAmount);
			Overdue overdue = new Overdue();
			overdue.setLoanPlatform("拍拍贷");
			overdue.setLoanId(loanId);
			overdue.setTermOfLoan(termOfLoan);
			overdue.setLoanDate(loanDate);
			overdue.setLoanAmount(Double.parseDouble(loanAmount));
			overdue.setOverdueMoney(Double.parseDouble(overdueMoney));
			overdue.setOverdueDays(Integer.parseInt(overdueDays));
			overdue.setRemarks(remarks);
			overdues.add(overdue);
			
		}
		String userInfo = doc.select(".blacklist_detail_nav ul li").first().html();
		String userRegex = "[\\s\\S]*用户名：(\\w+)[^\\W][\\s\\S]*姓名：(\\S+)\\s[\\s\\S]*手机号：(\\S+)\\s[\\s\\S]*身份证号：(\\S+)\\s[\\s\\S]*";
		Pattern pattern = Pattern.compile(userRegex);
		Matcher matcher = pattern.matcher(userInfo);
		if(matcher.find()){
			String username = matcher.group(1);
			String name = matcher.group(2);
			String mobile = matcher.group(3);
			String idCard = matcher.group(4);
			logger.info("username:{},name={},mobile={},idcard={}",username,name,mobile,idCard);
			blackName.setMobile(mobile);
			blackName.setIdCard(idCard);
			blackName.setName(name);
		}
		
		List<BlackName> blackNames = new ArrayList<BlackName>();
		blackNames.add(blackName);
		return blackNames;
	}

	public List<Request> split(String content, Map<String, Object> extras) {
		return null;
	}

}
