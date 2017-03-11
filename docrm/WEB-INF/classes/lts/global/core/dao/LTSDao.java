package lts.global.core.dao;

import gov.fdc.framework.core.dao.BaseDao;
import java.sql.Connection;

public abstract interface LTSDao
  extends BaseDao
{
  public abstract Connection getConnection();
}
