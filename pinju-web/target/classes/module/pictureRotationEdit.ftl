<body>
<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css" />
<script src="http://static.pinju.com/kindeditor/kindeditor-min.js"></script>
<script src="http://static.pinju.com/kindeditor/config.js"></script>
<script src="http://static.pinju.com/kindeditor/lang/zh_CN.js"></script>
<script>
			KindEditor.ready(function(K) {
				var editor = K.editor({
					uploadJson : '/image/uploadImage.htm?size=1024&type=2',
					fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
					categoryJson : '/images/getImagesCategoryJsonActon.htm',
					readonlyImageUrl : true,
					allowFileManager : true
				});
				K('#J_imageBtn').click(function() {
					editor.loadPlugin('image', function() {
						editor.plugin.imageDialog({
							imageUrl : K('#J_imageUrl').val(),
							clickFn : function(url, title, width, height, border, align) {
								K('#J_imageUrl').val(url);
								editor.hideDialog();
							}
						});
					});
				});
			});
		</script> 
<#setting number_format="#">
<#setting classic_compatible=true>
		<div class="lay_content">
		<form id="picUpload" action="${base}/shopDecoration/saveDesignModuleAction.htm" method="post" enctype ="multipart/form-data">
			<input type = "hidden" value="${pageId}" name="userPageId">
			<input type = "hidden" value="${moduleId}" name="moduleId">
			<input type = "hidden" value="${userId}" name="userId" id="userId">
			<input type="hidden" id="fileServer" name="fileServer" value="${fileServer}">
			<#if moduleTitle??>
			<table class="table_text">
				<tr>
					<th>模块标题：</th>
					<td><input type="text" class="txt" name="moduleTitle" id="moduleTitle" value="<#if moduleTitle?exists>${moduleTitle?html}</#if>">
					<label>
						<#if isShowTitle?? && (isShowTitle == "0" || isShowTitle == "")>
							<input type="checkbox" id="isShow" onclick="changeIsShowTitle()">显示标题栏
							<input type="hidden" name="isShowTitle" id="isShowTitle" value="${isShowTitle}">
						<#else>
							<input checked id="isShow" type="checkbox" onclick="changeIsShowTitle()" >显示标题栏
							<input type="hidden" name="isShowTitle" id="isShowTitle"  value="${isShowTitle}">
						</#if>
					</label>
					<span>（30个字符以内）</span></td>
				</tr>
			</table>
			<table class="table_text">
				<caption>模块设置</caption>
				<tr>
					<th>模块高度：</th>
					<td>
					  <label>
					  <select name="moduleheight" id="moduleheight" onchange="addCustomerHeight()">
					  		<#if pictureRotationHeightList??>
						  			<#list pictureRotationHeightList as height>
						  				<#if moduleheight == ""+height_index>
						  					<option selected value="${height_index}">${height}</option>
						  				<#else>
						  					<option value="${height_index}">${height}</option>
						  				</#if>
						  			</#list>
						  		</#if>
					  		
					  </select>
				      </label>
				      <#if moduleheight == 6>
				      	<input id="moduleheightCustomer" name="moduleheightCustomer"  type="text" class="txt" value="${moduleheightCustomer}" > 模块宽度为750px
				      <#else>
				      	<input id="moduleheightCustomer" name="moduleheightCustomer" style="display:none" type="text" class="txt" value="0" > 模块宽度为750px
				       </#if>
					  
					</td>
				</tr>
				<tr>
					<th>切换效果：</th>
					<td>
					  <label>
					  <select name="effect" id="effect">
					  		<#if pictureRotationEffectList??>
						  			<#list pictureRotationEffectList as effectLst>
						  				<#if effect == effectLst_index>
						  					<option selected value="${effectLst_index}">${effectLst}</option>
						  				<#else>
						  					<option value="${effectLst_index}">${effectLst}</option>
						  				</#if>
						  			</#list>
						  	</#if>
					  </select>
				      </label>
					</td>
				</tr>
			</table>
			
			
			<input type="hidden" id="imagePaths" name="imagePath" value="">
			<input type="hidden" id="num" name="num" value="">
			<input type="hidden" id="urlResultValue" name="urlResultValue" value="">
			
			<ul id="SlideImageList" class="lister">
				<li class="lister_title"><cite>操作</cite><span>上传图片</span><span>链接地址</span></li>
				<#if files??>
					<#list files as file>
						<li class="slide-image">
							<cite>
								<a class="move-up" href="#"><img src="http://static.pinju.com/images/sc-aroup-11.gif"></a>
								<a class="move-down" href="#"><img src="http://static.pinju.com/images/sc-arodown-11.gif"></a>
								<a class="delete-img" href="#"><img src="http://static.pinju.com/images/sc-delete-11.gif"></a>
							</cite>
							<span>
								<img width="40" height="40" src="${fileServer}${file}_40x40.jpg">
								<input type="hidden" class="imgFileHidden" name="imgFileHidden" value="${file}">
							</span>
							<span>
								<input type='text' class='tagetUrl' value="${urlString[file_index]}"/>
							</span>
						</li>
					</#list>
					
				<#else>
			
					<li class="slide-image">
						<cite><a class="move-up" href="#"><img src="http://static.pinju.com/images/sc-aroup-11.gif"></a><a class="move-down" href="#"><img src="http://static.pinju.com/images/sc-arodown-11.gif"></a><a class="delete-img" href="#"><img src="http://static.pinju.com/images/sc-delete-11.gif"></a></cite>
						<span><input type="text" readonly name="url" class="url" id="J_imageUrl" value="" /> <input type="button" id="J_imageBtn" value="选择图片" /></span>
						<span><input type='text'  class='tagetUrl'/></span>
					</li>
					
				</#if>
				
				
			
			
				
				
				<li class="add_button">
					<input class="cancel" type="button" value="添加" id="AddImage" />
				</li>
			</ul>		
			
			<#else>
						<table class="table_text">
						<tr>
							<th>模块标题：</th>
							<td><input type="text" class="txt" name="moduleTitle" id="moduleTitle" value="">
							<label>
									<input type="checkbox" id="isShow" onclick="changeIsShowTitle()">显示标题栏
									<input type="hidden" name="isShowTitle" id="isShowTitle">
							</label>
							<span>（30个字符以内）</span></td>
						</tr>
					</table>
					<table class="table_text">
						<caption>模块设置</caption>
						<tr>
							<th>模块高度：</th>
							<td>
							  <label>
							  <select name="moduleheight" id="moduleheight" onchange="addCustomerHeight()">
							  		<#if pictureRotationHeightList??>
								  			<#list pictureRotationHeightList as height>
								  				<option value="${height_index}">${height}</option>
								  			</#list>
								  		</#if>
							  		
							  </select>
						      </label>
							  <input id="moduleheightCustomer" name="moduleheightCustomer" style="display:none" type="text" class="txt" value="0" > 模块宽度为750px
							</td>
						</tr>
						<tr>
							<th>切换效果：</th>
							<td>
							  <label>
							  <select name="effect" id="effect">
							  		<#if pictureRotationEffectList??>
								  			<#list pictureRotationEffectList as effect>
								  					<option value="${effect_index}">${effect}</option>
								  			</#list>
								  	</#if>
							  </select>
						      </label>
							</td>
						</tr>
					</table>
					
					
					<input type="hidden" id="imagePaths" name="imagePath" value="">
					<input type="hidden" id="num" name="num" value="">
					<input type="hidden" id="urlResultValue" name="urlResultValue" value="">
					<ul id="SlideImageList" class="lister">
						<li class="lister_title">
						<cite>操作</cite><span>上传图片</span><span>链接地址</span></li>
						<li class="slide-image">
							<cite>
								<a class="move-up" href="#"><img src="http://static.pinju.com/images/sc-aroup-11.gif"></a>
								<a class="move-down" href="#"><img src="http://static.pinju.com/images/sc-arodown-11.gif"></a>
								<a class="delete-img" href="#"><img src="http://static.pinju.com/images/sc-delete-11.gif"></a>
							</cite>
							<span>
								<input type="text" readonly name="url" class="url" id="J_imageUrl" value="" /> <input type="button" id="J_imageBtn" value="选择图片" />
							</span>
							<span>
								<input type='text' class='tagetUrl'/>
							</span>
						</li>
						<li class="add_button">
					<input class="cancel" type="button" value="添加" id="AddImage" />
				</li>
						
					</ul>
					
			
			</#if>	
			
			<br><br><br>		
				<input type="hidden" name="id" value="${_ID}">
				
				
				<input class="save" id="saveImages"  type="button" value="保存"><input class="cancel" type="button" value="取消" onclick="checkPictureRotationSave()" />
				<!--<input id="saveImages" class="save" type="button" value="保存">-->
				
				
			</form>
			
			
			
			<textarea id="ImageTemplate" style="display:none;">
				<li class="slide-image">
					<cite><a class="move-up" href="#"><img src="http://static.pinju.com/images/sc-aroup-11.gif"></a><a class="move-down" href="#"><img src="http://static.pinju.com/images/sc-arodown-11.gif"></a><a class="delete-img" href="#"><img src="http://static.pinju.com/images/sc-delete-11.gif"></a></cite>
					<span><input type="text" readonly name="url" class="url" value="" /> <input type="button" class="image" value="选择图片" /></span>
					<span><input type='text' class='tagetUrl'/></span>
				</li>
		</textarea>
		</div>
    </body>
<