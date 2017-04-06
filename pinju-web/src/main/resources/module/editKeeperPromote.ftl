<#setting number_format="#">
<#setting classic_compatible=true>
<div class="lay_tabcontent">
			<ul class="tab">
				<a href="javascript:void(0)" class="count" id="promoteItem">推荐商品</a>
				<a href="javascript:void(0)" id="showSetting">显示设置</a>
			</ul>
			<form action="/shopDecoration/saveDesignModuleAction.htm" method="post" id="savePromoteForm">
			<!--========================================================-->
			
			<div id="promoteItemDiv">
			<div class="tab_content" ><!--列表容器-->
				<h2>推荐商品列表</h2>
				<div class="lister"><!--列表-->
				<div class="list1">
					<!--==========-->
					<#if ITEMLIST??>
						<#list ITEMLIST as item>
								<#if isPromoted?? && isPromoted[item_index] == "1">
									<ul class="goods" id="${item.id}">
										<a href="##"><img src="${picServer}${item.picUrl}" class="img"/></a>
										<cite class="ci-gray"><a class="promote promoted" href="javascript:void(0)"><img class="verygood" style="display:none" src="http://static.pinju.com/images/sc-hander-11.gif"><span>已推荐</span></a></cite>
										<li>
											<a href="#">${item.title?html}</a>
											<cite></cite>
											<strong>￥${item.priceByYuan}</strong>
										</li>
									</ul>
								<#else>
									<ul class="goods" id="${item.id}">
										<a href="##"><img src="${picServer}${item.picUrl}" class="img"/></a>
										<cite><a class="promote" href="javascript:void(0)"><img class="verygood" src="http://static.pinju.com/images/sc-hander-11.gif">
											<span>推荐</span></a></cite>
										<li>
											<a href="#">${item.title?html}</a>
											<cite></cite>
											<strong>￥${item.priceByYuan}</strong>
										</li>
									</ul>
								</#if>
							</#list>
					</#if>
					</div>
					<!--==========-->
					<dl>
						<input type="hidden" value="${nextPage}" name="nextPage" id="nextPage"/>
						<input type="hidden" value="${prevPage}" name="prevPage" id="prevPage"/>
						
						<input type="hidden" value="${picServer}" name="picServer" id="picServer"/>
						
						
						<#if totalPage !=null && totalPage != "">
							<dt>
							
								<#if totalPage == 1>
									<input name="nextButton" id="nextButton" disabled  type="button" class="button_previous_none">
								<#else>
									<input name="nextButton" id="nextButton"   type="button" class="button_previous">
								</#if>
							
							</dt>
						
							<dt><input name="prevButton" id="prevButton" disabled type="button" class="button_next_none"></dt>
						</#if>
						
						
						
						<dt>
						<#if currentPage!=null &&  currentPage != "">
						<span id="currentPageShow">${currentPage}/${totalPage}</span>页
						</#if>
						</dt>
					</dl>
				</div>
			</div>
			<!--========================================================-->
			<div class="tab_aro"></div>
			<!--========================================================-->
			<div class="tab_content"><!--列表容器-->
				<h2>已推荐<strong><span class="itemNumId" id="itemNumId">${ITEMNUM}</span></strong>个商品</h2>
				<div class="lister"><!--列表-->
					<div class="list2">
					<!--==========-->
					<#if ITEMLISTPROMOTE??>
					<#list ITEMLISTPROMOTE as item>
						<ul class="goods"  id="${item.id}">
							<a href="##"><img src="${picServer}${item.picUrl}" class="img"/></a>
							<cite><a class="promote" href="##"><img class="verygood" style="display:none" src="http://static.pinju.com/images/sc-hander-11.gif"><span>取消推荐</span></a></cite>
							<li>
								<a href="#">${item.title?html}</a>
								<cite></cite>
								<strong>￥${item.priceByYuan}</strong>
							</li>
						</ul>
					
					</#list>
					</#if>
					</div>
				</div>
			</div>
						
				<!--<input id="savePromote" class="save" type="button" value="保存"/>-->
				
				
			</div>
			
			<!-- 显示设置-->
			
			<div id="showSettingDiv" style="display:none">
				<table class="table_text">
				<tr>
					<th>模块标题：</th>
					<td>
						<input type="text" id="promoteTitle" name="promoteTitle" class="promoteTitle"  value="<#if promoteTitle?exists>${promoteTitle?html}</#if>"/>
						<label>
							<#if isShowTitle?? &&  isShowTitle != 0>
								<input type="checkbox" checked id="isShowTitle" name="isShowTitle" value="${isShowTitle}"/>
							<#else>
								<input type="checkbox"  id="isShowTitle" name="isShowTitle" value="${isShowTitle}"/>
							</#if>
							
							显示标题栏
						</label>
						<span>（不输入标题则不显示标题栏）</span>
					</td>
				</tr>
				<tr>
					<th>图片尺寸：</th>
					<td>
					  <label>
					  <select id="picSize" name="picSize">
					  		<#if picSizeList??>
					  			<#list picSizeList as picSizeL>
					  				<#if picSize == picSizeL>
					  					<option value="${picSizeL}" selected>${picSizeL}</option>
					  				<#else>
					  					<option value="${picSizeL}">${picSizeL}</option>
					  				</#if>
					  			</#list>
					  		</#if>
					  </select>
				      </label>
					</td>
				</tr>
				<tr>
					<th>商品数量：</th>
					<td>
					  <label>
					  <!--
					  <select id="itemShowCount" name="itemShowCount" onchange="addCustomer()">
					  	<#if itemShowCount == "-1">
					  		<option selected value="-1">自定义</option>
					  		<#list itemShowCountList as itemShowCountL>
					  			<option value="${itemShowCountL}">${itemShowCountL}</option>
					  		</#list>
					  	<#else>
					  		<#if itemShowCountList??>
					  			<#list itemShowCountList as itemShowCountL>
					  				<#if itemShowCount == itemShowCountL>
					  					<option value="${itemShowCountL}" selected>${itemShowCountL}</option>
					  					<input type="hidden" id="itemCountHidden" value="${itemShowCountL}" />
					  				<#else>
					  					<option value="${itemShowCountL}">${itemShowCountL}</option>
					  				</#if>
					  			</#list>
					  		</#if>
					  		<option value="-1">自定义</option>
					  </#if>
					  </select>
					  <input type="hidden" id="itemCountHidden" value="${itemShowCountL}" />
				      </label>
				      <#if itemShowCount == "-1">
				      	<input type="text" class="txt" id="itemShowCountCustomer" name="itemShowCountCustomer" value="${itemShowCountCustomer}" />
				      	<input type="hidden" id="itemCountHidden" value="${itemShowCountCustomer}" />
				      <#else>
				      	<input style="display:none" type="text" class="txt" id="itemShowCountCustomer" name="itemShowCountCustomer" value="0" />
				       </#if>
				       -->
				       
				        <select id="itemShowCount" name="itemShowCount" onchange="addCustomer()">
				      		 <#if itemShowCountList??>
					  			<#list itemShowCountList as itemShowCountL>
					  				<#if itemShowCount == itemShowCountL>
					  					<option value="${itemShowCountL}" selected>${itemShowCountL}</option>
					  				<#else>
					  					<option value="${itemShowCountL}">${itemShowCountL}</option>
					  				</#if>
					  			</#list>
					  		</#if>
					   </select>
					   <input type="hidden" id="itemCountHidden" value="${itemShowCount}" />
					   </label>
					</td>
				</tr>
				<tr>
					<th>排序方式：</th>
					<td>
					  <label>
					  <select name="SORTTYPE" id="SORTTYPE">
					  		<#if sorttypeList??>
					  			<#list sorttypeList as sorttypeL>
					  				<#if sorttypeL == SORTTYPE>
					  					<option value="${sorttypeL}" selected>${sorttypeL}</option>
					  				<#else>
					  					<option value="${sorttypeL}">${sorttypeL}</option>
					  				</#if>
					  			</#list>
					  		</#if>
					  </select>
				      </label>
					</td>
				</tr>
			</table>
				<!--<input class="save" id="saveShowSetting" type="button" value="保存">-->
				
				
				
			</div>
		</div>
				<input type="hidden" value="" name="ITEMIDS" id="promoteHidden"/>
				<input type="hidden"  name="moduleId" id="moduleId" value="${moduleId}"/>
				<input type="hidden"  name="userPageId" id="pageId" value="${pageId}"/>
				<input type="hidden"  name="id" id="_ID" value="${_ID}"/>
				<input type="hidden"  name="ITEMNUM" id="ITEMNUM" value="${ITEMNUM}"/>
				<input class="save" id="savePromote" type="button" value="保存"/>
				<input class="cancel" type="button" value="取消" onclick="window.parent.dialog.close();" />
		</form>
		
		
		