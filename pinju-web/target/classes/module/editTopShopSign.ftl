		<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css" />
		<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
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
				K('#image').click(function() {
					editor.loadPlugin('image', function() {
						editor.plugin.imageDialog({
							imageUrl : K('#url').val(),
							clickFn : function(url, title, width, height, border, align) {
								K('#url').val(url);
								editor.hideDialog();
							}
						});
					});
				});
			});
		</script>
		<!--===模块编辑——店铺招牌，开始-->
		<form name="form" action="saveDesignModuleAction.htm" method="post" enctype="multipart/form-data">
		<div class="lay_content">
			<table class="table_text">
				<caption>显示设置</caption>
				<tr>
					<th>头部高度：</th>
					<td><input type="text" name="height" class="txt" value="${height?if_exists}">像素（Pixel）</td>
				</tr>
				<tr>
					<th>是否显示：</th>
					<td>
						<label><input type="checkbox" name="hasShopLogo" <#if hasShopLogo?exists && hasShopLogo=="on">checked="true"</#if>>店铺标志</label>
						<label><input type="checkbox" name="hasShopName" <#if hasShopName?exists && hasShopName=="on">checked="true"</#if>>店铺标题</label>
						<label><input type="checkbox" name="hasShopImage" <#if hasShopImage?exists && hasShopImage=="on">checked="true"</#if>>显示背景图</label>
					</td>
				</tr>
				<tr>
					<th>更换背景图片：</th>
					<td><input type="text" readonly id="url" name="url" value="" /><input id="image" name="imageFile" type="button" value="选择图片">	</td>
				</tr>
				<tr>
					<th></th>
					<#if imageUrl?exists><input type="hidden" name="imageUrl" value="${imageUrl}"/>
					<td>
						<img height="100" width="300" src="${imageUrl?if_exists}"/>
						<#if imageName?exists><input type="hidden" name="imageName" value="${imageName}"/>
						<p>${imageName?if_exists}</p>
						</#if>
					</td>
					</#if>
						
					
					
					
				</tr>
			</table>			
			<input type="hidden" name="id" value="<#if _ID??>${_ID?string("0")}</#if>" />
			<input type="hidden" name="moduleId" value="7" />
			<input type="hidden" name="userPageId" value="${_USERPAGEID}" />
			<ul class="btn-weiz">
				<input class="save" type="submit" value="保存"><input class="cancel" type="button" value="取消" onclick="window.parent.dialog.close();" />
			</ul>
		</div>
		</form>
		<!--===模块编辑——店铺招牌，结束-->