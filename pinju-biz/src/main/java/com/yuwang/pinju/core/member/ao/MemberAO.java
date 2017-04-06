package com.yuwang.pinju.core.member.ao;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.domain.member.DsrStatDO;
import com.yuwang.pinju.domain.member.MemberAgreementDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;
import com.yuwang.pinju.domain.member.SellerQualityDO;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;

/**
 * <p>会员基本信息、会员个人资料、会员收货地址等信息管理 AO</p>
 *
 * @author gaobaowen
 * 2011-6-8 上午11:23:39
 */
public interface MemberAO {

	/**
	 * <p>会接受用户协议</p>
	 *
	 * @param agreement 会员协议数据
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数错误</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：会员编号不存在</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_ACCEPT_AGREEMENT RESULT_MEMBER_ACCEPT_AGREEMENT}：会员已经接受过用户协议，不需要重复接受</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，无其他包装数据返回</p>
	 *
	 * @author gaobaowen
	 * @since 2011-7-18 17:55:17
	 */
	Result acceptAgreement(MemberAgreementDO agreement);

	/**
	 * <p>会员登录管理。主要操作有：</p>
	 * <ul>
	 * 	<li>若会员在平台第一次登录，则创建该用户的基本信息资料</li>
	 * </ul>
	 *
	 * @param ticket	第三方认证回传的会员登录 ticket 数据
	 *
	 * @return  若 ticket 验证不通过将返回 null 值
	 *
	 * @author gaobaowen
	 */
	MemberDO login(MemberTicketDO ticket);

	/**
	 * <p>记录会员登录日志及安全信息登录日志</p>
	 *
	 * @param memberLoginHis
	 * @param
	 * @return  日志是否记录成功
	 *
	 * @author gaobaowen
	 */
	boolean logLogin(MemberLoginHisDO memberLoginHis);

	/**
	 * <p>设置盛大通行证会员的账号信息，以下情况时不做任何处理：</p>
	 *
	 * <ul>
	 * 	<li>会员不属于盛大通行证用户</li>
	 * 	<li>会员的 SNDA_PT_ACCOUNT 属性不为空（说明已经抓取过会员的账号信息，不再重复抓取）</li>
	 * </ul>
	 *
	 * @return 不属于盛大通行证用户、SNDA_PT_ACCOUNT 不为空、设置成功时返回 true；获取账号失败、设置账号信息错误时返回 false
	 *
	 * @author gaobaowen
	 * @since 2011-7-11 上午10:11:40
	 */
	boolean setMemberSndaAccount(MemberDO member);

	/**
	 * <p>会员登出。主要操作有：</p>
	 *
	 * <ul>
	 * 	<li>记录用户的登出信息</li>
	 * </ul>
	 *
	 * @param sessionId    用户会话 ID
	 * @param memberId     会员编号
	 * @param logoutTime   注销时间
	 * @return  处理是否成功
	 *
	 * @author gaobaowen
	 */
	boolean logout(String sessionId, long memberId, Date logoutTime);

	/**
	 * <p>根据会员编号（MID）查找会员的个人信息资料</p>
	 *
	 * @param memberId   会员编号（MID）
	 * @return
	 *
	 * @author gaobaowen
	 */
	MemberDO findMember(long memberId);

	/**
	 * <p>根据会员昵称查找会员基本信息</p>
	 *
	 * @param nickname
	 * @return  没有找到或者参数值为空时返回 null 值
	 *
	 * @author gaobaowen
	 */
	MemberDO findMemberByNickname(String nickname);

	/**
	 * <p>更新会员的个人资料。由于个人资料中的昵称、性别存放于会员基本信息中，
	 * 其他数据存放于会员个人资料中，因此更新会员个人资料时需要同时进行更新。</p>
	 *
	 * @param memberId   会员编号（MID）
	 * @return
	 *
	 * @author gaobaowen
	 */
	MemberInfoDO findMemberInfo(long memberId);

	/**
	 * <p>检查会员 email 是否可用，若该 email 未找到，或者属于当前会员时可以使用</p>
	 *
	 * @param email
	 * @param memberId 当前会员编号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：参数错误</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_EMAIL_HAS_EXIST RESULT_MEMBER_EMAIL_HAS_EXIST}：email 已经被其他会员使用</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，表示该 email 尚未被使用</p>
	 */
	Result checkEmail(String email, long memberId);

	/**
	 * <p>更新会员的个人资料。由于个人资料中的昵称、性别存放于会员基本信息中，
	 * 其他数据存放于会员个人资料中，因此更新会员个人资料时需要同时进行更新。</p>
	 *
	 * <p>若会员个人信息数据不存在时，则为该会员将建一条数据</p>
	 *
	 * @param member         会员基本信息
	 * @param memberInfo     会员个人信息
	 * @return	int[]：int[0] 会员基本信息更新的数据条数；int[1] 会员个人信息更新的数据条数（若为新增则为 1）
	 *
	 * @author gaobaowen
	 */
	int[] updateMemberInfo(MemberDO member, MemberInfoDO memberInfo);

	/**
	 * <p>根据会员编号（MID）查找该会员的收货地址信息。返回的数据按默认地址放在最前面，
	 * 非默认地址按收货地址的更新时间倒序进行排序。</p>
	 *
	 * @param memberId   会员编号（MEMBER ID）
	 * @return
	 *
	 * @author gaobaowen
	 */
	List<MemberDeliveryDO> findMemberDeliveries(long memberId);

	/**
	 * <p>更新会员的收货地址</p>
	 *
	 * <p>NOTE: 若更新的会员收货地址设为默认地址时，在更新时会先将该用户已有的收货地址全部更新为非默认，
	 * 然后再更新该条数据</p>
	 *
	 * @param memberDelivery   需要更新的会员地址数据
	 * @return    更新的记录数量（只有值为 1 时才是正确的）
	 *
	 * @author gaobaowen
	 */
	int updateMemberDelivery(MemberDeliveryDO memberDelivery);

	/**
	 * <p>保存会员的收货地址。保存会员资料时并不会检查会员已经拥有多少条地址数据了，若需要对收货地址进行数量限制，
	 * 需要在调用该方法之前调用 (@link MemberManager#countMemberDeliveries) 方法</p>
	 *
	 * @param memberDelivery     需要保存的会员收货地址信息
	 * @return
	 *
	 * @author gaobaowen
	 */
	MemberDeliveryDO saveMemberDelivery(MemberDeliveryDO memberDelivery);

	/**
	 * <p>根据会员编号（MID）统计会员目前有多少条收货地址数据</p>
	 *
	 * @param memberId   会员编号
	 * @return   该会员目前已经有多少条收货地址数据
	 *
	 * @author gaobaowen
	 */
	int countMemberDeliveries(long memberId);

	/**
	 * <p>根据会员编号（MID）删除该会员的一条收货地址数据。若删除的收货地址为默认地址时，
	 * 删除后该会员则不再有默认收货地址。</p>
	 *
	 * @param deliveryId    会员收货地址 ID
	 * @param memberId      会员编号 MEMBER ID
	 * @return   只有返回值为 1 时才表示删除成功了
	 *
	 * @author gaobaowen
	 */
	int removeMemberDelivery(long deliveryId, long memberId);

	/**
	 * <p>根据收货地址ID和会员编号（MID）将会员的一条收货地址置为默认地址。由于每一位会员只允许有一个默认地址，
	 * 因此在设置时会将该用户基本的默认地址置为非默认，然后再将该地址更改为默认地址</p>
	 *
	 * @param deliveryId    会员收货地址 ID
	 * @param memberId      会员编号 MEMBER ID
	 * @return   只有返回值为 1 时才表示设置成功了
	 *
	 * @author gaobaowen
	 */
	int setDefaultDelivery(long deliveryId, long memberId);

	/**
	 * <p>根据商家的会员编号获取商家的资质信息，用于正式环境中。详见 {@link #getMemberShopQuality(long, boolean)}</p>
	 *
	 * @param memberId 会员编号
	 * @return
	 *
	 * @author gaobaowen
	 * @since 2011-10-20 09:08:41
	 */
	Result getMemberShopQuality(long memberId);

	/**
	 * <p>根据商家的会员编号获取商家的资质信息</p>
	 *
	 * @param memberId 会员编号
	 * @param isCacheDebug 是否处于调试模式（若为非开发模式时忽略该值），调试模式时数据不经过缓存
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：会员编号不存在</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_SELLER_QUALITY_NOT_FOUND RESULT_MEMBER_SELLER_QUALITY_NOT_FOUND}：商家资质信息没有找到</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_SHOPINFO_SELLER_QUALITY KEY_MEMBER_SHOPINFO_SELLER_QUALITY}：商家资质信息，获取 {@link SellerQualityDO} 对象</li>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_SHOPINFO_DSR_STAT KEY_MEMBER_SHOPINFO_DSR_STAT}：商家 DSR 分数，获取 List&lt;{@link DsrStatDO}&gt; 对象</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 */
	Result getMemberShopQuality(long memberId, boolean isCacheDebug);

	/**
	 * <p>根据会员编号查询会员社区信息</p>
	 *
	 * @param memberId
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：无法得到会员信息，
	 *   可能是由于用户非法操作造成的，应提示用户重新登录</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_SNS_INFO KEY_MEMBER_SNS_INFO}：会员社区信息，获取 {@link MemberSnsInfoDO} 对象</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 16:19:51
	 */
	Result getMemberSnsInfoByMemberId(long memberId);

	/**
	 * <p>新增或者更新会员社区信息</p>
	 *
	 * @param memberSnsInfo
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：无法得到会员信息，
	 *   可能是由于用户非法操作造成的，应提示用户重新登录</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_SNS_INFO KEY_MEMBER_SNS_INFO}：新增的会员社区信息或者是更新该会员社区信息的数量。
	 *   若对象类型为 Number 时，则为更新数据，否则为新增数据。更新时转为 Number 类型后判断其值，若值等于 1 时表示更新成功。</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 16:49:39
	 */
	Result saveOrUpdateMemberSnsInfo(MemberSnsInfoDO memberSnsInfo);

	Result saveAvatars(File file, String filename, long memberId, String nickname);

	/**
	 * <p>根据会员编号查找已绑定的支付账户信息</p>
	 *
	 * @param memberId  会员编号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：无法得到会员信息，
	 *   可能是由于用户非法操作造成的，应提示用户重新登录</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_PAYMENT KEY_MEMBER_PAYMENT}：当前用户已绑定的支付账户信息，若值为 null
	 *   时表示当前会员没有绑定支付账户。</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-20 14:58:48
	 */
	Result getPaymentAccount(long memberId);

	/**
	 * <p>绑定支付账户</p>
	 *
	 * @param payment 支付账户信息
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：无法得到会员信息，
	 *   可能是由于用户非法操作造成的，应提示用户重新登录</li>
	 *   <li>{@link MemberResultConstant#RESULT_PAYMENT_REBOUND RESULT_PAYMENT_REBOUND}：当前会员已有绑定的支付账户，
	 *   不允许重复绑定</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_PAYMENT KEY_MEMBER_PAYMENT}：当前用户已绑定的支付账户信息</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-20 13:50:07
	 */
	Result boundPaymentAccount(MemberPaymentDO payment);

	/**
	 * <p>更新支付账号的绑定状态</p>
	 * @param memberPayment
	 * @return
	 */
	Result updateMemberPaymentByAccount(MemberPaymentDO memberPayment);

	/**
	 * <p>解绑支付账户</p>
	 *
	 * @param memberId 会员编号
	 * @return <p>若 {@link Result#isSuccess()} 结果为 false 时，需要处理的结果如下：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberResultConstant#RESULT_PARAMETERS_ERROR RESULT_PARAMETERS_ERROR}：请求参数值为 null</li>
	 *   <li>{@link MemberResultConstant#RESULT_PAYMENT_NOT_EXISTS RESULT_PAYMENT_NOT_EXISTS}：当前会员没有已绑定的支付账户，
	 *   可以直接导向至支付账户绑定页面</li>
	 *   <li>{@link MemberResultConstant#RESULT_UPDATE_COUNT_ERROR RESULT_UPDATE_COUNT_ERROR}：更新失败，可能没有更新到数据</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_MEMBER_NOT_EXIST RESULT_MEMBER_MEMBER_NOT_EXIST}：无法得到会员信息，
	 *   可能是由于用户非法操作造成的，应提示用户重新登录</li>
	 *   <li>{@link MemberResultConstant#RESULT_MEMBER_INNER_ERROR RESULT_MEMBER_INNER_ERROR}：内部错误</li>
	 * </ul>
	 *
	 * <p>若 {@link Result#isSuccess()} 结果为 true 时，使用 {@link Result#getModel(String)} 方法获取强转后使用：</p>
	 *
	 * <ul>
	 *   <li>{@link MemberKeyConstant#KEY_MEMBER_UPDATE_COUNT KEY_MEMBER_UPDATE_COUNT}：解绑的支付账户数量</li>
	 * </ul>
	 *
	 * @author gaobaowen
	 * @since 2011-7-20 13:54:04
	 */
	Result unboundPaymentAccount(long memberId);

	/**
	 * Created on 2011-9-13
	 * @desc <p>Discription:[签订完财付通代扣协议,更新签订状态为已签订]</p>
	 * @param
	 * @return Result
	 * @author:[石兴]
	 * @update:[2011-9-13] [更改人姓名]
	 */
	Result signPayAgreement(MemberPaymentDO memberPaymentDO);

	/**
	 * Created on 2011-9-13
	 * @desc <p>Discription:[判断用户是否签订协议,已签订则返回签订时间等]</p>
	 * @param
	 * @return Result
	 * @author:[石兴]
	 * @update:[2011-9-13] [更改人姓名]
	 */
	Result isSignPayAgreement(long memberId);

	/**
	 * Created on 2011-9-14
	 * @desc <p>Discription:[根据财付通账号查找已绑定财付通且未签订协议的会员]</p>
	 * @param
	 * @return MemberPaymentDO
	 * @author:[石兴]
	 * @update:[2011-9-14] [更改人姓名]
	 */
	MemberPaymentDO getPaymentDOByAccountNo(String accountNo);

	/**
	 * Created on 2011-9-23
	 * @desc <p>Discription:[委托退款关系查询接口]</p>
	 * @param payAccountNo 用户的财付通账号
	 * @return String
	 * @author:[石兴]
	 * @update:[2011-9-23] [更改人姓名]
	 */
	Result getInquireRelation(String payAccountNo);

	/**
	 * <p>查询该账号是否存在</p>
	 * param paymentQuery[accountNO, institution]
	 * @param paymentQuery
	 * @return
	 */
	Result findBoundMemberPaymented(MemberPaymentDO paymentQuery);

	/**
	 * <p>子账号登录处理</p>
	 *
	 * @param his  登录数据
	 * @return 更新的数据行数
	 *
	 * @author gaobaowen
	 * @since 2011-12-22 13:59:26
	 */
	boolean asstLogin(MemberLoginHisDO his);
}