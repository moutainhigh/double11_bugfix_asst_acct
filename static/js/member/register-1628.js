(function() {
  $('input').each(function() {
    $(this).attr('class', $(this).attr('class') || '');
  });

  // 在会员名处获得焦点
  $('input[name="loginName"]').focus();
  $('input[name="loginName"]').select();
})();

(function() {
  // stop copy, cut, paste, contextmenu event
  $('input[type="text"],input[type="password"]').bind({
    'copy cut paste contextmenu' : function() { return false; }
  });
})();

(function() {

  // 同意用户协议提示
  $('#agreement').click(function() {
    if($(this).attr('checked')) {
      $('#agreement-msg').hide();
    } else {
      $('#agreement-msg').addClass('point').text('需要同意用户协议后才能进行注册').show();
    }
  });

  // 检查用户是否同意用户协议
  var checkAgreement = function() {
    if(!$('#agreement').attr('checked')) {
      $('#agreement-msg').addClass('point').text('需要同意用户协议后才能进行注册').show();
      return false;
    }
    return true;
  };

  var showbox = function(message) {
    var box = $('div.err_box').find('span.point');
    $('#password, #confirm, #captcha').val('');
    $('#password').focus();
    if (box.length < 1) {
      $('div.err_box').append('<span class="point">' + message + '</span>');
      return;
    }
    box.text(message);
  }

  // 表单数据校验
  $('#register-form').effectiveAndValidate({
    submitHandler : function(form) {
      if(!checkAgreement()) {
        return;
      }
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
          var message = res ? '网络通信异常，注册失败' : '网络连接超时，注册失败';
          showbox(message);
        }
      });
    },

    invalidHandler : function(form, validator) {
      checkAgreement();
    },

    rules : {
      'loginName' : {
          required : true,
          byterangelength: [4,20],
          pattern: /^[\u4e00-\u9faf_0-9a-zA-Z-]+$/,
          pattern2: /^[\u4e00-\u9fafa-zA-Z].*$/,
          remote: {
              type : 'POST',
              async : true,
              url : BASE + '/async/member/nickname.htm',
              data : { nickname : function() { return $('input[name="loginName"]').val();}},
              dataType : 'json',
              dataFilter : function(json) {
                  var j = eval('(' + json + ')');
                  if(!j) return false;
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
        },
      'email' : {
          required : true,
          email: true,
          remote: {
              type : 'POST',
              async : true,
              url : BASE + '/async/member/email.htm',
              dataType : 'json',
              dataFilter : function(json) {
                  var j = eval('(' + json + ')');
                  if(!j) return false;
                  return j.result + '';
                }
            }
        },
      'captcha' : {
          required: true,
          minlength: 4,
          maxlength: 4,
          pattern: /^[0-9A-Za-z]{4}$/
        }
    },
    messages : {
      'loginName' : {
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
        },
      'email' : {
          required: '请输入邮箱地址',
          email: '邮箱地址不正确',
          remote: '该邮箱已经被其他会员使用了'
        },
      'captcha' : {
          required: '请输入验证码',
          minlength: '验证码应为 4 个字符',
          maxlength: '验证码应为 4 个字符',
          pattern: '验证码应由英文字母和数字组成'
        }
    },
    errorPlacement : function(error, element) {
      element[0].showError( error.text() );
    }
  }
  ,
  { wrapSelector : 'li',
    ignoreSelectors: ['#agreement'],
    inputNormalClass: '',
    inputErrorClass: 'error',
    message: {
        defaultClass: 'error',
        errorClass: 'point'
      }
  }
  );
})();