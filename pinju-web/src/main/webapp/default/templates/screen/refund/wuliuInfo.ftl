						<tr align="center">
                        <td height="60px" align="center" valign="middle" >
	                        <select id="logisticsId" name="logisticsId">
	                          <option value="">----物流名称----</option>
	                          <#if logisticsCorpDOList?exists>
	                          <#if (logisticsCorpDOList?size>0) >
         					  <#list logisticsCorpDOList as lstDO>
         					  <option value="${lstDO.corpCode!""}">${lstDO.corpName!""}</option>
					    	 </#list>
					    	 </#if>
					    	</#if>
	         				</select>                        
                        </td>
                        <td valign="middle">
                        	<p><input id="logisticsNumber" name="logisticsNumber" type="text" size="25" /></p>                  
                        </td>
                        <td  valign="middle" class="zi_shenhui paddingtb10">
                        	<input name="buyerMemo" type="text" size="25" value=""/>                        
                        	
                        </td>
                        <td  valign="middle" >
                        <a id="hrefDeliveryGoods" href="javascript:DeliveryGoods();"><img src="http://static.pinju.com/images/refund/16.gif" width="103" height="29" /></a></td>
                      </tr>