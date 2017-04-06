package com.yuwang.pinju.core.member.manager.sequence;

/**
 * <p>产生唯一序号</p>
 * 
 * <p>该接口所有的实现所产生的序列号必须是全局且在分布式环境中唯一，且是线程安全的</p>
 *
 * @author gaobaowen
 * 2011-6-8 下午12:03:37
 */
public interface SequenceGenerator {

	/**
	 * <p>根据序号名称获取下一个序列号</p>
	 *
	 * @param name  序号名称
	 * @return
	 *
	 * @author gaobaowen
	 */
	public long next(String name);
}
