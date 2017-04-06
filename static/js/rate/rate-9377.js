function msg(msg, offset) {
  $('#comments-' + offset).addClass('error');
  $('#msg-' + offset).text(msg).show();
}

function clearError(offset) {
  $('#comments-' + offset).removeClass('error');
  $('#msg-' + offset).text('').hide();
}

var msgcontent = ['SUCCESS', '评论内容不能为空', '评论字数应在 300 个字符以内', '请您认真审核您的评价，是否在您的评论中有涉及到违禁词'];

$(document).ready(function() {
  $('.raty').each(function() {
    var that = $(this);
    that.raty({
      scoreName: that.attr('name'),
      hintList: that.attr('hint').split('|'),
      start: that.attr('rate')
    });
  });

  $('#btnsub').click(function() {
    var c = $('.comments');
    var commentsData = [];
    var sbt = true;
    for(var i = 0, k = c.length; i < k; i++) {
      var t = $(c[i]);
      var v = $.trim(t.val());      
      t.val(v);
      if (v.length == 0) {
        msg(msgcontent[1], i);
        sbt = false;
        continue;
      }
      if (v.length > 300) {
        msg(msgcontent[2], i);
        sbt = false;
        continue;
      }
      commentsData.push('comments=' + encodeURIComponent(v));
    }

    if (!sbt) {
      $('.tippop').hide();
      $('#tips').text('填写的评论内容有误，请改正后再提交').show();    
      return false;
    }

    var that = $(this);
    that.html('<img src="http://static.pinju.com/artdialog/skins/icons/loading.gif">');
    that.prop('disabled', 'disabled');

    $.ajax({
      url: '/async/rate/validator.htm',
      type: "post",
      dataType: 'json',
      timeout: 2000,
      data: commentsData.join('&'),
      success: function(r) {
        var s = r.result;
        var sbt = true;
        for (var i = 0, k = s.length; i < k; i++) {
          if (s[i] === 0) {
            clearError(i);
            continue;
          }
          msg(msgcontent[s[i]], i);
          sbt = false;
        }
        if (!sbt) {
          $('.tippop').hide();
          $('#tips').text('填写的评论内容有误，请改正后再提交').show();
          that.text(that.attr('title'));
          that.removeAttr('disabled');
          return false;
        }        
        $('#form').submit();
      },
      error : function(x, t, e) {
        $('#form').submit();
      }
    });
  });

  $(".tippop .btn-swhite").click(function(e) {
    e.preventDefault();
    $(this).parents(".tippop").hide();
  });
  
  $('#backto').click(function() {
    location.href = '/orderBuyer/orderManager.htm';
  });
});