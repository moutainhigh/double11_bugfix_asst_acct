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
					<h2>角色设置</h2>
				</div>
				<div class="content cf">
				    <div style="padding:0 0 0 90px;">
						<div class="tips-normal">
							<p>角色信息更新成功</p>
						</div>
					</div>
					<div class="row">
						<label>角色名称：</label> 
						<span class="tips">${roleName?html}</span>
					</div>
					<div class="row">
						<label> 角色描述：</label> 
						<span class="tips">${roleDesc?html}</span>
					</div>
					<div class="row">
						<label><em>*</em> 角色权限：</label> 
						<div class="checkboxrow">
							<div class="row col2">
								<ul id="tree" class="tree"></ul>
							</div>
						</div>
					</div>
					<div class="row btnrow btnbkd">
						<button type="submit" class="btn-l4">确认</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="roleId" name="id" value="${id?html}"/> 
	<input type="hidden" id="permissions" name="permissions" value="${permissions?html}"/> 
	<input type="hidden" id="permissionIds" name="permissionIds" value="${permissionIds?html}"/>
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript">
    var treeObj;
	var setting = {
		checkable: true,
        isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "pId"
     };
	$(document).ready(function(){
		var zNodes = eval(${jsonString});
		treeObj = $("#tree").zTree(setting, zNodes);
		var permissionIds = $("#permissionIds").val();
		if (permissionIds != "") {
		   var idsArray = permissionIds.split(",");
		   for (var i = 0; i < idsArray.length; i++) {
		       if(idsArray[i] != "") {
			        var node = treeObj.getNodeByParam("id", idsArray[i]);
			        if (node != null) {
			            node.checked = true;
	                    treeObj.updateNode(node, true);
			        }
		       }
		   }
		}
	});
</script>
</body>
</html>