$(function() {
	var showFacetField = false;

	$("#allFacetField").click(function() {
		if (!showFacetField) {
			$(".h").removeClass("hide");
			$(this).removeClass("showall");
			$(this).addClass("showpart");
			$(this).html("收起");
			
			showFacetField = true;
		} else {
			$(".h").addClass("hide");
			$(this).removeClass("showpart");
			$(this).addClass("showall");
			$(this).html("显示全部分类");
			showFacetField = false;
		}
	});

	var s = new SearchUrl();
	$("#allResult").attr("href", s.getURL(false));

	//sort
	var sort = $("#prop_sort").val();
	if (sort != "startTime-desc") {
		$(".sort-starttime").attr("href", s.getCustomURL("sort", "startTime-desc"));
	}
	if (sort != "salesNum-desc") {
		$(".sort-salesNum").attr("href", s.getCustomURL("sort", "salesNum-desc"));
	}
	$(".sort-price-asc").attr("href", s.getCustomURL("sort", "price-asc"));
	$(".sort-price-desc").attr("href", s.getCustomURL("sort", "price-desc"));

	$("#selectLocal a").click(function() {
		$(this).attr("href", s.getCustomURL("city", $(this).html()));
	});
	$(".filter-cname").click(function() {
		$(this).attr("href", s.getCustomURL("catetoryName", $(this).attr("cname")));
	});
	$("#style-grid").click(function() {
		$(this).attr("href", s.getCustomURL("style", "grid"));
	});
	$("#style-list").click(function() {
		$(this).attr("href", s.getCustomURL("style", "list"));
	});
	
	var page = $("#prop_start").val();
	if (!page)
		page = 0;
	$(".search-prev").click(function() {
		$(this).attr("href", s.getCustomURL("start", Number(page) - 1));
	});
	$(".search-next").click(function() {
		$(this).attr("href", s.getCustomURL("start", Number(page) + 1));
	});

	var pages = Number($("#pages").val());
	var currPage = Number($("#currPage").val());
	if (pages > 100) {
		pages = 100;
	}
	$("#Pagination").pagination(pages * 40, {
		current_page : currPage - 1,
		items_per_page : 40,
		num_edge_entries : 3,
		num_display_entries : 3,
		callback : function(p) {
			window.location.href = s.getCustomURL("start", Number(p) + 1);
		}
	});

	$("#pagebutton").click(function() {
		var p = getPageNum($("#pagenum").val(), Number(pages));
		window.location.href = s.getCustomURL("start", p);
	});

	//search box
	var sbox = $('#searchBox').val();
	$('#q').suggest(sbox, {
		onSelect : function() {
			$('#search-form').submit();
		}
	});
	
	

	//img delay load
	$(".item_img").lazyload( {
		placeholder : "http://static.pinju.com/img/blank.gif"
	});
	
	//$(".plist-view .item .img a").followPop();

})