var GoEmail = {}

GoEmail.BASE = [
  ['@139.com', 'https://mail.10086.cn/'],
  ['@163.com', 'http://mail.163.com/'],
  ['@188.com', 'http://www.188.com/'],
  ['@189.cn', 'http://webmail14.189.cn/'],
  ['@21cn.com', 'http://mail.21cn.com/'],
  ['@263.net', 'http://mail.263.net/'],
  ['@alibaba.com.cn', 'http://mail.china.alibaba.com/'],
  ['@china.com', 'http://mail.china.com/'],
  ['@eyou.com', 'http://www.eyou.com/'],
  ['@gmail.com', 'https://mail.google.com/'],
  ['@hotmail.com', 'https://www.hotmail.com/'],
  ['@live.cn', 'https://mail.live.com/'],
  ['@live.com', 'https://mail.live.com/'],
  ['@qq.com', 'https://mail.qq.com/'],
  ['@vip.qq.com', 'https://mail.qq.com/'],
  ['@foxmail.com', 'https://mail.qq.com/'],
  ['@sina.cn', 'https://mail.sina.com.cn/'],
  ['@sina.com', 'https://mail.sina.com.cn/'],
  ['@sogou.com', 'http://mail.sogou.com/'],
  ['@sohu.com', 'http://mail.sohu.com/'],
  ['@tom.com', 'http://mail.tom.com/'],
  ['@vip.sina.com', 'http://vip.sina.com.cn/'],
  ['@wo.com.cn', 'http://mail.wo.com.cn/'],
  ['@yahoo.cn', 'http://mail.cn.yahoo.com/'],
  ['@yahoo.com', 'https://mail.yahoo.com/'],
  ['@yeah.net', 'http://www.yeah.net/']
]

GoEmail._go = function(link, address) {
  if (link.nodeName == 'A') {
    link.href = address;
    return;
  }
  link.onclick = function() {
    window.open(address);
  }
}

GoEmail.go = function(email, linkId) {
  var link = document.getElementById(linkId || 'go');
  if (!link) {
    return;
  }
  if (!email || email.length == 0 || email.indexOf('@') < 1) {
    GoEmail._go(link, 'http://www.pinju.com');
    return;
  }
  email = email.toLowerCase();
  for (var i = 0, k = GoEmail.BASE.length; i < k; i++) {
    if (email.indexOf(GoEmail.BASE[i][0]) > 0) {
      link.title = GoEmail.BASE[i][1];
      GoEmail._go(link, GoEmail.BASE[i][1]);      
      return;
    }
  }
  link.title = link.href = 'http://www.' + email.substring(email.indexOf('@') + 1);
}