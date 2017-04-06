
  $("#go-asst-role-button").click(function() {
     $("#go-asst-role").submit();
     return false;
  });
  function deleteRole(obj, roleId) {
     art.dialog({
         title:'提示信息',
         lock:true,
         opacity:0.6,
         content:"您确定要删除该角色吗？",
         icon:"question",
         button:[{name:"确定",
                  callback: function () {
                       $.ajax({
							  type : 'POST',
				              async : false,
				              url : BASE + '/async/asst/del-asst-role.htm',
				              data : { roleId : roleId},
				              dataType : 'json',
				              dataFilter : function(json) {
				                  var j = eval('(' + json + ')');
								  if (j.result == true) {
								    $(obj).parent().parent().remove();
								  } else {
								     art.dialog({title:'错误', content:'角色删除失败!',icon:"error"});
								  }
				               }
						 });
				  },
                  focus:true
                  },{
					  name: '取消',
					  callback: function () {
						  return true;
					  }
					}]
     });
  }
  $(document).ready(function() {
        $('.oddrow tr:even').addClass("odd");
		var pages = $("#pages").val();
		var currPage = $("#currPage").val();
		$("#Pagination").pagination(pages, {
			current_page : currPage - 1,
			num_edge_entries : 2,
			num_display_entries : 4,
			callback : function(page) {
				$("#shcm").val("pag");
				$("#currPage").val(++page);
				$("#seachForm").attr( {
					action : "asst-role-list.htm",
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
});