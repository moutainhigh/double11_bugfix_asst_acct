
KindEditor.options.filterMode = true;

KindEditor.options.htmlTags = {
	font : ['color', 'size', 'face', '.background-color', 'style'],
	span : [
		'.color', '.background-color', '.font-size', '.font-family', '.background',
		'.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.line-height', 'style'
	],
	div : [
		'align', '.border', '.margin', '.padding', '.text-align', '.color',
		'.background-color', '.font-size', '.font-family', '.font-weight', '.background',
		'.font-style', '.text-decoration', '.vertical-align', '.margin-left', 'style'
	],
	table: [
		'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'bordercolor',
		'.padding', '.margin', '.border', 'bgcolor', '.text-align', '.color', '.background-color',
		'.font-size', '.font-family', '.font-weight', '.font-style', '.text-decoration', '.background',
		'.width', '.height', '.border-collapse', 'style'
	],
	'td,th': [
		'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor',
		'.text-align', '.color', '.background-color', '.font-size', '.font-family', '.font-weight',
		'.font-style', '.text-decoration', '.vertical-align', '.background', '.border', 'style'
	],
	a : ['href', 'target', 'name', 'bid', 'style'],
	embed : ['src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess', 'style'],
	img : ['src', 'width', 'height', 'border', 'alt', 'title', 'align', 'usemap', '.width', '.height', '.border', 'style'],
	'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
		'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',
		'.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left', 'style'
	],
	pre : ['class', 'style'],
	map : ['name', 'style'],
	area : ['shape', 'coords', 'href', 'target', 'style'],
	'hr,br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : ['style']
};
