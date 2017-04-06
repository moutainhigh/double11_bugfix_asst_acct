var Constellation = function(id) {
  this.select = document.getElementById(id);
}

Constellation.options = (function() {
  var constellations = ['水瓶座','双鱼座','白羊座','金牛座','双子座','巨蟹座','狮子座','处女座','天秤座','天蝎座','射手座','魔羯座'];
  var options = [ new Option('-- 请选择 --', '') ];
  for (var i = 0, k = constellations.length; i < k; i++) {
    options.push(new Option(constellations[i], constellations[i]));
  }
  return options;
})();

Constellation.prototype = {

  init : function() {
    for (var i = 0, k = Constellation.options.length; i < k; i++) {
      this.select.options.add(Constellation.options[i]);
    }
    return this;
  },

  defaultValue : function(value) {
    for (var i = 1, k = this.select.options.length; i < k; i++) {
      if (this.select.options[i].value == value) {
        this.select.options[i].selected = true;
        this.select.options[i].defaultSelected = true;
      }
    }
    return this;
  }
}