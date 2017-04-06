	<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css" />
	<script src="http://static.pinju.com/kindeditor/kindeditor-min.js"></script>
	<script src="http://static.pinju.com/kindeditor/config.js"></script>
	<script src="http://static.pinju.com/kindeditor/lang/zh_CN.js"></script>
    <#setting classic_compatible=true>
		<!--========================================================编辑页面弹出层开始-->
		
			
		<!--===模块编辑——店铺招牌，开始-->
		<form action="../shopDecoration/saveDesignModuleAction.htm" method="post">
			
            <!--===自定义编辑器开始-->
            <textarea id="customCode" name="html" style="width:600px;height:480px;visibility:hidden;">${html!?html}</textarea>
            	<!--&nbsp;&nbsp;&nbsp;&nbsp;源码：已输入<span id="customCodeLen">0</span> 最多输入<font color='red'>4000</font>个字符-->
            	<!--&nbsp;&nbsp;&nbsp;&nbsp;源码：已输入<span id="customCodeLen">0</span> 最多输入<font color='red'>4000</font>个字符-->
            <!--===自定义编辑器结束-->
				<input type="hidden" name="moduleId" value="${_MODULEID?if_exists}">
				<input type="hidden" name="id" value="${_ID}">
				<input type="hidden" name="userPageId" value="${_USERPAGEID?if_exists}">
				
				<br />
				<input class="save"  type="submit" value="保存"><input class="cancel"  type="button" value="取消" onclick="window.parent.dialog.close();" />
				<!--<input class="save" type="submit" value="保存"><input class="cancel" type="button" value="取消">-->
		</form>
		<!--===模块编辑——店铺招牌，结束-->
		
		<!--========================================================-->
    
    <script>
        $(function() {
			KindEditor.create('#customCode', {
				items : [ 'source', '|', 'undo', 'redo', 'cut', 'copy', 'paste', '|',
					'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent', '|', 'selectall', '|', 'fontname', 'fontsize', 'image',
					'|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'removeformat', '/', 'table', 'hr', 'emoticons', 'link', 'unlink' ],
				width : 675,
				height : 450,
				minHeight : 195,
				filterMode : true,//过滤html代码
				resizeType : 0, //2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。
				uploadJson : '/image/uploadImage.htm?size=1024&type=2',
				fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
				categoryJson : '/images/getImagesCategoryJsonActon.htm',
				allowFileManager : true
			});
		});
    </script>
    
