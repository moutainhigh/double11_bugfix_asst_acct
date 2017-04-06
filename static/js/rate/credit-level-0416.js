(function() {
  $('#pin').qtip({
    content: '店铺等级说明：<br />一品是顶级，五品是普通；<br />四到二品正在努力晋级中。<br />本店铺已缴纳正品保证金',
    position: {
      corner: {
         target: 'rightMiddle',
         tooltip: 'bottomLeft'
      }
   },
   style: {
     color: 'black',
     background: '#FFFDDA',
     border: {
       width: 1,
       color: 'black'
     }
   }
  });
})();