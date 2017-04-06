
function regInput(obj, reg, inputStr){
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
   $("#registeredCapitalMoney").keypress(function(){ 
       return regInput(this,/^\d{1,5}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   
   $("#registeredCapitalMoney").bind('paste',function(){ 
       return regInput(this,/^\d{1,5}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   
   $("#registeredCapitalMoney").bind('drop',function(){ 
       return regInput(this,/^\d{1,5}\.?\d{0,2}$/,String.fromCharCode(event.keyCode));
   }); 
   $("#registeredCapitalMoney").css("ime-mode","disabled");
});

