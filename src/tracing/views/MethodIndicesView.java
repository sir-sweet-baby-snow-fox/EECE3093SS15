package tracing.views;

import dialogs.GreetingMsg;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.jdt.ui.JavaUI;
//import org.eclipse.jdt.*;


public class MethodIndicesView extends ViewPart implements ISelectionProvider{

	private Text indicesText;
	private int methodCount = 0;
	private double indexDurationTime = 0;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "tracing.views.MethodIndicesView";
	
	public int getMethodCount() {
		return methodCount;
	}

	public void indexMethods() {
		
		double indexStartTime = System.nanoTime();

		IProjectDescription description;
		try{
			//Assume path is at "C:\\iTrust\\.project"
			description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path("C:\\iTrust\\.project"));

			//Get the iTrust project, if it isn't open, open it.
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

						for (ICompilationUnit unit : aPackage.getCompilationUnits()) {

							System.out.println("--class name: "+ unit.getElementName());

							IType[] allTypes = unit.getAllTypes();
							for (IType type : allTypes) {

								IMethod[] methods = type.getMethods(); //Retrieve methods

								for (IMethod method : methods) {
									methodCount++;
									//methodIndexer.indexMethod(method);
									System.out.println("--Method name: "+ method.getElementName());
								}
							}
						}
					}
				}
			}
		}catch (Exception e2) { e2.printStackTrace(); }
		
		indexDurationTime = (System.nanoTime() - indexStartTime) / 1000000000.0;
		String indicesText = "Indexing time of " + methodCount + " methods is: " + String.format("%.2f", indexDurationTime) + " seconds.";
		setIndicesText(indicesText);
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
		
		//Attempt to open other views, if they aren't already displayed
		RequirementsIndicesView riv = (RequirementsIndicesView) getView(RequirementsIndicesView.ID);
		if(riv == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(RequirementsIndicesView.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		RequirementsView rv = (RequirementsView) getView(RequirementsView.ID);
		if(rv == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(RequirementsView.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Add a new double click listener to the package explorer tree
		IWorkbenchPage activePage = getSite().getWorkbenchWindow().getActivePage();
		IPackagesViewPart packExpl = (IPackagesViewPart)activePage.findView(JavaUI.ID_PACKAGES);
		if (packExpl != null)
		{
			TreeViewer treeView = packExpl.getTreeViewer();
		
			// May want to change to selection listener?
			treeView.addDoubleClickListener(new IDoubleClickListener(){
				@Override
			    public void doubleClick(DoubleClickEvent event) {
			        // Update method indices view text. Probably doesn't need to be it's own method.
					updateText();
				}
				
			});
		}
				
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
	
	private IViewPart getView(String id) {
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return view;
	}
	
	public void updateText()
	{
		if (indicesText != null)
		{
			indicesText.setText("Double Click");
		}
	}

}
