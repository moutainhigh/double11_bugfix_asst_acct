<#if query.skuListPV??>
		<#list query.skuListPV as skuListPV>
		<div class="sort cf" id="sort_list" name="sort_list">
            <span class="sort_tit">${skuListPV.cateProlevel.name!}：</span>
                     <div class="sort_list_box fl">
        				<ul class="sort_list J_skuList">
                    	<#list skuListPV.levelList as skuProValue>

                    		<#if skuListPV.cateProlevel.isSellCustom??&&(skuListPV.cateProlevel.isSellCustom==1||skuListPV.cateProlevel.isSellCustom==2)>
                    			 <li id="${skuListPV.cateProlevel.id!}:${skuProValue.id}" class="imgsitem-outline">
                    			 <#if skuProValue.valueType!=2>
                    			 	<a  href="javascript:;"  hidefocus="true">
		                            ${skuProValue.value?html}
		                            </a>
                    			 <#else>
	                    			 <a href="javascript:;" class="imgsitem" style="background:url(${imageServer}${skuProValue.imgUrl!}_40x40.jpg) center no-repeat;" hidefocus="true" title="${skuProValue.value?html!}">
										<span>${skuProValue.value?html!}</span>
									 </a>
								 </#if>
								 </li>
                    		<#else>
                    			<li id="${skuListPV.cateProlevel.id!}:${skuProValue.id}">
		                            <a  href="javascript:;"  hidefocus="true">
		                            ${skuProValue.value?html}
		                            </a>
		                         </li>
		                     </#if>
		                
                        </#list>
                        </ul>
                     </div>
	 	</div>
		</#list>
</#if>