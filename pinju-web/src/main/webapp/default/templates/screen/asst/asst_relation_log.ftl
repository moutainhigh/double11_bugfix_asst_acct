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
<script src="${base}/default/js/datePicker/WdatePicker.js"></script>

<#include "/default/templates/layout/asst/asst-acct-title.ftl">

<form action="${base}/asst/find-oper-log.htm" method="post" id="seachForm">
	<input id="target" type="hidden" name="query.target" value="${query.target}"/>
	<input id="paction" type="hidden" name="query.paction" value="${query.paction}"/>
		<div class="subaccount">
			<h2 class="subtitle">${query.asstLoginName} 操作日志</h2>
			<div class="actionarea btnbkd">
				时间：<input type="text" id="inputStartTime" name="query.inputStartTime" class="Wdate" value="${query.inputStartTime}" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-90}',maxDate:'%y-%M-{%d}'})" readOnly="true"/> 
				至 <input type="text" id="inputEndTime" name="query.inputEndTime" value="${query.inputEndTime}" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-90}',maxDate:'%y-%M-{%d}'})" class="Wdate" /> 
				&nbsp;&nbsp;&nbsp;&nbsp;
				  操作分类:<select id="action">
				        <option value=""></option>
				         <#if treePerVoList??>
				          <#list treePerVoList as perVo>
				            <#if perVo.label>
				              <optgroup label="${perVo.targetDesc}" value="${perVo.target}"/>
				              <#else>
				               <option value="${perVo.target}:${perVo.action}" <#if perVo.action == query.paction>selected="selected"</#if>>${perVo.actionDesc}</option>
				            </#if>
				          </#list>
				         </#if>
				       </select>
				 &nbsp;&nbsp;&nbsp;
				<button class="btn-sgray2" type="submit"><span>查询</span></button>
				<button class="btn-sgray2" type="button" onclick="location.href='${base}/asst/asst-relation-list.htm'" title="返回账号列表"><span>返回</span></button>
			</div>
			<table class="oddrow accountlist">
				<tr>
					<th>序号</th>
					<th>时间</th>
					<th>动作</th>
					<th>操作对象描述</th>
					<th>操作行为描述</th>
					<th>IP</th>
				</tr>
				<#if memberAsstOperLogList?? && memberAsstOperLogList.size() gt 0>
				  <#list memberAsstOperLogList as asstLog>
					<tr<#if asstLog_index%2!=0> class="odd"</#if>>
						<td width="10%">${asstLog.id}</td>
						<td width="10%"><#if asstLog.gmtCreate?exists>${asstLog.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
						<td width="30%" style="text-align:left;">${asstLog.operationLog?html}</td>
						<td width="15%">${asstLog.targetDesc}</td>
						<td width="15%">${asstLog.actionDesc}</td>
						<td width="15%">${asstLog.operationIp}</td>
					</tr>
				 </#list>
				 <tr>
			       <td align="right" colspan="6"><div id="Pagination" class="pagination" style="float:right;width=80px;"></td>
			    </tr>
				 <#else>
				   <tr>
				     <td colspan="4">没有登录日志</td>
				   </tr>
				</#if>
			</table>
		</div>
	</div>
<input type="hidden" name="query.inputAsstMemberId" value="${query.inputAsstMemberId?html}" />
<input type="hidden" name="query.page" id="currPage" value="${query.page?html}">
<input type="hidden" name="query.asstLoginName" id="asstLoginName" value="${query.asstLoginName?html}">
<input type="hidden" id="pages" value="${query.pages?html}">
</form>
<input type="hidden" value="red_asstMember" id="my-page" />
<script type="text/javascript">
  $(function() {
 	 $("#seachForm").submit(function() {
         var inputStartTime = $.trim($("#inputStartTime").val());
         var inputEndTime = $.trim($("#inputEndTime").val());
         if (inputStartTime != "" && inputEndTime != "") {
                 var startDate = new Date(Date.parse(inputStartTime.replace(/-/g,"/")));
			     var endDate = new Date(Date.parse(inputEndTime.replace(/-/g,"/")));
			     if(startDate > endDate) {
			        alert("开始时间大于结束时间!");
			        return false;
			     }
         }
     });
     
     $("#action").change(function(eventObject) {
         var target = eventObject.currentTarget.value;
         var split = target.split(":");
         $("#target").val(split[0]);
         $("#paction").val(split[1]);
     });
  });

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
					action : "find-oper-log.htm",
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
   });
</script>
</body>
</html>