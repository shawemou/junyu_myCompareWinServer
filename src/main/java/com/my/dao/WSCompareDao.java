package com.my.dao;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

import com.my.bean.VisitInfoBean;
import com.my.common.ReturnBean;
import com.my.dao.base.CommonDao;
import com.my.util.Base64ImgUtil;
import com.my.util.Check;
import com.my.util.GUID;
import com.my.util.Log4jUtil;

/**
 * @ClassName: WSCompareDao 
 * @Description: �ȶԽ�����
 * @author lulinlin
 *
 */
public class WSCompareDao extends CommonDao {
	/**
	 * �����û�
	 * @param viBean
	 * @param bean
	 * @return
	 */
	public List<Map<String, Object>> findUser(VisitInfoBean viBean, ReturnBean bean){
		String sql = "SELECT GUID,BUSABLE FROM T_USER T WHERE T.LOGIN_NAME = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(viBean.getCompareBean().getLoginName());
		return queryReturnListMap(sql,params);
	}
	/**
	 * �ȶԽ�����?
	 * @param viBean
	 * @param bean
	 * @param ip
	 * @return
	 */
	public boolean saveCompare(VisitInfoBean viBean, ReturnBean bean){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int ret = 0;
		boolean defaultCommit = true;
		
		try{
			String preparedSql = "INSERT INTO T_COMPARE (GUID,USER_GUID,NAME,CERTID,SEX,BIRTHDAY,FORK,ADDRESS,ISSUE_AUTHORITY,VALID_PRIOD,"
				+ "RETURN_CODE,RETURN_INFO,CODE1,SIMILARITY1,CODE2,SIMILARITY2,CODE3,SIMILARITY3,SOURCE_CODE,SOURCE_INFO,IP,HOLD_TIME) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			
			conn = getConnection();
			defaultCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(preparedSql);
			
			pstmt.setObject(1,viBean.getGuid());
			pstmt.setObject(2,viBean.getUserMap() != null ? viBean.getUserMap().get("GUID"): "" );
			pstmt.setObject(3,viBean.getCompareBean().getName());
			pstmt.setObject(4,viBean.getCompareBean().getCertid());
			pstmt.setObject(5,viBean.getCompareBean().getSex());
			pstmt.setObject(6,viBean.getCompareBean().getBirthday());
			pstmt.setObject(7,viBean.getCompareBean().getFork());
			pstmt.setObject(8,viBean.getCompareBean().getAddress());
			pstmt.setObject(9,viBean.getCompareBean().getIssue_authority());
			pstmt.setObject(10,viBean.getCompareBean().getVaild_priod());
			
			pstmt.setObject(11,bean.getCode());
			pstmt.setObject(12,bean.getInfo());
			pstmt.setObject(13,bean.getCrBean1().getCode());
			pstmt.setObject(14,bean.getCrBean1().getSimilarity());
			pstmt.setObject(15,bean.getCrBean2().getCode());
			pstmt.setObject(16,bean.getCrBean2().getSimilarity());
			pstmt.setObject(17,bean.getCrBean3().getCode());
			pstmt.setObject(18,bean.getCrBean3().getSimilarity());
			pstmt.setObject(19,bean.getCrBean4().getCode());
			pstmt.setObject(20,bean.getCrBean4().getInfo());
			
			pstmt.setObject(21,viBean.getIp());
			pstmt.setObject(22, new Date().getTime() - viBean.getBeginDate().getTime());
			ret = pstmt.executeUpdate();
			
			String guid_history = new GUID().toString();
			String sql_photo = "INSERT INTO T_COMPARE_PHOTO (GUID,HISTORY_GUID,PHOTO_ID,PHOTO_USER,PHOTO_CHIP)"
				+ " VALUES (?,?,EMPTY_BLOB(),EMPTY_BLOB(),EMPTY_BLOB())";
			pstmt = conn.prepareStatement(sql_photo);
			pstmt.setObject(1, guid_history);
			pstmt.setObject(2, viBean.getGuid());
			ret = pstmt.executeUpdate();
			
			if( ret > 0 ){
				pstmt = conn.prepareStatement("SELECT PHOTO_ID,PHOTO_USER,PHOTO_CHIP FROM T_COMPARE_PHOTO WHERE GUID = ? FOR UPDATE");
				pstmt.setObject(1, guid_history);
				rs = pstmt.executeQuery();
				if (rs.next()){
					
					if( !Check.IsStringNULL(viBean.getCompareBean().getStrIDPhoto()) ){
						byte[] data = Base64ImgUtil.fromBase64(viBean.getCompareBean().getStrIDPhoto());
						BLOB blob = (BLOB) rs.getBlob("PHOTO_ID");       
				        OutputStream outStream = blob.getBinaryOutputStream();    
				        outStream.write(data, 0, data.length);
				        outStream.flush();       
				        outStream.close();
					}
					
					if( !Check.IsStringNULL(viBean.getCompareBean().getStrUserPhoto()) ){
						byte[] data = Base64ImgUtil.fromBase64(viBean.getCompareBean().getStrUserPhoto());
						BLOB blob = (BLOB) rs.getBlob("PHOTO_USER");       
				        OutputStream outStream = blob.getBinaryOutputStream();    
				        outStream.write(data, 0, data.length);
				        outStream.flush();       
				        outStream.close();
					}
					
					if( !Check.IsStringNULL(viBean.getCompareBean().getStrChipPhoto()) ){
						byte[] data = Base64ImgUtil.fromBase64(viBean.getCompareBean().getStrChipPhoto());
						BLOB blob = (BLOB) rs.getBlob("PHOTO_CHIP");       
				        OutputStream outStream = blob.getBinaryOutputStream();    
				        outStream.write(data, 0, data.length);
				        outStream.flush();       
				        outStream.close();
					}
				}
			}
			conn.commit();
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				Log4jUtil.log.error("���ݿⱣ��ȶԼ�¼ʱ�쳣���ع�ʧ��", e1);
			}
			Log4jUtil.log.error("���ݿⱣ��ȶԼ�¼ʱ�쳣", e);
			return false;
		}finally{
			if( conn != null ){
				try {
					conn.setAutoCommit(defaultCommit);
				} catch (SQLException e) {
					Log4jUtil.log.error("���ݿⱣ��ȶԼ�¼ʱ�쳣,�ָ������ύ�쳣", e);
				}
			}
			closeRes(rs,pstmt,conn);
		}
		return true;
	}
}
