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
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=default"></script>
</head>

<body>
<script type="text/javascript">
 function deleteById(id){
           var r=confirm("删除之后将不能恢复且无法显示，请确保您的店铺没应用到此图片");
            if (r==true){
                $("#pageForm").attr({ action:"/images/deleteStorageFileInfoAction.htm?id="+id,method:"post" }); 
                 $("#pageForm").submit();
            }
       }
</script>

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
		<li class="current"><a href="http://www.pinju.com/images/queryStorageAction.htm" title="图片管理">图片管理</a></li>
		<li><a href="http://www.pinju.com/images/viewImagesUpLoadAction.htm" title="图片上传">图片上传</a></li>
		<li><a href="http://www.pinju.com/images/imagesCategoryAction.htm" title="分类管理">分类管理</a></li>
	</ul>	
</div>

<!-- content -->
 <form name="searchForm" action="#" method="post" id="pageForm">
<input type="hidden" id="j_categoryId" name="imageCategoryId" />
<input type="hidden" name="idCheck" value="${storageFileInfo.id!}">
<div style="display:none" >
		<select id="imageCategoryId">
	  <#if imagesCategoryList??>
		<#list imagesCategoryList as imagesCategory>
	                 <option value="${imagesCategory.firstCategoryId}">${(imagesCategory.firstCategoryName)?if_exists?html}</option>
				   <#list  imagesCategory.secondCategoryList as image>
                     <option value="${image[0]}">&nbsp;&nbsp;&nbsp;&nbsp;|_${(image[1])?if_exists?html}</option>
				   </#list>
	      </#list>
	  </#if>
	</select>
 </div>
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
						<a href="/images/queryStorageAction.htm" title="所有分类">所有分类</a>
					</li>
					<#if imagesCategoryList?exists>
						<#list imagesCategoryList as imagesCategory>
							<#if imagesCategory.secondCategoryList.size() == 0>
							<li>
							<#else>
							<li>
							<span class="accordion"></span>
							</#if>
								<a href="/images/queryStorageByCateorgIdAction.htm?imageCategoryId=${imagesCategory.firstCategoryId}" title="${(imagesCategory.firstCategoryName)?if_exists?html}">${(imagesCategory.firstCategoryName)?if_exists?html}</a>
							<#if imagesCategory.secondCategoryList?exists>
								<ul>
							<#list imagesCategory.secondCategoryList as image>
								<li><a href="/images/queryStorageByCateorgIdAction.htm?imageCategoryId=${image[0]}" title="${(image[1])?if_exists?html}">${(image[1])?if_exists?html}</a></li>
							</#list>
								</ul>
							</#if>
						</li>
					</#list>
					</#if>
				</ul>
		  </div>
		</div>
	</div>
  
	<div class="contents">
		<div class="imgdetails">
			<div class="topbar">
				<div class="imgtitle">
					<p class="name">
					    <input class="editoldName" type="hidden" value="${(storageFileInfo.fileName)?if_exists?html}" />
						<span class="title" title="${(storageFileInfo.fileName)?if_exists?html}">${(storageFileInfo.fileName)?if_exists?html}</span>
						<span class="title-edit"></span>
					</p>
					<p class="edit">
						<input class="editId" type="hidden" value="${storageFileInfo.id!}" />
						<input class="editName" type="text" maxlength="20" size="13" value="" />
						<button type="button">确定</button>
					</p>
				</div>
				<div class="eye">
					<a class="delete" href="javascript:deleteById(${storageFileInfo.id!})" title="删除图片">删除图片</a>
					<a class="back" href="javascript:window.history.back()" title="返回上一页">返回上一页</a>
				</div>
			</div>
			<div class="main">
				<div class="imgwrap">
					<div class="img">
						<img src="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_310x310.jpg" alt="310x310" />
					</div>
				</div>
				<div class="text">
					<ul>
						<li class="cat">所属分类：${(storageFileInfo.imageCategoryName)?if_exists?html}  [<a href="#" class="recatdetil"  title="移动">移动</a>] </li>
						<li>上传时间：${storageFileInfo.gmtCreate?string("yyyy-MM-dd")!}</li>
						<li>原图大小：<#if storageFileInfo.size?exists>${(storageFileInfo.size/1024)?string("0.00")}KB</#if></li>
						<li>原图尺寸：${storageFileInfo.dimension!} </li>
						<li>[ <a href="${imageServer}${storageFileInfo.path}${storageFileInfo.name}" title="查看原图">查看原图</a> ]</li>
					</ul>
					<div class="sizeshow">
						<h3>多尺寸使用</h3>
						<ul>
							<li>
								<span class="size">原：${storageFileInfo.dimension!} </span>
								<a href="#" title="复制链接" class="copylink" id="copylink-1200" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}">复制链接</a> 
								<a href="#" title="复制代码"class="copycode" id="copycode-1200" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}">复制代码</a> 
								<a href="${imageServer}${storageFileInfo.path}${storageFileInfo.name}" title="查看">查看</a>
							</li>
							<li>
								<span class="size">大：310×310</span>  
								<a class="copylink" id="copylink-0001" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_310x310.jpg" href="#" title="请复制链接">复制链接</a>
						        <a class="copycode" id="copycode-0001c" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_310x310.jpg" href="#" title="请复制代码">复制代码</a>
								<a href="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_310x310.jpg" title="查看">查看</a>
							</li>
							<li>
								<span class="size">中：160×160</span>  
								<a class="copylink" id="copylink-0001" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_160x160.jpg" href="#" title="请复制链接">复制链接</a>
						        <a class="copycode" id="copycode-0001c" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_160x160.jpg" href="#" title="请复制代码">复制代码</a>
								<a href="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_160x160.jpg" title="查看">查看</a>
							</li>
							<li>
								<span class="size">小：80×80</span>  
								<a class="copylink" id="copylink-0001" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_80x80.jpg" href="#" title="请复制链接">复制链接</a>
						        <a class="copycode" id="copycode-0001c" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_80x80.jpg" href="#" title="请复制代码">复制代码</a>
								<a href="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_80x80.jpg" title="查看">查看</a>
							</li>
							<li>
								<span class="size">小：40×40</span>  
								<a class="copylink" id="copylink-0001" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_40x40.jpg" href="#" title="请复制链接">复制链接</a>
						        <a class="copycode" id="copycode-0001c" data-url="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_40x40.jpg" href="#" title="请复制代码">复制代码</a>
								<a href="${imageServer}${storageFileInfo.path}${storageFileInfo.name}_40x40.jpg" title="查看">查看</a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
</form>
<!-- // content -->



</body>
</html>