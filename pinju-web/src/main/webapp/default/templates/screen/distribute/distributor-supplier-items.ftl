<title>供应商商品</title>
	<!--分销页面样式-->
	<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
	<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />
<div class="management_page02"><!--外部大框架-->
	<div class="box_width_700px floatleft lable_bg_style ">
		<div id="release" class="lable_after" style="cursor: pointer"> 招募书 </div>
        <div id="items" class="lable_present" style="cursor: default"> 产品目录 </div>
    </div>
    
  <div class="box_width_700px floatleft lable_bg_style margin_bottom">
  <div class="floatright margin_top10">共<span class="fontred">${query.items!0}</span>个查询结果</div></div>

  <div class="box_width_700px floatleft margin_bottom">
  	<form id="pageForm" name="pageForm" action="/" method="post">
		<input id="supplierId" type="hidden" name="supplierItemParam.supplierId" value="${supplierItemParam.supplierId}"/>
		<table width="700" border="0">
          <tr class="bottom_1px_gray bg_gray01 fontbold" align="center">
            <td width="350">商品名</td>
            <td width="150">分成比例</td>
            <td width="150">库存状态</td>
            <td width="150">更新时间</td>
          </tr>
<#if (distrbuteSupplierItemDOList)?? && (distrbuteSupplierItemDOList?size >0)>
          <#list distrbuteSupplierItemDOList as distrbuteSupplierItemDO>
          <tr class=" bottom_1px_gray_dashed">
            <td><div class="floatleft">
                <div class="floatleft">
	                <a href="/detail/${(distrbuteSupplierItemDO.itemDO.id)!0}.htm" target="_blank">
		                <#if distrbuteSupplierItemDO.itemDO??>
		                	<img src="${imageServer!''}${distrbuteSupplierItemDO.itemDO.picUrl!''}" width="50" height="50">
		                <#else>
		                	<img src="">
		                </#if>
		            </a>
                </div>
                <div class="floatleft box_width_200px padding_lr_10px">
                  <p>
	                  <a href="/detail/${(distrbuteSupplierItemDO.itemDO.id)!0}.htm" target="_blank">
		                  <#if distrbuteSupplierItemDO.itemDO??>
		                  	${(distrbuteSupplierItemDO.itemDO.title!'')?html}
		                  </#if>
		              </a>
                  </p>
                  <!--<p>&nbsp;</p>
                  <p class=" fontgray_01">分销品牌：2</p>-->
                </div>
                
                </div></td>
            <td align="center">${distrbuteSupplierItemDO.reward!0}%</td>
            <td align="center">
	            <#if distrbuteSupplierItemDO.itemDO??>
	            	${distrbuteSupplierItemDO.itemDO.curStock!''}
	            </#if>
            </td>
            <td align="center">
	            <#if distrbuteSupplierItemDO.itemDO??>
	            	<#if distrbuteSupplierItemDO.itemDO.lastModified??>
	            		${distrbuteSupplierItemDO.itemDO.lastModified?date}
		            </#if>
	            </#if>
            </td>
          </tr> 
          </#list>   
<#else>
<tr><td colspan="7" align='center'><label class="hong">查不到任何商品信息！</label></td></tr>
</#if>   
        </table>
<#if (distrbuteSupplierItemDOList)?? && (distrbuteSupplierItemDOList?size >0)>
<div class="cbottom imgbtn">
	<#include "/default/templates/control/bottomPage.ftl">
</div>
</#if>
	</form>
  </div>
  
  <div class="box_width_700px floatleft margin_bottom">
    <div class="floatright">
    	
    </div>
  </div>
    
</div><!--外部大框架end-->
<script type="text/javascript">
	$(function(){
	
		$('#applyDistributor').addClass('count');
		
		$('#release').click(
			function(){window.location.href = "/distributor/supplierRelease.htm?supplierParam.id=${(supplierItemParam.supplierId!'')?js_string}";}
		);
		//$('#items').click(
			//function(){window.location.href = "/distributor/supplierItems.htm?supplierItemParam.supplierId=${(supplierItemParam.supplierId!'')?js_string}";}
		//);
	});
</script>