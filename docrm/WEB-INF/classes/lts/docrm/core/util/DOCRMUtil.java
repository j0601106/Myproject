/*
@@程式代號 = DOCRMUtil.java
@@程式名稱 = DOCRMUtil
@@程式版本 = V1.000
@@更新日期 = 2011/08/20
@@檢查碼 = 內容由YPM自動產生
 */
package lts.docrm.core.util;

import java.util.Calendar;


public class DOCRMUtil {

	/**
	 * 取得民國年今日日期
	 * 
	 * @return String
	 */
	public static String getRocSysdate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR) - 1911);
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		month = month.length() == 1 ? "0" + month : month;
		String date = String.valueOf(c.get(Calendar.DATE));
		date = date.length() == 1 ? "0" + date : date;

		return year + month + date;
	}
	
	/**
	 * getVerifyNo 取得驗證碼
	 * @param StarFlag 驗證碼開頭字碼(預設無)
	 * @param Length 驗證碼長度(預設5長)
	 * @return String 驗證碼
	 */
	public static String getVerifyNo(String StarFlag, int Length) {
		// 2016.10.17 啟維 驗證碼模組中介

		StringBuffer VerifyNo = new StringBuffer();
		try {
			if (Length == 0){
				Length = 5;
			}
			if (StarFlag.length() > 0) {
				Length -= StarFlag.length();
				VerifyNo.append(StarFlag);
			}
			for (int i = 0; i < Length; i++) {
				if (((int) (Math.random() * 2)) == 0) {
					byte[] asciiCode = { (byte) ((int) (Math.random() * 26) + 65) };
					String asciiToString = new String(asciiCode);
					VerifyNo.append(asciiToString);
				} else {
					int random = (int) (Math.random() * 10);
					VerifyNo.append(random);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return VerifyNo.toString();
	}
}
