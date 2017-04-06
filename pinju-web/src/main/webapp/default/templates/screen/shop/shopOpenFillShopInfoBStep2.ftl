<title>店铺--开店</title>
<link href="http://static.pinju.com/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<link media="screen" type="text/css" href="http://static.pinju.com/css/pj_base.css" rel="stylesheet">
<script type="text/javascript" src="http://static.pinju.com/showloading/jquery.showFullLoading.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<link href="http://static.pinju.com/showloading/showLoading.css" rel="stylesheet" media="screen" /> 
<link href="http://static.pinju.com/css/open.css?t=20111201" rel="stylesheet" type="text/css" media="screen" />
<link rel="icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="http://static.pinju.com/favicon.ico" type="image/x-icon" />
<SCRIPT src="http://static.pinju.com/js/util.js"></SCRIPT>
<script type="text/javascript" src="http://static.pinju.com/js/jquery-ui-1.8.13.datepicker.min.js"></script>
<script type="text/javascript" src="http://static.pinju.com/js/jquery.ui.datepicker-zh-CN.js"></script>
<SCRIPT src="http://static.pinju.com/js/shop/shop.js"></SCRIPT>
<!--<SCRIPT src="${base}/default/js/shop.js"></SCRIPT>-->
<#setting classic_compatible=true>
            	<div class="steper"><h2 class="steper02"></h2></div>
                <div class="info">
                    <ul class="tab-main">
                    	<li><a href="javascript:void(0)">店铺信息</a></li>
                    	<li class="count"><a href="javascript:void(0)">企业信息</a></li>
                    	<li><a href="javascript:void(0)">品牌相关</a></li>
                    	<li><a href="javascript:void(0)">联系方式</a></li>
                    </ul>
    <div class="shop_pointer_style">
     	<a class=" fr" href="http://service.pinju.com/cms/html/2011/teach_0825/53.html" target="_blank">了解更多开店详情</a>
     	<p class="red bd">以下资料附带 * 的都为必填项目,请详细填写</p>
     </div>

<iframe id="uploadIframe" name="uploadIframe" style="display:none;"></iframe>    
 <form id="shopInfoForm" action="${base}/shop/saveBusinessShopInfoAction.htm?fillStep=2" method="post" onsubmit="return checkBusinessShopStep2()" enctype ="multipart/form-data">    
   <input type="hidden" name="pj0" value="${pj0}" />
   
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <input type="hidden" id="sellerType" name="sellerType" value="${sellerType}"/>
   <input type="hidden" value="red_shopOpen" id="my-page" />
   <#if shopBusinessInfoDO?exists>
   <input type="hidden" id="myProvB" name="myProvC" value="${shopBusinessInfoDO.province}"/>
	<input type="hidden" id="myCityB" name="myCityC" value="${shopBusinessInfoDO.city}"/>
      <!--shopInfo2 begin-->
      <h4><strong>基本企业信息</strong></h4>
      <#if errorMessage?? && errorMessage!="">
		<div class="shop_pointer_style">
			<p class="red">${errorMessage}</p>
		</div>
	</#if>
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>企业所在地</th>
                        	<td>
                            	<span id="shopCityB"></span><span id="shopBusinessInfoDOprovincetip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>企业名称</th>
                        	<td><input type="text" id="enterpriseName" name="shopBusinessInfoDO.enterpriseName" maxlength="30" value="${shopBusinessInfoDO.enterpriseName?html}" onblur="checkOnblur('#enterpriseName','#enterpriseNametip')" class="text"><span id="enterpriseNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>工商营业执照注册号</th>
                        	<td><input type="text" id="businessLicenseNumber"  name="shopBusinessInfoDO.businessLicenseNumber" maxlength="18" value="${shopBusinessInfoDO.businessLicenseNumber?html}" onblur="spCheck('businessLicenseNumber','#businessLicenseNumbertip')" class="text" ><span id="businessLicenseNumbertip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>组织机构代码证号</th>
                        	<td><input type="text" id="organizationCodeNumber"  name="shopBusinessInfoDO.organizationCodeNumber" maxlength="10" value="${shopBusinessInfoDO.organizationCodeNumber?html}" onblur="spCheck('organizationCodeNumber','#organizationCodeNumber')" class="text" ><span id="organizationCodeNumbertip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>经营范围</th>
                        	<td><input type="text" id="business" name="shopBusinessInfoDO.business" value="${shopBusinessInfoDO.business?html}" maxlength="50" class="text" onblur="checkOnblur('#business','#businesstip')"><span id="businesstip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>营业执照有效期限</th>
                        	<td>
	                        	<#if shopBusinessInfoDO.businessLicenseEndDate??>
							      	<input type="text" readonly id="businessLicenseEndDate" name="businessLicenseEndDate"  value="${shopBusinessInfoDO.businessLicenseEndDate?string("yyyy-MM-dd")}"  onchange="spCheck('businessLicenseEndDate','#businessLicenseEndDatetip')" class="text"/>
							      <#else>
							      	<input type="text" readonly id="businessLicenseEndDate" name="businessLicenseEndDate"  value="${shopBusinessInfoDO.businessLicenseEndDate}" onchange="spCheck('businessLicenseEndDate','#businessLicenseEndDatetip')" class="text"/>
							     </#if>
							     <span id="businessLicenseEndDatetip" class="tip"></span>
                        	</td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>法人代表姓名</th>
                        	<td><input type="text" id="legalName"  name="shopBusinessInfoDO.legalName" value="${shopBusinessInfoDO.legalName?html}" maxlength="20" class="text" onblur="checkOnblur('#legalName','#legalNametip');spCheck('legalName','#legalNametip');"><span id="legalNametip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>企业注册地址</th>
                        	<td><input type="text" id="registAddress"  name="shopBusinessInfoDO.registAddress" value="${shopBusinessInfoDO.registAddress?html}" maxlength="50" class="text" onblur="checkOnblur('#registAddress','#registAddresstip')"><span id="registAddresstip"  class="tip"></span></td>
                        </tr>
                    </table>
                    <h4><strong>商户信息</strong></h4>
                  <table>
                         <tr>
                        	<th><font color="red"> * </font>是否有其他网店</th>
                        	<td>
                        	<#list isOpenOuterShopList as isOpenOuterShop>
                                 <#if isOpenOuterShop_index==0>
									<label>
										<input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}"  onclick="spCheck('isHaveOuterShop','.shopBusInfo')" checked="checked" />${isOpenOuterShop}
									</label>
								 <#else>
								      <label>
	                                     <input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}" onclick="spCheck('isHaveOuterShop','.shopBusInfo')"/>${isOpenOuterShop}
							          </label>
								</#if>
							  </#list>
                            </td>
                        </tr>
                  </table>
                  <div class="shopBusInfo">
                  <table>
                    	<tr>
                        	<th><font color="red"> * </font>网上店铺地址</th>
                        	<td><input id="shopAddressUrl" name="shopBusinessInfoDO.outerShopAddressUrl" type="text" class="text" value="${shopBusinessInfoDO.outerShopAddressUrl?html}" onblur="spCheck('shopAddressUrl','#shopAddressUrltip')"/><span id="shopAddressUrltip" class="tip"></span>
                            <p>淘宝、拍拍等网站的店铺地址。例如：http://shop.xxx.com/123456或者http://123456.xxx.com</p>
                            </td>
                        </tr>
                    	<tr>
                        	<th>店铺等级</th>
                        	<td>
                              <#list shopLevelList as shopLevel>
                                 <#if shopBusinessInfoDO.outerShopLevel == shopLevel_index>
									<label>
										<input id="shopLevel" name="shopBusinessInfoDO.outerShopLevel" type="radio" value="${shopLevel_index}" checked="checked" />${shopLevel}
									</label>
								 <#else>
								      <label>
	                                     <input id="shopLevel" name="shopBusinessInfoDO.outerShopLevel" type="radio" value="${shopLevel_index}" />${shopLevel}
							          </label>
								</#if>
							  </#list>
							   <span id="shopLeveltip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>年销售规模</th>
                        	<td>
                             <#list saleScopeList as saleScope>
                                 <#if shopBusinessInfoDO.outerShopSaleScope == saleScope_index>
									<label>
										<input id="saleScope" name="shopBusinessInfoDO.outerShopSaleScope" type="radio" value="${saleScope_index}" checked="checked" />${saleScope}
									</label>
								 <#else>
								      <label>
	                                     <input id="saleScope" name="shopBusinessInfoDO.outerShopSaleScope" type="radio" value="${saleScope_index}" />${saleScope}
							          </label>
								</#if>
							  </#list>
							   <span id="saleScopetip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>是否入驻过B2C</th>
                        	<td>
                            <#list isEnterB2cList as isEnterB2c>
                                 <#if shopBusinessInfoDO.isEnterB2c == isEnterB2c_index>
									<label>
										<input id="isEnterB2c" name="shopBusinessInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" checked="checked" />${isEnterB2c}
									</label>
								 <#else>
								      <label>
	                                     <input id="isEnterB2c" name="shopBusinessInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" />${isEnterB2c}
							          </label>
								</#if>
							  </#list>
							   <span id="isEnterB2ctip" class="tip"></span>
							   <p>例如：京东、当当、麦考林、易迅等大型B2C平台</p>
                            </td>
                        </tr>
                    </table>
                    </div>   
                	<h4><strong>企业证件</strong>文件类型仅限jpg, gif, png。文件大小500K以内</h4>
                    <table>
                   		<tr>
                        	<th><font color="red"> * </font>企业营业执照</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB1"><span id="myFileB1hui" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>组织机构代码证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB2"><span id="myFileB2hui" class="hui"></span>
                            </td>
                        </tr>
                   		<!--<tr>
                        	<th>品牌授权书或品牌商标注册证书</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB3"><span id="myFileB3hui" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>商品质量检验证书 </th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB4"><span id="myFileB4hui" class="hui"></span>
                            </td>
                        </tr>-->
                        <tr>
                        	<th><font color="red"> * </font>税务登记证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB3"><span id="myFileB3hui" class="hui"></span>
                            </td>
                        </tr>
                    </table>  
                	<!--<h4><strong>化妆品或食品行业请提交三证</strong>文件类型仅限jpg, gif, png。文件大小2M以内</h4>
                    <table>
                   		<tr>
                        	<th>卫生许可证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB5" onchange="spCheck('myFileB5','#myFileB5hui')"><span id="myFileB5hui" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>生产许可证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB6" onchange="spCheck('myFileB6','#myFileB6hui')"><span id="myFileB6hui" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>经营许可证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB7" onchange="spCheck('myFileB7','#myFileB7hui')"><span id="myFileB7hui" class="hui"></span>
                            </td>
                        </tr>
                        <tr>
                        	<th></th>
                        	<td>
                            	<font color="red">${errorMessage!}</font>
                            </td>
                        </tr>
                    </table> -->
      <!--shopInfo2 end-->
      
    
    <#else>
    
		      <!--shopInfo2 begin-->
			      <h4><strong>基本企业信息</strong></h4>
			      	<#if errorMessage?? && errorMessage!="">
		<div class="shop_pointer_style">
			<p class="red">${errorMessage}</p>
		</div>
	</#if>
                    <table>
                    	<tr>
                        	<th><font color="red"> * </font>企业所在地</th>
                        	<td>
                            	<span id="shopCityB"></span><span id="shopBusinessInfoDOprovincetip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>企业名称</th>
                        	<td><input type="text" id="enterpriseName" name="shopBusinessInfoDO.enterpriseName" class="text" maxlength="30" onblur="checkOnblur('#enterpriseName','#enterpriseNametip')"><span id="enterpriseNametip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>工商营业执照0注册号</th>
                        	<td><input type="text" id="businessLicenseNumber"  name="shopBusinessInfoDO.businessLicenseNumber" class="text" maxlength='18' onblur="spCheck('businessLicenseNumber','#businessLicenseNumbertip')"><span id="businessLicenseNumbertip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>组织机构代码证号</th>
                        	<td><input type="text" id="organizationCodeNumber"  name="shopBusinessInfoDO.organizationCodeNumber" class="text" maxlength='8' onblur="spCheck('organizationCodeNumber','#organizationCodeNumbertip')"><span id="organizationCodeNumbertip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>经营范围</th>
                        	<td><input type="text" id="business" name="shopBusinessInfoDO.business" class="text" maxlength="50" onblur="checkOnblur('#business','#businesstip')"><span id="businesstip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>营业执照有效期限</th>
                        	<td><input type="text" readonly id="businessLicenseEndDate" name="shopBusinessInfoDO.businessLicenseEndDate" onchange="spCheck('businessLicenseEndDate','#businessLicenseEndDatetip')" class="text"><span id="businessLicenseEndDatetip" class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>法人代表姓名</th>
                        	<td><input type="text" id="legalName"  name="shopBusinessInfoDO.legalName" onblur="checkOnblur('#legalName','#legalNametip');spCheck('legalName','#legalNametip');"  maxlength="20" class="text"><span id="legalNametip"  class="tip"></span></td>
                        </tr>
                    	<tr>
                        	<th><font color="red"> * </font>企业注册地址</th>
                        	<td><input type="text" id="registAddress"  name="shopBusinessInfoDO.registAddress" onblur="checkOnblur('#registAddress','#registAddresstip')" maxlength="50" class="text"><span id="registAddresstip"  class="tip"></span></td>
                        </tr>
                    </table> 
                     <h4><strong>商户信息</strong></h4>
                    <table>
                     <tr>
                        	<th><font color="red"> * </font>是否有其他网店</th>
                        	<td>
                        	<#list isOpenOuterShopList as isOpenOuterShop>
                                 <#if isOpenOuterShop_index==0>
									<label>
										<input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}"  onclick="spCheck('isHaveOuterShop','.shopBusInfo')" checked="checked" />${isOpenOuterShop}
									</label>
								 <#else>
								      <label>
	                                     <input id="isHaveOuterShop" name="isHaveOuterShop" type="radio" value="${isOpenOuterShop_index}" onclick="spCheck('isHaveOuterShop','.shopBusInfo')"/>${isOpenOuterShop}
							          </label>
								</#if>
							  </#list>
                            </td>
                        </tr>
                   </table>
                   <div class="shopBusInfo">
                   <table>
                    	<tr>
                        	<th><font color="red"> * </font>网上店铺地址</th>
                        	<td><input id="shopAddressUrl" name="shopBusinessInfoDO.outerShopAddressUrl" type="text" class="text" onblur="spCheck('shopAddressUrl','#shopAddressUrltip')"><span id="shopAddressUrltip" class="tip"></span>
                            <p>淘宝、拍拍等网站的店铺地址。例如：http://shop.xxx.com/123456或者http://123456.xxx.com</p>
                            </td>
                        </tr>
                    	<tr>
                        	<th>店铺等级</th>
                        	<td>
                            <#list shopLevelList as shopLevel>
								<label>
								  <input id="shopLevel" name="shopBusinessInfoDO.outerShopLevel" type="radio" value="${shopLevel_index}" onblur="spCheck('shopLevel','#shopLeveltip')"/>${shopLevel}
								</label>
							</#list>
							<span id="shopLeveltip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>年销售规模</th>
                        	<td>
                              <#list saleScopeList as saleScope>
									<label>
										<input id="saleScope" name="shopBusinessInfoDO.outerShopSaleScope" type="radio" value="${saleScope_index}" onblur="spCheck('saleScope','#saleScopetip')"/>${saleScope}
									</label>
							</#list>
							<span id="saleScopetip" class="tip"></span>
                            </td>
                        </tr>
                    	<tr>
                        	<th>是否入驻过B2C</th>
                        	<td>
                              <#list isEnterB2cList as isEnterB2c>
									<label>
										<input id="isEnterB2c" name="shopBusinessInfoDO.isEnterB2c" type="radio" value="${isEnterB2c_index}" onblur="spCheck('isEnterB2c','#isEnterB2ctip')"/>${isEnterB2c}
									</label>
							</#list>
							<span id="isEnterB2ctip" class="tip"></span>
							<p>例如：京东、当当、麦考林、易迅等大型B2C平台</p>
                            </td>
                        </tr>
                    </table> 
                	<h4><strong>企业证件</strong>文件类型仅限jpg, gif, png。文件大小500K以内</h4>
                    <table>
                   		<tr>
                        	<th><font color="red"> * </font>企业营业执照</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB1"><span id="myFileB1hui" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th><font color="red"> * </font>组织机构代码证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB2"><span id="myFileB2hui" class="hui"></span>
                            </td>
                        </tr>
                   		<!--<tr>
                        	<th>品牌授权书或品牌商标注册证书</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB3"><span id="myFileB3hui" class="hui"></span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>商品质量检验证书 </th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB4"><span id="myFileB4hui" class="hui"></span>
                            </td>
                        </tr>-->
                         <tr>
                        	<th><font color="red"> * </font>税务登记证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB3"><span id="myFileB3hui" class="hui"></span>
                            </td>
                        </tr>
                    </table>
                    </div>  
                	<!--<h4><strong>化妆品或食品行业请提交三证</strong>文件类型仅限jpg, gif, png。文件大小2M以内</h4>
                    <table>
                   		<tr>
                        	<th>卫生许可证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB5" onchange="spCheck('myFileB5','#myFileB5hui')"><span id="myFileB5hui" class="hui">jpg,gif,png</span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>生产许可证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB6" onchange="spCheck('myFileB6','#myFileB6hui')"><span id="myFileB6hui" class="hui">jpg,gif,png</span>
                            </td>
                        </tr>
                   		<tr>
                        	<th>经营许可证</th>
                        	<td class="picture-up">
                            	<input type="file" name="myFile" id="myFileB7" onchange="spCheck('myFileB7','#myFileB7hui')"><span id="myFileB7hui" class="hui">jpg,gif,png</span>
                            </td>
                        </tr>
                        <tr>
                        	<th></th>
                        	<td>
                            	<font color="red">${errorMessage!}</font>
                            </td>
                        </tr>
                    </table> -->
			      <!--shopInfo2 end-->
			      
    </#if>
					<!--
					<h4><strong>您需要提交以下实物资料:</strong></h4>    
                    <div class="address">
                    	<p><strong>样品</strong>--您所销售的样品</p>
                    	<p>关于实物样品审核：品聚有权视实际审核情况要求商户寄送样品进行审核。样品资质审核完毕，商户可选择该样品进入品聚样品陈列库，进行备案，或者选择由品聚网在30个工作日内寄还商户。 </p>
                    	
                    	<p><strong>证件</strong>--请参考以下“商品经营资质”说明</p>
                        <p><strong>上海市浦东新区福山路388号宏嘉大厦8楼，商务部收&nbsp;&nbsp;邮编：200122</strong></p>
                    </div> 
                    	<h4><strong>商品经营资质</strong></h4>    
                    <div class="paper">
                    	<ul>
                        	<p class="title"><strong>1、</strong>若您申请服饰、童装、家纺类目的旗舰店，每个品牌须提供至少一份由第三方权威机构出具的质检报告。同时，品聚将会不定期针对经营的商品进行质检报告的抽查，请商户备案备查。</p>
                        	<p>各类产品的检测报告必须包含的检测项目如下：</p>
                            <li><span>a)&nbsp;男装/女装/袜子：</span>成分含量、色牢度【包括耐水色牢度、耐汗渍色牢度、耐摩擦色牢度】、标识标志、外观质量；</li>
                            <li><span>b)&nbsp;文胸/塑身服/童装/孕妇装类：</span>成分含量、色牢度【包括耐水色牢度、耐汗渍色牢度、耐摩擦色牢度】、甲醛含量、标识标志、外观质量；</li>
                            <li><span>c)&nbsp;三岁以下婴幼儿服装类：</span>成分含量、色牢度【包括耐水色牢度、耐汗渍色牢度、耐摩擦色牢度、耐唾液色牢度】、甲醛含量、标识标志、外观质量；</li>
                            <li><span>d)&nbsp;家居服：</span>成分含量、色牢度【包括耐水色牢度、耐汗渍色牢度、耐摩擦色牢度】、标识标志、外观质量、水洗尺寸变化率；</li>
                            <li><span>e)&nbsp;保暖内衣/床上套件：</span>成分含量、色牢度【包括耐水色牢度、耐汗渍色牢度、耐摩擦色牢度】、甲醛含量、标识标志、外观质量、水洗尺寸变化率；</li>
                            <li><span>f)&nbsp;羽绒服装：</span>成分含量、色牢度【包括耐水色牢度、耐汗渍色牢度、耐摩擦色牢度】、标识标志、外观质量、含绒量、充绒量；</li>
                            <li><span>g)&nbsp;羽绒被芯：</span>成分含量、标识标志、含绒量、充绒量；</li>
                            <li><span>h)&nbsp;棉被/蚕丝被芯：</span>成分含量、标识标志、原料要求；</li>
                            <li>皮革/皮草类服装：甲醛含量、可分解芳香胺染料、标识标志、外观检测。</li>
                    	</ul>                    	
                    	<ul>
                        	<p class="title"><strong>2、</strong>若您申请母婴类目，但产品涉及食品或化妆品，则需同时提供食品、化妆品类目需要的资质；若您申请家居类目，产品涉及日化清洁产品，则需同时提供化妆品类目的相关资质。</p>
                    	</ul>  
                    	<ul>
                        	<p class="title"><strong>3、</strong>若您经营食品，需额外提供如下资料：</p>
                        	<p><b>商户为生产厂商：</b></p>
                            <li><span>a)&nbsp;</span>食品生产厂商持有的《食品生产许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>b)	食品生产厂商持有的《食品卫生许可证》复印件（若商户所在地区以上两证已合并为一个证件，只需提供《食品生产许可证》）；</li>
                            <li><span>c)&nbsp;</span>商户出具的质量保证书。</li>
                            
                        	<p><b>商户非生产厂商（品牌系委托他人生产）：</b></p>
                            <li><span>a)&nbsp;</span>提供商户与生产厂商间的委托加工协议复印件；</li>
                            <li><span>b)&nbsp;</span>生产厂商持有的《食品生产许可证》复印件；</li>
                            <li><span>c)&nbsp;</span>商户自身持有的《食品流通许可证》复印件；</li>
                            <li><span>d)&nbsp;</span>商户出具的质量保证书。</li>
                    	</ul>                    	
                    	<ul>
                        	<p class="title"><strong>4、</strong>若您经营保健食品，需额外提供如下资料：</p>
                        	<p><b>商户为生产厂商：</b></p>
                            <li><span>a)&nbsp;</span>保健食品生产厂商持有的《食品生产许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>保健食品生产厂商持有的《食品卫生许可证》复印件；</li>
                            <li><span>c)&nbsp;</span>保健食品生产厂商持有的《保健食品批准证书》（即批准文号）复印件；</li>
                            <li><span>d)&nbsp;</span>近一年内商品的检验检疫合格报告或证明复印件；</li>
                            <li><span>e)&nbsp;</span>商户出具的质量保证书。</li>
                            
                        	<p><b>商户非生产厂商（品牌系委托他人生产）：</b></p>
                            <li><span>a)&nbsp;</span>商户自身持有的《食品流通许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>保健食品生产厂商的营业执照副本复印件；</li>
                            <li><span>c)&nbsp;</span>保健食品生产厂商持有的《食品生产许可证》复印件；</li>
                            <li><span>d)&nbsp;</span>保健食品生产厂商持有的《食品卫生许可证》复印件；</li>
                            <li><span>e)&nbsp;</span>保健食品生产厂商持有的《保健食品批准证书》（即批准文号）复印件；</li>
                            <li><span>f)&nbsp;</span>近一年内商品的检验检疫合格报告或证明复印件；</li>
                            <li><span>g)&nbsp;</span>质监局出具的委托加工备案登记表复印件；</li>
                            <li><span>h)&nbsp;</span>商户出具的质量保证书。</li>
                    	</ul>                    	
                    	<ul>
                        	<p class="title"><strong>5、</strong>若您经营酒类，需额外提供如下资料。</p>
                        	<p><b>商户为生产厂商：</b></p>
                            <li><span>a)&nbsp;</span>商户自身持有的《食品生产许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>商户自身持有的《酒类流通备案登记表》（或《酒类零售许可证》、《酒类批发许可证》、《酒类产销许可证》）；</li>
                            <li><span>c)&nbsp;</span>商户出具的质量保证书。</li>
                            
                        	<p><b>商户非生产厂商（品牌系委托他人生产）：</b></p>
                            <li><span>a)&nbsp;</span>商户自身持有的《食品流通许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>商户自身持有的《酒类流通备案登记表》（或《酒类零售许可证》、《酒类批发许可证》、《酒类产销许可证》）复印件；</li>
                            <li><span>c)&nbsp;</span>生产厂商持有的《食品生产许可证》复印件；</li>
                            <li><span>d)&nbsp;</span>商户与生产厂商间的委托加工协议复印件；</li>
                            <li><span>e)&nbsp;</span>商户出具的质量保证书。</li>
                    	</ul>                    	
                    	<ul>
                        	<p class="title"><strong>6、</strong>若您经营化妆品，需额外提供如下资料</p>
                        	<p><b>商户为生产厂商：</b></p>
                            <li><span>a)&nbsp;</span>化妆品生产厂商持有的《化妆品卫生许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>化妆品生产厂商持有的《化妆品生产许可证》（即《全国工业产品生产许可证》）复印件；</li>
                            <li><span>c)&nbsp;</span>化妆品生产厂商持有的企业营业执照副本复印件；</li>
                            <li><span>d)&nbsp;</span>近一年内所有单品的检验检疫合格的报告或证明复印件；</li>
                            <li><span>e)&nbsp;</span>如系特殊用途化妆品须提交针对该特殊用途化妆品的批准文件；</li>
                            <li><span>f)&nbsp;</span>商户出具的质量保证书。</li>
                            
                        	<p><b>商户为非生产厂商（品牌系委托他人生产）：</b></p>
                            <li><span>a)&nbsp;</span>化妆品生产厂商持有的《化妆品卫生许可证》复印件；</li>
                            <li><span>b)&nbsp;</span>化妆品生产厂商持有的《化妆品生产许可证》（即《全国工业产品生产许可证》）复印件；</li>
                            <li><span>c)&nbsp;</span>化妆品生产厂商持有的企业营业执照副本复印件；</li>
                            <li><span>d)&nbsp;</span>商户与生产厂商间的委托加工协议复印件；</li>
                            <li><span>e)&nbsp;</span>近一年内所有单品的检验检疫合格的报告或证明复印件；</li>
                            <li><span>f)&nbsp;</span>如系特殊用途化妆品须提交针对该特殊用途化妆品的批准文件；</li>
                            <li><span>g)&nbsp;</span>商户出具的质量保证书。</li>
                    	</ul>                    	
                    	<ul>
                        	<p class="title"><strong>7、</strong>若您经营进口商品，需额外提供如下资料</p>
                        	<p><b>进口食品（包括酒类）：</b></p>
                            <li><span>a)&nbsp;</span>中华人民共和国进口货物报关单复印件；</li>
                            <li><span>b)&nbsp;</span>商品出入境检验检疫合格证明或卫生证书复印件。</li>
                            
                        	<p><b>进口保健食品：</b></p>
                            <li><span>a)&nbsp;</span>中华人民共和国进口货物报关单复印件；</li>
                            <li><span>b)&nbsp;</span>商品出入境检验检疫合格证明或卫生证书复印件；</li>
                            <li><span>c)&nbsp;</span>《进口保健食品批准证书》（即批准文号）复印件。</li>
                            
                        	<p><b>进口化妆品：</b></p>
                            <li><span>a)&nbsp;</span>中华人民共和国海关进口货物报关单复印件；</li>
                            <li><span>b)&nbsp;</span>商品出入境检验检疫合格证明或卫生证书复印件；</li>
                            <li><span>c)&nbsp;</span>中华人民共和国卫生部（或食品药品监督管理局）进口非特殊用途化妆品备案凭证复印件；</li>
                            <li><span>d)&nbsp;</span>如系进口特殊用途化妆品须提交针对该特殊用途化妆品的批准文件复印件。</li>
                            <li><span>注：</span>进口酒类、进口保健食品、食品、化妆品在中国大陆办理商标注册申请即可。</li>
                    	</ul>                    	
                    	<ul>
                        	<p class="title"><strong>8、</strong>若您经营旅游类商品，商户名列国家旅游局最新公布的“全国百强旅行社”名单或景点景区、租车公司、邮轮公司、高尔夫球场及其它旅游企业以自有品牌入驻，可申请“旗舰店”，需提交如下资料：</p>
                            <li><span>a)&nbsp;</span>企业营业执照副本复印件（需完成有效年检且营业执照经营范围需包含所售商品）；</li>
                            <li><span>b)&nbsp;</span>企业税务登记证复印件（国税、地税均可）；</li>
                            <li><span>c)&nbsp;</span>旅行社企业需提供《旅行社业务经营许可证》复印件。</li>
                    	</ul>                    	
                    </div> -->                              
                    <ul class="button-main">
                    	<li>
                    		<p id="submitError" style="color:red"></p>
                    		<a class="button" href="/shop/prevStepAction.htm?prevStep=1">上一步</a>
                    		<input id="step2Submit" type="submit" value="下一步" class="button">
                    	</li>      	
                    </ul>
                </div>
                <div class="cf"></div>
                
                <input type="hidden" name="_token_" value="${_token_}" />
                </form>
<script>


function addShopInfoToCookieStep2(form, callback){
     $('input,textarea', form).each(function() {
        var input = this;
		if ("onpropertychange" in input){ //IE6/IE7/IE8
			input.onpropertychange = function(){
				if (window.event.propertyName == "value"){
					callback.call(this, window.event);
				}
			}
		} else {
			// Fix Firefox Bug: https://bugzilla.mozilla.org/show_bug.cgi?id=195696
			input.addEventListener("input", callback, false);
		}
	});
	$('select', form).change(function() {
		callback.call(this, window.event);
	});
}	
addShopInfoToCookieStep2($('#shopInfoForm'), function(){
	var province = $("#shopBusinessInfoDOprovince").val();
	var city = $("#shopBusinessInfoDOcity").val();
	var enterpriseName = $("#enterpriseName").val();
	var businessLicenseNumber = $("#businessLicenseNumber").val();
	var organizationCodeNumber = $("#organizationCodeNumber").val();
	var business = $("#business").val();
	var businessLicenseEndDate = $("#businessLicenseEndDate").val();
	var legalName = $("#legalName").val();
	var registAddress = $("#registAddress").val();
	var shopAddressUrl = $("#shopAddressUrl").val();
	
	province = "province="+encodeURIComponent(province);
	city = "city="+encodeURIComponent(city);
	enterpriseName = "enterpriseName="+encodeURIComponent(enterpriseName);
	businessLicenseNumber = "businessLicenseNumber="+businessLicenseNumber;
	organizationCodeNumber = "organizationCodeNumber="+organizationCodeNumber;
	business = "business="+encodeURIComponent(business);
	businessLicenseEndDate = "businessLicenseEndDate="+businessLicenseEndDate;
	legalName = "legalName="+encodeURIComponent(legalName);
	registAddress = "registAddress="+encodeURIComponent(registAddress);
	shopAddressUrl = "shopAddressUrl="+encodeURIComponent(shopAddressUrl);
	
	var strCookie = province + "," + enterpriseName + "," +businessLicenseNumber + "," + organizationCodeNumber + "," 
					+ business + "," + businessLicenseEndDate + "," + legalName + "," + registAddress + "," +shopAddressUrl + "," + city + ",";
	
	PinjuCookie.writeCookie('PJ2', strCookie, 'www.pinju.com', '/shop', 3);
});
</script>
                