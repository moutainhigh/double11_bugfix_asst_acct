				
			<div class="rights_box cf">
					<div class="tit">
						<span class="txt">维权申请协议</span>
					</div>
					<div class="rights_goods">
						<table class="rights_goods_tab">
							<tr>
								<th class="bla1">商品</th>
								<th class="bla2">单价(元)</th>
								<th class="bla3">数量</th>
								<th class="bla4">优惠(元)</th>
								<th class="bla5">小计(元)</th>
							</tr>
							<tr>
								<td>
									<div class="goods" style='text-align:center'>
										<div class="pic_box">
											<#assign orderItemDO = query.orderItemDO>
											<#if orderItemDO?? && orderItemDO.itemPicUrl??>
											<a href="${imageServer!}${(orderItemDO.itemPicUrl)?html}" target="_blank"><img width="88" height="88" src="${imageServer!}${(orderItemDO.itemPicUrl)?html}" /></a>
											</#if>
										</div>
										<span class="bd">
											<#if orderItemDO?? && orderItemDO.itemId?? && orderItemDO.itemTitle??>
												<a target="_blank" class="lh20 items_link" href="${base}/snapshot/itemSnapshot.htm?arg1=${(orderItemDO.snapId)!}">${orderItemDO.itemTitle?html?js_string}</a>
											</#if>
										</span>
										<#if orderItemDO?? && orderItemDO.itemSkuAttributes??>
											<span class="gray size">${orderItemDO.itemSkuAttributes}</span>
										</#if>
									</div>
								</td>
								<#assign price = orderItemDO.originalPrice / 100 >
								<td><span class="bd">${price?string("0.00")}</span></td>
								<td><span class="bd">${orderItemDO.buyNum}</span></td>
								<td><span class="bd">${orderItemDO.discountAmountByYuan}</span></td>
								<td><span class="bd">${orderItemDO.totalAmountByYuan}</span></td>
							</tr>
						</table>
					</div>
					<ul class="rights_list rights_app">
						<li>
							<span>维权申请时间：</span>
							<div>${rightsDO.applyDate?string("yyyy-MM-dd HH:mm:ss")}</div>
						</li>
						<li>
							<span>维权原因：</span>
							<div>
								<#if rightsDO.voucherType==0>
									七天无理由退货
								<#elseif rightsDO.voucherType==1>
									质量问题
								<#elseif rightsDO.voucherType==2>
									假一赔三
								</#if>
							</div>
						</li>
						<li>
							<span>是否需要退货：</span>
							<div>
								<#if rightsDO.isReturnGoods==1 >
									是
								<#else>
									否
								</#if>
							</div>
						</li>
						<li><span>维权状态：</span>
							<div class="bd red">
								<#if rightsDO.state == 0>
									维权申请中
								<#elseif rightsDO.state == 1>
									<#if rightsDO.buyerRequire=1>
										等待买家退还商品
									<#elseif rightsDO.buyerRequire=0>
										卖家同意,等待打款
									</#if>
								<#elseif rightsDO.state == 12>
									等待买家退还商品
								<#elseif rightsDO.state == 2>
									维权申请被拒绝
								<#elseif rightsDO.state == 3 ||rightsDO.state == 11>
									等待卖家确认收货
								<#elseif rightsDO.state == 4>
									客服介入中
								<#elseif rightsDO.state == 5>
									卖家同意,维权成功
								<#elseif rightsDO.state == 6>
									维权关闭
								<#elseif rightsDO.state == 7>
									客服仲裁维权成功
								<#elseif rightsDO.state == 8>
									客服仲裁维权关闭
								<#elseif rightsDO.state == 9>
									客服仲裁成功,等待打款
								<#elseif rightsDO.state == 10>
									维权协议已达成，等待打款
								</#if>
							</div>
						</li>
						<#assign rprice = rightsDO.buyerApplyPrice / 100 >
						<li><span>维权金额：</span><div>${rprice?string("0.00")}(元)</div></li>
						<li><span>维权说明：</span><div>${rightsDO.buyerReason}</div></li>
						<li>
							<span>附件：</span>
							<div class="picbox">
								<#if rightsDO.voucherPic1?? && rightsDO.voucherPic1 !=""><a href="${imageServer?html}${(rightsDO.voucherPic1)!}" target="_blank"><img width="94" height="94" src="${imageServer?html}${(rightsDO.voucherPic1)!}"></a></#if>
								<#if rightsDO.voucherPic2?? && rightsDO.voucherPic2 !=""><a href="${imageServer?html}${(rightsDO.voucherPic2)!}" target="_blank"><img width="94" height="94" src="${imageServer?html}${(rightsDO.voucherPic2)!}"></a></#if>
								<#if rightsDO.voucherPic3?? && rightsDO.voucherPic3 !=""><a href="${imageServer?html}${(rightsDO.voucherPic3)!}" target="_blank"><img width="94" height="94" src="${imageServer?html}${(rightsDO.voucherPic3)!}"></a></#if>
							</div>
						</li>
					</ul>
			</div>