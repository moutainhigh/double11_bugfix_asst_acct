
function validateMoney(obj, reg, inputStr){
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
       return validateMoney(this,/^\d{1,8}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   
   $("#globalMoney").bind('paste',function(){ 
       return validateMoney(this,/^\d{1,8}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   
   $("#globalMoney").bind('drop',function(){ 
       return validateMoney(this,/^\d{1,8}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   $("#globalMoney").css("ime-mode","disabled");
});

