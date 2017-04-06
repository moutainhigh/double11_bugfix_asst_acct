		var buyPageIndex = 1;     //页面索引初始值   
  		var buyPageSize = 10;     //每页显示条数初始化，修改显示条数，修改这里即可     
      //翻页调用   
      function buyListPageCallback(index, jq) {        
          initBuyListTable(index);  
      }  
      //请求数据   
      function initBuyListTable(buyPageIndex) { 
		var table=$("#already_buy_list");
      	table.text("正在读取购买记录,请稍候...");
      	var url=base+"/async/detail/getBuyOrderList.htm?itemId="+itemId+"&page="+buyPageIndex+"&size="+buyPageSize+"&jsoncallback=?&t="+new Date();
      	jQuery.post(url, function(data){
      			var firstBuyHtml='<tr><td colspan="5"></td></tr>';
				table.html(firstBuyHtml);
      			var count =data.count;
      			$("#buy_piece").text(count);
		     	 $("#Pagination").pagination(count, {  
		          callback: buyListPageCallback,  
		          link_to:"javascript:;",//点击属性
		          prev_text: '上一页',       //上一页按钮里text   
		          next_text: '下一页',       //下一页按钮里text   
		          items_per_page: buyPageSize,  //显示条数   
		          num_display_entries: 4,    //连续分页主体部分分页条目数   
		          current_page: buyPageIndex,   //当前页索引   
		          num_edge_entries: 2        //两侧首尾分页条目数   
			  });  
				 $.each(data.list, function(i,obj){
				 	var otherBuyHtml='<tr class=""><td>'+obj.buyerNick+'</td><td>'+obj.price+'</td><td>1</td><td>'+obj.gmtCreate+'</td><td>'+obj.skuAttr+'</td></tr>';
			  		table.append(otherBuyHtml);
				 });
				//table tr hover
				$("table.recorder_data tr").hover(function(){				
					$(this).addClass("hover");
				},function(){
					$(this).removeClass("hover");;
				});
				//scroll
				var pos = table.offset().top;
				$("html,body").scrollTop(pos);
				e.preventDefault();
		});                                       
      }  
