				<!--========================================================●搜索店内商品开始-->
				<#if !_PREVIEW?exists || _PREVIEW!="true">
				<div class="notic"><!--=模块提示-->
					<h2><a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?userPageId=${_USERPAGEID?if_exists?string("0")}&moduleId=6',600,600);}">搜索店内商品</a></h2>
				</div>
				</#if>
				<!--===================================-->
				<form name="_leftSearchForm" action="http://www.pinju.com/detail/shopItemList.htm" method="post" target="_blank">
				<input type="hidden" name="shopId" value="${_SHOPID?string('0')}" />
				<div class="box">
					<h3 class="title"><#if title?exists>${title?html}</#if></h3>
					<table  id="LeftSearch">
						<tr>
							<th>关键字：</th>
							<td><input name="searchKey" type="text" class="input_m" /></td>
						</tr>
						<#if hasPriceArea?exists && hasPriceArea=="on">
						<tr>
							<th>价格：</th>
							<td><input name="startPrice" type="text" class="input_s">到<input name="endPrice" type="text" class="input_s"></td>
						</tr>
						</#if>
						<tr>
							<th></th>
							<td><input type="button" onclick="onSubmit();" value="搜索" class="button" /></td>
						</tr>
						<tr>
							<th>热门：</th>
							<td><#if searchWords?exists><#list searchWords?split(",") as word><a href="javascript:setSearchKey('${word}');onSubmit();">${word}</a></#list></#if></td>
						</tr>
					</table>
				</div>
				</form>		
			    <script language="javascript" type="text/javascript">
			    <!--
			  		function onSubmit() {
			  			if(document._leftSearchForm.searchKey.value == '') {
			  				alert('请输入搜索关键字');
			  				return false;
			  			}
			  			document._leftSearchForm.submit();
			  		}
			  		
			  		function setSearchKey(key) {
			  			document._leftSearchForm.searchKey.value = key;
			  		}
			    //-->
			    </script>		
				<!--========================================================●搜索店内商品结束-->