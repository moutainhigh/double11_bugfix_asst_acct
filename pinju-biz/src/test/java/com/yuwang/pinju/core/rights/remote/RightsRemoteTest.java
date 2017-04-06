package com.yuwang.pinju.core.rights.remote;

import junit.framework.Assert;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.rights.ao.RightsAO;
import com.yuwang.pinju.domain.rights.RightsDO;

/**
 * @Project: pinju-biz
 * @Description: 维权远程接口测试(HESSIAN实现)
 * @author 石兴 shixing@zba.com
 * @date 2011-7-8 下午01:38:32
 * @update 2011-7-8 下午01:38:32
 * @version V1.0
 */
public class RightsRemoteTest {
	
	//private final static String URL ="http://10.245.130.209/rightsApplyAO.remote";  //杜成机器Hessian服务URL
	private final static String URL ="http://www.pinju.com/rightsApplyAO.remote"; //本机Hessian服务 
	
	private final static HessianProxyFactory factory = new HessianProxyFactory();
	
	/**
	 * Created on 2011-7-8 
	 * <p>Discription:[查询维权记录]</p>
	 * @param 
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Test
	public final void testGetRightsDO()throws Exception
	{
		RightsDO rightsDO = new RightsDO();
		rightsDO.setId(34L);
		RightsAO rightsAO = (RightsAO) factory.create(RightsAO.class, URL);
		Result result = rightsAO.getRightsDO(rightsDO);
		RightsDO resultDO = (RightsDO)result.getModel("rightsDO");
		Assert.assertNotNull(resultDO);
		Assert.assertTrue(result.isSuccess());
		System.out.println(resultDO.getApplyDate());
		System.out.println(resultDO.getBuyerId());
		System.out.println(resultDO.getBuyerNick());
		System.out.println(resultDO.getSellerId());
		System.out.println(resultDO.getSellerNick());
		System.out.println(resultDO.getBuyerApplyPrice());
		System.out.println(resultDO.getOrderId());
		System.out.println(resultDO.getSubOrderId());
		System.out.println(resultDO.getPrice());
		System.out.println(resultDO.getVoucherPic1());
		System.out.println(resultDO.getVoucherPic2());
		System.out.println(resultDO.getVoucherPic3());
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[买家已回寄商品,卖家收货超时,置维权成功状态]</p>
	 * @param 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Test
	public final void testSellerTakeDeliveryOvertime()throws Exception
	{
		RightsAO rightsAO = (RightsAO) factory.create(RightsAO.class, URL);
		//超时接口迁移到CRM中
		/*Result result = rightsAO.sellerTakeDeliveryOvertime(34L);
		Assert.assertTrue(result.isSuccess());*/
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[卖家拒绝维权申请,买家处理超时,置维权关闭状态]</p>
	 * @param 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Test
	public final void testBuyerProcessOvertime()throws Exception
	{
		RightsAO rightsAO = (RightsAO) factory.create(RightsAO.class, URL);
		//超时接口迁移到CRM中
		//Result result = rightsAO.buyerProcessOvertime(34L);
		//Assert.assertTrue(result.isSuccess());
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[卖家同意申请,要求买家发货,买家回寄商品超时,置维权关闭状态]</p>
	 * @param 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Test
	public final void testBuyerDeliveryOvertime()throws Exception
	{
		RightsAO rightsAO = (RightsAO) factory.create(RightsAO.class, URL);
		//超时接口迁移到CRM中
		//Result result = rightsAO.buyerDeliveryOvertime(34L);
		//Assert.assertTrue(result.isSuccess());
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[客服处理超时,置维权关闭]</p>
	 * @param 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Test
	public final void testCustomServiceOvertime()throws Exception
	{
		RightsAO rightsAO = (RightsAO) factory.create(RightsAO.class, URL);
		//超时接口迁移到CRM中
		//Result result = rightsAO.customServiceOvertime(34L);
		//Assert.assertTrue(result.isSuccess());
	}
	
	/**
	 * Created on 2011-7-4 
	 * <p>Discription:[买家发起请求,卖家处理超时(1.自动达成协议(不需回寄商品)，2.自动维权成功)]</p>
	 * @param 维权记录ID
	 * @return
	 * @author:[石兴]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Test
	public final void testSellerProcessOvertime() throws Exception
	{
		RightsAO rightsAO = (RightsAO) factory.create(RightsAO.class, URL);
		//超时接口迁移到CRM中
		//Result result = rightsAO.sellerProcessOvertime(34L);
		//Assert.assertTrue(result.isSuccess());
	}
}
