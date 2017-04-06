    <form name="pageForm" id="pageForm" action="">
			<#if query??>
				<#if query.items=0>
					<font color="red"><#if msgErrorMsg??>${msgErrorMsg}</#if></font> 
				</#if>
				<#if query.rightsMessageDOs??>
		        	<#assign rightsMessageDOs = query.rightsMessageDOs>
		            <#list rightsMessageDOs as rightsMessageDO>
		            <div class="msg_box">
						<ul class="msg_list">
							<li>
								<div class="user_name"><span>${(rightsMessageDO.userNick)!}</span></div>
									<p class="txt" style="word-wrap:break-word">
										<#if rightsMessageDO.voucherPic1??  >
											<a href="${imageServer!}${(rightsMessageDO.voucherPic1)!}" target="_blank">
												<img src="${imageServer!}${(rightsMessageDO.voucherPic1)!}" width="50" height="50" />
											</a>
										</#if>
										<#if rightsMessageDO.voucherPic2??  >
											<a href="${imageServer!}${(rightsMessageDO.voucherPic2)!}" target="_blank">
												<img src="${imageServer!}${(rightsMessageDO.voucherPic2)!}" width="50" height="50" />
											</a>
										</#if>
										<#if rightsMessageDO.voucherPic3??  >
											<a href="${imageServer!}${(rightsMessageDO.voucherPic3)!}" target="_blank">
												<img src="${imageServer!}${(rightsMessageDO.voucherPic3)!}" width="50" height="50" />
											</a>
										</#if>
										<br />
				        				${(rightsMessageDO.content)!}
				        			</p>
									<span class="date">${rightsMessageDO.gmtCreate?string("yyyy-MM-dd HH:mm:ss")}</span>
							</li>
						</ul>
					</div>
					</#list>
				</#if>
				<#if query.items!=0>
		        <tr><td><#include "/default/templates/control/pagination.ftl"></td></tr>
		        </#if>
			</#if>
		</table>
	</form>