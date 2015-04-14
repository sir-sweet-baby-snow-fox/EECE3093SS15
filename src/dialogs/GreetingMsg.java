package dialogs;

import indexer.Indexer;
import indexer.IndexerInfo;

import java.io.File;

import javax.swing.JOptionPane;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
/*import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;*/
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.ui.IPackagesViewPart;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;

import tracing.views.RequirementsView;
import tracing.views.MethodIndicesView;

public class GreetingMsg extends Dialog {

	protected Object result;
	protected Shell greetingMsgShell;
	private Text dirText;
	private static Display display;
	private Text acronymText;
	private Text stopWordText;
	private Text storeText;
	private RequirementsView reqInstance;
	private IndexerInfo indexerInfo;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GreetingMsg(Shell parent, int style) {
		super(parent, style);
		setText("Welcome!");
		indexerInfo = new IndexerInfo();
	}
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		greetingMsgShell.open();
		greetingMsgShell.layout();
		display = getParent().getDisplay();
		
		try{
			reqInstance = (RequirementsView) getView(RequirementsView.ID);
		} catch (Exception e) { System.out.println(e.toString()); }
		
		while (!greetingMsgShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		//display.close();
		return result;
	}
	
	public String[] getOptions() {
		return optionList;
	}
	
	public String[] openDisplay() {
		this.open();
		/*
		 * [0] -> path
		 * [1] -> file 1
		 * [2] -> file 2
		 */
		//String[] returnArr = new String[3];
		while (true) {
			
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		greetingMsgShell = new Shell(getParent(), getStyle());
		greetingMsgShell.setSize(450, 300);
		greetingMsgShell.setText(getText());
		
		final DirectoryDialog dirDialog = new DirectoryDialog(greetingMsgShell);
		final FileDialog fileDialog = new FileDialog(greetingMsgShell);
		
		dirText = new Text(greetingMsgShell, SWT.BORDER | SWT.SEARCH);
		dirText.setBounds(20, 10, 288, 21);
		dirText.setMessage("Resource Directory");
		
		acronymText = new Text(greetingMsgShell, SWT.BORDER);
		acronymText.setBounds(186, 65, 161, 21);
		acronymText.setText("");
		acronymText.setEnabled(false);
		
		stopWordText = new Text(greetingMsgShell, SWT.BORDER);
		stopWordText.setBounds(186, 109, 161, 21);
		stopWordText.setText("");
		stopWordText.setEnabled(false);
		
		storeText = new Text(greetingMsgShell, SWT.BORDER);
		storeText.setBounds(186, 153, 161, 21);
		storeText.setText("");
		storeText.setEnabled(false);
		
		final Button btnGetAcronymFile = new Button(greetingMsgShell, SWT.PUSH);
		btnGetAcronymFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexerInfo.acronymFilePath = fileDialog.open();
				if(indexerInfo.acronymFilePath != null) {
					acronymText.setText(indexerInfo.acronymFilePath);					
				}				
			}
		});
		btnGetAcronymFile.setBounds(353, 65, 75, 21);
		btnGetAcronymFile.setText("Browse...");
		btnGetAcronymFile.setEnabled(false);
		
		final Button btnGetStopWordFile = new Button(greetingMsgShell, SWT.PUSH);
		btnGetStopWordFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexerInfo.stopWordsFilePath = fileDialog.open();
				if(indexerInfo.stopWordsFilePath != null) {
					stopWordText.setText(indexerInfo.stopWordsFilePath);
				}
			}
			
		});
		btnGetStopWordFile.setBounds(353, 109, 75, 21);
		btnGetStopWordFile.setText("Browse...");
		btnGetStopWordFile.setEnabled(false);
		
		final Button btnStoreIndicesDir = new Button(greetingMsgShell, SWT.PUSH);
		btnStoreIndicesDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexerInfo.storeIndicesDir = dirDialog.open();
				if(indexerInfo.storeIndicesDir != null) {
					storeText.setText(indexerInfo.storeIndicesDir);
				}
			}
			
		});
		btnStoreIndicesDir.setBounds(353, 153, 75, 21);
		btnStoreIndicesDir.setText("Browse...");
		btnStoreIndicesDir.setEnabled(false);
		
		Button btnGetDir = new Button(greetingMsgShell, SWT.PUSH);
		btnGetDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				indexerInfo.resourceDirectoryPath = dirDialog.open();
				if (indexerInfo.resourceDirectoryPath != null) {
					dirText.setText(indexerInfo.resourceDirectoryPath);
					optionList[0] = indexerInfo.resourceDirectoryPath;
				}
			}
		});
		btnGetDir.setBounds(321, 10, 107, 21);
		btnGetDir.setText("Browse...");
		
		final Button btnCheckTok = new Button(greetingMsgShell, SWT.CHECK);
		btnCheckTok.setBounds(20, 45, 139, 16);
		btnCheckTok.setText("Tokenizing");
		
		final Button btnCheckAcronym = new Button(greetingMsgShell, SWT.CHECK);
		btnCheckAcronym.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCheckAcronym.getSelection() == true) {
					//disable corresponding text field
					btnGetAcronymFile.setEnabled(true);
					acronymText.setEnabled(true);
				}
				else {
					btnGetAcronymFile.setEnabled(false);
					acronymText.setEnabled(false);
				}
			}
		});
		btnCheckAcronym.setBounds(20, 67, 139, 16);
		btnCheckAcronym.setText("Restoring Acronyms");
		
		final Button btnCheckStopWords = new Button(greetingMsgShell, SWT.CHECK);
		btnCheckStopWords.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCheckStopWords.getSelection() == true) {
					//disable corresponding text field
					btnGetStopWordFile.setEnabled(true);
					stopWordText.setEnabled(true);
				}
				else {
					btnGetStopWordFile.setEnabled(false);
					stopWordText.setEnabled(false);
				}
			}
		});
		btnCheckStopWords.setBounds(20, 111, 139, 16);
		btnCheckStopWords.setText("Removing Stop Words");
		
		final Button btnStoreIndices = new Button(greetingMsgShell, SWT.CHECK);
		btnStoreIndices.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnStoreIndices.getSelection()) {
					// enable text fields
					btnStoreIndicesDir.setEnabled(true);
					storeText.setEnabled(true);
				} else {
					btnStoreIndicesDir.setEnabled(false);
					storeText.setEnabled(false);
				}
			}
		});
		btnStoreIndices.setBounds(20, 153, 139, 16);
		btnStoreIndices.setText("Storing Indices");
		
		Button btnCancel = new Button(greetingMsgShell, SWT.PUSH);		
			btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setBounds(353, 225, 75, 25);
		btnCancel.setText("Quit");
		
		final Button btnCheckStem = new Button(greetingMsgShell, SWT.CHECK);
		btnCheckStem.setBounds(20, 188, 139, 16);
		btnCheckStem.setText("Stemming");
		
		Button btnOK = new Button(greetingMsgShell, SWT.PUSH);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Check for valid resource directory path
				indexerInfo.resourceDirectoryPath = dirText.getText();
				File directoryFile = new File(indexerInfo.resourceDirectoryPath);
				
				if(!directoryFile.exists() || !directoryFile.isDirectory() ) {
					//If it doesn't exist, or it isn't a directory, display message,
					JOptionPane.showMessageDialog(null,"Resource Directory is invalid.","Error",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//Check for valid acronym text file, if it is used
				if(btnCheckAcronym.getSelection()) {
					indexerInfo.acronymFilePath = acronymText.getText();
					File acronymFile = new File(indexerInfo.acronymFilePath);
					
					//Check for errors
					if(!acronymFile.exists()) {
						JOptionPane.showMessageDialog(null,"Acronym text file not found.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					} else if (!acronymFile.isFile()) {
						JOptionPane.showMessageDialog(null,"Acronym text file is not a file.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					} else if (!acronymFile.toString().endsWith(".txt")) {
						JOptionPane.showMessageDialog(null,"Acronym text file is not a text file.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				} else {
					//User doesn't want to use acronym converter. File path should be empty
					indexerInfo.acronymFilePath = "";
				}
				
				//Check for valid stop text files, if it is used
				if(btnCheckStopWords.getSelection()) {
					indexerInfo.stopWordsFilePath = stopWordText.getText();
					File stopWordFile = new File(indexerInfo.stopWordsFilePath);
					
					//Check for errors
					if(!stopWordFile.exists()) {
						JOptionPane.showMessageDialog(null,"Stop word file was not found.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!stopWordFile.isFile()) {
						JOptionPane.showMessageDialog(null,"Stop word file is not a file.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}else if(!stopWordFile.toString().endsWith(".txt")) {
						JOptionPane.showMessageDialog(null,"Stop word file is not a text file.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				} else {
					//User doesn't want to use stop word remover. File path should be empty.
					indexerInfo.stopWordsFilePath = "";
				}
				
				//Check for valid storing indices dir, if it is used
				if (btnStoreIndices.getSelection()) {
					indexerInfo.storeIndicesDir = storeText.getText();
					File storeDirFile = new File(indexerInfo.storeIndicesDir);
					
					if(!storeDirFile.exists() || !storeDirFile.isDirectory() ) {
						//If it doesn't exist, or it isn't a directory, display message,
						JOptionPane.showMessageDialog(null,"Storing Indices directory is invalid.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				} else {
					// User doesn't want to store indices, file path should be empty.
					indexerInfo.storeIndicesDir = "";
				}

				//Update reqInstance variables
				reqInstance.setResourcePath(indexerInfo.resourceDirectoryPath);
				reqInstance.updateComboBox();
				
				//Perform the indexing
				indexerInfo.doTokenize = btnCheckTok.getSelection();
				indexerInfo.doStem = btnCheckStem.getSelection();
				Indexer indexer = new Indexer(indexerInfo);
				
				//Let reqInstance have access to index objects
				reqInstance.setIndexer(indexer);
				// Force the default text in the RequirementsView widget to update now that it has
				// access to the indexer, and thus the number of use cases and indexing time.
				reqInstance.setDefaultText();
				
				IProjectDescription description;
				try {
					description = ResourcesPlugin.getWorkspace().loadProjectDescription(new Path("C:\\iTrust\\.project"));
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
					if(!project.exists()){
						project.create(description, null);
						IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
							public String queryOverwrite(String file) { return ALL; }
						};
						String baseDir = "C:\\iTrust\\"; // location of files to import
						ImportOperation importOperation = new ImportOperation(project.getFullPath(),
								new File(baseDir), FileSystemStructureProvider.INSTANCE, overwriteQuery);
						importOperation.setCreateContainerStructure(false);
						importOperation.run(new NullProgressMonitor());
					}
				}
				catch (Exception e2) { e2.printStackTrace(); }
				
				MethodIndicesView methodIndicesView = (MethodIndicesView) getView(MethodIndicesView.ID);
				methodIndicesView.indexMethods();
				
				//Continue onto eclipse
				greetingMsgShell.close();
			}
		});
		btnOK.setBounds(20, 225, 75, 25);
		btnOK.setText("OK");
		
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