/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.set.prototype.web.internal.model.listener;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(service = ModelListener.class)
public class LayoutSetPrototypeLayoutModelListener
	extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) {
		updateLayoutSetPrototype(layout, layout.getModifiedDate());
	}

	@Override
	public void onAfterRemove(Layout layout) {
		updateLayoutSetPrototype(layout, new Date());
	}

	@Override
	public void onAfterUpdate(Layout originalLayout, Layout layout) {
		updateLayoutSetPrototype(layout, layout.getModifiedDate());
	}

	protected void updateLayoutSetPrototype(Layout layout, Date modifiedDate) {
		if (layout == null) {
			return;
		}

		Group group = _groupLocalService.fetchGroup(layout.getGroupId());

		if ((group == null) || !group.isLayoutSetPrototype()) {
			return;
		}

		try {
			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
					group.getClassPK());

			if (layoutSetPrototype != null) {
				LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
					layoutSetPrototype.getGroupId(), true);

				if (layoutSet != null) {
					layoutSet.setModifiedDate(modifiedDate);

					_layoutSetLocalService.updateLayoutSet(layoutSet);
				}
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetPrototypeLayoutModelListener.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

}