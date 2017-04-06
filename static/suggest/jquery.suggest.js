
	/*
	 *	jquery.suggest 1.1 - 2007-08-06
	 *
	 *	Uses code and techniques from following libraries:
	 *	1. http://www.dyve.net/jquery/?autocomplete
	 *	2. http://dev.jquery.com/browser/trunk/plugins/interface/iautocompleter.js
	 *
	 *	All the new stuff written by Peter Vulgaris (www.vulgarisoip.com)
	 *	Feel free to do whatever you want with this file
	 *
	 */

	/* 
		Modified: 2011-10-12

		1. 改成jsonp方式，支持跨域
		2. 支持右侧数据
		3. 添加options.keyClass, options.rightResultClass, options.rightResultTmpl参数
	*/

	(function($) {

		$.suggest = function(input, options) {

			var $input = $(input).attr("autocomplete", "off");
			var $results = $(document.createElement("ul"));
			var $iframe = $.browser.msie < 7 ? $('<iframe src="about:blank" style="position:absolute;top:0;left:0;z-index:1;filter:alpha(opacity=0)"></iframe>').hide().appendTo(document.body) : null;

			var timeout = false;		// hold timeout ID for suggestion results to appear
			var prevLength = 0;			// last recorded length of $input.val()
			var cache = [];				// cache MRU list
			var cacheSize = 0;			// size of cache in chars (bytes?)

			var width = parseInt($input.width()) + parseInt($input.css('padding-right')) + parseInt($input.css('padding-left'));
			$results.addClass(options.resultsClass).appendTo('body').css('width', width + 'px');


			resetPosition();
			$(window)
				.load(resetPosition)		// just in case user is changing size of page while loading
				.resize(resetPosition);

			$input.blur(function() {
				setTimeout(function() {
					$results.hide();
					$iframe && $iframe.hide();
				}, 200);
			});


			// help IE users if possible
			try {
				$results.bgiframe();
			} catch(e) { }


			// I really hate browser detection, but I don't see any other way
			if ($.browser.mozilla)
				$input.keypress(processKey);	// onkeypress repeats arrow keys in Mozilla/Opera
			else
				$input.keydown(processKey);		// onkeydown repeats arrow keys in IE/Safari

			// Bugfix: 开启输入法时无法触发事件
			// http://www.planabc.net/2011/10/18/oninput_and_onpropertychange/
			function bindInputEvent(input, callback){
				if ("onpropertychange" in input) { //IE6/IE7/IE8
					input.onpropertychange = function(){
						if (window.event.propertyName == "value"){
							callback.call(this, window.event);
						}
					}
				} else {
					// Fix Firefox Bug: https://bugzilla.mozilla.org/show_bug.cgi?id=195696
					$input.bind("input", callback);
				}
			}
			bindInputEvent($input[0], function() {
				if ($input.val().length != prevLength) {
					if (timeout)
						clearTimeout(timeout);
					timeout = setTimeout(suggest, options.delay);
					prevLength = $input.val().length;
				}
			});

			function resetPosition() {
				// requires jquery.dimension plugin
				var offset = $input.offset();
				$results.css({
					top: (offset.top + input.offsetHeight) + 'px',
					left: offset.left + 'px'
				});
				if ($iframe) {
					$iframe.css({
						top: (offset.top + input.offsetHeight) + 'px',
						left: offset.left + 'px'
					});
				}
			}


			function processKey(e) {

				// handling up/down/escape requires results to be visible
				// handling enter/tab requires that AND a result to be selected
				if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) ||
					(/^13$|^9$/.test(e.keyCode) && getCurrentResult())) {

		            if (e.preventDefault)
		                e.preventDefault();
					if (e.stopPropagation)
		                e.stopPropagation();

	                e.cancelBubble = true;
	                e.returnValue = false;

					switch(e.keyCode) {

						case 38: // up
							prevResult();
							break;

						case 40: // down
							nextResult();
							break;

						case 9:  // tab
						case 13: // return
							selectCurrentResult();
							break;

						case 27: //	escape
							$results.hide();
							$iframe && $iframe.hide();
							break;

					}
				}


			}


			function suggest() {

				var q = $.trim($input.val());

				if (q.length >= options.minchars) {

					cached = checkCache(q);

					if (cached) {

						displayItems(cached['items']);

					} else {
						$.ajax({
							type: 'GET',
							url: options.source,
							data: {q: q},
							dataType : 'jsonp',
							jsonpCallback : 'jsoncallback',
							success: function(data) {
								var txt = '';
								if (data.result) {
									$.each(data.result, function() {
										var right = options.rightResultTmpl.replace(/\{num\}/i, this[1]);
										txt += '<span class="' + options.keyClass + '">' + this[0] + '</span><span class="' + options.rightResultClass + '">' + right + '</span>\n';
									});
								} else {
									txt = data;
								}

								$results.hide();
								$iframe && $iframe.hide();

								var items = parseTxt(txt, q);

								displayItems(items);
								addToCache(q, items, txt.length);

							}
						});

					}

				} else {

					$results.hide();
					$iframe && $iframe.hide();

				}

			}


			function checkCache(q) {

				for (var i = 0; i < cache.length; i++)
					if (cache[i]['q'] == q) {
						cache.unshift(cache.splice(i, 1)[0]);
						return cache[0];
					}

				return false;

			}

			function addToCache(q, items, size) {

				while (cache.length && (cacheSize + size > options.maxCacheSize)) {
					var cached = cache.pop();
					cacheSize -= cached['size'];
				}

				cache.push({
					q: q,
					size: size,
					items: items
					});

				cacheSize += size;

			}

			function displayItems(items) {

				if (!items)
					return;

				if (!items.length) {
					$results.hide();
					$iframe && $iframe.hide();
					return;
				}

				var html = '';
				for (var i = 0; i < items.length; i++)
					html += '<li>' + items[i] + '</li>';

				$results.html(html).show();

				$results
					.children('li')
					.mouseover(function() {
						$results.children('li').removeClass(options.selectClass);
						$(this).addClass(options.selectClass);
					})
					.click(function(e) {
						e.preventDefault();
						e.stopPropagation();
						selectCurrentResult();
					});

				if ($iframe) {
					$iframe.css({
						width : $results.width(),
						height : $results.height()
					});
					$iframe.show();
				}
			}

			function parseTxt(txt, q) {
				var items = [];
				var tokens = txt.split(options.delimiter);

				// parse returned data for non-empty items
				for (var i = 0; i < tokens.length; i++) {
					var token = $.trim(tokens[i]);
					if (token) {
						//<span class="ac_key">三星手机</span><span class="ac_right_result">384524</span>
						var match;
						if ((match = /^(<[^>]+>)(.*?)(<\/[^>]+>[\s\S]*)$/.exec(token))) {
							var left = match[1], keyword = match[2], right = match[3],
								index1 = keyword.indexOf(q), index2 = index1 + q.length;
							if (index1 >= 0) {
								keyword = keyword.slice(0, index1) + '<span class="' + options.matchClass + '">' + keyword.slice(index1, index2) + '</span>' + keyword.slice(index2);
							}
							items[items.length] = left + keyword + right;
						}
					}
				}

				return items;
			}

			function getCurrentResult() {

				if (!$results.is(':visible'))
					return false;

				var $currentResult = $results.children('li.' + options.selectClass);

				if (!$currentResult.length)
					$currentResult = false;

				return $currentResult;

			}

			function selectCurrentResult() {

				$currentResult = getCurrentResult();

				if ($currentResult) {
					var keyword = $currentResult.find('.' + options.keyClass).html();
					$input.val(keyword.replace(/<[^>]+>/g, ''));
					$results.hide();
					$iframe && $iframe.hide();
					if (options.onSelect)
						options.onSelect.apply($input[0]);

				}

			}

			function nextResult() {

				$currentResult = getCurrentResult();

				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.next()
							.addClass(options.selectClass);
				else
					$results.children('li:first-child').addClass(options.selectClass);

			}

			function prevResult() {

				$currentResult = getCurrentResult();

				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.prev()
							.addClass(options.selectClass);
				else
					$results.children('li:last-child').addClass(options.selectClass);

			}

		}

		$.fn.suggest = function(source, options) {

			if (!source)
				return;

			options = options || {};
			options.source = source;
			options.delay = options.delay || 100;
			options.resultsClass = options.resultsClass || 'ac_results';
			options.selectClass = options.selectClass || 'ac_over';
			options.matchClass = options.matchClass || 'ac_match';
			options.keyClass = options.keyClass || 'ac_key';
			options.rightResultClass = options.rightResultClass || 'ac_right_result';
			options.minchars = options.minchars || 1;
			options.delimiter = options.delimiter || '\n';
			options.onSelect = options.onSelect || false;
			options.maxCacheSize = options.maxCacheSize || 65536;
			options.rightResultTmpl = options.rightResultTmpl || '约{num}个宝贝';

			this.each(function() {
				new $.suggest(this, options);
			});

			return this;

		};

	})(jQuery);

