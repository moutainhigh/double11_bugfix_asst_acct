<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商户诚信信息</title>
</head>

<body>
<link rel="stylesheet" href="http://static.pinju.com/css/goods/sp.css?t=20111214.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/credit/credit.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/page/pagination.css" type="text/css" media="screen" />

<!-- content -->
<div class="wrap cf">
<div class="credit-wrap">
  <div class="sell-left">
    <#include "/default/templates/control/member/seller-quality-info.ftl">
  </div>
  <div class="sell_right">

    <div class="row">
      <div class="title">
        <h2>商户诚信信息</h2>
      </div>
      <div class="text">
        <#if sellerQuality??>
        <h3>服务质量</h3>
        <ul class="half-list cf">
          <li><strong>退款率</strong> 90天内：<span><#if (sellerQuality.refundRate)??>${((sellerQuality.refundRate) / 100.0)?string("0.00")}<#else>0.00</#if>%</span></li>
          <li><strong>退款成功率</strong> 90天内：<span><#if (sellerQuality.refundSuccessRate)??>${((sellerQuality.refundSuccessRate) / 100.0)?string("0.00")}<#else>0.00</#if>%</span></li>
          <li><strong>维权率</strong> 90天内：<span><#if (sellerQuality.rightsRate)??>${((sellerQuality.rightsRate) / 100.0)?string("0.00")}<#else>0.00</#if>%</span></li>
          <li><strong>维权成功率</strong> 90天内：<span><#if (sellerQuality.rightsSuccessRate)??>${((sellerQuality.rightsSuccessRate) / 100.0)?string("0.00")}<#else>0.00</#if>%</span></li>
          <li><strong>纠纷率</strong> 90天内：<span><#if (sellerQuality.disputeRate)??>${((sellerQuality.disputeRate) / 100.0)?string("0.00")}<#else>0.00</#if>%</span></li>
        </ul>
        </#if>
      </div>
    </div>

    <div class="row">
      <div class="title">
        <h2>动态评分详情</h2>
      </div>
      <div class="text cf">
        <#list dsrStats as dsrStat>
        <#assign avg = (dsrStat.dimensionAvg/100.0)?string('0.0')>
        <#assign avg2 = (dsrStat.dimensionAvg/100.0)?string('0.00')>
        <div class="item<#if dsrStat_index%2!=0> last</#if>">
          <div class="title" title="${dsrStat.dimensionName}：<#if dsrStat.empty>目前还没有人评分<#else>${avg2} 分</#if>" style="cursor:pointer;">
            <div class="pdrate">${dsrStat.dimensionName}：
              <span class="rate">
              <b style="width:${avg}em;"></b>
              </span>
              <span class="rate_4"><#if dsrStat.empty>NA<#else>${avg}</#if></span>
            </div>
          </div>
          <dl>
            <dt>共${dsrStat.totalNumber}人打分 </dt>
            <dd>
              <ul id="score">
                <li style="cursor:pointer;" title="${dsrStat.dimensionName} ${dsrStat.number5} 人(${(dsrStat.percent5/100)?string('0.00')}%) 打了 5 分">
                  <strong>5</strong> 分
                  <span class="rate">
                    <b style="width:5em;"></b>
                  </span>
                  <span class="pers">${(dsrStat.percent5/100)?string('0.00')}%</span> (${dsrStat.number5}人)
                </li>
                <li style="cursor:pointer;" title="${dsrStat.dimensionName} ${dsrStat.number4} 人(${(dsrStat.percent4/100)?string('0.00')}%) 打了 4 分">
                  <strong>4</strong> 分
                  <span class="rate">
                    <b style="width:4em;"></b>
                  </span>
                  <span class="pers">${(dsrStat.percent4/100)?string('0.00')}%</span> (${dsrStat.number4}人)
                </li>
                <li style="cursor:pointer;" title="${dsrStat.dimensionName} ${dsrStat.number3} 人(${(dsrStat.percent3/100)?string('0.00')}%) 打了 3 分">
                  <strong>3</strong> 分
                  <span class="rate">
                    <b style="width:3em;"></b>
                  </span>
                  <span class="pers">${(dsrStat.percent3/100)?string('0.00')}%</span> (${dsrStat.number3}人)
                </li>
                <li style="cursor:pointer;" title="${dsrStat.dimensionName} ${dsrStat.number2} 人(${(dsrStat.percent2/100)?string('0.00')}%) 打了 2 分">
                  <strong>2</strong> 分
                  <span class="rate">
                    <b style="width:2em;"></b>
                  </span>
                  <span class="pers">${(dsrStat.percent2/100)?string('0.00')}%</span> (${dsrStat.number2}人)
                </li>
                <li style="cursor:pointer;" title="${dsrStat.dimensionName} ${dsrStat.number1} 人(${(dsrStat.percent1/100)?string('0.00')}%) 打了 1 分">
                  <strong>1</strong> 分
                  <span class="rate">
                    <b style="width:1em;"></b>
                  </span>
                  <span class="pers">${(dsrStat.percent1/100)?string('0.00')}%</span> (${dsrStat.number1}人)
                </li>
              </ul>
            </dd>
          </dl>
        </div>
        </#list>
      </div>
    </div>
  </div>

  <div class="fullrow">
    <div class="title">
      <h2>买家评价 (<span id="rate-count">0</span>)</h2>
    </div>    
    <div class="comments">
      <a name="comments"></a><div class="pagination"></div>
      <table>
        <thead>
          <tr>
            <th colspan="2">买家</th>
            <th>评论</th>
            <th>商品</th>
          </tr>
        </thead>
        <tbody id="comments-body">
        </tbody>
        <tfoot>
          <tr>
            <td colspan="4">
              <div class="cbottom imgbtn">
                <div class="pagination"></div>
              </div>
            </td>
          </tr>
        </tfoot>
      </table>      
    </div>
  </div>
</div>
</div>
<script src="http://static.pinju.com/js/jquery.pagination.js?t=20111017"></script>
<script type="text/javascript">
var SELLER = '${encoder.fixedEncodeMemberId((sellerQuality.memberId)!0)}';
var IMG_SERVER = '${imageServer}';
(function(){$("#score li").hover(function(){$(this).css("background-color","#f0f0f0")},function(){$(this).css("background-color","transparent")})})();(function(){var d="http://img.pinju.com/face-default/face_50x50.jpg";var c=[{reg:/&/g,rep:"&amp;"},{reg:/</g,rep:"&lt;"},{reg:/>/g,rep:"&gt;"},{reg:/"/g,rep:"&quot;"},{reg:/ /g,rep:"&nbsp;"},{reg:/(?:\r\n|\r|\n)+/g,rep:"<br />"}];var i='<tr class="{8}"><td class="img"><div class="avatar"><img src="{0}" /></div></td><td class="user"><span class="textwrap">{1}</span></td><td class="ptitle"><span class="textwrap">{2}</span><span class="desc">[{7}]</span></td><td class="pdesc"><span class="textwrap"><a href="http://www.pinju.com/detail/{3}.htm" target="_blank" title="{4}">{4}</a></span><span class="desc">{5}</span><br /><span class="price"><strong>{6}</strong> \u5143</span></td></tr>';var b='<tr><td colspan="4" style="text-align:center;"><img src="http://static.pinju.com/images/ajax/loadding.gif"></td></tr>';function h(j,k){e(j)}function a(k){var j=(""+k).substr(0,6);return IMG_SERVER+"/usericon/"+parseInt(j.substr(0,3))+"/"+parseInt(j.substr(3,3))+"/"+k+".jpg_50x50.jpg"}function f(k){if(!k){return null}for(var j=0;j<c.length;j++){k=k.replace(c[j]["reg"],c[j]["rep"])}return k}function g(k,l){var j=f(l.name);return pinju.Person.format(i,(!l.anonymous&&l.bid)?a(l.bid):d,(!l.anonymous&&l.bid)?'<a href="http://sns.pinju.com/'+l.bid+'" target="_blank" title="'+j+'">'+j+"</a>":j,f(l.comment),l.itemId,f(l.title),f(l.sat)||"\u9ed8\u8ba4\u6b3e\u5f0f",f(l.price),f(l.time),k%2==0?"even":"odd")}function e(j){var k=j+1;j=k;jQuery.post(PJ.ROOT+"/async/comments/seller.htm",{page:j,pageSize:10,seller:SELLER},function(n){var m=n.count;$("#rate-count").text(m);$(".pagination").pagination(m,{callback:h,link_to:"#comments",prev_text:"\u4e0a\u4e00\u9875",next_text:"\u4e0b\u4e00\u9875",items_per_page:10,num_display_entries:4,current_page:j-1,num_edge_entries:2});var o=n.comments;if(!o||o.length==0){return}var l=[];$.each(o,function(p,q){l.push(g(p,q))});$("#comments-body").html(l.join(""))})}h(0)})();
</script>
</body>
</html>
