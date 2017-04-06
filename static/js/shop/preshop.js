// 开店信息

$(function() {
	var liList = $('.stepwrap .tabs li');
	var titleList = $('.accordion .title');
	var toNext = $('.accordion .container .btn-tonext');
	function showAccordion(index) {
		var titleDiv = titleList.eq(index);
		titleDiv.toggleClass("topen");
		titleDiv.next().toggle();
		titleList.each(function() {
			if (this != titleDiv[0]) {
				$(this).removeClass('topen');
				$(this).next().hide();
			}
		});
	}
	liList.bind('click',function() {
		liList.removeClass('current');
		$(this).toggleClass("current");
		var tabIndex = $(this).index('.stepwrap .tabs li');
		showAccordion(tabIndex);
	});

	titleList.live('click',function() {
		var acdtitleIndex = $(this).index('.accordion .title');
		liList.removeClass('current');
		liList.eq(acdtitleIndex).addClass('current');
		showAccordion(acdtitleIndex);
	});
	
	toNext.live('click',function(e) {
		e.preventDefault();
		var theBtnIndex = $(this).index('.accordion .container .btn-tonext') + 1;
		liList.removeClass('current');
		liList.eq(theBtnIndex).addClass('current');
		showAccordion(theBtnIndex);
	});
	
	$('.accordion .topen').live('click',function() {
		return false;
	});
	
	$('#elseshopNo:checked').parents('fieldset').find("#othershop").hide();
	$('#elseshopYes').bind('click',function(){
		$("#othershop").show();
	});
	$('#elseshopNo').bind('click',function(){
		$("#othershop").hide();
	});
	

	
	//var i=0;
	//$("#addBrand").bind('click',function() {
	//	i++;
	//	if (i<6){
	//		var formId = 'qiyebrand'+i;
	//		$('#qiyebrand').clone().attr('id',formId).appendTo(".stepwrap");
	//		$('.accordion .title').removeClass('topen');
	//		$('.openshop .container').hide();
	//		$('#'+formId+'').find('.title').addClass('topen');
	//		$('#'+formId+'').find('.container').show();
	//		$('#'+formId+'').find('#brandNo').html('品牌信息'+(i+1)+'');
	//		$('#'+formId+'').find('input,textarea').val('');
			//console.log(i);
	//		showAccordion(i);
	//	}else{
	//		return false;
	//	}
	//});
	
});