	<#setting classic_compatible=true>
<LINK href="http://static.pinju.com/css/wuliu.css" rel="stylesheet" />
<script src="http://static.pinju.com/js/logistics/listTemplate.js"></script>

<title>品聚网</title>

<div class="wl-left">

<ul class="tab-main">
       <li><a href="${base}/logistics/listTemplate.htm">运费模板</a></li>
       <li class="count"><a href="javascript:void(0);">物流跟踪信息</a></li>
 </ul>

<form name="form" id="form" action=" " method="post">
<ul class="wl-chuangj">
	<li>物流公司：<select name="logisticsType" id="logisticsType" style="width:150px">
			<option value="">----物流名称----</option>
    		<#if logisticsCorpDOList?exists>
	           <#if (logisticsCorpDOList?size>0) >
         			<#list logisticsCorpDOList as lstDO>
         				<option value="${lstDO.corpCode!""}">${lstDO.corpName!""}</option>
					</#list>
				</#if>
			</#if>
		</select>
		<span class="hong" style="padding-left:15px" id="tipTypeMess"></span>
	</li>
<li>运单号码：<input type="text" style="width:150px" name="outLogisticsId" id="outLogisticsId"/>
<input type="button" value="查询"  class="pl-btn-juse" id="logisticsSearch"/> 
<span class="hong" id="tipBillMess"></span></li>
<li id="wuliuTip" style="margin-top:15px"> </li>
</ul>
</form> 

<div  class="lst-express-result" id="J_SRLo">
    <h3 style="border-bottom:1px solid #ccc;">查询结果</h3>
    <table width="98%" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    <td style="border-bottom:1px solid #ccc;">
    <table width="98%" border="0" cellspacing="0" cellpadding="0">
     <thead>
      <tr class="hd">
        <th style="text-align: center;">时 间 </th>
        <th style="text-align: left;">跟踪信息</th>
      </tr>
      </thead>
      <tbody id="trackInfo">
	  <tbody>
    </table>
    </td>
  </tr>
</table>
</div>
</div>
<p id="J_Loading" style="margin-top:15px"></p>
<input type="hidden" value="logistics-tool" id="my-page" />