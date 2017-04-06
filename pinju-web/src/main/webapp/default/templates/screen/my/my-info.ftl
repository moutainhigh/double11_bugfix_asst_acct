<#setting classic_compatible=true>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>个人资料</title>
</head>
<body>
<link type="text/css" href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<link type="text/css" href="http://static.pinju.com/css/my/my-info.css" rel="stylesheet" />
<h1 class="topic"><strong>个人资料</strong></h1>
<div class="sft">
  <div class="Personal">
    <#if invokeMessage??>
    <div class="message" id="message">${invokeMessage}</div>
    <script type="text/javascript">
    if($.browser.msie && $.browser.version<8){$('#message').show();}else{$('#message').fadeIn(2000);}
    </script>
    </#if>
    <form name="myinfo" action="${base}/my/info-${hash}-${member.id}-${memberInfo.id}.htm<#if returnUrl?exists && returnUrl?trim?trim gt 0>?returnUrl=${returnUrl?html}</#if>" method="post" id="form-input">
    <ul>
      <li style="text-align:right;">带<span class="hong">*</span>号为必填</li>
      <#if nickname>
      <li>
        <label>会员名</label>
        <div style="float:left;">&nbsp;&nbsp;${member.nickname}</div>
        <br class="cella">
      </li>
      <#else>
      <li>
        <label>会员名<span class="hong">*</span></label>
        <div>
        <input type="text" name="member.nickname" id="nickname" size="20" value="${member.nickname}" maxlength="20" title="昵称在 20 个字以内，一经填写将不允许修改" <#if (validator['nickname'])??>class="error" </#if>/>
        </div>
        <div>
        <span class="message <#if (validator['nickname'])??>error</#if>">${(validator['nickname'])!}</span>
        </div>
        <br class="cella">
      </li>
      </#if>
      
      <#if realName>
      <li>
        <label>真实姓名</label>
        <div style="float:left;">&nbsp;&nbsp;${memberInfo.realName}</div>
        <br class="cella">
      </li>
      <#else>
      <li>
        <label>真实姓名<span class="hong">*</span></label>
        <div> 
        <input type="text" id="first" name="memberInfo.realName" size="20" value="${(memberInfo.realName)?html}" maxlength="20" title="请填写真实姓名（真实姓名一经填写将不再允许修改）" <#if (validator['realName'])??>class="error" </#if>/>
        </div>
        <div>
        <span class="message <#if (validator['realName'])??>error</#if>">${(validator['realName'])!}</span>
        </div>
        <br class="cella">
      </li>
      </#if>
      <li class="gender">
        <label>性别<span class="hong">*</span></label>
        <div>
        <input name="member.sex" type="RADIO" value="1" title="请选择性别，不愿意公开请选择“保密” " <#if !member.sex?? || member.sex==1>checked="true" </#if>/>男
        <input name="member.sex" type="RADIO" value="0" <#if member.sex==0>checked="true" </#if>/>女
        <input name="member.sex" type="RADIO" value="2" <#if member.sex==2>checked="true" </#if>/>保密
        </div>
        <div>
        <span class="message <#if (validator['sex'])??>error</#if>">${(validator['sex'])!}</span>
        </div>
        <br class="cella">
      </li>
      <li class="birthday">
        <label>生日</label>
        <div>
        <input type="text" size="20" title="请填写出生日期" style="width:130px" id="datepicker" name="memberInfo.birthday" ignorefocus="true" ignoremouseover ="true" readonly="true" value="<#if (memberInfo.birthday)??>${memberInfo.birthday?string("yyyy-MM-dd")}</#if>"/>
        </div>
        <div>
        <span class="message"></span>
        </div>
        <br class="cella">
      </li>
      <li class="constellation">
        <label>星座</label>
        <div>
        <select name="memberInfo.constellation" id="constellation" value="" title="请选择您的星座" style="width:130px" ignorefocus="true" ignoremouseover ="true"></select>
        </div>
        <div>
        <span class="message <#if (validator['constellation'])??>error</#if>">${(validator['constellation'])!}</span>
        </div>
        <br class="cella">
      </li>
      <li class="seat">
        <label>个人所在地<span class="hong">*</span></label>
        <div>
        <select name="province" id="province" title="请选择您的所在地" <#if (validator['province'])??>class="error" </#if> ignorefocus="true" ignoremouseover ="true"></select><span class="hong">*</span>
        <select name="city" id="city" <#if (validator['city'])??>class="error" </#if> ignorefocus="true" ignoremouseover ="true"></select><span class="hong">*</span>
        <select name="district" id="district" ignorefocus="true" ignoremouseover ="true"></select>
        </div>
        <div>
        <span class="message <#if (validator['province'])?? || (validator['city'])?? || (validator['district'])??>error</#if>">${(validator['province'])!} ${(validator['city'])!} ${(validator['district'])!}</span>
        </div>
        <br class="cella">
      </li>
      <li class="address">
        <label>详细地址</label>
        <div>
        <textarea name="memberInfo.address" style="width:400px;height:100px;" title="请输入您的联系地址" <#if (validator['address'])??>class="error" </#if>>${(memberInfo.address)?html}</textarea>
        </div>
        <div>
        <span class="message <#if (validator['address'])??>error</#if>">${(validator['address'])!}</span>
        </div>
        <br class="cella">
      </li>
      <li>
        <label>邮政编码</label>
        <div>
        <input type="text" name="memberInfo.zipCode" size="20" value="${(memberInfo.zipCode)?html}" maxlength="6" id="email" title="请填写邮政编码" <#if (validator['zipCode'])??>class="error" </#if>/>
        </div>
        <div>
        <span class="message <#if (validator['zipCode'])??>error</#if>">${(validator['zipCode'])!}</span>
        </div>
        <br class="cella">
      </li>
      <li>
        <label>电话</label>
        <div>
        <input type="text" name="memberInfo.phone" size="20" value="${(memberInfo.phone)?html}" maxlength="20" title="请填写您的固定电话号码或者手机号码" <#if (validator['phone'])??>class="error" </#if>/>
        </div>
        <div>
        <span class="message <#if (validator['phone'])??>error</#if>">${(validator['phone'])!}</span>
        </div>
        <br class="cella">
      </li>
      <li class="gender">
        <label>将电话作为</label>
        <input name="memberInfo.phoneTrans" type="radio" value="0" <#if memberInfo.phoneTrans == 0>checked="true" </#if>/>作为交易联系方式
        <input name="memberInfo.phoneTrans" type="radio" value="1" <#if !memberInfo.phoneTrans?? || memberInfo.phoneTrans == 1>checked="true" </#if>/>不作为交易联系方式
        <span class="message <#if (validator['phoneTrans'])??>error</#if>">${(validator['phoneTrans'])!}</span>
      </li>
      <li>
        <label>&nbsp;</label>
        <button type="submit" class="btn-sorange">提交</button>
      </li>
      <li>
        <input type="hidden" name="memberInfo.province" value="${(memberInfo.province)?html}" id="mprovince" />
        <input type="hidden" name="memberInfo.city" value="${(memberInfo.city)?html}" id="mcity" />
        <input type="hidden" name="memberInfo.district" value="${(memberInfo.district)?html}" id="mdistrict" />
        <input type="hidden" name="memberInfo.pcdCode" value="${(memberInfo.pcdCode)?html}" id="mpcdCode" />
        <input type="hidden" value="my-info" id="my-page" />
      </li>
    </ul>
   </form>
  </div>
</div>

<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.15.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/pcd.js?t=20111114.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/my/constellation-min.js"></script>

<script type="text/javascript">
$(document).ready(function() {
(function() {
  var c = new Constellation('constellation');
  c.init();
  <#if memberInfo.constellation?? && memberInfo.constellation?length gt 0>
  c.defaultValue('${(memberInfo.constellation)?js_string}');
  </#if>
})();

(function() {
  var link = $('#province, #city, #district').linkage(pcd, {
        links: $('#mprovince, #mcity, #mdistrict'),
        <#if memberInfo.pcdCode??>
        defaultValue : '${(memberInfo.pcdCode)?js_string}',
        </#if>
        lastLink: '#mpcdCode'
    });
//  $.datepicker.setDefaults( $.datepicker.regional[ 'zh-CN' ] );
  $( "#datepicker" ).datepicker({
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yy-mm-dd',
    yearRange: 'c-70'
  });
})();

(function() {
  
  $('#form-input').effectiveAndValidate({
  
    submitHandler : function(form) {
      window.onbeforeunload = function(){};
      form.submit();
    },

    rules : {
      <#if !realName>
      'memberInfo.realName' : {
          required : true,
          minlength: 2,
          maxlength: 20
        },
      </#if>
      <#if !nickname>
        'member.nickname' : {
          required : true,
          minlength: 3,
          maxlength: 20,
          pattern : /^[0-9a-zA-Z\u4e00-\u9faf_]+$/,
          remote: {
              type : 'POST',
              async : true,
              url : '${base}/async/member/nickname.htm',
              data : { nickname : function() { return $('#nickname').val();}},
              dataType : 'json',
              dataFilter : function(json) {
                  var j = eval('(' + json + ')');
                  if(!j) return false;
                  return j.result + '';
                }
            }
        },
      </#if>
      'member.sex' : {
          required : true
        },
      'memberInfo.birthday' : {
          dateISO : true
        },
      'province' : {
          min : 110000,
          max : 999999
        },
      'city' : {
          min : 110000,
          max : 999999
        },
      'memberInfo.address' : {
          maxlength: 200
        },      
      'memberInfo.phone' : {
          pattern : /^(?:1[3458][0-9]{9}|[0-9-]+)?$/
        },
      'memberInfo.zipCode' : {
          pattern : /^(?:(?:0[1-7]|[1-8][0-9])[0-9]{4})?$/
        }
    },

    messages : {
      <#if !realName>
      'memberInfo.realName' : {
          required : '真实姓名不能为空',
          minlength: '真实姓名至少需要 2 个字符',
          maxlength: '真实姓名不能超过 20 个字符'
        },
      </#if>
      <#if !nickname>
      'member.nickname' : {
          required : '昵称不能为空',
          minlength: '昵称至少为 3 个字符',
          maxlength: '昵称不能超过 20 个字符',
          pattern: '昵称只允许数字、大小写英文字母、下划线或者汉字',
          remote : '该昵称已经被其他会员使用了'
        },
      </#if>
      'member.sex' : {
          required : '性别不能为空'
        },
      'memberInfo.birthday' : {
          dateISO : '生日必须为日期'
        },
      'province' : {
          min: '请选择省份和城市',
          max: '请选择省份和城市'
        },
      'city' : {
          min: '请选择省份和城市',
          max: '请选择省份和城市'
        },
      'memberInfo.address' : {
          maxlength: '详细地址应在 200 个字符以内'
        },
      'memberInfo.phone' : {
          pattern : '电话号码或手机号码格式不正确'
        },
      'memberInfo.zipCode' : {
          pattern : '邮政编码格式不正确'
        }
    },

    errorPlacement : function(error, element) {
      element[0].showError( error.text() );
    }
  },

  { wrapSelector : 'li' } 

  );

  if($.browser.msie) {  
    $('input[type="radio"], input[type="checkbox"]').css('border', 'none');
  }
})();

pinju.Person.initFocus('#first');
});
</script>
</body>
</html>
