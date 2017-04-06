		<!--===模块编辑——页头搜索内容，开始-->
		<form name="itemPromoteForm" action="saveDesignModuleAction.htm" method="post">
		<div class="lay_content">
			<table class="table_text">
				<tr>
					<th>模块标题：</th>
					<td><input type="text" name="TITLE" class="txt" value="<#if TITLE?exists>${TITLE?html}</#if>"><label><input type="checkbox" name="DISPLAYTITLE" value="Y"<#if DISPLAYTITLE?? && DISPLAYTITLE = "Y"> checked</#if>>显示标题栏</label><span>（不输入标题则不显示标题栏）</span></td>
				</tr>
			</table>
			<table class="table_text">
				<caption>商品筛选</caption>
				
				<tr>
					<th>关键词筛选：</th>
					<td><input type="text" class="txt" name="TITLEKEY" value="${TITLEKEY!""}"></td>
				</tr>
				<tr>
					<th>所属分类：</th>
					<td>
					  <label>
					  <select name="SELECTCATEGORY">
					  		<option value=""<#if !(SELECTCATEGORY ??) || SELECTCATEGORY == ""> selected</#if>>所有分类</option>
				  			<#list shopCategoryList?keys as key>
                        	<#if (shopCategoryList[key].subCategoryList?size > 0)>
                        	<optgroup label="${shopCategoryList[key].categoryName?html}">
                        	<#list shopCategoryList[key].subCategoryMap?keys as val>
                        	<option value="${val}"<#if (SELECTCATEGORY ??) && SELECTCATEGORY == val> selected</#if>>└${shopCategoryList[key].subCategoryMap[val]?html}</option>
                        	</#list>
                        	</optgroup>
                        	<#else>
                        	<option value="${shopCategoryList[key].id}"<#if (SELECTCATEGORY ??) && SELECTCATEGORY == shopCategoryList[key].id> selected</#if>>${shopCategoryList[key].categoryName}</option>
                        	</#if>
                        	</#list>
					  </select>
				      </label>
					</td>
				</tr>
				
				<tr>
					<th>价格范围：</th>
					<td class="short">
						￥<input type="text" class="txt" name="MINPRICE" value="${MINPRICE!""}">元—
						￥<input type="text" class="txt" name="MAXPRICE" value="${MAXPRICE!""}">元
					</td>
				</tr>
			</table>
			<table class="table_text">
				<caption>显示方式</caption>
				<!--
				<tr>
					<th>图片尺寸：</th>
					<td>
					  <label>
					  <select name="DIMENSION">
					  		<option value="0"<#if DIMENSION?? && DIMENSION == "s"> selected</#if>>120×120</option>
					  		<option value="1"<#if !(DIMENSION??) || DIMENSION == "m"> selected</#if>>160×160</option>
					  		<option value="2"<#if DIMENSION?? && DIMENSION == "b"> selected</#if>>220×220</option>
					  </select>
				      </label>
					</td>
				</tr>
				-->
				<tr>
					<th>商品数量：</th>
					<td>
					  <label>
					  <select name="ITEMNUM">
					  		<!--<option value="-1"<#if ITEMNUM?? && ITEMNUM == "-1"> selected</#if>>自定义</option>-->
					  		<option value="3"<#if ITEMNUM?? && ITEMNUM == "3"> selected</#if>>3</option>
					  		<option value="4"<#if ITEMNUM?? && ITEMNUM == "4"> selected</#if>>4</option>
					  		<option value="6"<#if ITEMNUM?? && ITEMNUM == "6"> selected</#if>>6</option>
					  		<option value="8"<#if ITEMNUM?? && ITEMNUM == "8"> selected</#if>>8</option>
					  		<option value="10"<#if ITEMNUM?? && ITEMNUM == "10"> selected</#if>>10</option>
					  		<option value="12"<#if ITEMNUM?? && ITEMNUM == "12"> selected</#if>>12</option>
					  		<option value="16"<#if !(ITEMNUM??) || ITEMNUM == "16"> selected</#if>>16</option>
					  </select>
				      </label>
					  <!--<input type="text" class="txt" name="OTHERNUM" value="${OTHERNUM!"输入商品数量"}" >-->
					</td>
				</tr>
				<tr>
					<th>排序方式：</th>
					<td>
					  <label>
					  <select name="SORTTYPE">
					  <!--
					  		<option>热门收藏</option>
					  		<option>人气指数</option>
					  		<option>热销商品在前</option>
					  		<option>热门收藏在前</option>
					  -->
					  		<option value="GMT_CREATE DESC"<#if !(SORTTYPE??) || SORTTYPE == "GMT_CREATE DESC"> selected</#if>>最新上架在前</option>
					  		<option value="PRICE ASC"<#if SORTTYPE?? && SORTTYPE == "PRICE ASC"> selected</#if>>低价格在前</option>
					  		<option value="PRICE DESC"<#if SORTTYPE?? && SORTTYPE == "PRICE DESC"> selected</#if>>低价格在后</option>
					  </select>
				      </label>
					</td>
				</tr>
			</table>						
			<ul class="btn-weiz">
				<input type="hidden" name="id" value="${_ID!""}" />
				<input type="hidden" name="moduleId" value="${_MODULEID!""}" />
				<input type="hidden" name="userPageId" value="${_USERPAGEID}" />
				<input class="save" type="submit" value="保存"><input class="cancel" type="button" value="取消" onclick="window.parent.dialog.close();" />
			</ul>
		</div>
		</form>
		<!--===模块编辑——页头搜索内容，结束-->
