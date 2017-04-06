/**
 * 卖家后台管理 JS
 * 
 */
$(document).ready(function() {
		var pages = $("#pages").val();
		if(pages>100) pages=0;
		var currPage = $("#currPage").val();
		$("#Pagination").pagination(pages, {
			current_page : currPage - 1,
			num_edge_entries : 2,
			num_display_entries : 4,
			callback : function(page) {
				$("#shcm").val("pag");
				$("#currPage").val(++page);
				$("#form1").attr( {
					action : "sellRefundList.htm?pageNum=" + (++page),
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
});
function tagSwitch(tg) {
	$(".selected").addClass('');
	document.getElementById(tg).className='selected';
	$("#tgState").val(tg);
	$("#shcm").val('tg');
	$('#form1').submit();
}