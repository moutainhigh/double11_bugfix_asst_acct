(function() {
  // stop copy, cut, paste, contextmenu event
  $('input[type="text"],input[type="password"]').bind({
    'copy cut paste contextmenu' : function() { return false; },
    'focus': function() { $(this).select() }
  });
})();


(function(){
   // 在会员名处获得焦点
  $('#nickname').focus();
  $('#nickname').select();

  var clearMessage = function() {
	$('#errorico').removeClass('error');
	$('#errorMessage').text('');
  };

  var showbox = function(message) {
	$('#errorico').addClass('error');
	$('#errorMessage').text(message);
  }
  
   // 表单数据校验
  $('#sina-register-form').effectiveAndValidate({
    submitHandler : function(form) {
      Encrypt.encrypt({
        timeout : 15000,
        success : function(key, iv, res) {
          $('input[name="tid"]').val(res.tid);
          var p = $('#password');
          p.val(Security.encryptHex(p.val(), key, iv));
          var c = $('#confirm');
          c.val(Security.encryptHex(c.val(), key, iv));
          form.submit();
        },
        error : function(key, iv, res) {
          var message = res ? '网络通信异常，处理失败' : '网络连接超时，处理失败';
          showbox(message);
        }
      });
    },

   rules : {
      'nickname' : {
          required : true,
          byterangelength: [4,20],
          pattern: /^[\u4e00-\u9faf_0-9a-zA-Z-]+$/,
          pattern2: /^[\u4e00-\u9fafa-zA-Z].*$/,
          remote: {
              type : 'POST',
              async : true,
              url : BASE + '/async/member/nickname.htm',
              data : { nickname : function() { return $.trim($('#nickname').val());}},
              dataType : 'json',
              dataFilter : function(json) {
                  var j = eval('(' + json + ')');
                  if(!j) return false;
                  if (j.result) {
					clearMessage();
                  }
                  return j.result + '';
                }
            }
        },
	   'password' : {
		  required: true,
		  rangelength: [6,16],
		  pattern: /^[\x21-\x7e]+$/
		},
      'confirm' : {
          equalTo: 'input[name="password"]'
        }
    },
    messages : {
      'nickname' : {
          required : '请输入会员名',
          byterangelength: '会员名为 {0}～{1} 个字母、数字、“-” 和下划线或者 2～10 个汉字',
          pattern: '会员名由英文字母、数字、汉字、“-” 和下划线组成',
          pattern2: '会员名需要以英文字母或汉字开头',
          remote: '该会员名已经被其他会员使用了'
        },
		'password' : {
		  required: '请输入密码',
		  rangelength: '密码应在 {0}～{1} 个字符内',
		  pattern: '密码仅允许数字、英文字母及符号'
		},
	   'confirm' : {
		  equalTo: '确认密码与密码不一致'
		}
    },
    errorPlacement : function(error, element) {
      $("#errorico").removeClass("error").addClass("error");
      $("#errorMessage").empty().append(error.text());
    }
  }
  ,
  { wrapSelector : 'li'
  }
  );
})();