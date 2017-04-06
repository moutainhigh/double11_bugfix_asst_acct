(function() {
  // placeholder
  $('input[placeholder]').placeholderEnhanced();
  // select text when gather focus
  $('#loginName,#password,#captcha').focus(function() {$(this).select();});
})();

(function() {
  // login page image
  var img = $("#login-left-ad img");
  var logo = $('#img10');
  var s = null;
  img.css('opacity','.4');
  logo.css('opacity', '1');
  $('#w-logo').hover(function() {
    $(this).removeClass('w-logo-g');
    clearTimeout(s);
    img.css('opacity','.4');
  }, function() {
    $(this).addClass('w-logo-g');
  });

  img.hover(function() {
    clearTimeout(s)
    img.css('opacity','.4');
    $(this).css('opacity', '1');
  }, function() {
    img.css('opacity','.4');
    s = setTimeout(function() {
      logo.css('opacity','1');
    }, 100);
  });

  $(".login-left-ad a").attr("hidefocus","hidefocus");
})();

(function() {

  // stop copy, cut, paste, contextmenu event
  $('#loginName,#password,#captcha').bind({
    'copy' : function() { return false; },
    'cut'  : function() { return false; },
    'paste': function() { return false; },
    'contextmenu': function() { return false; }
  });

  // show message box
  var msg = function(target, message) {
    var container = $('#message');
    container.addClass('error');
    container.text(message);
    container.show();
    if (target) {
      target.focus();
      target.select();
    }
    return false;
  };

  var clearmsg = function() {
    var container = $('#message');
    container.removeClass('error');
    container.hide();
    container.text('');
  };

  var getData = function(selector, key, iv, res) {
    var data = $(selector).serializeArray();
    var pk = [];
    for (var i = 0, k = data.length; i < k; i++) {
      if (data[i].name == 'password') {
        data[i].value = Security.encryptHex(data[i].value, key, iv);
      }
    }
    // data.push({ name:tid, value:res.tid });
    return data;
  };

  /**
   * 校验是否需要显示验证码
   */
  var showCaptcha = function(loginName){
    if (!loginName) {
      return;
    }
    loginName = $.trim(loginName);
    var len = 0;
    for (var i = 0, k = loginName.length; i < k; i++) {
      len += (loginName.charCodeAt(i) < 0xff) ? 1 : 2;
    }
    // 如果不足 4 个字节的话的不处理
    if (len < 4) {
      return;
    }
    $.ajax({
        type : 'POST',
        url : BASE + '/async/member/captcha.htm',
        data : {'check' : loginName },
        success : function(_m) {
          var old = $('#checkCaptcha').val() == 'true';
          $('#checkCaptcha').val(!!_m.result);
          if(_m.result) {
            if (!old) {
              changeCaptcha();
            }
            $('#captcha-block').show();
          } else {
            $('#captcha-block').hide();
          };
        }
    });
  };

  var unb = function() {
    var title = '登录中';
    var b = $('#pinju-login');
    b.unbind('click').attr('title', title).text(title);
    $('#login-box').addClass('loadding');
  };

  var bnd = function() {
    var title = '登录';
    var b = $('#pinju-login');
    b.bind('click', login).attr('title', title).text(title);
    $('#login-box').removeClass('loadding');
  };

  var loginFailed = function(message) {
    msg($('#password'), message);
    $('#password').val('');
    $('#captcha').val('');
    if ($('#checkCaptcha').val() == 'true') {
      changeCaptcha();
    }
    bnd();
  };

  var serialize = function(obj) {
    if (!obj) return obj;
    var kv = [];
    for (var key in obj) {
      kv.push(key + '=' + obj[key]);
    }
    return kv.join('\n');
  };

  var login = function() {
    // 登录账户校验
    var loginNameObj = $('#loginName');
    var loginName = $.trim(loginNameObj.val());
    if (loginName.length==0) {
      return msg(loginNameObj, '请输入登录账号');
    }
    if (!/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(loginName)) {
      var length = 0;
      for (var i = 0, k = loginName.length; i < k; i++) {
        length += (loginName.charCodeAt(i) < 0xff) ? 1 : 2;
      }
      if (length < 4 || length > 20) {
        return msg(loginNameObj, '登录账号为 4～20 个字母、数字、“-” 和下划线或者 2～10 个汉字或者验证后的 Email 地址、手机号码');
      }
      if (!/^[\u4e00-\u9faf_0-9a-zA-Z-]+$/.test(loginName)) {
        return msg(loginNameObj, '登录账号应由英文字母、数字、汉字、“-” 和下划线组成');
      }
      if (!/^[\u4e00-\u9faf0-9a-zA-Z].*$/.test(loginName)) {
        return msg(loginNameObj, '登录账号应以英文字母、数字或汉字开始');
      }
    }

    // 密码校验
    var passwordObj = $('#password');
    var password = passwordObj.val();
    if (password.length == 0) {
      return msg(passwordObj, '请输入密码');
    }
    if (password.length < 6 || password.length > 16) {
      return msg(passwordObj, '密码应为 6～16 个字符内');
    }
    if (!/^[\x21-\x7e]+$/.test(password)) {
      return msg(passwordObj, '密码仅允许数字、英文字母及符号');
    }

    // 验证码校验
    if ($('#checkCaptcha').val()=='true') {
      var captchaObj = $('#captcha');
      var captcha = captchaObj.val();
      if (captcha.length==0) {
        return msg(captchaObj, '请输入验证码');
      }
      if (captcha.length!=4) {
        return msg(captchaObj, '验证码需要有 4 个字符');
      }
      if (!/^[0-9A-Za-z]{4}$/.test(captcha)) {
        return msg(captchaObj, '验证码应由英文字母和数字组成');
      }
    }
    unb();

    Encrypt.encrypt({
      timeout : 15000,
      success : function(key, iv, res) {
        $('input[name="tid"]').val(res.tid);
        $.ajax({
          type : 'POST',
          url : 'http://www.pinju.com/member/login-dox.htm',
          data : getData('#login-form', key, iv, res),
          timeout: 15000,
          success : function(data, status) {
              if (!data) {
                msg(null, '登录失败，请稍候重试');
                return;
              }
              if (data.result == 0) {
                top.location.href = data['returnUrl'] || 'http://www.pinju.com/';
                return;
              }
              $('#login-left-ad').hide();
              $('#login-hint').show();
              var chkcaptcha = $('#checkCaptcha');
              chkcaptcha.val(data.showCaptcha || 'false');
              var password = $('#password');
              password.val('');
              $('#captcha').val('');
              if (data.showCaptcha && chkcaptcha.val() == 'true') {
                $('#captcha-block').show();
                changeCaptcha();
              }
              msg(password, data.message);
              bnd();
            },
          error : function() {
              loginFailed('网络连接超时，登录失败');
            }
        });
      },
      error : function(key, iv, res) {
        var message = res ? '网络通信异常，登录失败' : '登录连接超时，登录失败';
        loginFailed(message);
      }
    });
    return true;
  }

  var a=function() {
    var loginFormObj = $('#login-form');
    var errorCount = loginFormObj.attr('errorcount');
    loginFormObj.attr('errorcount',!errorCount ? 1 : errorCount + 1);
  };

  /**
   * 检查是否需要显示验证码
   */
  $('#loginName').change(function() {
    showCaptcha($(this).val());
  });

  $('#pinju-login').bind('click', login);
  $(document).keydown(function(e) {
    if (e.keyCode == 13) {
      $('#pinju-login').click();
    }
  });
})();