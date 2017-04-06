<#macro xjs src>
<script type="text/javascript" src="${base}/default/js/${src}"></script>
</#macro>

<#macro js src>
<script type="text/javascript" src="http://static.pinju.com/js/${src}"></script>
</#macro>

<#macro css src>
<link rel="stylesheet" href="http://static.pinju.com/css/${src}" />
</#macro>

<#macro orderRateMessage mid msg>
<p>${msg!}<br />您现在可以：
<br />返回 <a href="http://www.pinju.com">品聚首页</a>
<br />进入 <a href="http://sns.pinju.com/<#if mid??>${mid}</#if>" target="_blank">品聚社区个人主页</a>
<br />返回 <a href="http://www.pinju.com/orderBuyer/orderManager.htm">已买到的商品</a>，继续给卖家评价</p>
</#macro>

<#macro avatars memberId size version>
<#assign mid = memberId / 1000000000>
${imageServer}/usericon/${mid/1000}/${mid%1000}/${memberId}.jpg_${size}x${size}.jpg?t=${version}.jpg
</#macro>

<#macro loginpage link title img style class width height imgid>
<#if link?? && link?length gt 0>
<div class="${class}" style="${style}"><a href="${link?html}" title="${title?html}" target="_blank"><img src="${img?html}" width="${width}" height="${height}" id="${imgid}" /></a></div>
<#else>
<div class="${class}" style="${style}" title="${title?html}"><img src="${img?html}" width="${width}" height="${height}" id="${imgid}" /></div>
</#if>
</#macro>