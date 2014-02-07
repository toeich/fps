package de.jworks.fpp.print.ui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PrintPerspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView("de.jworks.fpp.print.explorer", IPageLayout.LEFT, 0.25f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("org.eclipse.ui.views.PropertySheet", IPageLayout.BOTTOM, 0.75f, IPageLayout.ID_EDITOR_AREA);
		layout.addView("org.eclipse.ui.views.ContentOutline", IPageLayout.RIGHT, 0.7f, IPageLayout.ID_EDITOR_AREA);
	}

}
