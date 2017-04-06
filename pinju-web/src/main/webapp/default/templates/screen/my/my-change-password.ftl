<#setting classic_compatible=true>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
  <title>修改密码</title>
</head>

<body>
<link rel="stylesheet" href="http://static.pinju.com/css/new/change_pass.css" type="text/css" media="screen" />
<h1 class="topic"><strong>修改密码</strong></h1>
<div class="sft">
  <p class="p_txt">密码由6～16位字符组成，为了您的账号安全，请尽量不要使用全数字、全字母、连续有规律字符以及与其他网站(如：其他游戏、邮箱、聊天工具等)相同的密码。 </p>
  <#if invokeMessage??>
  <div class="<#if success>success<#else>err_box</#if>">${invokeMessage}</div>
  </#if>
  <form action="${base}/my/change-password-do.htm" method="post" id="form">
  <ul class="p_list cf">
    <li>
      <span class="li_t"><label for="oldPassword">当前密码：</label></span>
      <span class="li_i"><input type="password" name="oldPassword" maxlength="16" title="请输入当前的密码" id="oldPassword" oncontextmenu="return false" onpaste="return false" /></span>
      <div class="li_e">
        <span class="err<#if (validator['oldPassword'])??> point</#if>" id="oldPasswordBox">${(validator['oldPassword'])!}</span>
      </div>
    </li>
    <li>
      <span class="li_t"><label for="newPassword">新密码：</label></span>
      <span class="li_i"><input type="password" name="newPassword" maxlength="16" title="请输入修改后的新密码" id="newPassword" oncontextmenu="return false" onpaste="return false" /></span>
      <div class="li_e">
        <span class="err<#if (validator['newPassword'])??> point</#if>" id="newPasswordBox">${(validator['newPassword'])!}</span>
      </div>
    </li>
    <li>
      <span class="li_t"><label for="confirm">确认密码：</label></span>
      <span class="li_i"><input type="password" name="confirm" maxlength="16" title="请再输入一遍修改后的新密码" id="confirm" oncontextmenu="return false" onpaste="return false" /></span>
      <div class="li_e">
        <span class="err<#if (validator['confirm'])??> point</#if>" id="confirmBox">${(validator['confirm'])!}</span>
      </div>
    </li>
  </ul>
  <span class="but_box">
    <input class="qe_but" type="button" id="button" value="确认" />
  </span>  
  <input type="hidden" name="pj0" value="${pj0}" />
  <input type="hidden" name="tid" value="" />
  <input type="hidden" value="change-password" id="my-page" />
  </form>
</div>
<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
<script type="text/javascript" src="http://static.pinju.com/js/member/key.js"></script>
<script type="text/javascript">
(function(){for(var c=["oldPasswordBox","newPasswordBox","confirmBox"],a=0,b=c.length;a<b;a++){var e=document.getElementById(c[a]);if(e.className.indexOf("point")<0)e.style.display="none"}$("#oldPassword, #newPassword, #confirm").focus(function(){$(this).select()});$("#oldPassword").focus()})();
(function(){var c=function(b,a,d){$(b).each(function(){var b=$(this);b.val(Security.encryptHex(b.val(),a,d))})},a=function(b,a){art.dialog({lock:true,opacity:0.6,title:"\u9519\u8bef",content:a,icon:"warning",esc:false,button:[{name:"\u786e\u5b9a",focus:true}],close:function(){$(b).focus()}})};$("#button").click(function(){if($.trim($("#oldPassword").val())=="")a("#oldPassword","\u8bf7\u8f93\u5165\u5f53\u524d\u5bc6\u7801");else if($.trim($("#newPassword").val())=="")a("#newPassword","\u8bf7\u8f93\u5165\u65b0\u5bc6\u7801");
else if($.trim($("#confirm").val())=="")a("#confirm","\u8bf7\u8f93\u5165\u786e\u8ba4\u5bc6\u7801");else if($("#confirm").val()!=$("#newPassword").val())a("#confirm","\u65b0\u5bc6\u7801\u4e0e\u786e\u8ba4\u5bc6\u7801\u4e0d\u540c");else if($("#oldPassword").val()==$("#newPassword").val())$("#newPassword,#confirm").val(""),a("#newPassword","\u65b0\u5bc6\u7801\u4e0e\u5f53\u524d\u5bc6\u7801\u4e0d\u80fd\u76f8\u540c");else return Encrypt.encrypt({timeout:15E3,success:function(b,a,d){$('input[name="tid"]').val(d.tid);
c("#oldPassword, #newPassword, #confirm",b,a);$("#form").submit()},error:function(b,c,d){b=d?"\u7f51\u7edc\u901a\u4fe1\u5f02\u5e38\uff0c\u4fee\u6539\u5bc6\u7801\u5931\u8d25":"\u7f51\u7edc\u8fde\u63a5\u8d85\u65f6\uff0c\u4fee\u6539\u5bc6\u7801\u5931\u8d25";$("#oldPassword, #newPassword, #confirm").val("");a("#oldPassword",b)}}),false});$("#form").keypress(function(a){a.keyCode==13&&$("#button").click()})})();
</script>
</body>
</html>
