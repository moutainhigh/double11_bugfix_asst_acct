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
	<form action="${base}/asst/edit-asst-relation.htm" method="post">
		<div class="subaccount">
			<div class="formbox">
				<div class="title">
					<h2>更新店铺员工账号</h2>
				</div>
				<div class="content cf">
				 <#if invokeMessage??>
				   <div style="padding:0 0 0 90px;">
						<div class="tips-error">
							<p>${invokeMessage}</p>
						</div>
					</div>
				 </#if>
					<div class="row">
						<label> 会员名：</label> 
						<span class="existtext">${loginName?html}</span>
					</div>
					<div class="row">
						<label> 设置角色：</label> 
						<div class="checkboxrow">
						 <#if memberAsstRoleList?? && memberAsstRoleList.size() gt 0>
							<div class="row">
								<label for="selectall"><input type="checkbox" name="" id="selectall" /> 全选</label>
							</div>
							<div class="row" id="roleselect">
							  <#list memberAsstRoleList as role>
							     <label for="role-${role_index}"><input id="role-${role_index}" type="checkbox" name="inputRoleId" value="${role.id}" <#if role.checked>checked="checked"</#if>/>${role.roleName}</label> 
							  </#list>
							</div>
						  <#else>
						    <div class="row">
								<label for="selectall"><a href="#">设置角色</a></label>
							</div>
						  </#if>
							<div class="row exinfo">
								<p>什么是角色？<br />角色是您设置的一个拥有不同权限的虚拟名称，您可以为角色赋予不同的权限，然后将角色赋予您的您的员工账号，该员工账号将获得角色的权限，一个员工账号可以有多个角色。您可以点击此处设置角色 <a href="${base}/asst/go-asst-role.htm" title="设置角色">设置角色</a></p>
							</div>
						</div>
					</div>
					<div class="row">
						<label> 描述：</label> 
						<input type="text" name="asstAcctDesc" id="asstAcctDesc" value="${asstAcctDesc?html}" title="最多输入20个汉字，您可以描述该账号信息"/>
						<span class="tips">最多输入20个汉字，您可以描述该账号信息</span>
					</div>
					<div class="row btnrow btnbkd">
						<button class="btn-l4" type="submit">更新</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
<input type="hidden" name="loginName" value="${loginName?html}" />
<input type="hidden" name="inputAsstMemberId" value="${inputAsstMemberId?html}" />
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
</body>
</html>