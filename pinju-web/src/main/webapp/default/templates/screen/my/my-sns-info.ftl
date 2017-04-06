<#setting classic_compatible=true>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>社区资料</title>
</head>
<body>
<link type="text/css" href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<link media="screen" type="text/css" rel="stylesheet" href="http://static.pinju.com/css/open.css?t=20111205.css" />
<link rel="stylesheet" href="http://static.pinju.com/css/member/member.css" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/pcd.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate.js"></script>

		<h1 class="topic"><strong>社区资料</strong></h1>
		<div class="sft">
			<div class="Personal avatar-wrap">
				<form action="${base}/my/sns-upload.htm" method="post" enctype="multipart/form-data" id="form-input">
				    <input name="pj0" type="hidden" value="${pj0}">
				    <input name="type" type="hidden" value="${type?html}">
				    <#if invokeMessage??><ul class="tips" id="pay-tips"><li style="padding-left:152px;height:10px;">${invokeMessage}</li></ul></#if>
					<ul>
						<li>
							<label>社区头像</label>
							<div><input type="file" id="first" name="avatar" title="社区头像" <#if (validator['avatar'])?exists>class="error" </#if>/></div>
							<div><span class="message error"><#if (validator['avatar'])?exists>${validator['avatar']}</#if></span></div>
							<div class="avatar-show">
								<div class="big"><span><img src="<#if ms.avatarsBasePath?exists>${imageServer}${ms.avatarsBasePath}_100x100.jpg?t=${ms.version}.jpg<#else>http://img.pinju.com/face-default/face_100x100.jpg</#if>" alt="大" /></span></div>&nbsp;
								<div class="medium"><span><img src="<#if ms.avatarsBasePath?exists>${imageServer}${ms.avatarsBasePath}_50x50.jpg?t=${ms.version}.jpg<#else>http://img.pinju.com/face-default/face_50x50.jpg</#if>" alt="中" /></span></div>&nbsp;
								<div class="small"><span><img src="<#if ms.avatarsBasePath?exists>${imageServer}${ms.avatarsBasePath}_30x30.jpg?t=${ms.version}.jpg<#else>http://img.pinju.com/face-default/face_30x30.jpg</#if>" alt="小" /></span></div>&nbsp;
							</div>
						</li>
						<li>
							<label>个人简介</label>
							<div>
								<textarea style="width:400px;height:100px;" id="biography" rows="3" cols="30" name="ms.biography" title="请输入您的个人简介，限 300 字以内" <#if (validator['biography'])?exists>class="error" </#if>>${ms.biography?html}</textarea>
							</div>
							<div>
					          <span class="message error"><#if (validator['biography'])?exists>validator['biography']</#if></span>
					        </div>
						</li>
						<li>
							<label>&nbsp;</label>
							<div class="btnbar">
							     <button id="submitBtn" class="btn-sorange" type="submit"><#if ms.memberId?exists>修改<#else>新增</#if></button>
							</div>
						</li>
					</ul>
				</form>
			</div>
		</div>
 <input type="hidden" value="my-sns-info" id="my-page" />
<script type="text/javascript">

(function() {
  $('#form-input').effectiveAndValidate({
    submitHandler : function(form) {
      window.onbeforeunload = function(){};
      form.submit();
      $("#submitBtn").attr("disabled", "true"); 
    },

    rules : {
      'ms.biography' : {
          maxlength: 300
        }
    },

    messages : {
      'ms.biography' : {
          maxlength: '个人简介不能超过 300 个字符'
        }
    },

    errorPlacement : function(error, element) {
       element[0].showError( error.text() );
    }
  }, 

  { wrapSelector : 'li' } 

  );
})();
</script>

<script type="text/javascript">
pinju.Person.initFocus('#first');
var infodata = pinju.Person.formValues('#form-input');
window.onbeforeunload = function(e) {
  e = e || window.e;
  if(pinju.Person.formValues('#form-input') != infodata) {    
    e.returnValue = '当前页面中的数据有修改，但是还没有保存，是否要离开？';
  }
}
</script>
</body>
</html>
