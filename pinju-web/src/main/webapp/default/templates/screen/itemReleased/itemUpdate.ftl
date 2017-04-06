<#setting classic_compatible=true>
<#setting number_format="#">
<title>编辑商品</title>
<link rel="stylesheet" href="http://static.pinju.com/kindeditor/themes/default/default.css?t=20111122.css" />
<script src="http://static.pinju.com/kindeditor/kindeditor-min.js?t=20111122.js"></script>
<script src="http://static.pinju.com/kindeditor/config.js"></script>
<script src="http://static.pinju.com/kindeditor/lang/zh_CN.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/jquery.form.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/util.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/item/item.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/json2.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/sku.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/item/spu.js?t=20111122.js"></script>
<script src="http://static.pinju.com/js/item/itemUpdate.js?t=20111122.js"></script>
<script src="http://static.pinju.com/showloading/jquery.showFullLoading.js?t=20111122.js"></script>
<!-- START <选择商品类目> -->
<script>
window.onload = function(){
	document.getElementById("released").disabled="";
}
</script>
<div class="wrap-border cf">
	<div class="bar-left">
		<h2 class="title-goods">
			产品信息
		</h2>
		<ul class="goods-class-lister">
			<#if (categoryQuery.categoryTitle==null)>
			<li class="imgbtn">
				<button id="updateCategory" name="updateCategory" type="button" class="btn-sorange">编辑类目</button>
			</li>
			<#else>
			<li>
				<strong>类目：</strong> ${categoryQuery.categoryTitle}
			</li>
			<li>
				<ul id="spuShow">
				</ul>
			</li>
			</#if>
		</ul>
	</div>
	<div class="main-box">
		<ul id="itemRelMsg" class="goods-notic" style="display:none;">
			<h3>
				以下发布信息未达到发布要求，请您审核后继续发布。
			</h3>
			<span id="itemMsg">
			</span>
		</ul>
		<h2 class="title-goods">
			一、商品基本信息
		</h2>
		<form id="itemReleasedForm" name="mainform">
			<input type="hidden" id="catetoryId" name="itemInput.catetoryId" value="${itemInput.catetoryId}">
			<input type="hidden" id="id" name="itemInput.id" value="${itemInput.id}">
			<input type="hidden" id="spuId" name="itemInput.spuId" value="${itemInput.spuId}">
			<input type="hidden" id="staticServer" name="staticServer" value="${staticServer}">
			<input type="hidden" id="imageServer" name="imageServer" value="${imageServer}">
			<input type="hidden" id="propertyValuePair" name="itemInput.propertyValuePair" value="">
			<input type="hidden" id="skuPropertyValuePair" name="itemInput.skuPropertyValuePair" value="">
			<input type="hidden" id="imagButtonType" name="imagButtonType" value="">
			
			<table class="goods-info-lister">
				<tr>
					<th>
						<cite>*</cite>商品类型：
					</th>
					<td>
						<label>
							<input id="radioItemType1" name="itemInput.itemDegree" type="radio" value="1" <#if itemInput.itemDegree == 1> checked = "checked" </#if> />
							全新
						</label>
						<label>
							<input id="radioItemType2" name="itemInput.itemDegree" type="radio" value="2" <#if itemInput.itemDegree == 2> checked = "checked" </#if>/>
							二手
						</label>
						<label>
							<input id="radioItemType3" name="itemInput.itemDegree" type="radio" value="3" <#if itemInput.itemDegree == 3> checked = "checked" </#if>/>
							个人闲置
						</label>
					</td>
				</tr>
				<#if (categoryQuery.propertySize>0)>
					<tr>
						<th>
							<cite>&nbsp;&nbsp;</cite>商品属性：
						</th>
						<td>
							<!-- START <选择商品类目>-->
							<table class="goods-lister" id="itemAtt">
								<#if categoryQuery.keyCategoryPropertyList?exists>
									<#if (categoryQuery.keyCategoryPropertyList?size>0)>
										<!-- 关键属性 -->
										<#list categoryQuery.keyCategoryPropertyList as ls>
											<tr cId = "${ls.cpId}"  need = "${ls.required}" 
								      				mult="${ls.isMultipleChoice}" enu="${ls.isEnumerate}" name="${ls.cpName}">
												<th><#if ls.required == 1><cite>*</cite><#else><cite>&nbsp;&nbsp;</cite></#if>${ls.cpName}：</th>
												<td>
													<#if ls.isEnumerate == 2>
														<#list ls.cpvList as v>
															<#if ls.inputType == 2>
																<input maxlength="${ls.lenLimit}" class="category-property" type="text" id="${ls.cpId}" value="${v.cpvValue?html}">
																请将字数保持在${ls.lenLimit}个字内
															<#elseif ls.inputType == 0>
																<input maxlength="60" class="category-property" type="text" id="${ls.cpId}" value="${v.cpvValue}" onkeyup="clearNoNum(this)"
									      						onblur="checkinput(this,${ls.maxValue},${ls.minValue},${ls.cpId})">
									      						请输入界于${ls.minValue}和${ls.maxValue}之间的数
															<#elseif ls.inputType == 1>
																<input maxlength="60" class="category-property" type="text" id="${ls.cpId}" value="${v.cpvValue}" onkeyup="clearNoNumLong(this)"
									      						onblur="checkinput(this,${ls.maxValue},${ls.minValue},${ls.cpId})">
									      						请输入界于${ls.minValue}和${ls.maxValue}之间的数
															</#if>
								      					</#list>
								      				<#else>
								      					<#if ls.isMultipleChoice == 1>
								      						<select id="property-${ls.cpId}" cid="${ls.cpId}" cids="${ls.childIds}"
								      							<#if ls.isSpuKey==1>
								      								class="spu-select category-property"
								      							<#else>
								      								class="category-property"
								      							</#if>
								      						><option value="0"></option>
											      				<#list ls.cpvList as v>
												      			<option value="${(v.cpvId)!}"
												      				<#if v.isSelect == 1>
									      								selected = "selected"
									      							</#if>
												      			>${v.cpvValue}</option>
												      			</#list>
											      			</select>
								      					<#elseif ls.isMultipleChoice == 2>
								      						<#list ls.cpvList as v>
										      					<input class="category-property" type="radio" id="${v.cpvId}" name="cpv${ls.cpId}" <#if v.isSelect == 1>checked = "checked"</#if>>${v.cpvValue}&nbsp;&nbsp;
										      				</#list>
								      					<#elseif ls.isMultipleChoice == 3>
								      						<#list ls.cpvList as v>
										      					<input class="category-property" type="checkbox" id="${v.cpvId}" name="cpv${ls.cpId}" <#if v.isSelect == 1>checked = "checked"</#if>>${v.cpvValue}&nbsp;&nbsp;
										      				</#list>
								      					</#if>
								      				</#if>
												</td>
											</tr>
										</#list>
									</#if>
								</#if>
								<#if categoryQuery.categoryPropertyList?exists>
									<#if (categoryQuery.categoryPropertyList?size>0)>
										<!-- 关键属性 -->
										<#list categoryQuery.categoryPropertyList as ls>
											<tr cId = "${ls.cpId}"  need = "${ls.required}" 
								      				mult="${ls.isMultipleChoice}" enu="${ls.isEnumerate}" name="${ls.cpName}">
												<th><#if ls.required == 1><cite>*</cite><#else><cite>&nbsp;&nbsp;</cite></#if>${ls.cpName}：</th>
												<td>
													<#if ls.isEnumerate == 2>
														<#list ls.cpvList as v>
															<#if ls.inputType == 2>
																<input maxlength="${ls.lenLimit}" class="category-property" type="text" id="${ls.cpId}" value="${v.cpvValue?html}">
																请将字数保持在${ls.lenLimit}个字内
															<#elseif ls.inputType == 0>
																<input maxlength="60" class="category-property" type="text" id="${ls.cpId}" value="${v.cpvValue}" onkeyup="clearNoNum(this)"
									      						onblur="checkinput(this,${ls.maxValue},${ls.minValue},${ls.cpId})">
									      						请输入界于${ls.minValue}和${ls.maxValue}之间的数
															<#elseif ls.inputType == 1>
																<input maxlength="60" class="category-property" type="text" id="${ls.cpId}" value="${v.cpvValue}" onkeyup="clearNoNumLong(this)"
									      						onblur="checkinput(this,${ls.maxValue},${ls.minValue},${ls.cpId})">
									      						请输入界于${ls.minValue}和${ls.maxValue}之间的数
															</#if>
								      					</#list>
								      				<#else>
								      					<#if ls.isMultipleChoice == 1>
								      						<select id="property-${ls.cpId}" cid="${ls.cpId}" cids="${ls.childIds}"
								      							<#if ls.isSpuKey==1>
								      								class="spu-select category-property"
								      							<#else>
								      								class="category-property"
								      							</#if>
								      						><option value="0"></option>
											      				<#list ls.cpvList as v>
												      				<option value="${(v.cpvId)!}" <#if v.isSelect == 1>selected = "selected"</#if>>${v.cpvValue}</option>
												      			</#list>
											      			</select>
								      					<#elseif ls.isMultipleChoice == 2>
								      						<#list ls.cpvList as v>
										      					<input class="category-property" type="radio" id="${v.cpvId}" name="cpv${ls.cpId}" <#if v.isSelect == 1>checked = "checked"</#if>>${v.cpvValue}&nbsp;&nbsp;
										      				</#list>
								      					<#elseif ls.isMultipleChoice == 3>
								      						<#list ls.cpvList as v>
										      					<input class="category-property" type="checkbox" id="${v.cpvId}" name="cpv${ls.cpId}" <#if v.isSelect == 1>checked = "checked"</#if>>${v.cpvValue}&nbsp;&nbsp;
										      				</#list>
								      					</#if>
								      				</#if>
												</td>
											</tr>
										</#list>
									</#if>
								</#if>
							</table>
							<!-- END -->
						</td>
					</tr>
				</#if>
				<tr>
					<th>
						<cite>*</cite>商品标题：
					</th>
					<td>
						<input id="txtItemTitle" name="itemInput.title" class="text" type="text" maxlength="60" value="${(itemInput.title)?html}"/>
						请将字数保持在30个汉字内（60个字符）
					</td>
				</tr>
				<tr>
					<th>
						<cite>*</cite>一口价：
					</th>
					<td class="short">
						<input id="txtItemPrice" name="itemInput.price" class="text" type="text"  maxlength="12" value="${(itemInput.price)!}"/>
						元
					</td>
				</tr>
				
				<#if categoryQuery.sellCategoryPropertyList?exists>
					<#if (categoryQuery.sellCategoryPropertyList?size>0)>
							<#list categoryQuery.sellCategoryPropertyList as ls>
								<tr class="sku-option" sku-name="${ls.cpId}" sku-title="${ls.cpName}" sku-custom="${ls.isSellCustom}">
							      	<th><cite>&nbsp;&nbsp;</cite>${ls.cpName}：</th>
							      	<td>
								      	<#if ls.isMultipleChoice == 3>
								      		<#list ls.cpvList as v>
												<input class="category-property" type="checkbox" data-custom-id="${v.id}" data-custom-name="${v.value?html}" data-custom-url="${v.imgUrl}" sku-name="${v.cpvId}" id="${(v.cpvId)!}" name="cpv${ls.cpId}" data-value-type="${v.valueType}" data-show-value="${v.showValue?html}" value="${v.cpvValue?html}" <#if v.isSelect == 1>checked = "checked"</#if>>
												<#if v.valueType == 2><label for="${(v.cpvId)!}" class="custom-sku-block custom-sku-bgcolor-${v.cpvValue?html}" title="${v.showValue?html}"></label>
												<#elseif v.valueType == 3><label for="${(v.cpvId)!}" class="custom-sku-block" style="background-color:${v.cpvValue?html};" title="${v.showValue?html}"></label>
												<#else><label for="${(v.cpvId)!}">${v.cpvValue?html}</label>
												</#if>
											</#list>
								      	<#else>
								      		<#list ls.cpvList as v>
											    <input class="category-property" type="radio" data-custom-id="${v.id}" data-custom-name="${v.value?html}" data-custom-url="${v.imgUrl}" id="${(v.cpvId)!}" sku-name="${v.cpvId}" name="cpv${ls.cpId}" data-value-type="${v.valueType}" data-show-value="${v.showValue?html}" value="${v.cpvValue?html}" <#if v.isSelect == 1>checked = "checked"</#if>>
											    <#if v.valueType == 2><label for="${(v.cpvId)!}" class="custom-sku-block" style="background-image:url(${v.cpvValue?html});" title="${v.showValue?html}"></label>
												<#elseif v.valueType == 3><label for="${(v.cpvId)!}" class="custom-sku-block" style="background-color:${v.cpvValue?html};" title="${v.showValue?html}"></label>
												<#else><label for="${(v.cpvId)!}">${v.cpvValue?html}</label>
												</#if>
										    </#list>
								      	</#if>
							        </td>
								</tr>
							</#list>
							<tr>
								<th></th>
								<td>
									<table class="l-table" style="display:none;">
										<tr id="SkuFormHead">
											<th>
												价格<span class="required">*</span>
											</th>
											<th>
												数量<span class="required">*</span>
											</th>
											<th>
												商家编码
											</th>
										</tr>
										<tr id="SkuFormRow">
											<td class="short">
												<input name="" type="text" class="text sku-price">元</td>
											<td class="short">
												<input name="" type="text" class="text sku-capacity">件</td>
											<td class="short">
												<input name="" type="text" class="text sku-sellerCode" maxlength="30" style="width:100px"></td>
											<td style="display:none;">
												<input name="" type="hidden" class="sku-id" /></td>
										</tr>
									</table>
									<table class="l-table" id="SkuForm"></table>
									<#if categoryQuery.skuList?exists>
										<#if (categoryQuery.skuList?size>0)>
											<span id="skuList">
												<#list categoryQuery.skuList as v>
													<input type="hidden" name="update-${v.values}" id='${v.id}' value="${v.id}" pv="${v.values}" price="${v.price}" stock="${v.currentStock}" sellerCode="${v.sellerCode}">
												</#list>
											</span>
										</#if>
									</#if>
								</td>
							</tr>
					</#if>
				</#if>
				
				<tr>
					<th>
						<cite>&nbsp;&nbsp;</cite>商家编码：
					</th>
					<td class="short">
						<input type="text" class="text" name="itemInput.code" maxlength="30" value="${(itemInput.code)!}"/>
					</td>
				</tr>
				<tr>
					<th>
						<cite>*</cite>商品数量：
					</th>
					<td class="short">
						<input id="txtItemNumber" name="itemInput.number" value="${(itemInput.number)!}" class="text" type="text" maxlength="12"/>
						件&nbsp;&nbsp;请认真填写。无货挂空，可能引起投诉或退款。
					</td>
				</tr>
				<tr>
					<th>
						<cite>&nbsp;&nbsp;</cite>商品图片：
					</th>
					<input id="itemEditPicUrl1" value="${itemInput.itemEditPicUrl[0]}" name="itemInput.itemEditPicUrl" type="hidden" />
					<input id="itemEditPicUrl2" value="${itemInput.itemEditPicUrl[1]}" name="itemInput.itemEditPicUrl" type="hidden" />
					<input id="itemEditPicUrl3" value="${itemInput.itemEditPicUrl[2]}" name="itemInput.itemEditPicUrl" type="hidden" />
					<input id="itemEditPicUrl4" value="${itemInput.itemEditPicUrl[3]}" name="itemInput.itemEditPicUrl" type="hidden" />
					<input id="itemEditPicUrl5" value="${itemInput.itemEditPicUrl[4]}" name="itemInput.itemEditPicUrl" type="hidden" />
					<td style="position:relative;">
						<ul class="pic-box">
							<li class="header">
								<span><!--1200*1200px以上的图片可在商品详情页提供图片放大功能--></span>
							</li>
							<li id="picImgLi1" class="hasimg">
								<span class="preview"><img id="picImg1" src="${imageServer}${itemInput.picUrl}_80x80.jpg"></span>
								<span class="upload"><a href="javascript:"><input hidefocus="true" id="picFile1" name="imgFile" type="file">
								</a></span>
								<span class="act"><span class="del" onClick="removePic(1);">删除</span></span>
							</li>
							<li id="picImgLi2" 
								<#if (itemInput.itemPicUrl[0])??>
								class="hasimg">
									<span class="preview"><img id="picImg2" src="${imageServer}${itemInput.itemPicUrl[0]}_80x80.jpg"></span>
								<#else>
								>
									<span class="preview"><img id="picImg2" class="pic-none" src="${staticServer}/img/pic-none.jpg"></span>
								</#if>
								<span class="upload"><a href="javascript:"><input hidefocus="true" id="picFile2" name="imgFile"  type="file">
								</a></span>
								<span class="act"><span class="del" onClick="removePic(2);">删除</span></span>
							</li>
							<li id="picImgLi3"
								<#if (itemInput.itemPicUrl[1])??>
								class="hasimg">
									<span class="preview"><img id="picImg3" src="${imageServer}${itemInput.itemPicUrl[1]}_80x80.jpg"></span>
								<#else>
								>
									<span class="preview"><img id="picImg3" class="pic-none" src="${staticServer}/img/pic-none.jpg"></span>
								</#if>
								<span class="upload"><a href="javascript:"><input hidefocus="true" id="picFile3" name="imgFile" type="file">
								</a></span>
								<span class="act"><span class="del" onClick="removePic(3);">删除</span></span>
							</li>
							<li id="picImgLi4"
								<#if (itemInput.itemPicUrl[2])??>
								class="hasimg">
									<span class="preview"><img id="picImg4" src="${imageServer}${itemInput.itemPicUrl[2]}_80x80.jpg"></span>
								<#else>
								>
									<span class="preview"><img id="picImg4" class="pic-none" src="${staticServer}/img/pic-none.jpg"></span>
								</#if>
								<span class="upload"><a href="javascript:"><input hidefocus="true" id="picFile4" name="imgFile" type="file">
								</a></span>
								<span class="act"><span class="del" onClick="removePic(4);">删除</span></span>
							</li>
							<li id="picImgLi5"
								<#if (itemInput.itemPicUrl[3])??>
								class="last hasimg">
									<span class="preview"><img id="picImg5" src="${imageServer}${itemInput.itemPicUrl[3]}_80x80.jpg"></span>
								<#else>
								class="last">
									<span class="preview"><img id="picImg5" class="pic-none" src="${staticServer}/img/pic-none.jpg"></span>
								</#if>
								<span class="upload"><a href="javascript:"><input hidefocus="true" id="picFile5" name="imgFile" type="file">
								</a></span>
								<span class="act"><span class="del" onClick="removePic(5);">删除</span></span>
							</li>
							<!--<li>
								<div style="width:100px;height:100px;"><img id="picImg" src="${imageServer}${itemInput.picUrl}"></div>
								<a href="javascript:" ><input hidefocus="true" id="picFile" name="itemInput.picFile" type="file"></a>
							</li>-->
						</ul>
						图片大小不能超过500K，<a href="javascript:;" id="J_selectImageFromSpace">从图片空间选择图片</a>
					</td>
				</tr>
				
				<#if shopCategoryList?exists>
				<tr>
					<th>
						<cite>&nbsp;&nbsp;</cite>店铺中所有类目：
					</th>
					<td>
						<div class="shop-class">
							
								<#list shopCategoryList.keySet() as key>
	                        		<#if (shopCategoryList.get(key).subCategoryList?size > 0)>
	                        			<ul>
											<li> 
												${shopCategoryList.get(key).categoryName}
											</li>
											<#list shopCategoryList.get(key).subCategoryMap.keySet() as val>
			                            		<li>
													<label>
													<#if (shopCategoryList.get(key).categoryImage!=null&&shopCategoryList.get(key).categoryImage!="")>
														<#assign checked =""> 
														<#list shopCategoryList.get(key).categoryImage.split(",") as value>
															<#if value==val>
																&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="itemInput.storeCategories" checked="checked" type="checkbox" value="${val}">
																${shopCategoryList.get(key).subCategoryMap.get(val)?html}
																<#assign checked ="checked"> 
																<#break>
															</#if>
														</#list>
														<#if (checked== "")>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="itemInput.storeCategories" type="checkbox" value="${val}">
															${shopCategoryList.get(key).subCategoryMap.get(val)?html}
														</#if>
			                            			<#else>
			                            				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="itemInput.storeCategories" type="checkbox" value="${val}">
														${shopCategoryList.get(key).subCategoryMap.get(val)?html}
			                            			</#if>				
													</label>
												</li>
			                            	</#list>
		                            	</ul>
	                            	<#else>
	                            		<ul>
		                            		<li>
		                            			<label>
		                            			<#if (shopCategoryList.get(key).categoryImage=="checked")>
		                            				<input name="itemInput.storeCategories" type="checkbox" checked="checked" value="${shopCategoryList.get(key).id}">${shopCategoryList.get(key).categoryName?html}
		                            			<#else>
		                            				<input name="itemInput.storeCategories" type="checkbox" value="${shopCategoryList.get(key).id}">${shopCategoryList.get(key).categoryName?html}
		                            			</#if>		
		                            			</label>
		                            		</li>
	                            		</ul>
									</#if>
								</#list>
							</ul>
						</div>
					</td>
				</tr>
				</#if>
				<tr>
					<th>
						<cite>*</cite>商品描述：
					</th>
					<td>
						<textarea id="txaItemDiscription" name="itemInput.description" rows="12" cols="80" style="width: 80%">${itemInput.description?html}</textarea>
						源码：已输入<span id="disLength">0</span> 最多输入<font color='red'>40000</font>
					</td>
				</tr>
			</table>
			<h2 class="title-goods">
				二、商品物流信息
			</h2>
			<table class="goods-info-lister">
				<tr>
					<th>
						<cite>*</cite>所在地：
					</th>
					<td>
						<div class="goods-lister">
							<ul>
								<li>
									<input type="hidden" id="currProvinces" value="${itemInput.provinces}">
									<input type="hidden" id="currCity" value="${itemInput.city}">
									<span id="provcity"></span>
								</li>
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>
						<cite>&nbsp;&nbsp;</cite>运费：
					</th>
					<td>
						<div class="goods-lister">
							<ul>
								<li>
									<label>
										<input id="freightRadio1" name="itemInput.freightType" onclick="$('#txtFreightP').hide();" type="radio" value="1"
											<#if itemInput.freightType==1>
												checked = "checked"
											</#if>
										/>
										卖家承担运费
									</label>
								</li>
								<!--<li>
									<label>
										<input id="freightRadio2" name="itemInput.freightType" onclick="$('#txtFreightP').show();" type="radio" value="2" checked />
										买家承担运费
									</label>
								</li>
								<li class="traffic" id="txtFreightP">
									<span>平邮：<input id="txtMailCosts" class="text" name="itemInput.mailCosts" type="text" size="12" maxlength="12">元</span>
									<span style="display:none">EMS：<input id="txtEmsCosts" class="text" name="itemInput.emsCosts" type="text" size="12" maxlength="12">元</span>
									<span style="display:none">快递：<input id="txtDeliveryCosts" class="text" name="itemInput.deliveryCosts" type="text" size="12" maxlength="12">元</span>
									<div class="cf"></div>
								</li>-->
								<ul>
									<li>
										<label>
											<input  id="freightRadio2" name="itemInput.freightType" onclick="$('#txtFreightP').show();" type="radio" value="2"
												<#if itemInput.freightType==2>
													checked = "checked"
												</#if>
											/>
											买家承担运费
										</label>
									</li>
									<li class="traffic" id="txtFreightP" <#if itemInput.freightType==1>style="display:none;"</#if>>
										<div class="traffic-txt">
											<label>
												<input id="TransportRadio" name="itemInput.buyFreightType" type="radio" value="1"
													<#if itemInput.buyFreightType==1>
														checked = "checked"
													</#if> 
												/>
												<input id="TransportHidden" value="${itemInput.freeTemplates}" name="itemInput.freeTemplates" type="hidden" />
												<span id="listTemplateButton">使用运费模板</span>
											</label>
											<div class="traffic-btn">
												<#if itemInput.freeTemplatesName!=null&&!itemInput.freeTemplatesName.equals("")>
												<span id="TransportTitle">${itemInput.freeTemplatesName?html}</span>
												<#else>
												<span id="TransportTitle">请点击</span>
												</#if>
												<!--【有模板和未选模板文字】请点击按钮--><!--【已经选择了模板】当前运费模板：模板名称模板名称-->
												<a href="javascript:" id="TransportTrigger">创建运费模板</a>
												<!----><!-- START <弹出层>-->
												<div class="lay hide" id="TransportSelecter">
													<h3>
														<cite>
															<a href="javascript:void(0)" id="TransportClose">×关闭</a>
														</cite>请选择运费模板
													</h3>
													<span id="TransportList">
													</span>
													<div class="pager">
														<ul>
															<a href="javascript:void(0)" id="TransportP">上一页</a>
															<b><span id="TransportPage">1/N</span></b>
															<a href="javascript:void(0)" id="TransportN">下一页</a>
														</ul>
													</div>
													<div class="cf"></div>
												</div>
											</div>
										</div>
										<div class="cf"></div>
										<span>
											<label>
												<input name="itemInput.buyFreightType" id="CostsRadio"  type="radio" value="2" onclick="setCostsRadio();"
													<#if itemInput.buyFreightType==2>
														checked = "checked"
													</#if>
												/>
												平邮：
											</label>
											<input id="txtMailCosts" onfocus="setCostsRadio();" <#if itemInput.mailCosts>value="${itemInput.mailCosts}"</#if> name="itemInput.mailCosts" type="text" size="12" maxlength="12" value="0">
											元
											&nbsp;&nbsp;快递：
											<input id="txtDeliveryCosts" onfocus="setCostsRadio();"  <#if itemInput.deliveryCosts>value="${itemInput.deliveryCosts}"</#if> name="itemInput.deliveryCosts" type="text" size="12" maxlength="12" value="0">
											元
											&nbsp;&nbsp;EMS：
											<input id="txtEmsCosts" onfocus="setCostsRadio();" <#if itemInput.emsCosts>value="${itemInput.emsCosts}"</#if> name="itemInput.emsCosts" type="text" size="12" maxlength="12" value="0">元</span>
										<div class="cf"></div>
									</li>
									<li class="traffic">EMS填0时，前台将不显示该项。</li>
								</ul>
							</ul>
						</div>
					</td>
				</tr>
			</table>
			<h2 class="title-goods">
				三、其他信息
			</h2>
			<table class="goods-info-lister">
				<!--<tr>
					<th>
						<cite>&nbsp;&nbsp;</cite>有效期：
					</th>
					<td>
						<label>
							<input id="radioExpiryDate" name="itemInput.expiryType" type="radio" value="7" checked/>
							7天
						</label>
						<label>
							<input id="radioExpiryDate" name="itemInput.expiryType" type="radio" value="14" />
							14天
						</label>
						<label>
							<input id="radioExpiryDate" name="itemInput.expiryType" type="radio" value="21" />
							21天
						</label>
					</td>
				</tr>-->
				<tr>
					<th>
						<cite>&nbsp;&nbsp;</cite><!--开始时间：-->
					</th>
					<td>
						<div class="goods-lister">
							<span id="selectStartDate"></span>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<ul class="button-group">
			<li>
				<input id="released" type="button" class="button" value="更新" disabled="disabled">
			</li>
			<li>
				<input name="" type="button" class="button" value="返回" onclick="javascript:history.go(-1);">
			</li>
		</ul>
	</div>
	
</div>
<!-- END -->

