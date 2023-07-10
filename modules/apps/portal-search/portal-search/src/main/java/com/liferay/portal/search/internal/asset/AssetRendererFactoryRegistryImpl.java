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

package com.liferay.portal.search.internal.asset;

import com.liferay.asset.kernel.AssetRendererFactoryRegistry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adam Brandizzi
 */
@Component(service = AssetRendererFactoryRegistry.class)
public class AssetRendererFactoryRegistryImpl
	implements AssetRendererFactoryRegistry {

	@Override
	public List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId) {

		return _filterAssetRendererFactories(companyId, false);
	}

	@Override
	public List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId, boolean filterSelectable) {

		return _filterAssetRendererFactories(companyId, filterSelectable);
	}

	@Override
	public <T> AssetRendererFactory<T> getAssetRendererFactoryByClass(
		Class<T> clazz) {

		return (AssetRendererFactory<T>)
			_classNameAssetRenderFactoriesServiceTrackerMap.getService(
				clazz.getName());
	}

	@Override
	public AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className) {

		return _classNameAssetRenderFactoriesServiceTrackerMap.getService(
			className);
	}

	@Override
	public AssetRendererFactory<?> getAssetRendererFactoryByClassNameId(
		long classNameId) {

		return _classNameAssetRenderFactoriesServiceTrackerMap.getService(
			_portal.getClassName(classNameId));
	}

	@Override
	public AssetRendererFactory<?> getAssetRendererFactoryByType(String type) {
		return _typeAssetRenderFactoriesServiceTrackerMap.getService(type);
	}

	@Override
	public long[] getClassNameIds(long companyId) {
		return getClassNameIds(companyId, false);
	}

	@Override
	public long[] getClassNameIds(long companyId, boolean filterSelectable) {
		if (companyId > 0) {
			return TransformUtil.transformToLongArray(
				_filterAssetRendererFactories(companyId, filterSelectable),
				AssetRendererFactory::getClassNameId);
		}

		return TransformUtil.transformToLongArray(
			_classNameAssetRenderFactoriesServiceTrackerMap.keySet(),
			className -> {
				AssetRendererFactory<?> assetRendererFactory =
					_classNameAssetRenderFactoriesServiceTrackerMap.getService(
						className);

				return assetRendererFactory.getClassNameId();
			});
	}

	@Override
	public long[] getIndexableClassNameIds(
		long companyId, boolean filterSelectable) {

		return ArrayUtil.filter(
			getClassNameIds(companyId, filterSelectable),
			classNameId -> {
				Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
					_portal.getClassName(classNameId));

				if (indexer == null) {
					return false;
				}

				return true;
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_classNameAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<AssetRendererFactory<?>>)
					(Class<?>)AssetRendererFactory.class,
				null,
				(serviceReference, emitter) -> {
					AssetRendererFactory<?> assetRendererFactory =
						bundleContext.getService(serviceReference);

					emitter.emit(assetRendererFactory.getClassName());
				});

		_typeAssetRenderFactoriesServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext,
				(Class<AssetRendererFactory<?>>)
					(Class<?>)AssetRendererFactory.class,
				null,
				(serviceReference, emitter) -> {
					AssetRendererFactory<?> assetRendererFactory =
						bundleContext.getService(serviceReference);

					emitter.emit(assetRendererFactory.getType());
				});
	}

	@Deactivate
	protected void deactivate() {
		_classNameAssetRenderFactoriesServiceTrackerMap.close();
		_typeAssetRenderFactoriesServiceTrackerMap.close();
	}

	private List<AssetRendererFactory<?>> _filterAssetRendererFactories(
		long companyId, boolean filterSelectable) {

		List<AssetRendererFactory<?>> filteredAssetRendererFactories =
			new CopyOnWriteArrayList<>();

		for (String key :
				_classNameAssetRenderFactoriesServiceTrackerMap.keySet()) {

			AssetRendererFactory<?> assetRendererFactory =
				_classNameAssetRenderFactoriesServiceTrackerMap.getService(key);

			if (assetRendererFactory.isActive(companyId) &&
				(!filterSelectable || assetRendererFactory.isSelectable())) {

				filteredAssetRendererFactories.add(assetRendererFactory);
			}
		}

		return filteredAssetRendererFactories;
	}

	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_classNameAssetRenderFactoriesServiceTrackerMap;

	@Reference
	private Portal _portal;

	private ServiceTrackerMap<String, AssetRendererFactory<?>>
		_typeAssetRenderFactoriesServiceTrackerMap;

}