package de.jworks.fpp.print.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class LayoutEditor extends MultiPageEditorPart {

	public static final String ID = "de.jworks.fpp.print.layout"; //$NON-NLS-1$

	public LayoutEditor() {
	}

	@Override
	protected void createPages() {
		int layoutPageId = addPage(new LayoutViewer(getContainer(), SWT.NONE));
		setPageText(layoutPageId, "Layout");
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IContentOutlinePage.class) {
			return new LayoutEditorOutlinePage(this);
		}
		return super.getAdapter(adapter);
	}
	
}
