(function() {

var check = function(pwd, tid) {
  $.ajax({
    type: "post",
    timeout: 15000, 
    cache: false,
    dataType: 'json',
    data: {passWord:pwd,tid:tid},
	url: "/orderPay/ajaxCheckPassWord.htm",
	success: function(responseData){ 
	  if (responseData) {
	    if (responseData["success"] === 'success') {
		  if(!confirm("您是否确认将 "+ $('#orderTotalPrice').val() + " 元支付给卖家？"))
		    return;
		  submitForm();
		  return;
		} else if (responseData['error']) {
		  alert(responseData['error']);
		  $('#payPassword').val('');
		  return;
		}
      }
	  alert('网络连接超时');
	  $('#payPassword').val('');
	},
	error : function() {
	  alert('网络连接超时');
	  $('#payPassword').val('');
	}
  });
}

var submitForm = function() {
  Encrypt.encrypt({
    timeout : 15000,
    success : function(key, iv, res) {
        $('#submitButton').hide();
        $('#submitLoad').show();
        $('#tid').val(res.tid);
        $('#payPassword').val(Security.encryptHex($('#payPassword').val(), key, iv));
        $('#receiveForm').submit();
      },
    error : function() {
      alert('网络连接超时');
	  $('#payPassword').val('');
    }
  });
}

$('#pay-button').click(function(){
  var passWord = $.trim($('#payPassword').val());
  if(passWord ==""){
    alert("请输入您的品聚密码！");
	$('#payPassword').focus();
    return;
  }

  Encrypt.encrypt({
    timeout : 15000,
    success : function(key, iv, res) {
      check(Security.encryptHex($('#payPassword').val(), key, iv), res.tid);
    },
    error : function() {
      alert('网络连接超时');
    }
  });
});

$(document).keydown(function(e) {
  if (e.keyCode == 13) {
    $('#pay-button').click();
  }
});

})();