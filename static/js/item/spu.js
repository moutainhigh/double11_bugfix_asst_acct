function Spu(cId, vId) {
	this.id;
	this.cId = cId;
	this.vId = vId;
	this.name;
	this.memo;
	this.propertyValues = [];
	this.picUrl;
	this.keyPropertyValue;
	this.price;
	this.initDate();
};

Spu.prototype.initDate = function() {
	this.setAttrDisplay();
	$("#spuShow").html("");
	$("#spuId").val(0);
	var _this = this;
	if (this.cId > 0 && this.vId > 0) {
		$.getJSON("/item/getSpu.htm", {
			cid : this.cId,
			vid : this.vId
		}, function(obj) {
			if (obj && obj.spuId) {
				_this.id = obj.spuId;
				_this.name = obj.name;
				_this.price = obj.marketPrice;
				_this.memo = obj.memo;
				_this.propertyValues = obj.idsList;
				_this.initUi();
			}
		});
	}
};

Spu.prototype.initUi = function() {
	$("#spuId").val(this.id);
	if (this.name) {
		$("#spuShow").append($("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").html("名称：" + this.name));
	}
	if (this.price) {
		$("#spuShow").append($("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").html("市场价：" + this.price));
	}
	if (this.propertyValues.length > 0) {
		for (i in this.propertyValues) {
			var id = this.propertyValues[i].pid;
			if (id && id != "" && id != "undefined") {
				for (j in this.propertyValues[i].vids) {
					var li = $("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").attr("cid", id);
					li.append(this.propertyValues[i].name + "：" + this.propertyValues[i].vids[j]);
					$("#spuShow").append(li);
				}
				this.setAttrDisplay(id);
			}
		}
	}
	if (this.memo) {
		$("#spuShow").append($("<li style='word-wrap:break-word;line-height:18px;font-size: 12px;'></li>").html("描述：" + this.memo));
	}
};

Spu.prototype.setAttrDisplay = function(id) {
	$("#itemAtt").find("tr").each(function() {
		if (!id) {
			$(this).removeClass("hide");
		}
		if ($(this).attr("cid") == id) {
			$(this).addClass("hide");
		}
	});
};
