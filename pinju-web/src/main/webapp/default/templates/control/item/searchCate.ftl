<#if cateResult??>
	<#if cateResult.currentFrom==1>
		<!-- 来源于首页 -->
		<#if cateResult.cateMap??>
		<#assign cateMap = cateResult.cateMap>
					<ul class="menu-pcat">
						<#list cateMap.get(cateResult.oneLevelCateId) as oneLevel>
							<li class="haschildren <#if cateResult.twoLevelCateId??&&oneLevel.id==cateResult.twoLevelCateId> active</#if>">
									<a href="${searchServer!}/search/search.htm?cp=${oneLevel.id}" <#if oneLevel.isLeaf!=1>class="title"</#if> title="${oneLevel.name?html}" >
									<#if (oneLevel.name?length > 10)>
										${oneLevel.name?substring(0,10)?html}...
									<#else>
										${oneLevel.name?html}
									</#if>
										</a>
									<#if oneLevel.id??&&oneLevel.isLeaf!=1&&cateMap.get(oneLevel.id)??>
										<ul>
										<#list cateMap.get(oneLevel.id) as twoLevel>
													<li class="haschildren <#if cateResult.threeLevelCateId??&&twoLevel.id==cateResult.threeLevelCateId> active</#if>">
														<a <#if twoLevel.isLeaf!=1>class="title"</#if> href="${searchServer!}/search/search.htm?cp=${twoLevel.id}" title="${twoLevel.name?html}">
															<#if (twoLevel.name?length > 10)>
																${twoLevel.name?substring(0,10)?html}...
															<#else>
																${twoLevel.name?html}
															</#if>
														</a>
												<#if twoLevel.id??&&twoLevel.isLeaf!=1&&cateMap.get(twoLevel.id)??>
													<ul>
													<#list cateMap.get(twoLevel.id) as threeLevel>
													<li>
														<a href="${searchServer!}/search/search.htm?cp=${threeLevel.id}"
															 title="${threeLevel.name?html}" <#if cateResult.fourLevleCateId??&&threeLevel.id==cateResult.fourLevleCateId> class="current"</#if>>
															${threeLevel.name?html}
														</a>
													</li>
													</#list>
													</ul>
												</#if>
												</li>
										</#list>
										</ul>
									</#if>
							</li>
						</#list>
					</ul>
		</#if>
	<#else>
		<!-- 来源于搜索结果 -->
	</#if>
</#if>