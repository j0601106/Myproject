package lts.global.core.dao.support;

import gov.fdc.framework.api.BaseAdapter;
import gov.fdc.framework.core.dao.support.DataSourceContextHolder;
import gov.fdc.framework.core.dao.support.FDCTransactionManager;
import gov.fdc.framework.core.dao.support.FDCTransactionStatus;
import gov.fdc.framework.core.log.LoggerUtil;
import java.lang.reflect.Method;
import java.sql.Connection;
import lts.global.core.dao.LTSDao;
import lts.global.core.dao.LTSDaoFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.jdbc.datasource.DataSourceUtils;









public class LTSDaoAdvice
  implements MethodInterceptor
{
  public LTSDaoFactory getDaoFactory()
  {
    return LTSDaoFactory.getDaoFactory();
  }
  
  public Object invoke(MethodInvocation methodInvocation) throws Throwable
  {
    Method method = methodInvocation.getMethod();
    
    String message = "\t--- [Execute] LTSDaoAdvice 0 " + getMethodSignature(method) + " ---- ";
    LoggerUtil.info(getClass(), message);
    
    long t1 = System.currentTimeMillis();
    Object result = null;
    try
    {
      if ((methodInvocation.getThis() instanceof LTSDaoAdviceTarget)) {
        //LTSDao dao = getDaoFactory().createDao(DataSourceContextHolder.getTargetDataSource());
    	LTSDao dao = getDaoFactory().createDao("ds_SSR_E77A");
        ((LTSDaoAdviceTarget)methodInvocation.getThis()).setDao(dao);
        boolean enableTx = true;
        if ((methodInvocation.getThis() instanceof BaseAdapter)) {
          enableTx = false;
        }
        
        LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : target Class :[" + methodInvocation.getThis() + "], enable Transaction(" + enableTx + ") ...  ### ");
        
        if (enableTx) {
          FDCTransactionManager tx = null;
          try {
            tx = dao.beginTransaction();
            
            Connection conn = DataSourceUtils.getConnection(dao.getDataSource());
            
            LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : conn (" + conn + ") ...  ### ");
            
            LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : beginTransaction(" + tx.hashCode() + ") ...  ### ");
            
            result = methodInvocation.proceed();
            
            LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : start tx.commited(" + tx.hashCode() + ") ...  ### ");
            
            if (tx.getTransaction().isCompleted()) {
              LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : tx is Completed.  ### ");
              
              if (tx.getTransaction().isRollbackOnly()) {
                LoggerUtil.warn(getClass(), " ### LTSDaoAdvice :tx.status.isRollbackOnly. execute rollback!! ...  ### ");
                tx.rollback();
              }
            }
            else
            {
              LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : tx is not Completed.  ### ");
              
              if (tx.getTransaction().isRollbackOnly()) {
                LoggerUtil.warn(getClass(), " ### LTSDaoAdvice :tx.status.isRollbackOnly. skip commit, do rollBack!! ...  ### ");
                tx.rollback();
              } else {
                LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : tx.commited(" + tx.hashCode() + ") ...  ### ");
                tx.commit();
              }
            }
            
            LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : finish tx.commited.  ### ");
          }
          catch (Throwable e) {
            e.printStackTrace();
            try
            {
              if (tx != null) {
                LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : start tx.rollback(" + tx.hashCode() + ") ...  ### ");
                
                if (tx.getTransaction().isCompleted()) {
                  LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : tx is Completed.  ### ");
                  
                  if (tx.getTransaction().isRollbackOnly()) {
                    LoggerUtil.warn(getClass(), " ### LTSDaoAdvice :tx.status.isRollbackOnly. do rollBack!! ...  ### ");
                    tx.rollback();
                  }
                }
                else {
                  LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : tx.rollback(" + tx.hashCode() + ") ...  ### ");
                  
                  tx.rollback();
                }
                
                LoggerUtil.debug(getClass(), " ### LTSDaoAdvice : finish tx.rollback(" + tx.hashCode() + ") ...  ### ");
              }
            } catch (Throwable t) {
              t.printStackTrace();
              LoggerUtil.error(getClass(), t);
            }
            throw e;
          }
        } else {
          result = methodInvocation.proceed();
        }
      }
      else {
        result = methodInvocation.proceed();
      }
    } finally { long t2;
      String message1;
      long t21 = System.currentTimeMillis();
      String message11 = "--- [Leave] LTSDaoAdvice " + String.valueOf(t21 - t1) + " " + getMethodSignature(method);
      LoggerUtil.info(getClass(), message11);
    }
    
    return result;
  }
  



  private String getMethodSignature(Method method)
  {
    String methodSignature = method.getDeclaringClass().toString() + "." + method.getName() + "()";
    
    return methodSignature;
  }
}
