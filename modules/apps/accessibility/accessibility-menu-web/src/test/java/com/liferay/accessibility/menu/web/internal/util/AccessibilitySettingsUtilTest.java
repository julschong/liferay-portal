/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.accessibility.menu.web.internal.util;

import com.liferay.accessibility.menu.web.internal.model.AccessibilitySetting;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.servlet.http.HttpServletRequest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Evan Thibodeau
 */
public class AccessibilitySettingsUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_languageUtilMockedStatic.when(
			() -> LanguageUtil.get(
				Mockito.any(HttpServletRequest.class), Mockito.anyString())
		).thenReturn(
			null
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_languageUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_httpServletRequest = new MockHttpServletRequest();
	}

	@Test
	public void testGetAccessibilitySettings() {
		for (AccessibilitySetting accessibilitySetting :
				AccessibilitySettingsUtil.getAccessibilitySettings(
					_httpServletRequest)) {

			Assert.assertEquals(
				null, accessibilitySetting.getSessionClicksValue());
		}
	}

	private static final MockedStatic<LanguageUtil> _languageUtilMockedStatic =
		Mockito.mockStatic(LanguageUtil.class);

	private HttpServletRequest _httpServletRequest;

}