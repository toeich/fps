package de.jworks.fpp.print.ui;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.part.NullEditorInput;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	@Override
	public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	@Override
	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setTitle("FPS :: Print :: UI");
	}
	
	@Override
	public void postWindowOpen() {
		try {
			IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
			IWorkbenchWindow window = configurer.getWindow();
			IWorkbenchPage activePage = window.getActivePage();
			activePage.openEditor(new NullEditorInput(), "de.jworks.fpp.print.layout");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
