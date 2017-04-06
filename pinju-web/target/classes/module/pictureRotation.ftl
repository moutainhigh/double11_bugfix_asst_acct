<#setting classic_compatible=true>
		<#if !_PREVIEW?exists || _PREVIEW!="true">
        <div class="notic"><!--=模块提示-->
					<h2><a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?userPageId=${pageId}&moduleId=12', 600, 650);}">图片轮播（750px -）</a></h2>			
				</div>
		</#if>
		
		
		
		
		<#if isShowTitle?? && isShowTitle == "1" && moduleTitle??>
					<h3 class="title"><#if moduleTitle?exists>${moduleTitle?html}</#if></h3>
					</#if>
        <#if files??>
        <#if effect?? && effect == 0>
        	<#if moduleheight == 6>
        		<div class="slide-show" module-type="slide-show" module-effect="slide" module-auto-delay="3000" style="height:${moduleheightCustomer}px;width:750px;">
        		<#assign h=moduleheightCustomer>
        	<#else>
        		<#list pictureRotationHeightList as height>
	        		<#if height_index == moduleheight>
	        			<div class="slide-show" module-type="slide-show" module-effect="slide" module-auto-delay="3000" style="height:${height};width:750px;">
	        			<#assign h=height>
	        		</#if>
	        	</#list>
        	</#if>
        <#else>
        	<#if moduleheight == 6>
        		<div class="slide-show" module-type="slide-show" module-effect="fade" module-auto-delay="3000" style="height:${moduleheightCustomer}px;width:750px;">
        		<#assign h=moduleheightCustomer>
        	<#else>
	        	<#list pictureRotationHeightList as height>
	        		<#if height_index == moduleheight>
	        			<div class="slide-show" module-type="slide-show" module-effect="fade" module-auto-delay="3000" style="height:${height};width:750px;">
	        			<#assign h=height>
	        		</#if>
	        	</#list>
	        </#if>
        </#if>
        
        
            <ul class="sw-tabs">
            	<#if files??>
            		<#list files as file>
                		<li class="sw-tab">${file_index+1}</li>
                	</#list>
                </#if>
            </ul>
            <ul class="sw-panels">
                	<#if files??>
	                	<#list files as file>
	                		<li class="sw-panel">
	                			<a href="${urlString[file_index]}" target="_blank"><img width="750" height="${h}" src="${fileServer}${file}" alt="" /></a>
	                		</li>
	                	</#list>
                	</#if>
            </ul>
        </div>
        </#if>

