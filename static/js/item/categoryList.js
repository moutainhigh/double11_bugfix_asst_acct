$(document).ready(function() {
	getCategoryList(0);
	$("#startItemReleased").bind("click", function() {
		$("#startItemReleasedFrom").attr( {
			action : "itemReleased.htm",
			method : "post"
		}).submit();
	});
});

function getCategoryList(id, obj, lv) {

	if (id != 0) {
		$(obj).parent().find("a").each(function() {
			$(this).removeClass("select");
		});
		if (!$(obj).attr("isLeaf")) {
			$("#startItemReleased").removeAttr("disabled");
		} else {
			$("#startItemReleased").attr("disabled", "disabled");
		}
		$(obj).addClass("select");
		$("#categoryId").val(id);
	}

	$.post("../item/getCategoryList.htm", {
		categoryParentId : id
	}, function(obj) {
		if (obj) {
			var o = $("#category" + obj.categoryLevel);
			if (!obj.size) {
				o.hide();
			} else {
				o.show();
				if (obj.categoryLevel == 2) {
					$("#category3").hide();
					$("#category4").hide();
				} else if (obj.categoryLevel == 3) {
					$("#category4").hide();
				}
				
				if (obj.categoryLevel) {
					o.html("<div class=\"box-search\"><input type=\"text\" categoryLevel=\"" + obj.categoryLevel
							+ "\"  onkeypress='categporySort(event,this)' size=\"16\"></div>");
				}

				for (i in obj.itemCategory) {

					var isLeaf = obj.itemCategory[i].isLeaf;
					var name = obj.itemCategory[i].name;
					var id = obj.itemCategory[i].id;
					var spell = obj.itemCategory[i].spell;

					var h = $("<a href='javascript:'></a>").html(name).attr("id", id).bind("click", function(e) {
						getCategoryList(this.id, this);
					});

					if (spell) {
						h.attr("spell", spell);
					}
					if (isLeaf == 0) {
						h.attr("isLeaf", true);
						h.addClass("box-icon");
					}
					o.append(h);
				}
			}
		}
	}, "json");
}

function categporySort(event, obj) {
	if (event.keyCode == 13) {
		var val = $(obj).val().toLowerCase();
		var level = $(obj).attr("categoryLevel");
		if (val) {
			var o = $("#category" + level);
			var t = $("<div></div>");
			o.find(".box-search").each(function() {
				t.append($(this));
			});
			o.find("a").each(function() {
				$(this).removeClass("hong");
				if ($(this).html().toLowerCase().indexOf(val) != -1) {
					$(this).addClass("hong");
					t.append($(this));
				} else {
					var spell = $(this).attr("spell");
					if (spell && spell != "undefined") {
						if (spell.toLowerCase().indexOf(val) != -1) {
							$(this).addClass("hong");
							t.append($(this));
						}
					}
				}
			});
			o.find("a").each(function() {
				t.append($(this));
			});
			o.html(t.html());
		}
	}
}