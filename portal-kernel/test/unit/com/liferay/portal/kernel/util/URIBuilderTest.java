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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Julius Lee
 */
public class URIBuilderTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);

		Mockito.when(_portal.stripURLAnchor(Mockito.anyString(), Mockito.anyString())).then(invocationOnMock -> _stripURLAnchor(
			(String) invocationOnMock.getArguments()[0], StringPool.POUND));
	}

	@Test
	public void testAddParameter() {
		String[] stripURLAnchor = _stripURLAnchor(
			_BASE_URL_WITH_ANCHOR, StringPool.POUND);

		String testParamKey = "testParamKey";
		String testParamValue = "testParamValue";

		String actualURL = URIBuilder.create(
			_BASE_URL_WITH_ANCHOR
		).addParameter(
			testParamKey, testParamValue
		).build();
		String expectedURL = StringBundler.concat(
			stripURLAnchor[0], StringPool.QUESTION, testParamKey,
			StringPool.EQUAL, testParamValue, stripURLAnchor[1]);

		Assert.assertEquals(expectedURL, actualURL);

		stripURLAnchor = _stripURLAnchor(actualURL, StringPool.POUND);

		actualURL = URIBuilder.create(
			actualURL
		).addParameter(
			testParamKey, testParamValue
		).build();

		expectedURL = StringBundler.concat(
			stripURLAnchor[0], StringPool.AMPERSAND, testParamKey,
			StringPool.EQUAL, testParamValue, stripURLAnchor[1]);

		Assert.assertEquals(expectedURL, actualURL);
	}

	@Test
	public void testAddParameterWithEncodedParamValue() {
		String testParamKey = "testParamKey";
		String testParamValue = "test ParamValue";

		String actualURL = URIBuilder.create(
			_BASE_URL_WITH_ANCHOR
		).addParameter(
			testParamKey, testParamValue
		).build();

		String[] stripURLAnchor = _stripURLAnchor(
			_BASE_URL_WITH_ANCHOR, StringPool.POUND);

		String encodedExpectedValue = "test+ParamValue";

		String expectedURL = StringBundler.concat(
			stripURLAnchor[0], StringPool.QUESTION, testParamKey,
			StringPool.EQUAL, encodedExpectedValue, stripURLAnchor[1]);

		Assert.assertEquals(expectedURL, actualURL);
	}

	@Test
	public void testCreatingURI() {
		URIBuilder uriBuilder = URIBuilder.create(_BASE_URL);

		Assert.assertEquals(_BASE_URL, uriBuilder.build());
	}

	@Test
	public void testCreatingURIWithURIInput() throws URISyntaxException {
		URIBuilder uriBuilder = URIBuilder.create(new URI(_BASE_URL));

		Assert.assertEquals(_BASE_URL, uriBuilder.build());
	}

	@Test
	public void testNullURL() {
		String nullString = null;

		Assert.assertNull(
			URIBuilder.create(
				nullString
			).addParameter(
				"test", "test"
			).setParameter(
				"test", "test"
			).removeParameter(
				"test"
			).encodeParameters(
			).build());

		nullString = "null";

		Assert.assertNull(
			URIBuilder.create(
				nullString
			).addParameter(
				"test", "test"
			).setParameter(
				"test", "test"
			).removeParameter(
				"test"
			).encodeParameters(
			).build());

		nullString = "";

		Assert.assertNull(
			URIBuilder.create(
				nullString
			).addParameter(
				"test", "test"
			).setParameter(
				"test", "test"
			).removeParameter(
				"test"
			).encodeParameters(
			).build());
	}

	@Test
	public void testParamMethodsWithNullValue() {

		// Param method should not be executed if value passed in is null

		String nullValue = null;

		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).addParameter(
				"test", nullValue
			).build());
		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).setParameter(
				"test", nullValue
			).build());
		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).removeParameter(
				nullValue
			).build());

		nullValue = "null";

		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).addParameter(
				"test", nullValue
			).build());
		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).setParameter(
				"test", nullValue
			).build());
		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).removeParameter(
				nullValue
			).build());

		nullValue = "";

		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).addParameter(
				"test", nullValue
			).build());
		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).setParameter(
				"test", nullValue
			).build());
		Assert.assertEquals(
			_BASE_URL,
			URIBuilder.create(
				_BASE_URL
			).removeParameter(
				nullValue
			).build());
	}

	@Test
	public void testRemoveParameter() {
		Assert.assertEquals(
			_BASE_URL_WITH_ANCHOR,
			URIBuilder.create(
				_BASE_URL + "?testParamKey=testParamVal#TestAnchor"
			).removeParameter(
				"testParamKey"
			).build());
	}

	@Test
	public void testSetParameter() {
		String[] stripAnchor = _stripURLAnchor(
			_BASE_URL_WITH_ANCHOR, StringPool.POUND);

		String testParamKey = "testParamKey";
		String testParamValue = "testParamValue";

		String actualURL = URIBuilder.create(
			_BASE_URL_WITH_ANCHOR
		).setParameter(
			testParamKey, testParamValue
		).build();

		String expectedURL = StringBundler.concat(
			stripAnchor[0], StringPool.QUESTION, testParamKey, StringPool.EQUAL,
			testParamValue, stripAnchor[1]);

		Assert.assertEquals(expectedURL, actualURL);

		stripAnchor = _stripURLAnchor(actualURL, StringPool.POUND);

		Assert.assertEquals(
			expectedURL,
			URIBuilder.create(
				actualURL
			).setParameter(
				testParamKey, testParamValue
			).build());
	}

	private String[] _stripURLAnchor(String url, String separator) {

		// _stripURLAnchor is copied from PortalImpl for ease of testing

		String anchor = StringPool.BLANK;

		int pos = url.indexOf(separator);

		if (pos != -1) {
			anchor = url.substring(pos);
			url = url.substring(0, pos);
		}

		return new String[] {url, anchor};
	}

	private static final String _BASE_URL = "http://test.test";

	private static final String _BASE_URL_WITH_ANCHOR =
		"http://test.test#TestAnchor";

	@Mock
	private Portal _portal;

}