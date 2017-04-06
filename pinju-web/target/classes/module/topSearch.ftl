			<!--========================================================●页头搜索开始-->
			<#if !_PREVIEW?exists || _PREVIEW!="true">
			<div class="notic"><!--模块提示-->
				<h2><a href="javascript:if(dialog){dialog.open('getEditModuleAction.htm?userPageId=${_USERPAGEID?if_exists?string("0")}&moduleId=5',600,600);}">页头搜索</a></h2>	
			</div>
			</#if>
			<!--===================================-->
			<div class="box">
				<ul class="search" id="TopSearch">
					<cite>
					热门搜索：
					<#if searchWords?exists>
					<#list searchWords?split(",") as word>
					<a href="javascript:setTopSearchKey('${word}');onTopSubmit();">${word}</a>
					</#list>
					</#if>
					</cite>
					<form name="_topSearchForm" action="http://www.pinju.com/detail/shopItemList.htm" method="post" target="_blank">
					<input type="hidden" name="shopId" value="${_SHOPID?string('0')}" />
					<li>关键字：<input name="searchKey" type="text" class="input_l" /></li>
					<li><input type="button" onclick="onTopSubmit();" value="搜索" class="button" /></li>
					</form>
				</ul>
		  </div>
		  <script language="javascript" type="text/javascript">
		  <!--
		  		function onTopSubmit() {
		  			if(document._topSearchForm.searchKey.value == '') {
		  				alert('请输入搜索关键字');
		  				return false;
		  			}
		  			document._topSearchForm.submit();
		  		}
		  		
		  		function setTopSearchKey(key) {
		  			document._topSearchForm.searchKey.value = key;
		  		}
		  //-->
		  </script>		
		  <!--========================================================●页头搜索结束-->