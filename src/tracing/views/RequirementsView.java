package tracing.views;


import indexer.Indexer;
import indexer.RequirementsTokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.io.File;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import dialogs.GreetingMsg;



/**
 * This sample class demonstrates how to plug-in a new
 * workbench view. The view shows data obtained from the
 * model. The sample creates a dummy model on the fly,
 * but a real implementation would connect to the model
 * available either in this or another plug-in (e.g. the workspace).
 * The view is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model
 * objects should be presented in the view. Each
 * view can present the same model objects using
 * different labels and icons, if needed. Alternatively,
 * a single label provider can be shared between views
 * in order to ensure that objects of the same type are
 * presented in the same way everywhere.
 * <p>
 */

public class RequirementsView extends ViewPart implements ISelectionProvider{
	
	private ISelection selection;
	private ComboViewer comboViewer;
	private Indexer indexer = null;
	//private String test = "this is a test string";
	private String resourcePath;
	File[] resourceFiles;
	ArrayList<File> validFiles = new ArrayList<File>();
	private double durationTime = 0;
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "tracing.views.RequirementsView";
	
	private void showMessage(){
		//MessageDialog.openInformation(new Shell(), "Testhello", "Hello, Eclipse world");
		
		GreetingMsg msg = new GreetingMsg(new Shell(), SWT.BORDER | SWT.WRAP);
		msg.open();
		
	}
	
	/**
	 * The constructor.
	 */
	public RequirementsView() {
		resourcePath = "";
	}
	
	public void setResourcePath(String input) {
		//return acronymFilePath;
		resourcePath = input;
	}
	
	public void setIndexer(Indexer newIndexer) {
		indexer = newIndexer;
		durationTime = indexer.GetIndexTime();
	}
	
	
	public void updateComboBox() {
		final Combo combo = comboViewer.getCombo();
		combo.removeAll();
		combo.add("Choose Use Case");
		
		//Retrieve use case files from resource directory.
		if(!resourcePath.isEmpty()) {
			File folder = new File(resourcePath);
			resourceFiles = folder.listFiles();
			for (int i = 0; i < resourceFiles.length; i++) {
				if (resourceFiles[i].isFile()) {
					//Remove file extension from use case name
					String fileName = resourceFiles[i].getName();
					
					//Make sure the file is a .txt
					if (!fileName.endsWith(".txt"))
						continue; // go to the next file
					
					int lastPeriodIndex = fileName.lastIndexOf('.');
					String useCaseName = fileName.substring(0, lastPeriodIndex);
					
					combo.add(useCaseName);
					
					validFiles.add(resourceFiles[i]);
				}
			}
		}
		
				combo.select(0); //Default choice is no file selected
		
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		//Set layout forum of parent composite
		parent.setLayout(new FormLayout());
		
		//Create a drop box
		comboViewer = new ComboViewer(parent,SWT.NONE|SWT.DROP_DOWN);
		final Combo combo = comboViewer.getCombo();
		
		//Fill the combo box with the correct file data.
		updateComboBox();
		
		//Set combo position
		FormData formdata = new FormData();
		formdata.top=new FormAttachment(0,5);
		formdata.left = new FormAttachment(0,10);
		formdata.right = new FormAttachment(0,290);
		combo.setLayoutData(formdata);
		
		//Set text position
		final Text text = new Text(parent,SWT.MULTI|SWT.V_SCROLL| SWT.READ_ONLY | SWT.WRAP);
		formdata = new FormData();
		formdata.top=new FormAttachment(combo,10);
		formdata.bottom = new FormAttachment(combo,600);
		formdata.left = new FormAttachment(0,5);
		formdata.right = new FormAttachment(0,355);
		text.setLayoutData(formdata);
		
		combo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				RequirementsIndicesView reqIndView = getRequirementsView("tracing.views.RequirementsIndicesView");
				
				final Combo combo = comboViewer.getCombo();
				
				//Fill text with the correct information.
				//If no use case is selected, display indexing time.
				//Otherwise, display the content of the selected file.
				if(combo.getSelectionIndex()==0) {
				
					// Force durationTime to display only to four decimal places
					DecimalFormat df = new DecimalFormat("##.####");
					text.setText("Indexing time of " + (combo.getItemCount()-1) + " requirement(s) is: " + df.format(durationTime) + " seconds.");
					reqIndView.setIndicesText("");
				}  else {
					//User has selected a use case associated with a file name.
					try {
						//Display original file contents
						StringBuilder s = new StringBuilder();
						int fileIndex = combo.getSelectionIndex() - 1; //First selection is not a file.
						String useCaseFileName = validFiles.get(fileIndex).toString();
						
						for (String line : Files.readAllLines(Paths.get(useCaseFileName))) {
							s.append(line + "\n");
						}
						text.setText(s.toString());
						
						//Display process content
						String indexContent = indexer.getIndexFile(fileIndex);
						reqIndView.setIndicesText(indexContent.toString());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						text.setText(e1.getMessage());
					}
					
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection comboSelection = event.getSelection();
				setSelection(comboSelection);
			}
			
		});
		
		//Attempt to open other views, if they aren't already displayed
		RequirementsIndicesView reqIndView = (RequirementsIndicesView) getView(RequirementsIndicesView.ID);
		if(reqIndView == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(RequirementsIndicesView.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		MethodIndicesView methodIndView = (MethodIndicesView) getView(MethodIndicesView.ID);
		if(methodIndView == null) {
			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(MethodIndicesView.ID);
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private RequirementsIndicesView getRequirementsView(String id) {
		RequirementsIndicesView reqIndView = (RequirementsIndicesView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return reqIndView;
	}
	
	private IViewPart getView(String id) {
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return view;
	}
	
	@Override
	public void setSelection(ISelection selection) {
		this.selection = selection;
		SelectionChangedEvent event = new SelectionChangedEvent(comboViewer,selection);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		
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
	
	public void setDefaultText()
	{
		// This is a bit of a kludge (to me, at least), but sends selection event
		// when called. Ideally, this method will ONLY ever be used in GreetingMsg
		// in order to force the default text box to update in order to display the
		// indexing time.
		comboViewer.getCombo().notifyListeners(SWT.Selection, new Event());
	}
	
}
