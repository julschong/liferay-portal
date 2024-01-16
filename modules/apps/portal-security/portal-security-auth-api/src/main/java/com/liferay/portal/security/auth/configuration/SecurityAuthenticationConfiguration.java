/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Julius Lee
 */
@ExtendedObjectClassDefinition(category = "security-tools")
@Meta.OCD(
	id = "com.liferay.portal.security.auth.configuration.SecurityAuthenticationConfiguration",
	localization = "content/Language",
	name = "security-authentication-configuration-name"
)
public interface SecurityAuthenticationConfiguration {

	@Meta.AD(
		deflt = "com.liferay.portal.security.auth.internal.DefaultEmailAddressValidator",
		name = "security-authentication-use-liberal-email-address-validator",
		required = false
	)
	public String emailAddressValidatorClassName();

	@Meta.AD(
		deflt = "com.liferay.portal.security.auth.internal.DefaultScreenNameValidator",
		name = "security-authentication-use-liberal-screen-name-validator",
		required = false
	)
	public String screenNameValidatorClassname();

	@Meta.AD(
		deflt = "com.liferay.portal.security.auth.internal.DefaultFullNameGenerator",
		name = "security-authentication-use-family-name-first-full-name-generator",
		required = false
	)
	public String fullNameGeneratorClassName();

}