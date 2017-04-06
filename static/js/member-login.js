var app = {};

app.writeLogin = function(c) {
  var s = [];
  s.push('http://www.sdo.com/LoginFrame.asp?appDomain=pinju.com');
  s.push('CSSURL=' + PJ.ROOT_ENCODED + '%2Fdefault%2Fcss%2Fiframelogin.css?20110530');
  if(!c) {
	  s.push('curURL=' + PJ.ROOT_ENCODED + 'member%2fauth.htm');
	  s.push('returnURL=' + PJ.ROOT_ENCODED + '%2fmember%2fauth.htm');
  } else {
	  s.push('curURL=' + PJ.ROOT_ENCODED + '%2fmember%2fauth.htm%3FreturnUrl%3d' + encodeURIComponent(c));
	  s.push('returnURL=' + PJ.ROOT_ENCODED + '%2fmember%2fauth.htm%3FreturnUrl%3d' + encodeURIComponent(c));
  }
  s.push('appId=202622300');
  s.push('appArea=1');
  document.write('<iframe id="iframeAccount" width="300" scrolling="no" height="130" frameborder="0" allowtransparency="true" src="' + s.join('&') + '" name="login"></iframe>');
}