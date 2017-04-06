<title>申请商品分销</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<!--right -->
	<div class="right_850px floatright"><!--右侧开始 -->
  	<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${nickName!''}，欢迎进入云营销</div>
        
      <div class="box_806 padding_10px border_1px_gray bg_white margin_bottom"><span class="fontgray_03">我是买家 > 云营销 ></span> <span class="fontred">申请商品分销</span></div>
        
        <div class="box_826">
        <form id="pageForm" name="pageForm" action="/" method="post">
        <table width="826" border="0" >
          <tr class="border_1px_gray bg_gray01 fontbold">
            <td width="40%">供应商名称</td>
            <td width="20%">利润区间</td>
            <td colspan="2" width="20%">
            <select id="province" name="province" class="box_width_100px">
      			<option value="">所有地区</option>
					<option value="安徽">安徽</option>
					<option value="北京">北京</option>
					<option value="重庆">重庆</option>
					<option value="福建">福建</option>
					<option value="甘肃">甘肃</option>
					<option value="广东">广东</option>
					<option value="广西">广西</option>
					<option value="贵州">贵州</option>
					<option value="海南">海南</option>
					<option value="河北">河北</option>
					<option value="黑龙江">黑龙江</option>
					<option value="河南">河南</option>
					<option value="湖北">湖北</option>
					<option value="湖南">湖南</option>
					<option value="江苏">江苏</option>
					<option value="江西">江西</option>
					<option value="吉林">吉林</option>
					<option value="辽宁">辽宁</option>
					<option value="内蒙古">内蒙古</option>
					<option value="宁夏">宁夏</option>
					<option value="青海">青海</option>
					<option value="山东">山东</option>
					<option value="上海">上海</option>
					<option value="山西">山西</option>
					<option value="陕西">陕西</option>
					<option value="四川">四川</option>
					<option value="天津">天津</option>
					<option value="新疆">新疆</option>
					<option value="西藏">西藏</option>
					<option value="云南">云南</option>
					<option value="浙江">浙江</option>
					<option value="香港">香港</option>
					<option value="澳门">澳门</option>
					<option value="台湾">台湾</option>
					<option value="海外">海外</option>
     		</select>
     		</td>
     		<td width="10%"></td>
            <!--<td width="100"><select id="exchangeType" name="exchangeType" class="box_width_100px">
      			  <option value="">提供消保</option> 
                  <option value="0">基础消保服务1型</option> 
                  <option value="1">基础消保服务2型</option> 
                  </select></td>-->
            <td width="10%" align="center">操作</td>
          </tr>
          
          <tr>
            <td colspan="6"></td>
          </tr>          
<#if (distributeSupplierDOList)?? && (distributeSupplierDOList?size >0)>
		<#list distributeSupplierDOList as distributeSupplierDO>
          <tr class="bottom_1px_gray_dashed">
            <td><div class="floatleft">
                <div class="floatleft">
                  <#if (distributeSupplierDO.shopInfoAllDO?? && distributeSupplierDO.shopInfoAllDO.shopLogo??)>
                  	<img src="${imageServer!''}${(distributeSupplierDO.shopInfoAllDO.shopLogo)!''}"/>
                  <#else>
                  	<img src="http://static.pinju.com/img/shop_default_logo.png"/>
                  </#if>
                </div>
                <div class="floatleft box_width_200px padding_lr_10px">
                  <p>
                  <#if distributeSupplierDO.shopInfoAllDO??>
                  	<a href="/shopDecoration/viewShop.htm?shopId=${distributeSupplierDO.shopId!''}" target="_blank">${distributeSupplierDO.shopInfoAllDO.name!''}</a>
                  </#if>
                  </p>
                  <!--<p>&nbsp;</p>
                  <p class=" fontgray_01">分销品牌：
                  <#if distributeSupplierDO.shopBusinessInfoDO??>
                  	${distributeSupplierDO.shopBusinessInfoDO.brandName}
                  </#if></p>-->
                </div>
                </div></td>
            <td><p>返点比例：<span class="fontorange_01">${distributeSupplierDO.minReward!''}%~${distributeSupplierDO.maxReward!''}%</span></p>
              <p>&nbsp;</p>
            <p>分销商品：${distributeSupplierDO.count!0}</p></td>
            <td width="76">
            <#if distributeSupplierDO.shopInfoAllDO??>
            	${distributeSupplierDO.shopInfoAllDO.province!''}
            </#if>
            </td>
            <td width="60"><a href="/distributor/supplierItems.htm?supplierItemParam.supplierId=${distributeSupplierDO.id}">商品列表</a></td>
            <td align="center"></td>
            <!--<td align="center"><p>
            <#if distributeSupplierDO.shopInfoAllDO?? && distributeSupplierDO.shopInfoAllDO.exchangeMargin??>
            	<#if distributeSupplierDO.shopInfoAllDO.exchangeMargin=='0'>
            		基础消保服务1型
            	<#elseif distributeSupplierDO.shopInfoAllDO.exchangeMargin=='1'>
            		基础消保服务2型
            	</#if>
            </#if>
            </p></td>-->
            <td align="center"><p><a href="/distributor/supplierRelease.htm?supplierParam.id=${distributeSupplierDO.id}">招募书</a></p>
              <p>&nbsp;</p>
            <p><input id="apply_${distributeSupplierDO.id}" supplierId="${distributeSupplierDO.id}" name="apply" type="button" class="orange_button_004" value="我要分销"></p></td>
          </tr>  
         </#list>  
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何供应商信息！</label></td></tr>
</#if>
        </table>
<#if (distributeSupplierDOList)?? && ( distributeSupplierDOList?size >0)>
	<div class="cbottom imgbtn">
         <#include "/default/templates/control/bottomPage.ftl">
    </div>
</#if>
         </form>         
      </div>
        
  	</div><!--分销大框架结束 -->
</div><!--右侧结束 -->
<script type="text/javascript">
	$(function(){
		$('#applyDistributor').addClass('count');
	
		$('select[name="province"]').val('${(province!"")?js_string}');
		$('select[name="exchangeType"]').val('${(exchangeType!"")?js_string}');
		$('input[name="apply"]').click(
			function(){
				$.ajax({
					data:"queryparam.supplierId="+$(this).attr('supplierId'),
					url:"/distributor/apply.htm",
					type:"POST",
					success:function(callback){
						if(callback.status){
							alert(callback.message);
						}else{
							alert(callback.message);
						}
					},
					error:function(){
					}
				});
			}
		);
		$('select[name="province"]').change(submitForm);
		$('select[name="exchangeType"]').change(submitForm);
	});
	var submitForm = function submitForm(){
		$('#pageForm').attr("action","/distributor/applyDistributor.htm").submit();
	}
</script>