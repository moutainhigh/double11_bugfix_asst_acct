package com.yuwang.pinju.core.util.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yuwang.pinju.core.item.ao.impl.ItemAOImpl;
import com.yuwang.pinju.core.util.EmptyUtil;

public class ItemUtil {

	static Logger log = Logger.getLogger(ItemAOImpl.class.getName());

	/**
	 * 获得商品属性hash表(属性,属性值编号列表)
	 * 
	 * @param property
	 * @return
	 * @throws Exception
	 */
	public static void getItemPropertyMap(String property, List valueIdList, Map selectValueIdMap) throws Exception {
		try {

			if (EmptyUtil.isBlank(property)) {
				return;
			}

			String attrs[] = property.split(";");
			for (String attr : attrs) {
				String attrId = attr.split(":")[0];
				String values[] = attr.split(":")[1].split(",");
				List<String> ids = null;

				if (selectValueIdMap.containsKey(attrId)) {
					ids = (List) selectValueIdMap.get(attrId);
				} else {
					ids = new ArrayList<String>();
				}

				for (String vid : values) {
					if (!ids.contains(vid)) {
						ids.add(vid);
					}
					if (!valueIdList.contains(Long.valueOf(vid))) {
						valueIdList.add(Long.valueOf(vid));
					}
				}

				selectValueIdMap.put(attrId, ids);
			}

		} catch (Exception e) {
			throw e;
		}
	}
}
