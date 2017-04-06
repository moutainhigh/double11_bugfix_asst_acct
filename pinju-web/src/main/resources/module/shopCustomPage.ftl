<#if !_PREVIEW?exists || _PREVIEW!="true">
<div class="notic"><!--=模块提示-->
	<h2>
		<a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?userPageId=${_USERPAGEID?if_exists?string("0")}&moduleId=${_MODULEID?if_exists?string("0")}',680,600);}">
			<#if _MODULEID?? && (_MODULEID == 16 || _MODULEID == 17 || _MODULEID == 18)>
				自定义模块（190px -）
			<#elseif _MODULEID?? && (_MODULEID == 19 || _MODULEID == 20 || _MODULEID == 21)>
				自定义模块（750px -）
			<#else>
				自定义模块（950px -）
			</#if>							
		</a>
	</h2>			
</div>
</#if>
<div id="html_id">
${html?if_exists}
</div>