/**
 * 数字框校验
 */
$(".validateNum").each(function(){
	$(this).numeral({
		decimalPlace:0,
 		maxLength:20
	});
}); 
/**
 * 判断是否数字及大小区间
 */
var validateNum=function(num,min,max,obj){
	var fval = parseFloat(num);
	if(isNaN(fval)){
		alert(obj+'不是数字');
		return false;
	 }else{
		var sval = new String(fval);
        var i = sval.indexOf(".");
        if(i>=0){
            var ssval = sval.substring(i+1);
            if(ssval.length > 2){
           	 	alert(obj+'只能是小于两位小数点的数');
           	 	return false;
            }
		}
		if(fval<min || fval> max){
    		alert(obj+"必须是大于"+min+"及小于"+max+"的数字");
    		return false;
    	}
	}
	return true;
}
/**  
 * str 传入字符串  
 * len 字符串长度  
 * i 小数点限定位数  
 **/  
function check(str,len,i) {   
    if(str.length>len){   
        alert("超长");   
        return false;   
    }   
	var s = '^\\d+(\\.\\d{1,'+i+'})?$';   
	var re = new RegExp(s);  
	if(!re.test(str)) {   
    	return false;   
	} else {   
    	return true;   
	}   
} 
/**
 * 判断金额，并且小数点后只能输入两位小数
 */	
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
/**
 * 先把非数字的都替换掉包括.，除了数字
 */
function clearNoLong(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
}
/**
 * 限制html标签的输入
 */
function removeHTMLTag(obj) {
    obj.value = obj.value.replace(/<\/?[^>]*>/g,''); //去除HTML tag
    obj.value = obj.value.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
    //obj.value = obj.value.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
    obj.value = obj.value.replace(/&nbsp;/ig,'');//去掉&nbsp;   
    //return obj;
}
