package com.yuwang.pinju.core.trade.manager.impl;


import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.manager.sequence.StepCacheSequenceGenerator;
import com.yuwang.pinju.core.trade.dao.PaySequenceDAO;

public class PaySequenceGenerator extends StepCacheSequenceGenerator {

	private PaySequenceDAO paySequenceDAO;
	
	private final static int cacheMax = 50;
	
	public final static String seqName = "SEQ_PAY_REFUND_ID";

	@Override
	protected SequenceInfo retriveSequenceInfo(String name) {
		Long current = new Long(0);
		try {
			current = paySequenceDAO.getSequenceNextByName(name);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		SequenceInfo seqInfo = new SequenceInfo(name, current*cacheMax, cacheMax);
		return seqInfo;
	}
	
	public long next(String name,int size){
		return (super.next(name) & Long.MAX_VALUE) % getNum(size);
	}
	
	private long getNum(int size){
		long num = 0;
		switch (size){
			case 2: 
				num = 100;
				break;
			case 3: 
				num = 1000;
				break;
			case 4: 
				num = 10000;
				break;
			case 5: 
				num = 100000;
				break;
			case 6: 
				num = 1000000;
				break;
			case 7: 
				num = 10000000;
				break;
			case 8: 
				num = 100000000;
				break;
			case 9: 
				num = 1000000000;
				break;
			case 10: 
				num = 10000000000L;
				break;
			default:
				num = 10;
		}
		return num;
	}

	public void setPaySequenceDAO(PaySequenceDAO paySequenceDAO) {
		this.paySequenceDAO = paySequenceDAO;
	}
	
}
