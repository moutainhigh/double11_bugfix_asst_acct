<link href="http://static.pinju.com/css/page.css" rel="stylesheet" type="text/css" />
<script src="http://static.pinju.com/js/jquery.cookie.js"></script>
<script src="http://static.pinju.com/js/jquery.myPagination.js" type="text/javascript"></script>

<input type="hidden" name="currentPage" id="currentPage" value="${(query.page)!0}">
<input type="hidden" name="pages" id="pages" value="${(query.pages)!1}">
<div id="Pagination" class="pagination"></div>
<script type="text/javascript">
		// 默认总页数为 1 
		var pages = $("#pages").val();
        var current_page = $("#currentPage").val();
        
        function InitData(){
        	$("#Pagination").myPagination({
	        	currPage: 1,
	        	pageCount: pages,
	        	cssStyle: 'flickr',  // 样式可以自定义
	        	// 更改显示样式
	        	info: {
	        		first: '<<',
		            first_on: true,
		            last: '>>',
		            last_on: true,
		            prev: '<上一页',
		            prev_on: true,
		            next: '下一页>',
		            next_on: true,
	        		msg: "<span>共{sumPage}页到第{currText}页</span>"
	        	},
	        	ajax: {
	        		on: true,
	        		callback: 'ajaxCallBack', // 需要自定义函数，用于不同DIV/TABLE更新
	        		url: '${(query.action)!}', // 自定义ACTION
	        		dataType: 'json',
	        		param : {
						on : true, // true 表示传参，false 表示不传参
						page : 1, // 必填，不可以修改，自动更新，后台通过获取此参数，判断跳转页数
						currentPage : current_page + 1
					}
	        	}
	        });
	        $("#Pagination").show();
        }
        
        function InitData(definedParams){
        	var params = {on : true, page : 1, currentPage : current_page + 1};
        	if(definedParams){
	        	for(var p in definedParams){
	        		params[p] = definedParams[p];
	        	}	
        	}
        	$("#Pagination").myPagination({
	        	currPage: 1,
	        	pageCount: pages,
	        	cssStyle: 'flickr', // 样式可以自定义
	        	// 更改显示样式
	        	info: {
	        		first: '<<',
		            first_on: true,
		            last: '>>',
		            last_on: true,
		            prev: '<上一页',
		            prev_on: true,
		            next: '下一页>',
		            next_on: true,
	        		msg: "<span>共{sumPage}页到第{currText}页</span>"
	        	},
	        	ajax: {
	        		on: true,
	        		callback: 'ajaxCallBack', // 需要自定义函数，用于不同DIV/TABLE更新
	        		url: '${(query.action)!}', // 自定义ACTION
	        		dataType: 'json',
	        		param : params
	        	}
	        });
	        $("#Pagination").show();
        }
        
        function refeshPages(definedParams, tableTrs, tableID){
        	$.ajax({
        		type: 'POST',
        		url: '${(query.action)!}' + '?type=refresh',
        		data: definedParams,
        		success: function(totalPages){
        			pages = totalPages;
        			if(parseInt(pages) < 1){
						tableTrs.remove();
						var tip = "<tr><td colspan=\"4\"><div style=\"text-align:center;color:red;\"><span>无对应查询结果，请重新查询</span></div></td></tr>";
						tableID.append(tip);
						$("#Pagination").hide();
						return false;     			
        			}else{
        				InitData(definedParams);
        			}
        		}
        	});
        }

</script>