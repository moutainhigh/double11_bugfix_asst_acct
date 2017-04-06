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
 <form action="${base}/asst/add-asst-role.htm" method="post" id="asst-role-form">
		<div class="subaccount">
			<div class="formbox">
				<div class="title">
					<h2>角色设置</h2>
				</div>
				<div class="content cf">
					<div class="row">
						<label><em>*</em> 角色名称：</label> 
						<input type="text" id="roleName" name="roleName" value="${roleName?html}" title="请输入角色名称" class="<#if (validator['roleName'])??>error</#if>" maxlength="20"/> 
						<span id="role-err-id" class="tips <#if (validator['roleName'])??>tips-error</#if>" style="display:inline;"><#if (validator['roleName'])??>${(validator['roleName'])!}<#else>请输入角色名称</#if></span>
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
						<button type="submit" class="btn-l4">确认</button>
						<button type="button" class="btn-l4" onclick="location.href='${base}/asst/asst-role-list.htm'" title="返回角色列表">返回列表</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="permissions" name="permissions" value="${permissions?html}"/> 
	<input type="hidden" id="permissionIds" name="permissionIds" value="${permissionIds?html}"/>
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
<script type="text/javascript">var BASE='${base}';var jstr = '${jsonString}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
    var treeObj;var setting={checkable:true,isSimpleData:true,treeNodeKey:"id",treeNodeParentKey:"pId"};$(document).ready(function(){var zNodes=eval(jstr);treeObj=$("#tree").zTree(setting,zNodes);var permissionIds=$("#permissionIds").val();if(permissionIds!=""){var idsArray=permissionIds.split(",");for(var i=0;i<idsArray.length;i++){if(idsArray[i]!=""){var node=treeObj.getNodeByParam("id",idsArray[i]);if(node!=null){node.checked=true;treeObj.updateNode(node,true)}}}}});$(function(){$("#selectall").click(function(){if($(this).attr("checked")=="checked"){treeObj.checkAllNodes(true)}});$("#selectnone").click(function(){if($(this).attr("checked")=="checked"){treeObj.checkAllNodes(false)}});var showValidateError=function(){$("#role-err-id").addClass("tips-error").show().append("\u60a8\u8f93\u5165\u7684\u89d2\u8272\u540d\u79f0\u5df2\u5b58\u5728");return false};var setCheckedPermission=function(){var checkedNodes=treeObj.getCheckedNodes(true);if(checkedNodes.length>0){var permissionStr="";var permissionIds="";for(var i=0;i<checkedNodes.length;i++){if(checkedNodes[i].target!=""){permissionStr+=checkedNodes[i].target+":"+checkedNodes[i].action+";";permissionIds+=checkedNodes[i].id+","}}$("#permissions").val(permissionStr);$("#permissionIds").val(permissionIds)}};var roleExist=function(){var ret=true;$.ajax({type:"POST",async:false,url:BASE+"/async/member/rolename.htm",data:{roleName:$("#roleName").val()},dataType:"json",dataFilter:function(json){var j=eval("("+json+")");if(j.result==true){return true}ret=showValidateError()}});return ret};$("#asst-role-form").effectiveAndValidate({submitHandler:function(form){if(roleExist()){setCheckedPermission();form.submit()}},rules:{roleName:{required:true,byterangelength:[1,20],pattern:/^[\u4e00-\u9faf_0-9a-zA-Z-]+$/}},messages:{roleName:{required:"\u8bf7\u8f93\u5165\u89d2\u8272\u540d",byterangelength:"\u89d2\u8272\u540d\u75311~20\u82f1\u6587\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u6c49\u5b57\u3001\u201c-\u201d \u548c\u4e0b\u5212\u7ebf\u7ec4\u6210",pattern:"\u89d2\u8272\u540d\u7531\u82f1\u6587\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u6c49\u5b57\u3001\u201c-\u201d \u548c\u4e0b\u5212\u7ebf\u7ec4\u6210"}},errorPlacement:function(error,element){element[0].showError(error.text())}},{wrapSelector:"div.row",inputErrorClass:"error",inputNormalClass:"",message:{defaultClass:"tips",errorClass:"tips-error"}})});function expandAll(a){treeObj.expandAll(a)};
</script>
</body>
</html>