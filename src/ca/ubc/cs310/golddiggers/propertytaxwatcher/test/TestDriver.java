package ca.ubc.cs310.golddiggers.propertytaxwatcher.test;

public class TestDriver
{

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		UITest uiTest = new UITest();
		uiTest.setUp();
		uiTest.testFullApplication();
		uiTest.tearDown();

	}

}
