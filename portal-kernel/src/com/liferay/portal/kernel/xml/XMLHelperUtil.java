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

package com.liferay.portal.kernel.xml;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.IOException;

/**
 * @author Julius Lee
 */
public class XMLHelperUtil {

	public static String formatXML(Document document) {
		return _xmlHelper.formatXML(document);
	}

	public static String formatXML(String xml) {
		return _xmlHelper.formatXML(xml);
	}

	public static String fromCompactSafe(String xml) {
		return _xmlHelper.fromCompactSafe(xml);
	}

	public static String stripInvalidChars(String xml) {
		return _xmlHelper.stripInvalidChars(xml);
	}

	public static String toCompactSafe(String xml) {
		return _xmlHelper.toCompactSafe(xml);
	}

	public static String toString(Node node) throws IOException {
		return _xmlHelper.toString(node);
	}

	public static String toString(Node node, String indent) throws IOException {
		return _xmlHelper.toString(node, indent);
	}

	public static String toString(
			Node node, String indent, boolean expandEmptyElements)
		throws IOException {

		return _xmlHelper.toString(node, indent, expandEmptyElements);
	}

	public static String toString(
			Node node, String indent, boolean expandEmptyElements,
			boolean trimText)
		throws IOException {

		return _xmlHelper.toString(node, indent, expandEmptyElements, trimText);
	}

	public static String toString(String xml)
		throws DocumentException, IOException {

		return _xmlHelper.toString(xml);
	}

	public static String toString(String xml, String indent)
		throws DocumentException, IOException {

		return _xmlHelper.toString(xml, indent);
	}

	private static volatile XMLHelper _xmlHelper =
		ServiceProxyFactory.newServiceTrackedInstance(
			XMLHelper.class, XMLHelperUtil.class, "_xmlHelper", false);

}