(function(){
  var s = [ 'oldPasswordBox', 'newPasswordBox', 'confirmBox' ];
  for (var i = 0, k = s.length; i < k; i++) {
    var box = document.getElementById(s[i]);
    if (box.className.indexOf('point') < 0) {
      box.style.display = 'none';
    }
  }
  $('#oldPassword, #newPassword, #confirm').focus(function() {
    $(this).select();
  });  
  $('#oldPassword').focus();
})();

(function() {

  var changeVal = function(selector, key, iv) {
    $(selector).each(function(){
      var that = $(this);
      that.val(Security.encryptHex(that.val(), key, iv))
    });
  };

  var message = function(fc, msg) {
    var dialog = art.dialog({
      lock: true,
      opacity: 0.6,	// 透明度
      title: '错误',
      content: msg,
      icon: 'warning',
      esc: false,
      button: [{
        name: '确定',
        focus: true
      }],
      close: function() {
        $(fc).focus();
      }
    });
  }

  $('#button').click(function() {
    if ($.trim($('#oldPassword').val()) == '') {
      message('#oldPassword', '请输入当前密码');      
      return;
    }
    if ($.trim($('#newPassword').val()) == '') {
      message('#newPassword', '请输入新密码');
      return;
    }
    if ($.trim($('#confirm').val()) == '') {
      message('#confirm', '请输入确认密码');
      return;
    }
    if ($('#confirm').val() != $('#newPassword').val()) {
      message('#confirm', '新密码与确认密码不同');
      return;
    }
    if ($('#oldPassword').val() == $('#newPassword').val()) {
      $('#newPassword,#confirm').val('');
      message('#newPassword', '新密码与当前密码不能相同');
      return;
    }

    Encrypt.encrypt({
      timeout : 15000,
      success : function(key, iv, res) {
        $('input[name="tid"]').val(res.tid);
        changeVal('#oldPassword, #newPassword, #confirm', key, iv);
        $('#form').submit();
      },
      error : function(key, iv, res) {
        var msg = res ? '网络通信异常，修改密码失败' : '网络连接超时，修改密码失败';
        $('#oldPassword, #newPassword, #confirm').val('');
        message('#oldPassword', msg);
      }
    });
    return false;
  });

  $('#form').keypress(function(event) {
    if (event.keyCode == 13) {
      $('#button').click();
    }
  });
})();