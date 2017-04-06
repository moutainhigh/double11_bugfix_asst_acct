/**
 * 表单验证及效果
 */
(function($) {

$.extend($.fn, {

  /**
   * 表单验果及验证工具（使用 jQuery Validate 框架）
   * 
   * @param validateOptions jQuery Validate 参数，详见 http://docs.jquery.com/Plugins/Validation
   * @param effectiveOptions 表单效果参数，参数为对象含义如下：
   * { debug           : false,    // 是否在 firebug 控制台中输出日志信息
   *   ignoreSelectors : [],       // 需要忽略的选择器
   *   wrapSelector    : 'p',      // 表单行外包装元素
   *   loadOnFocus     : true,     // 页面加载后第一个元素是否需要获得焦点
   * inputNormalClass: 'normal',   // 正常情况下元素的样式类
   * inputHoverClass : 'hover',    // 鼠标移动至上时元素的样式类
   * inputFocusClass : 'focus',    // 元素获得焦点时的样式类
   * inputErrorClass : 'error',    // 校验出错时元素的样式类
   * message         : {
   *       tag           : 'span',     // 消息容器元素名
   *       defaultClass  : 'message',  // 消息元素默认样式类
   *       errorClass    : 'error'     // 消息元素错误样式类
   *   }
   * }
   * 
   * @author gaobaowen
   * @since 2011.06.09
   */
  effectiveAndValidate : function( validateOptions, effectiveOptions ) {

    $.validator.addMethod("pattern", function(value, element, parameter) {
        return parameter.test(value);
      }, 'format error');

    return new $.EffectiveAndValidate( effectiveOptions, this.validate( validateOptions ), this[0] );
  }

});

$.EffectiveAndValidate = function( effectiveOptions, validate, form ) {
  this.settings = $.extend( true, {}, $.EffectiveAndValidate.defaults, effectiveOptions || {} );
  this.form = form;
  this.validate = validate;
  this.init();
}

$.extend($.EffectiveAndValidate, {

  defaults : {
    debug           : false,      // 是否使用调试
    ignoreSelectors : [],         // 需要忽略的选择器
    wrapSelector    : 'p',        // 表单行外包装元素
    loadOnFocus     : true,       // 页面加载后第一个元素是否需要获得焦点
    inputNormalClass: 'normal',   // 正常情况下元素的样式类
    inputHoverClass : 'hover',    // 鼠标移动至上时元素的样式类
    inputFocusClass : 'focus',    // 元素获得焦点时的样式类
    inputErrorClass : 'error',    // 校验出错时元素的样式类
    message : {
      tag           : 'span',     // 消息容器元素名
      defaultClass  : 'message',  // 消息元素默认样式类
      errorClass    : 'error'     // 消息元素错误样式类
    },
    event : {
      mouseover : function(first, element, elements) {
        if ( first.hasFocus() || first.hasError() ) {
          this.settings.debug && window.console && console.info('trigger mouseover event, current line is focused or has error');
          return;
        }
        elements.addClass(this.settings.inputHoverClass);
        element.showMessage();
      },
      mouseout : function(first, element, elements) {
        if ( first.hasFocus() || first.hasError() ) {
          this.settings.debug && window.console && console.info('trigger mouseout event, current line is focused or has error');
          return;
        }
        elements.removeClass(this.settings.inputHoverClass);
        element.hideMessage();
      },
      focus : function(first, element, elements) {
        $(element).select();
        if( first.hasError() ) {
          this.settings.debug && window.console && console.info('trigger focus event, current line has error');
          return;
        }
        elements.removeClass(this.settings.inputHoverClass).addClass(this.settings.inputFocusClass);
        element.showMessage();
      },
      blur : function(first, element, elements) {
        if( first.hasError() ) {
          this.settings.debug && window.console && console.info('trigger blur event, current line has error');
          return;
        }
        elements.removeClass(this.settings.inputHoverClass + ' ' + this.settings.inputFocusClass);
        element.hideMessage();
        this.validate.element( element );
      }
    }
  },

  setDefaults : function( settings ) {
    $.extend( $.EffectAndValidate.defaults, settings );
  },

  prototype : {

    /**
     * 初始化
     */
    init : function() {
      var me = this;
      $(this.form).find(this.settings.wrapSelector).each(function(i) {
        var elements = $(this).find('input, select, textarea')
                           .not(':submit, :reset, [disabled]')
                           .not(me.settings.ignoreSelectors)
                           .addClass(me.settings.inputNormalClass);
        me.settings.debug && window.console && console.info('init effective validate, %d element(s) is selected');
        if(elements.length > 0) {
          me.bindMessage( elements, me );
          me.bindEvent( elements, me );
        }
        if(me.settings.loadOnFocus && (i == 0)) {
          me.settings.debug && window.console && console.info('init neet load on focus, focus in element name: %s', elements.first().attr('name'));
          //elements.first().focus();
        }
      });
    },

    /**
     * 在元素上添加函数
     */
    bindMessage : function(elements, me) {
      me.settings.debug && window.console && console.info('init execute bindMessage');
      var info = elements.first().attr('title');
      elements.each( function() {
        $.extend( this, {
          messageBox : $(this).parents(me.settings.wrapSelector).find(me.settings.message.tag + '.' + me.settings.message.defaultClass),
          showMessage : function() {
            me.settings.debug && window.console && console.info('trigger showMessage event, element name: %s, message: %s', this.name, info);
            this.messageBox.show().removeClass(me.settings.message.errorClass).text(info || '');
          },
          showError : function(errorMessage) {
            me.settings.debug && window.console && console.info('trigger showError event, element name: %s, message: %s', this.name, errorMessage);
            this.messageBox.show().addClass(me.settings.message.errorClass).text(errorMessage);
          },
          hideMessage : function() {
            me.settings.debug && window.console && console.info('trigger hideMessage event, hide element name: %s message', this.name);
            this.messageBox.text('').hide();
          },
          hasFocus : function() {
            me.settings.debug && window.console && console.info('trigger hasFocus event, element name: %s, current class: %s', this.name, $(this).attr('class'));
            return $(this).attr('class').indexOf(me.settings.inputFocusClass) > -1;
          },
          hasError : function() {
            me.settings.debug && window.console && console.info('trigger hasError event, element name: %s, current class: %s', this.name, $(this).attr('class'));
            return $(this).attr('class').indexOf(me.settings.inputErrorClass) > -1;
          }
        });
      });
    },

    /**
     * mouseover, mouseout, hover, blur 事件绑定
     */
    bindEvent : function(elements, me) {
      me.settings.debug && window.console && console.info('init execute bindEvent');
      var first;
      elements.each(function(i) {
        me.settings.debug && window.console && console.info('bindEvent, element No.%d, name: %s', i, $(this).attr('name'));
        if(i == 0) {
          first = this;
          me.settings.debug && window.console && console.info('bindEvent find first element, element No.%d, name: %s', i, $(this).attr('name'));
        }
        var element = this;
        $.each(me.settings.event, function(name, fn) {
          me.settings.debug && window.console && console.info('bindEvent start bind %s event', name);
          $(element).bind( name, function() {            
            fn.call( me, first, element, elements );
            me.settings.debug && window.console && console.info('bindEvent, bind %s event on element name: %s', name, $(element).attr('name'));
          });
        });
      });
    }
  }
});

})(jQuery);