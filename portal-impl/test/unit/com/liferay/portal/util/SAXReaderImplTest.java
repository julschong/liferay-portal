/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReader;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.xml.SAXReaderImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Brian Wing Shun Chan
 * @author Roberto Díaz
 */
public class SAXReaderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_saxReader = new SAXReaderImpl();
	}

	@Test
	public void testOutputFromStringInputFormat()
		throws DocumentException, IOException {
		Document document = _saxReader.read("<?xml version=\"1.0\"?><tag>content</tag>");

		Assert.assertEquals("<?xml version=\"1.0\"?>\n\n<tag>content</tag>", document.formattedString());
	}

	@Test
	public void testSameOutputFromStringAndDocument()
		throws IOException, DocumentException {
		Document documentFromString = _saxReader.read("<?xml version=\"1.0\"?><tag>content</tag>");

		Document document = _saxReader.createDocument();
		Element testElement = document.addElement("tag");
		testElement.addText("content");

		Assert.assertEquals(document.formattedString(), documentFromString.formattedString());
	}

	@Test
	public void testEscapingCDATASpecialCharacters() throws IOException, DocumentException {
		Document document = _saxReader.createDocument();
		Element testElement = document.addElement("tag");
		testElement.addCDATA("]]>");

		Assert.assertEquals("<?xml version=\"1.0\"?>\n\n<tag><![CDATA[]]]]><![CDATA[>]]></tag>", document.formattedString());

		Document documentFromString = _saxReader.read("<?xml version=\"1.0\"?><tag><![CDATA[]]>]]></tag>");

		Assert.assertEquals("<?xml version=\"1.0\"?>\n\n<tag><![CDATA[]]]]><![CDATA[>]]></tag>", documentFromString.formattedString());
	}

	@Test
	public void testOutputFromAlreadyFormattedStringInputWithCDATA()
		throws IOException, DocumentException, org.dom4j.DocumentException {
		Document documentFromString = _saxReader.read("<?xml version=\"1.0\"?>\n\n<tag>\n\t<![CDATA[\n\t\t]]>\n\t]]>\n</tag>");

		System.out.println(documentFromString.formattedString());
	}

	private static SAXReader _saxReader;
}