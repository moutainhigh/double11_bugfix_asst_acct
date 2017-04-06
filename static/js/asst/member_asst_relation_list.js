$(function() {
     $("#go-asst-button").click(function() {
	     $("#go-asst-relation").submit();
	     return false;
	  });
  });

  function goEidtRoleForm(roleId) {
     $("#roleId").val(roleId);
     $("#go-eidt-asst-role-form").submit()
  }
  
  function goEditAsst(id) {
     $("#editAsstMemberId").val(id);
     $("#go-edit-asst-relation").submit();
  }

  function queryLog(asstMemberId, asstLoginName) {
    $("#asstMemberId").val(asstMemberId);
    $("#asstLoginName").val(asstLoginName);
    $("#oper-log").submit();
  }

  function setStatus(obj) {
     var stauts = $(obj).attr('status');
     var a = (stauts == "0" ? "您确定要冻结该账号吗?":"您确定要解除冻结账号吗?");
     art.dialog({
         title:'提示信息',
         lock:true,
         opacity:0.6,
         content:a,
         icon:"question",
         button:[{name:"确定",
                  callback: function () {
                      var asstMemberId = $(obj).attr('value');
                      var status = $(obj).attr('status');
					  $.ajax({
							  type : 'POST',
				              async : false,
				              url : BASE + '/async/asst/relation.htm',
				              data : { asstMemberId : asstMemberId, status: status},
				              dataType : 'json',
				              dataFilter : function(json) {
				                  var j = eval('(' + json + ')');
								  if (j.result == true) {
								      var retStatus = status=="0"?2:0;
									  $(obj).attr('status', retStatus);
									  $(obj).empty().html(status=="0"?"解冻":"冻结");
								  } else {
								      art.dialog({title:'错误', content:'账号状态更新失败',icon:"error"});
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
					action : "asst-relation-list.htm",
					method : "post"
				}).submit();
			},
			items_per_page : 1
		});
   });