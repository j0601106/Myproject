package lts.global.api.adapter;

import gov.fdc.framework.api.impl.AbstractAdapter;
import gov.fdc.library.exception.LTSDefineException;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;
import lts.global.core.dao.support.LTSDaoAdviceTarget;
import lts.global.core.util.LTSAssertHandler;
















public abstract class LTSAdapter
  extends AbstractAdapter
  implements LTSDaoAdviceTarget
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
  




  protected void assertParam(String text, boolean require, String validateType, String length)
  {
    try
    {
      LTSAssertHandler.assertString(text, require, validateType, length);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  



  protected void assertParam(String text, boolean require, String validateType)
  {
    try
    {
      LTSAssertHandler.assertString(text, require, validateType);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  


  protected void assertParam(String text, boolean require)
  {
    try
    {
      LTSAssertHandler.assertString(text, require);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  




  protected void assertParam(long inp, boolean require, String validateType, String length)
  {
    try
    {
      LTSAssertHandler.ltsAssert(inp, require, validateType, length);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  




  protected void assertParam(long inp, boolean require, String validateType)
  {
    try
    {
      LTSAssertHandler.ltsAssert(inp, require, validateType);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  




  protected void assertParam(long inp, boolean require)
  {
    try
    {
      LTSAssertHandler.ltsAssert(inp, require);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  




  protected void assertParam(double inp, boolean require, String validateType, String length)
  {
    try
    {
      LTSAssertHandler.ltsAssert(inp, require, validateType, length);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  




  protected void assertParam(double inp, boolean require, String validateType)
  {
    try
    {
      LTSAssertHandler.ltsAssert(inp, require, validateType);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  




  protected void assertParam(double inp, boolean require)
  {
    try
    {
      LTSAssertHandler.ltsAssert(inp, require);
    } catch (Exception e) {
      if ((e instanceof LTSDefineException)) {
        addErrorCode(((LTSDefineException)e).getMesageType());
      }
    }
  }
  
  public Object getReturnObject()
  {
    return null;
  }
}