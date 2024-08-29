package com.ninza.hrm.ap.genericUtility;

import java.io.FileInputStream;
import java.util.Properties;

public class FileUtility {

	/**
	 * used to read the data from propertied file based on key
	 * @param key
	 * @return
	 * @throws Throwable
	 */
	public String getDataFromPropertiesFile(String key) throws Throwable {
		
		FileInputStream fis = new FileInputStream("./propertiesfile/commondata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);
		String data = pObj.getProperty(key);
		
		return data;
	}

}
