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
			$("#frm").attr( {
				action : "/coupon/couponBatchAll.htm?pageNum=" + (++page),
				method : "post"
			}).submit();
		},
		items_per_page : 1
	});
});

function invalidCoupon(batchId) {
	if (confirm("此操作将关闭领取链接，且无法恢复，但不影响已经领取的优惠券的使用，建议您及时删除推广页面的代码和链接，确认要继续吗？")) {
		$("input[name='tcb.id']").val($("#"+batchId).val());
		$("#frm").attr( {
				action : "/coupon/invalidCouponBatchById.htm",
				method : "post"
		}).submit();
	}
}

function closeCoupon(batchId) {
	if (confirm("您确认要删除这个失效的优惠券吗？")) {
		$("input[name='tcb.id']").val($("#"+batchId).val());
		$("#frm").attr( {
				action : "/coupon/closeCouponBatchById.htm",
				method : "post"
		}).submit();
	}
}

function closeCouponBatch() {
	if (confirm("您确认要删除这些失效的优惠券吗？")) {
		$("#frm").attr( {
				action : "/coupon/closeCouponBatch.htm",
				method : "post"
		}).submit();
	}
}

function editCoupon(batchId) {
	$("input[name='tcb.id']").val($("#"+batchId).val());
	$("#frm").attr( {
			action : "/coupon/queryCouponBatchById.htm",
			method : "post"
		}).submit();
}

