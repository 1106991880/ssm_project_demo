/**
 * 
 */
package cn.springmvc.utildao;

/**
 * MyBatisʵ�������ҳ
 * 
 * @author dongdong
 * 
 */
public class MySQLDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if (offset >= 0) {
			pagingSelect.append(sql).append(" limit ").append(offset).append(", ").append(limit);
		} else {
			pagingSelect.append(sql).append(" limit ").append(0).append(", ").append(limit);
		}
		// StringBuffer finalSQL = new StringBuffer("SELECT (@ROWNUM := @ROWNUM + 1) AS ROW_NUM, t.* FROM (");
		// finalSQL.append(pagingSelect.toString());
		// finalSQL.append(") t, (SELECT (@rownum := 0)) N ORDER BY ROW_NUM");
		// return finalSQL.toString();
		return pagingSelect.toString();
	}

}
