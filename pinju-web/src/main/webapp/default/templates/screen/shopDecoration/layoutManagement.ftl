<title>${shopName}</title>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<#include "/default/templates/layout/site-nav.ftl">
<#setting classic_compatible=true>
        <div id="EditorTopMenu">
            <div id="EditorTopMenuInner"></div>
        </div>
        <div id="EditorNav" style="overflow:hidden">
            <div id="EditorNavInner">
                <a href="#" id="PrimaryLogo"></a>
            <div class="shopzx-top">
            <ul class="shopzx-left" >
            <li class="shopzx-left-noselected"><a href="shopDecorationAction.htm">编辑</a></li>
            <li class="shopzx-left-noselected"><a href="getEditCustomerPageAction.htm">页面</a></li>
            <li class="shopzx-left-noselected"><a href="shopTemplateManagerAction.htm">模板</a></li>
            <li class="shopzx-left-selected"><a href="showIndexLayoutManagementAction.htm">布局</a></li>
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
        <div id="LayoutEditor" class="config-body">
            <div class="config-block">
                <div class="config-caption">首页布局管理</div>
                <div class="config-content">
                <div id="LayoutUtil">
                        
                        <form name="LayoutForm" method="post" action="${base}/shopDecoration/savePageAction.htm" id="LayoutForm">
                        	<input type="hidden" name="userId" value="${userId}" id="userId" />
                        	<input type="hidden" name="pageId" value="${pageId}" id="pageId" />
                        	<input type="hidden" name="shopId" value="${shopId}" id="shopId" />
                        	
                            <input type="hidden" name="xml" value="" id="LayoutXML" />
                            <input type="submit" name="submit" value="保存布局" id="SubmitLayout" class="button" onclick="return confirm('确认要保存吗?')" />
                        </form>
                    </div>
                    <div id="LayoutArea" layout-type="<#if shopPageLayoutList?exists && (shopPageLayoutList.size()>0) && shopPageLayoutList.get(0)?exists>${shopPageLayoutList.get(0).layoutsType}</#if>">
                        <div id="LayoutHeader" class="layout" layout-type="head">
                            <div class="slot-wrapper">
                                <div id="LayoutHeaderCaption" class="layout-caption">店铺页头</div>
                                <ul class="slot">
                                	<#assign i=0>
                                    <#list shopPageLayoutList as layout>
                                    	<#assign i=i+1>
	                                	<#if layout.type == "head">
	                                		<li class="module" module-id="${layout.id}" module-name="${layout.name}" is-custom-code="${layout.isCustomCode}">
	                                        <span>${layout.title}</span>
	                                        <#if i!=1><a href="#del-module" class="delete-module" onclick="return false;"></a></#if>
	                                    	</li>
	                                	</#if>
	                                </#list>
                                </ul>
                                <a href="#${base}/shopDecoration/queryModulePrototypeAction.htm?type=0" class="add-module">添加模块</a>
                            </div>
                        </div>
                        <div class="layout cust-layout" layout-type="left-right">
                            <div class="slot-wrapper" id="LayoutBodySubM">
                                <ul class="slot">
                                    <#list shopPageLayoutList as layout>
	                                	<#if layout.type == "left">
	                                		<li class="module" module-id="${layout.id}" module-name="${layout.name}" is-custom-code="${layout.isCustomCode}">
	                                        <span>${layout.title}</span>
	                                        <a href="#del-module" class="delete-module" onclick="return false;"></a>
	                                    	</li>
	                                	</#if>
	                                </#list>
                                </ul>
                                <a href="#${base}/shopDecoration/queryModulePrototypeAction.htm?type=1" class="add-module">添加模块</a>
                            </div>
                            <div class="slot-wrapper" id="LayoutBodySMain">
                                <ul class="slot">
                                    <#list shopPageLayoutList as layout>
	                                	<#if layout.type == "right">
	                                		<li class="module" module-id="${layout.id}" module-name="${layout.name}" is-custom-code="${layout.isCustomCode}">
	                                        <span>${layout.title}</span>
	                                        <a href="#del-module" class="delete-module" onclick="return false;"></a>
	                                    	</li>
	                                	</#if>
	                                </#list>
                                </ul>
                                <a href="#${base}/shopDecoration/queryModulePrototypeAction.htm?type=2" class="add-module">添加模块</a>
                            </div>
                        </div>
                        <div id="LayoutFooter" class="layout" layout-type="footer">
                            <div class="slot-wrapper">
                                <ul class="slot">
                                    <#list shopPageLayoutList as layout>
	                                	<#if layout.type == "footer">
	                                		<li class="module" module-id="${layout.id}" module-name="${layout.name}" is-custom-code="${layout.isCustomCode}">
	                                        <span>${layout.title}</span>
	                                        <a href="#del-module" class="delete-module" onclick="return false;"></a>
	                                    	</li>
	                                	</#if>
	                                </#list>
                                </ul>
                                <a href="#${base}/shopDecoration/queryModulePrototypeAction.htm?type=3" class="add-module">添加模块</a>
                                <div id="LayoutFooterCaption" class="layout-caption">店铺页尾</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="DialogMask">
            <iframe src="about:blank"></iframe>
        </div>
        <div id="Dialog">
            <div id="DialogShadow"></div>
            <div id="DialogLayout">
                <div id="DialogCaption">
                    <span></span>
                    <a href="#close"></a>
                </div>
                <iframe src="about:blank" id="DialogContent"></iframe>
            </div>
        </div>
 <#include "/default/templates/layout/footer.ftl">
