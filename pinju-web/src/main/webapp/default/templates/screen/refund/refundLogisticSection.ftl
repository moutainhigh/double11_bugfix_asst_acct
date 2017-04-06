	<!--按钮以下第四部分 -->
		 <#if tradeRefundLogisticsDO?exists>
            <div class="boxh0 top10 paddingbottom20  t2">
                <div class="boxpadding zi_14size t3 bd"><!--退款协议title --><strong>退货物流信息</strong></div>
                <div class="boxh2  t5">
                <div class="boxred floatleft"></div>
                </div>
                
             <div class="boxh0 top10"><!--退 的商品信息 -->
                            
            		<table id="tableId">
                      <tr align="center">
                        <td width="183" height="30px" valign="middle">物流名称</td>
                        <td width="225" valign="middle">运单号码</td>
                        <td width="160" valign="middle">备注</td>
                        <td width="169" valign="middle">
                        	<!--	
                                                                  操作
                             -->
                        </td>
                      </tr>
                      <tr align="center">
                        <td colspan="4" valign="middle"><img src="http://static.pinju.com/images/refund/shuiping01.gif" width="708" height="1" /></td>
             		  </tr>
             		 
             		  <tr align="center">
                        <td width="183" height="30px" valign="middle">
                        	${tradeRefundLogisticsDO.logisticsName!""}
                        <input type="hidden" value="${tradeRefundLogisticsDO.id?c}" />
                        	</td>
                        <td width="225" valign="middle">
                        <a href="${base}/refund/viewRefundLogisticsinfo.htm?cd=${tradeRefundLogisticsDO.logisticsNumber!""}&exp=${tradeRefundLogisticsDO.logisticsId!""}&id=${refundDO.id?c}"  target=_blank>
                        ${tradeRefundLogisticsDO.logisticsNumber!""}</a>
                        </td>
                        <td width="160" valign="middle">${tradeRefundLogisticsDO.buyerMemo!""}</td>
                        <td width="169" valign="middle">
                        <!--
                        <a herf="#" id="loadWuliuInfo">修改</a>
                        -->
                        </td>
                      </tr>
                     
                     <!--
                     <tr align="center" style="display:none">
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
                        	<input name="buyerMemo" id="buyerMemo" type="text" size="25" value="备注"/>                        
                        	
                        </td>
                        <td  valign="middle" >
                        <a id="updateWuliu" href="#">修改</a></td>
                        
                      </tr>
                      -->
              </table> 
                                   
            </div>   
                
            </div><!--按钮以下第四部分结束 -->
           </#if>