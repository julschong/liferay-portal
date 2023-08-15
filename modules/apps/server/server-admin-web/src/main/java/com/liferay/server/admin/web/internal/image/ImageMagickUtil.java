/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.image;

import com.liferay.portal.kernel.image.ImageMagick;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Properties;

/**
 * The ImageMagick utility class.
 *
 * @author Alexander Chow
 */
public class ImageMagickUtil {

	/**
	 * Returns the global search path configured for ImageMagick.
	 *
	 * @return the global search path
	 * @throws Exception if an unexpected error occurred
	 */
	public static String getGlobalSearchPath() throws Exception {
		return _imageMagick.getGlobalSearchPath();
	}

	/**
	 * Returns the cache and resource usage limits configured for ImageMagick.
	 *
	 * @return the cache and resource usage limits
	 * @throws Exception if an unexpected error occurred
	 */
	public static Properties getResourceLimitsProperties() throws Exception {
		return _imageMagick.getResourceLimitsProperties();
	}

	/**
	 * Returns <code>true</code> if ImageMagick is enabled.
	 *
	 * @return <code>true</code> if ImageMagick is enabled; <code>false</code>
	 *         otherwise
	 */
	public static boolean isEnabled() {
		return _imageMagick.isEnabled();
	}

	private static volatile ImageMagick _imageMagick =
		ServiceProxyFactory.newServiceTrackedInstance(
			ImageMagick.class, ImageMagickUtil.class, "_imageMagick", false);

}