package com.my.dao;

import java.util.ArrayList;
import java.util.List;

import com.my.bean.CompareReturnBean;
import com.my.bean.VisitInfoBean;
import com.my.dao.base.CommonDao;

/**
 * @ClassName: WSSourceCompareDao 
 * @Description: �����Դ������ؽ��
 *
 */
public class WSSourceCompareDao extends CommonDao {

	/**
	 * �ȶԽ�����?
	 * @param viBean ������Ϣ
	 * @param bean ��Դ����ȶԽ��
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
