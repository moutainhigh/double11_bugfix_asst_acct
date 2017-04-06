<#setting classic_compatible=true>
<LINK href="http://static.pinju.com/css/logistics.css" rel="stylesheet" />

<script src="http://static.pinju.com/js/util.js"></script>
<script src="http://static.pinju.com/js/logistics/createTemplate.js"></script>

<title>品聚网</title>

<form name="form" id="form" action="${base}/logistics/saveTemplate.htm" method="post">
<input type="hidden" id="templateId" name="templateId" value="${templateId}">
<input type="hidden" id="_logisticsTypePost" value="${logisticsTypePost}">
<input type="hidden" id="_logisticsTypeCourier" value="${logisticsTypeCourier}">
<input type="hidden" id="_logisticsTypeEMS" value="${logisticsTypeEMS}">
<input type="hidden" id="isTempName" name="isTempName" value="${(templateName)?html}">

<!-- 隐藏文本框区域 -->
<!-- 存放平邮信息 -->
<input type="hidden" id="postInfor" style="width:300px"/>
<!-- 存放快递信息 -->
<input type="hidden" id="courierInfor" style="width:300px"/>
<!-- 存放EMS信息-->
<input type="hidden" id="emsInfor" style="width:300px"/>
<!-- 将平邮、快递、EMS结合-->
<input type="hidden" id="allInfor" name="tempinfor" style="width:400px"/>

			
				<div class="transport">
					<ul class="tab-main">
						<li class="count">
							<a href="##">新增运费模板</a>
						</li>
					</ul>
					
					
					
					<ul class="transpor-tinfo">
					
					
					
						<h3>请输入运费模板名称：<span>(提示：您可以按照物品重量和体积来命名。比如“小于一公斤的物品”)</span></h3>
						<li>
							<input id="templateName" name="templateName" type="text" value="${(templateName)?html}" class="text" maxlength=25></li>
						<li>
						<li id="tipTemplateName" class="cheng" style="display:none">运费模板名称为必填项，并且不能超过25个字</li>
						
						<li id="tipTemplateNameExist" class="cheng" style="display:none">运费模板名称不可重复</li>
						
					</ul>

					<ul class="transpor-tinfo">
						<h3>请选择并添加运费方式：<span>(提示：除指定地区外，其余地区的运费采用“默认运费”)</span></h3>
						<li id="tipLogisticsType" class="cheng" style="display:none">请至少选择一种物流模式！</li>
						<li>
							<label>
								<input type="checkbox" id="logisticsTypePost" name="logisticsTypePost" class="logisticsType" value="1" />平邮
							</label>
						</li>
						<div class="pay-info" id="divlogisticsType1">
							<div class="first">
								请设置默认运费：
								<input id="postFee" name="postFee" type="text" value="${postFee!"0.01"}" class="text pinju-fee" onFocus="f_focus(this)" onBlur = "f_blur(this)"/>
								元，
								每超过一件需要增加运费：
								<input id="postFeeIncrease" name="postFeeIncrease" value="${postFeeIncrease!"0.00"}" type="text" class="text pinju-fee" style="width:40px;" onFocus="f_focus(this)" onBlur = "f_blur(this)"/>元
							</div>
								
							<div id="tipPostFee" class="cheng" style="padding:5px;">运费必须为有效值（大于0.00元），且不得大于999.99元</div>
						    <div id="tipPostFeeIncrease" class="cheng" style="padding:5px;">商品的加收运费不得大于商品运费的数值</div>
								
							<!--动态生成(平邮：为指定地区设定运费)-->
							<dl class="lister" id="postTemp">
								<#if tempDetailMap?exists>
									<#assign key='key'+(templateId?c)>
									<#list tempDetailMap[key] as arae>
									     <#if arae.logisticsTypeId=1>
											<dl class="lister" id="postTemp1">
												<#assign areaName=""/>
												<#list arae.areaName?split(",") as c>
												    <#assign areaName="${areaName},${c?split('|')[1]}"/>
												</#list>
											        至<input id="txtPostAddress" readonly="readonly" type="text" value="${areaName?substring(1)}" class="text" title=""><input id="txtPostAddressCode" readonly="readonly" type="hidden" value="${arae.areaName}" class="text" title="">的运费：<input id="txtPostMoney" type="text" value="${arae.defaultCarriage}" class="text pinju-number" onFocus="f_focus(this)" onBlur = "f_blur(this)" >元，每多一件商品加收<input id="txtPostManyMoney" type='text' value="${arae.carriageIncrease}" class="text pinju-number" onFocus="f_focus(this)" onBlur = "f_blur(this)" >	元<a href="##" onclick="deltemp(this)" title="删除后别忘记点击“保存并返回”">删除</a>
											</dl>
										</#if>
									</#list>
								</#if>
							</dl>
							
							<div id="tipPostArea" class="cheng" style="padding:5px;"></div>
							
							
							<div>
								<a href="##" class="add-area-pack">为指定地区设置运费</a>
							</div>
							<div class="transport-area">
								<p>
									<cite>
										<a href="##" class="cancel"></a>
									</cite>选择地区
								</p>
								<table>
									<tr>
										<td>
											<input id="jzh" type="checkbox" value="">江浙沪</td>
										<td>
											<input id="hd" type="checkbox" value="">华东</td>
										<td>
											<input id="hb" type="checkbox" value="">华北</td>
										<td>
											<input id="hz" type="checkbox" value="">华中</td>
										<td>
											<input id="hn" type="checkbox" value="">华南</td>
									</tr>
									<tr>
										<td>
											<input id="db" type="checkbox" value="">东北</td>
										<td>
											<input id="xb" type="checkbox" value="">西北</td>
										<td>
											<input id="xn" type="checkbox" value="">西南</td>
										<td>
											<input id="gat" type="checkbox" value="">港澳台</td>
										<td>
											<input id="hw" type="checkbox" value="">海外</td>
									</tr>
								</table>
								<table id="postTable">
									<tr>
										<td> 
											<input id="3" type="checkbox" value="11|北京">北京</td>
										<td>
											<input id="3" type="checkbox" value="12|天津">天津</td>
										<td>
											<input id="3" type="checkbox" value="13|河北">河北</td>
										<td>
											<input id="3" type="checkbox" value="14|山西">山西</td>
										<td>
											<input id="3" type="checkbox" value="15|内蒙古">内蒙古</td>
									</tr>
									<tr>
										<td>
											<input id="6" type="checkbox" value="21|辽宁">辽宁</td>
										<td>
											<input id="6" type="checkbox" value="22|吉林">吉林</td>
										<td>
											<input id="6" type="checkbox" value="23|黑龙江">黑龙江</td>
										<td>
											<input id="1" type="checkbox" value="31|上海">上海</td>
										<td>
											<input id="1" type="checkbox" value="32|江苏">江苏</td>
									</tr>
									<tr>
										<td>
											<input id="1" type="checkbox" value="33|浙江">浙江</td>
										<td>
											<input id="2" type="checkbox" value="34|安徽">安徽</td>
										<td>
											<input id="5" type="checkbox" value="35|福建">福建</td>
										<td>
											<input id="2" type="checkbox" value="36|江西">江西</td>
										<td>
											<input id="3" type="checkbox" value="37|山东">山东</td>
									</tr>
									<tr>
										<td>
											<input id="4" type="checkbox" value="41|河南">河南</td>
										<td>
											<input id="4" type="checkbox" value="42|湖北">湖北</td>
										<td>
											<input id="4" type="checkbox" value="43|湖南">湖南</td>
										<td>
											<input id="5" type="checkbox" value="44|广东">广东</td>
										<td>
											<input id="5" type="checkbox" value="45|广西">广西</td>
									</tr>
									<tr>
										<td>
											<input id="5" type="checkbox" value="46|海南">海南</td>
										<td>
											<input id="8" type="checkbox" value="50|重庆">重庆</td>
										<td>
											<input id="8" type="checkbox" value="51|四川">四川</td>
										<td>
											<input id="8" type="checkbox" value="52|贵州">贵州</td>
										<td>
											<input id="8" type="checkbox" value="53|云南">云南</td>
									</tr>
									<tr>
										<td>
											<input id="8" type="checkbox" value="54|西藏">西藏</td>
										<td>
											<input id="7" type="checkbox" value="61|陕西">陕西</td>
										<td>
											<input id="7" type="checkbox" value="62|甘肃">甘肃</td>
										<td>
											<input id="7" type="checkbox" value="63|青海">青海</td>
										<td>
											<input id="7" type="checkbox" value="64|宁夏">宁夏</td>
									</tr>
									<tr>
										<td>
											<input id="7" type="checkbox" value="65|新疆">新疆</td>
										<td>
											<input id="9" type="checkbox" value="71|台湾">台湾</td>
										<td>
											<input id="9" type="checkbox" value="81|香港">香港</td>
										<td>
											<input id="9" type="checkbox" value="82|澳门">澳门</td>
										<td>
											<input id="10" type="checkbox" value="99|海外">海外</td>
									</tr>
								</table>
								<input type="button" value="确定" id="postOK">
								&nbsp;&nbsp;
								<input type="button" value="取消" id="postClose" class="close">
							</div>
						</div>
						<li>
							<label>
								<input type="checkbox" id="logisticsTypeCourier" name="logisticsTypeCourier" class="logisticsType" value="2" />快递</label>
						</li>
						<div class="pay-info" id="divlogisticsType2">
							<div class="first">
								请设置默认运费：
								<input id="courierFee" name="courierFee" type="text" value="${courierFee!"0.01"}" class="text pinju-fee" onFocus="f_focus(this)" onBlur = "f_blur(this)" />
								元，
								每超过一件需要增加运费：
								<input id="courierFeeIncrease" name="courierFeeIncrease" type="text" value="${courierFeeIncrease!"0.00"}" class="text pinju-fee" onFocus="f_focus(this)" onBlur = "f_blur(this)" />元
							</div>
								
							<div id="tipCourierFee" class="cheng" style="padding:5px;">运费必须为有效值（大于0.00元），且不得大于999.99元</div>
						    <div id="tipCourierFeeIncrease" class="cheng" style="padding:5px;">商品的加收运费不得大于商品运费的数值</div> 
								
							<!--动态生成(快递：为指定地区设定运费)-->
							<dl class="lister" id="courierTemp">
								<#if tempDetailMap?exists>
									<#assign key='key'+(templateId?c)>
										<#list tempDetailMap[key] as arae>
											<#if arae.logisticsTypeId=2>
												<dl class="lister" id="courierTemp1">
													<#assign areaName=""/>
													<#list arae.areaName?split(",") as c>
													    <#assign areaName="${areaName},${c?split('|')[1]}"/>
													</#list>
													<input id="txtCourierAddressCode" readonly="readonly" type="hidden" value="${arae.areaName}" class="text" title="">
												        至<input id="txtCourierAddress" readonly="readonly" type="text" value="${areaName?substring(1)}" class="text" title="">的运费：<input id="txtCourierMoney" type="text" value="${arae.defaultCarriage}" class="text pinju-number" onFocus="f_focus(this)" onBlur = "f_blur(this)" >元，每多一件商品加收<input id="txtCourierManyMoney" type='text' value="${arae.carriageIncrease}" class="text pinju-number" onFocus="f_focus(this)" onBlur = "f_blur(this)" >元<a href="##" onclick="deltemp(this)" title="删除后别忘记点击“保存并返回”">删除</a>
												</dl>
											</#if>
											
									</#list>
								</#if>
							</dl>
							
							<div id="tipCourierArea" class="cheng" style="padding:5px;"></div>
							
							<div>
								<a href="##" class="add-area-pack">为指定地区设置运费</a>
							</div>
							<div class="transport-area">
								<p>
									<cite>
										<a href="##" class="cancel"></a>
									</cite>选择地区
								</p>
								<table>
									<tr>
										<td>
											<input id="jzh" type="checkbox" value="">江浙沪</td>
										<td>
											<input id="hd" type="checkbox" value="">华东</td>
										<td>
											<input id="hb" type="checkbox" value="">华北</td>
										<td>
											<input id="hz" type="checkbox" value="">华中</td>
										<td>
											<input id="hn" type="checkbox" value="">华南</td>
									</tr>
									<tr>
										<td>
											<input id="db" type="checkbox" value="">东北</td>
										<td>
											<input id="xb" type="checkbox" value="">西北</td>
										<td>
											<input id="xn" type="checkbox" value="">西南</td>
										<td>
											<input id="gat" type="checkbox" value="">港澳台</td>
										<td>
											<input id="hw" type="checkbox" value="">海外</td>
									</tr>
								</table>
								<table id="courierTable">
									<tr>
										<td> 
											<input id="3" type="checkbox" value="11|北京">北京</td>
										<td>
											<input id="3" type="checkbox" value="12|天津">天津</td>
										<td>
											<input id="3" type="checkbox" value="13|河北">河北</td>
										<td>
											<input id="3" type="checkbox" value="14|山西">山西</td>
										<td>
											<input id="3" type="checkbox" value="15|内蒙古">内蒙古</td>
									</tr>
									<tr>
										<td>
											<input id="6" type="checkbox" value="21|辽宁">辽宁</td>
										<td>
											<input id="6" type="checkbox" value="22|吉林">吉林</td>
										<td>
											<input id="6" type="checkbox" value="23|黑龙江">黑龙江</td>
										<td>
											<input id="1" type="checkbox" value="31|上海">上海</td>
										<td>
											<input id="1" type="checkbox" value="32|江苏">江苏</td>
									</tr>
									<tr>
										<td>
											<input id="1" type="checkbox" value="33|浙江">浙江</td>
										<td>
											<input id="2" type="checkbox" value="34|安徽">安徽</td>
										<td>
											<input id="5" type="checkbox" value="35|福建">福建</td>
										<td>
											<input id="2" type="checkbox" value="36|江西">江西</td>
										<td>
											<input id="3" type="checkbox" value="37|山东">山东</td>
									</tr>
									<tr>
										<td>
											<input id="4" type="checkbox" value="41|河南">河南</td>
										<td>
											<input id="4" type="checkbox" value="42|湖北">湖北</td>
										<td>
											<input id="4" type="checkbox" value="43|湖南">湖南</td>
										<td>
											<input id="5" type="checkbox" value="44|广东">广东</td>
										<td>
											<input id="5" type="checkbox" value="45|广西">广西</td>
									</tr>
									<tr>
										<td>
											<input id="5" type="checkbox" value="46|海南">海南</td>
										<td>
											<input id="8" type="checkbox" value="50|重庆">重庆</td>
										<td>
											<input id="8" type="checkbox" value="51|四川">四川</td>
										<td>
											<input id="8" type="checkbox" value="52|贵州">贵州</td>
										<td>
											<input id="8" type="checkbox" value="53|云南">云南</td>
									</tr>
									<tr>
										<td>
											<input id="8" type="checkbox" value="54|西藏">西藏</td>
										<td>
											<input id="7" type="checkbox" value="61|陕西">陕西</td>
										<td>
											<input id="7" type="checkbox" value="62|甘肃">甘肃</td>
										<td>
											<input id="7" type="checkbox" value="63|青海">青海</td>
										<td>
											<input id="7" type="checkbox" value="64|宁夏">宁夏</td>
									</tr>
									<tr>
										<td>
											<input id="7" type="checkbox" value="65|新疆">新疆</td>
										<td>
											<input id="9" type="checkbox" value="71|台湾">台湾</td>
										<td>
											<input id="9" type="checkbox" value="81|香港">香港</td>
										<td>
											<input id="9" type="checkbox" value="82|澳门">澳门</td>
										<td>
											<input id="10" type="checkbox" value="99|海外">海外</td>
									</tr>
								</table>
								<input type="button" value="确定" id="courierOK">
								&nbsp;&nbsp;
								<input type="button" value="取消" id="courierclose" class="close">
							</div>
						</div>
						<li>
							<label>
								<input type="checkbox" id="logisticsTypeEMS" name="logisticsTypeEMS" class="logisticsType" value="3" />EMS</label>
						</li>
						<div class="pay-info" id="divlogisticsType3">
							<div class="first">
								请设置默认运费：
								<input id="emsFee" name="emsFee" type="text" value="${emsFee!"0.01"}" class="text pinju-fee" onFocus = "f_focus(this)" onBlur = "f_blur(this)" />
								元，
								每超过一件需要增加运费：
								<input id="emsFeeIncrease" name="emsFeeIncrease" type="text" value="${emsFeeIncrease!"0.00"}" class="text pinju-fee" onFocus = "f_focus(this)" onBlur = "f_blur(this)" />元
							</div>
								
							<div id="tipEmsFee" class="cheng" style="padding:5px;">运费必须为有效值（大于0.00元），且不得大于999.99元</div>
							<div id="tipEmsFeeIncrease" class="cheng" style="padding:5px;">商品的加收运费不得大于商品运费的数值</div>
								
							<!--动态生成(EMS：为指定地区设定运费)-->
							<dl class="lister" id="emsTemp">
							
								<#if tempDetailMap?exists>
									<#assign key='key'+(templateId?c)>
										<#list tempDetailMap[key] as arae>
											 <#if arae.logisticsTypeId=3>
												<dl class="lister" id="courierTemp1">
													<#assign areaName=""/>
													<#list arae.areaName?split(",") as c>
													    <#assign areaName="${areaName},${c?split('|')[1]}"/>
													</#list>
													<input id="txtEmsAddressCode" readonly="readonly" type="hidden" value="${arae.areaName}" class="text" title="">
												        至<input id="txtEmsAddress" readonly="readonly" type="text" value="${areaName?substring(1)}" class="text" title="">的运费：<input id="txtEmsMoney" type="text" value="${arae.defaultCarriage}" class="text pinju-number" onFocus="f_focus(this)" onBlur = "f_blur(this)" >元，每多一件商品加收<input id="txtEmsManyMoney" type='text' value="${arae.carriageIncrease}" class="text pinju-number" onFocus="f_focus(this)" onBlur = "f_blur(this)" >元<a href="##" onclick="deltemp(this)" title="删除后别忘记点击“保存并返回”">删除</a>
												</dl>
											 </#if>
									</#list>
								</#if>
								
							</dl>
							
							<div id="tipEmsArea" class="cheng" style="padding:5px;"></div>
							
							
							<div>
								<a href="##" class="add-area-pack">为指定地区设置运费</a>
							</div>
							<div class="transport-area">
								<p>
									<cite>
										<a href="##" class="cancel"></a>
									</cite>选择地区
								</p>
								<table>
									<tr>
										<td>
											<input id="jzh" type="checkbox" value="">江浙沪</td>
										<td>
											<input id="hd" type="checkbox" value="">华东</td>
										<td>
											<input id="hb" type="checkbox" value="">华北</td>
										<td>
											<input id="hz" type="checkbox" value="">华中</td>
										<td>
											<input id="hn" type="checkbox" value="">华南</td>
									</tr>
									<tr>
										<td>
											<input id="db" type="checkbox" value="">东北</td>
										<td>
											<input id="xb" type="checkbox" value="">西北</td>
										<td>
											<input id="xn" type="checkbox" value="">西南</td>
										<td>
											<input id="gat" type="checkbox" value="">港澳台</td>
										<td>
											<input id="hw" type="checkbox" value="">海外</td>
									</tr>
								</table>
								<table id="EMSTable">
									<tr>
										<td> 
											<input id="3" type="checkbox" value="11|北京">北京</td>
										<td>
											<input id="3" type="checkbox" value="12|天津">天津</td>
										<td>
											<input id="3" type="checkbox" value="13|河北">河北</td>
										<td>
											<input id="3" type="checkbox" value="14|山西">山西</td>
										<td>
											<input id="3" type="checkbox" value="15|内蒙古">内蒙古</td>
									</tr>
									<tr>
										<td>
											<input id="6" type="checkbox" value="21|辽宁">辽宁</td>
										<td>
											<input id="6" type="checkbox" value="22|吉林">吉林</td>
										<td>
											<input id="6" type="checkbox" value="23|黑龙江">黑龙江</td>
										<td>
											<input id="1" type="checkbox" value="31|上海">上海</td>
										<td>
											<input id="1" type="checkbox" value="32|江苏">江苏</td>
									</tr>
									<tr>
										<td>
											<input id="1" type="checkbox" value="33|浙江">浙江</td>
										<td>
											<input id="2" type="checkbox" value="34|安徽">安徽</td>
										<td>
											<input id="5" type="checkbox" value="35|福建">福建</td>
										<td>
											<input id="2" type="checkbox" value="36|江西">江西</td>
										<td>
											<input id="3" type="checkbox" value="37|山东">山东</td>
									</tr>
									<tr>
										<td>
											<input id="4" type="checkbox" value="41|河南">河南</td>
										<td>
											<input id="4" type="checkbox" value="42|湖北">湖北</td>
										<td>
											<input id="4" type="checkbox" value="43|湖南">湖南</td>
										<td>
											<input id="5" type="checkbox" value="44|广东">广东</td>
										<td>
											<input id="5" type="checkbox" value="45|广西">广西</td>
									</tr>
									<tr>
										<td>
											<input id="5" type="checkbox" value="46|海南">海南</td>
										<td>
											<input id="8" type="checkbox" value="50|重庆">重庆</td>
										<td>
											<input id="8" type="checkbox" value="51|四川">四川</td>
										<td>
											<input id="8" type="checkbox" value="52|贵州">贵州</td>
										<td>
											<input id="8" type="checkbox" value="53|云南">云南</td>
									</tr>
									<tr>
										<td>
											<input id="8" type="checkbox" value="54|西藏">西藏</td>
										<td>
											<input id="7" type="checkbox" value="61|陕西">陕西</td>
										<td>
											<input id="7" type="checkbox" value="62|甘肃">甘肃</td>
										<td>
											<input id="7" type="checkbox" value="63|青海">青海</td>
										<td>
											<input id="7" type="checkbox" value="64|宁夏">宁夏</td>
									</tr>
									<tr>
										<td>
											<input id="7" type="checkbox" value="65|新疆">新疆</td>
										<td>
											<input id="9" type="checkbox" value="71|台湾">台湾</td>
										<td>
											<input id="9" type="checkbox" value="81|香港">香港</td>
										<td>
											<input id="9" type="checkbox" value="82|澳门">澳门</td>
										<td>
											<input id="10" type="checkbox" value="99|海外">海外</td>
									</tr>
								</table>
								<input type="button" value="确定" id="emsOK">
								&nbsp;&nbsp;
								<input type="button" value="取消" id="emsClose" class="close">
								</div>
						</div>
					</ul>
					
					<ul class="transpor-tinfo">
						<h3>请添加运费说明：<span>(提示：您可以设置发货时间、到货时间以及快递公司网址等内容，细致的说明有助于减少交易纠纷)</span></h3>
						<li class="explain">
							<textarea id="templateMemo" name="templateMemo">${templateMemo?html}</textarea>
							
						</li>
						<li id="tipTemplateMemo" class="cheng" style="display:none">运费说明最多可以输入20个字</li>
					</ul>
					
						
					
					
					<ul class="button-group">
						<li>
							<input id="saveTemplate" type="button" value="保存" class="button" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input id="returnTempList" type="button" value="返回" class="button" />
						</li>

					</ul>
				</div>
				<div class="cf"></div>
<input type="hidden" value="logistics-tool" id="my-page" />
</form>