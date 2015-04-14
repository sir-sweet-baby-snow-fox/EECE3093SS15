package tracing.views;

import indexer.CodeTokenizer;

import java.util.HashMap;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.jdt.core.*;
import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.jdt.ui.JavaUI;
//import org.eclipse.jdt.*;


/**
 * @date April 7, 2015
 * @author Ricky
 * @description
 * 	Eclipse Plug-in view used for displaying the indexed methods in the iTrust project. Will initially display
 * processing time, but after a method is selected in the project browser, the indexed method will be displayed.
 */
public class MethodIndicesView extends ViewPart implements ISelectionProvider{

	private Text indicesText;
	private int methodCount = 0;
	private double indexDurationTime = 0;
	private HashMap<String, String> methodHash;
	
	public static final double NANOSEC_SEC_CONVERT = 1000000000.0;

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "tracing.views.MethodIndicesView";
	
	/**
	 * Default constructor.
	 */
	public MethodIndicesView() {
		methodHash = new HashMap<String, String>();
	}
	
	/**
	 * @return Number of methods indexed. Will return zero if no project was processed.
	 */
	public int getMethodCount() {
		return methodCount;
	}

	/**
	 * 	Attempts to open the iTrust project and process the methods. iTrust project is assumed to be a C:\iTrust\.project.
	 * Indexes the methods and stores the method signature and indexed text into a hashmap for retrieval later. Also, times
	 * the total indexing time for display purposes when no method is chosen. 
	 */
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
							
							IType[] allTypes = unit.getAllTypes();
							for (IType type : allTypes) {

								IMethod[] methods = type.getMethods(); //Retrieve methods

								for (IMethod method : methods) {
									
									methodCount++;
									
									// add method to indices list
									CodeTokenizer ct = new CodeTokenizer();
									ct.tokenize(method.getSource());
									String methodIndex = ct.getTokensAsString();
									methodHash.put(method.getKey(), methodIndex);
								}
							}
						}
					}
				}
			}
		}catch (Exception e2) { e2.printStackTrace(); }
		
		indexDurationTime = (System.nanoTime() - indexStartTime) / NANOSEC_SEC_CONVERT;
		String defaultText = "Indexing time of " + methodCount + " methods is: " + String.format("%.2f", indexDurationTime) + " seconds.";
		setIndicesText(defaultText);
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
		titleLabel.setText("Method Index:");
		titleLabel.setLayoutData(formdata);

		//Create text area
		indicesText = new Text(parent,SWT.MULTI|SWT.V_SCROLL|SWT.READ_ONLY | SWT.WRAP);
		formdata = new FormData();
		formdata.top = new FormAttachment(titleLabel,5);
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
		IPackagesViewPart packageExplorer = (IPackagesViewPart)activePage.findView(JavaUI.ID_PACKAGES);
		
		if (packageExplorer != null)
		{
			TreeViewer treeView = packageExplorer.getTreeViewer();
		
			// May want to change to selection listener?
			treeView.addDoubleClickListener(new IDoubleClickListener(){
				@Override
			    public void doubleClick(DoubleClickEvent event) {
					
					IStructuredSelection selected = (IStructuredSelection) treeView.getSelection();
					if (!selected.isEmpty())
					{
						Object element = selected.getFirstElement();
						
						if (element instanceof IMethod)
						{
							IMethod selectedMethod = ((IMethod) element);

							setIndicesText(methodHash.get(selectedMethod.getKey()));
						}
					}
				}
				
			});
		}
				
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * Set the textbox text.
	 * 
	 * @param text Text to display in the text box.
	 */
	public void setIndicesText(String text){
		if(indicesText != null) {
			indicesText.setText(text);
		}
	}
	
	/**
	 * 	To use this, pass the id of the desired view. Each view should have a static string that is the id. For example,
	 * RequirementsView has a public static ID field that can always be access like "RequirementsView.ID". Then, cast the
	 * return to the class you want. For example:
	 * 
	 * RequirementsView rv = (RequirementsView) getViewId(RequirementsView.ID);
	 * 
	 * @param id Id of the Eclipse Plugin View that is desired.
	 * @return An IViewPart superclass that the view inherits.
	 */
	private IViewPart getView(String id) {
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return view;
	}
	

}
