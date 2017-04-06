function SearchUrl() {

	this.prop = {
		q : $("#prop_q").val(),
		filterExclude : $("#prop_filterExclude").val(),
		features : $("#prop_features").val(),
		cp : $("#prop_cp").val(),
		sort : $("#prop_sort").val(),
		catetoryName : $("#prop_catetoryName").val(),
		city : $("#prop_city").val(),
		startPrice : $("#prop_startPrice").val(),
		endPrice : $("#prop_endPrice").val(),
		start : $("#currPage").val(),
		facetLimit : $("#prop_facetLimit").val(),
		sellerId : $("#prop_sellerId").val(),
		style : $("#prop_style").val()
	}
	this.searchServer = $("#searchServer").val();
	this.url = "/search/search.htm";

};

SearchUrl.prototype.getURL = function(flag) {
	var u = "";
	if (typeof (this.searchServer) != "undefined") {
		u += this.searchServer;
	}
	u += this.url;
	var p = ""
	for (i in this.prop) {
		if (typeof (this.prop[i]) != "undefined" && this.prop[i] != "") {
			if (!flag && i == "catetoryName") {
				continue;
			}
			if (p != "") {
				p += "&";
			}
			p += i + "=" + encodeURIComponent(this.prop[i]);
		}
	}
	if (p != "") {
		return u + "?" + p;
	}
	return u;
};

SearchUrl.prototype.getCustomURL = function(obj, value) {
	var u = "";
	if (typeof (this.searchServer) != "undefined") {
		u += this.searchServer;
	}
	u += this.url;
	var p = ""
	var flag = false;
	for (i in this.prop) {
		if (typeof (this.prop[i]) != "undefined" && this.prop[i] != "") {
			if (p != "") {
				p += "&";
			}
			if (obj == i) {
				flag = true;
				p += obj + "=" + encodeURIComponent(value);
			} else {
				p += i + "=" + encodeURIComponent(this.prop[i]);
			}
		}
	}

	if (!flag) {
		if (p != "") {
			p += "&";
		}
		p += obj + "=" + encodeURIComponent(value);
	}
	if (p != "") {
		return u + "?" + p;
	}
	return u;
};

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