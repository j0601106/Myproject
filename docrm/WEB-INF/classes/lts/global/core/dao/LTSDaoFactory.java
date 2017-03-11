package lts.global.core.dao;

import gov.fdc.framework.core.dao.DAOFactory;
import gov.fdc.framework.core.util.ApplicationContextSingleton;
import gov.fdc.library.exception.LTSDefineException;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;




public class LTSDaoFactory
  extends DAOFactory
{
  private Map<String, LTSDao> daoCacheMap = new Hashtable();
  


  public void setDaoCacheMap(Map<String, LTSDao> daoMap)
  {
    this.daoCacheMap = daoMap;
  }
  


  public static LTSDaoFactory getDaoFactory()
  {
    return (LTSDaoFactory)ApplicationContextSingleton.getInstance().getBean("ltsDaoFactory");
  }
  



  public LTSDao createDao(String dsType)
  {
    LTSDao dao = (LTSDao)this.daoCacheMap.get(dsType);
    if (dao != null) {
      dao.setDataSourceName(dsType);
      return dao;
    }
    throw new LTSDefineException(" ### dao not found, dsType[" + dsType + "] ### ");
  }
  

  public LTSDao createDao(String url, String username, String password)
  {
    DataSource ds = createDataSource(url, username, password);
    
    LTSDao dao = (LTSDao)ApplicationContextSingleton.getInstance().getBean("LTSDaoImpl");
    dao.setDataSource(ds);
    dao.setDataSourceName("ds_CUSTOM_" + username);
    return dao;
  }
  

  public LTSDao createDao(String driveClass, String url, String username, String password)
  {
    DataSource ds = createDataSource(driveClass, url, username, password);
    
    LTSDao dao = (LTSDao)ApplicationContextSingleton.getInstance().getBean("LTSDaoImpl");
    dao.setDataSource(ds);
    dao.setDataSourceName("ds_CUSTOM_" + username);
    return dao;
  }
  


  private String callStackToString()
  {
    return stackToString(new RuntimeException());
  }
  




  private String stackToString(Throwable e)
  {
    StringWriter sw = new StringWriter();
    BufferedWriter bw = new BufferedWriter(sw);
    PrintWriter pw = new PrintWriter(bw);
    e.printStackTrace(pw);
    pw.close();
    String text = sw.getBuffer().toString();
    
    text = text.substring(text.indexOf("at"));
    text = text.replace("at ", "DEBUG_FRAME = ");
    return text;
  }
  



  private String callLTSStackToString()
  {
    return ltsStackToString(new RuntimeException());
  }
  



  private String ltsStackToString(Throwable e)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("LTS EXECUTE STACK : [\n");
    for (StackTraceElement element : e.getStackTrace()) {
      if ((element.getClassName().startsWith("lts")) || (element.getClassName().startsWith("gov.fdc")) || (element.getClassName().startsWith("com.acer"))) {
        sb.append("\t\t").append(element.getClassName()).append(".").append(element.getMethodName()).append("(").append("LINE:").append(element.getLineNumber()).append(")").append("\n");
      }
    }
    sb.append("\t").append("]\n");
    return sb.toString();
  }
  

  public void close()
    throws Throwable
  {
    for (LTSDao dao : this.daoCacheMap.values()) {
      dao.close();
      dao = null;
    }
    this.daoCacheMap = null;
  }
}