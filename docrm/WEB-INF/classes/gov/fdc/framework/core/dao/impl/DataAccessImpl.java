package gov.fdc.framework.core.dao.impl;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.fdc.framework.core.dao.DBResultSet;
import gov.fdc.framework.core.dao.DaQueryResule;
import gov.fdc.framework.core.dao.DataAccess;
import gov.fdc.framework.core.dao.Query;
import gov.fdc.framework.core.dao.ResultSetCallBack;
import gov.fdc.library.exception.LTSDataAccessException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedCaseInsensitiveMap;








@Component("jdbcDataAccess")
@Scope("prototype")
public class DataAccessImpl
  implements DataAccess
{
  final Logger log = LoggerFactory.getLogger("LtsDataAccess");
  




  private JdbcTemplate jdbcTemplate;
  




  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  



  private DataSource dataSource;
  




  public String toString()
  {
    if (this.dataSource == null) {
      return getClass() + "(" + hashCode() + "){dataSource:[null]}";
    }
    
    return getClass() + "(" + hashCode() + "){dataSource:[" + this.dataSource + "]}";
  }
  





  public DataAccessImpl() {}
  




  public DataAccessImpl(DataSource dataSource)
  {
    this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
  }
  


  public void setDataSource(DataSource dataSource)
    throws LTSDataAccessException
  {
    this.dataSource = dataSource;
    
    if (this.jdbcTemplate == null) {
      this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    if (this.namedParameterJdbcTemplate == null) {
      this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
    }
  }
  
  public int insert(String sql)
    throws LTSDataAccessException
  {
    return this.jdbcTemplate.update(sql);
  }
  
  public int update(String sql) throws LTSDataAccessException
  {
    return this.jdbcTemplate.update(sql);
  }
  
  public int delete(String sql) throws LTSDataAccessException
  {
    return this.jdbcTemplate.update(sql);
  }
  
  public int insert(String sql, Object[] params)
    throws LTSDataAccessException
  {
    return this.jdbcTemplate.update(sql, params);
  }
  
  public int update(String sql, Object[] params)
    throws LTSDataAccessException
  {
    return this.jdbcTemplate.update(sql, params);
  }
  
  public int delete(String sql, Object[] params)
    throws LTSDataAccessException
  {
    return this.jdbcTemplate.update(sql, params);
  }
  
  public int insert(String sql, Map<String, Object> params)
    throws LTSDataAccessException
  {
    return this.namedParameterJdbcTemplate.update(sql, params);
  }
  
  public int update(String sql, Map<String, Object> params)
    throws LTSDataAccessException
  {
    return this.namedParameterJdbcTemplate.update(sql, params);
  }
  
  public int delete(String sql, Map<String, Object> params)
    throws LTSDataAccessException
  {
    return this.namedParameterJdbcTemplate.update(sql, params);
  }
  
  public int[] insert(String sql, final List<Object[]> params)
    throws LTSDataAccessException
  {
    try
    {
      this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
      {
        public void setValues(PreparedStatement ps, int i)
          throws SQLException
        {
          for (int j = 0; j < ((Object[])params.get(i)).length; j++) {
            ps.setObject(j + 1, ((Object[])params.get(i))[j]);
          }
        }
        
        public int getBatchSize() {
          return params.size();
        }
        
      });
    }
    catch (Exception e)
    {
      this.log.error(foramtForBatchLog(sql, params), e);
      throw new LTSDataAccessException(e);
    }
	return null;
  }
  
  public int[] update(String sql, final List<Object[]> params)
    throws LTSDataAccessException
  {
    try
    {
      this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
      {
        public void setValues(PreparedStatement ps, int i)
          throws SQLException
        {
          for (int j = 0; j < ((Object[])params.get(i)).length; j++) {
            ps.setObject(j + 1, ((Object[])params.get(i))[j]);
          }
        }
        
        public int getBatchSize() {
          return params.size();
        }
        
      });
    }
    catch (Exception e)
    {
      this.log.error(foramtForBatchLog(sql, params), e);
      throw new LTSDataAccessException(e);
    }
	return null;
  }
  
  public int[] delete(String sql, final List<Object[]> params) throws LTSDataAccessException
  {
    try
    {
      this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
      {
        public void setValues(PreparedStatement ps, int i)
          throws SQLException
        {
          for (int j = 0; j < ((Object[])params.get(i)).length; j++) {
            ps.setObject(j + 1, ((Object[])params.get(i))[j]);
          }
        }
        
        public int getBatchSize() {
          return params.size();
        }
        
      });
    }
    catch (Exception e)
    {
      this.log.error(foramtForBatchLog(sql, params), e);
      throw new LTSDataAccessException(e);
    }
	return null;
  }
  
  public int[] insert(String[] sql) throws LTSDataAccessException
  {
    return this.jdbcTemplate.batchUpdate(sql);
  }
  
  public int[] update(String[] sql) throws LTSDataAccessException
  {
    return this.jdbcTemplate.batchUpdate(sql);
  }
  
  public int[] delete(String[] sql) throws LTSDataAccessException
  {
    return this.jdbcTemplate.batchUpdate(sql);
  }
  
  public DaQueryResule query(String sql, Object[] params, int pageSize, int pageNum)
    throws LTSDataAccessException
  {
    try
    {
      List<?> dataList = null;
      int totalCount = 0;
      

      if (pageNum > 0)
      {
        dataList = (List)this.jdbcTemplate.query(getOraclePagingWarpper(sql, pageSize, pageNum), params, new ListResultSetExtractor());

        totalCount = this.jdbcTemplate.queryForInt(getOracleRowCountWarpper(sql), params);
      }
      else
      {
        dataList = (List)this.jdbcTemplate.query(sql, params, new ListResultSetExtractor(null));
        totalCount = dataList.size();
      }
      DaQueryResule result = new DaQueryResule()
      {
        private List<?> dataList;
        private int totalCount;
        
        public List<?> getDataList() {
          return this.dataList;
        }
        
        public int getTotalCount()
        {
          return this.totalCount;
        }
        
        public DaQueryResule setDataList(List<?> dataList)
        {
          this.dataList = dataList;
          return this;
        }
        
        public DaQueryResule setTotalCount(int totalCount)
        {
          this.totalCount = totalCount;
          return this;
        }
        
      };
      result.setDataList(dataList);
      result.setTotalCount(totalCount);
      return result;
    }
    catch (Throwable t) {
      throw new LTSDataAccessException(t);
    }
  }
  

  public DaQueryResule query(String sql, Map<String, Object> params, int pageSize, int pageNum)
    throws LTSDataAccessException
  {
    try
    {
      List<?> dataList = null;
      int totalCount = 0;
      

      if (pageNum > 0) {
        dataList = (List)this.namedParameterJdbcTemplate.query(getOraclePagingWarpper(sql, pageSize, pageNum), params, new ListResultSetExtractor(null));
        
        totalCount = this.namedParameterJdbcTemplate.queryForInt(getOracleRowCountWarpper(sql), params);

      }
      else
      {
        dataList = (List)this.namedParameterJdbcTemplate.query(sql, params, new ListResultSetExtractor(null));
        totalCount = dataList.size();
      }
      
      DaQueryResule result = new DaQueryResule()
      {
        private List<?> dataList;
        private int totalCount;
        
        public List<?> getDataList() {
          return this.dataList;
        }
        
        public int getTotalCount()
        {
          return this.totalCount;
        }
        
        public DaQueryResule setDataList(List<?> dataList)
        {
          this.dataList = dataList;
          return this;
        }
        
        public DaQueryResule setTotalCount(int totalCount)
        {
          this.totalCount = totalCount;
          return this;
        }
        
      };
      result.setDataList(dataList);
      result.setTotalCount(totalCount);
      
      return result;
    }
    catch (Throwable t) {
      throw new LTSDataAccessException(t);
    }
  }
  
  public DaQueryResule query(String sql, int pageSize, int pageNum) throws LTSDataAccessException
  {
    try
    {
      List<?> dataList = null;
      int totalCount = 0;
      

      if (pageNum > 0)
      {
        dataList = (List)this.jdbcTemplate.query(getOraclePagingWarpper(sql, pageSize, pageNum), new ListResultSetExtractor(null));
        
        totalCount = this.jdbcTemplate.queryForInt(getOracleRowCountWarpper(sql));

      }
      else
      {
        dataList = (List)this.jdbcTemplate.query(sql, new ListResultSetExtractor(null));
        totalCount = dataList.size();
      }
      
      DaQueryResule result = new DaQueryResule()
      {
        private List<?> dataList;
        private int totalCount;
        
        public List<?> getDataList() {
          return this.dataList;
        }
        
        public int getTotalCount()
        {
          return this.totalCount;
        }
        
        public DaQueryResule setDataList(List<?> dataList)
        {
          this.dataList = dataList;
          return this;
        }
        
        public DaQueryResule setTotalCount(int totalCount)
        {
          this.totalCount = totalCount;
          return this;
        }
        
      };
      result.setDataList(dataList);
      result.setTotalCount(totalCount);
      
      return result;
    }
    catch (Throwable t) {
      throw new LTSDataAccessException(t);
    }
  }
  
  public DBResultSet executeQuery(String sql, Object[] params, boolean totalCountFlag)
    throws LTSDataAccessException
  {
    return executeQuery(sql, params, totalCountFlag, 1003, 1007);
  }
  







  public DBResultSet executeQuery(String sql, Object[] params, boolean totalCountFlag, int resultSetType, int resultSetConcurrenc)
    throws LTSDataAccessException
  {
    PreparedStatement pstmt = null;
    try
    {
      int totalCount = -1;
      
      if (totalCountFlag) {
        totalCount = this.jdbcTemplate.queryForInt(getOracleRowCountWarpper(sql), params);
      }
      

      Connection conn = getConnection();
      
      pstmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrenc);
      


      setPreparedStatementParamater(1, pstmt, params);
      
      return DBResultSet.createDBResultSet(pstmt.executeQuery(), totalCount, this);
    } catch (Exception e) {
      if (pstmt != null) {
        try {
          pstmt.close();
        } catch (SQLException e1) {
          throw new LTSDataAccessException(e1);
        }
      }
      throw new LTSDataAccessException(e);
    }
  }
  
  public DBResultSet executeQuery(String sql, Map<String, Object> params, boolean totalCountFlag)
    throws LTSDataAccessException
  {
    return executeQuery(sql, params, totalCountFlag, 1003, 1007);
  }
  








  public DBResultSet executeQuery(String sql, Map<String, Object> params, boolean totalCountFlag, int resultSetType, int resultSetConcurrenc)
    throws LTSDataAccessException
  {
    List<Object> objList = new ArrayList();
    
    String[] textArr = sql.split(" ");
    
    for (String str : textArr) {
      if (str.indexOf(":") >= 0) {
        String val = str.substring(str.indexOf(":") + 1, str.length());
        val = val == null ? null : val.trim();
        if (val != null) {
          val = val.trim();
          objList.add(params.get(val));
        }
      }
    }
    
    return executeQuery(sql, objList.toArray(), totalCountFlag, resultSetType, resultSetConcurrenc);
  }
  
  public DBResultSet executeQuery(String sql, boolean totalCountFlag) throws LTSDataAccessException
  {
    return executeQuery(sql, totalCountFlag, 1003, 1007);
  }
  






  public DBResultSet executeQuery(String sql, boolean totalCountFlag, int resultSetType, int resultSetConcurrenc)
    throws LTSDataAccessException
  {
    Statement stmt = null;
    try
    {
      int totalCount = -1;
      
      if (totalCountFlag) {
        totalCount = this.jdbcTemplate.queryForInt(getOracleRowCountWarpper(sql));
      }
      

      Connection conn = getConnection();
      
      stmt = conn.createStatement(resultSetType, resultSetConcurrenc);
      


      return DBResultSet.createDBResultSet(stmt.executeQuery(sql), totalCount, this);
    } catch (Exception e) {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e1) {
          throw new LTSDataAccessException(e1);
        }
      }
      throw new LTSDataAccessException(e);
    }
  }
  

  public void queryForCallBack(String sql, Object[] params, final ResultSetCallBack resultSetCallBack)
    throws LTSDataAccessException
  {
    try
    {
      this.jdbcTemplate.query(sql, params, new RowCallbackHandler() {
        public void processRow(ResultSet resultSet) throws SQLException {
          resultSetCallBack.callBack(resultSet);
        }
      });
    } catch (Exception e) {
      throw new LTSDataAccessException(e);
    }
  }
  

  public void queryForCallBack(String sql, Map<String, Object> params, final ResultSetCallBack resultSetCallBack)
    throws LTSDataAccessException
  {
    try
    {
      this.namedParameterJdbcTemplate.query(sql, params, new RowCallbackHandler()
      {
        public void processRow(ResultSet resultSet) throws SQLException
        {
          resultSetCallBack.callBack(resultSet);
        }
      });
    } catch (Exception e) {
      throw new LTSDataAccessException(e);
    }
  }
  

  public void queryForCallBack(String sql, final ResultSetCallBack resultSetCallBack)
    throws LTSDataAccessException
  {
    try
    {
      this.jdbcTemplate.query(sql, new RowCallbackHandler() {
        public void processRow(ResultSet resultSet) throws SQLException {
          resultSetCallBack.callBack(resultSet);
        }
      });
    } catch (Exception e) {
      throw new LTSDataAccessException(e);
    }
  }
  
  public Map<?, ?> queryForMap(String sql, Object[] params)
    throws LTSDataAccessException
  {
    return (Map)this.jdbcTemplate.query(sql, params, new MapResultSetExtractor(null));
  }
  
  public Map<?, ?> queryForMap(String sql, Map<String, Object> params)
    throws LTSDataAccessException
  {
    return (Map)this.namedParameterJdbcTemplate.query(sql, params, new MapResultSetExtractor(null));
  }
  
  public Map<?, ?> queryForMap(String sql) throws LTSDataAccessException
  {
    return (Map)this.jdbcTemplate.query(sql, new MapResultSetExtractor(null));
  }
  
  public List<?> queryForList(String sql, Object[] params)
    throws LTSDataAccessException
  {
    return (List)this.jdbcTemplate.query(sql, params, new ListResultSetExtractor(null));
  }
  
  public List<?> queryForList(String sql, Map<String, Object> params)
    throws LTSDataAccessException
  {
    return (List)this.namedParameterJdbcTemplate.query(sql, params, new ListResultSetExtractor(null));
  }
  
  public List<?> queryForList(String sql) throws LTSDataAccessException
  {
    return (List)this.jdbcTemplate.query(sql, new ListResultSetExtractor(null));
  }
  


  private Connection getConnection()
  {
    return DataSourceUtils.getConnection(this.dataSource);
  }
  


  public void releaseConnection(Connection conn)
  {
    DataSourceUtils.releaseConnection(conn, this.dataSource);
  }
  
  public int executeUpdate(String sql) throws LTSDataAccessException
  {
    return executeUpdate(new String[] { sql })[0];
  }
  
  public int[] executeUpdate(String[] sql)
    throws LTSDataAccessException
  {
    Statement stmt = null;
    
    try
    {
      Connection conn = getConnection();
      stmt = conn.createStatement();
      
      for (int i = 0; i < sql.length; i++) {
        stmt.addBatch(sql[i]);
      }
      int[] rtn = stmt.executeBatch();
      return rtn;
    }
    catch (Exception e) {
      throw new LTSDataAccessException(e);
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e1) {
          throw new LTSDataAccessException(e1);
        }
      }
    }
  }
  


  public int executeUpdate(String sql, Object[] params)
    throws LTSDataAccessException
  {
    PreparedStatement pstmt = null;
    try
    {
      Connection conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      setPreparedStatementParamater(1, pstmt, params);
      
      int rtn = pstmt.executeUpdate();
      return rtn;
    }
    catch (Exception e) {
      throw new LTSDataAccessException(e);
    } finally {
      if (pstmt != null) {
        try {
          pstmt.close();
        } catch (SQLException e1) {
          throw new LTSDataAccessException(e1);
        }
      }
    }
  }
  


  public int[] executeUpdate(String sql, final List<Object[]> params)
    throws LTSDataAccessException
  {
    try
    {
      this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter()
      {
        public void setValues(PreparedStatement ps, int i)
          throws SQLException
        {
          for (int j = 0; j < ((Object[])params.get(i)).length; j++) {
            ps.setObject(j + 1, ((Object[])params.get(i))[j]);
          }
        }
        
        public int getBatchSize() {
          return params.size();
        }
      });
    }
    catch (Exception e) {
      this.log.error(foramtForBatchLog(sql, params), e);
      throw new LTSDataAccessException(e);
    }
	return null;
  }
  





  private void setPreparedStatementParamater(int startIndex, PreparedStatement pstmt, Object[] objs)
    throws SQLException
  {
    for (Object obj : objs) {
      if (obj == null) {
        pstmt.setNull(startIndex++, 0);
      } else if ((obj instanceof String)) {
        pstmt.setString(startIndex++, (String)obj);
      } else {
        pstmt.setObject(startIndex++, obj);
      }
    }
  }
  
  public int[] insert(String sql, Map<String, ?>[] params)
    throws LTSDataAccessException
  {
    return this.namedParameterJdbcTemplate.batchUpdate(sql, params);
  }
  
  public int[] update(String sql, Map<String, ?>[] params)
    throws LTSDataAccessException
  {
    return this.namedParameterJdbcTemplate.batchUpdate(sql, params);
  }
  
  public int[] delete(String sql, Map<String, ?>[] params)
    throws LTSDataAccessException
  {
    return this.namedParameterJdbcTemplate.batchUpdate(sql, params);
  }
  



  private String getObj2String(Object obj)
  {
    if ((obj instanceof Object[])) {
      return getObj2String((Object[])obj);
    }
    return obj.toString().replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", " ");
  }
  









  private String getObj2String(Object[] objs)
  {
    return Arrays.asList(objs).toString();
  }
  




  protected String getOraclePagingWarpper(Query q)
  {
    return getOraclePagingWarpper(q.getSql(), q.getPageNo(), q.getPageSize());
  }
  




  protected String getOracleRowCountWarpper(Query q)
  {
    return getOracleRowCountWarpper(q.getSql());
  }
  

  protected String getOraclePagingWarpper_1(String sql, int pageSize, int pageNum)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(sql);
    return sb.toString();
  }
  
  protected String getOraclePagingWarpper(String sql, int pageSize, int pageNum)
  {
    StringBuffer sb = new StringBuffer();
    //sb.append(" SELECT * FROM ");
    
    //sb.append(" ( ");
    //sb.append(sql);
    //sb.append(" ) a ");

    //sb.append(" WHERE a.rowid  between ").append((pageNum - 1) * pageSize + 1);
    //sb.append(" and ").append(pageNum * pageSize + 1);
    
    sb.append(sql);
    sb.append(" Limit ");
    sb.append((pageNum - 1) * pageSize +" , " + pageSize);
        
    return sb.toString();
  }
  

  protected String getOraclePagingWarpper_old(String sql, int pageSize, int pageNum)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT * FROM ");
    sb.append(" ( ");
    sb.append(" SELECT a.*, rownum r__ ");
    sb.append(" FROM ");
    sb.append(" ( ");
    sb.append(sql);
    sb.append(" ) a ");
    sb.append(" WHERE rownum < ").append(pageNum * pageSize + 1);
    sb.append(" ) ");
    sb.append(" WHERE r__ >= ").append((pageNum - 1) * pageSize + 1);
    return sb.toString();
  }
  

  protected String getOracleRowCountWarpper_1(String sql)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(sql);
    
    return sb.toString();
  }

  protected String getOracleRowCountWarpper(String sql)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT count(*) as cnt FROM ( ").append(sql).append(" ) a___ ");
    
    return sb.toString();
  }
  



  private static class MapResultSetExtractor
    implements ResultSetExtractor<Map<?, ?>>
  {
    public MapResultSetExtractor(Object object) {
		// TODO Auto-generated constructor stub
	}

	public Map<?, ?> extractData(ResultSet rs)
      throws SQLException, DataAccessException
    {
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      int count = 0;
      
      Map dataMap = new LinkedCaseInsensitiveMap(columnCount);
      
      while (rs.next()) {
        if (count >= 1) {
          throw new LTSDataAccessException("Incorrect result size: expected : 1");
        }
        for (int i = 1; i <= columnCount; i++) {
          String key = JdbcUtils.lookupColumnName(rsmd, i);
          Object val = JdbcUtils.getResultSetValue(rs, i);
          val = DataAccessImpl.refineValue(val, rsmd, i);
          dataMap.put(key, val);
        }
        
        count++;
      }
      
      if (count == 0) {
        throw new LTSDataAccessException("Incorrect result size: expected : 1");
      }
      
      return dataMap;
    }
  }
  




  private static class ListResultSetExtractor
    implements ResultSetExtractor<List<?>>
  {
    public ListResultSetExtractor(Object object) {
		// TODO Auto-generated constructor stub
	}

	public ListResultSetExtractor() {
		// TODO Auto-generated constructor stub
	}

	public List<?> extractData(ResultSet rs)
      throws SQLException, DataAccessException
    {
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      
      List<Map> rtn = new ArrayList();
      while (rs.next())
      {
        Map dataMap = new LinkedCaseInsensitiveMap(columnCount);
        for (int i = 1; i <= columnCount; i++) {
          String key = JdbcUtils.lookupColumnName(rsmd, i);
          Object val = JdbcUtils.getResultSetValue(rs, i);
          val = DataAccessImpl.refineValue(val, rsmd, i);
          dataMap.put(key, val);
        }
        rtn.add(dataMap);
      }
      return rtn;
    }
  }
  

  private static Object refineValue(Object val, ResultSetMetaData rsmd, int i)
    throws SQLException, DataAccessException
  {
    switch (rsmd.getColumnType(i))
    {
    case -6: 
    case -5: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 8: 
      val = val == null ? new BigDecimal("0") : val;
      break;
    

    case -16: 
    case -15: 
    case -9: 
    case -3: 
    case -1: 
    case 1: 
    case 12: 
      val = val == null ? "" : val;
    }
    
    return val;
  }
  





























  private String foramtForBatchLog(String sql, List<Object[]> params)
  {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).setPrettyPrinting().setVersion(1.0D).create();
    





    Map outPutMap = new HashMap();
    outPutMap.put("sql", sql);
    outPutMap.put("params", params);
    return gson.toJson(outPutMap);
  }
}
