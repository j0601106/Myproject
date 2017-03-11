package lts.global.batch.manager.impl;

import gov.fdc.framework.batch.impl.AbstractBatchManager;
import lts.global.batch.manager.LTSBatchManager;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;
import lts.global.core.dao.support.LTSDaoAdviceTarget;
import org.springframework.util.Assert;













public abstract class LTSBatchManagerImpl
  extends AbstractBatchManager
  implements LTSBatchManager, LTSDaoAdviceTarget
{
  private LTSDao dao;
  
  public LTSBatchManagerImpl() {}
  
  protected LTSBatchManagerImpl(String progCd, String[] args)
  {
    super(progCd, args);
  }
  








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
    Assert.notNull(dsType, "dsType can't null!!");
    LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(dsType);
    return dao;
  }
  





  protected LTSDao createDao(String url, String password, String name)
  {
    Assert.notNull(url, "url can't null!!");
    Assert.notNull(password, "password can't null!!");
    Assert.notNull(name, "name can't null!!");
    LTSDao dao = LTSDaoFactory.getDaoFactory().createDao(url, password, name);
    return dao;
  }
}
