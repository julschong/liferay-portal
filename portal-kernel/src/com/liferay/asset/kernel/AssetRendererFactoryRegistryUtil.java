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
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.List;

import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Bruno Farache
 * @author Marcellus Tavares
 */
public class AssetRendererFactoryRegistryUtil {

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return _getAssetRendererFactoryRegistry().getAssetRendererFactories(
			companyId);
	}

	public static List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId, boolean filterSelectable) {

		return _getAssetRendererFactoryRegistry().getAssetRendererFactories(
			companyId, filterSelectable);
	}

	public static <T> AssetRendererFactory<T> getAssetRendererFactoryByClass(
		Class<T> clazz) {

		return (AssetRendererFactory<T>)
			_getAssetRendererFactoryRegistry().getAssetRendererFactoryByClass(
				clazz);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		return _getAssetRendererFactoryRegistry().
			getAssetRendererFactoryByClassName(className);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByClassNameId(
		long classNameId) {

		return _getAssetRendererFactoryRegistry().
			getAssetRendererFactoryByClassNameId(classNameId);
	}

	public static AssetRendererFactory<?> getAssetRendererFactoryByType(
		String type) {

		return _getAssetRendererFactoryRegistry().getAssetRendererFactoryByType(
			type);
	}

	public static long[] getClassNameIds(long companyId) {
		return _getAssetRendererFactoryRegistry().getClassNameIds(companyId);
	}

	public static long[] getClassNameIds(
		long companyId, boolean filterSelectable) {

		return _getAssetRendererFactoryRegistry().getClassNameIds(
			companyId, filterSelectable);
	}

	public static long[] getIndexableClassNameIds(
		long companyId, boolean filterSelectable) {

		return _getAssetRendererFactoryRegistry().getIndexableClassNameIds(
			companyId, filterSelectable);
	}

	private static AssetRendererFactoryRegistry
		_getAssetRendererFactoryRegistry() {

		return _serviceTracker.getService();
	}

	private AssetRendererFactoryRegistryUtil() {
	}

	private static ServiceTracker
		<AssetRendererFactoryRegistry, AssetRendererFactoryRegistry>
			_serviceTracker;

	{
		_serviceTracker = new ServiceTracker<>(
			SystemBundleUtil.getBundleContext(),
			AssetRendererFactoryRegistry.class, null);

		_serviceTracker.open();
	}

}