package tracing.views;

import dialogs.GreetingMsg;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jdt.core.*;

public class MethodIndicesView extends ViewPart implements ISelectionProvider{

	Text indicesText;
	int totalMethods = 0;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "tracing.views.MethodIndicesView";

	private void showMessage(){

	}

	public int getMethodCount() {

		IProjectDescription description;
		try{
			description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path("C:\\iTrust\\.project"));

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
			if(!project.isOpen()) {
				project.open(null);
			}
			
			System.out.println("project name: " + project.getName());

			if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {

				IJavaProject javaProject = JavaCore.create(project);
				IPackageFragment[] packages = javaProject.getPackageFragments();

				// process each package
				for (IPackageFragment aPackage : packages) {

					// We will only look at the package from the source folder
					// K_BINARY would include also included JARS, e.g. rt.jar
					// only process the JAR files
					if (aPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {

						for (ICompilationUnit unit : aPackage
								.getCompilationUnits()) {

							System.out.println("--class name: "
									+ unit.getElementName());

							IType[] allTypes = unit.getAllTypes();
							for (IType type : allTypes) {

								IMethod[] methods = type.getMethods();

								for (IMethod method : methods) {
									totalMethods++;
									System.out.println("--Method name: "+ method.getElementName());
									System.out.println("Signature: "+ method.getSignature());
									System.out.println("Return Type: "+ method.getReturnType());
									System.out.println("source: "+ method.getSource());
									System.out.println("to string: "+ method.toString());
									System.out.println("new: "+ method.getPath().toString());
								}
							}
						}
					}
				}
			}
		}catch (Exception e2) { e2.printStackTrace(); }
		return totalMethods;
	}

	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public ISelection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSelection(ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPartControl(Composite parent) {

		//Set layout forum of parent composite
		parent.setLayout(new FormLayout());

		FormData formdata = new FormData();
		formdata.top=new FormAttachment(0,5);
		formdata.left = new FormAttachment(0,10);
		formdata.right = new FormAttachment(0,200);

		//Create title label
		Label titleLabel = new Label(parent,SWT.SINGLE);
		titleLabel.setText("Method Indices:");
		titleLabel.setLayoutData(formdata);

		//Create text area
		indicesText = new Text(parent,SWT.MULTI|SWT.V_SCROLL|SWT.READ_ONLY | SWT.WRAP);
		formdata = new FormData();
		formdata.top = new FormAttachment(titleLabel,10);
		formdata.bottom = new FormAttachment(titleLabel,230);
		formdata.left = new FormAttachment(0,10);
		formdata.right = new FormAttachment(0,800);
		indicesText.setLayoutData(formdata);

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void setIndicesText(String text){
		if(indicesText != null) {
			indicesText.setText(text);
		}
	}

}
