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

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public interface AssetRendererFactoryRegistry {

	public List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId);

	public List<AssetRendererFactory<?>> getAssetRendererFactories(
		long companyId, boolean filterSelectable);

	public <T> AssetRendererFactory<T> getAssetRendererFactoryByClass(
		Class<T> clazz);

	public AssetRendererFactory<?> getAssetRendererFactoryByClassName(
		String className);

	public AssetRendererFactory<?> getAssetRendererFactoryByClassNameId(
		long classNameId);

	public AssetRendererFactory<?> getAssetRendererFactoryByType(String type);

	public long[] getClassNameIds(long companyId);

	public long[] getClassNameIds(long companyId, boolean filterSelectable);

	public long[] getIndexableClassNameIds(
		long companyId, boolean filterSelectable);

}