<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>图片空间 - 图片分类</title>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/imghost/style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/imghost/imghost.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/category.js"></script>
</head>

<body>
<div class="header">
	<div class="masthead cf">
		<div class="logo">
			<a href="http://www.pinju.com/">
				<img class="logo_pic" width="314" height="57" src="http://static.pinju.com/img/imghost/logo-imghost.png" />
			</a>
		</div>
	</div>
</div>

<!-- nav imghost -->
<div class="wrap nav-imghost">
	<ul class="eye">
		<li><a href="http://service.pinju.com/cms/html/2011/bisapp_0921/60.html" title="使用帮助">使用帮助</a></li>
	</ul>
	<ul class="nav-wrap">
		<li><a href="http://www.pinju.com/images/queryStorageAction.htm" title="图片管理">图片管理</a></li>
		<li><a href="http://www.pinju.com/images/viewImagesUpLoadAction.htm" title="图片上传">图片上传</a></li>
		<li class="current"><a href="http://www.pinju.com/images/imagesCategoryAction.htm" title="分类管理">分类管理</a></li>
	</ul>	
</div>

<!-- content -->
<div class="wrap cf">
	<div class="sidebar">
		<div class="box">
			<div class="title"><h2>图片空间统计</h2></div>
			<div class="content">
				<p>已用空间：<strong><#if userSize?exists>${(userSize/1048576)?string("0.00")}</#if></strong> M<br />
				共 <strong>${imageCount?if_exists}</strong> 张</p>
			</div>
		</div>
		<div class="box">
			<div class="title"><h2>图片分类</h2></div>
			<div class="content cf">
				<ul class="imgcat">
				<li class="active">
					<a href="${base}/images/queryStorageAction.htm" title="所有分类">所有分类</a>
				</li>
				<#list  showList as imagesCategory>
				<#if imagesCategory.secondCategoryList.size() == 0>
					<li>
					<#else>
					<li>
					<span class="accordion"></span>
					</#if>
					<a href="${base}/images/queryStorageByCateorgIdAction.htm?imageCategoryId=${imagesCategory.firstCategoryId}" title="">${(imagesCategory.firstCategoryName?if_exists)?html}</a>
					
						<ul>
						<#list  imagesCategory.secondCategoryList as image>
							<li class="haschildren active"><a href="${base}/images/queryStorageByCateorgIdAction.htm?imageCategoryId=${image[0]?if_exists}" title="">${(image[1]?if_exists)?html}</a></li>
							</#list>
						</ul>
					</li>
					</#list>
				</ul>
			</div>
		</div>
	</div>
  
	<div class="contents">
		<div class="wrap-dtb">
			<div class="shop-set">  
				<form id="CatelogEditor" name="CatelogEditor" action="${base}/images/updateImageCateGoryAction.htm" method="post" onsubmit="return saveCatelog();">
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="bbfl">
						<tbody>
						<tr>
							<th>分类名称</th>
							<th>添加子分类</th>
							<th>展开子分类</th>
							<th>上移</th>
							<th>下移</th>
							<th>删除</th>
						</tr>
						<!--展现开始-->	
						<#list  showList as imagesCategory>
						<tr class="catelog-row prt-catelog">
						<#if (imagesCategory.firstCategoryName=="默认分类" || imagesCategory.firstCategoryName=="店铺装修" || imagesCategory.firstCategoryName=="商品图片")>
						<td><input name="" type="text" value="${(imagesCategory.firstCategoryName?if_exists)?html}" size="26" class="catelog-name" id="${imagesCategory.firstCategoryId?if_exists}" readonly="readonly"/></td>
						<td class="bbfl-sub"></td>
						<td class="bbfl-open"></td>
						<td class="bbfl-up"></td>
						<td class="bbfl-down"></td>
						<td class="bbfl-delete"></td>
						<td>&nbsp;</td>
						<#else>
						<td><input name="" type="text" value="${(imagesCategory.firstCategoryName)?if_exists?html}" size="26" class="catelog-name" id="${imagesCategory.firstCategoryId?if_exists}"/></td>
						<td class="bbfl-sub"><a href="#" class="add-child-catelog">添加子分类</a></td>
						<td class="bbfl-open"><input type="checkbox" value="" checked="checked" class="catelog-expanded" /></td>
						<td class="bbfl-up"><a href="#" class="mu-prt-catelog"></a></td>
						<td class="bbfl-down"><a href="#" class="md-prt-catelog"></a></td>
						<td class="bbfl-delete"><a href="#" class="del-prt-catelog"></a></td>
						<td>&nbsp;</td>
						</#if> 
						</tr>
						
						<#list  imagesCategory.secondCategoryList as image>
					<tr class="catelog-row chi-catelog">
					<td align="right" class="blff-zi"><i></i><input type="text" value="${(image[1]?if_exists)?html}" size="20" class="catelog-name" id="${image[0]?if_exists}"/></td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td class="bbfl-up"><a href="#" class="mu-chi-catelog"></a></td>
					<td class="bbfl-down"><a href="#" class="md-chi-catelog"></a></td>
					<td class="bbfl-delete"><a href="#" class="del-chi-catelog"></a></td>
					</tr>
						</#list>
						</#list>
					
						
						<!--展现结束-->
						<tr id="CatelogEditorBottom" class="bbfl-botomm">
							<td colspan="5"><input type="button" id="AddParentCatelog" value="添加一级分类"> <input type="submit" value="保存修改" id="SaveAll"></td>
						</tr>
						
					</tbody>
				</table>

				<input type="hidden" value="" name="firstCategory" id="FirstCategory" />
				<input type="hidden" value="" name="categoryList" id="CategoryList" />

				</form>
			</div>
		</div>
	</div>

</div>
<!-- // content -->
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
			if(confirm('删除一级分类会将该分类中所有图片删除，确定此操作吗？')){
				row.remove();
				rebuildCatelog();
			}
		}
	}
	this.delChildCatelog=function(_this){
	if(confirm('删除二级分类会将图片移动到所属一级分类中，确定此操作吗？')){
			_this.parents('.chi-catelog').remove();
			rebuildCatelog();
		}
	}
	this.moveUpParentCatelog=function(_this){
		var row=_this.parents('.prt-catelog');
		var index=parseInt(row.attr('id').split('-')[1]);
		if(index<4){
			alert('不能移动到默认分类的上面！');
			rebuildCatelog();
			return;
		}
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
				item.css('display',$('#PrtCatelog-'+cur_parent_index).find('.catelog-expanded').is(':checked')?'block':'none');
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
                    for (var i = 0; i < pl; i++) {
                        var c = prts.eq(i).val().toLowerCase();
                        var checkDengYu = c.indexOf('=');
                        //找出中文的个数
                        var cArrs = c.match(/[^x00-xff]/ig);
                        if(cArrs!=null){
	                        if((20 - (c.length - cArrs.length + (cArrs.length*3)))< 0){
	                        	alert('类目名称长度不能超过20,中文为3个长度其余为1个长度！');
	                        	 return false;
	                        }
                        }else{
	                         if(c.length > 20){
		                        	alert('类目名称长度不能超过20,中文为3个长度其余为1个长度！');
		                        	return false;
	                         }
                        }
                        if(checkDengYu > -1){
                        	alert('类目名称中不能包含有“=”号！');
                        	return false;
                        }
                        for (var j = 0; j < pl; j++) {
                            if (i != j && c == prts.eq(j).val()) {
                                alert('"' + c + '"类目重复出现，请检查');
                                return false;
                            }
                        }
                    }
                    for (var i = 0; i < cl; i++) {
                        var c = chis.eq(i).val().toLowerCase();
                        //找出中文的个数
                        var cArrs = c.match(/[^x00-xff]/ig);
                        var checkDengYu = c.indexOf('=');
                        if(cArrs!=null){
	                        if((20 - (c.length - cArrs.length + (cArrs.length*3)))< 0){
	                        	alert('类目名称长度不能超过20,中文为3个长度其余为1个长度！');
	                        	 return false;
	                        }
                        }else{
	                         if(c.length > 20){
		                        	alert('类目名称长度不能超过20,中文为3个长度其余为1个长度！');
		                        	return false;
	                         }
                        }
                        if(checkDengYu > -1){
                        	alert('类目名称中不能包含有“=”号！');
                        	return false;
                        }
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

<#if (status?exists && status == 'Y')> 
 			alert('修改分类信息成功！');
<#elseif (status?exists && status== 'N')>
  			alert('修改分类信息失败！');
<#elseif (status?exists && status== 'M')>
			alert('一级分类的长度加上二级分类的长度不能大于4000个字符!');
</#if>

</script>
</body>
</html>

