package com.yuwang.pinju.web.freemarker.share;

import java.util.regex.Pattern;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.domain.member.SellerQualityDO;

/**
 * <p>品聚网用于编码解码的 freemarker 扩展</p>
 *
 * @author gaobaowen
 * 2011-6-22 上午09:54:41
 */
public class PinjuEncoder {

	private static PinjuEncoder instance = new PinjuEncoder();

	private final static Pattern MID_PATTERN = Pattern.compile("[1-9][0-9]{14}");

	private PinjuEncoder() {
	}

	public static PinjuEncoder getInstance() {
		return instance;
	}

	/**
	 * <p>将数字 MEMBER ID 编码成为 13 位字符</p>
	 *
	 * @param memberId
	 * @return
	 *
	 * @author gaobaowen
	 */
	public String encodeMemberId(long memberId) {
		if(memberId < 1) {
			return null;
		}
		return MemberIdPuzzle.encode(memberId);
	}

	public String fixedEncodeStrMid(String mid) {
		if (mid == null || !MID_PATTERN.matcher(mid).matches()) {
			return "INVALID";
		}
		return fixedEncodeMemberId(Long.parseLong(mid));
	}

	public String fixedEncodeMemberId(long memberId) {
		if (memberId < 1) {
			return null;
		}
		return MemberIdPuzzle.encode(memberId, 789L, 7);
	}

	public String fixedEncodeMemberId(SellerQualityDO sellerQuality) {
		if (sellerQuality == null) {
			return null;
		}
		return fixedEncodeMemberId(sellerQuality.getMemberId());
	}
}
