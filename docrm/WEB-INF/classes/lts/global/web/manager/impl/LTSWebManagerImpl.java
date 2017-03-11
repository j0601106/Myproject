package lts.global.web.manager.impl;

import gov.fdc.framework.web.manager.impl.AbstractWebManager;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;
import lts.global.core.dao.support.LTSDaoAdviceTarget;
import lts.global.web.manager.LTSWebManager;
















public abstract class LTSWebManagerImpl
  extends AbstractWebManager
  implements LTSWebManager, LTSDaoAdviceTarget
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
    LTSDao dao = LTSDaoFactory.getDaoFactory().createDao("ds_SSR_E77A");
    return dao;
  }
  





  protected LTSDao createDao(String url, String password, String name)
  {
    LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(url, password, name);
    return dao;
  }
}
