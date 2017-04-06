(function() {
  $('#score li').hover(function() {
    $(this).css('background-color', '#f0f0f0');
  }, function() {
    $(this).css('background-color', 'transparent');
  });
})();

(function() {
var DEFAULT_IMG = 'http://img.pinju.com/face-default/face_50x50.jpg';

var ENCODE = [
  {reg: /&/g, rep: '&amp;'},
  {reg: /</g, rep: '&lt;'},
  {reg: />/g, rep: '&gt;'},
  {reg: /"/g, rep: '&quot;'},
  {reg: / /g, rep: '&nbsp;'},
  {reg: /(?:\r\n|\r|\n)+/g, rep: '<br />'}
];

/**
 * {0} -- avatar
 * {1} -- nick & sns link
 * {2} -- comment
 * {3} -- item id
 * {4} -- item title
 * {5} -- sku attr
 * {6} -- price
 * {7} -- comment time
 * {8} -- table row class
 */
var TEMPLATE = '<tr class="{8}">' +
  '<td class="img">' +
    '<div class="avatar">'+
      '<img src="{0}" />' +
    '</div>' +
  '</td>' +
  '<td class="user">' +
    '<span class="textwrap">{1}</span>' +
  '</td>' +
  '<td class="ptitle">' +
    '<span class="textwrap">{2}</span>' +
    '<span class="desc">[{7}]</span>' +
  '</td>' +
  '<td class="pdesc">' +
    '<span class="textwrap"><a href="http://www.pinju.com/detail/{3}.htm" target="_blank" title="{4}">{4}</a></span>' +
    '<span class="desc">{5}</span><br />' +
    '<span class="price"><strong>{6}</strong> 元</span>' +
  '</td>' +
'</tr>';

var LOADDING = '<tr><td colspan="4" style="text-align:center;"><img src="http://static.pinju.com/images/ajax/loadding.gif"></td></tr>';

function callback(index, jq) {      
  rateInitTable(index);  
}

function getAvatarPath(id) {
  var base = ('' + id).substr(0, 6);
  return IMG_SERVER + '/usericon/' +  parseInt(base.substr(0, 3)) + '/' + parseInt(base.substr(3, 3)) + '/' + id + '.jpg_50x50.jpg';
}

function encode(s) {
  if (!s) return null;
  for (var i = 0; i < ENCODE.length; i++) {
    s = s.replace(ENCODE[i]['reg'], ENCODE[i]['rep']);
  }
  return s;
}

function createRow(index, comment) {
  var name = encode(comment['name']);
  return pinju.Person.format(TEMPLATE,
      (!comment['anonymous'] && comment['bid']) ? getAvatarPath(comment.bid) : DEFAULT_IMG,
      (!comment['anonymous'] && comment['bid']) ? '<a href="http://sns.pinju.com/' + comment.bid + '" target="_blank" title="' + name + '">' + name + '</a>' : name,
      encode(comment['comment']),
      comment['itemId'],
      encode(comment['title']),
      encode(comment['sat']) || '默认款式',
      encode(comment['price']),
      encode(comment['time']),
      index % 2 == 0 ? 'even' : 'odd'
    );
}

//请求数据
function rateInitTable(pageIndex) {
  var page = pageIndex + 1;
  pageIndex = page;
  jQuery.post(PJ.ROOT + '/async/comments/seller.htm', {'page':pageIndex,'pageSize':10,'seller':SELLER}, function(data) {
    var count = data.count;    
    $('#rate-count').text(count);
    $(".pagination").pagination(count, {  
      callback: callback,
      link_to:"#comments",
      prev_text: '上一页',   
      next_text: '下一页',   
      items_per_page: 10,    
      num_display_entries: 4,   
      current_page: pageIndex - 1,
      num_edge_entries: 2   
    });
    var comments = data['comments'];
    if (!comments || comments.length == 0) {
      return;
    }
    var inner = [];
    $.each(comments, function(index, comment) {
      inner.push( createRow(index, comment) );
    });
    $('#comments-body').html(inner.join(''));
  });
}

callback(0);

})();