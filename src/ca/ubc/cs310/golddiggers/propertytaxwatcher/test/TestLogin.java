package ca.ubc.cs310.golddiggers.propertytaxwatcher.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Before;
import org.junit.Test;

public class TestLogin extends SeleniumTest
{
	
	@Before
	public void setUp() throws Exception {
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "http://golddiggers310.appspot.com/";
		this.selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}
	
	@Test
	public void login() throws Exception 
	{
		selenium.open("/");
		selenium.click("link=Sign In");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=Email", "golddiggers310");
		selenium.type("id=Passwd", "propertytax310");
		selenium.click("id=signIn");
		selenium.waitForPageToLoad("30000");
		//selenium.isElementPresent("class=\"twtr-profile-img-anchor\"");
	}
}
