/**
 * <p>省市区联动框</p>
 * <p>联动框 select 的样式建议设成：select { size:portrait; } 以自适应内容宽</p>
 * @autho gaobaowen
 * @since 2011.06.07
 */
(function($) {

  var ROOT_CODE = '000000';

  var DEFAULT_CHOICE = '-- 请选择 --';
  var DEFAULT_VALUE  = '-1';

  var linkages;
  var data;
  var settings = {
      /* 是否处于调试模式，调试模式时可以在 firebug 中输出调信息 */
      debug : false,
      /* 关联的对象，同步设置值 */
      links : undefined,
      /* 设置选中的值，还是值的字面文字 */
      isLinkValue : false,
      /* 与联动选择中最小的值或字面文字关联 */
      lastLink : undefined
    };

  var me;

  /**
   * 初始化联动对象
   *
   * @param d 参数需要按 pcode 升序排序
   * @param options 对象
   *   debug : 是否处于调试模式，调试模式时可以在 firebug 中输出调信息（false）
   *   links : 关联的对象，同步设置值（undefined）
   *   isLinkValue : 设置选中的值，还是值的字面文字（false）
   *   lastLink : 与联动选择中最小的值或字面文字关联（undefined）
   */
  $.fn.linkage = function(d, options) {
    linkages = $(this);
    data = d;
    $.extend( settings, arguments[1] || {} );
    me = new Linkage().init(linkages);
    return me;
  };

  var initLinkages = function() {
    for(var i = 0, j = 1; j < linkages.length; i++, j++) {
      bindChange(i, j);
      linkages[i].value = DEFAULT_VALUE;
    }
    settings.debug && window.console && console.info( 'init linkages bind finished');
    if(settings.links) {
      linkages.each(function(i) {
        $(this).bind('change', function() {
          fireChange(i, this, $(this).val());
        })
      });
    }

    linkages.append('<option value="' + DEFAULT_VALUE + '">' + DEFAULT_CHOICE + '</option>');
    var init = selectOptions( ROOT_CODE );
    $(linkages[0]).append(init.join(''));
  };

  var binarySearch = function( code, field ) {
    if (!field) field = 2;
    var left  = -1, right = data.length;
    var a = 0;
    while ( left + 1 != right ) {
      var center = ( left + right ) >> 1;
      if ( data[center][field] < code ) {
        left = center;
      } else {
        right = center;
      }
    }
    return ( right < data.length && data[right][field] == code ) ? right : -1;
  };

  var selectOptions = function( code ) {
    var offset = binarySearch( code );
    if (offset < 0) {
      return [];
    }
    var pcode = data[offset][2], opts = [];
    var max = data.length - 1;
    while ( pcode == code) {
      opts.push('<option value="' + data[offset][0] + '">' + data[offset][1] + '</option>');
      if(offset == max) {
        break;
      }
      pcode = data[++offset][2];
    }
    return opts;
  };

  var bindChange = function(a, b) {
    if ( b >= linkages.length ) {
      return;
    }
    var left = linkages[a];
    var right = linkages[b];
    $(left).bind('change', function() {
      settings.debug && window.console && console.info( 'bind change value: %s', this.value);
      changeOptions(right, this.value);
    });
  };

  var changeOptions = function( right, pcode ) {
    right.options.length = 1;
    var offset = binarySearch( pcode );
    if ( offset < 0 ) {
      $(right).trigger('change');
      return;
    }
    var opts = selectOptions( pcode );
    $( right ).append( opts.join('') ).trigger('change');
  };

  var fireChange = function( index, node, value ) {
    if( !settings.links ) {
      return;
    }
    $(settings.links[index]).val( settings.isLinkValue ? value : me.getName(value) );
    settings.debug && window.console && console.info( 'fire change event, change value to %i, value %s', index, settings.isLinkValue ? value : me.getName(value));
    for(var i = linkages.length - 1; i > -1; i-- ) {
      var val = $(linkages[i]).val();
      settings.debug && window.console && console.info( 'fire change event, find last link, index %i : value %s', i, val);
      if( val > -1 ) {
        $(settings.lastLink).val( val );
        settings.debug && window.console && console.info( 'fire change event, last link set value: %s, lastLink name: %s', val, $(settings.lastLink).attr('name') );
        break;
      }
    }
  };

  var getParentCode = function(code) {
    RegExp.lastIndex = 0;
    var arr = code.match(/\d{2}/g);
    if(!arr) return -1;
    settings.debug && window.console && console.info( 'get parent code, code: %s, arr: %s', code, arr );
    for(var i = 1, k = arr.length; i < k; i++) {
      if(arr[i] == '00') {
        arr[i - 1] = '00';
        return arr.join('');
      }
    }
    arr[arr.length - 1] = '00';
    return arr.join('');
  };

  var Linkage = function() {

    return {

      container : null,

      init : function(obj) {
        this.container = obj;
        initLinkages();        
        return this;
      },

      /**
       * 设置初始值
       */
      setValue : function() {
        if(arguments.length < 1) {
          return;
        }
        var links = [];
        var v = arguments[0];
        var n = 100;
        while(v > 0) {
          if(v % n != 0) {
            links.unshift(v);
          }
          v = Math.floor(v / n) * n;
          n *= 100;
        }
        for(var i = 0; i < linkages.length && i < links.length; i++) {
          $(linkages[i]).val(links[i]).trigger('change');
        }
      },

      /**
       * 根据代码获取省、市或者区的名称
       */
      getName : function(code) {
        var pcode = getParentCode(code);
        var index = binarySearch(pcode);
        if(index < 0) {
          return '';
        }
        for(var i = index, k = data.length; i < k; i++) {
          if(data[i][0] == code) {
            settings.debug && window.console && console.info( 'find code: %s value: %s, index: %d, search: %d', code, data[i][1], i, i - index);
            return data[i][1];
          }
        }
      }
    };
  };
})(jQuery);