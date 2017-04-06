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
<link href="http://static.pinju.com/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<#include "/default/templates/layout/asst/asst-role-title.ftl">
 <form action="${base}/asst/eidt-asst-role.htm" method="post" id="eidt-asst-role-form">
		<div class="subaccount">
			<div class="formbox">
				<div class="title">
					<h2>角色更新</h2>
				</div>
				<div class="content cf">
					<div class="row">
						<label> 角色名称：</label> 
						<span class="existtext">${roleName?html}</span>
					</div>
					<div class="row">
						<label> 角色描述：</label> 
						<input class="roledesc" type="text" id="roleDesc" name="roleDesc" value="${roleDesc?html}" maxlength="500"/>
						<span class="tips" style="display:inline;"></span>
					</div>
					<div class="row">
						<label> 角色权限：</label> 
						<div class="checkboxrow">
							<div class="row rolerule">
								<label for="selectall"><input type="radio" id="selectall" name="rolerule" /> 全选</label>
								<label for="selectnone"><input type="radio" id="selectnone" name="rolerule" /> 全不选</label>
							</div>
							<div class="row">
								<button onclick="expandAll(true);" type="button" onfocus="this.blur();" class="btn-expand">展开全部</button>
								<button onclick="expandAll(false);" type="button" onfocus="this.blur();" class="btn-collapse">折叠全部</button>
							</div>
							<div class="row col2">
								<ul id="tree" class="tree"></ul>
							</div>
						</div>
					</div>
					<div class="row btnrow btnbkd">
						<button type="submit" class="btn-l4" title="确认修改">确认</button>
						<button type="button" class="btn-l4" onclick="location.href='${base}/asst/asst-role-list.htm'" title="返回角色列表">返回列表</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="roleId" name="id" value="${id?html}"/> 
	<input type="hidden" id="roleName" name="roleName" value="${roleName?html}"/>
	<input type="hidden" id="permissions" name="permissions" value="${permissions?html}"/> 
	<input type="hidden" id="permissionIds" name="permissionIds" value="${permissionIds?html}"/>
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
<script type="text/javascript">var BASE='${base}';var jstr = '${jsonString}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
    var treeObj;var setting={checkable:true,isSimpleData:true,treeNodeKey:"id",treeNodeParentKey:"pId"};$(document).ready(function(){var zNodes=eval(jstr);treeObj=$("#tree").zTree(setting,zNodes);var permissionIds=$("#permissionIds").val();if(permissionIds!=""){var idsArray=permissionIds.split(",");for(var i=0;i<idsArray.length;i++){if(idsArray[i]!=""){var node=treeObj.getNodeByParam("id",idsArray[i]);if(node!=null){node.checked=true;treeObj.updateNode(node,true)}}}}});$(function(){$("#selectall").click(function(){if($(this).attr("checked")=="checked"){treeObj.checkAllNodes(true)}});$("#selectnone").click(function(){if($(this).attr("checked")=="checked"){treeObj.checkAllNodes(false)}});var b=function(){$("#role-err-id").addClass("tips-error").show().append("\u60a8\u8f93\u5165\u7684\u89d2\u8272\u540d\u79f0\u5df2\u5b58\u5728");return false};var a=function(){var c=treeObj.getCheckedNodes(true);if(c.length>0){var f="";var d="";for(var e=0;e<c.length;e++){if(c[e].target!=""){f+=c[e].target+":"+c[e].action+";";d+=c[e].id+","}}$("#permissions").val(f);$("#permissionIds").val(d)}};$("#eidt-asst-role-form").submit(function(){a()})});function expandAll(a){treeObj.expandAll(a)};
</script>
</body>
</html>