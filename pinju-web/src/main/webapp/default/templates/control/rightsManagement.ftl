<#if 1==1>
      <h3>退款维权</h3>
     <#if memberAuth.hasAsstPerm('crm', 'refund')>
     <li name="seller-refund-management"><a href="${base}/refund/sellerRefundList.htm">退款管理</a></li>
     </#if>
     
     <li name="sellerRightsList"><a href="${base}/rights/sellerRightsList.htm">维权管理</a></li>
     <!--<li><a href="${base}/rights/rightsList.htm">维权管理</a></li>-->
</#if>