<div class="wrapfull navfull_1018">
	<div class="pj_nav">
		<ul class="nav_list">
        	<li id="nobodyno"></li>
            <li ><a href="http://www.pinju.com/index.html" title="首页">首&nbsp;&nbsp;页</a></li>
			<li ><a href="http://ti.pinju.com" title="体验购">体&nbsp;验&nbsp;购</a></li> 
			<li ><a href="http://mingdian.pinju.com" title="名店街">名&nbsp;店&nbsp;街</a></li>              
            <!-- <li ><a href="###" title="特价折扣" >特价折扣</a></li> -->
            <li ><a href="http://pinpai.pinju.com" title="网货品牌">网货品牌</a></li>
           
            <!-- <li ><a href="###" title="名人坊">名&nbsp;人&nbsp;坊</a></li> -->
            <!-- <li ><a href="###" title="艺术廊">艺&nbsp;术&nbsp;廊</a></li> -->
            <li ><a id="subStation" href="javascript:void(0);" title="城市分站">城市分站</a></li>
            <!-- <li style="float:right;"><a class="nav_right" href="###" title="商户入驻">商户入驻</a></li> -->

		</ul>
	</div>
</div>
<!-- 城市分站弹出层 -->
<div id="chooseLocal" class="citylist" style="display:none;">
	<div class="text">
		<p>欢迎您访问城市分站，推荐给您的城市是<span name="defaultCity">上海</span>，请进入 <a id="defaultUrl" href="http://www.pinju.com/"><span name="defaultCity">上海</span>分站</a> >></p>
	</div>
	<div class="list">
		<ul id="cityUL">
		</ul>
	</div>
</div>
<script type="text/javascript">
	(function(){
		$("#subStation").bind("click",
			function(){
				$.ajax({
					method: "POST",
					url: "http://www.pinju.com/async/substation/getAllCityStations.htm",
					dataType: "JSONP",
					jsonp : "callBack", 
					cache: false,
					success: function(data){
						var cityList = eval(data);
						var tips = "<li>";
						var len = 0;
						if(cityList && cityList.length > 0){
							len = cityList.length;
							$.each(cityList, function(index, city) {
							
								if(city.status && city.cityUrl){
									tips = tips + "<span><a href='" + city.cityUrl
										+ "'>" + city.cityName + "</a></span>";
								}else{
									tips = tips + "<span>" + city.cityName + "（敬请期待）</span>";
								}
								if((index + 1) % 3 == 0){
									tips = tips + "</li><li>";
								}
								
							});
						}else{
							tips = tips + "<span>暂无分站，敬请期待</span>";
						}
						tips = tips + "</li>";
						$("#cityUL").html(tips);
						var dialogHeight = (len / 3 + 1) * 50;
						$.dialog({
							id: "SubStation",
							padding: 0,
							// follow: document.getElementById('subStation'),
							title: "请选择您要前往的城市分站",
							drag: false,
		    				resize: false,
							content: document.getElementById('chooseLocal')
						});
					}
				});
				
			}
		);
	})();
</script>