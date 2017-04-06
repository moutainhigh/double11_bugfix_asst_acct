<title>${shopName}</title>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css" />
<link href="http://static.pinju.com/css/jquery.colourPicker.css" rel="stylesheet" />
<script src="http://static.pinju.com/kindeditor/kindeditor-min.js"></script>
<script src="http://static.pinju.com/kindeditor/config.js"></script>
<script src="http://static.pinju.com/kindeditor/lang/zh_CN.js"></script>
<script>
	KindEditor.ready(function(K) {
		var editor = K.editor({
			uploadJson : '/image/uploadImage.htm?size=1024&type=2',
			fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
			categoryJson : '/images/getImagesCategoryJsonActon.htm',
			readonlyImageUrl : true,
			allowFileManager : true
		});
		K('#image').click(function() {
			editor.loadPlugin('image', function() {
				editor.plugin.imageDialog({
					imageUrl : K('#url').val(),
					clickFn : function(url, title, width, height, border, align) {
						K('#url').val(url);
						editor.hideDialog();
						$('#backgroundimage').attr('src',$('#url').val());
					}
				});
			});
		});
	});
</script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.colourPicker.js"></SCRIPT>
<#include "/default/templates/layout/site-nav.ftl">
<div id="EditorTopMenu">
            <div id="EditorTopMenuInner"></div>
        </div>
        <div id="EditorNav" style="overflow:hidden">
            <div id="EditorNavInner">
                <a href="#" id="PrimaryLogo"></a>
            <div class="shopzx-top">
            <ul class="shopzx-left" >
            <li ><a href="shopDecorationAction.htm">编辑</a></li>
            <li ><a href="getEditCustomerPageAction.htm">页面</a></li>
            <li class="shopzx-left-selected"><a href="shopTemplateManagerAction.htm">模板</a></li>
            <li><a href="showIndexLayoutManagementAction.htm">布局</a></li>
            </ul>
            <ul class="shopzx-right" >
            <li><a target="_blank" href="http://service.pinju.com/cms/html/2011/teach_0825/52.html">装修帮助</a></li>
            <li><a target="_blank" href="http://shop${shopId?if_exists}.pinju.com">查看店铺</a></li>
            <li><a target="_blank" href="restoreDesignAction.htm">还原</a></li>
            <li><input type="button" value="预览" class="but-juse" onclick="Javascript:window.open('shopDecorationAction.htm?preview=true')"/></li>
            <li><input type="button" value="发布" class="but-juse" onclick="Javascript:window.open('releaseDesignAction.htm')"/></li>
            </ul>
            </div>
            </div>
        </div>
		<!--==================【页中模块组开始】-->
		<form method="post" action="saveShopTemplateAction.htm" >
		<input id="templateColor" name="templateColor" value="" type="hidden"/>
		<div class="wrap"><!--【页中模块组容器】-->       
			<!--====【左侧颜色开始】-->
		<div class="mb-color">
		<p>更换模板颜色</p>
		<div class="mb-border">
		<ul class="mb-co">
		<li><a href="javascript:flushTemplate('red');" title="红色"><div class="mb-co-red"></div></a></li>
		<li><a href="javascript:flushTemplate('green');" title="绿色"><div class="mb-co-green"></div></a></li>
		<li><a href="javascript:flushTemplate('blue');" title="蓝色"><div class="mb-co-blue"></div></a></li>
		<li><a href="javascript:flushTemplate('gray');" title="灰色"><div class="mb-co-gray"></div></a></li>
		<li><a href="javascript:flushTemplate('purple');" title="紫色"><div class="mb-co-purple"></div></a></li>
		</ul>
		<ul >
		<li><div id="redTemplate" style="display:none"><img src="http://static.pinju.com/images/shop/mb-back-red.gif" alt="当前模板"/></div>
		<div id="greenTemplate" style="display:none"><img src="http://static.pinju.com/images/shop/mb-back-green.gif" alt="当前模板"/></div>
		<div id="blueTemplate" style="display:none"><img src="http://static.pinju.com/images/shop/mb-back-blue.gif" alt="当前模板"/></div>
		<div id="grayTemplate" style="display:none"><img src="http://static.pinju.com/images/shop/mb-back-gray.gif" alt="当前模板"/></div>
		<div id="purpleTemplate" style="display:none"><img src="http://static.pinju.com/images/shop/mb-back-purple.gif" alt="当前模板"/></div>
		</li>
		<li><input type="button" value="预览" class="mb-co-btn" /></li>
		</ul>
		</div>
		</div>	
			<!--=========【左侧颜色结束】-->			
			<!--=========【右侧当前模板开始】-->
		<div class="mb-back">
		<p>页面整体背景</p>
		<div class="mb-changbj">
		<ul class="mb-chmain">
		<li>
			<span class="mb-chleft">背景色</span>
			<div id="pickColor" class="pickcolor">
					<select name="colour">
						<option value="ffffff">#ffffff</option>
						<option value="ffccc9">#ffccc9</option>
						<option value="ffce93">#ffce93</option>
						<option value="fffc9e">#fffc9e</option>
						<option value="ffffc7">#ffffc7</option>
						<option value="9aff99">#9aff99</option>
						<option value="96fffb">#96fffb</option>
						<option value="cdffff">#cdffff</option>
						<option value="cbcefb">#cbcefb</option>
						<option value="cfcfcf">#cfcfcf</option>
						<option value="fd6864">#fd6864</option>
						<option value="fe996b">#fe996b</option>
						<option value="fffe65">#fffe65</option>
						<option value="fcff2f">#fcff2f</option>
						<option value="67fd9a">#67fd9a</option>
						<option value="38fff8">#38fff8</option>
						<option value="68fdff">#68fdff</option>
						<option value="9698ed">#9698ed</option>
						<option value="c0c0c0">#c0c0c0</option>
						<option value="fe0000">#fe0000</option>
						<option value="f8a102">#f8a102</option>
						<option value="ffcc67">#ffcc67</option>
						<option value="f8ff00">#f8ff00</option>
						<option value="34ff34">#34ff34</option>
						<option value="68cbd0">#68cbd0</option>
						<option value="34cdf9">#34cdf9</option>
						<option value="6665cd">#6665cd</option>
						<option value="9b9b9b">#9b9b9b</option>
						<option value="cb0000">#cb0000</option>
						<option value="f56b00">#f56b00</option>
						<option value="ffcb2f">#ffcb2f</option>
						<option value="ffc702">#ffc702</option>
						<option value="32cb00">#32cb00</option>
						<option value="00d2cb">#00d2cb</option>
						<option value="3166ff">#3166ff</option>
						<option value="6434fc">#6434fc</option>
						<option value="656565">#656565</option>
						<option value="9a0000">#9a0000</option>
						<option value="ce6301">#ce6301</option>
						<option value="cd9934">#cd9934</option>
						<option value="999903">#999903</option>
						<option value="009901">#009901</option>
						<option value="329a9d">#329a9d</option>
						<option value="3531ff">#3531ff</option>
						<option value="6200c9">#6200c9</option>
						<option value="343434">#343434</option>
						<option value="680100">#680100</option>
						<option value="963400">#963400</option>
						<option value="986536">#986536</option>
						<option value="646809">#646809</option>
						<option value="036400">#036400</option>
						<option value="34696d">#34696d</option>
						<option value="00009b">#00009b</option>
						<option value="303498">#303498</option>
						<option value="000000">#000000</option>
						<option value="330001">#330001</option>
						<option value="643403">#643403</option>
						<option value="663234">#663234</option>
						<option value="343300">#343300</option>
						<option value="013300">#013300</option>
						<option value="003532">#003532</option>
						<option value="010066">#010066</option>
						<option value="340096">#340096</option>
					</select>
				</div>
		</li>
		<!--
		<li><span class="mb-chleft">背景色</span>
		<div class="mb-co-red  right10"></div>
		<div class="mb-yanse">
		
		<input type="text" maxlength="6" class="input_m"><p>请输入6位颜色值</p></div><input type="submit"  class="cancel" value="确定">
		</li>
		-->
		<li>
			<span class="mb-chleft">背景图片</span>
			<input type="text" readonly id="url" name="url" value="${url!''}" /><input id="image" name="imageFile" type="button" value="选择图片">
		</li>
		<li>
			<span class="mb-chleft">&nbsp;</span>
			<img id="backgroundimage" name="backgroundimage" height="125" width="125" src="<#if url?exists && url !=''>${url!''}<#else>http://static.pinju.com/images/shop/mb-back1.gif</#if>" style="margin-right:10px"/><a href="javascript:clearimage()" class="blue">清空背景图片</a>
		</li>		
		<li><span class="mb-chleft">背景效果</span>
			<select name="backgroundeffect">
				<option value="repeat" <#if backgroundeffect?exists && backgroundeffect == 'repeat'>selected</#if>>平铺</option>
				<option value="no-repeat" <#if backgroundeffect?exists && backgroundeffect == 'no-repeat'>selected</#if>>不重复</option>
				<option value="repeat-x" <#if backgroundeffect?exists && backgroundeffect == 'repeat-x'>selected</#if>>横向平铺</option>
				<option value="repeat-y" <#if backgroundeffect?exists && backgroundeffect == 'repeat-y'>selected</#if>>纵向平铺</option>
			</select>
		</li>
		<li><span class="mb-chleft">背景对齐</span>
			<select name="backgroundtype">
				<option value="left center" <#if backgroundtype?exists && backgroundtype == 'left center'>selected</#if>>左对齐</option>
				<option value="right center" <#if backgroundtype?exists && backgroundtype == 'right center'>selected</#if>>右对齐</option>
				<option value="center center" <#if backgroundtype?exists && backgroundtype == 'center center'>selected</#if>>居中</option>
				<option value="center top" <#if backgroundtype?exists && backgroundtype == 'center top'>selected</#if>>上对齐</option>
				<option value="center bottom" <#if backgroundtype?exists && backgroundtype == 'center bottom'>selected</#if>>底端对齐</option>
			</select>
		</li>
		</ul></div>
		</div>			
		<!--========【右侧当前模板结束】-->
        <div class="center">
        <input type="submit" value="保存" class="save"/>
        </div>
        </div>
        </form>
<script type="text/javascript">
function flushTemplate(template){
	if(template=='red'){
		$('#redTemplate').css('display','block');
		$('#greenTemplate').css('display','none');
		$('#blueTemplate').css('display','none');
		$('#grayTemplate').css('display','none');
		$('#purpleTemplate').css('display','none');
		
		$('#templateColor').val('red');
	}else if(template=='green'){
		$('#redTemplate').css('display','none');
		$('#greenTemplate').css('display','block');
		$('#blueTemplate').css('display','none');
		$('#grayTemplate').css('display','none');
		$('#purpleTemplate').css('display','none');
		
		$('#templateColor').val('green');
	}else if(template=='blue'){
		$('#redTemplate').css('display','none');
		$('#greenTemplate').css('display','none');
		$('#blueTemplate').css('display','block');
		$('#grayTemplate').css('display','none');
		$('#purpleTemplate').css('display','none');
		
		$('#templateColor').val('blue');
	}else if(template=='gray'){
		$('#redTemplate').css('display','none');
		$('#greenTemplate').css('display','none');
		$('#blueTemplate').css('display','none');
		$('#grayTemplate').css('display','block');
		$('#purpleTemplate').css('display','none');
		
		$('#templateColor').val('gray');
	}else if(template=='purple'){
		$('#redTemplate').css('display','none');
		$('#greenTemplate').css('display','none');
		$('#blueTemplate').css('display','none');
		$('#grayTemplate').css('display','none');
		$('#purpleTemplate').css('display','block');
		
		$('#templateColor').val('purple');
	}
	
}
function clearimage(){
	$('#url').val('');
	$('#backgroundimage').attr('src','http://static.pinju.com/images/shop/mb-back1.gif');
}
flushTemplate('${template}');

$(function() {
	$('#pickColor select').colourPicker();
});
$('#pickColor select').val('${colour?if_exists}')
</script>
 <#include "/default/templates/layout/footer.ftl">