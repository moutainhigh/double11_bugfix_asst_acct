<title>店铺分类管理</title>
<link href="http://static.pinju.com/css/open.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jq.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/category.js"></SCRIPT>
<#setting classic_compatible=true>
<#setting number_format="#">
			<form name="itemOperaForm" id="itemOperaForm" action="" method="post">			
				<input type="hidden" name="itemQuery.ids" value="" id="itemId" />
				<input type="hidden" name="returnUrl" value="" id="_returnUrl" />
			</form>
			<form name="itemCategorizeForm" id="itemCategorizeForm" action="saveItemCategorize.htm" method="post">
			<input type="hidden" name="pageId" value="${pageId!"1"}" id="_pageId" />
			<input type="hidden" name="allCount" value="${allCount!""}" id="_allCount" />
			<input type="hidden" name="moveOrAdd" value="${moveOrAdd!"1"}" id="moveOrAdd" />
			<input type="hidden" name="itemCategorizeId" value="${itemCategorizeId!""}" id="itemCategorizeId" />
                <div class="shop-set">  
                	<h3><strong>店铺分类管理</strong></h3>
                    <ul class="tab-main">
                    	<li><a href="${base}/shop/shopCategory.htm">编辑分类</a></li>
                    	<li class="count"><a href="javascript:void(0);">商品归类</a></li>
                    </ul>
                    <ul class="class-table">
                    	  <h4>商品所在分类
                            <select name="categoryId" onChange="selectCategory();" id="_categoryId">
                            	<option value="">全部商品</option>
                            	<option value="0"<#if categoryId == "0"> selected</#if>>未分类商品</option>
                            	<#list  shopCategoryList.values() as shopCategory>
                            	<#if (shopCategory.subCategoryList?size > 0)>
                            	<optgroup label="${shopCategory.categoryName?html}">
                            	<#list  shopCategory.subCategoryList  as subCategory>
                            	<option value="${subCategory[0]?if_exists}"<#if categoryId ?? && categoryId == subCategory[0]!""> selected</#if>>└${subCategory[1]?html}</option>
                            	</#list>
                            	</optgroup>
                            	<#else>
                            	<option value="${shopCategory.id}"<#if categoryId ?? && categoryId == shopCategory.id> selected</#if>>${shopCategory.categoryName!?html}</option>
                            	</#if>
                            	</#list>
                            </select>
                        </h4>
                        <li class="editer">
                            <label><input id="SELECTALL1" onClick="checkedAll(this, 0);" type="checkbox" />全选</label>
                            <select name="addCategory" onChange="add2Category(this);">
                            	<option>添加到分类</option>
                            	<#list  shopCategoryList.values() as shopCategory>
                            	<#if (shopCategory.subCategoryList?size > 0)>
                            	<optgroup label="${shopCategory.categoryName?html}">
                            	<#list  shopCategory.subCategoryList  as subCategory>
                            	<option value="${subCategory[0]?if_exists}"<#if categoryId ?? && categoryId == subCategory[0]!""> selected</#if>>└${subCategory[1]?html}</option>
                            	</#list>
                            	</optgroup>
                            	<#else>
                            	<option value="${shopCategory.id}"<#if categoryId ?? && categoryId == shopCategory.id> selected</#if>>${shopCategory.categoryName!?html}</option>
                            	</#if>
                            	</#list>
                            </select>
                            <select name="moveCategory" onChange="move2Category(this);">
                            	<option>移动到分类</option>
                            	<#list  shopCategoryList.values() as shopCategory>
                            	<#if (shopCategory.subCategoryList?size > 0)>
                            	<optgroup label="${shopCategory.categoryName?html}">
                            	<#list  shopCategory.subCategoryList  as subCategory>
                            	<option value="${subCategory[0]?if_exists}"<#if categoryId ?? && categoryId == subCategory[0]!""> selected</#if>>└${subCategory[1]?html}</option>
                            	</#list>
                            	</optgroup>
                            	<#else>
                            	<option value="${shopCategory.id}"<#if categoryId ?? && categoryId == shopCategory.id> selected</#if>>${shopCategory.categoryName!?html}</option>
                            	</#if>
                            	</#list>
                            </select>
                            <input id="nextButton" type="button" onClick="prevOrNextPage(-1);" />
                            <input id="prevButton" type="button" onClick="prevOrNextPage(1);" />
                            <span>${pageId}/${allPages}</span>页
                        </li>
                    </ul>
                    <ul class="class-table">
                    	<table>
                        	<tr>
                            	<th class="c-checkbox"></th>
                            	<th class="c-name">商品描述</th>
                            	<th>所属分类</th>
                            	<th>编辑商品</th>
                            </tr>
                            <#if itemCategorizeList ??>
                            <#list itemCategorizeList as itemCategorize>
                        	<tr class="level-line">
                            	<td><input name="saveItemList" id="_ITEM${itemCategorize.id}" type="checkbox" value="${itemCategorize.itemCates}"></td>
                            	<td>
                                    <a href="/detail/${itemCategorize.id}.htm" class="img-project"><img src="${imageServer}${itemCategorize.picUrl}_80x80.jpg" /></a>
                                    <a class="commodity_links" href="/detail/${itemCategorize.id}.htm">${itemCategorize.title?html}</a>
                                </td>
                            	<td class="class-path">
                            		<#if itemCategorize.categoryNameList??>
                            		<#list itemCategorize.categoryNameList.keySet() as itemKey>
                                	<p><span>${itemCategorize.categoryNameList.get(itemKey)}</span>
                                    <a href="javascript:delCategory('${itemCategorize.id}', '${itemKey}');" class="del-m"></a>
                                    </p>
                                    </#list>
                                    </#if>
                                </td>
                            	<td>
                                    <a href="${base}/itemReleased/${itemCategorize.id}.htm" target="_blank">编辑</a>
                                    <a href="javascript:delShelfItem(${itemCategorize.id});">下架</a>
                                    <a href="javascript:delItem(${itemCategorize.id});">删除</a>
                                </td>
                            </tr>
                            </#list>
                            </#if>
                        </table>
                    </ul>
                    <ul class="class-table">
                        <li class="editer">
                            <label><input id="SELECTALL0" onClick="checkedAll(this, 1);" type="checkbox" />全选</label>
                            <select name="addCategory" onChange="add2Category(this);">
                            	<option>添加到分类</option>
                            	<#list  shopCategoryList.values() as shopCategory>
                            	<#if (shopCategory.subCategoryList?size > 0)>
                            	<optgroup label="${shopCategory.categoryName?html}">
                            	<#list  shopCategory.subCategoryList  as subCategory>
                            	<option value="${subCategory[0]?if_exists}"<#if categoryId ?? && categoryId == subCategory[0]!""> selected</#if>>└${subCategory[1]?html}</option>
                            	</#list>
                            	</optgroup>
                            	<#else>
                            	<option value="${shopCategory.id}"<#if categoryId ?? && categoryId == shopCategory.id> selected</#if>>${shopCategory.categoryName!?html}</option>
                            	</#if>
                            	</#list>
                            </select>
                            <select name="moveCategory" onChange="move2Category(this);">
                            	<option>移动到分类</option>
                            	<#list  shopCategoryList.values() as shopCategory>
                            	<#if (shopCategory.subCategoryList?size > 0)>
                            	<optgroup label="${shopCategory.categoryName?html}">
                            	<#list  shopCategory.subCategoryList  as subCategory>
                            	<option value="${subCategory[0]?if_exists}"<#if categoryId ?? && categoryId == subCategory[0]!""> selected</#if>>└${subCategory[1]?html}</option>
                            	</#list>
                            	</optgroup>
                            	<#else>
                            	<option value="${shopCategory.id}"<#if categoryId ?? && categoryId == shopCategory.id> selected</#if>>${shopCategory.categoryName!?html}</option>
                            	</#if>
                            	</#list>
                            </select>
                            <input id="nextButton1" type="button" onClick="prevOrNextPage(-1);" />
                            <input id="prevButton1" type="button" onClick="prevOrNextPage(1);" />
                            <span>${pageId}/${allPages}</span>页
                        </li>
                    </ul>
                </div>
            
            </form>
            <input type="hidden" value="red_shopCategory" id="my-page" />
            <script language="javascript" type="text/javascript">
            <!--
            	var $$ = function(objId) {
            		return document.getElementById(objId);
            	}
            	
            	function delShelfItem(itemId) {
            		if (confirm("确认下架此商品？")) {
            			$$('itemOperaForm').action = '/item/userDelShelfItemExt.htm';
            			$$('itemId').value = itemId;
            			var url = '/shop/itemCategorize.htm?pageId=';
            			url += $$('_pageId').value;
            			url += '&allCount=';
            			url += $$('_allCount').value;
            			url += '&categoryId=';
            			url += $$('_categoryId').options[$$('_categoryId').options.selectedIndex].value;
            			$$('_returnUrl').value = url;
            			$$('itemOperaForm').submit();
            		}
            	}
            	
            	function delItem(itemId) {
            		if (confirm("确认删除选中商品？")) {
            			$$('itemOperaForm').action = '/item/userDelItemExt.htm';
            			$$('itemId').value = itemId;            			
            			var url = '/shop/itemCategorize.htm?pageId=';
            			url += $$('_pageId').value;
            			url += '&allCount=';
            			url += $$('_allCount').value;
            			url += '&categoryId=';
            			url += $$('_categoryId').options[$$('_categoryId').options.selectedIndex].value;
            			$$('_returnUrl').value = url;
            			$$('itemOperaForm').submit();
            		}
            	}
            	
            	function _init() {
	            	var pageId = parseInt(${pageId});	
	            	var allPage = parseInt(${allPages});            	
	            	if(pageId == 1) {
	            		$$('nextButton').disabled = true;
	            		$$('nextButton').className = 'button_next_none';
	            		$$('nextButton1').disabled = true;
	            		$$('nextButton1').className = 'button_next_none';
	            	} else { 
	            		$$('nextButton').disabled = false;
	            		$$('nextButton').className = 'button_next';
	            		$$('nextButton1').disabled = false;
	            		$$('nextButton1').className = 'button_next';
	            	}
	            	if(pageId == allPage) {
	            		$$('prevButton').disabled = true;
	            		$$('prevButton').className = 'button_previous_none';
	            		$$('prevButton1').disabled = true;
	            		$$('prevButton1').className = 'button_previous_none';
	            	} else {
	            		$$('prevButton').disabled = false;
	            		$$('prevButton').className = 'button_previous';
	            		$$('prevButton1').disabled = false;
	            		$$('prevButton1').className = 'button_previous';
	            	}
            	}
            	_init();
            	
            	function move2Category(obj) {
            		var id = obj.options[obj.options.selectedIndex].value;
            		if(isChecked() && confirm('您确认移动到该分类吗？')) {
            			$$('moveOrAdd').value = 0;
            			$$('itemCategorizeId').value = id;
            			$$('itemCategorizeForm').action = 'saveItemCategorize.htm';
            			$$('itemCategorizeForm').submit();
            		} else
            			obj.selectedIndex = 0;
            	}

            	function add2Category(obj) {
            		var id = obj.options[obj.options.selectedIndex].value;
            		if(isChecked() && confirm('您确认增加到该分类吗？')) {
            			$$('moveOrAdd').value = 1;
            			$$('itemCategorizeId').value = id;
            			$$('itemCategorizeForm').action = 'saveItemCategorize.htm';
            			$$('itemCategorizeForm').submit();
            		} else
            			obj.selectedIndex = 0;
            	}
            	
            	function delCategory(itemId, cateId) {
            		if(confirm('您确认从该分类中移除吗？')) {
            			$$('moveOrAdd').value = 2;
            			$$('_ITEM' + itemId).checked = true;            		
            			$$('itemCategorizeId').value = cateId;
            			$$('itemCategorizeForm').action = 'saveItemCategorize.htm';
            			$$('itemCategorizeForm').submit();
            		}
            	}
            	
            	function selectCategory() {
            		$$('itemCategorizeForm').pageId.value = 1;
            		$$('itemCategorizeForm').action = 'itemCategorize.htm';
            		$$('itemCategorizeForm').submit();
            	}
            	
            	function isChecked() {
            		var objs = $$('itemCategorizeForm').elements;
            		for(var i = 0; i < objs.length; i++) {
            			if(objs[i].name == 'saveItemList' 
            				&& objs[i].type == 'checkbox'
            				&& objs[i].checked == true)
            				return true;
            		}
            		alert('请选择需要归类的商品');
            		return false;
            	}
            	
            	function checkedAll(obj, postion) {
            		var objs = $$('itemCategorizeForm').elements;
            		for(var i = 0; i < objs.length; i++) {
            			if(objs[i].name == 'saveItemList' 
            				&& objs[i].type == 'checkbox') {
            				objs[i].checked = obj.checked;
            			}
            		}
            		$$('SELECTALL' + postion).checked = obj.checked
            	}
            	
            	function prevOrNextPage(increment) {
            		$$('itemCategorizeForm').pageId.value = parseInt(${pageId}) + increment;
            		$$('itemCategorizeForm').action = 'itemCategorize.htm';
            		$$('itemCategorizeForm').submit();
            	}
            //-->
            </script>
