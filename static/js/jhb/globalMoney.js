
function clearNoNum(obj){
	//先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	//保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	//只能为2位小数
	if(obj.value.indexOf('.') != -1){
		obj.value = obj.value.substring(0, obj.value.indexOf('.') + 3);
	}
}

function validateMoneyInput(obj, reg, inputStr){
	var docSel = document.selection.createRange()
	if (docSel.parentElement().tagName != "INPUT") return false
	oSel = docSel.duplicate()
	oSel.text = ""
	var srcRange = obj.createTextRange()
	oSel.setEndPoint("StartToStart", srcRange)
	var str = oSel.text + inputStr + srcRange.text.substr(oSel.text.length)
	return reg.test(str)
}

$(document).ready(function(){
   $("#globalMoney").keypress(function(){ 
       return validateMoneyInput(this,/^\d{1,8}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   
   $("#globalMoney").bind('paste',function(){ 
       return validateMoneyInput(this,/^\d{1,8}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   
   $("#globalMoney").bind('drop',function(){ 
       return validateMoneyInput(this,/^\d{1,8}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   $("#globalMoney").css("ime-mode","disabled");
});
/**
 * 创建富文本编辑器
 */ 
var EditorObject;
function loadEditor(){
	EditorObject = KindEditor.create('#txaItemDiscription', {
		items : [ 'source', '|', 'undo', 'redo', 'cut', 'copy', 'paste', '|',
			'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist',
			'insertunorderedlist', 'indent', 'outdent', '|', 'selectall', '|', 'fontname', 'fontsize',
			'|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough', 'removeformat', '/',
			'image', 'hr', 'emoticons', 'link', 'unlink' ],
		filterMode : true,//过滤html代码
		resizeType : 1, //2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。
		uploadJson : '/image/uploadImage.htm?size=1024&type=1',
		//fileManagerJson : '/images/getStorageFileInfoJsonActon.htm',
		//categoryJson : '/images/getImagesCategoryJsonActon.htm',		
		allowImageUpload : true,
		//allowFileManager : true,
		afterChange : function(){
			setDisLength();
		}
	});
}

function setDisLength(){
	var count = 0;
	if (typeof EditorObject != 'undefined') {
		count = EditorObject.count();
	}
	if(count > 50000){
		$("#disLength").css("color","red");
	}else{
		$("#disLength").css("color","green");
	}
	$("#disLength").html(count);
}
