var treeObj;
	var setting = {
		checkable: true,
        isSimpleData: true,
		treeNodeKey: "id",
		treeNodeParentKey: "pId"
     };
	$(document).ready(function(){
		var zNodes = eval(jstr);
		treeObj = $("#tree").zTree(setting, zNodes);
		var permissionIds = $("#permissionIds").val();
		if (permissionIds != "") {
		   var idsArray = permissionIds.split(",");
		   for (var i = 0; i < idsArray.length; i++) {
                if(idsArray[i] != "") {
			        var node = treeObj.getNodeByParam("id", idsArray[i]);
			        if (node != null) {
			            node.checked = true;
	                    treeObj.updateNode(node, true);
			        }
		       }
		   }
		}
	});
	$(function() {
	    $("#selectall").click(function() {
		   if($(this).attr("checked") == 'checked') {
	          treeObj.checkAllNodes(true);
		   } 
	    });
	    
	    $("#selectnone").click(function() {
		   if($(this).attr("checked") == 'checked') {
	          treeObj.checkAllNodes(false);
		   } 
	    });
	    
	    var showValidateError = function() {
	       $("#role-err-id").addClass("tips-error").show().append("您输入的角色名称已存在");
	       return false;
	    }
	    
	    var setCheckedPermission = function() {
	       var checkedNodes = treeObj.getCheckedNodes(true);
	       if(checkedNodes.length > 0) {
	          var permissionStr = "";
	          var permissionIds = "";
	          for (var i = 0; i < checkedNodes.length; i++) {
	             if (checkedNodes[i].target != "") {
	                permissionStr += checkedNodes[i].target + ":" + checkedNodes[i].action + ";";
	                permissionIds += checkedNodes[i].id + ",";
	             }
	          }
	          $("#permissions").val(permissionStr);
	          $("#permissionIds").val(permissionIds);
	       }
	    }
	    
	    var roleExist = function() {
	         var ret = true;
			 $.ajax({
				  type : 'POST',
	              async : false,
	              url : BASE + '/async/member/rolename.htm',
	              data : { roleName : $("#roleName").val()},
	              dataType : 'json',
	              dataFilter : function(json) {
	                  var j = eval('(' + json + ')');
					  if (j.result == true) {
						  return true;
					  }
	                  ret = showValidateError();
	               }
			 });
			 return ret;
	    }

	  // 表单数据校验
	  $('#asst-role-form').effectiveAndValidate({
	    submitHandler : function(form) {
	         if (roleExist()) {
	            setCheckedPermission();
	            form.submit();
	         }
	    },
	     rules : {
	      'roleName' : {
	          required : true,
	          byterangelength: [1,20],
	          pattern: /^[\u4e00-\u9faf_0-9a-zA-Z-]+$/
	        }
	       },
		    messages : {
		      'roleName' : {
		          required : '请输入角色名',
		          byterangelength: '角色名由1~20英文字母、数字、汉字、“-” 和下划线组成',
		          pattern: '角色名由英文字母、数字、汉字、“-” 和下划线组成'
		        }
		    },
		    errorPlacement : function(error, element) {
		      element[0].showError( error.text() );
		    }
		  } ,
		  { wrapSelector : 'div.row',
		    inputErrorClass: 'error',
			inputNormalClass: '',
		    message: {
		        defaultClass: 'tips',
		        errorClass: 'tips-error'
		      }
		  });
	});

	function expandAll(epa) {
       treeObj.expandAll(epa);
    }