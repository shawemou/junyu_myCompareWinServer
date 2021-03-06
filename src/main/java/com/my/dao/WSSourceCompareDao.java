package com.my.dao;

import java.util.ArrayList;
import java.util.List;

import com.my.bean.CompareReturnBean;
import com.my.bean.VisitInfoBean;
import com.my.dao.base.CommonDao;

/**
 * @ClassName: WSSourceCompareDao 
 * @Description: 保存多源结果返回结果
 *
 */
public class WSSourceCompareDao extends CommonDao {

	/**
	 * 比对结果入库?
	 * @param viBean 基本信息
	 * @param bean 多源结果比对结果
	 * @return
	 */
	public boolean saveCompare(VisitInfoBean viBean, CompareReturnBean bean){
		String preparedSql = "UPDATE T_COMPARE SET SOURCE_CODE = ?,SOURCE_INFO = ? WHERE GUID = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(bean.getCode());
		params.add(bean.getInfo());
		params.add(viBean.getGuid());
		return modify(preparedSql, params) > 0;
	}
}
