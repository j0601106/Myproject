package lts.docrm.api.adapter;

import gov.fdc.framework.core.dao.Query;
import gov.fdc.library.exception.LTSDataAccessException;
import lts.docrm.core.util.DOCRMConstant;
import lts.docrm.core.util.XMLUtil2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.acer.util2.MapUtil;

@Scope("prototype")
@Component("DOCRM260UPAdapter")
public class DOCRM260UPAdapter extends DOCRMAdapter{

	@Override
	public boolean start() throws Exception {

		List list = new ArrayList();
		
		list.add("\\DOCRM\\intranet\\question\\up\\");
		list.add("\\DOCRM\\intranet\\qia_add_schedule\\up\\");
		
		for(int j=0; j<list.size(); j++){
			File targetFile = new File(list.get(j).toString());

			if ((!targetFile.exists() && !targetFile.isDirectory()) || (targetFile.listFiles().length == 0)) {
				continue;
			}
			
			File[] fileList = targetFile.listFiles();
			
			for(int i=0; i<fileList.length; i++){
				
				String filePath = fileList[i].toString();
				File file = new File(filePath);
				String fileNM = file.getName();

				if(fileNM.lastIndexOf(".") == -1 || !"xml".equals(fileNM.substring(fileNM.lastIndexOf(".")+1))){
					continue;
				}
				
				Map map = new HashMap();
				
				map.put("MSG_NM", fileNM);
				
				Query query = Query.createQuery().setSqlId("docrm.dao.DOCRM260ConvertAdapter.insertDOCRMT320").setParamMap(map);
				this.getDao().insert(query);
			}
		}
		return true;
	}
}
