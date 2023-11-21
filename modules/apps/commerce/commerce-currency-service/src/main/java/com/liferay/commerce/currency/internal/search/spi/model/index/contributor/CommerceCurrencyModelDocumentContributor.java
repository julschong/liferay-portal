/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.internal.search.spi.model.index.contributor;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mahmoud Azzam
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.currency.model.CommerceCurrency",
	service = ModelDocumentContributor.class
)
public class CommerceCurrencyModelDocumentContributor
	implements ModelDocumentContributor<CommerceCurrency> {

	@Override
	public void contribute(
		Document document, CommerceCurrency commerceCurrency) {

		document.addKeyword(CPField.ACTIVE, commerceCurrency.isActive());
		document.addText(CPField.CODE, commerceCurrency.getCode());
		document.addNumberSortable(
			Field.PRIORITY, commerceCurrency.getPriority());

		String[] languageIds = LocalizationUtil.getAvailableLanguageIds(
			commerceCurrency.getName());

		for (String languageId : languageIds) {
			String name = commerceCurrency.getName(languageId);

			document.addText(Field.CONTENT, name);
			document.addText(
				LocalizationUtil.getLocalizedName(Field.NAME, languageId),
				name);

			String commerceCurrencyDefaultLanguageId =
				LocalizationUtil.getDefaultLanguageId(
					commerceCurrency.getName());

			if (languageId.equals(commerceCurrencyDefaultLanguageId)) {
				document.addText(Field.NAME, name);
				document.addText("defaultLanguageId", languageId);
			}
		}
	}

}