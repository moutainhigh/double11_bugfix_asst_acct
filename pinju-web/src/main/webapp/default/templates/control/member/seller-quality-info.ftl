<link rel="stylesheet" href="http://static.pinju.com/css/sp-sidebar.css?t=20111215.css" type="text/css" media="screen" />
<div class="businessInfo cf">
  <h3 class="businessInfo-title">商家信息</h3>
  <div class="businessInfo-quality">
    <div class="quality-img"></div>
    <ul>
      <li>
        <span class="zheng"></span>
        <a href="http://service.pinju.com/cms/html/quality/jyps/" title="正品保障" target="_blank">正品保障</a>
      </li>
      <li>
        <span class="xiu"></span>
        <a href="http://service.pinju.com/cms/html/quality/30dayswx/" title="30天维修" target="_blank">30天维修</a>
      </li>
      <li>
        <span class="tui"></span>
        <a href="http://service.pinju.com/cms/html/quality/7daysthh/" title="七天退换" target="_blank">七天退换</a>
      </li>
      <li>
        <span class="fa"></span>
        <a class="fa-link" href="http://service.pinju.com/cms/html/quality/72hoursfh/" title="72小时发货" target="_blank">72小时发货</a>
      </li>
    </ul>
  </div>
  <div class="businessInfo-flagship">
    <dl>
      <dt class="pin${(sellerQuality.levelGrade)!'4'}" id="pin"></dt>
      <dd class="titlea" title="${(sellerQuality.levelName)!'五品'}${(sellerQuality.sellerTypeName)!'普通店'}">${(sellerQuality.levelName)!'五品'}${(sellerQuality.sellerTypeName)!'普通店'}</dd>
      <dd class="name">
        <#if mid??><a href="http://www.pinju.com/credit/${mid!''}.htm" title="${(sellerQuality.shopName)!''}">${(sellerQuality.shopName)!''}</a></#if>
      </dd>
    </dl>
  </div>
  <#if sellerQuality??>
  <#if sellerQuality.sellerType != '4'>
  <ul class="businessInfo-corporation">    
    <li>
      <span>所在地：</span>
      <span class="name">${(sellerQuality.localName)!'&nbsp;'}</span>
    </li> 
    <li>
      <span>公司名：</span>
      <span class="name">${(sellerQuality.companyName)!'&nbsp;'}</span>
    </li>
  </ul>
  </#if>
  </#if>
  <ul class="businessInfo-marks">
    <li class="business">营业执照</li>
    <#if sellerQuality??>
    <#if sellerQuality.sellerType=='2' || sellerQuality.sellerType=='3'>
    <li class="brand">品牌授权</li>
    </#if>
    </#if>    
    <li class="name">实名认证</li>
  </ul>

  <#if dsrStats?? && mid??>
  <ul class="businessInfo-pdrate">
    <li class="titlea">店铺动态评分</li>
    <#list dsrStats as dsrStat>
    <#assign avg = (dsrStat.dimensionAvg/100.0)?string('0.0')>
    <#assign avg2 = (dsrStat.dimensionAvg/100.0)?string('0.00')>
    <li>
      <span>${dsrStat.dimensionName}：</span>
      <a href="http://www.pinju.com/credit/${mid!''}.htm" title="${dsrStat.dimensionName}：<#if dsrStat.empty>目前还没有人评分<#else>${avg2} 分</#if>">
        <span class="rate">
          <b style="width:${avg}em;"></b>
        </span>
        <span class="num"><#if dsrStat.empty>NA<#else>${avg}</#if></span>
      </a>
    </li>
    </#list>
  </ul>
  </#if>
  <a class="businessInfo-go" href="http://shop${(sellerQuality.shopId)!''}.pinju.com" title="${(sellerQuality.shopName)!''}" target="_blank"></a>
<script type="text/javascript" src="http://static.pinju.com/jquery/jquery.qtip-1.0.0-rc3.min.js"></script>
<script type="text/javascript">
<#-- static/js/rate/credit-level-0416.js -->
(function(){$("#pin").qtip({content:"\u5e97\u94fa\u7b49\u7ea7\u8bf4\u660e\uff1a<br />\u4e00\u54c1\u662f\u9876\u7ea7\uff0c\u4e94\u54c1\u662f\u666e\u901a\uff1b<br />\u56db\u5230\u4e8c\u54c1\u6b63\u5728\u52aa\u529b\u664b\u7ea7\u4e2d\u3002<br />\u672c\u5e97\u94fa\u5df2\u7f34\u7eb3\u6b63\u54c1\u4fdd\u8bc1\u91d1",position:{corner:{target:"rightMiddle",tooltip:"bottomLeft"}},style:{color:"black",background:"#FFFDDA",border:{width:1,color:"black"}}})})();
</script>
<#include "/default/templates/screen/favorite/shop_favorite.ftl">
</div>
