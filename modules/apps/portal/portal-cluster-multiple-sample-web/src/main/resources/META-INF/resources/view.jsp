<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ page import="com.liferay.petra.function.transform.TransformUtil" %><%@
page import="com.liferay.portal.cluster.multiple.sample.web.internal.ClusterSampleData" %><%@
page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %><%@
page import="com.liferay.portal.kernel.servlet.PortalSessionContext" %>

<%@ page import="java.util.Collections" %><%@
page import="java.util.List" %>

<%@ page import="javax.servlet.http.HttpSession" %>

<portlet:defineObjects />

<%
ClusterSampleData clusterSampleData = new ClusterSampleData();
%>

<div class="h4">Server Data:</div>

<p>Following data is from the server that generated this response:</p>

<ul>
	<li>
		<b>Computer Name:</b> <%= clusterSampleData.getComputerName() %>
	</li>
	<li>
		<b>Liferay Home:</b> <%= clusterSampleData.getLiferayHome() %>
	</li>
	<li>
		<b>Current timestamp:</b> <%= clusterSampleData.getTimestamp() %>
	</li>
</ul>

<div class="logged-in-sessions">
	<h4>Logged In Sessions with Attributes: </h4>

	<%
	List<String> sessionIds = TransformUtil.transform(PortalSessionContext.values(), HttpSession::getId);

	Collections.sort(sessionIds);

	for (String sessionId : sessionIds) {
		HttpSession httpSession = PortalSessionContext.get(sessionId);

		Object userId = httpSession.getAttribute("USER_ID");

		if (userId == null) {
			continue;
		}

		out.println(sessionId);

		List<String> attributeNamesList = Collections.list(httpSession.getAttributeNames());

		Collections.sort(attributeNamesList);

		for (String attributeName : attributeNamesList) {
			out.print(attributeName + ": ");
			out.println(JSONFactoryUtil.serialize(httpSession.getAttribute(attributeName)));
		}
	}
	%>

</div>

<div class="h4">Session Data:</div>

<%
ClusterSampleData portletSessionClusterSampleData = (ClusterSampleData)portletSession.getAttribute(ClusterSampleData.class.getName());
%>

<c:choose>
	<c:when test="<%= portletSessionClusterSampleData != null %>">
		<p>Following data is stored in the portlet session:</p>

		<ul>
			<li>
				<b>Stored Data:</b> <%= portletSessionClusterSampleData.getData() %>
			</li>
			<li>
				<b>Stored Timestamp:</b> <%= portletSessionClusterSampleData.getTimestamp() %>
			</li>
		</ul>

		<p>The data was stored by:</p>

		<ul>
			<li>
				<b>Computer Name:</b> <%= portletSessionClusterSampleData.getComputerName() %>
			</li>
			<li>
				<b>Liferay Home:</b> <%= portletSessionClusterSampleData.getLiferayHome() %>
			</li>
		</ul>
	</c:when>
	<c:otherwise>

		<%
		portletSession.setAttribute(ClusterSampleData.class.getName(), clusterSampleData);
		%>

		<p>No session data exists, generating a new one with random string: <i><%= clusterSampleData.getData() %></i></p>
	</c:otherwise>
</c:choose>