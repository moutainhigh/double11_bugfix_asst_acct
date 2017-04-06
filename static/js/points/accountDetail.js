$(document).ready(function() {
  $.datepicker.setDefaults( $.datepicker.regional[ 'zh-CN' ] );
  $( "#startDate" ).datepicker({
    changeMonth: true,
    changeYear: true,
    maxDate:new Date(),
	autoSize : false,
    dateFormat: 'yy-mm-dd'
  });
  $("#endDate").datepicker({
    changeMonth: true,
    changeYear: true,
    maxDate:new Date(),
	autoSize : false,
    dateFormat: 'yy-mm-dd'
  });
});

function checkEndDate(){
	     var startTime = $("#startDate").val();
	     var endTime = $("#endDate").val();
	     if(endTime.length==0){
	     	$("#error").html('<font color="red">结束日期不能为空!</font>');
	     	return false;
	     }else{
	     	$("#error").html('');
	     }
	     var startDate = new Date(Date.parse(startTime.replace(/-/g,"/")));
	     var endDate = new Date(Date.parse(endTime.replace(/-/g,"/")));
	     if(startDate > endDate) {
	        $("#error").html('<font color="red">开始日期不能大于结束日期!</font>');
	        return false;
	     }else{
	     	$("#error").html('');
	     }
}
function checkAccountDetail(){
	     var startTime = $("#startDate").val();
	     var endTime = $("#endDate").val();
	     if(startTime.length==0){
	     	$("#error").html('<font color="red">开始日期不能为空!</font>');
	     	return false;
	     }
	     if(endTime.length==0){
	     	$("#error").html('<font color="red">结束日期不能为空!</font>');
	     	return false;
	     }
	     var startDate = new Date(Date.parse(startTime.replace(/-/g,"/")));
	     var endDate = new Date(Date.parse(endTime.replace(/-/g,"/")));
	     if(startDate > endDate) {
	        $("#error").html('<font color="red">开始日期不能大于结束日期!</font>');
	        return false;
	     }
	      $("#error").html('');
	     return true;
}