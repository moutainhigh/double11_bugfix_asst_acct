package com.yuwang.pinju.domain.member.login;

import java.io.Serializable;
import java.util.List;

public class LoginPageImgVO implements Serializable {

	private static final long serialVersionUID = -4734119583026971283L;

	/**
	 * 登录页面默认图片数据
	 */
	public final static LoginPageImgVO DEFAULT_LOGIN_PAGE_IMG = new LoginPageImgVO(
		new MemberLoginPageImgDO[] {
			new MemberLoginPageImgDO(1, 1, "http://static.pinju.com/images/login_adpic/login_01.jpg?t=20111203.jpg", "http://www.pinju.com/detail/14094.htm", "香港OPPO包包 女包韩版2011新款正品时尚休闲包单肩斜挎", 1, "b4", "top:2px; left:2px;", 195, 95),
			new MemberLoginPageImgDO(2, 2, "http://static.pinju.com/images/login_adpic/login_02.jpg?t=20111203.jpg", "http://www.pinju.com/detail/23504.htm", "宝诚 老虎吊坠/项坠 925纯银项链 女短款 韩国时尚纯银饰", 1, "b4", "top:2px; left:202px;", 195, 95),
			new MemberLoginPageImgDO(3, 3, "http://static.pinju.com/images/login_adpic/login_03.jpg?t=20111203.jpg", "http://shop10207.pinju.com", "华路仕旗舰店", 1, "b1", "top:2px; left:402px;", 95, 95),
			new MemberLoginPageImgDO(4, 4, "http://static.pinju.com/images/login_adpic/login_04.jpg?t=20111203.jpg", "http://shop11301.pinju.com", "洛兹喜力派旗舰店", 1, "b1", "top:102px; left:2px;", 95, 95),
			new MemberLoginPageImgDO(5, 5, "http://static.pinju.com/images/login_adpic/login_05.jpg?t=20111203.jpg", "http://shop12751.pinju.com", "jossing旗舰店", 1, "b1", "top:102px; left:102px;", 95, 95),
			new MemberLoginPageImgDO(6, 6, "http://static.pinju.com/images/login_adpic/login_06.jpg?t=20111203.jpg", "http://www.pinju.com/detail/23987.htm", "包邮 2011新款可爱平底鞋 针织毛钱平跟短靴", 1, "b1", "top:102px; left:402px;", 95, 95),
			new MemberLoginPageImgDO(7, 7, "http://static.pinju.com/images/login_adpic/login_07.jpg?t=20111203.jpg", "http://www.pinju.com/detail/24565.htm", "【Mr.Smart】新款顶级商务男鞋黑色经典系带优越款", 1, "b1", "top:201px; left:2px;", 95, 95),
			new MemberLoginPageImgDO(8, 8, "http://static.pinju.com/images/login_adpic/login_08.jpg?t=20111203.jpg", "http://www.pinju.com/detail/20752.htm", "The Rose Shop 玫瑰 美白 补水 睡眠 免洗 保", 1, "b1", "top:201px; left:102px;", 95, 95),
			new MemberLoginPageImgDO(9, 9, "http://static.pinju.com/images/login_adpic/login_09.jpg?t=20111203.jpg", "http://www.pinju.com/detail/52348.htm", "荷兰本土 Nutrilon牛栏成长1+ 升级版 4段奶粉", 1, "b1", "top:202px; left:402px;", 95, 95),
			new MemberLoginPageImgDO(10, 10, "http://static.pinju.com/images/login_adpic/login_10.jpg?t=20111203.jpg", "http://shop11337.pinju.com", "Man Friday旗舰店", 1, "b2", "top:301px; left:2px;", 195, 195),
			new MemberLoginPageImgDO(11, 11, "http://static.pinju.com/images/login_adpic/login_11.gif?t=20111203.gif", null, "旗舰店", 1, "b3", "top:302px; left:202px;", 95, 195),
			new MemberLoginPageImgDO(12, 12, "http://static.pinju.com/images/login_adpic/login_12.gif?t=20111203.gif", null, "品牌店", 1, "b3", "top:302px; left:302px;", 95, 195),
			new MemberLoginPageImgDO(13, 13, "http://static.pinju.com/images/login_adpic/login_13.gif?t=20111203.gif", null, "普通店", 1, "b3", "top:302px; left:402px;", 95, 195)
		}
	);

	private MemberLoginPageImgDO[] images = new MemberLoginPageImgDO[13];

	public LoginPageImgVO(List<MemberLoginPageImgDO> imgs) {
		loadImages(imgs);
		repairImages();
	}

	public LoginPageImgVO(MemberLoginPageImgDO[] images) {
		this.images = images;
	}

	public MemberLoginPageImgDO getImg(int index) {
		if (index < 1 || index > images.length) {
			return new MemberLoginPageImgDO();
		}
		return images[index - 1];
	}

	private void loadImages(List<MemberLoginPageImgDO> imgs) {
		if (imgs == null || imgs.size() == 0) {
			return;
		}
		for (MemberLoginPageImgDO img : imgs) {
			int index = img.getPosition() - 1;
			if (index < 0 || index > images.length) {
				continue;
			}
			images[index] = img;
		}
	}

	private void repairImages() {
		for (int i = 0; i < images.length; i++) {			
			if (images[i] == null) {
				images[i] = DEFAULT_LOGIN_PAGE_IMG.images[i];
			}
		}
	}
}
