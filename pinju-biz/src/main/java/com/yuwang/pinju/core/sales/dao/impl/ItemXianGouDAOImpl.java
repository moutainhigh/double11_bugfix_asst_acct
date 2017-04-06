package com.yuwang.pinju.core.sales.dao.impl;

import java.util.List;
import java.util.Random;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.sales.dao.ItemXianGouDAO;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouQuery;


public class ItemXianGouDAOImpl extends BaseDAO implements ItemXianGouDAO {
    
    private static Random random = new Random();
    
	@Override
	public ItemXianGouDO getItemXianGouDOById(Long id) throws DaoException {
		return (ItemXianGouDO)executeQueryForObject("ItemXianGou.getItemXianGouDOById",id);
	}

	@Override
	public ItemXianGouDO getItemXianGouDOByItemId(Long itemId) throws DaoException {
		return (ItemXianGouDO)executeQueryForObject("ItemXianGou.getItemXianGouDOByItemId",itemId);
	}

	@Override
	public void insertItemXianGouDO(ItemXianGouDO itemXianGouDO)
			throws DaoException {
		executeInsert("ItemXianGou.insertItemXianGouDO", itemXianGouDO);
		
	}

	@Override
	public boolean updateItemXianGouDO(ItemXianGouDO itemXianGouDO) throws DaoException {
		return executeUpdate("ItemXianGou.updateItemXianGouDO", itemXianGouDO)==1?true:false;
	}
	
    @SuppressWarnings("unchecked")
    @Override
    public List<ItemXianGouDO> getItemXianGouDOs(
            ItemXianGouQuery itemXianGouQuery) throws DaoException
    {
        // 查询Ids总数/5 得到总页面，随机取其中一页即可;
        int count = getItemXianGouDOsCount(itemXianGouQuery);
//        int pages = count / 5 + (count % 5 == 0 ? 0 : 1);
        int pages = count / 5 ;
        int curPage = random.nextInt(pages+1);
        if (pages>0)
        {
            while (curPage==0)
            {
                curPage = random.nextInt(pages+1);
            }            
        }
        itemXianGouQuery.setItemsPerPage(5);
        itemXianGouQuery.setPage(curPage);
        itemXianGouQuery.setItems(count);
        return (List<ItemXianGouDO>)executeQueryForList("ItemXianGou.getItemXianGouDOs",itemXianGouQuery);
    }

    @Override
    public Integer getItemXianGouDOsCount(ItemXianGouQuery itemXianGouQuery)
            throws DaoException
    {
        return (Integer)executeQueryForObject("ItemXianGou.getItemXianGouDOsCount",itemXianGouQuery);
    }
}
