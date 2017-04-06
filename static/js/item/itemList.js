$(document).ready(function() {

	$("#btnSearch").bind("click", function() {

		var title = $("#item_search_title").val();
		var code = $("#item_search_code").val();
		var minPrice = $("#item_search_minPrice").val();
		var maxPrice = $("#item_search_maxPrice").val();
		var minSales = $("#item_search_minSales").val();
		var maxSales = $("#item_search_maxSales").val();

		$("#itemListForm").attr( {
			action : "itemList.htm",
			method : "post"
		}).submit();
	});

	$(document).find('[name="delItem"]').bind("click", function() {
		if (getSelectItemId() != "") {
			if (confirm("确认删除选中商品？")) {
				$("#itemListForm").attr( {
					action : "/item/userDelItem.htm",
					method : "post"
				}).submit();
			}
		}else{
			alert("请选择需要删除的商品")
		}
	});
	$(document).find('[name="delShelfItem"]').bind("click", function() {
		if (getSelectItemId() != "") {
			if (confirm("确认下架选中商品？")) {
				$("#itemListForm").attr( {
					action : "/item/userDelShelfItem.htm",
					method : "post"
				}).submit();
			}
		}else{
			alert("请选择需要下架的商品")
		}
	});
	$(document).find('[name="addShelfItem"]').bind("click", function() {
		var flag = getSelectItemIdForAdd();
		if (flag != "") {
			if(flag == "false"){
				alert("运营下架商品请编辑后上架。")
			}else if(flag == "falseAPI"){
				alert("无类目商品请编辑后上架。")
			}else{
				if (confirm("确认上架选中商品？")) {
					$("#itemListForm").attr( {
						action : "/item/userAddShelfItem.htm",
						method : "post"
					}).submit();
				}
			}
			
		}else{
			alert("请选择需要上架的商品")
		}
	});

	$(".editItem").each(function() {
		$(this).click(function() {
			$("#itemListForm").attr( {
				action : "/itemReleased/getUpdate.htm?itemInput.id=" + this.id,
				method : "post"
			}).submit();
		});
	});

	var pages = $("#pages").val();
	var currPage = $("#currPage").val();

	$("#Pagination").pagination(pages, {
		current_page : currPage - 1,
		num_edge_entries : 2,
		num_display_entries : 4,
		callback : function(page) {
			$("#currPage").val(page+1);
			$("#itemListForm").attr( {
				action : "itemList.htm",
				method : "post"
			}).submit();
		},
		items_per_page : 1
	});

	$.allSelect( {
		buttonName : "selectAll",
		allCheckBoxId : "all",
		listDivId : "item-List",
		listCheckBoxName : "itemQuery.ids"
	});

	$("#filterUl").find("li").each(function() {
		$(this).bind("click", function() {
			var t = $(this).attr("type");
			$("#searchFilterType").val(t);
			$("#itemListForm").attr( {
				action : "itemList.htm",
				method : "post"
			}).submit();
			return false;
		});
	});

});

function getSelectItemIdForAdd() {
	var t = "";
	var falg = "true";
	$("#item-List").find('input[type="checkbox"]').each(function() {
		if ($(this).attr("checked")) {
			if (t) {
				t += ",";
			}
			t += $(this).val();	
			var noadd = $("#noAddShelfItem-"+$(this).val()).val();
			var noaddAPI = $("#noAddShelfItemAPI-"+$(this).val()).val();
			if(!noadd || noadd == ""){
//				falg = false;
			}else{
				falg = "false";
			}
			if(!noaddAPI || noaddAPI == ""){
//				falg = false;
			}else{
				falg = "falseAPI";
			}
		}
	});
	if(falg!="true"){
		t = falg;
	}
	return t;
}

function getSelectItemId() {
	var t = "";
	$("#item-List").find('input[type="checkbox"]').each(function() {
		if ($(this).attr("checked")) {
			if (t) {
				t += ",";
			}
			t += $(this).val();
		}
	});
	return t;
}
