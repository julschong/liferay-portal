/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cluster.multiple.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.cluster.multiple.test.rule.ClusteringTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Julius Lee
 */
@RunWith(Arquillian.class)
public class ClusteringTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
			new LiferayIntegrationTestRule();

	@Test
	public void testDispatchLogDisplayContextExceptions() throws Exception {
		System.out.println("Integration Test");
	}

	@Rule
	public ClusteringTestRule _clusteringTestRule = new ClusteringTestRule();

}