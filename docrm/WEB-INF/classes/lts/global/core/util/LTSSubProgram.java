package lts.global.core.util;

import lts.global.core.dao.LTSDao;

public class LTSSubProgram
{
  private LTSDao ltaDao;
  
  public void setDao(LTSDao ltaDao) {
    this.ltaDao = ltaDao;
  }
  
  public LTSDao getDao() {
    return this.ltaDao;
  }
}
