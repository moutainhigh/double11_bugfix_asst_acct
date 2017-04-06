$(document).ready(function() {
	var pages = $("#pages").val();
	if(pages>100) pages=0;
	var currPage = $("#currPage").val();
	$("#Pagination").pagination(pages, {
		current_page : currPage - 1,
		num_edge_entries : 2,
		num_display_entries : 4,
		callback : function(page) {
			$("#currPage").val(++page);
			$("#form1").attr( {
				action : "sellReport/simpleReport.htm?pageNum=" + (++page),
				method : "post"
			}).submit();
		},
		items_per_page : 1
	});
});

function isShowCondition(type) {
	if (type == "bytime") {
		$("#sel1").val(type);
		$("#selDate").val("7");
		selDateChange(7);
		$("#bytime").show();
		$("#byyear").hide();
	} 
	if (type == "byyear") {
		$("#sel2").val(type);
		$("input[name='startDate']").val("");
		$("input[name='endDate']").val("");
		$("#selDate").val("");
		$("#bytime").hide();
		$("#byyear").show();
	}
}
function selDateChange(dateVal) {
	var sdate = new Date();
	var edate = new Date();
	sdate.setDate(sdate.getDate()-dateVal);
	var sdateVal = sdate.format("yyyy-MM-dd");
	
	var edate = new Date();
	edate.setDate(edate.getDate()-1);
	var edateVal = edate.format("yyyy-MM-dd");
	if (dateVal > 0) {
		$("input[name='startDate']").val(sdateVal);
		$("input[name='endDate']").val(edateVal);
	}
}

function isSubmit(flagVal,status) {
	if (flagVal == "day") {
		if ($("input[name='startDate']").val() == "" || $("input[name='endDate']").val() == "") {
			//alert("请选择精确时间段");
			art.dialog.alert("请选择精确时间段");
			return false;		
		}
	} else {
		if ($("input[name='year']").val() == "" ) {
			//alert("请选择年份");
			art.dialog.alert("请选择精确时间段");
			return false;
		}
	}
	$('#flag').val(flagVal);
	if (status == 1) {
		$("#orderField").val("");
		$("#orderStatus").val("");
	}
	document.frm.action="/sellReport/simpleReport.htm";
	document.frm.submit();
}

function orderSubmit(field) {
	if ($("#orderField").val()  != field) {
		$("#orderField").val(field);
		$("#orderStatus").val("desc");
	} else if ($("#orderField").val()  == field) {
		if ($("#orderStatus").val() == "desc") {
			$("#orderStatus").val("asc");
		} else {
			$("#orderStatus").val("desc");
		}
	}
	isSubmit($("#flag").val(),2);
}

function importcsv() {
	$("#form1").attr( {
		action : "/sellReport/importCSV.htm",
		method : "get"
	}).submit();
}
