<@load.js src="/modules.js"/>
<title>我的分销商</title>
<link href="http://static.pinju.com/css/shopDecoration/base.css?t=20111203.css" rel="stylesheet" /> 
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->

        
      <div class="box_806 padding_10px fontgray_01">您好，${userName}，欢迎进入分销中心</div>
        
      <div class="box_806 padding_10px ltr_1px_gray bg_white"><span class="fontgray_03">我是卖家 > 云营销 ></span> <span class="fontred">爱分享管理</span></div>
      <div class="box_806 padding_10px border_1px_orange bg_yellow01 margin_bottom">
      	<div class="floatleft">申请中的爱分享<span class="fontred">（${applierNum!"0"}） </span></div>
        <div class="floatleft margin_left20">合作中的爱分享 <span class="fontred">（${cooperatorNum!"0"}） </span></div>
        <div class="floatleft margin_left20">终止关系的爱分享<span class="fontred"> （${expiredNum!"0"}） </span></div>
	  </div>
        
        
		<div class="box_806 lable_bg_style padding_lr_10px">
		   <div <#if pageType==0>class="lable_present"<#else>class="lable_after"</#if>> <a href="${base}/supplier/getApplier.htm">待审核</a> </div>
            <div <#if pageType==1>class="lable_present"<#else>class="lable_after"</#if>> <a href="${base}/supplier/getCooperator.htm">合作中</a> </div>
            <div <#if pageType==2>class="lable_present"<#else>class="lable_after"</#if>> <a href="${base}/supplier/getExpire.htm">终止合作</a> </div>
        </div><!--标签 end --> 
		<!--社区不支持
  		<div class="box_806 padding_10px">
			<div class="floatleft margin_top2">经验值： <input name="text" type="text" class="box_width_150px"></div>
            <div class="floatleft margin_left20 margin_top2">等级：  <input name="text" type="text" class="box_width_150px"></div>
           <div class="floatleft margin_left20"> <input name="button" type="button" class=" gray_button_002" value="搜索"></div>
        </div>
       -->
		<div class="box_806 lable_bg_style padding_lr_10px">

      	</div><!--标签 end --> 
     	<form id="pageForm" name="pageForm" method="post" action="/">
     		<#if pageType == 0>
     			<input type="hidden" name="index" id="index"/>
		        <div class="box_826">
		        <table width="826" border="0" class="bottom_1px_gray bg_gray01 fontbold">
		          <tr>
		            <td width="220"><input id="titleCheckbox" name="titleCheckbox" type="checkbox" value="">
		            &nbsp;分销商名称</td>
		            <!-- <td width="100">社区等级</td>
		            <td width="126">经验值</td>
		            <td width="80">威望</td> -->
		            <td width="100">申请时间</td>
		            <td width="200" align="center">操作</td>
		          </tr>
		        </table>
		          
		        <table width="826" border="0"> 
		         <#if pageList?exists && (pageList?size > 0)>
		         	<#assign i=0 />
		          	<#list pageList as item>
			          <tr class="bottom_1px_gray_dashed fontgray_03">
			            <td width="220">
			            	<input type="hidden" name="pageList[${i}].id" value="${item.id}" />
			            	<input type="hidden" id="check${i}" name="pageList[${i}].checked" />
			            	<input type="hidden" id="cooperateMonth${i}" name="pageList[${i}].cooperateMonth" />
			            	<input type="hidden" id="remark${i}" name="pageList[${i}].remark" />
			            	<input type="hidden" id="status${i}" name="pageList[${i}].status" />
			            	<input type="hidden" id="oldStatus${i}" name="pageList[${i}].oldStatus" value="0"/>
			            	<input id="${i}" name="itemCheckbox" value="" type="checkbox" onclick="itemCheck(this)">
			            	<#if item.distributeDistributorDO??>
			              		<a href="http://sns.pinju.com/dashboard/index?id=${item.distributeDistributorDO.memberId!''}"  target="_blank">${item.distributeDistributorDO.nickName!''}</a>
			              	</#if>
			              	</td>
				            <td width="100">${item.applyDate}</td>
				            <td width="200" align="center">
				            <#if item.distributeDistributorDO??>
			            		<input name="agreeButton" type="button" class="orange_button_002" value="同意" onclick="agree('${i}','${item.distributeDistributorDO.memberId!0}')">
			            	</#if>
			        		<input name="refuseButton" type="button" class="gray_button_002 margin_left20" value="拒绝" onclick="refuseOne('${i}')">
			            </td>
			          </tr>
			          <#assign i=i+1/>
			          </#list>
			      <#else>
			          <tr><td colspan="7" align='center'><label class="hong">您还没有收到分销商的申请！</label></td></tr>
		          </#if>
		        </table>
		      </div>
		      <div class="cbottom imgbtn">
		      	<#include "/default/templates/control/bottomPage.ftl">
		      </div>
		      <div class="box_806 padding_10px fontgray_01" align="center">
		       	  <input name="button" type="button" class="orange_button_004" value="批量同意" onclick="agree()">
		          <input name="button" type="button" class="gray_button_004 margin_left20" value="批量拒绝" onclick="refuseAll()">	
		      </div>
	        <#elseif pageType == 1>
	        	<div class="box_826">
		        <table width="826" border="0" class="bottom_1px_gray bg_gray01 fontbold">
		          <tr>
		            <td width="220">&nbsp;分销商名称</td>
		            <td width="90">起始时间</td>
		            <td width="90">终止时间</td>
		            <td width="66">合作状态</td>
		          </tr>
		        </table>
		          
		        <table width="826" border="0"> 
		         <#if pageList?exists && (pageList?size > 0)>
		          	<#list pageList as item>
			          <tr class="bottom_1px_gray_dashed fontgray_03">
			            <td width="220">
				            <#if item.distributeDistributorDO??>
				              	<a href="http://sns.pinju.com/dashboard/index?id=${item.distributeDistributorDO.memberId}" target="_blank">
				              		${item.distributeDistributorDO.nickName}</a>
				            </#if>
				            </td>
				            <td width="90">${item.startDate}</td>
				            <td width="90">${item.endDate}</td>
				            <td width="66">正常</td>
			            </td>
			          </tr>
			          </#list>
			      <#else>
			          <tr><td colspan="7" align='center'><label class="hong">您还没有合作中的分销商！</label></td></tr>
		          </#if>
		        </table>
		      </div>
		      <div class="cbottom imgbtn">
		      	<#include "/default/templates/control/bottomPage.ftl">
		      </div>
	        <#elseif pageType == 2>
		        <div class="box_826">
			        <table width="826" border="0" class="bottom_1px_gray bg_gray01 fontbold">
			          <tr>
			            <td width="220">&nbsp;分销商名称</td>
			            <td width="90">起始时间</td>
			            <td width="90">终止时间</td>
			            <td width="66">合作状态</td>
			          </tr>
			        </table>
			          
			        <table width="826" border="0"> 
			         <#if pageList?exists && (pageList?size > 0)>
			          	<#list pageList as item>
				          <tr class="bottom_1px_gray_dashed fontgray_03">
				            <td width="220">
				            	<#if item.distributeDistributorDO??>
				              		<a href="http://sns.pinju.com/dashboard/index?id=${item.distributeDistributorDO.memberId}" target="_blank">${item.distributeDistributorDO.nickName}</a>
				              	</#if>
				              	</td>
					            <td width="90">${item.startDate}</td>
					            <td width="90">${item.endDate}</td>
					            <td width="66">${item.displayStatus}</td>
				            </td>
				          </tr>
				          </#list>
				      <#else>
			          	<tr><td colspan="7" align='center'><label class="hong">您还没有终止合同的分销商！</label></td></tr>
			          </#if>
			        </table>
			      </div>
			      <div class="cbottom imgbtn">
			      	<#include "/default/templates/control/bottomPage.ftl">
			      </div>
	        </#if>
        </form>
        <div id="DialogMask" style="display:none">
            <iframe src="about:blank"></iframe>
        </div>
        <div id="Dialog">
            <div id="DialogShadow"></div>
            <div id="DialogLayout">
                <div id="DialogCaption">
                    <span></span>
                    <a href="#close"></a>
                </div>
                <iframe src="about:blank" id="DialogContent"></iframe>
            </div>
        </div>
  	</div><!--分销大框架结束 -->
</div><!--右侧结束 -->
<script type="text/javascript">
	var dialog = new Dialog();
	
	function agree(index, name){
		var url = "popup.htm";
		if(index){
			url += "?index=" + index + "&name=" + name;
		}else{
			var submitNum = 0;
			$("input[name='itemCheckbox']").each(function() { 
				if($(this).attr("checked")){
					submitNum++;
				}
			});
			if(0 == submitNum){
	    		alert('请选择您要同意的分销商');
	    		return false;
	    	}
		}
		dialog.open(url,600,445);
	}
	
	function itemCheck(o){
		if(o.checked){
			$("#check"+o.id).attr("value",1);
		}else{
			$("#check"+o.id).attr("value",0);
		}
	}
	
	function passAll(months,remark){
		$("input[name='itemCheckbox']").each(function() {
			if($(this).attr("checked")){
				$("#cooperateMonth"+this.id).attr("value",months);
				$("#remark"+this.id).attr("value",remark);
				$("#status"+this.id).attr("value",1);
			}
		});
		$("#pageForm").attr( {
			action : "pass.htm",
			method : "post"
		}).submit();
	}
	
	function passOne(index, months, remark){
		$("#cooperateMonth"+index).attr("value",months);
		$("#remark"+index).attr("value",remark);
		$("#status"+index).attr("value",1);
		$("#index").attr("value",index);
		$("#pageForm").attr( {
			action : "pass.htm",
			method : "post"
		}).submit();
	}
	
	function refuseAll(){
		var submitNum = 0;
		$("input[name='itemCheckbox']").each(function() {
			if($(this).attr("checked")){
				$("#status"+this.id).attr("value",2);
				submitNum++;
			}
		});
		if(0 == submitNum){
    		alert('请选择您要拒绝申请的分销商。');
    		return false;
    	}
    	if(confirm("确定要拒绝所选分销商的申请吗？")){
			$("#pageForm").attr( {
				action : "refuse.htm",
				method : "post"
			}).submit();
		}
	}
	
	function refuseOne(index){
		if(confirm("确定要拒绝所选分销商的申请吗？")){
			$("#status"+index).attr("value",2);
			$("#index").attr("value",index);
			$("#pageForm").attr( {
				action : "refuse.htm",
				method : "post"
			}).submit();
		}
	}
	
	$(function() {
		$('#distributorManager').addClass('count');
		
			$("#titleCheckbox").click(function() {
				if(this.checked){
					$("input[name='itemCheckbox']").each(function() {
						$(this).attr("checked", true);
						$("#check"+this.id).attr("value",1);
					});
				}else{
					$("input[name='itemCheckbox']").each(function() {
						$(this).attr("checked", false);
						$("#check"+this.id).attr("value",0);
					});
				}
			});
		});
</script>
