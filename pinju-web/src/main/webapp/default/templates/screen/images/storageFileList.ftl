<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>图片空间 - 图片分类</title>
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<link href="http://static.pinju.com/css/imghost/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/imghost/imghost.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/openShop/jqui.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/shop/category.js"></script>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=default"></script>
</head>

<body>
<#setting classic_compatible=true>
<script type="text/javascript">
$(document).ready(function() {
(function() {
$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
					$("#startDate").datepicker( {
						changeMonth : true,
						changeYear : true,
						autoSize : false,
						dateFormat: 'yy-mm-dd'
					});
$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
					$("#endDate").datepicker( {
						changeMonth : true,
						changeYear : true,
						autoSize : false,
						dateFormat: 'yy-mm-dd'
					});
})();
});

function checkStorageFileInfo(){
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    if(startDate.length!=0 && endDate.length!=0){
        var startDate = new Date(Date.parse(startDate.replace(/-/g,"/")));
	     var endDate = new Date(Date.parse(endDate.replace(/-/g,"/")));
	     if(startDate > endDate) {
	        $("#error").html('<font color="red">开始日期不能大于结束日期!</font>');
	        return false;
	     }else{
	     	$("#error").html('');
	     }
    }
}
     function checkAll() 
      {
         //var oj=document.searchForm.idCheck;
         var oj = $(".img-select");
           for (var i=0;i<oj.length;i++ ){
              oj[i].checked=true;
            }
      }
      
      function checkNull() 
      {
        //var oj=document.searchForm.idCheck;
        var oj = $(".img-select");
         for (var i=0;i<oj.length;i++ ){
            if (oj[i].checked){
               oj[i].checked=false;
            }else{
              oj[i].checked=true;
            }
          }
       }
       
       function delect(){
       var count=0;
         //var oj=document.searchForm.idCheck;
         var oj = $(".img-select");
         for (var i=0;i<oj.length;i++ ){
            if (oj[i].checked){
              count++;
           }
          }
         if(count==1){
            var r=confirm("删除之后将不能恢复且无法显示，请确保您的店铺没应用到此图片");
            if (r==true){
               $("#pageForm").attr({ action:"/images/deleteStorageFileInfoAction.htm",method:"post" });
               $("#pageForm").submit();
            }
         }else if(count>1){
            var r=confirm("您选择了多个删除，您确定要删除吗？删除之后将不能恢复且无法显示，请确保您的店铺没应用到此图片");
            if (r==true){
                $("#pageForm").attr({ action:"/images/deleteStorageFileInfoAction.htm",method:"post" }); 
                 $("#pageForm").submit();
            }
         }else if(count==0){
           alert("请选择要删除的图片");
         }
       }
       
       function searchByOrder(){
            $("#pageForm").attr({ action:"/images/queryStorageAction.htm",method:"post" }); 
            $("#pageForm").submit();
       }
       
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
 <form name="searchForm" action="/images/queryStorageAction.htm" method="post" id="pageForm" onsubmit="return checkStorageFileInfo();">
	<input type="hidden" id="j_categoryId" name="imageCategoryId" />
	<div style="display:none" >
		<select id="imageCategoryId">
			  <#if imagesCategoryList??>
					<#list imagesCategoryList as imagesCategory>
				                 <option value="${imagesCategory.firstCategoryId}">${(imagesCategory.firstCategoryName)?html}</option>
							   <#list  imagesCategory.secondCategoryList as image>
			                     <option value="${image[0]}">&nbsp;&nbsp;&nbsp;&nbsp;|_${(image[1])?if_exists?html}</option>
							   </#list>
				    </#list>
			  </#if>
		</select>
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
		<div class="image-demand">
			上传日期： <input type="text" name="storageFileInfo.startDate" id="startDate" value="<#if storageFileInfo.startDate?length gt 11>${storageFileInfo.startDate[0..9]}</#if>" /> 至 <input type="text" name="storageFileInfo.endDate" id="endDate" value="<#if storageFileInfo.endDate?length gt 11>${storageFileInfo.endDate[0..9]}</#if>" /> 分类：
			<select id="storageFileInfo.imageCategoryId" name="storageFileInfo.imageCategoryId">
			 <option value="">所有图片</option>
			<#list imagesCategoryList as imagesCategory>
		              <#if imagesCategory.firstCategoryId==storageFileInfo.imageCategoryId>
		                 <option value="${imagesCategory.firstCategoryId}" selected>${(imagesCategory.firstCategoryName)?if_exists?html}</option>
		              <#else>
		               <option value="${imagesCategory.firstCategoryId}">${(imagesCategory.firstCategoryName)?if_exists?html}</option>
		              </#if>
					   <#list  imagesCategory.secondCategoryList as image>
					   <#if image[0]==storageFileInfo.imageCategoryId>
		                  <option value="${image[0]}" selected>&nbsp;&nbsp;&nbsp;&nbsp;|_${(image[1])?if_exists?html}</option>
		              <#else>
		                  <option value="${image[0]}">&nbsp;&nbsp;&nbsp;&nbsp;|_${(image[1])?if_exists?html}</option>
		              </#if>
					   </#list>
				  </#list>
			</select> <button type="submit">搜索</button><span id="error"></span>
		</div>
		<div class="actionbar cf">
			<div class="image-modify">
				<span class="title">批量操作</span>
				<ul> 
					<li><a href="javascript:checkAll()">全选</a>|</li>
					<li><a href="javascript:checkNull()">反选</a>|</li>
					<li><a href="#" class="recat"  title="recat">移动</a>|</li>
					<li><a href="javascript:delect()">删除</a></li>
				</ul>
			</div>
			<div class="eye">
				排序：
				<select id="orderBy" name="storageFileInfo.orderBy" onchange="searchByOrder()">
				     <#list orderList as order>
					      			<#if storageFileInfo.orderBy == order[0]>
										<option value="${order[0]}" selected>${order[1]}</option>
									<#else>
										<option value="${order[0]}" >${order[1]}</option>
									</#if>
									
					 </#list>
				</select>
			</div>
		</div>
	  <#if resultList?exists && resultList.size()!=0 >
		<div class="imgwrap cf">
			<ul>
			   <#list resultList as result>
				<li class="img-item">
					<div class="img">
						<a href="/images/queryStorageFileInfoAction.htm?id=${result.id}" target="_blank"><img src="${imageServer!}${result.path!}${result.name!}_160x160.jpg"></a>
						<input class="img-select" type="checkbox" id="idCheck${result.id!}" value="${result.id!}" name="idCheck"/>
					</div>
					<p class="name">
					    <input class="editoldName" type="hidden" value="${(result.fileName)?if_exists?html}" />
						<span class="title" title="${(result.fileName)?if_exists?html}">${(result.fileName)?if_exists?html}</span>
						<span class="title-edit"></span>
					</p>
					<p class="edit">
						<input class="editId" type="hidden" value="${result.id}" />
						<input class="editName" type="text" maxlength="20" size="13" value="" />
						<button type="button">确定</button>
					</p>
					<p><#if result.size?exists>${(result.size/1024)?string("0.00")}KB</#if></p>
					<p class="act">
						<a href="/images/queryStorageFileInfoAction.htm?id=${result.id}" target="_blank">详情</a> 
						| <a href="javascript:deleteById(${result.id!})">删除</a>
						<br />
						<a class="copylink" id="copylink-000${result.id}" data-url="${imageServer!}${result.path!}${result.name!}_160x160.jpg" href="#" title="请复制链接">复制链接</a>
						| <a class="copycode" id="copycode-000${result.id}c" data-url="${imageServer!}${result.path!}${result.name!}_160x160.jpg" href="#" title="请复制代码">复制代码</a>
					</p>
				</li>
			 </#list>
			</ul>
		</div>
		<div class="actionbar cf">
			<div class="image-modify">
				<span class="title">批量操作</span>
				<ul> 
					<li><a href="javascript:checkAll()">全选</a>|</li>
					<li><a href="javascript:checkNull()">反选</a>|</li>
					<li><a href="#" class="recat" title="recat">移动</a>|</li>
					<li><a href="javascript:delect()">删除</a></li>
				</ul>
			</div>
		</div>
		<#include "/default/templates/control/bottomPage.ftl">
	<#else>
    <div class="upload-img">
			<span>${searchMsg!}</span>
	       <#if searchMsg=='此分类下还没有图片'>
			<a href="http://www.pinju.com/images/viewImagesUpLoadAction.htm?categoryId=${storageFileInfo.imageCategoryId}">上传图片</a>
		   </#if>
	</div>
	</#if>
	</div>
</div>
</form>
<!-- // content -->
</body>
</html>