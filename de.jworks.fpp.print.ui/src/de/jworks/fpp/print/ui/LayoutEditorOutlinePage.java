package de.jworks.fpp.print.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class LayoutEditorOutlinePage extends ContentOutlinePage {

	private LayoutEditor layoutEditor;

	public LayoutEditorOutlinePage(LayoutEditor layoutEditor) {
		this.layoutEditor = layoutEditor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		getTreeViewer().setContentProvider(new BaseWorkbenchContentProvider());
		getTreeViewer().setLabelProvider(new WorkbenchLabelProvider());
		getTreeViewer().setInput(new WorkbenchAdapter() {
			@Override
			public String getLabel(Object object) {
				return "a";
			}
			@Override
			public Object[] getChildren(Object object) {
				return new Object[] {
						new WorkbenchAdapter() {
							public String getLabel(Object object) {
								return "b";
							};
						}
				};
			}
		});
	}
	
}
