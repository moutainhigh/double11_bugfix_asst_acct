function showLeftDay(deadline){
	$(deadline).countdown({
		attrName : 'leftDay',
		tmpl : '<span>%{d}</span>天<span>%{h}</span>小时<span>%{m}</span>分<span>%{s}</span>秒'
	});
}
