package tracing.views;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;
import java.io.File;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;



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
	private Hashtable<String, String> acronymHashTable;
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "tracing.views.RequirementsView";
	
	/**
	 * The constructor.
	 */
	public RequirementsView() {
		
	}
	
	/**
	 * Returns the restore acronym form of the inputAcronym, or the inputAcronym
	 * restored value can be found in the list.
	 * @param inputAcronym	Acronym to be restored
	 * @param acronymMap 	The mapping to be used to restore the acronym
	 * @return				The restored version of the acronym, or the input if no mapping is found.
	 */
	public String fromAcronym(Hashtable<String, String> acronymHashtable, String inputAcronym) {
		if(acronymHashtable.containsKey(inputAcronym)) {
			return acronymHashtable.get(inputAcronym);
		} else {
			return inputAcronym;
		}
	}
	
	private Hashtable<String, String> createAcronymMap(File acronymListFile) {
		Hashtable<String, String> newHashtable = new Hashtable<String, String>();
		System.out.println("hi");
		//User has selected a use case associated with a file name.
		try {
			for (String line : Files.readAllLines(acronymListFile.toPath())) {
				System.out.println(line);
				String[] splitLine = line.split(":");
				for(int i = 0; i < splitLine.length; i++) {
					System.out.println(splitLine[i]);
				}
				System.out.println();
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
		}
		return newHashtable;
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
		combo.add("Choose Use Case");
		
		//Retrieve use case files from resource directory.
		final String resourceDirectory = "C:\\Users\\Ricky\\workspace\\Lab 1\\src\\Resource";
		File folder = new File(resourceDirectory);
		File[] resourceFiles = folder.listFiles();

		//Fill combo box with file names.
		for (int i = 0; i < resourceFiles.length; i++) {
			if (resourceFiles[i].isFile()) {
				combo.add(resourceFiles[i].getName());
			}
		}
		
		combo.select(0); //Default choice is no file selected
		
		//Setup acronym mapper
		String acronymListFilePath = "C:\\Users\\Ricky\\workspace\\Lab 1\\src\\Acronym_List.txt";
		File acronymListFile = new File(acronymListFilePath);
		acronymHashTable = createAcronymMap(acronymListFile);
		
		//Set combo position
		FormData formdata = new FormData();
		formdata.top=new FormAttachment(0,5);
		formdata.left = new FormAttachment(0,10);
		formdata.right = new FormAttachment(0,290);
		combo.setLayoutData(formdata);
		
		//Set text position
		final Text text = new Text(parent,SWT.MULTI|SWT.V_SCROLL|SWT.H_SCROLL|SWT.READ_ONLY);
		formdata = new FormData();
		formdata.top  =new FormAttachment(combo,10);
		formdata.bottom = new FormAttachment(combo,600);
		formdata.left = new FormAttachment(0,5);
		formdata.right = new FormAttachment(0,355);
		text.setLayoutData(formdata);
		//set text content
		text.setText("Indexing time of X requirement(s) is: Y seconds.");
		
		combo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Fill text with the correct information.
				//If no use case is selected, display indexing time.
				//Otherwise, display the content of the selected file.
				if(combo.getSelectionIndex()==0)
					text.setText("Indexing time of X requirement(s) is: Y seconds.");
				else {
					//User has selected a use case associated with a file name.
					try {
						StringBuilder s = new StringBuilder();
						String useCaseFileName = resourceDirectory + "/" + combo.getText();
						for (String line : Files.readAllLines(Paths.get(useCaseFileName))) {
							
							//TODO: Do some processing on the current line of text
							
							s.append(line + "\n");
						}
						text.setText(s.toString());
					} catch (IOException e1) {
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
}