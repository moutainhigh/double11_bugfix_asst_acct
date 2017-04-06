<!-- 公共分页ftl,记得设置分页的action,使用方法可以问我, add by liuboen 
使用方法：	1、继承paginator类
			2、设置总条数,每页显示多少条
			3、设置action即可
			4、include
-->
<#setting classic_compatible=true>
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<link href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />  
<input type="hidden" name="currentPage" id="currentPage" value="${(query.page)!}">
<input type="hidden" name="pages" id="pages" value="${(query.pages)!}">
<div id="Pagination" class="pagination"></div>
<script>
		var pages = $("#pages").val();
		var currPage = $("#currentPage").val();
		$("#Pagination").pagination(pages, {
			current_page : currPage - 1,
			num_edge_entries : 2,
			num_display_entries : 4,
			callback : function(page) {
			    $("#currentPage").val(++page);
				$("#pageForm").attr( {
				    action : "${(query.action)!}" ,
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
</script>