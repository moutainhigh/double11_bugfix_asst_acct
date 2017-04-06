<#setting classic_compatible=true>
<#setting url_escaping_charset='UTF-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>子账号管理</title>
</head>
<body>
<link media="screen" type="text/css" href="http://static.pinju.com/css/member/subaccount.css" rel="stylesheet">
<#include "/default/templates/layout/asst/asst-acct-title.ftl">
		<div class="subaccount">
			<div class="formbox">
				<div class="title">
					<h2>更新店铺员工账号</h2>
				</div>
				<div class="content cf">
				   <div style="padding:0 0 0 90px;">
						<div class="tips-normal">
							<p>账号信息更新成功</p>
						</div>
					</div>
					<div class="row">
						<label><em>*</em> 会员名：</label> 
						<span class="tips">${loginName?html}</span>
					</div>
					<div class="row">
						<label> 角色：</label> 
						<div class="checkboxrow">
						 <#if memberAsstMemberRoles?? && memberAsstMemberRoles.size() gt 0>
							<div class="row" id="roleselect">
							  <#list memberAsstMemberRoles as role>
							     ${role.asstRoleName}<#if role_has_next>,</#if>
							  </#list>
							</div>
						  <#else>
						    <div class="row">
								<label for="selectall">无角色</label>
							</div>
						  </#if>
						</div>
					</div>
					<div class="row">
						<label> 描述：</label> 
						<span class="tips">${asstAcctDesc?html}</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
<input type="hidden" value="red_asstMember" id="my-page" />
</body>
</html>