	<!--按钮以下第二部分 -->
	<script src="http://static.pinju.com/js/refund/leaveWord.js"></script>
       <div class="fb_box">
       <A NAME="leaveword"></a>
			<ul>
              <li class="zi_shenhui"><span style="vertical-align:top;">填写留言：</span><span><textarea style="overflow:hidden;width:480px;" id="texLeave" name="texLeave" cols="40" rows="8">${texLeave!}</textarea></span>
              <p id='msgFont'class='hui left60'>（注：请详细描述您的理由，以便卖家和客服人员判断，限300字)</p>
              </li>
               <li>上传凭证：<span class="hui">可选项，每张图片大小不超过500K，最多3张  支持GIF、JPG、PNG格式</span></li>
               <li class="shuomingbox"><span class="floatleft">1.<input id="file1" name="voucherPic" type="file" size="45"></span>&nbsp;&nbsp;&nbsp;</li>
               <li class="shuomingbox"><span class="floatleft">2.<input id="file2" name="voucherPic" type="file" size="45"></span>&nbsp;&nbsp;&nbsp;</li>
               <li class="shuomingbox"><span class="floatleft">3.<input id="file3" name="voucherPic" type="file" size="45"></span>&nbsp;&nbsp;&nbsp;</li>
               <li class="shuomingbox"><a  href="javascript:void(0)" class="shuomingbox_submit" id="saveLeveWord"><img src="http://static.pinju.com/images/refund/13_13.gif" width="103" height="29"  /></a></li>
               <li class="left280 top10" id="submitLoad" style="display: none; font-weight: normal; clear: both; text-align: left; margin: -15px 15px 30px 147px;">
						<img align="absmiddle" src="http://static.pinju.com/images/ajax/loadding.gif"> 数据正在提交中，请稍等...
				</li>
				<input type="hidden" name="refundId" value="${refundDO.id?c}">
				<input type="hidden" id="pageUrl" name="pageUrl" value="" />
           	</ul>
           </div>
           <iframe name="leaveWord_hidden_frame" id="leaveWord_hidden_frame" style="display:none"></iframe>
       <!--按钮以下第二部分结束 -->
  <script type="text/javascript">
	var refundId = '${refundDO.id?c}';
</script>