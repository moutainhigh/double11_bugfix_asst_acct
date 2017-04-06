<#setting classic_compatible=true>
<#escape x as x?html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>收货地址管理</title>
</head>
<body>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/pcd.js?t=20111114.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/linkage-min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/form/effective-validate-min.js"></script>

<style type="text/css">
span.hong { color:red; }
#form-input div { float:left; }
#form-input textarea { font-size:12px; }
#form-input input.hover, #form-input select.hover, #form-input textarea.hover { background:#fdfcdc; }
#form-input input.focus, #form-input select.focus, #form-input textarea.focus { border:1px solid gray; background:#fdfcdc; }
#form-input input.error, #form-input select.error, #form-input textarea.error { border:1px solid #EA0000; background:#FFD2D2; }

#form-input .normal { border:1px solid gray; resize:none; }

#form-input span.message { color:#1D95C7; margin-left:10px; }
#form-input span.error { color:#EA0000; }
#form-input label[for] { cursor:pointer; }
div.message { padding:10px 0; margin:10px 0;text-align:center; border:1px solid green; color:green; font-size:18px; font-weight:bold; background:#FFFFBB; display:none; }
form li { height:26px; clear:both; }
form li.address { height:80px; }
form textarea { margin-bottom:10px; }
</style>
  <h1 class="topic"><strong>收货地址 </strong></h1>
  <div class="sft">
    <div class="Personal">
      <#if invokeMessage?exists>
      <div class="message" id="message">${invokeMessage}</div>
      <script type="text/javascript">
      if($.browser.msie && $.browser.version<8){$('#message').show();}else{$('#message').fadeIn(2000);}
      </script>
      </#if>
      <#if deliveries?? && deliveries.size() gte 10>
      <div class="tips">收货地址中已有 10 条记录，不能再次增加</div>
      </#if>
      <form id="form-input" name="address_form" action="${base}/my/address-update--${delivery.hash}--${_token_}.htm" method="post">
      <ul>
        <li>
          <strong class="huang">新增收货地址：</strong>电话号码，手机号码选填一项，其余都为必填
        </li>
        <li>
          <label>收货人姓名<span class="hong">*</span></label>
          <div>
          <input type="text" id="first" name="delivery.receiverName" value="${(delivery.receiverName)?html}" title="请输入收货人的姓名" />
          </div>
          <div>
            <span class="message<#if validator['receiverName']?exists> error</#if>">${validator['receiverName']}</span>
          </div>
          <br class="cella" />
        </li>
        <li class="seat">
          <label>所在地区<span class="hong">*</span></label>
          <div>
          <select name="province" id="province" title="请选择送货的地区" <#if validator['province']?exists>class="error" </#if> ignorefocus="true" ignoremouseover ="true"></select><span class="hong">*</span>
          <select name="city" id="city" <#if validator['province']?exists>class="city" </#if> ignorefocus="true" ignoremouseover ="true"></select><span class="hong">*</span>
          <select name="district" id="district" ignorefocus="true" ignoremouseover ="true"></select>
          </div>
          <div>
          <span class="message<#if validator['province']?exists || validator['city']?exists> error</#if>">${validator['province']} ${validator['city']}</span>
          </div>
          <br class="cella" />  
        </li>
        <li class="address">
          <label>街道地址<span class="hong">*</span></label>        
          <div><textarea name="delivery.address" style="width:350px;height:70px;" title="请输入您的送货地址，地区不需要再填写" <#if validator['address']?exists>class="error" </#if>>${(delivery.address)?html}</textarea></div>
          <div>
          <span class="message<#if validator['address']?exists> error</#if>" style="float:left;">${validator['address']}</span>
          </div>
          <br class="cella" />
        </li>
        <li style="clear:both;">
          <label>邮政编码<span class="hong">*</span></label>
          <div>
          <input type="text" name="delivery.zipCode" size="20" value="${(delivery.zipCode)?html}" maxlength="6" title="请填写邮政编码" <#if validator['zipCode']?exists>class="error" </#if>/>
          </div>
          <div>
          <span class="message<#if validator['zipCode']?exists> error</#if>">${validator['zipCode']}</span>
          </div>
          <br class="cella" />
        </li>
        <li>
          <label>座机号码</label>
          <div>
          <input type="text" id="area" name="phone.area" size="5" title="请输入电话号码" maxlength="4" />
          -<input type="text" id="tel" name="phone.tel" size="10" maxlength="8" />
          -<input type="text" id="ext" name="phone.ext" size="5" maxlength="8" style="margin-right:8px;" />区号 - 电话号码 - 分机
          </div>
          <div>
            <span class="message<#if validator['area']?exists || validator['tel']?exists || validator['ext']?exists> error</#if>">${validator['area']} ${validator['tel']} ${validator['ext']}</span>
          </div>
          <br class="cella" />
        </li>
        <li>
          <label>手机号码</label>
          <div>
          <input type="text" name="delivery.mobile" size="20" value="${(delivery.mobile)?html}" maxlength="11" title="请填写收货人的手机号码" <#if validator['mobile']?exists>class="error" </#if>/>
          </div>
          <div>
          <span class="message<#if validator['mobile']?exists> error</#if>">${validator['mobile']}</span>
          </div>
          <br class="cella" />
        </li>
        <li class="gender">
          <label>设为默认</label>
          <div>
          <input name="delivery.status" type="checkbox" value="1" title="是否将该地址作为默认的收货地址"<#if delivery.status == 1> checked="true"</#if> />
          </div>
          <div>
          <span class="message"></span>
          </div>
          <br class="cella" />
        </li>
        <li class="imgbtn">
          <label>&nbsp;</label>
          <button name="submit" type="submit" class="btn-sorange<#if deliveries?? && deliveries.size() gte 10> disabled</#if>" <#if deliveries?? && deliveries.size() gte 10>disabled="disabled"</#if>>新增</button>
          <button name="reset" type="reset" class="btn-sgray">清空</button>          
        </li>
        <li>
          <input type="hidden" name="delivery.province" value="${(delivery.province)?html}" id="mprovince" />
          <input type="hidden" name="delivery.city" value="${(delivery.city)?html}" id="mcity" />
          <input type="hidden" name="delivery.district" value="${(delivery.district)?html}" id="mdistrict" />
          <input type="hidden" name="delivery.pcdCode" value="${(delivery.pcdCode)?html}" id="mpcdCode" />
        </li>
      </ul>
      </form>
    </div>

    <div class="pers-adder">
      <strong class="huang">已保存有效地址</strong>
      <table border="0" cellspacing="0" cellpadding="0" style="width:100%;">
        <thead>
          <tr class="adder-hd">
            <td width="7%">收货人</td>
            <td width="18%">所在地区</td>
            <td width="28%">街道地址</td>
            <td width="7%">邮编</td>
            <td width="18%">电话/手机</td>
            <td colspan="2">操作</td>
          </tr>
        </thead>
        <tbody>
        <#list deliveries as p>
        <tr class="border-co<#if p.status == 1> highlight</#if>">
          <td class="list-address">${p.receiverName}</td>
          <td>${p.province} ${p.city} ${p.district}</td>
          <td class="list-address">${p.address}</td>
          <td>${p.zipCode}</td>
          <td>${p.phone} ${p.mobile}</td>
          <td width="8%"><#if p.status == 1>默认<#else><a class="default" href="${base}/my/address-default-${encoder.encodeMemberId(p.memberId)}-${p.hash}-${p.id?c}-${_token_}.htm">设为默认</a></#if></td>
          <td width="11%"><a href="javascript:update(${p_index})" title="点击后在编辑框中进行修改">修改</a>|<a href="javascript:remove(${p_index}, '${encoder.encodeMemberId(p.memberId)}-${p.hash}-${p.id?c}-${_token_}.htm')" title="删除该条收货地址">删除</a></td>
        </tr>
        </tbody>
        </#list>
      </table>
    </div>
  </div>
</div>
<input type="hidden" value="address-manager" id="my-page" />
<script type="text/javascript">

var link;
(function() {

  link = $('#province, #city, #district').linkage(pcd, {
        links: $('#mprovince, #mcity, #mdistrict'),
        lastLink: '#mpcdCode'
    });
  <#if delivery.pcdCode?exists>
  link.setValue('${(delivery.pcdCode)?js_string}');
  </#if>

  $('#form-input').submit(function() {
    window.onbeforeunload = function(){};
  });

  $('#form-input').effectiveAndValidate({

    invalidHandler : function(form, validator) {
      var errors = validator.numberOfInvalids();
      if ( errors ) {
        alert('收货地址有 ' + errors + ' 项数据有误，请重新填写！');
      }
    },

    rules : {
      'delivery.receiverName' : {
          required : true,
          minlength: 2,
          maxlength: 20
        },
      'province' : {
          min : 110000,
          max : 999999
        },
      'city' : {
          min : 110000,
          max : 999999
        },
      'delivery.address' : {
          required: true,
          rangelength: [1, 200]
        },
      'delivery.zipCode' : {
          required : true,
          pattern : /^(?:(?:0[1-7]|[1-8][0-9])[0-9]{4})?$/
        },
      'phone.area' : {
          pattern : /^(?:0[1-9][0-9][0-9]?)?$/
        },
      'phone.tel' : {
          pattern : /^(?:[1-9][0-9]{5,7})?$/
        },
      'phone.ext' : {
          pattern : /^(?:[0-9]{1,8})?$/
        },
      'delivery.mobile' : {
          required : function(element) {
               return ($.trim($('#tel').val()) == '');
             },
          pattern : /^(?:1[3458][0-9]{9})?$/
        }
    },

    messages : {
      'delivery.receiverName' : {
          required : '收货人姓名不能为空',
          minlength: '收货人姓名最少需要 2 个字符',
          maxlength: '收货人姓名不能超过 20 个字符'
        },      
      'province' : {
          min : '请选择省份和城市',
          max : '请选择省份和城市'
        },
      'city' : {
          min : '请选择省份和城市',
          max : '请选择省份和城市'
        },
      'delivery.address' : {
          required: '收货地址不能为空',
          rangelength: '收货地址应在 200 个字符以内'
        },
      'delivery.zipCode' : {
          required : '邮政编码不能为空',
          pattern : '邮政编码格式不正确'
        },
      'phone.area' : {
          pattern : '区号不正确'
        },
      'phone.tel' : {
          pattern : '电话号码不正确'
        },
      'phone.ext' : {
          pattern : '分机号应为数字'
        },
      'delivery.mobile' : {
          required : '电话号码和手机号码必须填写一项',
          pattern : '手机号码格式不正确'
        }
    },

    errorPlacement : function(error, element) {
      element[0].showError( error.text() );
    }
  },

  { wrapSelector : 'li' } 

  );

  $('button[type="reset"]').click(function() {
    $('#form-input').attr('action', '${base}/my/address-update--${delivery.hash}--${_token_}.htm');
    $('input[name="delivery.status"]').attr('checked', false);
    var x=$('button[type="submit"]');
    x.text('新增');
    <#if deliveries?? && deliveries.size() gte 10>
    x.addClass('disabled');
    x.attr('disabled',true);
    </#if>
    pinju.Person.initFocus('input[name="delivery.receiverName"]');
    $('#message').hide();
    $('#province').val('-1');
  });

  if($.browser.msie) {  
    $('input[type="radio"], input[type="checkbox"]').css('border', 'none');
  }
})();
</script>

<#if deliveries?exists>
<script type="text/javascript">
function js(v){if(v){return v.replace(/&lt;/g,"<").replace(/&gt;/g,">");}}
var upset={a:function(d){$('input[name="delivery.receiverName"]').val(js(d[1]));},e:function(d){link.setValue(js(d[5]));},f:function(d){$('textarea[name="delivery.address"]').val(js(d[6]));},g:function(d){$('input[name="delivery.zipCode"]').val(js(d[7]));},h:function(d){if(!d[8]||d[8]==''){return;}RegExp.lastIndex=0;var n=/(?:\((\d+)\))?(\d+)?(?:\*(\d+))?/g.exec(d[8]);if(!n){return;}$('#area').val(n[1]);$('#tel').val(n[2]);$('#ext').val(n[3]);},i:function(d){$('input[name="delivery.mobile"]').val(d[9]);},j:function(d){$('input[name="delivery.status"]').attr('checked',(d[10]=='1'));},k:function(d){$('#form-input').attr('action','${base}/my/address-update-'+d[0]);}, l:function(d){var s=$('button[type="submit"]');s.removeClass('disabled');s.text('修改');s.get(0).disabled='';},m:function(d){$('#message').hide();},n:function(d){pinju.Person.initFocus('input[name="delivery.receiverName"]');}};
var list = [];
<#list deliveries as p>
list[${p_index}] = ['${encoder.encodeMemberId(p.memberId)}-${p.hash}-${p.id?c}-${_token_}.htm','${p.receiverName?js_string}','${p.province?js_string}','${p.city?js_string}','${p.district?js_string}','${p.pcdCode?js_string}','${p.address?js_string}','${p.zipCode?js_string}','${p.phone?js_string}','${p.mobile?js_string}','${p.status}'];
</#list>
function update(i){if(i>=list.length){return;}var d=list[i];for(var n in upset){upset[n].call(null, d);}}
function remove(a,h){var info=[];var d=list[a];info.push('是否需要删除该条'+(d[9]=='1'?'默认的':'')+'收货地址？');info.push('---------------------------------');push(info,'收货人',d[1]);push(info,'地　区',d[2]+' '+d[3]+' '+d[4]);push(info,'地　址',d[6]);push(info,'邮　编',d[7]);push(info,'电　话',d[8]);push(info,'手　机',d[9]);var c=window.confirm(info.join('\n'));if(c)location.href='${base}/my/address-remove-'+h;}
function push(o,t,v){if(v==''){return;}o.push(t+': '+v);}
(function(){$('.default').hide();$('.border-co').hover(function(){$(this).find('.default').show();$(this).addClass('current');},function(){$(this).find('.default').hide();$(this).removeClass('current');});})();
</script>
</#if>
</body>
</html>
</#escape>