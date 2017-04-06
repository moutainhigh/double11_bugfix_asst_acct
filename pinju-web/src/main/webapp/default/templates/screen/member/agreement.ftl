<#setting classic_compatible=true>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>用户协议确认</title>
</head>

<body>
<link href="http://static.pinju.com/css/common.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
  <div class="wrap-border">
    <div class="sft">
      <ul class="tips">您好，亲爱的用户，欢迎您使用品聚网！由于您首次登陆品聚网，请您先确认用户协议并设置昵称。
      </ul>
      <form action="${base}/agreement/accept.htm" method="post" id="agreement">
      <table>
        <tr>
          <th>昵称(*)：</th>
          <td style="width:350px;">
            <input name="nickname" type="text" class="text" placeholder="昵称在 20 个字以内，一经填写将不允许修改"><span class="right"><img id="ok" src="http://static.pinju.com/img/icon-succeed.png"></span>
          </td>
        </tr>        
        <tr>
          <th></th>
          <td>
            <div class="tips" id="tips">
              ${invokeMessage}
              ${(validator['nickname'])!}
            </div>
          </td>
        </tr>
      </table>
<script type="text/javascript">
(function(){$('#ok').hide();})();
<#if !invokeMessage?? && !(validator['nickname'])??>
(function(){$('#tips').hide();})();
</#if>
</script>
      <ul class="button-main">
        <li class="only"><input type="submit" value="我同意用户协议" class="button"></li>
        <input type="hidden" name="returnUrl" value="${returnUrl}">
      </ul>
      </form>
      <ul class="agreement">
        <strong>品聚网用户协议</strong>
        <p></p>
      </ul>
    </div>
  </div>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/pcd.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage.js"></script>
<script type="text/javascript">
(function(){
$.validator.addMethod("pattern", function(value, element, parameter) {
        return parameter.test(value);
      }, 'format error');
var validate = $('#agreement').validate({
    submitHandler : function() {
      window.onbeforeunload = function(){};
      form.submit();
    },
    invalidHandler : function(form, validator) {
      var errors = validator.numberOfInvalids();
      if ( errors ) {
        alert('个人信息有 ' + errors + ' 项数据有误，请重新填写！');
      }
    },
    rules : {
        'nickname' : {
          required : true,
          minlength: 3,
          maxlength: 20,
          pattern : /^[0-9a-zA-Z\u4e00-\u9faf_]+$/,
          remote: {
              type : 'POST',
              async : true,
              url : '${base}/my/nickname.htm',
              data : { nickname : function() { return $('input[name="nickname"]').val();}},
              dataType : 'json',
              dataFilter : function(json) {
                  var j = eval('(' + json + ')');
                  if(!j) return false;
                  return j.result + '';
                }
            }
        }
    },

    messages : {
      'nickname' : {
          required : '昵称不能为空',
          minlength: '昵称至少为 3 个字符',
          maxlength: '昵称不能超过 20 个字符',
          pattern: '昵称只允许数字、大小写英文字母、下划线或者汉字',
          remote : '该昵称已经被其他会员使用了'
        }
    },

    errorPlacement : function(error, element) {
      var e = $('#tips');
      e.text( error.text() );
      e.show();
    }
  });
$('input[name="nickname"]').blur(function(){
  if(validate.element(this)) {
    $('#ok').show();
    $('#tips').hide();
  }
});
})();
</script>
</body>
</html>
