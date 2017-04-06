/**
 * 
 * 文本框只能输入数字，并屏蔽输入法和粘贴 兼容火狐 IE
 * 
 * 
 * @author liming
 * @since 2011-5-30
 * @param decimalPlace
 *            小数点位数（默认0）
 * @param decimalPlace
 *            数字长度（包含小数点）（默认30）
 * @import jquery-1.6.1.min.js
 * @demo $("#input").numeral({ decimalPlace:2, maxLength:10 });
 * 
 */
$(function() {
	$.fn.numeral = function(opts) {
		opts = jQuery.extend( {
			decimalPlace : 0,
			maxLength : 30
		}, opts || {});
		$(this).css("ime-mode", "disabled");
		this.bind("keypress", function(e) {
			var code = (e.keyCode ? e.keyCode : e.which);
			if (e.ctrlKey) {
				return;
			}
			if (!$.browser.msie && (e.keyCode == 0x8)) {
				return;
			}
			if (code == 37 || code == 39) {
				return;
			}
			if ($(this).val().length >= opts.maxLength) {
				return false;
			}
			if (opts.decimalPlace == 0) {
				return code >= 48 && code <= 57
			} else {
				var d = $(this).val().indexOf(".");
				if (code == 46 && d != -1) {
					return false;
				}
				if (code == 46 && this.value == "") {
					return false;
				}
				if (code == 46 && this.value.length + 1 == opts.maxLength) {
					return false;
				}
				return code >= 48 && code <= 57 || code == 46;
			}
		});
		this.bind("blur", function() {
			if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
				this.value = this.value.substr(0, this.value.length - 1);
			} else if (isNaN(this.value)) {
				this.value = "";
			}
		});
		// this.bind("paste", function() {
			// var s = clipboardData.getData('text');
			// if (!/\D/.test(s))
				// ;
			// value = s.replace(/^0*/, '');
			// return false;
		// });
		this.bind("dragenter", function() {
			return false;
		});
		this.bind("keyup", function() {
			if (opts.decimalPlace > 0) {
				var d = $(this).val().indexOf(".");
				if (d != -1 && $(this).val().length - d > opts.decimalPlace + 1) {
					this.value = this.value.substring(0, d + opts.decimalPlace + 1);
				}
			}
			if (/^0+$/.test(this.value)) {
				this.value = 0;
				return;
			}
			if (/^0+/.test(this.value)) {
				this.value = this.value.replace(/^0+/, '');
			}
		});
	};
});


/**
 * 
 * 文本框只能输入数字，并屏蔽输入法和粘贴 兼容火狐 IE
 * 
 * @since 2011-07-28
 * @param decimalPlace
 *            小数点位数（默认0）
 * @param decimalPlace
 *            数字长度（包含小数点）（默认30）
 * @import jquery-1.6.1.min.js
 * @demo $("#input").numeral({ decimalPlace:2, maxLength:10 });
 * 
 *  注意：此支持数字以“0”开头的数字，eg：0.5
 * 
 */
$(function() {
	$.fn.numeralDecimal = function(opts) {
		opts = jQuery.extend( {
			decimalPlace : 0,
			maxLength : 30
		}, opts || {});
		$(this).css("ime-mode", "disabled");
		this.bind("keypress", function(e) {
			var code = (e.keyCode ? e.keyCode : e.which);
			if (e.ctrlKey) {
				return;
			}
			if (!$.browser.msie && (e.keyCode == 0x8)) {
				return;
			}
			if (code == 37 || code == 39) {
				return;
			}
			if ($(this).val().length >= opts.maxLength) {
				return false;
			}
			if (opts.decimalPlace == 0) {
				return code >= 48 && code <= 57
			} else {
				var d = $(this).val().indexOf(".");
				if (code == 46 && d != -1) {
					return false;
				}
				if (code == 46 && this.value == "") {
					return false;
				}
				if (code == 46 && this.value.length + 1 == opts.maxLength) {
					return false;
				}
				return code >= 48 && code <= 57 || code == 46;
			}
		});
		this.bind("blur", function() {
			if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
				this.value = this.value.substr(0, this.value.length - 1);
			} else if (isNaN(this.value)) {
				this.value = "";
			}
		});
		
		
		// this.bind("paste", function() {
			// var s = clipboardData.getData('text');
			// if (!/\D/.test(s));
			// value = s.replace(/^0*/, '');
			// return false;
		// });
		this.bind("dragenter", function() {
			return false;
		});
		
		this.bind("keyup", function() {
			if (opts.decimalPlace > 0) {
				var d = $(this).val().indexOf(".");
				if (d != -1 && $(this).val().length - d > opts.decimalPlace + 1) {
					this.value = this.value.substring(0, d + opts.decimalPlace + 1);
				}
			}
			//if (/(^0+)/.test(this.value)) {
			//	this.value = this.value.replace(/^0*/, '');
			//}
			
		});
	};
});

/**
 * 日期格式化
 * 
 * @author liming
 * @since 2011-6-2
 * 
 * @param {Object}
 *            format
 * @memberOf {TypeName}
 * @return {TypeName}
 * 
 * @demo
 * 
 * var now = new Date(); alert(now.format("yyyy年MM月dd日")); alert(new
 * Date().format("yyyy-MM-dd"));
 * 
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

/**
 * 设置地区和城市的选择
 * 
 * @author liming
 * @since 2011-6-2
 * 
 * @param provBuildName
 *            地区Name
 * @param cityBuildName
 *            城市Name
 * @param provClass
 *            地区class
 * @param cityClass
 *            城市class
 * 
 * @demo //默认 $("#city").selectprov(); //带参数 $("#city").selectprov({
 *       provBuildName : "myprov", cityBuildName : "mycity", provClass :
 *       "myProvClass", cityClass : "myCityClass" });
 * 
 */
$(function() {
	$.fn.selectprov = function(opts) {
		opts = jQuery.extend( {
			provBuildName : "prov",
			cityBuildName : "city",
			provClass : "",
			cityClass : "",
			currProv : "",
			currCity : ""
		}, opts || {});

		var provSelect = $("<select></select>");
		var citySelect = $("<select></select>");
		provSelect.attr("name", opts.provBuildName.replace(/[^0-9a-zA-Z.]/g, ""));
		citySelect.attr("name", opts.cityBuildName.replace(/[^0-9a-zA-Z.]/g, ""));
		provSelect.attr("id", opts.provBuildName.replace(/[^0-9a-zA-Z]/g, ""));
		citySelect.attr("id", opts.cityBuildName.replace(/[^0-9a-zA-Z]/g, ""));
		provSelect.addClass(opts.provClass);
		citySelect.addClass(opts.cityClass);
		citySelect.css("width", "100px");

		var provList = [ '安徽', '北京', '重庆', '福建', '甘肃', '广东', '广西', '贵州', '海南', '河北', '黑龙江', '河南', '湖北', '湖南', '江苏',
				'江西', '吉林', '辽宁', '内蒙古', '宁夏', '青海', '山东', '上海', '山西', '陕西', '四川', '天津', '新疆', '西藏', '云南', '浙江', '香港',
				'澳门', '台湾', '海外' ];

		provSelect.append($("<option></option>").val("").html("----请选择省份----"));
		for (i in provList) {
			provSelect.append($("<option></option>").val(provList[i]).html(provList[i]));
		}

		if (opts.currProv != "") {
			provSelect.val(opts.currProv);
		}

		provSelect.bind("change", initCitySelect);

		function initCitySelect(e) {
			switch (provSelect.val()) {
			case "安徽":
				var cityOptions = new Array("合肥(*)", "合肥", "安庆", "安庆", "蚌埠", "蚌埠", "亳州", "亳州", "巢湖", "巢湖", "滁州", "滁州",
						"阜阳", "阜阳", "贵池", "贵池", "淮北", "淮北", "淮化", "淮化", "淮南", "淮南", "黄山", "黄山", "九华山", "九华山", "六安",
						"六安", "马鞍山", "马鞍山", "宿州", "宿州", "铜陵", "铜陵", "屯溪", "屯溪", "芜湖", "芜湖", "宣城", "宣城");
				break;
			case "北京":
				var cityOptions = new Array("北京", "北京");
				break;
			case "重庆":
				var cityOptions = new Array("重庆", "重庆");
				break;
			case "福建":
				var cityOptions = new Array("福州(*)", "福州", "福安", "福安", "龙岩", "龙岩", "南平", "南平", "宁德", "宁德", "莆田", "莆田",
						"泉州", "泉州", "三明", "三明", "邵武", "邵武", "石狮", "石狮", "永安", "永安", "武夷山", "武夷山", "厦门", "厦门", "漳州",
						"漳州");
				break;
			case "甘肃":
				var cityOptions = new Array("兰州(*)", "兰州", "白银", "白银", "定西", "定西", "敦煌", "敦煌", "甘南", "甘南", "金昌", "金昌",
						"酒泉", "酒泉", "临夏", "临夏", "嘉峪关", "嘉峪关", "平凉", "平凉", "天水", "天水", "武都", "武都", "武威", "武威", "西峰",
						"西峰", "张掖", "张掖");
				break;
			case "广东":
				var cityOptions = new Array("广州(*)", "广州", "潮阳", "潮阳", "潮州", "潮州", "澄海", "澄海", "东莞", "东莞", "佛山", "佛山",
						"河源", "河源", "惠州", "惠州", "江门", "江门", "揭阳", "揭阳", "开平", "开平", "茂名", "茂名", "梅州", "梅州", "清远", "清远",
						"汕头", "汕头", "汕尾", "汕尾", "韶关", "韶关", "深圳", "深圳", "顺德", "顺德", "阳江", "阳江", "英德", "英德", "云浮", "云浮",
						"增城", "增城", "湛江", "湛江", "肇庆", "肇庆", "中山", "中山", "珠海", "珠海");
				break;
			case "广西":
				var cityOptions = new Array("南宁(*)", "南宁", "百色", "百色", "北海", "北海", "桂林", "桂林", "防城港", "防城港", "河池",
						"河池", "贺州", "贺州", "柳州", "柳州", "钦州", "钦州", "梧州", "梧州", "来宾", "来宾", "玉林", "玉林");
				break;
			case "贵州":
				var cityOptions = new Array("贵阳(*)", "贵阳", "安顺", "安顺", "毕节", "毕节", "都匀", "都匀", "凯里", "凯里", "六盘水",
						"六盘水", "铜仁", "铜仁", "兴义", "兴义", "玉屏", "玉屏", "遵义", "遵义");
				break;
			case "海南":
				var cityOptions = new Array("海口(*)", "海口", "儋县", "儋县", "陵水", "陵水", "琼海", "琼海", "三亚", "三亚", "五指山",
						"五指山", "万宁", "万宁");
				break;
			case "河北":
				var cityOptions = new Array("石家庄(*)", "石家庄", "保定", "保定", "北戴河", "北戴河", "沧州", "沧州", "承德", "承德", "丰润",
						"丰润", "邯郸", "邯郸", "衡水", "衡水", "廊坊", "廊坊", "南戴河", "南戴河", "秦皇岛", "秦皇岛", "唐山", "唐山", "新城", "新城",
						"邢台", "邢台", "张家口", "张家口");
				break;
			case "黑龙江":
				var cityOptions = new Array("哈尔滨(*)", "哈尔滨", "北安", "北安", "大庆", "大庆", "大兴安岭", "大兴安岭", "鹤岗", "鹤岗", "黑河",
						"黑河", "佳木斯", "佳木斯", "鸡西", "鸡西", "牡丹江", "牡丹江", "齐齐哈尔", "齐齐哈尔", "七台河", "七台河", "双鸭山", "双鸭山", "绥化",
						"绥化", "伊春", "伊春");
				break;
			case "河南":
				var cityOptions = new Array("郑州(*)", "郑州", "安阳", "安阳", "鹤壁", "鹤壁", "潢川", "潢川", "焦作", "焦作", "济源", "济源",
						"开封", "开封", "漯河", "漯河", "洛阳", "洛阳", "南阳", "南阳", "平顶山", "平顶山", "濮阳", "濮阳", "三门峡", "三门峡", "商丘",
						"商丘", "新乡", "新乡", "信阳", "信阳", "许昌", "许昌", "周口", "周口", "驻马店", "驻马店");
				break;
			case "湖北":
				var cityOptions = new Array("武汉(*)", "武汉", "恩施", "恩施", "鄂州", "鄂州", "黄冈", "黄冈", "黄石", "黄石", "荆门", "荆门",
						"荆州", "荆州", "潜江", "潜江", "十堰", "十堰", "随州", "随州", "武穴", "武穴", "仙桃", "仙桃", "咸宁", "咸宁", "襄阳", "襄阳",
						"襄樊", "襄樊", "孝感", "孝感", "宜昌", "宜昌");
				break;
			case "湖南":
				var cityOptions = new Array("长沙(*)", "长沙", "常德", "常德", "郴州", "郴州", "衡阳", "衡阳", "怀化", "怀化", "吉首", "吉首",
						"娄底", "娄底", "邵阳", "邵阳", "湘潭", "湘潭", "益阳", "益阳", "岳阳", "岳阳", "永州", "永州", "张家界", "张家界", "株洲",
						"株洲");
				break;
			case "江苏":
				var cityOptions = new Array("南京(*)", "南京", "常熟", "常熟", "常州", "常州", "海门", "海门", "淮安", "淮安", "江都", "江都",
						"江阴", "江阴", "昆山", "昆山", "连云港", "连云港", "南通", "南通", "启东", "启东", "沭阳", "沭阳", "宿迁", "宿迁", "苏州",
						"苏州", "太仓", "太仓", "泰州", "泰州", "同里", "同里", "无锡", "无锡", "徐州", "徐州", "盐城", "盐城", "扬州", "扬州", "宜兴",
						"宜兴", "仪征", "仪征", "张家港", "张家港", "镇江", "镇江", "周庄", "周庄");
				break;
			case "江西":
				var cityOptions = new Array("南昌(*)", "南昌", "抚州", "抚州", "赣州", "赣州", "吉安", "吉安", "景德镇", "景德镇", "井冈山",
						"井冈山", "九江", "九江", "庐山", "庐山", "萍乡", "萍乡", "上饶", "上饶", "新余", "新余", "宜春", "宜春", "鹰潭", "鹰潭");
				break;
			case "吉林":
				var cityOptions = new Array("长春(*)", "长春", "白城", "白城", "白山", "白山", "珲春", "珲春", "辽源", "辽源", "梅河", "梅河",
						"吉林", "吉林", "四平", "四平", "松原", "松原", "通化", "通化", "延吉", "延吉");
				break;
			case "辽宁":
				var cityOptions = new Array("沈阳(*)", "沈阳", "鞍山", "鞍山", "本溪", "本溪", "朝阳", "朝阳", "大连", "大连", "丹东", "丹东",
						"抚顺", "抚顺", "阜新", "阜新", "葫芦岛", "葫芦岛", "锦州", "锦州", "辽阳", "辽阳", "盘锦", "盘锦", "铁岭", "铁岭", "营口",
						"营口");
				break;
			case "内蒙古":
				var cityOptions = new Array("呼和浩特(*)", "呼和浩特", "阿拉善盟", "阿拉善盟", "包头", "包头", "赤峰", "赤峰", "东胜", "东胜",
						"海拉尔", "海拉尔", "集宁", "集宁", "临河", "临河", "通辽", "通辽", "乌海", "乌海", "乌兰浩特", "乌兰浩特", "锡林浩特", "锡林浩特");
				break;
			case "宁夏":
				var cityOptions = new Array("银川(*)", "银川", "固原", "固原", "石嘴山", "石嘴山", "吴忠", "吴忠");
				break;
			case "青海":
				var cityOptions = new Array("西宁(*)", "西宁", "德令哈", "德令哈", "格尔木", "格尔木", "共和", "共和", "海东", "海东", "海晏",
						"海晏", "玛沁", "玛沁", "同仁", "同仁", "玉树", "玉树");
				break;
			case "山东":
				var cityOptions = new Array("济南(*)", "济南", "滨州", "滨州", "兖州", "兖州", "德州", "德州", "东营", "东营", "菏泽", "菏泽",
						"济宁", "济宁", "莱芜", "莱芜", "聊城", "聊城", "临沂", "临沂", "蓬莱", "蓬莱", "青岛", "青岛", "曲阜", "曲阜", "日照", "日照",
						"泰安", "泰安", "潍坊", "潍坊", "威海", "威海", "烟台", "烟台", "枣庄", "枣庄", "淄博", "淄博");
				break;
			case "上海":
				var cityOptions = new Array("上海", "上海");
				break;
			case "山西":
				var cityOptions = new Array("太原(*)", "太原", "长治", "长治", "大同", "大同", "候马", "候马", "晋城", "晋城", "离石", "离石",
						"临汾", "临汾", "宁武", "宁武", "朔州", "朔州", "忻州", "忻州", "阳泉", "阳泉", "榆次", "榆次", "运城", "运城");
				break;
			case "陕西":
				var cityOptions = new Array("西安(*)", "西安", "安康", "安康", "宝鸡", "宝鸡", "汉中", "汉中", "渭南", "渭南", "商州", "商州",
						"绥德", "绥德", "铜川", "铜川", "咸阳", "咸阳", "延安", "延安", "榆林", "榆林");
				break;
			case "四川":
				var cityOptions = new Array("成都(*)", "成都", "巴中", "巴中", "达州", "达州", "德阳", "德阳", "都江堰", "都江堰", "峨眉山",
						"峨眉山", "广安", "广安", "广元", "广元", "九寨沟", "九寨沟", "康定", "康定", "乐山", "乐山", "泸州", "泸州", "马尔康", "马尔康",
						"绵阳", "绵阳", "眉山", "眉山", "南充", "南充", "内江", "内江", "攀枝花", "攀枝花", "遂宁", "遂宁", "汶川", "汶川", "西昌",
						"西昌", "雅安", "雅安", "宜宾", "宜宾", "自贡", "自贡", "资阳", "资阳");
				break;
			case "天津":
				var cityOptions = new Array("天津", "天津");
				break;
			case "新疆":
				var cityOptions = new Array("乌鲁木齐(*)", "乌鲁木齐", "阿克苏", "阿克苏", "阿勒泰", "阿勒泰", "阿图什", "阿图什", "博乐", "博乐",
						"昌吉", "昌吉", "东山", "东山", "哈密", "哈密", "和田", "和田", "喀什", "喀什", "克拉玛依", "克拉玛依", "库车", "库车", "库尔勒",
						"库尔勒", "奎屯", "奎屯", "石河子", "石河子", "塔城", "塔城", "吐鲁番", "吐鲁番", "伊宁", "伊宁");
				break;
			case "西藏":
				var cityOptions = new Array("拉萨(*)", "拉萨", "阿里", "阿里", "昌都", "昌都", "林芝", "林芝", "那曲", "那曲", "日喀则",
						"日喀则", "山南", "山南");
				break;
			case "云南":
				var cityOptions = new Array("昆明(*)", "昆明", "大理", "大理", "保山", "保山", "楚雄", "楚雄", "东川", "东川", "个旧", "个旧",
						"景洪", "景洪", "开远", "开远", "临沧", "临沧", "丽江", "丽江", "六库", "六库", "潞西", "潞西", "曲靖", "曲靖", "思茅", "思茅",
						"文山", "文山", "西双版纳", "西双版纳", "玉溪", "玉溪", "中甸", "中甸", "昭通", "昭通");
				break;
			case "浙江":
				var cityOptions = new Array("杭州(*)", "杭州", "安吉", "安吉", "慈溪", "慈溪", "定海", "定海", "奉化", "奉化", "海盐", "海盐",
						"黄岩", "黄岩", "湖州", "湖州", "嘉兴", "嘉兴", "金华", "金华", "临安", "临安", "临海", "临海", "丽水", "丽水", "宁波", "宁波",
						"瓯海", "瓯海", "平湖", "平湖", "千岛湖", "千岛湖", "衢州", "衢州", "江山", "江山", "瑞安", "瑞安", "绍兴", "绍兴", "嵊州",
						"嵊州", "台州", "台州", "温岭", "温岭", "温州", "温州", "舟山", "舟山");
				break;
			case "香港":
				var cityOptions = new Array("香港", "香港", "九龙", "九龙", "新界", "新界");
				break;
			case "澳门":
				var cityOptions = new Array("澳门", "澳门");
				break;
			case "台湾":
				var cityOptions = new Array("台北(*)", "台北", "基隆", "基隆", "台南", "台南", "台中", "台中", "新竹", "新竹", "嘉义", "嘉义",
						"高雄", "高雄", "金门县", "金门县", "南投县", "南投县", "台北县", "台北县", "宜兰县", "宜兰县", "新竹县", "新竹县", "桃园县", "桃园县",
						"苗栗县", "苗栗县", "台中县", "台中县", "彰化县", "彰化县", "嘉义县", "嘉义县", "云林县", "云林县", "台南县", "台南县", "高雄县",
						"高雄县", "屏东县", "屏东县", "台东县", "台东县", "花莲县", "花莲县", "澎湖县", "澎湖县", "其它", "其它");
				break;
			case "海外":
				var cityOptions = new Array("美国(*)", "美国", "英国", "英国", "法国", "法国", "瑞士", "瑞士", "澳洲", "澳洲", "新西兰",
						"新西兰", "加拿大", "加拿大", "奥地利", "奥地利", "韩国", "韩国", "日本", "日本", "德国", "德国", "意大利", "意大利", "西班牙",
						"西班牙", "俄罗斯", "俄罗斯", "泰国", "泰国", "印度", "印度", "荷兰", "荷兰", "新加坡", "新加坡", "其它国家", "其它国家");
				break;
			default:
				var cityOptions = new Array("", "");
				break;
			}
			citySelect.html("");
			for ( var i = 0; i < cityOptions.length / 2; i++) {
				citySelect.append($("<option></option>").val(cityOptions[i * 2 + 1]).html(cityOptions[i * 2]));
			}
			if (opts.currCity != "") {
				citySelect.val(opts.currCity);
			}
		}
		;

		if (opts.currCity != "") {
			initCitySelect();
		}

		$(this).append(provSelect);
		$(this).append("&nbsp;&nbsp;");
		$(this).append(citySelect);

		this.bind("keypress", function(e) {
		});
	};
});

function stripscript(s) {
	var pattern = new RegExp("[`~!@#$%^&*()=|{}':;',\\[\\].<>/?~！@#￥％……&*（）——|{}【】《》‘；：”“'。，、？]")
	var rs = "";
	for ( var i = 0; i < s.length; i++) {
		rs = rs + s.substr(i, 1).replace(pattern, '');
	}
	return rs;
}

jQuery.allSelect = function(opts) {

	opts = jQuery.extend( {
		buttonName : "",
		allCheckBoxId : "",
		listDivId : "",
		listCheckBoxName : ""
	}, opts || {});

	if (opts.allCheckBoxId != "") {
		$("#" + opts.allCheckBoxId).bind("click", function() {
			changeAll(true);
		});
	}

	if (opts.buttonName != null) {
		$('[name="' + opts.buttonName + '"]').each(function() {
			$(this).bind("click", function() {
				changeAll(false);
			});
		});
	}

	function changeAll(flag) {

		var isSelect = $("#" + opts.allCheckBoxId).attr("checked");
		if (!flag) {
			isSelect = !isSelect;
		}
		if (opts.listDivId != "") {
			$("#" + opts.listDivId).find('input[type="checkbox"]').each(function() {
				if (isSelect) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			});
		} else if (opts.listCheckBoxName != "") {

			$('input[name="itemQuery.ids"]').each(function() {
				if (isSelect) {
					$(this).attr("checked", true);
				} else {
					$(this).attr("checked", false);
				}
			});

			if (isSelect) {
				$("#" + opts.allCheckBoxId).attr("checked", true);
			} else {
				$("#" + opts.allCheckBoxId).attr("checked", false);
			}

		}
	}

};

$(function() {

	$.fn.addLessInput = function(opts) {
		opts = jQuery.extend( {
			addValue : "+",
			addClass : "",
			funOrder : false,
			interval : 1,
			lessValue : "-",
			lessClass : "",
			inputId : "0",
			inputName : "",
			inputClass : "",
			editable : false
		}, opts || {});

		var addLink = $("<a href='javascript:'></a>").html(opts.addValue);
		var delLink = $("<a href='javascript:'></a>").html(opts.lessValue);
		var input = $(
				"<input type='text' style='height:20px;line-height:20px;width:20px;'" + "align='center' maxlength='2'>")
				.attr("id", opts.inputId).addClass(opts.inputClass).bind("keypress", function(e) {
					var code = (e.keyCode ? e.keyCode : e.which);
					if (!$.browser.msie && (e.keyCode == 0x8)) {
						return;
					}
					return code >= 48 && code <= 57
				});
		;

		var nbsp = "";
		for ( var i = 0; i < opts.interval; i++) {
			nbsp += "&nbsp;"
		}

		if (opts.funOrder) {
			$(this).append(addLink);
			$(this).append(nbsp);
			$(this).append(input);
			$(this).append(nbsp);
			$(this).append(delLink);
		} else {
			$(this).append(delLink);
			$(this).append(nbsp);
			$(this).append(input);
			$(this).append(nbsp);
			$(this).append(addLink);
		}

	};
});

function initRichText(id) {
	KE.show( {
		id : id,
		resizeMode : 1,
		allowPreviewEmoticons : false,
		allowUpload : false,
		items : [ 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold', 'italic', 'underline', 'removeformat',
				'|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist', 'insertunorderedlist', '|', ,
				'emoticons', 'image', 'link' ],
		allowFileManager : true
	});
}

function isNullNumber(obj) {
	if (!isObj(obj) || obj == "" || !Number(obj)) {
		return false;
	}
	return true;
}

function isObj(obj) {
	if (obj == null || typeof (obj) == 'undefined') {
		return false;
	}
	return true;
}


$(function() {
	
	$.fn.addItemReleasedDate = function(opts) {
		
		opts = jQuery.extend( {
			radioName:"",
			yearName:"",
			hourName:"",
			minuteName:""
		}, opts || {});
		
		
		var radio1 = $("<input type='radio' class='nobo-input' name='itemInput.releasedType' value='1' >");
		var radio2 = $("<input type='radio' class='nobo-input' name='itemInput.releasedType' value='2' >");
		var radio3 = $("<input type='radio' class='nobo-input' name='itemInput.releasedType' value='3' >");
		
		
		var now = new Date();
		var selectStartDateYear = $("<select id='itemInput.releasedYear' name='itemInput.releasedYear'></select>");
		var selectStartDateHour = $("<select id='itemInput.releasedHour' name='itemInput.releasedHour'></select>");
		var selectStartDateMinute = $("<select id='itemInput.releasedMinute' name='itemInput.releasedMinute'></select>");
		for ( var i = 0; i < 21; i++) {
			now.setDate(now.getDate());
			var d = now.format("yyyy年MM月dd日");
			var v = now.format("yyyy-MM-dd");
			selectStartDateYear.append(
				$("<option></option>").attr("value",v).html(d)
			);
		}
		
		for(var i = 0;i<24;i++){
			selectStartDateHour.append(
				$("<option></option>").attr("value",i).html(i)
			);
		}
		
		for(var i = 0;i<60;i+=5){
			selectStartDateMinute.append(
				$("<option></option>").attr("value",i).html(i)
			);
		}
		
//		this.append($("<p></p>").append(radio1).append("&nbsp;立刻上架"));
//		this.append($("<p></p>").append(radio2)
//			.append("&nbsp;设定&nbsp;&nbsp;")
//			.append(selectStartDateYear)
//			.append("&nbsp;&nbsp;")
//			.append(selectStartDateHour)
//			.append("分&nbsp;&nbsp;")
//			.append(selectStartDateMinute)
//			.append("时&nbsp;&nbsp;")
//			.append("&nbsp;&nbsp;<span class=\"hui\">你可以设定商品的销售时间。</span>")
//		);
//		this.append($("<p></p>").append(radio3).append("&nbsp;放入仓库"));
		
//		this.append($("<p></p>").append(radio3).append("&nbsp;放入仓库"));
		
//		radio3.attr("checked","checked");
//			disabled(true);
			
		this.append($("<p></p>").append(radio1).append("&nbsp;立刻上架&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(radio3).append("&nbsp;放入仓库"));
		
		radio1.attr("checked","checked");
			disabled(true);
		
		radio1.click(function(){
			disabled(true);
		})
		radio2.click(function(){	
			disabled(false);
		})
		radio3.click(function(){
			disabled(true);
		})
		
		function disabled(flag){
			if(flag){
//				document.getElementById("itemInput.releasedYear").disabled="disabled";
//				document.getElementById("itemInput.releasedHour").disabled="disabled";
//				document.getElementById("itemInput.releasedMinute").disabled="disabled";
//				selectStartDateYear.attr("disabled",flag);
//				selectStartDateHour.attr("disabled",flag);
//				selectStartDateMinute.attr("disabled",flag);
			}else{
//				document.getElementById("itemInput.releasedYear").disabled="";
//				document.getElementById("itemInput.releasedHour").disabled="";
//				document.getElementById("itemInput.releasedMinute").disabled="";
//				selectStartDateYear.removeAttr("disabled");
//				selectStartDateHour.removeAttr("disabled");
//				selectStartDateMinute.removeAttr("disabled");
			}
		}
		
	};
});

/**
 * 复制内容到剪切板
 * 
 * @param {Object} t
 * @return {TypeName} 
 */
function copytoclip(t) {
	if (window.clipboarddata) {
		window.clipboarddata.setdata("text", t);
	} else if (window.netscape) {
		try {
			netscape.security.privilegemanager.enableprivilege('universalxpconnect');
		} catch (e) {
			alert("about:config -> signed.applets.codebase_principal_support=false");
			return;
		}
		var clip = components.classes['@mozilla.org/widget/clipboard;1']
				.createinstance(components.interfaces.nsiclipboard);
		if (!clip) {
			return;
		}
		var trans = components.classes['@mozilla.org/widget/transferable;1']
				.createinstance(components.interfaces.nsitransferable);
		if (!trans) {
			return;
		}
		trans.adddataflavor('text/unicode');
		var str = new object();
		var len = new object();
		var str = components.classes["@mozilla.org/supports-string;1"]
				.createinstance(components.interfaces.nsisupportsstring);
		var copytext = t;
		str.data = copytext;
		trans.settransferdata("text/unicode", str, copytext.length * 2);
		var clipid = components.interfaces.nsiclipboard;
		if (!clip) {
			return false;
		}
		clip.setdata(trans, null, clipid.kglobalclipboard);
	}
	return;
}

String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
}

/**
 * json转换为字符串
 * @param {Object} o
 * @return {TypeName} 
 */
function JsonToStr(o) {
	var arr = [];
	var fmt = function(s) {
		if (typeof s == 'object' && s != null)
			return JsonToStr(s);
		return /^(string|number)$/.test(typeof s) ? "'" +  s.replace(/(\')|(\\')/g, "\\\'") + "'" : s;
	}
	for ( var i in o)
		//alert(o[i]);
		arr.push("'" + i + "':" + fmt(o[i]));
	return '{' + arr.join(',') + '}';
}

function getLength(str) {
    var len = str.length;
    var reLen = 0;
    for (var i = 0; i < len; i++) {       
        if (str.charCodeAt(i) < 27 || str.charCodeAt(i) > 126) {
            // 全角   
            reLen += 2;
        } else {
            reLen++;
        }
    }
    return reLen;   
}

function noticelist(pcode, ptypedsc, sontype, sontypetitle) {
	window.open(encodeURI(encodeURI("notice/noticelistaction?ptypedsc=" + ptypedsc + "&&notice.ptypeid=" + pcode
			+ "&&notice.sontype.id=" + sontype + "&&notice.sontype.title=" + sontypetitle)));
}

function copyToClipboard(txt) {
	if(txt.indexOf(window.location.host)== -1){
		txt = window.location.host+txt;
	}
	if (window.clipboardData) {
		window.clipboardData.clearData();
		window.clipboardData.setData("Text", txt);
	} else if (navigator.userAgent.indexOf("Opera") != -1) {
		window.location = txt;
	} else if (window.netscape) {
		try {
			netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
		} catch (e) {
			alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将'signed.applets.codebase_principal_support'设置为'true'");
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1']
				.createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
			return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1']
				.createInstance(Components.interfaces.nsITransferable);
		if (!trans)
			return;
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"]
				.createInstance(Components.interfaces.nsISupportsString);
		var copytext = txt;
		str.data = copytext;
		trans.setTransferData("text/unicode", str, copytext.length * 2);
		var clipid = Components.interfaces.nsIClipboard;
		if (!clip)
			return false;
		clip.setData(trans, null, clipid.kGlobalClipboard);
		alert("复制成功！")
	}
}

  	  //毫秒转换为YYYY.MM.DD HH:mm:ss 格式
  	  function timeMillisToDate(timeMillis){
  	  		var date = new Date(timeMillis);
  	  		var year=date.getFullYear().toString();
  	  		var month=("0"+(date.getMonth()+1)).slice(-2);
  	  		var day=("0"+date.getDate().toString()).slice(-2);
  	  		var hour=("0"+date.getHours()).slice(-2);
  	  		var minutes=("0"+date.getMinutes()).slice(-2);
  	  		var seconds=("0"+date.getSeconds()).slice(-2);
  	  		//var milliseconds=date.getMilliseconds()
  	  		var time=year+"."+month+"."+day+" "+hour+":"+minutes+":"+seconds;
  	  		return time;
  	  }
  	  
  	  //需要替换的HTML
  	  var HTML_REPLACE=[
  	  		[/&/g,'&amp;'],
  	  		[/</g,'&lt;'],
  	  		[/>/g,'&gt;'],
  	  		[/\"/g,'&quot;'],
  	  		[/ /g,'&nbsp;'],
  	  		[/\r\n|\n|\r/g,'<br/>']
  	  	];
  	  
  	  /**
  	   * 替换所有HTML标签
  	   * @param {需要替换的html} str
  	   * @return {替换后的html}
  	   */
  	  function replaceHTMLTag(str) {
  	  		for(i=0;i<HTML_REPLACE.length;i++){
  	  				 str = str.replace(HTML_REPLACE[i][0],HTML_REPLACE[i][1]);
  	  			}
		          // str = str.replace(/&/g,'&amp;');
		          // str = str.replace(/</g,'&lt;');
		          // str = str.replace(/>/g,'&gt;');
		          // str = str.replace(/\"/g,'&quot;');
	       return str;
    }
    
     //去除所有HTML标签
  	  function removeHTMLTag(str) {
            str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
            str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
            return str;
    }