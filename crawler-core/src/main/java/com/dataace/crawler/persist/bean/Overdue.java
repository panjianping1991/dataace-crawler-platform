package com.dataace.crawler.persist.bean;

public class Overdue {

	private String originalUsername;
	private Double loanAmount;
	private Double overdueMoney;
	private Integer overdueDays;
	private Double overduePrincipalAndInterest;
	private String loanDate;
	private String loanPlatform;
	private String loanId;//标号
	
	private String termOfLoan;//借款期限
	private String status;
	private String remarks;
	private Integer overdueCount;
	
	
	
	public Integer getOverdueCount() {
		return overdueCount;
	}
	public void setOverdueCount(Integer overdueCount) {
		this.overdueCount = overdueCount;
	}
	public String getOriginalUsername() {
		return originalUsername;
	}
	public void setOriginalUsername(String originalUsername) {
		this.originalUsername = originalUsername;
	}
	
	
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Double getOverdueMoney() {
		return overdueMoney;
	}
	public void setOverdueMoney(Double overdueMoney) {
		this.overdueMoney = overdueMoney;
	}
	public Integer getOverdueDays() {
		return overdueDays;
	}
	public void setOverdueDays(Integer overdueDays) {
		this.overdueDays = overdueDays;
	}
	public Double getOverduePrincipalAndInterest() {
		return overduePrincipalAndInterest;
	}
	public void setOverduePrincipalAndInterest(Double overduePrincipalAndInterest) {
		this.overduePrincipalAndInterest = overduePrincipalAndInterest;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getLoanPlatform() {
		return loanPlatform;
	}
	public void setLoanPlatform(String loanPlatform) {
		this.loanPlatform = loanPlatform;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	
	public String getTermOfLoan() {
		return termOfLoan;
	}
	public void setTermOfLoan(String termOfLoan) {
		this.termOfLoan = termOfLoan;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
