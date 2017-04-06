<title>店铺分类管理</title>
<link href="http://static.pinju.com/css/sell.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/category.js"></SCRIPT>
    <div class="shop-set">  
    	<h3><strong>店铺分类管理</strong></h3>
        <ul class="tab-main">
        	<li class="count"><a href="javascript:void(0);">编辑分类</a></li>
        	<li><a href="${base}/shop/itemCategorize.htm">商品归类</a></li>
        </ul>
  		<#if result?exists&&result=="success"><div class="tips-pop"><p>保存成功！</p></div>
  		<script>
			$(function() {
			setTimeout(function(){
				$(".tips-pop").fadeOut('slow');
			}, 3000);
			
	 		});
		</script>
  		</#if>
  		
  <form style="margin:0;padding:0;" method="post" action="insertShopCategory.htm" name="CatelogEditor" id="CatelogEditor" onsubmit="return saveCatelog();">
     <input type="hidden" value="red_shopCategory" id="my-page" />
     <table width="100%" cellspacing="0" cellpadding="0" border="0" class="bbfl">
       <tbody><tr class="bbfl-hd">
         <td>分类名称</td>
         <td>添加子分类</td>
         <td>展开子分类</td>
         <td>上移</td>
         <td>下移</td>
         <td>删除</td>
       </tr>
     <input  type="hidden" name="sequenceId" value="${sequenceId}"/>
     <#if shopCategoryList?exists>
	     <!--展现开始-->
	     <#list  shopCategoryList.values() as shopCategory>
				<tr class="catelog-row prt-catelog">
					<td><input name="" type="text" value="${shopCategory.categoryName!?html}" size="26" class="catelog-name" id="${shopCategory.id?if_exists}" /></td>
					<td class="bbfl-sub"><a href="#" class="add-child-catelog">添加子分类</a></td>
					<td class="bbfl-open"><input type="checkbox" value="" checked="checked" class="catelog-expanded" /></td>
					<td class="bbfl-up"><a href="#" class="mu-prt-catelog"></a></td>
					<td class="bbfl-down"><a href="#" class="md-prt-catelog"></a></td>
					<td class="bbfl-delete"><a href="#" class="del-prt-catelog"></a></td>
				</tr>
						 
				<#list  shopCategory.subCategoryList  as subCategory>			
					<tr class="catelog-row chi-catelog">
						<td align="right" class="blff-zi"><i></i><input type="text" value="${subCategory[1]!?html}" size="20" class="catelog-name" id="${subCategory[0]?if_exists}" /></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td class="bbfl-up"><a href="#" class="mu-chi-catelog"></a></td>
						<td class="bbfl-down"><a href="#" class="md-chi-catelog"></a></td>
						<td class="bbfl-delete"><a href="#" class="del-chi-catelog"></a></td>
					</tr>
				</#list> 
	     </#list>
     </#if>
	  <!--展现结束-->
       
       <tr class="bbfl-botomm" id="CatelogEditorBottom">
         <td colspan="6"><input type="button" value="添加新分类" id="AddParentCatelog"></td>
        </tr>
     </tbody></table>
	<input id="SaveAll" type="submit" value="保存" />
  </div>
<input type="hidden" value="" name="firstCategory" id="FirstCategory" />
<input type="hidden" value="" name="categoryList" id="CategoryList" />
</form>


<textarea id="ParentCatelogTpl" style="display:none;">
<tr class="catelog-row prt-catelog">
	<td><input name="" type="text" value="" size="26" class="catelog-name" id="0"/></td>
	<td class="bbfl-sub"><a href="#" class="add-child-catelog">添加子分类</a></td>
	<td class="bbfl-open"><input type="checkbox" value="" checked="checked" class="catelog-expanded" /></td>
	<td class="bbfl-up"><a href="#" class="mu-prt-catelog"></a></td>
	<td class="bbfl-down"><a href="#" class="md-prt-catelog"></a></td>
	<td class="bbfl-delete"><a href="#" class="del-prt-catelog"></a></td>
	<td>&nbsp;</td>
</tr>
</textarea>
<textarea id="ChildCatelogTpl" style="display:none;">
<tr class="catelog-row chi-catelog">
	<td align="right" class="blff-zi"><i></i><input type="text" value="" size="20" class="catelog-name" id="0"/></td>
	<td>&nbsp;</td>
	<td>&nbsp;</td>
	<td class="bbfl-up"><a href="#" class="mu-chi-catelog"></a></td>
	<td class="bbfl-down"><a href="#" class="md-chi-catelog"></a></td>
	<td class="bbfl-delete"><a href="#" class="del-chi-catelog"></a></td>
</tr>
</textarea>
<script>
function trim(str){   
	return	str.replace(/^\s+|\s+$/g,"");   
}
(function(){
	this.addParentCatelog=function(){
		var obj=$($('#ParentCatelogTpl').val());
		var index=$('.prt-catelog').size();
		obj.find('.add-child-catelog').click(function(){
			addChildCatelog($(this));
		});
		obj.find('.del-prt-catelog').click(function(){
			delParentCatelog($(this));
		});
		obj.find('.mu-prt-catelog').click(function(){
			moveUpParentCatelog($(this));
		});
		obj.find('.md-prt-catelog').click(function(){
			moveDownParentCatelog($(this));
		});
		$('#CatelogEditorBottom').before(obj);
		rebuildCatelog();
	}
	this.addChildCatelog=function(_this){
		var parent_index=$('.prt-catelog').index(_this.parents('.prt-catelog'));
		var obj=$($('#ChildCatelogTpl').val());
		var index=$('.catelog-name-'+parent_index).size();
		obj.find('.del-chi-catelog').click(function(){
			delChildCatelog($(this));
		});
		obj.find('.mu-chi-catelog').click(function(){
			moveUpChildCatelog($(this));
		});
		obj.find('.md-chi-catelog').click(function(){
			moveDownChildCatelog($(this));
		});
		if(index) $('.chi-catelog-'+parent_index).eq(index-1).after(obj);
		else $('#PrtCatelog-'+parent_index).after(obj);
		rebuildCatelog();
	}
	this.delParentCatelog=function(_this){
		var row=_this.parents('.prt-catelog');
		var index=$('.prt-catelog').index(row);
		if($('.chi-catelog-'+index).size()){
			alert('包含子类目，不能删除！');
		}else{
			row.remove();
			rebuildCatelog();
		}
	}
	this.delChildCatelog=function(_this){
		_this.parents('.chi-catelog').remove();
		rebuildCatelog();
	}
	this.moveUpParentCatelog=function(_this){
		var row=_this.parents('.prt-catelog');
		var index=parseInt(row.attr('id').split('-')[1]);
		if(index){
			$('#PrtCatelog-'+(index-1)).before(row);
			if($('.chi-catelog-'+index).size()) $('#PrtCatelog-'+(index-1)).before($('.chi-catelog-'+index));
		}else{
			alert('已在最前，无法上移！');
		}
		rebuildCatelog();
	}
	this.moveDownParentCatelog=function(_this){
		var row=_this.parents('.prt-catelog');
		var index=parseInt(row.attr('id').split('-')[1]);
		if((index+1)>=$('.prt-catelog').size()){
			alert('已在最后，无法下移！');
		}else{
			if($('.chi-catelog-'+(index+1)).size()){
				if($('.chi-catelog-'+index).size()) $('.chi-catelog-'+(index+1)).last().after($('.chi-catelog-'+index));
				$('.chi-catelog-'+(index+1)).last().after(row);
			}
			else {
				if($('.chi-catelog-'+index).size()) $('#PrtCatelog-'+(index+1)).after($('.chi-catelog-'+index));
				$('#PrtCatelog-'+(index+1)).after(row);
			}
		}
		rebuildCatelog();
	}
	this.moveUpChildCatelog=function(_this){
		var row=_this.parents('.chi-catelog');
		var prt_index=parseInt(row.attr('id').split('-')[1]);
		var index=parseInt(row.attr('id').split('-')[2]);
		if(index-1){
			$('#ChiCatelog-'+prt_index+'-'+(index-1)).before(row);
		}else{
			alert('已在最前，无法上移！');
		}
		rebuildCatelog();
	}
	this.moveDownChildCatelog=function(_this){
		var row=_this.parents('.chi-catelog');
		var prt_index=parseInt(row.attr('id').split('-')[1]);
		var index=parseInt(row.attr('id').split('-')[2]);
		if(index>=$('.chi-catelog-'+prt_index).size()){
			alert('已在最后，无法下移！');
		}else{
			$('#ChiCatelog-'+prt_index+'-'+(index+1)).after(row);
		}
		rebuildCatelog();
	}
	this.rebuildCatelog=function(){
		var cur_parent_index=-1;
		var cur_child_index=0;
		var first_category=[];
		var category_list=[];
		var str_catelog_list=[];
		$('.catelog-row').each(function(index){
			var item=$(this);
			if(item.hasClass('prt-catelog')){
				cur_parent_index++;
				cur_child_index=0;
				var name_el=item.attr('id','PrtCatelog-'+cur_parent_index).find('.catelog-name');
				name_el.attr('name','catelog_name_'+cur_parent_index+'_0');
				first_category.push(name_el.attr('id')+'@!@'+name_el.val());
				category_list.push([]);
			}else{
				cur_child_index++;
				item.attr({
					'class':'catelog-row chi-catelog',
					'id':'ChiCatelog-'+cur_parent_index+'-'+cur_child_index
				}).addClass('chi-catelog-'+cur_parent_index).find('.catelog-name').attr({
					'class':'catelog-name',
					'name':'catelog_name_'+cur_parent_index+'_'+cur_child_index
				}).addClass('catelog-name-'+cur_parent_index);
				item.css('display',$('#PrtCatelog-'+cur_parent_index).find('.catelog-expanded').is(':checked')?'table-row':'none');
				category_list[cur_parent_index].push(item.find('.catelog-name').attr('id')+'@!@'+item.find('.catelog-name').val());
			}
		});
		for(var i=0;i<category_list.length;i++){
			str_catelog_list.push(first_category[i]+'='+category_list[i].join(','));
		}
		$('#FirstCategory').val(first_category.join(','));
		$('#CategoryList').val(str_catelog_list.join("\n"));
	}
	this.saveCatelog = function() {
                    rebuildCatelog();
                    var prts = $('.prt-catelog .catelog-name');
                    var chis = $('.chi-catelog .catelog-name');
                    var cn = $('.catelog-name');
                    var cnl = $('.catelog-name').size();
                    var pl = prts.size();
                    var cl = chis.size();
                    for (var i = 0; i < cnl; i++) {
                        if (trim(cn.eq(i).val()) == '') {
                            alert('类目名称不能为空！');
                            return false;
                        }
                    }
                    for (var i = 0; i < cnl; i++) {
                        if (cn.eq(i).val().indexOf('=') >= 0) {
                            alert('类目名称不能有=号！');
                            return false;
                        }
                    }
                    for (var i = 0; i < pl; i++) {
                        var c = prts.eq(i).val().toLowerCase();
                        for (var j = 0; j < pl; j++) {
                            if (i != j && c == prts.eq(j).val()) {
                                alert('"' + c + '"类目重复出现，请检查');
                                return false;
                            }
                        }
                    }
                    for (var i = 0; i < cl; i++) {
                        var c = chis.eq(i).val().toLowerCase();
                        for (var j = 0; j < cl; j++) {
                            if (i != j && c == chis.eq(j).val() && chis.eq(i).attr('name').split('_')[2] == chis.eq(j).attr('name').split('_')[2]) {
                                alert('"' + c + '"类目重复出现，请检查');
                                return false;
                            }
                        }
                    }
                    return true;
                }
})();
$(function(){
	$('#AddParentCatelog').click(function(){
		addParentCatelog();
	});
	$('.add-child-catelog').click(function(){
		addChildCatelog($(this));
	});
	$('.del-prt-catelog').click(function(){
		delParentCatelog($(this));
	});
	$('.del-chi-catelog').click(function(){
		delChildCatelog($(this));
	});
	$('.mu-prt-catelog').click(function(){
		moveUpParentCatelog($(this));
	});
	$('.md-prt-catelog').click(function(){
		moveDownParentCatelog($(this));
	});
	$('.mu-chi-catelog').click(function(){
		moveUpChildCatelog($(this));
	});
	$('.md-chi-catelog').click(function(){
		moveDownChildCatelog($(this));
	});
	$('.catelog-expanded').change(function(){
		rebuildCatelog();
	});
	rebuildCatelog();
});
</script>
