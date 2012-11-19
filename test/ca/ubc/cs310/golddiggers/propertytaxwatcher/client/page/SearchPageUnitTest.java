package ca.ubc.cs310.golddiggers.propertytaxwatcher.client.page;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ca.ubc.cs310.golddiggers.propertytaxwatcher.server.SearchParameter;

/**
 * SearchPageUnitTest class. This class is responsible for running JUnit tests
 * on the logic done in the SearchPage class.
 * 
 * @author Hubert Ngu
 */
public class SearchPageUnitTest
{
	private static final Class<?> TEST_CLASS = SearchPage.class;

	private static SearchPage page;

	@Before
	public void setUp()
	{
		// We need to mock the class because we can't call on GWT.create().
		page = Mockito.mock(SearchPage.class);
	}

	@Test
	public void testParameterFilledAll() throws Exception
	{
		SearchParameter para = new SearchParameter();
		para.setMinCurrentLandValue(Integer.MIN_VALUE);
		para.setMaxCurrentLandValue(Integer.MAX_VALUE);
		para.setPostalCode(new String());

		Method m = getMethod("isParameterFilled", SearchParameter.class);
		Boolean result = (Boolean) m.invoke(page, para);

		assertTrue(result);
	}

	@Test
	public void testParameterFilledTwo() throws Exception
	{
		SearchParameter para = new SearchParameter();
		para.setMinCurrentLandValue(Integer.MIN_VALUE);
		para.setMaxCurrentLandValue(Integer.MAX_VALUE);

		Method m = getMethod("isParameterFilled", SearchParameter.class);
		Boolean result = (Boolean) m.invoke(page, para);

		assertTrue(result);
	}

	@Test
	public void testParameterFilledOne() throws Exception
	{
		SearchParameter para = new SearchParameter();
		para.setMaxCurrentLandValue(Integer.MAX_VALUE);

		Method m = getMethod("isParameterFilled", SearchParameter.class);
		Boolean result = (Boolean) m.invoke(page, para);

		assertTrue(result);
	}

	@Test
	public void testParameterFilledDefault() throws Exception
	{
		// Defaults all parameters to false
		SearchParameter para = new SearchParameter();

		Method m = getMethod("isParameterFilled", SearchParameter.class);
		Boolean result = (Boolean) m.invoke(page, para);

		assertFalse(result);
	}

	@Test
	public void testIsNumberDecimal() throws Exception
	{
		String decimal = "3.1417";

		Method m = getMethod("isNumber", String.class);
		Boolean result = (Boolean) m.invoke(page, decimal);

		assertFalse(result);
	}

	@Test
	public void testIsNumberNotNumber() throws Exception
	{
		String notNumber = "I'm obviously not a number";

		Method m = getMethod("isNumber", String.class);
		Boolean result = (Boolean) m.invoke(page, notNumber);

		assertFalse(result);
	}

	@Test
	public void testIsNumber() throws Exception
	{
		String number = "123414";

		Method m = getMethod("isNumber", String.class);
		Boolean result = (Boolean) m.invoke(page, number);

		assertTrue(result);
	}

	@Test
	public void testMinSearchableDifferent() throws Exception
	{
		Field f = getField("MIN_LAND_VALUE_TEXT");
		String message = (String) f.get(page) + "DIFFERENT";

		Method m = getMethod("minSearchable", String.class);
		Boolean result = (Boolean) m.invoke(page, message);

		assertTrue(result);
	}

	@Test
	public void testMinSearchableSame() throws Exception
	{
		Field f = getField("MIN_LAND_VALUE_TEXT");
		String message = (String) f.get(page);

		Method m = getMethod("minSearchable", String.class);
		Boolean result = (Boolean) m.invoke(page, message);

		assertFalse(result);

	}

	@Test
	public void testMaxSearchableDifferent() throws Exception
	{
		Field f = getField("MAX_LAND_VALUE_TEXT");
		String message = (String) f.get(page) + "DIFFERENT";

		Method m = getMethod("maxSearchable", String.class);
		Boolean result = (Boolean) m.invoke(page, message);

		assertTrue(result);
	}

	@Test
	public void testMaxSearchableSame() throws Exception
	{
		Field f = getField("MAX_LAND_VALUE_TEXT");
		String message = (String) f.get(page);

		Method m = getMethod("maxSearchable", String.class);
		Boolean result = (Boolean) m.invoke(page, message);

		assertFalse(result);
	}

	@Test
	public void testEqualsSearchableMatchingMessage() throws Exception
	{
		String message = "message";
		String condition = "message";

		Method m = getMethod("equalsSearchable", String.class, String.class);
		Boolean result = (Boolean) m.invoke(page, message, condition);

		assertFalse(result);
	}

	@Test
	public void testEqualsSearchableDifferentMessage() throws Exception
	{
		String message = "message";
		String condition = "condition";

		Method m = getMethod("equalsSearchable", String.class, String.class);
		Boolean result = (Boolean) m.invoke(page, message, condition);

		assertTrue(result);
	}

	public Field getField(String name) throws Exception
	{
		Field f = TEST_CLASS.getDeclaredField(name);
		f.setAccessible(true);
		return f;
	}

	public Method getMethod(String name, Class<?>... args) throws Exception
	{
		Method m = TEST_CLASS.getDeclaredMethod(name, args);
		m.setAccessible(true);
		return m;
	}
}
