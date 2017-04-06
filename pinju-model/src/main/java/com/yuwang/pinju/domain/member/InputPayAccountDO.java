package com.yuwang.pinju.domain.member;

import org.hibernate.validator.constraints.NotEmpty;

public class InputPayAccountDO {

	private String account;
	private String username;
	private String agreement;
	private Integer accountType;

	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	@NotEmpty(message = "{inputPayAccount.agreement.notempty}")
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	@NotEmpty(message = "{inputPayAccount.username.notempty}")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotEmpty(message = "{inputPayAccount.account.notempty}")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
}
