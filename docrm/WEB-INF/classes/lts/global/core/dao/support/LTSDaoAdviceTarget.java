package lts.global.core.dao.support;

import lts.global.core.dao.LTSDao;

public abstract interface LTSDaoAdviceTarget
{
  public abstract LTSDao getDao();
  
  public abstract void setDao(LTSDao paramLTSDao);
}
