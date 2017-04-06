// 将输入验证码字符转为大写
$('#captcha').keyup(function() {
  var t = $(this);
  t.val(t.val().toUpperCase());
});

// 更改验证码图片
var changeCaptcha = function() {
  var t = $('#captcha-img');
  if($.browser.msie && $.browser.version == 6){
    // stupid ie6 !!!
    setTimeout(function() {
      t.attr('src', t.attr('basesrc') + '&r=' + Math.random());
    }, 0);
  } else {
    t.attr('src', t.attr('basesrc') + '&r=' + Math.random());
  }
  $('#captcha').val('').focus();
}
$('#captcha-img').click(changeCaptcha);
$('#captcha-change').click(changeCaptcha);
