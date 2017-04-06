<#setting classic_compatible=true>
<#setting number_format="#">
<title>发布商品</title>
<!-- START <选择商品类目> -->
<div class="wrap" id="sale-header"></div>
<div class="wrap-border cf" id="wrap-gray">
	<div class="select-button">
		<!--<input name="" type="button" class="left" />-->
	</div>
	<div class="select-frame" id="CategorySelecter">
		<div class="select-box" id="CategoryWrapper" ></div>
	</div>
	<div class="select-button">
		<!--<input name="" type="button" class="right" />-->
	</div>
</div>
<!-- END -->
<!-- START <选择商品类目> -->
<div class="wrap">
	<ul class="class-count">
		<li>
			<strong>您当前的选择是：</strong>
		</li>
		<li id="CategoryResult"></li>
	</ul>
</div>
<!-- END -->
<!-- START <发布主按钮> -->
<div class="wrap">
	<ul class="button-main">
		<li>
			<form id="startItemReleasedFrom">
				<#if itemInput!=null&&itemInput.categoryPath??>
					<input type="hidden" id="categoryPath" name="categoryPath" value="${itemInput.categoryPath}"/>
				<#else>
					<input type="hidden" id="categoryPath" name="categoryPath" value=""/>
				</#if>
				<input type="hidden" id="categoryId" name="categoryId" value=""/>
				<input type="hidden" id="categoryTitle" name="categoryTitle" value=""/>
				<input type="hidden" id="Submitable" name="Submitable" value="no" />
				<#if itemInput!=null&&itemInput.id??>
				<input type="hidden" id="itemInput.id" name="itemInput.id" value="${itemInput.id}"/>
				<#else>
					<input type="hidden" id="id" name="id" value=""/>
				</#if>
				<input type="button" id="submitButton" value="好了，发布商品" class="button" />
			</form>
		</li>
	</ul>
</div>
<!-- END -->
<textarea class="dom-tpl" rows="0" cols="0" id="CategoriesTpl">
	<ul class="categories" level="">
		<li class="top-search">
			<input name="" type="text" class="t">
		</li>
		<li class="top-search-bg"></li>
	</ul>
</textarea>
<textarea class="dom-tpl" rows="0" cols="0" id="CategoryTpl">
	<li>
		<a id="" href="javascript:;" spell="" isleaf="">商品类目</a>
	</li>
</textarea>
<textarea class="dom-tpl" rows="0" cols="0" id="CategoryResultTpl">
	<span>&gt;</span>
	<a href="javascript:">类目类目</a>
</textarea>
	
<script type="text/javascript">
	$(document).ready(function() {
		var cs = new CategorySelecter();
		cs.genChildCategory(0);
		$("#submitButton").click(function() {
			if ($('#Submitable').val() == 'no'){
				alert('请选择子类目');
			}else{
				$("#startItemReleasedFrom").attr( {
					action : "itemReleased.htm",
					method : "post"
				}).submit();
			}
		});
	});
</script>