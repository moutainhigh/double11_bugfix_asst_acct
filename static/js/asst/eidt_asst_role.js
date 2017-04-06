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
	    
	    $("#eidt-asst-role-form").submit(function() {
	        setCheckedPermission();
	    });
	});
	function expandAll(epa) {
       treeObj.expandAll(epa);
    };