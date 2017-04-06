//share button
//author LT ~ 2011-07-27

(function(){
	$(document).ready(function(){
		var share_content = $("#newsTitle").val();
		var _pic =$("#_pic").val();
		if(!share_content){
			share_content = "品聚 | 天下无假，爱上网购！";
		}
		if(!_pic){
			_pic =encodeURI("http://pinju.com/img_index/bg_main_2_1.jpg");
		}
		var share_contentVal = encodeURI(share_content),
			share_currentUrl = encodeURIComponent(document.location);
		var tsinaVal = function(){
				var _shareUrl = "http://service.weibo.com/share/share.php?",
					_appkey = encodeURI("2992571369"),
					//_pic = encodeURI("http://pinju.com/img_index/bg_main_2_1.jpg"),
					_ralateUid = "2166840413";
				var _u = _shareUrl+'&title='+share_contentVal+'&url='+share_currentUrl+'&appkey='+_appkey+'&pic='+_pic+'&ralateUid='+_ralateUid;
				return _u;
			},
			tqqVal = function(){
				var _shareUrl = "http://v.t.qq.com/share/share.php?",
					_appkey = encodeURI(""),
					//_pic = encodeURI(""),
					_site = '',// from 'site url'
					_assname = "";
				var _u = _shareUrl+'&title='+share_contentVal+'&url='+share_currentUrl+'&appkey='+_appkey+'&pic='+""+'&assname='+_assname;
				return _u;
			},
			qzoneVal = function(){
				var _shareUrl = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?",
					//_pic = encodeURI("http://pinju.com/img_index/bg_main_2_1.jpg"),
					_summary = "";
				var _u = _shareUrl+'url='+share_currentUrl+'&title='+share_contentVal+'&pics='+_pic+'&summary='+_summary;
				return _u;
			},
			renrenVal = function(){
				var _shareUrl = "http://share.renren.com/share/buttonshare.do?";
				var _u = _shareUrl+'link='+share_currentUrl+'&title='+share_contentVal;
				return _u;
			},
			kaixin001Val = function(){
				var _shareUrl = "http://www.kaixin001.com/repaste/share.php?";
				var _u = _shareUrl+'rtitle='+share_contentVal+'&rurl='+share_currentUrl+'&rcontent='+share_contentVal+share_currentUrl;
				return _u;
			},
			t163Val = function(){
				var _shareUrl = "http://t.163.com/article/user/checkLogin.do?",
					//_pic = encodeURI("http://pinju.com/img_index/bg_main_2_1.jpg"),
					_source = ''//source site;
				var _u = _shareUrl+'source='+_source+'&info='+share_contentVal+'+'+share_currentUrl+'&images='+_pic;
				return _u;
			},
			tsohuVal = function(){
				var _shareUrl = "http://t.sohu.com/third/post.jsp?",
					_pic = encodeURI("http://pinju.com/img_index/bg_main_2_1.jpg");
				var _u = _shareUrl+'url='+share_currentUrl+'&title='+share_contentVal+'&content=utf-8&pic='+_pic;
				return _u;
			},
			txpengyouVal = function(){
				var _shareUrl = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?to=pengyou";
				var _u = _shareUrl+'&url='+share_currentUrl;
				return _u;
			},
			doubanVal = function(){
				var _shareUrl = "http://shuo.douban.com/!service/share?",
					_pic = encodeURI("http://pinju.com/img_index/bg_main_2_1.jpg");
				var _u = _shareUrl+'image='+_pic+'&href='+share_currentUrl+'&name='+share_contentVal;
				return _u;
			};
		//console.log(tsinaVal());
		$("#share_tsina").attr("href",tsinaVal()).attr("target","_blank").attr("title","分享到新浪微博");
		$("#share_tqq").attr("href",tqqVal()).attr("target","_blank").attr("title","分享到新腾讯微博");
		$("#share_qzone").attr("href",qzoneVal()).attr("target","_blank").attr("title","分享到QQ空间");
		$("#share_renren").attr("href",renrenVal()).attr("target","_blank").attr("title","分享到新人人网");
		$("#share_kaixin001").attr("href",kaixin001Val()).attr("target","_blank").attr("title","分享到开心网");
		$("#share_t163").attr("href",t163Val()).attr("target","_blank").attr("title","分享到网易微博");
		$("#share_tsohu").attr("href",tsohuVal()).attr("target","_blank").attr("title","分享到搜狐微博");
		$("#share_txpengyou").attr("href",txpengyouVal()).attr("target","_blank").attr("title","分享到腾讯朋友");
		$("#share_douban").attr("href",doubanVal()).attr("target","_blank").attr("title","分享到豆瓣微博");
	});
	
})()
