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
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<link href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />
<#include "/default/templates/layout/asst/asst-role-title.ftl">
<form action="${base}/asst/asst-role-list.htm" method="post" id="seachForm">
		<div class="subaccount">
			<div class="actionarea btnbkd">
				<button class="btn-m4" id="go-asst-role-button" title="新增角色"><span>新增角色</span></button>
			</div>
			<table class="oddrow accountlist">
				<tr>
					<th>角色名</th>
					<th>描述</th>
					<th>最后修改时间</th>
					<th>操作</th>
				</tr>
			<#if memberAsstRoleList?? && memberAsstRoleList.size() gt 0>
			  <#list memberAsstRoleList as role>
				<tr<#if role_index%2!=0> class="odd"</#if>>
					<td>${(role.roleName)?html}</td>
					<td>${(role.roleDesc)?html}</td>
					<td><#if role.gmtModified?exists>${role.gmtModified?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
					<td class="action">
					    <a href="javascript:void(0)" onclick="editRole(${role.id});" title="编辑【${(role.roleName)?html}】角色及拥有的权限">编辑</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" onclick="deleteRole(this, ${role.id})" title="删除【${(role.roleName)?html}】角色">删除</a>
					</td>
				</tr>
			 </#list>
			</#if>
			  <tr>
			    <td align="right" colspan="9"><div id="Pagination" class="pagination" style="float:right;width=80px;"></td>
			  </tr>
			</table>
		</div>
	</div>
   <input type="hidden" name="query.page" id="currPage" value="${query.page?html}">
   <input type="hidden" id="pages" value="${query.pages?html}">
</form>
<form action="${base}/asst/go-asst-role.htm" method="post" id="go-asst-role">
</form>
<form action="${base}/asst/go-eidt-asst-role.htm" method="post" id="go-eidt-asst-role">
  <input type="hidden" id="roleId" name="id" />
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript">
  $("#go-asst-role-button").click(function() {
     $("#go-asst-role").submit();
     return false;
  });
  
  function editRole(roleId) {
     $("#roleId").val(roleId);
     $("#go-eidt-asst-role").submit();
  }
  
  function deleteRole(obj, roleId) {
     art.dialog({
         title:'提示信息',
         lock:true,
         opacity:0.6,
         content:"您确定要删除该角色信息吗？<br />一经删除，所有分配该角色权限的账号将自动失去该角色拥有的权限",
         icon:"question",
         button:[{name:"确定",
                  callback: function () {
                       $.ajax({
							  type : 'POST',
				              async : false,
				              url : BASE + '/async/asst/del-asst-role.htm',
				              data : { roleId : roleId},
				              dataType : 'json',
				              dataFilter : function(json) {
				                  var j = eval('(' + json + ')');
								  if (j.result == true) {
								    $(obj).parent().parent().remove();
								  } else {
								     art.dialog({title:'错误', content:'角色删除失败!',icon:"error"});
								  }
				               }
						 });
				  },
                  focus:true
                  },{
					  name: '取消',
					  callback: function () {
						  return true;
					  }
					}]
     });
  }
  $(document).ready(function() {
		var pages = $("#pages").val();
		var currPage = $("#currPage").val();
		$("#Pagination").pagination(pages, {
			current_page : currPage - 1,
			num_edge_entries : 2,
			num_display_entries : 4,
			callback : function(page) {
				$("#shcm").val("pag");
				$("#currPage").val(++page);
				$("#seachForm").attr( {
					action : "asst-role-list.htm",
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
});
</script>
</body>
</html>