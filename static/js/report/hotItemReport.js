$(document).ready(function() {
	
	$("#statistics_but").each(function() {
		$(this).click(function() {
			if($("select[name='sellReportQuery.dateCode']").val() ==""){
				//$("#msgTip").parent().show();
				//$("#msgTip").html("请您选择统计的时间段");
			}else{
				$("#currPage").val("");
				$("#sortPattern").val("");
				$("#sortKey").val("");
				$("#hotItemsForm").attr( {
					action : "/sellReport/hotSellItemsReportStatistics.htm?v="+new Date().getTime(),
					method : "post"
				}).submit();
			}
		});
	});
	
	var pages = $("#pages").val();
	var currPage = $("#currPage").val();
	
	$("#Pagination").pagination(pages, {
		current_page : currPage - 1,
		num_edge_entries : 2,
		num_display_entries : 10,
		callback : function(page) {
			$("#currPage").val(page+1);
			$("#hotItemsForm").attr( {
				action : "/sellReport/hotSellItemsReportStatistics.htm?v="+new Date().getTime(),
				method : "post"
			}).submit();
		},
		items_per_page : 1
	});
	
});


function orderSubmit(field) {
	//alert(field);
	if ($("#sortKey").val()  != field) {
		$("#sortKey").val(field);
		$("#orderStatus").val("ASC");
	} else if ($("#sortKey").val()  == field) {
		if ($("#orderStatus").val() == "DESC") {
			$("#orderStatus").val("ASC");
		} else {
			$("#orderStatus").val("DESC");
		}
	}
	$("#hotItemsForm").attr( {
		action : "/sellReport/hotSellItemsReportStatistics.htm?v="+new Date().getTime(),
		method : "post"
	}).submit();
}

function importCSV() {
	$("input[name='sellReportQuery.status']").val("3");
	$("#hotItemsForm").attr( {
		action : "/sellReport/importCsvStatistics.htm?v="+new Date().getTime(),
		method : "get"
	}).submit();
	$("input[name='sellReportQuery.status']").val("");
}