$(function() {

	$(".s-pan").click(function() {
		$(".s-pan").removeClass("active")
		$(this).addClass("active");
		$("#search").attr("action", $(this).attr("href"));
		return false;
	});

	// top search fields label auto show/hide
	if ($(".search-fields input").val() != "") {
		$(".search-fields input").prev("label").hide();
	}
	$(".search-fields input").focus(function() {
		$(this).prev("label").hide();
	});
	$(".search-fields input").blur(function() {
		var searchwords = $(".search-fields input").val();
		if (searchwords.length > 0) {
			$(".search-fields label").hide();
		} else {
			$(this).prev("label").show();
		}
	});

	// suggestlist hover class
	$(".suggestlist li").hover(function() {
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
	});

	//order by price - hover class
	$(".orderprice").hover(function() {
		$(this).addClass("hover");
	}, function() {
		$(this).removeClass("hover");
	});

	//select local in subfilter
	$("#selectLocal").hover(function() {
		$(this).find("ul").show();
		$(this).find(".slocal").addClass("hover");
	}, function() {
		$(this).find("ul").hide();
		$(this).find(".slocal").removeClass("hover");
	});

	//select local in plist-title > .location
	$(".plist-title > .location").hover(function() {
		$(this).find("ul").show();
	}, function() {
		$(this).find("ul").hide();
	});

	// left menu show/hide in search product page
	//$(".menu-pcat .haschildren > a").click(function(e) {
		//$(this).parent().toggleClass("active");
		//e.preventDefault();
//	});
	
	$(".menu-pcat ul ul a").click(function() {
		$(".menu-pcat ul ul a").removeClass("current");
		$(this).addClass("current");
	});

	// sort conditions of products in search page
	$(".sortitem .selected").hover(function() {
		$(this).find("ul").toggle();
	}, function() {
		$(this).find("ul").toggle();
	});
	$(".sortitem .sortlist a").click(function() {
		var sortext = $(this).text();
		//console.log(sortext);
		$(".sortext").replaceWith("<span class='sortext'>" + sortext + "</span>");
		//e.preventDefault();
	});
	$('.moretag a').click(function(e) {
		e.preventDefault();
		$(this).parent().prev().toggleClass('show2row');
		$(this).parent().toggleClass('showall');
	});
	$('.pv-large .item').hover(function() {
		$(this).addClass('hover');
	}, function() {
		$(this).removeClass('hover');
	});
});