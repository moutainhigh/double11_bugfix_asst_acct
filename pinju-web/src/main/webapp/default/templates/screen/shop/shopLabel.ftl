<!-- content -->
<title>店铺标签</title>
<form id="form1" action="${base}/shopLabel/updateShopLabel.htm" method="post">
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link media="screen" type="text/css" href="http://static.pinju.com/css/open.css" rel="stylesheet">
<script src="http://static.pinju.com/js/shop/tagshop.js"></script>
<script>
<#if (message?exists && message == 'Y')>alert('修改店铺标签成功！');</#if>
<#if (message?exists && message == 'N')>alert('修改店铺标签失败！');</#if>
<#if (message?exists && message == 'M')>alert('修改的店铺表中包含了敏感词！');</#if>
</script>
<!-- content -->

		<ul class="breadcrumb">
			<li><a href="#">我是卖家</a> &gt; </li>
			<li>店铺管理 &gt; </li>
			<li>店铺标签设置</li>
		</ul>
		<div class="titletop">
			<h1>店铺标签设置</h1>
			<span class="eye"><a target="_blank" href="http://service.pinju.com/cms/html/2011/matool_1213/116.html">使用帮助</a></span>
		</div>
		<p class="exinfo">设置店铺标签可以增加店铺被搜索到的几率哦！</p>
		
		<div class="blockwrap">
			<div class="formwrap">
				<div class="row">
					<label>添加店铺标签：</label> <input value="多个标签词之间请用空格分开" id="addTagInput" class="taginput" type="text" /> <a id="addTag" class="btn-addtag" href="#">添加</a>
				</div>
				<p class="nolabelrow" id="J_nolabelrow">一次最多可以提交5个标签，每个标签的字符必须在1-14个字符范围内</p>
			</div>
			
			<div class="shoptaglist">
				<dl class="cf">
					<dt>我的店铺标签:</dt>
					<dd>
						<ul id="taglistshow">
					
						</ul>
					</dd>
				</dl>
			</div>
		</div>
		<input type="hidden" id="J_wordData" name="wordData" value="${(shopInfoDO.shopLabel!'')?html}" />
		
		<div class="btn-imgtext btn-center">
			<button  class="savethisact" type="submit"><span>保存本次操作</span></button>
		</div>
		
		<div class="cswrap tagtipwrap">
			<h2>什么是店铺标签？为什么要设置店铺标签？</h2>
			<p>1.您可以用简洁的语言描述自己店铺的风格、类型、与主营商品。<br />
			2.您可以一次添加多个标签，标签与标签之间用空格分开。<br />
			3.设置成功后，您的店铺标签会显示在您店铺左侧的标签栏中，同时会被设置为网页的关键字，方便被搜索引擎收录，从而提高您店铺和商品被搜索到的概率。<br />
			<strong>设置成功后，您的网页代码中会自动增加您的店铺关键字，效果如图：</strong><br/>
			<br />
			<img src="http://static.pinju.com/img/shop-meta.png" alt="店铺关键字效果图" />
		</div>
</form>


