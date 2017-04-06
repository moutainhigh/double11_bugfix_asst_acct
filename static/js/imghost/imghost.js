$(function(){
	
	// left menu show/hide in search product page
	$(".imgcat .accordion").click(function() {
		$(this).parent().toggleClass("active");
	});
	
	$(".imgcat a").click(function() {
		$(".imgcat a").removeClass("current");
		$(this).addClass("current");
	});
	
	$(".imgwrap li,.imgcat li").hover(function () {
			$(this).addClass("hover");
		},
		function () {
			$(this).removeClass("hover");
		}
	);
	
	$(".name .title-edit").click(function() {
		var imgTitle = $(this).parent().find("input.editoldName").val();
		$(this).parent().next(".edit").find("input.editName").val(imgTitle);
		$(this).parent().hide();
		$(this).parent().next(".edit").show();
	});
	
	$(".edit button").bind("click",function() {
		var button = $(this);
		var imgTitle = button.prev().val();
		var imgId = button.parent().find(".editId").val();
		if ($.trim(imgTitle) !== ""){
		 $.ajax({
					// 后台处理程序
					url : "/images/updateStorageFileNameAction.htm",
					// 数据发送方式
					type : "post",
					// 接受数据格式
					dataType : "json",
					// 要传递的数据
					data : {
						fileName:$.trim(imgTitle),
						id:$.trim(imgId)
					},
					// 回传函数
					success : function callBack(result) {
						var json = eval("(" + result + ")");
						if(json.isUpdate==1){
							var titleArea = button.parent().prev(".name").find(".title");
							titleArea.text(imgTitle).attr('title', imgTitle);
							titleArea.prev().val(imgTitle);
							button.parent().hide();
							button.parent().prev(".name").show();
							alert("保存成功");
						}else{
							alert("保存失败");
						}
					}
				});
		}else{
			alert("图片名称不能为空");
		}
	});
	//分类移动
	$(".recat").click(function(e) {
	  e.preventDefault();
	  var count=0;
         var oj=$('.img-select');
         for (var i=0;i<oj.length;i++ ){
           if (oj[i].checked){
              count++;
           }
          }
     if(count>0){
  		art.dialog({
  			content: $('<div/>').append($('#imageCategoryId').eq(0).clone().attr('id', 'imageCategoryId2')).html(),
  			ok: function(){
  			    $('#j_categoryId').val($('#imageCategoryId2').val());
            $("#pageForm").attr({ action:"/images/removeFileInfoAction.htm",method:"post" });
            $("#pageForm").submit(); 
  				}
  		});}
  	else{
  	   alert("请选择要移动的图片");
    }
	});
	$(".recatdetil").click(function(e) {
	  e.preventDefault();
  		art.dialog({
  			content: $('<div/>').append($('#imageCategoryId').eq(0).clone().attr('id', 'imageCategoryId2')).html(),
  			ok: function(){
  			    $('#j_categoryId').val($('#imageCategoryId2').val());
            $("#pageForm").attr({ action:"/images/removeFileInfoAction.htm",method:"post" });
            $("#pageForm").submit(); 
  				}
  		});
	});
	
	//拷贝图片链接 & 代码
	$(".copylink").click(function(e) {
		var headTitle = $(this).attr("title");
		var linkcode = $(this).attr("data-url");
		var linkcodeID = $(this).attr("id");
		e.preventDefault();
		art.dialog({
			id:linkcodeID,
			follow: document.getElementById(linkcodeID),
			title: headTitle,
			content: "<textarea style='font-size: 13px;' rows='3' cols='60'>"+linkcode+"</textarea>"
		});
	});

	$(".copycode").click(function(e) {
		var headTitle = $(this).attr("title");
		var linkcode = $(this).attr("data-url");
		var linkcodeID = $(this).attr("id");
		e.preventDefault();
		art.dialog({
			id:linkcodeID,
			follow: document.getElementById(linkcodeID),
			title: headTitle,
			content: "<textarea style='font-size: 13px;' rows='3' cols='60'><img src='"+linkcode+"' alt='图片来自品聚图片空间' /></textarea>"
		});
	});

});