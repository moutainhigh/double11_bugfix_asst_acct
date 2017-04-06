$(function() {

	$("#shopSearchAction").attr("action", "shopSearch.htm");

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
		callback : function(page) {
			$("#start").val((page + 1));
			$("#shopSearchAction").submit();
		}
	});

	$("#topnext").click(function(e) {
		e.preventDefault();
		var start = $("#start").val();
		$("#start").val(getPageNum(Number(start) + 1, pages));
		$("#shopSearchAction").submit();
	});

	$("#topprv").click(function(e) {
		e.preventDefault();
		var start = $("#start").val();
		$("#start").val(getPageNum(Number(start) - 1, pages));
		$("#shopSearchAction").submit();
	});

	$("#pagebutton").click(function() {
		$("#start").val(getPageNum($("#pagenum").val(), pages));
		$("#shopSearchAction").submit();
	});

	$("#fq-city").change(function() {
		if ($(this).val() == "") {
			//return;
		}
		$("#city").val($(this).val());
		$("#shopSearchAction").submit();
	});

	var city = $("#city").val();
	if (city == "") {
		$("#fq-city").val("所有地区");
	} else {
		$("#fq-city").val(city);
	}

	//searchBox
	var sbox = $('#searchBox').val();
	$('#q').suggest(sbox, {
		onSelect : function() {
			$('#search-form').submit();
		}
	});

	//img delay load
	$(".shop_img").lazyload( {
		placeholder : "http://static.pinju.com/img/blank.gif"
	});

});

function getPageNum(num, pages) {
	if (/[^\d]/.test(num)) {
		return 1;
	}
	if (num > pages) {
		return pages;
	}
	if (num < 1) {
		return 1;
	}
	return num;
}