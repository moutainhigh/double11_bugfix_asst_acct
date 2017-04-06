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

  $("#selectall").click(function() {
	  if($(this).attr("checked") == 'checked') {
         $("#roleselect").find("input[type='checkbox']").attr('checked', true);
	  } else {
         $("#roleselect").find("input[type='checkbox']").attr('checked', false);
	  }
  });

  $("#roleselect").click(function() {
	  var flag = true;
      $(this).find("input[type='checkbox']").each(function() {
		  if($(this).attr('checked') == undefined) {
              $("#selectall").attr("checked", false);
			  flag = false;
			  return false;
		  }
	  });
	  if (flag) {
		  $("#selectall").attr("checked", true);
	  }
  });
  
  // 表单数据校验
  $('#asst-relation-form').effectiveAndValidate({
    submitHandler : function(form) {
      Encrypt.encrypt({
        timeout : 45000,
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
	   'asstAcctDesc' : {
          maxlength : 20
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
      'asstAcctDesc' : {
          maxlength: '描述不能超过20个汉字'
        }
    },
    errorPlacement : function(error, element) {
      element[0].showError( error.text() );
    }
  }
  ,
  { wrapSelector : 'div.row',
    inputErrorClass: 'error',
	inputNormalClass: '',
    message: {
        defaultClass: 'tips',
        errorClass: 'tips-error'
      }
  }
  );
})();