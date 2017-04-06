<title>发布招募</title>
<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css" />
<script src="http://static.pinju.com/kindeditor/kindeditor-min.js"></script>
<script src="http://static.pinju.com/kindeditor/config.js"></script>
<script src="http://static.pinju.com/kindeditor/lang/zh_CN.js"></script>
<link href="http://static.pinju.com/css/jin/sns-base.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/jin/management.css" rel="stylesheet" />

<div class="right_content02"><!--分销大框架开始 -->
        
      <div class="box_806 padding_10px fontgray_01 margin_bottom">您好，${userName}，欢迎进入云营销</div>
        
      <div class="box_806 padding_10px border_1px_gray bg_white margin_bottom"><span class="fontgray_03">我是卖家 > 云营销 > </span><span class="fontred">发布爱分享招募信息</span></div><!--标签 end -->
      <form id="releaseSupplierForm" name="releaseSupplierForm" action="${base}/supplier/release.htm" method="post">
      <input type="hidden" name="distributeSupplierDO.id" value="${distributeSupplierDO.id!""}" />
      <input type="hidden" name="distributeSupplierDO.memberId" value="${distributeSupplierDO.memberId!""}" />
      <input type="hidden" name="distributeSupplierDO.nickName" value="${distributeSupplierDO.nickName!""}" />
      <div class="box_806 padding_10px ltr_1px_gray bg_white ">
      		<ul class="prospectus_list">
      		  <li><span class="fontbold">基本信息</span></li>
      		  <li><strong>招募书名称：</strong><input id="applyTitle" name="distributeSupplierDO.applyTitle" type="text" class="w_200" value="${(distributeSupplierDO.applyTitle!"")?html}" maxLength="64"></li>

              <li><strong>招募书：</strong><div class="plugin_box01"><textarea id="applyContent" name="distributeSupplierDO.applyContent" maxLength="2000">${(distributeSupplierDO.applyContent!"")?html}</textarea></div></li>
            </ul> 
      </div><!--基本信息 end -->

	<div class="box_806 padding_10px border_1px_gray bg_white margin_bottom">
      		<ul>
      		  <li><span class="fontbold">供应商联系信息</span></li>
      		  <li>用户名：        	
	      		  <a href="http://sns.pinju.com/dashboard/index?id=${(distributeSupplierDO.memberId)!''}"  target="_blank">
	        		${(distributeSupplierDO.nickName)!''}
	        	  </a>
        	  </li>
            </ul> 
      </div><!--供货商联系信息 end -->

      <div class="box_806 padding_10px fontgray_01" align="center">
       	  <input name="button" type="button" class="orange_button_004" value="发布招募书" onclick="save()">
          <input name="button" type="button" class="gray_button_004 margin_left20" value="取消" onclick="reset()">	
      </div>
      </form>
  	</div><!--分销大框架结束 -->
  	
  	<script type="text/javascript">
  		<#if errorMessage?exists && status == false> 
  			alert('${(errorMessage!"")?js_string}');
  			window.location.href = "/supplier/getAllItem.htm";
  		<#elseif errorMessage?? && status == true>
  			alert('${(errorMessage!"")?js_string}');
  		<#elseif successMessage?? && status == true>
  			alert('${(successMessage!"")?js_string}');
  		</#if>
  		$(function() {
  			
  			$('#releaseSupplier').addClass('count');
  			
			window.EditorObject = KindEditor.create('#applyContent', {
				items : [ 'undo', 'redo', 'cut', 'copy', 'paste', '|',
					'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'indent', 'outdent', '|', 'selectall', '|', 'fontname', 'fontsize',
					'|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'removeformat'],
				width : 680,
				height : 380,
				minHeight : 195,
				filterMode : true,//过滤html代码
				resizeType : 0, //2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。
				afterChange : function(){
					setDisLength();
				}
			});
		});

        function validate(){
        	if(0 == jQuery.trim($("#applyTitle").attr("value")).length){
        		alert('请填写招募书名称');
        		return false;
        	}
        	return true;
        }
        
        function save(){
        	if(validate()){
        	 	EditorObject.sync();
				$("#releaseSupplierForm").submit();
        	}
		}
		
		function setDisLength(){
		
		}
		
		function reset(){
		}
    </script>