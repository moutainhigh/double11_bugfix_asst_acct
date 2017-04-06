$(document).ready(function() {
	
	//执行统计操作
	$("#statistics_but").each(function() {
		$(this).click(function() {
			var dateCode=$("select[name='sellReportQuery.dateCode']").val();
			var beginDate=$("#beginDate").val();
			var endDate=$("#endDate").val();
			if(dateCode ==""){
				if(beginDate =="" || endDate == ""){
					art.dialog.alert("请您选择统计的时间段 或者精确的时间段!");
					return;
			 }
			}
				$("#currPage").val("");
				$("#sortPattern").val("");
				$("#sortKey").val("");
				//alert($("#beginDate").val());
				$("#hotICategoryForm").attr( {
					action : "/sellReport/hotSellerCategoryStatistics.htm?v="+new Date().getTime(),
					method : "post"
				}).submit();
		});
	});
	
	
	//更改时间
	$("select[name='sellReportQuery.dateCode']").each(function(){
		$(this).bind("change",function(){
			var val=$(this).val();
			
			switch (val) {
				case '1':  //昨天
					$("#beginDate").val(new Date().DateAdd("d", -1).DateFormat("yyyy-MM-dd"));
					$("#endDate").val(new Date().DateAdd("d", -1).DateFormat("yyyy-MM-dd"));
					break;
				case '2': //近三天
					$("#beginDate").val(new Date().DateAdd("d", -3).DateFormat("yyyy-MM-dd"));
					$("#endDate").val(new Date().DateAdd("d", -1).DateFormat("yyyy-MM-dd"));
					break;
				case '3': //近一周
					$("#beginDate").val(new Date().DateAdd("w", -1).DateFormat("yyyy-MM-dd"));
					$("#endDate").val(new Date().DateAdd("d", -1).DateFormat("yyyy-MM-dd"));
					break;
				case '4': //近一个月
					$("#beginDate").val(new Date().DateAdd("m", 0).DateFormat("yyyy-MM-dd"));
					$("#endDate").val(new Date().DateAdd("d", -1).DateFormat("yyyy-MM-dd"));
					break;
				case '5': //近三个月
					$("#beginDate").val(new Date().DateAdd("m", -2).DateFormat("yyyy-MM-dd"));
					$("#endDate").val(new Date().DateAdd("d", -1).DateFormat("yyyy-MM-dd"));
					break;
				default:
					$("#beginDate").val("");
					$("#endDate").val("");
					break;
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
			$("#hotICategoryForm").attr( {
				action : "/sellReport/hotSellerCategoryStatistics.htm?v="+new Date().getTime(),
				method : "post"
			}).submit();
		},
		items_per_page : 1
	});
});

function chanageSel(){
	$("select[name='sellReportQuery.dateCode']").val("");
}

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
	$("#hotICategoryForm").attr( {
		action : "/sellReport/hotSellerCategoryStatistics.htm?v="+new Date().getTime(),
		method : "post"
	}).submit();
}

function importCSV() {
	$("input[name='sellReportQuery.status']").val("2");
	$("#hotICategoryForm").attr( {
		action : "/sellReport/importCsvStatistics.htm?v="+new Date().getTime(),
		method : "get"
	}).submit();
	$("input[name='sellReportQuery.status']").val("");
}
