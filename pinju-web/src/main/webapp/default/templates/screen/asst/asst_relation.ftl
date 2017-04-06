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
	<form action="${base}/asst/add-asst-relation.htm" method="post" id="asst-relation-form">
		<div class="subaccount">
			<div class="formbox">
				<div class="title">
					<h2>新增店铺员工账号</h2>
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
						<label><em>*</em> 会员名：</label> 
						<input type="text" name="loginName" id="loginName" value="${loginName?html}" title="会员名为 4～20 个英文字母、数字、“-” 和下划线或者 2～10 个汉字" class="<#if (validator['loginName'])??>error</#if>" maxlength="20" autocomplete="off" /> 
						<span class="tips <#if (validator['loginName'])??>tips-error</#if>"><#if (validator['loginName'])??>${(validator['loginName'])!}<#else>您的员工可以使用此会员名登录</#if></span>
					</div>
					<div class="row">
						<label><em>*</em> 密码：</label> 
						<input type="password" name="password" id="password" maxlength="16" title="密码为大小写英文字母、数字、符号的 6～16 个字符" class="<#if (validator['password'])??>error</#if>"/>
						<span class="tips <#if (validator['password'])??>tips-error</#if>"><#if (validator['password'])??>${(validator['password'])!}<#else>密码为大小写英文字母、数字、符号的 6～16 个字符</#if></span>
					</div>
					<div class="row">
						<label><em>*</em> 重复输入密码：</label> 
						<input type="password" name="confirm" id="confirm" maxlength="16" title="请再次输入密码" class="<#if (validator['confirm'])??>error</#if>"/>
						<span class="tips <#if (validator['confirm'])??>tips-error</#if>"><#if (validator['confirm'])??>${(validator['confirm'])!}<#else>请再次输入密码</#if></span>
					</div>
					<div class="row">
						<label> 设置角色：</label> 
						<div class="checkboxrow">
						 <#if memberAsstRoleList?? && memberAsstRoleList.size() gt 0>
							<div class="row">
								<label for="selectall"><input type="checkbox" name="roleSelect" id="selectall" /> 全选</label>
							</div>
							<div class="row" id="roleselect">
							  <#list memberAsstRoleList as role>
							     <label for="role-${role_index}"><input id="role-${role_index}" type="checkbox" name="inputRoleId" value="${role.id}" <#if role.checked>checked="checked"</#if>/>${role.roleName}</label> 
							  </#list>
							</div>
						  <#else>
						    <div class="row">
								<label for="selectall"><a href="${base}/asst/go-asst-role.htm">设置角色</a></label>
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
						<button class="btn-l4" type="submit">完成</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
<input type="hidden" name="tid" value="" />
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/key.js?t=20111130.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/asst/asst-relation-min.js"></script>
</body>
</html>