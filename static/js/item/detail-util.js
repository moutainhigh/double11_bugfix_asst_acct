
  	  //毫秒转换为YYYY.MM.DD HH:mm:ss 格式
  	  function timeMillisToDate(timeMillis){
  	  		var date = new Date(timeMillis);
  	  		var year=date.getFullYear().toString();
  	  		var month=("0"+(date.getMonth()+1)).slice(-2);
  	  		var day=("0"+date.getDate().toString()).slice(-2);
  	  		var hour=("0"+date.getHours()).slice(-2);
  	  		var minutes=("0"+date.getMinutes()).slice(-2);
  	  		var seconds=("0"+date.getSeconds()).slice(-2);
  	  		//var milliseconds=date.getMilliseconds()
  	  		var time=year+"."+month+"."+day+" "+hour+":"+minutes+":"+seconds;
  	  		return time;
  	  }
  	  
  	  //需要替换的HTML
  	  var HTML_REPLACE=[
  	  		[/&/g,'&amp;'],
  	  		[/</g,'&lt;'],
  	  		[/>/g,'&gt;'],
  	  		[/\"/g,'&quot;'],
  	  		[/ /g,'&nbsp;'],
  	  		[/\r\n|\n|\r/g,'<br/>']
  	  	];
  	  
  	  /**
  	   * 替换所有HTML标签
  	   * @param {需要替换的html} str
  	   * @return {替换后的html}
  	   */
  	  function replaceHTMLTag(str) {
  	  		for(i=0;i<HTML_REPLACE.length;i++){
  	  				 str = str.replace(HTML_REPLACE[i][0],HTML_REPLACE[i][1]);
  	  			}
		          // str = str.replace(/&/g,'&amp;');
		          // str = str.replace(/</g,'&lt;');
		          // str = str.replace(/>/g,'&gt;');
		          // str = str.replace(/\"/g,'&quot;');
	       return str;
    }
    
     //去除所有HTML标签
  	  function removeHTMLTag(str) {
            str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
            str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
            return str;
    }