<#if query.spuProList??>
			   	<#assign spuProList = query.spuProList>
				   <#list spuProList as spuMap>
				   <li>	${spuMap.cateProName}：&nbsp;${spuMap.cateProValue?html}</li>
					</#list>
			   </#if>
			   <#if query.cateProList??>
			   	<#assign cateProList = query.cateProList>
				   <#list cateProList as cateMap>
				   <li>	${cateMap.cateProName}：&nbsp;${cateMap.cateProValue?html}</li>
					</#list>
			 </#if>