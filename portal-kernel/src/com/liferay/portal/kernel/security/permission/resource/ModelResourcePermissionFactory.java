/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.internal.security.permission.resource.PermissionCacheKey;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionFactory {

	public static <T extends GroupedModel> ModelResourcePermission<T> create(
		Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		ModelResourcePermissionConfigurator<T>
			modelResourcePermissionConfigurator) {

		return create(
			modelClass, primKeyToLongFunction, getModelUnsafeFunction,
			portletResourcePermission, modelResourcePermissionConfigurator,
			UnaryOperator.identity());
	}

	public static <T extends GroupedModel> ModelResourcePermission<T> create(
		Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		ModelResourcePermissionConfigurator<T>
			modelResourcePermissionConfigurator,
		UnaryOperator<String> actionIdMapper) {

		List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics =
			new ArrayList<>();

		ModelResourcePermissionDefinition<T> modelResourcePermissionDefinition =
			new DefaultModelResourcePermissionDefinition<>(
				modelClass, primKeyToLongFunction, getModelUnsafeFunction,
				portletResourcePermission, actionIdMapper);

		ModelResourcePermission<T> modelResourcePermission =
			new DefaultModelResourcePermission<>(
				modelResourcePermissionDefinition,
				modelResourcePermissionLogics);

		modelResourcePermissionConfigurator.
			configureModelResourcePermissionLogics(
				modelResourcePermission, modelResourcePermissionLogics::add);

		return modelResourcePermission;
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClassedModel> ModelResourcePermission<T>
		getInstance(
			Class<? extends BaseService> declaringServiceClass,
			String fieldName, Class<T> modelClass) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			ModelResourcePermission.class, declaringServiceClass, fieldName,
			"(model.class.name=" + modelClass.getName() + ")", true);
	}

	@FunctionalInterface
	public interface ModelResourcePermissionConfigurator
		<T extends GroupedModel> {

		public void configureModelResourcePermissionLogics(
			ModelResourcePermission<T> modelResourcePermission,
			Consumer<ModelResourcePermissionLogic<T>> consumer);

	}

	private static class DefaultModelResourcePermission<T extends GroupedModel>
		implements ModelResourcePermission<T> {

		public DefaultModelResourcePermission(
			ModelResourcePermissionDefinition<T>
				modelResourcePermissionDefinition,
			List<ModelResourcePermissionLogic<T>>
				modelResourcePermissionLogics) {

			_modelResourcePermissionDefinition =
				modelResourcePermissionDefinition;
			_modelResourcePermissionLogics = modelResourcePermissionLogics;

			Class<T> modelClass =
				modelResourcePermissionDefinition.getModelClass();

			_modelName = modelClass.getName();
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {

			if (!contains(permissionChecker, primaryKey, actionId)) {
				throw new PrincipalException.MustHavePermission(
					permissionChecker, _modelName, primaryKey, actionId);
			}
		}

		@Override
		public void check(
				PermissionChecker permissionChecker, T model, String actionId)
			throws PortalException {

			if (!contains(permissionChecker, model, actionId)) {
				throw new PrincipalException.MustHavePermission(
					permissionChecker, _modelName,
					_modelResourcePermissionDefinition.getPrimaryKey(model),
					actionId);
			}
		}

		@Override
		public boolean contains(
				PermissionChecker permissionChecker, long primaryKey,
				String actionId)
			throws PortalException {

			Map<Object, Object> permissionChecksMap =
				permissionChecker.getPermissionChecksMap();

			PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
				_modelName, primaryKey, actionId);

			Boolean contains = (Boolean)permissionChecksMap.get(
				permissionCacheKey);

			if (contains == null) {
				contains = _contains(
					permissionChecker,
					_modelResourcePermissionDefinition.getModel(primaryKey),
					actionId);

				permissionChecksMap.put(permissionCacheKey, contains);
			}

			return contains;
		}

		@Override
		public boolean contains(
				PermissionChecker permissionChecker, T model, String actionId)
			throws PortalException {

			Map<Object, Object> permissionChecksMap =
				permissionChecker.getPermissionChecksMap();

			PermissionCacheKey permissionCacheKey = new PermissionCacheKey(
				_modelName,
				_modelResourcePermissionDefinition.getPrimaryKey(model),
				actionId);

			Boolean contains = (Boolean)permissionChecksMap.get(
				permissionCacheKey);

			if (contains == null) {
				contains = _contains(permissionChecker, model, actionId);

				permissionChecksMap.put(permissionCacheKey, contains);
			}

			return contains;
		}

		@Override
		public String getModelName() {
			return _modelName;
		}

		@Override
		public PortletResourcePermission getPortletResourcePermission() {
			return _modelResourcePermissionDefinition.
				getPortletResourcePermission();
		}

		private boolean _contains(
				PermissionChecker permissionChecker, T model, String actionId)
			throws PortalException {

			actionId = _modelResourcePermissionDefinition.mapActionId(actionId);

			for (ModelResourcePermissionLogic<T> modelResourcePermissionLogic :
					_modelResourcePermissionLogics) {

				Boolean contains = modelResourcePermissionLogic.contains(
					permissionChecker, _modelName, model, actionId);

				if (contains != null) {
					return contains;
				}
			}

			String primKey = String.valueOf(
				_modelResourcePermissionDefinition.getPrimaryKey(model));

			if (permissionChecker.hasOwnerPermission(
					model.getCompanyId(), _modelName, primKey,
					model.getUserId(), actionId)) {

				return true;
			}

			return permissionChecker.hasPermission(
				model.getGroupId(), _modelName, primKey, actionId);
		}

		private final String _modelName;
		private final ModelResourcePermissionDefinition<T>
			_modelResourcePermissionDefinition;
		private final List<ModelResourcePermissionLogic<T>>
			_modelResourcePermissionLogics;

	}

	private static class DefaultModelResourcePermissionDefinition
		<T extends GroupedModel>
			implements ModelResourcePermissionDefinition<T> {

		@Override
		public T getModel(long primaryKey) throws PortalException {
			return _getModelUnsafeFunction.apply(primaryKey);
		}

		@Override
		public Class<T> getModelClass() {
			return _modelClass;
		}

		@Override
		public PortletResourcePermission getPortletResourcePermission() {
			return _portletResourcePermission;
		}

		@Override
		public long getPrimaryKey(T t) {
			return _primKeyToLongFunction.applyAsLong(t);
		}

		@Override
		public String mapActionId(String actionId) {
			return _actionIdMapper.apply(actionId);
		}

		@Override
		public void registerModelResourcePermissionLogics(
			ModelResourcePermission<T> modelResourcePermission,
			Consumer<ModelResourcePermissionLogic<T>> logicConsumer) {
		}

		private DefaultModelResourcePermissionDefinition(
			Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
			UnsafeFunction<Long, T, ? extends PortalException>
				getModelUnsafeFunction,
			PortletResourcePermission portletResourcePermission,
			UnaryOperator<String> actionIdMapper) {

			_modelClass = Objects.requireNonNull(modelClass);
			_primKeyToLongFunction = Objects.requireNonNull(
				primKeyToLongFunction);
			_getModelUnsafeFunction = Objects.requireNonNull(
				getModelUnsafeFunction);
			_portletResourcePermission = portletResourcePermission;
			_actionIdMapper = Objects.requireNonNull(actionIdMapper);
		}

		private final UnaryOperator<String> _actionIdMapper;
		private final UnsafeFunction<Long, T, ? extends PortalException>
			_getModelUnsafeFunction;
		private final Class<T> _modelClass;
		private final PortletResourcePermission _portletResourcePermission;
		private final ToLongFunction<T> _primKeyToLongFunction;

	}

}