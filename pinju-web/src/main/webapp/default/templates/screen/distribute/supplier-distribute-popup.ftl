<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 产品环境视情况决定缓存策略 -->
<meta http-equiv="expires" content="0" />
<meta name="robots" content="all" />
<meta name="googlebot" content="all" />
<meta name="baiduspider" content="all" />
<meta name="keywords" content="页面关键字" />
<meta name="description" content="页面描述" />

<title>分销商${(nickName!"")?html}授权</title>
	<!--分销页面样式-->
	<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
	
	<script src="http://static.pinju.com/jquery/jquery-1.6.2.min.js"></script>
	</head>
<body>
<div class="formwrap">
	<h1 class="title">分销商 <em>${(nickName!"")?html}</em> 申请信息:</h1>
	<ul>
		<li class="row"><label>用户：</label> <#if nickName?exists>${nickName!""}</#if></li>
		<li class="row"><label>合作备忘录：</label> <textarea max="1000" id="popupRemark" name="remark" cols="20" rows="8"></textarea></li>
		<li class="row"><label>合作期限：</label> 
			<select id="popupMonth" name="select">
				<option value="1">1</option> 
				<option value="2">2</option> 
				<option value="3">3</option> 
			</select> <span>个月</span>
		</li>
	</ul>
    
	<div class="imgbtn" style="text-align: right;">
		<button name="button" type="button" class="btn-sgray" onclick="window.parent.dialog.close();">取 消</button>
		<button name="button" type="submit" class="btn-sorange" onclick="submit()">确 认</button>
	</div>    
</div>
</body>
</html>
<script type="text/javascript">
	var index = "${(index!"")?js_string}";
	function submit(){
		var _max = $('#popupRemark').attr('max');
	    var _length = $.trim($('#popupRemark').val()).length;
	    if(_length <= 0){
	    	alert('请输入备忘录内容');
	    }else if(_length <= _max){
			var remark = $("#popupRemark").attr("value");
			var month = $("#popupMonth").attr("value");
			if(confirm("确定要同意该分销商的申请吗？")) {
				if(0 == index.length){
					window.parent.passAll(month, remark);
				}else{
					window.parent.passOne(index, month, remark);
				}
				window.parent.dialog.close();
			}
		}else{
			alert('备忘录内容不得超过1000字');
		}
	}
</script>
