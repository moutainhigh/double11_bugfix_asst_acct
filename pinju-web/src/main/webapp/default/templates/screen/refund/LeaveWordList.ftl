	<script src="http://static.pinju.com/artdialog/jquery.artDialog.js?skin=pj"></script>
	<script src="http://static.pinju.com/artdialog/jquery.iframeTools.js?skin=pj"></script>
 	<div class="boxpadding zi_14size t3 bd"><strong>留言/凭证记录</strong></div>
     <div class="boxh2  t5"><div class="boxred floatleft"></div></div>
        <#if levList?exists>
    	<#if (levList?size>0) >
          <#list levList as lst>
            <div class="leaveword_box" style="display:inline;">
            <ul class="leaveword">
            		<li class="first">
            			 <#if (lst.userType =1)>
            			 	<span>买家</span>
            			 <#elseif (lst.userType =2)>
            			 	<span>卖家</span>
            			 <#elseif (lst.userType =3)>
            			 	<span>客服</span>
            			 </#if>
            			 (${lst.memberNick})&nbsp;&nbsp;说：
            		</li>
                    <li>${lst.content?default(" ")?html}</li>
                    <li class="floatright">${lst.gmtCreate?datetime}</li>
                    <li>
	                    <#if (lst.pic1)??><img src="${imageServer+lst.pic1}" width="50" height="50" class="showBigImage" title="点击图片可看大图"/></#if>
	                    <#if (lst.pic2)??><img src="${imageServer+lst.pic2}" width="50" height="50"  class="showBigImage" title="点击图片可看大图"/></#if>
	                    <#if (lst.pic3)??><img src="${imageServer+lst.pic3}" width="50" height="50"  class="showBigImage" title="点击图片可看大图"/></#if>
                    </li>
               </ul>
                    
                    <ul style="display:none;">hidden</ul>
            <div style="display:inline; display:none"><input class="pj-btn-call" type="submit" value="" />
            </div>

            </div>
           </#list>
            <#else>
         	<div class="leaveword_box" style="display:inline;">
            <ul class="leaveword">
             <li>没有任何留言凭证<li>
            </ul>
           </div>
         </#if>
       </#if>
       