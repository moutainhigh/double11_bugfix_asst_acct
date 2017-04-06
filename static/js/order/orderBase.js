/** 
* Create on 2011-7-14
* author 杜成
* 订单页面专用
*/
/**
 * Create on 2011-7-14
 * @param {Object} idName
 * author 杜成
 */
function isShow(idName){
	var flag = $(idName).css('display');
	if(flag=='none')
		$(idName).css('display','block');
	else
		$(idName).css('display','none');
}