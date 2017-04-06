function receipt(oid) {
	if(!checkform()) return; //检查数据
	if(!confirm("你确定收到退货了吗？")) return;
	$('#form1').attr('action','sellApplyManage_aff.htm');
	$('#form1').submit();
}
function reject(oid) {
	if($("#texLeave").attr("value")==""){
		alert("请在下面留言中填写拒绝原因！");
		return;
	}
	if(!checkform()) return; //检查数据
	if(!confirm("你确定要拒绝退款吗？")) return;
	
	$("#shcm").val("rej");
	$('#form1').submit();
}
function agree(oid) {
	if(!checkform()) return; //检查数据
	if(!confirm("你确定要同意退款吗？")) return;
	$("#shcm").val("agr");
	$('#form1').submit();
}
function customer(oid) {
	if(!checkform()) return; //检查数据
	if(!confirm("你确定要申请客服介入吗？")) return;
	$("#shcm").val("cst");
	$('#form1').submit();
}
function checkform(){
	var upType = 'jpg,gif,png';
	var upSize =  10;  //2097152;
	if($("#texLeave").attr("value").length>100){
		alert("留言的长度不能超过300字！");
		return false;
	}
	fileName = $('#file1').val();
	ftype=new String(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLocaleLowerCase();
	if(upType.indexOf(ftype)<0){
		alert(fsize);
		alert("请选择正确的文件类型！");
		return false;
	}
//	ImgObj = new Image();
//	ImgObj.src=fileName;
//	if(ImgObj.readyState!="complete") sleep(1000);
//	if(ImgObj.fileSize>upSize){
//		alert("最大上传文件大小为2M！");
//		return false;
//	}
	
	fileName = $('#file2').val();
	ftype=new String(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLocaleLowerCase();
	if(upType.indexOf(ftype)<0){
		alert("请选择正确的文件类型！");
		return false;
	}

	fileName = $('#file3').val();
	ftype=new String(fileName.substring(fileName.lastIndexOf(".")+1,fileName.length)).toLocaleLowerCase();
	if(upType.indexOf(ftype)<0){
		alert("请选择正确的文件类型！");
		return false;
	}
	return true;
}
if($("#shid").val()=="fileError"){
	alert("上传文件的长度不能超过2M！");
}
function sleep(num) { 
	var tempDate=new Date(); var tempStr=""; 
	var theXmlHttp = new ActiveXObject( "Microsoft.XMLHTTP" ); 
	while((new Date()-tempDate)<num ) { 
	tempStr+="\n"+(new Date()-tempDate); 
	try{ 
	theXmlHttp .open( "get", "about:blank?JK="+Math.random(), false ); 
	theXmlHttp .send(); 
	} 
	catch(e){;} 
	} 
	return; 
}
