$(document).ready(function(){$("#statistics_but").each(function(){$(this).click(function(){var c=$("select[name='sellReportQuery.dateCode']").val();var d=$("#beginDate").val();var e=$("#endDate").val();if(c==""){if(d==""||e==""){art.dialog({time:3,lock:true,background:"#F6F6F6",opacity:0.5,content:"请您选择统计的时间段 或者精确的时间段",icon:"warning",ok:true});return}}$("#currPage").val("");$("#sortPattern").val("");$("#sortKey").val("");$("#hotICategoryForm").attr({action:"/sellReport/hotSellerCategoryStatistics.htm?version="+new Date().getTime(),method:"post"}).submit()})});$("select[name='sellReportQuery.dateCode']").each(function(){$(this).bind("change",function(){var c=$(this).val();switch(c){case"1":$("#beginDate").val(new Date().DateAdd("d",-1).DateFormat("yyyy-MM-dd"));$("#endDate").val(new Date().DateAdd("d",-1).DateFormat("yyyy-MM-dd"));break;case"2":$("#beginDate").val(new Date().DateAdd("d",-3).DateFormat("yyyy-MM-dd"));$("#endDate").val(new Date().DateAdd("d",-1).DateFormat("yyyy-MM-dd"));break;case"3":$("#beginDate").val(new Date().DateAdd("w",-1).DateFormat("yyyy-MM-dd"));$("#endDate").val(new Date().DateAdd("d",-1).DateFormat("yyyy-MM-dd"));break;case"4":$("#beginDate").val(new Date().DateAdd("m",0).DateFormat("yyyy-MM-dd"));$("#endDate").val(new Date().DateAdd("d",-1).DateFormat("yyyy-MM-dd"));break;case"5":$("#beginDate").val(new Date().DateAdd("m",-2).DateFormat("yyyy-MM-dd"));$("#endDate").val(new Date().DateAdd("d",-1).DateFormat("yyyy-MM-dd"));break;default:$("#beginDate").val("");$("#endDate").val("");break}})});var a=$("#pages").val();var b=$("#currPage").val();$("#Pagination").pagination(a,{current_page:b-1,num_edge_entries:2,num_display_entries:10,callback:function(c){$("#currPage").val(c+1);$("#hotICategoryForm").attr({action:"/sellReport/hotSellerCategoryStatistics.htm?version="+new Date().getTime(),method:"post"}).submit()},items_per_page:1})});function chanageSel(){$("select[name='sellReportQuery.dateCode']").val("")}function orderSubmit(a){if($("#sortKey").val()!=a){$("#sortKey").val(a);$("#orderStatus").val("ASC")}else{if($("#sortKey").val()==a){if($("#orderStatus").val()=="DESC"){$("#orderStatus").val("ASC")}else{$("#orderStatus").val("DESC")}}}$("#hotICategoryForm").attr({action:"/sellReport/hotSellerCategoryStatistics.htm?version="+new Date().getTime(),method:"post"}).submit()}function importCSV(){$("input[name='sellReportQuery.status']").val("2");$("#hotICategoryForm").attr({action:"/sellReport/importCsvStatistics.htm?version="+new Date().getTime(),method:"post"}).submit();$("input[name='sellReportQuery.status']").val("")};