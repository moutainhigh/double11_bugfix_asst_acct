<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
 <link href="http://static.pinju.com/css/rao/scores.css" rel="stylesheet" type="text/css" media="screen" />
 <link href="http://static.pinju.com/css/rao/common.css" rel="stylesheet" type="text/css" media="screen" />
<SCRIPT src="http://static.pinju.com/js/points/accountDetail.js"></SCRIPT>
 <#setting classic_compatible=true>
  <div class="scores-right">
 <form name="searchForm" action="${base}/points/showAccountInfo.htm" method="post" onsubmit="return checkAccountDetail()" id="pageForm">
 <input type="hidden" value="red_sellpoints" id="my-page" />  
    <input type="hidden" value="red_sellpoints" id="my-page" /> 
    <ul class="act">
          <li class="here"><a href="#">积分总账户</a></li>
    </ul>
    <ul class="bold">
    <li>可用积分额：<span class="hong">${balance}</span></li>
    <li>查看交易记录： <select id="tradeChannel" name="tradeChannel">
					    <option value="">所有渠道</option>
					    <#list channelList as channel>
						    <#if channel.value == tradeChannel>
								<option value="${channel.value}" selected>${channel.name}</option>
							<#else>
						        <option value="${channel.value}">${channel.name}</option>
						     </#if>
					    </#list>
					    </select><input type="text" id="startDate" readonly name="startDate" value="${startDate!}"><input type="text" id="endDate" readonly name="endDate" value="${endDate!}" onchange="checkEndDate()"><input type="submit" value="搜索" class="pl-btn-hui"></li>
    </ul>
    
    
    
    
   <!-- <table>
                 <tr>
                    <th>渠道类型：</th>
                    <td> 
	                    <select id="tradeChannel" name="tradeChannel">
					    <option value="">全部类型</option>
					    <#list channelList as channel>
						    <#if channel.value == tradeChannel>
								<option value="${channel.value}" selected>${channel.name}</option>
							<#else>
						        <option value="${channel.value}">${channel.name}</option>
						     </#if>
					    </#list>
					    </select>
                   </td>
                   <td colspan="2"> <span id="error"  class="tip"></span></td>
                  </tr>
                <tr>
                 <th>开始时间：</th>
                 <td><input type="text" id="startDate" readonly name="startDate" value="${startDate!}"></td>
                 <th> 结束时间：</th>
                 <td><input type="text" id="endDate" readonly name="endDate" value="${endDate!}" onchange="checkEndDate()"></td>
                 <th>&nbsp;</th>
                 <td><input type="submit" value="搜索" class="pl-btn-hui"></td>
                </tr>
          </table> -->
        <#if resultList ??>
   <table cellspacing="0" cellpadding="0" class="scores">
      <tr>
        <td>时间</td>
        <td>操作</td>
        <td>操作积分值</td>
        <td>操作前积分余额</td>
        <td>操作后积分余额</td>
        <td>渠道</td>
        <td>备注</td>
      </tr>
				<#list resultList as accountDetail>
				  <tr>
				   <td>${accountDetail.createDate?date}</td>
				   <#if accountDetail.isAdd==0>
				      <td>消费</td>
				   <#else>
				      <td>获取</td>
				   </#if>
				   <td>${accountDetail.tradeAmount}</td>
				   <td>${accountDetail.beforeAmount}</td>
				   <td>${accountDetail.afterAmount}</td>
				   <td>${dictionaryUtil.queryDictionaryByValue(accountDetail.tradeChannel)}</td>
				   <td>${accountDetail.remark}</td>
				  </tr>
				</#list>
    </table>
    	<#include "/default/templates/control/bottomPage.ftl">
   	</#if>
   	</form>
   </div>