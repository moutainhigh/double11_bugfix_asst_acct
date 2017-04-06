<link href="http://static.pinju.com/css/page.css" rel="stylesheet" type="text/css" />

<script src="http://static.pinju.com/js/jquery.cookie.js"></script>
<script src="http://static.pinju.com/js/jquery.myPagination.js" type="text/javascript"></script> 

<input type="hidden" name="currentPage" id="currentPage" value="${query.page}">
<input type="hidden" name="pages" id="pages" value="${query.pages}">
<div id="Pagination" class="pagination"></div>

<script>
	var pages = $("#pages").val();
	var currentPage = $("#currentPage").val();
	$("#Pagination").myPagination({
        currPage: currentPage,
        pageCount: pages,
        cssStyle: 'flickr',
        //更改样式
        info: {
        	first: '<<',
            first_on: true,
            last: '>>',
            last_on: true,
            prev: '<上一页',
            prev_on: true,
            next: '下一页>',
            next_on: true,
        	//msg: "<span>共{sumPage}页到第{currText}页</span>"
        	msg: "<span>共{sumPage}页</span>"
        },
        callback: function(page){
        	$("#currentPage").val(page);
        	$("#pageForm").attr( {
				action : "${(query.action)!}",
				method : "post"
			}).submit();
        }
	});
</script>