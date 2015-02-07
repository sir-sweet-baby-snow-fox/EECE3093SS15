package tracing.views;


import indexer.Indexer;
import indexer.Tokenizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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
	private Indexer indexer = null;
	private String test = "this is a test string";
	private String resourcePath;
	File[] resourceFiles;
	private long durationTime = 0;
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "tracing.views.RequirementsView";
	
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
					int lastPeriodIndex = fileName.lastIndexOf('.');
					String useCaseName = fileName.substring(0, lastPeriodIndex);
					combo.add(useCaseName);
				}
			}
		}
		
		// TODO: Remove this when we actually load the files...This is just for testing
		//combo.add("TokenizerTest");
		//combo.add("StopWordRemovalTest");
		
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
		final Text text = new Text(parent,SWT.MULTI|SWT.V_SCROLL| SWT.H_SCROLL | SWT.READ_ONLY);
		formdata = new FormData();
		formdata.top=new FormAttachment(combo,10);
		formdata.bottom = new FormAttachment(combo,600);
		formdata.left = new FormAttachment(0,5);
		formdata.right = new FormAttachment(0,355);
		text.setLayoutData(formdata);
		
		//set text content
		//text.setText("Indexing time of X requirement(s) is: Y seconds.");
		text.setText("Indexing time of " + combo.getItemCount() + " requirement(s) is: " + durationTime + " seconds.");
		
		combo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				RequirementsIndicesView riv = getRequirementsView("tracing.views.RequirementsIndicesView");
				
				final Combo combo = comboViewer.getCombo();
				
				//Fill text with the correct information.
				//If no use case is selected, display indexing time.
				//Otherwise, display the content of the selected file.
				if(combo.getSelectionIndex()==0)
					text.setText("Indexing time of " + (combo.getItemCount()-1) + " requirement(s) is: " + durationTime + " seconds.");
				else if (combo.getText().equals("TokenizerTest")) {
					// TODO: Remove this and run tokenizer/indexer on whatever file is selected but
					// 		 this at least shows how to tokenize and how to set text on the other view
					System.out.println("TokenizerTest");
					Tokenizer t = new Tokenizer();
					String[] parts = t.TokenizeString("Thi:s ha's a _lot of' T!hi%$n\ngs$ w%^234Ng");
					StringBuilder sb = new StringBuilder();
					for (String part : parts)
						sb.append(part + " ");
					
					riv.setIndicesText(sb.toString());
				}
				else if (combo.getText().equals("StopWordRemovalTest")) {
					System.out.println("StopWordRemovalTest");
//					Tokenizer t = new Tokenizer();
//					String[] parts = t.TokenizeString("The BOY had a cat and a dog");
//					try {
//						String[] cleanParts = t.RemoveStopWords(resourceDirectory + "/Stop_Word_List.txt", parts);
//						text.setText(Arrays.toString(cleanParts));
//					} catch (IOException e1) {
//						text.setText("");
//					}
				}
				else{
					System.out.println(combo.getText());
					
					//User has selected a use case associated with a file name.
					try {
						//Display original file contents
						StringBuilder s = new StringBuilder();
						int fileIndex = combo.getSelectionIndex() - 1; //First selection is not a file.
						String useCaseFileName = resourceFiles[fileIndex].toString();
						
						for (String line : Files.readAllLines(Paths.get(useCaseFileName))) {
							s.append(line + "\n");
						}
						text.setText(s.toString());
						
						//Display proess content
						String indexContent = indexer.getIndexFile(fileIndex);
						riv.setIndicesText(indexContent.toString());
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
		
	}
	private RequirementsIndicesView getRequirementsView(String id) {
		RequirementsIndicesView riv = (RequirementsIndicesView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return riv;
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
