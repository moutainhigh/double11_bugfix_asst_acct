<#setting classic_compatible=true>
<link rel="stylesheet" href="http://static.pinju.com/css/pj_base.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://static.pinju.com/css/goods/sp.css?t=20111214.css" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/page/pagination.css" rel="stylesheet" />
<link href="http://static.pinju.com/css/sns/sns-base.css" rel="stylesheet" type="text/css" media="screen" />
<link href="http://static.pinju.com/css/credit/honesty.css" rel="stylesheet" type="text/css" media="screen" />

<div class="main_1000px page_center"><!--整体框架 -->
<!--上半部分-->
<div class="honesty_box1000px padding_tb_10px">
  <!--left-->
  <div class="sell-left">
    <#include "/default/templates/control/member/seller-quality-info.ftl">
  </div>
  <!--right-->
  <div class="sell-right">
    <div class="honesty_right margin_left10 border_1px_gray margin_bottom">
      <div class="honesty_right_container title_bg_01 bottom_1px_gray padding_tb_5px">
        <strong>商户诚信信息</strong>
      </div>
      <div class="honesty_right_container">
        <div class="honesty_right_container02 bg_gray01 bottom_1px_gray fontbold">服务质量</div>
        <div class="honesty_right_container02 margin_top10">
          <div class="floatleft margin_left40">
            <p><span class="fontbold fontgray_03">退款率</span><span class="fontgray_03"> 90天内：</span><span class="fontorange_01">${(sellerQuality.refundRate / 100.0)?string("0.00")}%</span></p>
            <p><span class="fontbold fontgray_03">维权率</span><span class="fontgray_03"> 90天内：</span><span class="fontorange_01">${(sellerQuality.rightsRate / 100.0)?string("0.00")}%</span></p>
            <p><span class="fontbold fontgray_03">纠纷率</span><span class="fontgray_03"> 90天内：</span><span class="fontorange_01">${(sellerQuality.disputeRate / 100.0)?string("0.00")}%</span></p>
          </div>
          <div class="floatleft margin_left40">
            <p><span class="fontbold fontgray_03">退款成功 </span><span class="fontgray_03"> 90天内：</span><span class="fontorange_01">${(sellerQuality.refundSuccessRate / 100.0)?string("0.00")}%</span></p>
            <p><span class="fontbold fontgray_03">维权成功 </span><span class="fontgray_03"> 90天内：</span><span class="fontorange_01">${(sellerQuality.rightsSuccessRate / 100.0)?string("0.00")}%</span></p>
          </div>
        </div>
      </div>
    </div><!--右上 end-->
    <div class="honesty_right margin_left10 border_1px_gray">
      <div class="honesty_right_container title_bg_01 bottom_1px_gray padding_tb_5px">
        <strong>动态评分详情</strong>
      </div>
      <div class="honesty_right_container">
        <div class="honesty_right_container02 margin_top10">
          <#list dsrStats as dsr>
          <div class="floatleft margin_left40 fontgray_03">
            <p />
            <p>${dsr.dimensionName}：<span class="fontblue_01">${(dsr.dimensionAvg / 100.0)?string('0.0')}</span></p>
            <p>共 ${dsr.totalNumber} 人打分</p>
            <#list [5,4,3,2,1] as cnt>
            <p class="bottom_1px_gray"><span class="fontbold fontgray_03">${cnt}</span><span class="fontgray_01"> 分</span>：<span class="fontorange_01">${(dsr["percent${cnt}"] / 100.0)?string("0.00")}%</span><#if dsr["number${cnt}"] gt 0><span class="fontgray_01">（${dsr["number${cnt}"]}人）</span></#if></p>
            </#list>
            <p /><p />
          </div>
          </#list>
        </div>
      </div>
    </div>
  </div>
  <!--right end-->
</div><!--上半部分end-->
<!--下半部分-->
<div class="honesty_box1000px border_1px_gray">
  <div class="honesty_box1000px title_bg_01 bottom_1px_gray padding_tb_5px"><strong class="margin_left10">买家的评论</strong></div>
    <div class="honesty_box1000px" id="rate-wrap">
      <div id="pagination" class="pagination"></div>
      <table width="1000" border="0" id="rate">
        <thead>
          <tr class="bg_gray01 bottom_1px_gray" align="center">
            <td width="250px">买家</td>
            <td width="600px">评论</td>
            <td width="200px">商品</td>
          </tr>
        </thead>
        <tbody>
        </tbody>
      </table>
      <div width="1000" id="load"><img src="http://static.pinju.com/images/ajax/loadding.gif"></div>
      <div id="pagination" class="pagination"></div>
    </div><!--table end-->
  </div><!--下半部分end-->
</div><!--整体框架end -->
<div id="xxxx"></div>
<script src="http://static.pinju.com/js/jquery.pagination.js"></script>
<script src="http://static.pinju.com/js/pinju-global.js"></script>
<script type="text/javascript">
(function() {
$.pageIndex = 1;
$.retry = function() {
  $('#load').html('<img src="http://static.pinju.com/images/ajax/loadding.gif">');
  init($.pageIndex - 1);
}
$.ajaxSetup({
  'timeout':2000,
  'error':function() {
    var load = $('#load');
    load.show();
    $('#rate tbody').empty();
    load.html('暂时时无法加载评论数据，请稍候<a href="javascript:void(0);" onclick="$.retry();">再试</a>');
    $('.pagination').hide();
  }
});
var pageIndex = 0;
var pageSize = 10;
// 0 - pic
// 1 - nic
// 2 - comment
// 3 - time
// 4 - item
// 5 - price
// 6 - item
// 7 - bgcolor
var template = '<tr{7}><td><div class=" head_image"><img src="{0}" width="61" height="60" /></div><div class="floatleft padding_10px box_width_150px">{1}</div></td><td><p>{2}</p><p class="fontgray_01">[{3}]</p></td><td><a href="${base}/detail/{6}.htm" target="_blank">{4}</a><br/>{5}元</td></tr>';
init(0);
function init(pageIndex) {
  var page = pageIndex+1;
  $.pageIndex = page;
  var url = 'http://sns.pinju.com/api/comment/seller.htm?id=' + ${sellerQuality.memberId}  + '&page=' + page + '&size=' + pageSize + '&jsoncallback=?&seed=' + Math.random();
  $.getJSON(url, function(data) {
    rateLoadded();
    var pagination = $('.pagination');
    if(!data || data.result != 'OK') {
      $('#load').show();
      $('#load').text('暂时没有评论数据');
      pagination.hide();
      return;
    }
    var rateNum = data.max;
    pagination.pagination(rateNum, {
      callback: pagingCallback,
      link_to: 'javascript:void(0);',
      prev_text: '上一页',
      next_text: '下一页',
      items_per_page: pageSize,
      num_display_entries: 3,
      current_page: $.pageIndex - 1,
      num_edge_entries: 1
    });
    var s = [];
    $.each(data.comments, function(i, comment) {
      var k = pinju.Person.format(template,
        'http://static.pinju.com/images/sns/head_pic_001.jpg',
        (comment.anonymous == '1') ? (comment.buyerNickname || '') : ('<a href="#" target="_blank">' + (comment.buyerNickname || '') + '</a>'),
        comment.comment || '',
        comment.commentTime || '',
        comment.itemName || '',
        formatNum(comment.itemPrice / 100, '0.00', '0.00'),
        comment.itemId,
        i%2==0?'':' class="bg_gray01"'
      );
      s.push(k);
    });
    $('#rate tbody').html(s.join(''));
    pagination.show();    
  });
}
function pagingCallback(index, jq) {
  init(index);
};

function rateLoadding(h, p) {
  var d = $('#load');
  d.animate({opacity:"0.5"},"normal");
  d.height(h);
  var p = w.position();
  d.css('top', p.top + 'px');
  d.css('left', p.left + 'px');
  d.show();
}

function rateLoadded() {
  var d = $('#load');  
  d.hide();
}

function formatNum(n,p,d){var u=d?d:0;if(n==''||n==null)return u;if(isNaN(n))return u;if(p==''||p==null)return n;var l=p.length;var o=p.indexOf('.');var g=p.indexOf(',');var t=(o==-1?0:l-o-1);n=parseFloat(n).toFixed(t)+'';if(g!=-1){if(o==-1)o=l;var c=o-g-1;var r=new RegExp('(-?\\d+)(\\d{'+c+'})');while(r.test(n))n=n.replace(reg,'$1,$2');}return n;}
})();
</script>