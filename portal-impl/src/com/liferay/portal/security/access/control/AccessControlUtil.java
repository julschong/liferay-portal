/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.access.control;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.internal.security.access.control.AllowedIPAddressesValidator;
import com.liferay.portal.kernel.internal.security.access.control.AllowedIPAddressesValidatorFactory;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.auth.verifier.AuthVerifierResult;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.security.auth.AuthVerifierPipeline;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tomas Polesovsky
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AccessControlUtil {

	public static AccessControlContext getAccessControlContext() {
		return _accessControlContext.get();
	}

	public static void initAccessControlContext(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Map<String, Object> settings) {

		AccessControlContext accessControlContext = getAccessControlContext();

		if (accessControlContext != null) {
			throw new IllegalStateException(
				"Authentication context is already initialized");
		}

		accessControlContext = new AccessControlContext();

		accessControlContext.setRequest(httpServletRequest);
		accessControlContext.setResponse(httpServletResponse);

		Map<String, Object> accessControlContextSettings =
			accessControlContext.getSettings();

		accessControlContextSettings.putAll(settings);

		setAccessControlContext(accessControlContext);
	}

	public static void initContextUser(long userId) throws AuthException {
		try {
			User user = UserLocalServiceUtil.getUser(userId);

			CompanyThreadLocal.setCompanyId(user.getCompanyId());

			PrincipalThreadLocal.setName(userId);

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			AccessControlThreadLocal.setRemoteAccess(false);
		}
		catch (Exception exception) {
			throw new AuthException(exception.getMessage(), exception);
		}
	}

	public static boolean isAccessAllowed(
		HttpServletRequest httpServletRequest, Set<String> hostsAllowed) {

		if (hostsAllowed.isEmpty()) {
			return true;
		}

		String remoteAddr = httpServletRequest.getRemoteAddr();

		for (String hostAllowed : hostsAllowed) {
			AllowedIPAddressesValidator allowedIPAddressesValidator =
				AllowedIPAddressesValidatorFactory.create(hostAllowed);

			if (allowedIPAddressesValidator.isAllowedIPAddress(remoteAddr)) {
				return true;
			}
		}

		Set<String> computerAddresses = PortalUtil.getComputerAddresses();

		if (computerAddresses.contains(remoteAddr) &&
			hostsAllowed.contains(_SERVER_IP)) {

			return true;
		}

		return false;
	}

	public static void setAccessControlContext(
		AccessControlContext accessControlContext) {

		_accessControlContext.set(accessControlContext);
	}

	public static AuthVerifierResult.State verifyRequest()
		throws PortalException {

		AuthVerifierResult authVerifierResult = null;

		AccessControlContext accessControlContext = getAccessControlContext();

		Map<String, Object> settings = accessControlContext.getSettings();

		if (!settings.containsKey(AuthVerifierPipeline.class.getName())) {
			AuthVerifierPipeline portalAuthVerifierPipeline =
				AuthVerifierPipeline.getPortalAuthVerifierPipeline();

			authVerifierResult = portalAuthVerifierPipeline.verifyRequest(
				accessControlContext);
		}
		else {
			AuthVerifierPipeline authVerifierPipeline =
				(AuthVerifierPipeline)settings.get(
					AuthVerifierPipeline.class.getName());

			authVerifierResult = authVerifierPipeline.verifyRequest(
				accessControlContext);

			if ((authVerifierResult.getState() ==
					AuthVerifierResult.State.NOT_APPLICABLE) ||
				(authVerifierResult.getState() ==
					AuthVerifierResult.State.UNSUCCESSFUL)) {

				AuthVerifierPipeline portalAuthVerifierPipeline =
					AuthVerifierPipeline.getPortalAuthVerifierPipeline();

				authVerifierResult = portalAuthVerifierPipeline.verifyRequest(
					accessControlContext);
			}
		}

		Map<String, Object> authVerifierResultSettings =
			authVerifierResult.getSettings();

		if (authVerifierResultSettings != null) {
			settings.putAll(authVerifierResultSettings);
		}

		accessControlContext.setAuthVerifierResult(authVerifierResult);

		return authVerifierResult.getState();
	}

	private AccessControlUtil() {
	}

	private static final String _SERVER_IP = "SERVER_IP";

	private static final ThreadLocal<AccessControlContext>
		_accessControlContext = new CentralizedThreadLocal<>(
			AccessControlUtil.class + "._accessControlContext");

}