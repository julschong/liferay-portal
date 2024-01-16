/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.internal.configurator;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.security.auth.EmailAddressValidator;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.ScreenNameValidator;
import com.liferay.portal.security.auth.configuration.SecurityAuthenticationConfiguration;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Julius Lee
 */
@Component(
	configurationPid = "com.liferay.portal.security.auth.configuration.SecurityAuthenticationConfiguration",
	service = {}
)
public class SecurityAuthenticationConfigurator {

	@Activate
	@Modified
	protected void activate(
			BundleContext bundleContext, Map<String, Object> properties)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException {

		SecurityAuthenticationConfiguration
			securityAuthenticationConfiguration =
				ConfigurableUtil.createConfigurable(
					SecurityAuthenticationConfiguration.class, properties);

		_registerService(
			bundleContext, EmailAddressValidator.class,
			securityAuthenticationConfiguration.
				emailAddressValidatorClassName());
		_registerService(
			bundleContext, FullNameGenerator.class,
			securityAuthenticationConfiguration.fullNameGeneratorClassName());
		_registerService(
			bundleContext, ScreenNameValidator.class,
			securityAuthenticationConfiguration.screenNameValidatorClassname());
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrationsMap.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrationsMap.clear();
		_implClassNamesMap.clear();
	}

	private <S> void _registerService(
			BundleContext bundleContext, Class<? super S> interfaceClass,
			String implementationClassName)
		throws ClassNotFoundException, IllegalAccessException,
			   InstantiationException {

		if (_serviceRegistrationsMap.containsKey(interfaceClass)) {
			String oldImplClassName = _implClassNamesMap.get(interfaceClass);

			if (!oldImplClassName.equals(implementationClassName)) {
				ServiceRegistration<?> serviceRegistration =
					_serviceRegistrationsMap.get(interfaceClass);

				serviceRegistration.unregister();
			}
		}

		Class<S> clazz = (Class<S>)Class.forName(implementationClassName);

		ServiceRegistration<? super S> serviceRegistration =
			bundleContext.registerService(
				interfaceClass, clazz.newInstance(), null);

		_serviceRegistrationsMap.put(interfaceClass, serviceRegistration);

		_implClassNamesMap.put(interfaceClass, implementationClassName);
	}

	private final Map<Class<?>, String> _implClassNamesMap = new HashMap<>();
	private final Map<Class<?>, ServiceRegistration<?>>
		_serviceRegistrationsMap = new HashMap<>();

}