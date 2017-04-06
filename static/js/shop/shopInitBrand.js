var ENCODE = [
  {reg: /&/g, rep: '&amp;'},
  {reg: /</g, rep: '&lt;'},
  {reg: />/g, rep: '&gt;'},
  {reg: /"/g, rep: '&quot;'},
  {reg: / /g, rep: '&nbsp;'}
];

function encode(s) {
  if (!s) return null;
  for (var i = 0; i < ENCODE.length; i++) {
    s = s.replace(ENCODE[i]['reg'], ENCODE[i]['rep']);
  }
  return s;
}
function addShopInfoToCookieStep() {	
	bindInputEventToForm($('#shopInfoForm'), function(){
		var trademarkNumber = "";
		var brandName = "";
		$(".kd-tjlist").each(function(){
			var brandNameEach = $.trim($(this).find(".brandName").val());
			var trademarkNumberEach =$.trim( $(this).find(".trademarkNumber").val());
			if(brandNameEach){
				trademarkNumber += encodeURIComponent(trademarkNumberEach)+"|";
			}else{
				trademarkNumber += ""+"|";
			}
			if(trademarkNumberEach){
				brandName += encodeURIComponent(brandNameEach)+"|";
			}else{
				brandName += ""+"|";
			}
			
		});
		trademarkNumber = "trademarkNumber="+trademarkNumber;
		brandName = "brandName="+brandName;
		trademarkNumber = trademarkNumber.substring(0,trademarkNumber.length-1);
		brandName = brandName.substring(0,brandName.length-1);
		
		var strCookie = trademarkNumber + "," + brandName ;
		PinjuCookie.writeCookie('PJ3', strCookie, 'www.pinju.com', '/shop', 3);
	});
}

$(document)
		.ready(
				function() {
					var trademarkNumber = $("#trademarkNumberHidden").val();

					var brandName = $("#brandNameHidden").val();
					var brandEnglishName = $("#brandEnglishNameHidden").val();
					var brandStory = $("#brandStoryHidden").val();

					var trademarkNumberArr;
					var brandNameArr;
					var brandEnglishNameArr;
					var brandStoryArr;

					if (trademarkNumber) {
						trademarkNumberArr = trademarkNumber.split("|");

					}
					if (brandName) {
						brandNameArr = brandName.split("|");
					}
					if (brandEnglishName) {
						brandEnglishNameArr = brandEnglishName.split("|");
					}
					if (brandStory) {
						brandStoryArr = brandStory.split("|");
					}
					if ((trademarkNumberArr && trademarkNumberArr.length > 0 )
						|| (brandNameArr && brandNameArr.length>0 )
						//&& brandEnglishNameArr && brandEnglishNameArr.length>0 
						//&& brandStoryArr && brandStoryArr.length>0
						) {
						var count1 = 0;
						var count2 = 0;
						if(trademarkNumberArr && trademarkNumberArr.length > 0){
							count1 = trademarkNumberArr.length;
						}
						if(brandNameArr && brandNameArr.length > 0){
							count2 = brandNameArr.length;
						}
						if(count1 == count2){
							brandInfoCount = count1;
						}else if(count1 > count2){
							brandInfoCount = count1;
						}else{
							brandInfoCount = count2;
						}
						//brandInfoCount = trademarkNumberArr.length;
						
						for ( var i = 0; i < brandInfoCount; i++) {
							seqBrand = seqBrand + 1;
							var trnumber = "";
							var bname = "";
							var bename = "";
							var bstory = "";
							if(trademarkNumberArr && i <= trademarkNumberArr.length){
								trnumber = trademarkNumberArr[i];
							}else{
								trnumber = "";
							}
							if(brandNameArr && i <= brandNameArr.length){
								bname = brandNameArr[i];
							}else{
								bname = "";
							}
							if(brandEnglishNameArr && i <= brandEnglishNameArr.length){
								bename = brandEnglishNameArr[i];
							}else{
								bename = "";
							}
							if(brandStoryArr && i <= brandStoryArr.length){
								bstory = brandStoryArr[i];
							}else{
								bstory = "";
							}
							$("#brandInfo")
									.append(
											"<ul id='brandInfoUl' class='kd-tjlist'><table class='info-brand'>"
													+ "<tr><th><font color=red> * </font>商标注册证号（申请号）</th>"
													+ "<td><input type='text' id='trademarkNumber' class='trademarkNumber' onblur=checkBrandSp('trademarkNumber')"
													+ ""
													+ " value='"
													+ encode(trnumber)
													+ "'/><span id='trademarkNumbertip' class='trademarkNumbertip'></span></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌名称</th>"
													+ "<td><input type='text' id='brandName' class='brandName' onblur=checkBrandSp('brandName')"
													+ ""
													+ " value='"
													+ encode(bname)
													+ "'/><span id='brandNametip' class='brandNametip'></span><p></p></td></tr>"
													+ "<tr><th>品牌英文名</th>"
													+ "<td><input type='text' id='brandEnglishName' class='brandEnglishName' onblur=checkBrandSp('brandEnglishName')"
													+ ""
													+ " value='"
													+ encode(bename)
													+ "'/><span id='brandEnglishNametip' class='brandEnglishNametip'></span><p>只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</p></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌logo</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"1' class='myFile1' onchange=checkBrandSp('myFile','"+seqBrand+"1');checkBrandImage('myFile','"+seqBrand+"1') /><span id='myFilehui' class=myFilehui"+seqBrand+"1></span><p>文件宽度不大于500PX，文件大小80K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌故事</th>"
													+ "<td><textarea cols='40' rows='5' id='brandStory' class='brandStory' onblur=checkBrandSp('brandStory')"
													+ ""
													+ "> "
													+ encode(bstory)
													+ "</textarea><span id='brandStorytip' class='brandStorytip'></span><p>不能超过500个字符!</p></td></tr>"
													+ ""
													+ "<tr><th><font color=red> * </font>品牌授权书或品牌商标注册证书</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"2' class='myFile2' onchange=checkBrandSp('myFile','"+seqBrand+"2');checkBrandImage('myFile','"+seqBrand+"2') /><span id='myFilehui' class=myFilehui"+seqBrand+"2></span><p>文件宽度不大于500PX，文件大小500K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><th><font color=red> * </font>商品质量检验证书</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"3' class='myFile3' onchange=checkBrandSp('myFile','"+seqBrand+"3');checkBrandImage('myFile','"+seqBrand+"3') /><span id='myFilehui' class=myFilehui"+seqBrand+"3></span><p>文件宽度不大于500PX，文件大小500K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+"<tr><td><cite><a href='javascript:void(0)'  class='delBrand'> 删除品牌</a></cite></td></tr></table></ul>");
						}
					} else {
						
						$("#brandInfo")
									.append(
											"<ul id='brandInfoUl' class='kd-tjlist'><table class='info-brand'>"
													+ "<tr><th><font color=red> * </font>商标注册证号（申请号）</th>"
													+ "<td><input type='text' id='trademarkNumber' class='trademarkNumber' onblur=checkBrandSp('trademarkNumber')"
													+ ""
													+ " value=''/><span id='trademarkNumbertip' class='trademarkNumbertip'></span></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌名称</th>"
													+ "<td><input type='text' id='brandName' class='brandName' onblur=checkBrandSp('brandName')"
													+ ""
													+ " value=''/><span id='brandNametip' class='brandNametip'></span><p></p></td></tr>"
													+ "<tr><th>品牌英文名</th>"
													+ "<td><input type='text' id='brandEnglishName' class='brandEnglishName' onblur=checkBrandSp('brandEnglishName')"
													+ ""
													+ " value=''/><span id='brandEnglishNametip' class='brandEnglishNametip'></span><p>只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</p></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌logo</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"1' class='myFile1' onchange=checkBrandSp('myFile','"+seqBrand+"1');checkBrandImage('myFile','"+seqBrand+"1') /><span id='myFilehui' class=myFilehui"+seqBrand+"1></span><p>文件宽度不大于500PX，文件大小80K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌故事</th>"
													+ "<td><textarea cols='40' rows='5' id='brandStory' class='brandStory' onblur=checkBrandSp('brandStory')"
													+ ""
													+ "> "
													+ "</textarea><span id='brandStorytip' class='brandStorytip'></span><p>不能超过500个字符!</p></td></tr>"
													+ ""
													+ "<tr><th><font color=red> * </font>品牌授权书或品牌商标注册证书</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"2' class='myFile2' onchange=checkBrandSp('myFile','"+seqBrand+"2');checkBrandImage('myFile','"+seqBrand+"2') /><span id='myFilehui' class=myFilehui"+seqBrand+"2></span><p>文件宽度不大于500PX，文件大小500K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><th><font color=red> * </font>商品质量检验证书</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"3' class='myFile3' onchange=checkBrandSp('myFile','"+seqBrand+"3');checkBrandImage('myFile','"+seqBrand+"3') /><span id='myFilehui' class=myFilehui"+seqBrand+"3></span><p>文件宽度不大于500PX，文件大小500K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><td><cite><a href='javascript:void(0)'  class='delBrand'> 删除品牌</a></cite></td></tr></table></ul>");

					}

					
					
					bindDelBrand();
					
					addShopInfoToCookieStep();
				});


function bindDelBrand(){
	$(".delBrand").click(function(){
		$(this).closest('.kd-tjlist').remove();
		//brandInfoCount = brandInfoCount - 1;
	});
}


function addBrandInfo() {
	brandInfoCount = brandInfoCount + 1;
	seqBrand = seqBrand + 1;
	var i = 0;
	$(".kd-tjlist").each(function(){
		i = i + 1;
	});
	if(i == 6){
		alert("对不起,您最多只能添加6个品牌!");
		return;
	}
$("#brandInfo")
									.append(
											"<ul id='brandInfoUl' class='kd-tjlist'><table class='info-brand'>"
													+ "<tr><th><font color=red> * </font>商标注册证号（申请号）</th>"
													+ "<td><input type='text' id='trademarkNumber' class='trademarkNumber' onblur=checkBrandSp('trademarkNumber')"
													+ ""
													+ " value=''/><span id='trademarkNumbertip' class='trademarkNumbertip'></span></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌名称</th>"
													+ "<td><input type='text' id='brandName' class='brandName' onblur=checkBrandSp('brandName')"
													+ ""
													+ " value=''/><span id='brandNametip' class='brandNametip'></span><p></p></td></tr>"
													+ "<tr><th>品牌英文名</th>"
													+ "<td><input type='text' id='brandEnglishName' class='brandEnglishName' onblur=checkBrandSp('brandEnglishName')"
													+ ""
													+ " value=''/><span id='brandEnglishNametip' class='brandEnglishNametip'></span><p>只能输入英文、数字或部分特殊字符(如: !@#$^&*=_+()?<>等 )！(选填)</p></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌logo</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"1' class='myFile1' onchange=checkBrandSp('myFile','"+seqBrand+"1');checkBrandImage('myFile','"+seqBrand+"1') /><span id='myFilehui' class=myFilehui"+seqBrand+"1></span><p>文件宽度不大于500PX，文件大小80K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><th><font color=red> * </font>品牌故事</th>"
													+ "<td><textarea cols='40' rows='5' id='brandStory' class='brandStory' onblur=checkBrandSp('brandStory')"
													+ ""
													+ "> "
													+ "</textarea><span id='brandStorytip' class='brandStorytip'></span><p>不能超过500个字符!</p></td></tr>"
													+ ""
													+ "<tr><th><font color=red> * </font>品牌授权书或品牌商标注册证书</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"2' class='myFile2' onchange=checkBrandSp('myFile','"+seqBrand+"2');checkBrandImage('myFile','"+seqBrand+"2') /><span id='myFilehui' class=myFilehui"+seqBrand+"2></span><p>文件宽度不大于500PX，文件大小500K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><th><font color=red> * </font>商品质量检验证书</th>"
													+ "<td class='picture-up'><input type='file' name='myFile' id='myFile"+seqBrand+"3' class='myFile3' onchange=checkBrandSp('myFile','"+seqBrand+"3');checkBrandImage('myFile','"+seqBrand+"3') /><span id='myFilehui' class=myFilehui"+seqBrand+"3></span><p>文件宽度不大于500PX，文件大小500K以内，  请使用jpg、gif、png格式</p></td></tr>"
													+ "<tr><td><cite><a href='javascript:void(0)'  class='delBrand'> 删除品牌</a></cite></td></tr></table></ul>");
															

	bindDelBrand();
	
	addShopInfoToCookieStep();
}


