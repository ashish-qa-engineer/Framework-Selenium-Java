package com.amazon.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	private Properties prop;
	
	public ConfigReader() {
		prop = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream("src/test/resources/configurations/config.properties");
			prop.load(fis);
			
		}catch(IOException io) {
			io.printStackTrace();
		}
		
	}
	
	public String getUrl() {
		ConfigReader configReader = new ConfigReader();
		if (configReader.getServer().equalsIgnoreCase("PROD")) {
			return prop.getProperty("PRODURL");
		}else if(configReader.getServer().equalsIgnoreCase("DEV")){
			return prop.getProperty("DEVURL");
		}else {
			return prop.getProperty("QAURL");
		}
		
	}

	public String getBrowser() {
		return prop.getProperty("BROWSER");
	}


	public Long getGlobalWaitValue() {
		
		return Long.parseLong(prop.getProperty("GLOBALWAIT"));
	}
	
	public String getFieldsVerificationExcelName() {
		
		return prop.getProperty("FIELDS_VERIFICATION_EXCEL");
	}
	
	public String getMaxRetriesValue() {
		
		return prop.getProperty("MAX_RETRIES");
	}

	public String getServer() {
		return prop.getProperty("ENV");
	}
	
	public String getParallelMode() {
		return prop.getProperty("PARALLEL_MODE");
	}
	
	public String getMaxParallelTests() {
		return prop.getProperty("MAX_PARALLEL_TESTS");
	}
	
	public String getRunMode() {
		return prop.getProperty("RUN_MODE");
	}

	public String getGridUrl() {
		return prop.getProperty("GRID_URL");
	}
	
}

