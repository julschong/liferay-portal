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

package com.liferay.util.xml;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class XMLSafeReader extends UnsyncStringReader {

	public XMLSafeReader(String xml) {
		super(_escapeCDATAClosingCharacters(_fixProlog(xml)));
	}

	private static String _escapeCDATAClosingCharacters(String xml) {

		// If the closing token of a CDATA container is found inside the CDATA
		// container, split the CDATA container into two separate CDATA
		// containers. This is generally accepted method of "escaping" for this
		// case since there is no real way to escape those characters. See
		// LPS-85393 for more information.

		if (xml == null) {
			return null;
		}

		StringBundler sb = new StringBundler();

		int prev = 0;
		int start = xml.indexOf("]]>");

		while (start > - 1 && start < xml.length() - 1) {
			sb.append(xml.substring(prev, start));

			int end = start + 3;

			while (end < xml.length() - 1) {
				if (!Character.isWhitespace(xml.charAt(end))) {
					break;
				}

				end++;
			}

			if (end < xml.length() - 1) {
				if (xml.charAt(end) == CharPool.LESS_THAN) {
					sb.append("[$SPECIAL_CHARACTER$]");
					end++;
				} else {
					sb.append(xml.substring(start, end));
				}
			}

			prev = end;
			start = xml.indexOf("]]>", end);
		}

		sb.append(xml.substring(prev));

		xml = sb.toString();

		xml = StringUtil.replace(xml, "]]>", "]]]]><![CDATA[>");

		return StringUtil.replace(xml, "[$SPECIAL_CHARACTER$]", "]]><");
	}

	private static String _fixProlog(String xml) {

		// LEP-1921

		if (xml != null) {
			int pos = xml.indexOf(CharPool.LESS_THAN);

			if (pos > 0) {
				xml = xml.substring(pos);
			}
		}

		return xml;
	}

}