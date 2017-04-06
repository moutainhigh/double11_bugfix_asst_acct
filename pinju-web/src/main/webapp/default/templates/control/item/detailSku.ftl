<#if query.skuListPV??>
		<#list query.skuListPV as skuListPV>
		<div class="sort cf">
            <span class="sort_tit fl">${skuListPV.cateProlevel.name!}：</span>
                     <div class="sort_list_box fl">
        				<ul class="sort_list J_skuList">
                    	<#list skuListPV.levelList as skuProValue>
                        <li id="${skuListPV.cateProlevel.id!}:${skuProValue.id}" class="pj-border2 ">
                            <a  href="javascript:;">${skuProValue.value}</a>
                        </li>
                        </#list>
                        </ul>
                     </div>
	 	</div>
		</#list>
</#if>