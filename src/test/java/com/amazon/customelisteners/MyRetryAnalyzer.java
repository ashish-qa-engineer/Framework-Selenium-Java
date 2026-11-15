package com.amazon.customelisteners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.amazon.utils.ConfigReader;

public class MyRetryAnalyzer implements IRetryAnalyzer {

	ConfigReader configReader = new ConfigReader();
	private int retryCount = 0;
	private int max_retryCount = Integer.valueOf(configReader.getMaxRetriesValue());

	@Override
	public boolean retry(ITestResult result) {
		if (retryCount < max_retryCount && !result.isSuccess()) {
			retryCount++;
			System.out.println("Retrying the Test ==> " + result.getName() + " ==> Attempt No = " + retryCount);
			return true;
		}
		return false;
	}

}
