(function() {
  $('span.error').each(function(){
    if($(this).attr('class').indexOf('point') < 0) {
      $(this).hide();
    }
  });
  $('input').each(function() {
    $(this).attr('class', $(this).attr('class') || '');
  });

  // 在会员名处获得焦点
  $('input[name="email"]').focus();
  $('input[name="email"]').select();
})();

(function() {

  // 表单数据校验
  $('#form').effectiveAndValidate({
    submitHandler : function(form) {
      form.submit();
    },

    rules : {
      'email' : {
        required : true,
        email: true,
        maxlength: 100
      },
      'captcha' : {
          required: true,
          minlength: 4,
          maxlength: 4,
          pattern: /^[0-9A-Za-z]{4}$/
        }
    },
    messages : {
      'email' : {
          required: '请输入邮箱地址',
          email: '邮箱地址不正确',
          maxlength: '邮箱地址应在 {0} 个字符以内'
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
    inputNormalClass: '',
    inputErrorClass: 'error',
    message: {
        defaultClass: 'error',
        errorClass: 'point'
      }
  }
  );
})();