<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="expires" content="0" />
	<meta name="robots" content="all" />
	<meta name="googlebot" content="all" />
	<meta name="baiduspider" content="all" />
	<title>品聚 - 商品搜索</title>
	<#include "/default/templates/layout/header.ftl">
	<link href="http://static.pinju.com/css/search/search.css?t=20111208.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>

	<div class="top_bar">
		<div class="top_bar_box cf">
			<div class="login_status">
				<span class="welcome">您好！欢迎来到品聚</span>
				<span>[ <a href="#">注册</a> ]</span>
				<span>[ <a href="#">登陆</a> ]</span>
			</div>
			<div class="liaison">
				<a href="#">加入我们</a>|<a href="#">联系我们</a>
			</div>
		</div>
	</div>

	<div class="wrap">
		<div class="logo">
			<img class="logo_pic" width="147" height="47" src="http://static.pinju.com/img/logo_redtext.png" />
		</div>
		
		<div class="search-panel"> 
			<div class="tabs"> 
				<ul> 
					<li><a href="##" class="active">宝贝</a></li> 
					<li><a href="##">店铺</a></li> 
				</ul> 
			</div> 
			<form name="search" action=""> 
				<div class="search-fields"> 
					<label class="hidden" for="q"></label> 
					<input value="请输入" accesskey="s" id="q" name="q">
				</div> 
				<input type="submit" value="搜&nbsp;&nbsp;索" class="btn">
			</form>
		</div> 
	</div>
	
	<div class="wrap sp-search cf">
		<ul class="breadcrumbs-s">
			<li><a href="#">所有分类</a></li>
			<li><a href="#">相机/摄像机/摄影器材</a></li>
			<li>数码摄像机</li>
			<li class="total">找到相关宝贝<strong>132456</strong>件</li>
		</ul>
	
		<div class="plwrap cf">
		
			<div class="bar-left">
				<h2 class="bar-topic">商品分类</h2>
				<#include "/default/templates/control/item/searchCate.ftl">
			</div>
			<div class="contents">

				<ul class="tabbar">
					<li class="item"><a href="#">所有宝贝</a></li>
					<li class="pageitem">
						<div class="pagination" id="Pagination">
							<span>1/100</span>
							<span class="prev" title="上一页"></span>
							<a class="next" href="#" title="下一页"></a>
						</div>
					</li>
				</ul>
				
				<div class="sfilter">
					<div class="basicfilter cf">
						<ul>
							<li class="keywords">
							<label for="filterSearchKeyWord">关键字：</label><input type="text" value="迷你音箱" size="10" name="q" id="filterSearchKeyWord" gtbfieldid="11">
							</li>
							<li class="exclude">
								<label for="filterExclude">排除：</label><input type="text" value="" name="" id="filterExclude">
							</li>
							<li class="checkbox"><input type="checkbox" id="" name=""><label for="">限时折扣</label></li>
							<li class="checkbox"><input type="checkbox" id="" name=""><label for="">消费者保障</label></li>
							<li class="checkbox"><input type="checkbox" id="" name=""><label for="">7天退换</label></li>
							<li class="checkbox"><input type="checkbox" id="" name=""><label for="">正品保障</label></li>
							<li class="submit"><input type="submit" id="" name="" value="确定"></li>
						</ul>
					</div>
					<div class="sortbar cf">
						<div class="sortdefault">
							<ul>
								<li class="selected"><span>默认排序</span>
									<ul>
										<li><a class="by-sale" href="" title="销量最高在前">销量从高到低</a></li>
										<li><a class="by-rank" href="" title="信用最高在前">信用从高到低</a></li>
										<li><a class="by-pricel2h" href="" title="价格最低在前">价格从低到高</a></li>
										<li><a class="by-priceh2l" href="" title="价格最高在前">价格从高到低</a></li>
									</ul>
								</li>
							</ul>
						</div>
						<ul class="sortbtns">
							<li><a title="销量从高到低" href="" class="by-sale">销量</a></li>
							<li><a title="信用从高到低" href="" class="by-rank ordered">信用</a></li>
							<li><a title="点击按价格从低到高排序" href="" class="by-price">价格</a></li>
						</ul>
						<div class="pricearea">
							<input type="text" title="按价格区间筛选 最低价" name="start_price" class="txt" value="">
							<span>-</span>
							<input type="text" title="按价格区间筛选 最高价" name="end_price" class="txt" value="">
						</div>
					</div>
				</div>
				
				<div class="pdcat">
					<h3><a href="#" title="text">数码摄像机</a></h3>
					<ul class="productdetail">
						<li>
							<dl>
								<dt>索尼型号：</dt>
								<dd><a href="#" title="text">HDR-CX180E</a></dd>
								<dd><a href="#" title="text">HDR-CX300</a></dd>
								<dd><a href="#" title="text">HDR-CX150E</a></dd>
								<dd><a href="#" title="text">HDR-CX170</a></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>售后服务：</dt>
								<dd><a href="#" title="text">全国联保</a></dd>
								<dd><a href="#" title="text">店铺三包</a></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>显示屏尺寸：</dt>
								<dd><a href="#" title="text">2.7</a></dd>
							</dl>
						</li>
						<li>
							<dl>
								<dt>有效像素：</dt>
								<dd><a href="#" title="text">400-500万</a></dd>
								<dd><a href="#" title="text">300-400万</a></dd>
							</dl>
						</li>
						<li class="last">
							<dl>
								<dt>光学变焦：</dt>
								<dd><a href="#" title="text">10倍及以下</a></dd>
								<dd><a href="#" title="text">12倍</a></dd>
								<dd><a href="#" title="text">24倍</a></dd>
								<dd><a href="#" title="text">50倍</a></dd>
							</dl>
						</li>
					</ul>
					<ul class="relatedcat">
						<li><a href="#" title="text">相机/摄像机/摄影器材</a>(503)</li>
						<li><a href="#" title="text">3C数码配件市场</a>(456)</li>
						<li><a href="#" title="text">功能箱包</a>(189)</li>
						<li><a href="#" title="text">电玩/配件/游戏/攻略</a>(28)</li>
					</ul>
				</div>
				
				<dl class="relatedprod">
					<dt>你是不是想找：</dt>
					<dd><a href="#" title="text">索尼cx180e</a></dd>
					<dd><a href="#" title="text">索尼hdr-cx180e</a></dd>
					<dd><a href="#" title="text">cx180e索尼摄像机</a></dd>
					<dd><a href="#" title="text">索尼sony hdr-cx180e</a></dd>
					<dd><a href="#" title="text">索尼摄像机cx180</a></dd>
				</dl>
				
				<div class="expmessage"><p><a title="合并同卖家宝贝" href="">合并同卖家宝贝</a></p></div>
			
				<ul class="plist-view cf">
					<li class="item">				
						<div class="img">
							<img src="media/product-icon.jpg" width="81" height="81" alt="text" />
						</div>
						<dl  class="titletext">
							<dt><a href="#" title="text">SONY <strong>索尼</strong> HDR-<strong>CX180E</strong> 数码高清摄像机 正品行货 全国联保 特价</a></dt>
							<dd>
								<span class="shopowner"><a href="#" title="text">祁连金牌店</a></span>
							</dd>
						</dl>
						<dl class="price">
							<dt>￥ <strong>3292.99</strong></dt>
							<dd>运费：0.00</dd>
						</dl>
						<div class="location">
							<span>浙江金华</span>
						</div>
						<div class="saleamount">
							<span>最近成交25笔</span> <span class="q_7"></span>
						</div>
						<div class="quality_items">
							<ul>
								<li><span class="ql_7"></span></li>
								<li><span class="ql_p"></span></li>
								<li><span class="ql_3b"></span></li>
								<li><span class="ql_exp"></span></li>
							</ul>
						</div>
					</li>
					<li class="item">				
						<div class="img">
							<img src="media/product-icon.jpg" width="81" height="81" alt="text" />
						</div>
						<dl  class="titletext">
							<dt><a href="#" title="text">SONY <strong>索尼</strong> HDR-<strong>CX180E</strong> 数码高清摄像机 正品行货 全国联保 特价</a></dt>
							<dd>
								<span class="shopowner"><a href="#" title="text">蓝音数码专营店</a></span>
							</dd>
						</dl>
						<dl class="price">
							<dt>￥ <strong>3292.99</strong></dt>
							<dd>运费：0.00</dd>
						</dl>
						<div class="location">
							<span>浙江金华</span>
						</div>
						<div class="saleamount">
							<span>最近成交25笔</span>
						</div>
						<div class="quality_items">
							<ul>
								<li><span class="ql_7"></span></li>
								<li><span class="ql_p"></span></li>
								<li><span class="ql_3b"></span></li>
								<li><span class="ql_exp"></span></li>
							</ul>
						</div>
					</li>
					<li class="item">				
						<div class="img">
							<img src="media/product-icon.jpg" width="81" height="81" alt="text" />
						</div>
						<dl  class="titletext">
							<dt><a href="#" title="text">SONY <strong>索尼</strong> HDR-<strong>CX180E</strong> 数码高清摄像机 正品行货 全国联保 特价</a></dt>
							<dd>
								<span class="shopowner"><a href="#" title="text">祁连金牌店</a></span>
							</dd>
						</dl>
						<dl class="price">
							<dt>￥ <strong>3292.99</strong></dt>
							<dd>运费：0.00</dd>
						</dl>
						<div class="location">
							<span>浙江金华</span>
						</div>
						<div class="saleamount">
							<span>最近成交25笔</span> <span class="q_7"></span>
						</div>
						<div class="quality_items">
							<ul>
								<li><span class="ql_7"></span></li>
								<li><span class="ql_p"></span></li>
								<li><span class="ql_3b"></span></li>
								<li><span class="ql_exp"></span></li>
							</ul>
						</div>
					</li>
					<li class="item">				
						<div class="img">
							<img src="media/product-icon.jpg" width="81" height="81" alt="text" />
						</div>
						<dl  class="titletext">
							<dt><a href="#" title="text">SONY <strong>索尼</strong> HDR-<strong>CX180E</strong> 数码高清摄像机 正品行货 全国联保 特价</a></dt>
							<dd>
								<span class="shopowner"><a href="#" title="text">蓝音数码专营店</a></span>
							</dd>
						</dl>
						<dl class="price">
							<dt>￥ <strong>3292.99</strong></dt>
							<dd>运费：0.00</dd>
						</dl>
						<div class="location">
							<span>浙江金华</span>
						</div>
						<div class="saleamount">
							<span>最近成交25笔</span>
						</div>
						<div class="quality_items">
							<ul>
								<li><span class="ql_7"></span></li>
								<li><span class="ql_p"></span></li>
								<li><span class="ql_3b"></span></li>
								<li><span class="ql_exp"></span></li>
							</ul>
						</div>
					</li>
					<li class="item">				
						<div class="img">
							<img src="media/product-icon.jpg" width="81" height="81" alt="text" />
						</div>
						<dl  class="titletext">
							<dt><a href="#" title="text">SONY <strong>索尼</strong> HDR-<strong>CX180E</strong> 数码高清摄像机 正品行货 全国联保 特价</a></dt>
							<dd>
								<span class="shopowner"><a href="#" title="text">祁连金牌店</a></span>
							</dd>
						</dl>
						<dl class="price">
							<dt>￥ <strong>3292.99</strong></dt>
							<dd>运费：0.00</dd>
						</dl>
						<div class="location">
							<span>浙江金华</span>
						</div>
						<div class="saleamount">
							<span>最近成交25笔</span> <span class="q_7"></span>
						</div>
						<div class="quality_items">
							<ul>
								<li><span class="ql_7"></span></li>
								<li><span class="ql_p"></span></li>
								<li><span class="ql_3b"></span></li>
								<li><span class="ql_exp"></span></li>
							</ul>
						</div>
					</li>
				</ul>
					
				<div class="pagination" id="Pagination">
					<span>上一页</span>
					<span class="current">1</span>
					<a href="#">2</a>
					<a href="#">3</a>
					<a href="#">4</a>
					<a href="#" class="next"><span>下一页</span></a>
				</div>
			</div>
		</div>
	</div>

	<div class="footer">
		<div class="footer_main">
			<ul class="foot_nav">
				<li><a href="#">首页</a></li>
				<li><a href="#">关于品聚</a></li>
				<li><a href="#">加入我们</a></li>
				<li><a href="#">联系我们</a></li>
				<li><a href="#">用户协议</a></li>
				<li><a href="#">新手帮助</a></li>
			</ul>
			<p class="copyright">上海御网信息技术有限公司  版权所有</p>
			<p class="copyright"><span>媒体联系：PR@zba.com</span>丨  <span>商户加盟： business@zba.com</span>  丨<span>  聚伙伴加盟：jupartner@zba.com</span></p>
		</div>
	</div>
<!--[if lt IE 9]><script src="http://static.pinju.com/js/html5.js"></script><![endif]-->
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/search/search.js"></script>

</body>
</html>
