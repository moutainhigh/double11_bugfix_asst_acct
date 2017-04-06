<div class="header cf">
	<div class="masthead cf">
		<div class="logo">
			<#if searchType?? && searchType==3>
			<a href="http://www.pinju.com" class="f_left">
				<img class="f_left" src="http://static.pinju.com/img/pj_logo180x54.gif?t=20111125.gif"alt="品聚网" title="点击回到品聚网首页" />
			</a>
			<img class="f_left" src="http://static.pinju.com/img/logoplace-hot.jpg">
			<#else>
				<a href="http://www.pinju.com">
					<img class="logo_pic" width="326" height="54" src="http://static.pinju.com/images/pj_logo_17.gif?t=20111114.gif" alt="品聚网" title="点击回到品聚网首页" />
				</a>
			</#if>
		</div>
		<div class="search-panel"> 
			<div class="tabs" id="J_search_tabs">
				<ul> 
					<li><a id="itemPanel" data-type="goods" data-url="http://www.pinju.com/search/isbox.htm?callback=?" <#if !searchType?? || searchType==1>class="active"</#if>>商品</a></li> 
					<li><a id="shopPanel" data-type="shop" data-url="http://www.pinju.com/search/ssbox.htm?callback=?" <#if searchType?? && searchType==2>class="active"</#if>>店铺</a></li>
					<li><a id="hotPanel" data-type="hot" data-url="http://www.pinju.com/search/isbox.htm?callback=?" <#if searchType?? && searchType==3>class="active"</#if>>热卖</a></li>
					<input type="hidden" name="searchHidden" id="searchHidden" value="" /></li>
				</ul> 
			</div> 
			<form name="search" id="J_search_form" method="GET"> 
				<div class="search-fields"> 
					<label for="J_search_keyword" <#if q??>style="display:none;"</#if>>搜索你要找的内容</label> 
					<input id="J_search_keyword" name="q" value="<#if q??>${q?html}</#if>" maxLength="50" autocomplete="off"/>
				</div> 
				<input type="submit" value="搜&nbsp;&nbsp;索" class="btn" id="J_search_btn" />
			</form>
			<input name="type" type="hidden" id="J_search_type" value="${searchType!}" />
			<input name="type" type="hidden" id="J_base_url" value="${base}" />
			<iframe src="http://www.pinju.com/cms/html/keywords/" scrolling="no" height="20" width="570" frameborder="0" ></iframe>
		</div>
	</div>
</div>
<link href="http://static.pinju.com/suggest/jquery.suggest.css" rel="stylesheet" />
<script src="http://static.pinju.com/suggest/jquery.suggest.js"></script>
<script>
	$(function() {
		var form = $('#J_search_form'),
			label = form.find('label'),
			keyword = $('#J_search_keyword'),
			baseUrl = $('#J_base_url').val();
			
		$('#J_search_tabs a').click(function() {
			$('#J_search_tabs a').removeClass('active');
			$(this).addClass('active');
			var type = $(this).attr('data-type');
			//$('#J_search_type').val(type);
			if (type == 'goods') {
				form.attr('action', 'http://www.pinju.com/search/search.htm');
			} else if (type == 'shop') {
				form.attr('action', 'http://www.pinju.com/search/shopSearch.htm');
			} else if (type == 'hot') {
				form.attr('action', 'http://www.pinju.com/hot/hot.htm');
			}
			keyword.unbind();
			keyword.suggest($(this).attr('data-url'), {
				onSelect : function() {
					form.submit();
				}
			});
			keyword.focus(function() {
				label.hide();
			}).blur(function() {
				if ($.trim(keyword.val()) === '') {
					label.show();
				}
			});
		});
		
		var currentIndex = $('#J_search_type').val() ;
		$('#J_search_tabs a').eq(currentIndex-1).click();
	});
</script>