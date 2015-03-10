package dialogs;

import indexer.Indexer;

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
	protected Shell shell;
	private String dependantStr;
	private Text dirText;
	private static Display display;
	private String directory;
	private Text acText;
	private Text stopText;
	private Text storeText;
	private String acronymStr;
	private String stopStr;
	private String storeStr;
	private String[] optionList = new String[5];
	private RequirementsView reqInstance;
	private String reqViewId = "tracing.views.RequirementsView";
	//private String dirFilterStr;
	
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public GreetingMsg(Shell parent, int style) {
		super(parent, style);
		setText("Welcome!");
		directory = "";
		acronymStr = "";
		stopStr = "";
		storeStr = "";
	}
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		display = getParent().getDisplay();
		
		try{
			reqInstance = getRequirementsView(RequirementsView.ID);
		} catch (Exception e) { System.out.println(e.toString()); }
		
		while (!shell.isDisposed()) {
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
		String[] returnArr = new String[3];
		while (true) {
			
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());
		
		final DirectoryDialog dirDialog = new DirectoryDialog(shell);
		final FileDialog fileDialog = new FileDialog(shell);
		
		dirText = new Text(shell, SWT.BORDER | SWT.SEARCH);
		dirText.setBounds(20, 10, 288, 21);
		dirText.setText("(Resource Directory)");
		
		acText = new Text(shell, SWT.BORDER);
		acText.setBounds(186, 65, 161, 21);
		acText.setText("");
		acText.setEnabled(false);
		
		stopText = new Text(shell, SWT.BORDER);
		stopText.setBounds(186, 109, 161, 21);
		stopText.setText("");
		stopText.setEnabled(false);
		
		storeText = new Text(shell, SWT.BORDER);
		storeText.setBounds(186, 153, 161, 21);
		storeText.setText("");
		storeText.setEnabled(false);
		
		final Button btnGetAcFile = new Button(shell, SWT.PUSH);
		btnGetAcFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				acronymStr = fileDialog.open();
				if(acronymStr != null) {
					acText.setText(acronymStr);
					
				}
				
			}
		});
		btnGetAcFile.setBounds(353, 65, 75, 21);
		btnGetAcFile.setText("Browse...");
		btnGetAcFile.setEnabled(false);
		// listener corresponding to text_1
		
		final Button btnGetStopFile = new Button(shell, SWT.PUSH);
		btnGetStopFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				stopStr = fileDialog.open();
				if(stopStr != null) {
					stopText.setText(stopStr);
				}
			}
			
		});
		btnGetStopFile.setBounds(353, 109, 75, 21);
		btnGetStopFile.setText("Browse...");
		btnGetStopFile.setEnabled(false);
		//listener corresponding to text_2
		
		final Button btnStoreIndicesDir = new Button(shell, SWT.PUSH);
		btnStoreIndicesDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				storeStr = dirDialog.open();
				if(storeStr != null) {
					storeText.setText(storeStr);
				}
			}
			
		});
		btnStoreIndicesDir.setBounds(353, 153, 75, 21);
		btnStoreIndicesDir.setText("Browse...");
		btnStoreIndicesDir.setEnabled(false);
		//listener corresponding to text_3
		
		Button btnGetDir = new Button(shell, SWT.PUSH);
		btnGetDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				directory = dirDialog.open();
				if (directory != null) {
					//text.setText("");
					dirText.setText(directory);
					optionList[0] = directory;
				}
			}
		});
		btnGetDir.setBounds(321, 10, 107, 21);
		btnGetDir.setText("Browse...");
		
		final Button btnCheckTok = new Button(shell, SWT.CHECK);
		btnCheckTok.setBounds(20, 45, 139, 16);
		btnCheckTok.setText("Tokenizing");
		
		final Button btnCheckAc = new Button(shell, SWT.CHECK);
		btnCheckAc.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCheckAc.getSelection() == true) {
					//disable corresponding text field
					btnGetAcFile.setEnabled(true);
					acText.setEnabled(true);
				}
				else {
					btnGetAcFile.setEnabled(false);
					acText.setEnabled(false);
				}
			}
		});
		btnCheckAc.setBounds(20, 67, 139, 16);
		btnCheckAc.setText("Restoring Acronyms");
		
		final Button btnCheckStop = new Button(shell, SWT.CHECK);
		btnCheckStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(btnCheckStop.getSelection() == true) {
					//disable corresponding text field
					btnGetStopFile.setEnabled(true);
					stopText.setEnabled(true);
				}
				else {
					btnGetStopFile.setEnabled(false);
					stopText.setEnabled(false);
				}
			}
		});
		btnCheckStop.setBounds(20, 111, 139, 16);
		btnCheckStop.setText("Removing Stop Words");
		
		final Button btnStoreIndices = new Button(shell, SWT.CHECK);
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
		
		Button btnCancel = new Button(shell, SWT.PUSH);		
			btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//shell.dispose();
				System.exit(0);
			}
		});
		btnCancel.setBounds(353, 225, 75, 25);
		btnCancel.setText("Quit");
		
		final Button btnCheckStem = new Button(shell, SWT.CHECK);
		btnCheckStem.setBounds(20, 188, 139, 16);
		btnCheckStem.setText("Stemming");
		
		Button btnOK = new Button(shell, SWT.PUSH);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				//Check for valid resource directory path
				directory = dirText.getText();
				File directoryFile = new File(directory);
				if(!directoryFile.exists() || !directoryFile.isDirectory() ) {
					//If it doesnt exist, or it isnt a directory, display message,
					JOptionPane.showMessageDialog(null,"Resource Directory is invalid.","Error",JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				//Check for valid acronym text file, if it is used
				if(btnCheckAc.getSelection()) {
					acronymStr = acText.getText();
					File acronymFile = new File(acronymStr);
					
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
					acronymStr = "";
				}
				
				//Check for valid stop text files, if it is used
				if(btnCheckStop.getSelection()) {
					stopStr = stopText.getText();
					File stopWordFile = new File(stopStr);
					
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
					stopStr = "";
				}
				
				//Check for valid storing indices dir, if it is used
				if (btnStoreIndices.getSelection()) {
					storeStr = storeText.getText();
					
					File storeDirFile = new File(storeStr);
					if(!storeDirFile.exists() || !storeDirFile.isDirectory() ) {
						//If it doesnt exist, or it isnt a directory, display message,
						JOptionPane.showMessageDialog(null,"Storing Indices directory is invalid.","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				} else {
					// User doesn't want to store indices, file path should be empty.
					storeStr = "";
				}

				//Update reqInstance variables
				reqInstance.setResourcePath(directory);
				reqInstance.updateComboBox();
				
				//Perform the indexing
				Indexer indexer = new Indexer(directory, btnCheckTok.getSelection() , btnCheckStem.getSelection()
						, acronymStr, stopStr, storeStr);
				
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
				shell.close();
			}
		});
		btnOK.setBounds(20, 225, 75, 25);
		btnOK.setText("OK");
		
	}
	
	private RequirementsView getRequirementsView(String id) {
		RequirementsView riv = (RequirementsView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return riv;
	}
	
	private IViewPart getView(String id) {
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(id);
		return view;
	}
}