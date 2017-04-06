package com.yuwang.pinju.core.member.manager.sequence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 内存中存储序列的步长，以避免每一次请求序列时重新从序列发生源中去获取
 * </p>
 * 
 * @author gaobaowen
 * @since 2011-6-8 下午12:05:41
 */
public abstract class StepCacheSequenceGenerator implements SequenceGenerator {

	/**
	 * 序列缓存
	 */
	private Map<String, SequenceInfo> cache = new ConcurrentHashMap<String, SequenceInfo>();

	@Override
	public long next(String name) {
		// 从序号缓存中获取序号信息
		SequenceInfo sequenceInfo = cache.get(name);
		if (sequenceInfo == null) {
			synchronized (cache) {
				// 若缓存中序号不存在时，则重新获取序号
				sequenceInfo = retriveAndPut(name);
			}
		}
		synchronized (sequenceInfo) {
			if (!sequenceInfo.hasNext()) {
				// 如果缓存的序列不存在下一个时，则重新获取序号
				sequenceInfo = retriveAndPut(name);
			}
		}
		if (sequenceInfo == null) {
			return -1;
		}
		return sequenceInfo.next();
	}

	/**
	 * <p>
	 * 查询下一批序号信息并将其置入到序列缓存中
	 * </p>
	 * 
	 * @param name
	 * @return
	 * 
	 * @author gaobaowen
	 */
	private SequenceInfo retriveAndPut(String name) {
		SequenceInfo sequenceInfo = retriveSequenceInfo(name);
		cache.put(sequenceInfo.getName(), sequenceInfo);
		return sequenceInfo;
	}

	/**
	 * <p>
	 * 获取下一批序列信息
	 * </p>
	 * 
	 * @param name
	 * @return
	 * 
	 * @author gaobaowen
	 */
	protected abstract SequenceInfo retriveSequenceInfo(String name);

	/**
	 * <p>
	 * 序列信息
	 * </p>
	 * 
	 * @author gaobaowen 2011-6-8 下午12:22:11
	 */
	protected static class SequenceInfo {

		/**
		 * 序列名
		 */
		private String name;

		/**
		 * 序列当前值
		 */
		private AtomicLong current;

		/**
		 * 该批序列的最大值
		 */
		private long currentMax;

		/**
		 * <p>
		 * 序列信息构造
		 * </p>
		 * 
		 * @param name           序列名
		 * @param current        序列当前值
		 * @param sequenceStep   序列缓存步长
		 */
		public SequenceInfo(String name, long current, int sequenceStep) {
			this.name = name;
			this.current = new AtomicLong(current);
			this.currentMax = current + sequenceStep;
		}

		public String getName() {
			return name;
		}

		private boolean hasNext() {
			return (current.get() < currentMax);
		}

		private long next() {
			return current.getAndIncrement();
		}

		@Override
		public String toString() {
			return "SequenceInfo [name=" + name + ", current=" + current + ", currentMax=" + currentMax + "]";
		}
	}
}