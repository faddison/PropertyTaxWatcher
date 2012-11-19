package ca.ubc.cs310.golddiggers.propertytaxwatcher.test;

import org.junit.Test;
import org.openqa.selenium.WebDriverBackedSelenium;

public class TestPage extends SeleniumTest
{
	@Test
	public void testHomePage() throws Exception
	{
		// home
		selenium.click("css=button.gwt-Button");
	}

	@Test
	public void testSearchPage() throws Exception
	{
		// search
		selenium.click("xpath=(//button[@type='button'])[2]");
	}
	
	@Test
	public void testComparePage() throws Exception 
	{
		// compare
		selenium.click("xpath=(//button[@type='button'])[3]");
	}

	@Test
	public void testDataPage() throws Exception 
	{
		// data
		selenium.click("xpath=(//button[@type='button'])[4]");
	}

	@Test
	public void testAboutPage() throws Exception 
	{
		// about
		selenium.click("xpath=(//button[@type='button'])[5]");
	}
	
}
