package lts.global.core.dao.support;

import gov.fdc.framework.core.log.LoggerUtil;
import org.springframework.util.Assert;

public class DataSourceContextHolder
{

  /** @deprecated */
  public static final String DATA_SOURCE_PREFIX_STRING = "ds";
  private static final ThreadLocal<String> dataSourceContextHolder = new ThreadLocal();
  private static final ThreadLocal<String> schemaNameContextHolder = new ThreadLocal();

  public static void setTargetDataSource(String targetDataSource)
  {
    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : set Target DataSource with [" + targetDataSource + "]. ### ");
    Assert.notNull(targetDataSource, "Target data source cannot be null.");
    dataSourceContextHolder.set(targetDataSource);
  }

  public static String getTargetDataSource()
  {
    String rtn = (String)dataSourceContextHolder.get();
    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : get Target DataSource. ds[" + rtn + "] ### ");
    return rtn;
  }

  public static void setSchemaName(String targetSchemaName)
  {
    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : set schema name with [" + targetSchemaName + "]. ### ");
    Assert.notNull(targetSchemaName, "Target schema name cannot be null.");
    schemaNameContextHolder.set(targetSchemaName);
  }

  public static String getSchemaName()
  {
    String rtn = (String)schemaNameContextHolder.get();
    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : get schema name. schema[" + rtn + "] ### ");
    return rtn;
  }

  /** @deprecated */
  public static String getCurrentDataSourceSchemaName()
  {
    String rtn = ((String)dataSourceContextHolder.get()).replaceAll("ds_TAX_", "").replaceAll("ds_OA_", "").replaceAll("ds_DOC_", "").replaceAll("ds", "");

    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : get schema name. name[" + rtn + "] ### ");
    Assert.notNull(dataSourceContextHolder.get(), "ContextHolder cannot be null.");
    return rtn;
  }

  public static void resetDefaultDataSource()
  {
    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : clean DataSource. ### ");
    dataSourceContextHolder.remove();
    LoggerUtil.debug(DataSourceContextHolder.class, " ### DataSourceContextHolder : clean schema name. ### ");
    schemaNameContextHolder.remove();
  }
}