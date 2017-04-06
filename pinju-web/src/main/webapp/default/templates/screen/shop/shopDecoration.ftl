<!DOCTYPE html>
<html>
<head>
<title>${shopName}</title>
<meta name="keywords" content="品聚网 购物商城 店铺,${(shopName!'')?html},${(shopCategoryStr!'')?html}"/>
<meta name="description" content="欢迎来到品聚,${(shopType!'')?html},${(shopCategoryStr!'')?html},${(shopName!'')?html},<#if shopLabel?exists>${(shopLabel!'')?html}<#else>品聚 </#if>" />
<link rel="icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/img/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" />
<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<link href="http://static.pinju.com/css/shopDecoration/base.css?t=20111203.css" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/js/shopDecoration/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shopDecoration/base.js"></script>
<SCRIPT src="http://static.pinju.com/js/util.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shopDecoration/layoutManagement.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/coupon/getCoupon.js?t=20111208.js"></script>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<!--<script type="text/javascript" src="/default/js/layoutManagement.js"></script>-->
</head>
  <body>
<#include "/default/templates/layout/site-nav.ftl">

<link href="http://static.pinju.com/css/shopDecoration/style/<#if templateColor?exists>${templateColor}<#else>red</#if>.css" rel="stylesheet" type="text/css" />
    	<#if preview?exists && preview == "true">
    		<#include "/default/templates/layout/laybaner.ftl">
    		<div class="shopsplit"></div>
    	</#if>
    	<div style="background:#${colour?if_exists} url('${url?if_exists}') ${backgroundeffect?if_exists} ${backgroundtype?if_exists};">
	    <!--url(图片路径)-->
	    
	    <!--动态效果-->
	    <!--重复：repeat=重复-->
	    <!--重复：no-repeat=不重复-->
	    <!--重复：repeat=平铺-->
	    <!--重复：repeat-x=横向平铺-->
	    <!--重复：repeat-y=纵向平铺-->
	    
	    <!--背景对齐-->
	    <!--重复：left=左对齐-->
	    <!--重复：right=右对齐-->
	    <!--重复：center=居中-->
	    
	    <!--重复：top=上对齐-->
	    <!--重复：center=右对齐-->
	    <!--重复：bottom=底端对齐-->
	    
	    <!--背景颜色选择器-->
	    <!--重复：#333=颜色色值-->
			
		<#if !preview?exists || preview!="true">
		<div id="EditorTopMenu">
            <div id="EditorTopMenuInner"></div>
        </div>
        <div id="EditorNav">
            <div id="EditorNavInner">
                <a href="#" id="PrimaryLogo"></a>
            <div class="shopzx-top">
            <ul class="shopzx-left" >
            <li class="shopzx-left-selected" onMouseOver="$('#editPage').css('display','block')" onMouseOut="$('#editPage').css('display','none')"><a href="shopDecorationAction.htm">编辑</a>
            <div id="editPage" class="ed-xiala" style="display:none;">                    
            <div class="ed-xia">
            <ul >
            <li>店铺基础页</li>
            <li><a href="shopDecorationAction.htm">首页</a></li>
            <#list userPageList as userpage>
            	<li><a href="shopDecorationAction.htm?userPageId=${userpage.id?if_exists}">${userpage.title!?html}</a></li>
            </#list>
            </ul>
            <ul >
            <li>商品详情页</li>
            <li>商品列表页</li>
            </ul>
            </div>
            <p><a href="getEditCustomerPageAction.htm">管理所有页面>></a>&nbsp;</p>
            </div>
            </li>
            <li ><a href="getEditCustomerPageAction.htm">页面</a></li>
            <li><a href="shopTemplateManagerAction.htm">模板</a></li>
            <li><a href="showIndexLayoutManagementAction.htm">布局</a></li>
            </ul>
            <ul class="shopzx-right" >
            <li><a target="_blank" href="http://service.pinju.com/cms/html/2011/teach_0825/52.html">装修帮助</a></li>
            <li><a target="_blank" href="http://shop${shopId?if_exists}.pinju.com">查看店铺</a></li>
            <li><a href="restoreDesignAction.htm" onclick="return confirm('操作不可恢复,确认要还原吗?')">还原</a></li>
            <li><input type="button" value="预览" class="but-juse" onclick="Javascript:window.open('shopDecorationAction.htm?preview=true')"/></li>
            <li><input type="button" value="发布" class="but-juse" onclick="Javascript:if( confirm('确认要发布吗?')){window.open('releaseDesignAction.htm');}"/></li>
            </ul>
            </div>
            </div>
        </div>
        </#if>
${pageHtml?if_exists}
		<div id="DialogMask" style="display:none">
            <iframe src="about:blank"></iframe>
        </div>
        <div id="Dialog">
            <div id="DialogShadow"></div>
            <div id="DialogLayout">
                <div id="DialogCaption">
                    <span></span>
                    <a href="#close"></a>
                </div>
                <iframe src="about:blank" id="DialogContent" style="overflow:auto;"></iframe>
            </div>
        </div>
        
        </div>
         <script type="text/javascript">
            var dialog;
            $(function() {
                dialog = new Dialog();
            });
        </script>
 <#include "/default/templates/layout/footer.ftl">
   </body>
  </html>
