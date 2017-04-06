		<!--===模块编辑——页头搜索内容，开始-->
		<form name="bestSellerForm" action="saveDesignModuleAction.htm" method="post">
		<div class="lay_content">
			<table class="table_text">
				<tr>
					<th>模块标题：</th>
					<td><input type="text" name="TITLE" value="<#if TITLE?exists>${TITLE?html}</#if>" class="txt"><label><input type="checkbox" name="DISPLAYTITLE" value="Y"<#if DISPLAYTITLE?? && DISPLAYTITLE = "Y"> checked</#if>>显示标题栏</label><span>（不输入标题则不显示标题栏）</span></td>
				</tr>
				<!--
				<tr>
					<th>选择分类：</th>
					<td>
					  <label>
					  <select name="select">
					  		<option>所有分类</option>
					  		<option>自定义大分类</option>
					  		<option>∟自定义小分类</option>
					  		<option>∟自定义小分类</option>
					  		<option>∟自定义小分类</option>
					  </select>
				      </label>
					</td>
				</tr>
				-->
				<tr>
					<th>显示数量：</th>
					<td>
					  <label>
					  <select name="DISPLAYNUM">
					  		<option value="3"<#if !(DISPLAYNUM??) || DISPLAYNUM == "3"> selected</#if>>3个商品</option>
					  		<option value="4"<#if DISPLAYNUM?? && DISPLAYNUM == "4"> selected</#if>>4个商品</option>
					  		<option value="5"<#if DISPLAYNUM?? && DISPLAYNUM == "5"> selected</#if>>5个商品</option>
					  		<option value="6"<#if DISPLAYNUM?? && DISPLAYNUM == "6"> selected</#if>>6个商品</option>
					  		<option value="8"<#if DISPLAYNUM?? && DISPLAYNUM == "8"> selected</#if>>8个商品</option>
					  		<option value="10"<#if DISPLAYNUM?? && DISPLAYNUM == "10"> selected</#if>>10个商品</option>
					  </select>
				      </label>
					</td>
				</tr>
				<!--
				<tr>
					<th>价格范围：</th>
					<td class="short">
						￥<input type="text" class="txt">元—
						￥<input type="text" class="txt">元
					</td>
				</tr>
				-->
				<tr>
					<th>默认显示：</th>
					<td>
					  <label>
					  <select name="DEFAULTDISPLAY">
					  		<option value="0"<#if !(DEFAULTDISPLAY ??) || DEFAULTDISPLAY == "0"> selected</#if>>本月热销榜</option>
					  		<!--option value="1"<#if DEFAULTDISPLAY?? && DEFAULTDISPLAY == "1"> selected</#if>>热门收藏榜</option-->
					  </select>
				      </label>
					  <label><input name="DISPLAYCOUNT" type="checkbox" value="Y"<#if DISPLAYCOUNT?? && DISPLAYCOUNT == "Y"> checked</#if>>
					  显示最近30天数据<span></label>
					</td>
				</tr>
			</table>
						
			<ul class="btn-weiz">
				<input type="hidden" name="id" value="${_ID!""}" />
				<input type="hidden" name="moduleId" value="9" />
				<input type="hidden" name="userPageId" value="${_USERPAGEID}" />
				<input class="save" type="submit" value="保存" /><input class="cancel" type="button" value="取消" onclick="window.parent.dialog.close();" />
			</ul>
		</div>
		</form>
