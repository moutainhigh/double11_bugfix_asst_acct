 <form action="" id="detailForm" name="detailForm" method="GET">
	                    <span class="sort_tit">数量：</span>
	                    <div class="sort_list_box fl">
	                        <ul class="sort_list">
	                            <li><input type="text" id="SelectedNum" name="SelectedNum" value="1" class="text_count" /> <span class="tips">
	                            (库存<strong id="SpecCapacity">${itemDO.curStock!}</strong>件
	                            <#if query.actLimitCount??&&query.actLimitCount!=0>
			                        ,每人限购<strong id="actLimitCount">${query.actLimitCount}</strong>件
			                    <#else>
			                      	 <#if query.actLimitCount??>
			                      	 	<strong id="actLimitCount" style="display:none">${query.actLimitCount}</strong>
			                      	 </#if>
			                    </#if>)
	                            </span></li>
	                        </ul>
	                    </div>
	                    <input type="hidden"  value="${itemDO.id?c}" id="itemId" name="itemId">
	                    <input type="hidden"  value="${query.encryptItemId}" id="encryptItemId" name="encryptItemId">
	                    <input type="hidden" value="" id="SelectedSKU"  name="SelectedSKU" class="required" />
	                    <input type="hidden" value="${query.getPriceCentToDollar(itemDO.price)!}" id="SelectedPrice"  name="SelectedPrice" class="required SelectedPrice" />
	                    <input type="hidden" value="" id="SelectedSkuId"  name="SelectedSkuId" />
	                    <input type="hidden"  id="bussinessType"  name="bussinessType" value="${bussinessType?default(200)}"/>
	                    <#if query.isLimitBuyItem??&&query.isLimitBuyItem==true>
	                    <input type="hidden"  id="limitBuyCode"  name="limitBuyCode" value=""/>
	                    </#if>
	                    <input type="hidden"  id="ad_key"  name="p" value=""/>
	                    <#if channelId??>
	                    	<input type="hidden" value="${channelId}" id="channelId"  name="channelId" />
	                    </#if>
</form>