package lts.global.core.manager.impl;

import gov.fdc.framework.core.manager.impl.AbstractManager;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;
import lts.global.core.dao.support.LTSDaoAdviceTarget;
import lts.global.core.manager.LTSManager;

















public abstract class LTSManagerImpl
  extends AbstractManager
  implements LTSManager, LTSDaoAdviceTarget
{
  private LTSDao dao;
  
  public LTSDao getDao()
  {
    return this.dao;
  }
  



  public void setDao(LTSDao dao)
  {
    this.dao = dao;
  }
  



  protected LTSDao createDao(String dsType)
  {
    LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(dsType);
    return dao;
  }
  





  protected LTSDao createDao(String url, String password, String name)
  {
    LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(url, password, name);
    return dao;
  }
}