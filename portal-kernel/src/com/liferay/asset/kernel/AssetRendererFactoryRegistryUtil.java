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

package com.liferay.asset.kernel;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class AssetRendererFactoryRegistryUtil {

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return _assetRendererFactoryRegistry.getAssetRendererFactories(
			companyId);
	}

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId, boolean filterSelectable) {

		return _assetRendererFactoryRegistry.getAssetRendererFactories(
			companyId, filterSelectable);
	}

	public static <T> AssetRendererFactory<T> getAssetRendererFactoryByClass(
		Class<T> clazz) {

		return (AssetRendererFactory<T>)
			_assetRendererFactoryRegistry.getAssetRendererFactoryByClass(clazz);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		return _assetRendererFactoryRegistry.getAssetRendererFactoryByClassName(
			className);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassNameId(
		long classNameId) {

		return _assetRendererFactoryRegistry.
			getAssetRendererFactoryByClassNameId(classNameId);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByType(
		String type) {

		return _assetRendererFactoryRegistry.getAssetRendererFactoryByType(
			type);
	}

	public static long[] getClassNameIds(long companyId) {
		return _assetRendererFactoryRegistry.getClassNameIds(companyId);
	}

	public static long[] getClassNameIds(
		long companyId, boolean filterSelectable) {

		return _assetRendererFactoryRegistry.getClassNameIds(
			companyId, filterSelectable);
	}

	public static long[] getIndexableClassNameIds(
		long companyId, boolean filterSelectable) {

		return _assetRendererFactoryRegistry.getIndexableClassNameIds(
			companyId, filterSelectable);
	}

	private AssetRendererFactoryRegistryUtil() {
	}

	private static volatile AssetRendererFactoryRegistry
		_assetRendererFactoryRegistry =
			ServiceProxyFactory.newServiceTrackedInstance(
				AssetRendererFactoryRegistry.class,
				AssetRendererFactoryRegistryUtil.class,
				"_assetRendererFactoryRegistry", false);

}