$(document).ready(function() {
		$.ajax({
			data:$('#logisticsForm').serialize(),
			type: "post",
			url: "${base}/orderSeller/logisticsInfoAjax.htm",
			dataType: 'json',
			success: function(data){ 
				$("#submitLoad").hide();
				var showInfo = "<span class='bor-yellow'>！无法获取有效信息，请至 <a target='_blank' href='${(logisticsCorpDO.corpUrl!'')?html}' class='lan'>【${(logisticsCorpDO.corpName!'')?html}】</a>查询</span>";
		    	var logistics = data.root;
		    	if(logistics != null){
		    		var steps =logistics.step;
		    		 if(steps && steps.length !=0 ){
		    		 	 showInfo = "<span class='bor-yellow'>！以下信息由物流公司提供，如有疑问请咨询 <a target='_blank' href='${(logisticsCorpDO.corpUrl!'')?html}' class='lan'>【${(logisticsCorpDO.corpName!'')?html}】</a></span><ul class='wl-genzh'>";
		    		 	
	 	     			for(i = 0; i < steps.length; i++){
	 	     				showInfo +="<li>"+steps[i].datetime+"&nbsp;&nbsp;"+steps[i].remark+"</li>";
	 	     			}
	 	     			showInfo += "</ul>";
	 	     		}
		    	}
	    		$("#logisticsInfoSpan").html(showInfo);		
		    	    	
			} 
		});
	});