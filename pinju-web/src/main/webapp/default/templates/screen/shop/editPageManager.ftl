<title>${shopName}</title>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
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
            <li class="shopzx-left-selected"><a href="getEditCustomerPageAction.htm">页面</a></li>
            <li><a href="shopTemplateManagerAction.htm">模板</a></li>
            <li><a href="showIndexLayoutManagementAction.htm">布局</a></li>
            </ul>
            <ul class="shopzx-right" >
            <li><a target="_blank" href="http://service.pinju.com/cms/html/2011/teach_0825/52.html">装修帮助</a></li>
            <li><a target="_blank" href="http://shop${shopId?if_exists}.pinju.com">查看店铺</a></li>
            <li><a href="restoreDesignAction.htm" onclick="return confirm('操作不可恢复,确认要还原吗?')">还原</a></li>
            <li><input type="button" value="预览" class="but-juse" onclick="Javascript:window.open('shopDecorationAction.htm?preview=true')"/></li>
            <li><input type="button" value="发布" class="but-juse" onclick="Javascript:if( confirm('确认要发布吗?')){window.open('releaseDesignAction.htm?preview=true');}"/></li>
            </ul>
            </div>
            </div>
        </div>
		<div class="wrap" id="page_control">
        	<h2>页面管理</h2>
            <form method="post" action="saveCustomerPageAction.htm" id="PageManagement" name="userDesignPage" onsubmit="return savePages();">
            <table>
            	<tr>
                	<th class="th_name">页面名称</th>
                	<th class="th_address">获取地址</th>
                	<th class="th_control">操作</th>
                	<th>编辑</th>
                	<th class="th_nav">在导航显示</th>
                </tr>
            	<tr>
                	<td>首页</td>
                	<td><a id="_FirstPage" class="J_CopyToClipboard" href="#" data-url="http://www.pinju.com/shopDecoration/viewShop.htm?shopId=${shopId}">获取</a></td>
                	<td>
                        <span>&nbsp;</span>
                        <span>&nbsp;</span>
                        <span>&nbsp;</span>
                    </td>
                	<td><a href="shopDecorationAction.htm"><img src="http://static.pinju.com/images/shop/sc-edit-11.gif"></a></td>
                	<td><input name="checkbox" type="checkbox" disabled id="checkbox" value="" checked></td>
                </tr>
                <!--
            	<tr>
                	<td>消费者保障</td>
                	<td><a id="_CustomPage-1" href="javascript:copyToClipboard('http://www.pinju.com/shopDecoration/shopDecorationAction.htm', -1);">获取</a></td>
                	<td>
                        <span>&nbsp;</span>
                        <span>&nbsp;</span>
                        <span>&nbsp;</span>
                    </td>
                	<td>&nbsp;</td>
                	<td><input name="checkbox" type="checkbox" disabled id="checkbox" value="" checked></td>
                </tr>
                -->
                <#if userPageList ??>
                <#list userPageList as page>
                <tr class="cust-page" id="${page.id}">
                <td>
                    <input name="page-name" type="text" value="${page.title!?html}" class="page-name" />
                </td>
                <td>
                    <a id="_CustomPage${page.id}" class="J_CopyToClipboard" href="#" data-url="http://www.pinju.com/shopDecoration/viewShop.htm?userPageId=${page.id}">获取</a>
                </td>
                <td>
                    <a href="#" class="move-up"><img src="http://static.pinju.com/images/shop/sc-aroup-11.gif" /></a>
                    <a href="#" class="move-down"><img src="http://static.pinju.com/images/shop/sc-arodown-11.gif" /></a>
                    <a href="#" class="delete-page"><img src="http://static.pinju.com/images/shop/sc-delete-11.gif" /></a>
                </td>
                <td>
                    <a href="shopDecorationAction.htm?userPageId=${page.id}" class="edit-page"><img src="http://static.pinju.com/images/shop/sc-edit-11.gif"></a>
                </td>
                <td>
                    <input name="visible" type="checkbox" class="visible"<#if page.properties.displayNavigate?? && page.properties.displayNavigate == "Y"> checked</#if> />
                </td>
            </tr>
                </#list>
                </#if>
            	<tr>
                        <td colspan="5">
                            <input name="" type="button" id="AddPage" value="添加页面" />
                            <input name="" type="submit" value="保存" />
                            <input type="hidden" name="params" id="PMResult" value="" />
                        </td>
                </tr>
            </table>
            </form>
		</div>
		<textarea id="RowTemplate" style="display:none;" cols="0" rows="0">
            <tr class="cust-page" id="0">
                <td>
                    <input name="page-name" type="text" value="" class="page-name" />
                </td>
                <td>
                    <a href="javascript:alert('新增未保存页面无法获取地址！');">获取</a>
                </td>
                <td>
                    <a href="#" class="move-up"><img src="http://static.pinju.com/images/shop/sc-aroup-11.gif" /></a>
                    <a href="#" class="move-down"><img src="http://static.pinju.com/images/shop/sc-arodown-11.gif" /></a>
                    <a href="#" class="delete-page"><img src="http://static.pinju.com/images/shop/sc-delete-11.gif" /></a>
                </td>
                <td>
                    <a href="javascript:cantEdit();" class="edit-page"><img src="http://static.pinju.com/images/shop/sc-edit-11.gif"></a>
                </td>
                <td>
                    <input name="visible" type="checkbox" class="visible" />
                </td>
            </tr>
        </textarea>
        <script type="text/javascript" src="http://static.pinju.com/js/ZeroClipboard.js"></script>
	    <script type="text/javascript">
	        (function() {
	            this.cantEdit = function() {
	                alert('请先保存本次修改！');
	            };
	            this.checkName = function(target) {
	                var _name = target.val();
	                var _conflict = 0;
	                if (_name == '') {
	                    alert('页面名称不能为空');
	                    return false;
	                }
	                $('.cust-page .page-name').each(function() {
	                    if ($(this).val() == _name) {
	                        _conflict++;
	                    }
	                });
	                if (_conflict > 1) {
	                    alert('页面名称不能相同');
	                    return false;
	                }
	            };
	            this.moveUp = function(target) {
	                var _obj = target.closest('.cust-page');
	                if ($('.cust-page').index(_obj) > 0) {
	                    _obj.prev().before(_obj);
	                }
	                else {
	                    alert('已经是第一个页面，无法继续向前移动');
	                    return false;
	                }
	            };
	            this.moveDown = function(target) {
	                var _obj = target.closest('.cust-page');
	                if ($('.cust-page').index(_obj) < $('.cust-page').size() - 1) {
	                    _obj.next().after(_obj);
	                }
	                else {
	                    alert('已经是最后一个页面，无法继续向前移动');
	                    return false;
	                }
	            };
	            this.delPage = function(target) {
	                target.closest('.cust-page').remove();
	            };
	            this.addPage = function(target) {
	                if ($('.cust-page').size() < 3) {
	                    var _obj = $($('#RowTemplate').val());
	                    _obj.find('.page-name').change(function() {
	                        checkName($(this));
	                    });
	                    _obj.find('.move-up').click(function() {
	                        moveUp($(this));
	                    });
	                    _obj.find('.move-down').click(function() {
	                        moveDown($(this));
	                    });
	                    _obj.find('.delete-page').click(function() {
	                        delPage($(this));
	                    });
	                    target.closest('tr').before(_obj);
	                }
	                else {
	                    alert('目前暂时只能添加三个自定义页面，敬请期待品聚上线版！');
	                }
	            }
	            this.savePages = function() {
	                var _empty = false;
	                var _conflict = false;
	                $('.cust-page .page-name').each(function() {
	                    if ($(this).val() == '') {
	                        _empty = true;
	                        return false;
	                    }
	                    var _is_conflict = 0;
	                    var _name = $(this).val();
	                    $('.cust-page .page-name').each(function() {
	                        if ($(this).val() == _name) _is_conflict++;
	                    });
	                    if (_is_conflict > 1) _conflict = true;
	                });
	                if (_empty) {
	                    alert('页面名称不能为空');
	                    return false;
	                }
	                if (_conflict) {
	                    alert('页面名称不能相同');
	                    return false;
	                }
	                var result = [];
	                $('.cust-page').each(function(index) {
	                    var str = index + 1;
	                    str += '=';
	                    str += $(this).attr('id') + ';';
	                    str += $(this).find('.page-name').val() + ';';
	                    str += ($(this).find('.visible').is(':checked') ? 'Y' : 'N');
	                    result.push(str);
	                });
	                result = result.join('\n');
	                $('#PMResult').val(result);
	                //alert($('#PMResult').val());
	                return true;
	            }
	        })();
	        $(function() {
	            $('.cust-page .page-name').change(function() {
	                checkName($(this));
	            });
	            $('.cust-page .move-up').click(function() {
	                moveUp($(this));
	            });
	            $('.cust-page .move-down').click(function() {
	                moveDown($(this));
	            });
	            $('.cust-page .delete-page').click(function() {
	                delPage($(this));
	            });
	            $('#AddPage').click(function() {
	                addPage($(this));
	            });
	            ZeroClipboard.setMoviePath('http://static.pinju.com/js/ZeroClipboard.swf');
		    	$('#page_control .J_CopyToClipboard').each(function() {
					var clip = new ZeroClipboard.Client();
					clip.setHandCursor(true);
					clip.setText($(this).attr('data-url'));
					clip.glue(this);
					clip.addEventListener( "complete", function(){
						alert("您的地址已复制到剪贴板！");
					});
				});
	        });
	    </script> 
<#include "/default/templates/layout/footer.ftl">