<#setting classic_compatible=true>
<!-- content -->
<link href="http://static.pinju.com/css/shopcart/sns-base.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/shopcart/buycart.css?t=20111027.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/rao/buy.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript" src="http://static.pinju.com/raty/js/jquery.raty.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/rate/rate-min.js"></script>

<!--内容页面开始-->
<div class="main_1000px page_center">
  <#include "/default/templates/layout/rate/order-rate-common.ftl">  

  <div class="cart_container">
    <div class="title">
      <h2>评价商品</h2>
      <div class="horizen_gray" style="*margin:10px 0;"><span class="horizen_red"></span></div>
    </div>

    <div class="ratewrap">
      <div class="tips-text">
        <ol class="dstyle">
          <li>品聚网交易后的评价，需要在本次交易完成后的15天内完成。</li>
          <li>请您根据本次交易，给予真实、客观、仔细地评价。您的评价将是其他会员的参考，也将影响卖家的动态店铺评分。</li>
          <li>您对卖家作出的评论，会作为一条微博内容，显示在我们的<a href="http://sns.pinju.com/" target="_blank">社区</a>中。</li>
          <li>动态店铺评分计分规则：店铺评分是匿名的；店铺评分成功后无法修改。</li>
        </ol>
      </div>

      <div class="item-title">
        <h3>被评卖家: <span>${(order.sellerNick)!?html}</span></h3>
      </div>

      <form id="form" action="${base}/rate/buyer-comment.htm" method="post">

      <#assign offset = 0>
      <#list orderItems as item>
      <div class="item <#if !item_has_next>last </#if>cf">
        <div class="pd-title">
          <h4>对商品进行评论</h4>
          <div class="imgwrap"><div class="img"><img src="${imageServer}${item.itemPicUrl}_80x80.jpg" /></div></div>
          <div class="text"><a href="${base}/detail/${item.itemId}.htm" target="_blank">${(item.itemTitle)?html}</a></div>
        </div>

        <div class="pd-rate">
          <#if itemDsr?? && itemDsr.size() gt 0>
          <h4>动态评分</h4>
          <#list itemDsr as dsr>
          <dl class="rateshow">
            <dt>${(dsr.name)?html}：</dt>
            <dd><div class="raty" id="ir${offset}" name="idsr[${offset}].rate" rate="${(idsr[offset].rate)!'0'?html}" hint="1分：${dsr.memo1}|2分：${dsr.memo2}|3分：${dsr.memo3}|4分：${dsr.memo4}|5分：${dsr.memo5}"></div></dd>
          </dl>
          <input type="hidden" name="idsr[${offset}].dimen" value="${(dsr.id)?html}" />
          <input type="hidden" name="idsr[${offset}].item" value="${(item.itemId)?html}" />
          <#assign offset = offset + 1>
          </#list>
          </#if>
          <dl class="ratetext">
            <dt>
              <strong>对商品的评论（300字以内）</strong>
              <label for="anonymous-${item_index}"><input id="anonymous-${item_index}" type="checkbox" name="comment[${item_index}].anonymous" value="1" <#if (comment[item_index].anonymous)! == '1'>checked="checked" </#if>/> 匿名评价</label>
            </dt>
            <dd>
              <textarea tabindex="${item_index + 1}" rows="6" name="comment[${item_index}].comment" class="comments<#if (errors[item_index])??> error</#if>" id="comments-${item_index}"<#if item_index = 0> autofocus</#if>>${(comment[item_index].comment)!?html}</textarea>              
              <#if item_index == 0 && !invokeMessage??>
              <div class="tippop">
                <div>
                  <p>您的评价将会在您的个人社区中以分享的方式出现</p>
                  <a href="##" class="btn-swhite">我知道了</a>
                </div>
              </div>
              </#if>
              <div class="tips-error" id="msg-${item_index}"<#if !(errors[item_index])??> style="display:none;"</#if>>${(errors[item_index])!}<div>
            </dd>
          </dl> 
        </div>
      </div>
      <input type="hidden" name="comment[${item_index}].item" value="${(item.itemId)?html}" />
      </#list>

      <#if shopDsr && shopDsr.size() gt 0>
      <div class="ratelse cf">
        <h4>动态评分</h4>
        <dl class="rateshow">
          <#list shopDsr as dsr>
          <dt>${(dsr.name)?html}</dt>
          <dd>
            <div id="sr${dsr_index}" class="raty" name="sdsr[${dsr_index}].rate" rate="${(sdsr[dsr_index].rate)!'0'?html}" hint="1分：${dsr.memo1}|2分：${dsr.memo2}|3分：${dsr.memo3}|4分：${dsr.memo4}|5分：${dsr.memo5}"></div>
          </dd>
          <input type="hidden" name="sdsr[${dsr_index}].dimen" value="${(dsr.id)?html}" />
          </#list>
        </dl>

        <div class="rateway">
          <p>小提示：点击星星就能打分了，请客观给出评价。</p>
        </div>
      </div>
      </#if>

      <div class="tips-error" <#if !invokeMessage??>style="display:none;" </#if>id="tips">${invokeMessage}</div>
      <div class="btn">
        <button type="button" class="btn-lred" id="btnsub" title="确认提交">确认提交</button>
        <button type="button" class="btn-lred" id="backto" title="以后再评">以后再评</button>
      </div>
      <input type="hidden" name="oid" value="${oid?html}" />
      </form>
    </div>
  </div>
</div>