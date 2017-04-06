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
<form action="${base}/asst/asst-relation-list.htm" method="post" id="seachForm">
<#include "/default/templates/layout/asst/asst-acct-title.ftl">
		<div class="subaccount">
			<div class="actionarea btnbkd">
				<button class="btn-m8" id="go-asst-button" title="新增店铺员工账户"><span>新增店铺员工账户</span></button>
			</div>
			<table class="oddrow accountlist">
				<tr>
					<th>会员名</th>
					<th>最后一次登录时间</th>
					<th>最后一次登录IP地址</th>
					<th>角色</th>
					<th>描述</th>
					<th>操作</th>
				</tr>
			<#if memberAsstRelationList?? && memberAsstRelationList.size() gt 0>
			  <#list memberAsstRelationList as relation>
			     <tr<#if relation_index%2!=0> class="odd"</#if>>
					<td>${relation.asstLoginName}</td>
					<td><#if relation.lastLoginTime?exists>${relation.lastLoginTime?string("yyyy-MM-dd HH:mm:ss")}<#else>没有登录过</#if></td>
					<td><#if relation.lastLoginIp?exists>${relation.lastLoginIp}<#else>没有登录过</#if></td>
					<td>
					  <#if relation.memberAsstMemberRoleList?? && relation.memberAsstMemberRoleList.size() gt 0>
					    <#list relation.memberAsstMemberRoleList as role>
					       <a href="javascript:void(0)" onclick="goEidtRoleForm(${role.asstRoleId})" title="编辑【${role.asstRoleName}】角色">${role.asstRoleName}</a><#if role_has_next>,</#if>
					    </#list>
					  <#else>
					    无角色
					  </#if>
					</td>
					<td>${(relation.asstAcctDesc)?html}</td>
					<td class="action">
						<a href="javascript:void(0)" onclick="queryLog('${encoder.encodeMemberId(relation.asstMemberId)}','${relation.asstLoginName}')" title="查看【${relation.asstLoginName}】操作日志">查看日志</a> 
						<a href="javascript:void(0)" onclick="goEditAsst('${encoder.encodeMemberId(relation.asstMemberId)}')" title="编辑【${relation.asstLoginName}】信息及角色">编辑</a> 
						<a href="javascript:void(0)" onclick="setStatus(this);" value="${encoder.encodeMemberId(relation.asstMemberId)}" status="${relation.status}" title="<#if relation.status==0>冻结<#elseif relation.status == 2>解冻</#if>账号"><#if relation.status == 0>冻结</#if><#if relation.status == 2>解冻</#if></a>
					</td>
				</tr>
			  </#list>
				<tr>
			       <td align="right" colspan="6"><div id="Pagination" class="pagination" style="float:right;width=80px;"></td>
			    </tr>
			    <#else>
			       <tr>
			       <td align="center" colspan="6">没有子账号信息</td>
			    </tr>
			   </#if>
			</table>
		</div>
	</div>
   <input type="hidden" name="query.page" id="currPage" value="${query.page?html}">
   <input type="hidden" id="pages" value="${query.pages?html}">
 </form>
<input type="hidden" value="red_asstMember" id="my-page" />
<form action="${base}/asst/go-asst-relation.htm" method="post" id="go-asst-relation">
</form>
<form action="${base}/asst/go-eidt-asst-role.htm" method="post" id="go-eidt-asst-role-form">
  <input type="hidden" id="roleId" name="id" />
</form>
<form action="${base}/asst/find-oper-log.htm" method="post" id="oper-log">
   <input type="hidden" id="asstMemberId" name="query.inputAsstMemberId" />
   <input type="hidden" id="asstLoginName" name="query.asstLoginName" />
   <input type="hidden" name="first" value="first"/>
</form>
<form action="${base}/asst/go-edit-asst-relation.htm" method="post" id="go-edit-asst-relation">
   <input type="hidden" id="editAsstMemberId" name="inputAsstMemberId" />
</form>
<script type="text/javascript" src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script type="text/javascript">var BASE='${base}';</script>
<script type="text/javascript">
  $(function() {
     $("#go-asst-button").click(function() {
	     $("#go-asst-relation").submit();
	     return false;
	  });
  });

  function goEidtRoleForm(roleId) {
     $("#roleId").val(roleId);
     $("#go-eidt-asst-role-form").submit()
  }

  function goEditAsst(id) {
     $("#editAsstMemberId").val(id);
     $("#go-edit-asst-relation").submit();
  }

  function queryLog(asstMemberId, asstLoginName) {
    $("#asstMemberId").val(asstMemberId);
    $("#asstLoginName").val(asstLoginName);
    $("#oper-log").submit();
  }

  function setStatus(obj) {
     var stauts = $(obj).attr('status');
     var a = (stauts == "0" ? "您确定要冻结该账号吗？<br />一经冻结，该账号将无法再登录品聚！<br />但是您可以再将该账号解冻":"您确定要解除冻结账号吗？<br />一经解冻，该账户将可以登录并且进入您的店铺进行相应权限的操作");
     art.dialog({
         title:'提示信息',
         lock:true,
         opacity:0.6,
         content:a,
         icon:"question",
         button:[{name:"确定",
                  callback: function () {
                      var asstMemberId = $(obj).attr('value');
                      var status = $(obj).attr('status');
					  $.ajax({
							  type : 'POST',
				              async : false,
				              url : BASE + '/async/asst/relation.htm',
				              data : { asstMemberId : asstMemberId, status: status},
				              dataType : 'json',
				              dataFilter : function(json) {
				                  var j = eval('(' + json + ')');
								  if (j.result == true) {
								      var retStatus = status=="0"?2:0;
									  $(obj).attr('status', retStatus);
									  $(obj).empty().html(status=="0"?"解冻":"冻结");
								  } else {
								      art.dialog({title:'错误', content:'账号状态更新失败',icon:"error"});
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
					action : "asst-relation-list.htm",
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
   });
</script>
</body>
</html>