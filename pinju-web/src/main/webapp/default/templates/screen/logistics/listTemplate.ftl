<#setting classic_compatible=true>
<LINK href="http://static.pinju.com/css/logistics.css" rel="stylesheet" />
<script src="http://static.pinju.com/js/logistics/listTemplate.js"></script>

<title>品聚网</title>

                <div class="transport">
                
                    <ul class="tab-main">
                    	<li class="count"><a href="#">运费模板</a></li>
                    	 <li><a href="${base}/logistics/logisticsTrack.htm">物流跟踪信息</a></li>
                    </ul>
                    
                	
                    <ul class="imgsfbtn" style="margin-top:10px;">
                        <li><button id="createTemplate" name="createTemplate" type="button" class="btn-orange"><span>新增运费模板</span></button></li>
                    </ul>
                    
                     <#if templateList?exists>
					<#list templateList as template>
					<#assign key='key'+(template.id?c)>
	
                    <ul>

            	<table class="transport-table">
                	<tr>
                    	<td colspan="4" class="caption">
                            <strong>${(template.templateName)?html}</strong>
                            <cite>最后编辑时间：${template.gmtModified?date?string('yyyy-MM-dd HH:mm:ss')}
                            <a href="${base}/logistics/editTemplate.htm?templateId=${template.id}">修改</a>
                           <a class="hrefDelete" href="javaScript:void(0)" onclick="delTemplate('${template.id}')">删除</a>
                            </cite>
                        </td>
                   	</tr>
                	<tr>
                    	<th>运送方式</th>
                    	<th>运费</th>
                    	<th>运送到</th>
                    	<th class="last">每多一件商品</th>
                    </tr>
                	<#list templateDetailMap[key] as detail>
					<tr>
					    <td>${detail.logisticsTypeName}</td>
					    <td>${detail.defaultCarriage}元</td>
					    <td>${detail.areaName}</td>
					    <td>+${detail.carriageIncrease}元</td>
					  </tr>
					</#list>
                	
                	<tr>
                    	<td colspan="4" class="explan"><strong>运费说明：</strong>${(template.memo!"")?html}</td>
                   	</tr>
                </table>
            	
            </ul>  
            
            	</#list>
			</#if> 
			
				
                    <div class="page-coder">
	                        <form action="" method="post" name="pageForm" id="pageForm">
	                        <#if templateList.size() != 0 >
								<#include "/default/templates/control/bottomPage.ftl">
							</#if>
						</form>
						
                    </div>
                </div>
                <div class="cf"></div>
    

 <input type="hidden" value="logistics-tool" id="my-page" />