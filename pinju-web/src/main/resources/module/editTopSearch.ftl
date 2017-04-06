
		<!--===模块编辑——页头搜索内容，开始-->
		<form name="form" action="saveDesignModuleAction.htm" method="post" onsubmit="return doSubmit();">
		<div class="lay_content">
			<ul class="lister">
				<li class="lister_title"><cite>编辑</cite><span>关键字</span></li>
				<#if searchWords?exists>
				<#list searchWords?split(",") as word>
				<li class="kw-row">
					<cite>
						<a class="move-up" href="#"><img src="http://static.pinju.com/images/shop/sc-aroup-11.gif"></a>
						<a class="move-down" href="#"><img src="http://static.pinju.com/images/shop/sc-arodown-11.gif"></a>
						<a href="#"><img class="delete-kw" src="http://static.pinju.com/images/shop/sc-delete-11.gif"></a>
					</cite>
					<span><input type="text" name="searchWords" class="txt" value="${word}"></span>
				</li>
				</#list>
				</#if>
				<li class="add_button">
					<input type="hidden" name="id" value="<#if _ID??>${_ID?string("0")}</#if>" />
					<input type="hidden" name="moduleId" value="5" />
					<input type="hidden" name="keep" value="0" />
					<input type="hidden" name="userPageId" value="${_USERPAGEID?string("0")}" />
					<input id="AddKeyword" class="cancel" type="button" value="添加">
				</li>
			</ul>			
			<ul class="btn-weiz">
				<input class="save" type="submit" value="保存"><input class="cancel" type="button" value="取消" onclick="window.parent.dialog.close();" />
			</ul>
		</div>
		</form>
		<textarea id="KeywordTemplate" style="display:none">
				<li class="kw-row">
					<cite>
						<a class="move-up" href="#"><img src="http://static.pinju.com/images/shop/sc-aroup-11.gif"></a>
						<a class="move-down" href="#"><img src="http://static.pinju.com/images/shop/sc-arodown-11.gif"></a>
						<a href="#"><img class="delete-kw" src="http://static.pinju.com/images/shop/sc-delete-11.gif"></a>
					</cite>
					<span><input type="text" name="searchWords" class="txt" value=""></span>
				</li>
		</textarea>
		<!--===模块编辑——页头搜索内容，结束-->
<script type="text/javascript">
function doSubmit(){
	var _txt=$('.kw-row .txt');
	for(var i=0;i<_txt.size();i++) {
		if(!checkKeywords(_txt.eq(i))) return false;
	}
	return true;
}
$('.delete-kw').click(function() {
	$(this).closest('.kw-row').remove();
});
$('#AddKeyword').click(function() {
	var count = 0;
	$(".kw-row").each(function(){
		count = count + 1;
	});
	if(count >= 6){
		alert("对不起最多只能添加6条关键词!");
		return;
	}
	var obj = $($('#KeywordTemplate').val());
	obj.find('.move-up').click(function() {
	   searchMoveUp($(this));
	});
	obj.find('.move-down').click(function() {
	  searchMoveDown($(this));
	});
	obj.find('.delete-kw').click(function() {
		obj.remove();
	});
	obj.find('.txt').blur(function(){
		checkKeywords($(this));
	});
	$('#AddKeyword').closest('li').before(obj);
});
function trim(str){   
	return	str.replace(/^\s+|\s+$/g,"");   
}
function checkKeywords(target) {
	var _name = target.val();
	var _conflict = 0;
	if (trim(_name) == '') {
		alert('搜索关键字不能为空');
		return false;
	}
    $('.kw-row .txt').each(function() {
        if ($(this).val() == _name) {
            _conflict++;
        }
    });
    if (_conflict > 1) {
        alert('页面名称不能相同');
        return false;
    }
    return true;
};
function searchMoveUp(target){
	var _obj = target.closest('.kw-row');
	                if ($('.kw-row').index(_obj) > 0) {
	                    _obj.prev().before(_obj);
	                }
	                else {
	                    alert('已经是第一个页面，无法继续向前移动');
	                    return false;
	                }
	                
}

function searchMoveDown(target){
	var _obj = target.closest('.kw-row');
	                if ($('.kw-row').index(_obj) < $('.kw-row').size() - 1) {
	                    _obj.next().after(_obj);
	                }
	                else {
	                    alert('已经是最后一个页面，无法继续向前移动');
	                    return false;
	                }
}

$('.kw-row .move-up').click(function() {
	searchMoveUp($(this));
});
$('.kw-row .move-down').click(function() {
	searchMoveDown($(this));
});
$('.kw-row .txt').blur(function(){
	checkKeywords($(this));
});
</script>