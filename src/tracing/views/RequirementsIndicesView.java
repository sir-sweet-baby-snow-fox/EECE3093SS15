package tracing.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display; //JMR
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.ViewPart;

public class RequirementsIndicesView extends ViewPart implements ISelectionProvider{

	//private Display display = new Display();
	
	private void showMessage(){
		/*MessageDialog.openInformation(new Shell(),
				"Welcome",
				"Please make your selections.");*/
		//Image image = new Image(null, "C:/git/EECE3093SS15/Resources/freeman.jpg");
		String[] buttonArr = new String[2];
		buttonArr[0] = "browse";
		buttonArr[1] = "cancel";
		MessageDialog wDial = new MessageDialog(new Shell(), "Welcome", null, "tolf", 0, buttonArr, 1 );
		wDial.open();
		
		
		
		//Label label = new Label(new Shell(), SWT.BORDER | SWT.WRAP);
		
		
		/*Label label = new Label(shell, SWT.BORDER | SWT.WRAP);
		label.setBackground(disp.getSystemColor(SWT.COLOR_GREEN));
		label.setText("Select a dir/file by clicking the buttons below.");
		
		buttonSelectDir = new Button(shell, SWT.PUSH);
		buttonSelectDir.setText("Browse");
		buttonSelectDir.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        DirectoryDialog directoryDialog = new DirectoryDialog(shell);
		        
		        directoryDialog.setFilterPath(selectedDir);
		        directoryDialog.setMessage("Please select a directory and click OK");
		        
		        String dir = directoryDialog.open();
		        if(dir != null) {
		          label.setText("Selected dir: " + dir);
		          selectedDir = dir;
		        }
		      }
		    });
		    
		    buttonSelectFile = new Button(shell, SWT.PUSH);
		    buttonSelectFile.setText("Select a file/multiple files");
		    buttonSelectFile.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event event) {
		        FileDialog fileDialog = new FileDialog(shell, SWT.MULTI);

		        fileDialog.setFilterPath(fileFilterPath);
		        
		        fileDialog.setFilterExtensions(new String[]{"*.rtf", "*.html", "*.*"});
		        fileDialog.setFilterNames(new String[]{ "Rich Text Format", "HTML Document", "Any"});
		        
		        String firstFile = fileDialog.open();

		        if(firstFile != null) {
		          fileFilterPath = fileDialog.getFilterPath();
		          String[] selectedFiles = fileDialog.getFileNames();
		          StringBuffer sb = new StringBuffer("Selected files under dir " + fileDialog.getFilterPath() +  ": \n");
		          for(int i=0; i<selectedFiles.length; i++) {
		            sb.append(selectedFiles[i] + "\n");
		          }
		          label.setText(sb.toString());
		        }
		      }
		    });
		    
		    label.setBounds(0, 0, 400, 60);
		    buttonSelectDir.setBounds(0, 65, 200, 30);
		    buttonSelectFile.setBounds(200, 65, 200, 30);

		    shell.pack();
		    shell.open();
		    //textUser.forceFocus();

		    // Set up the event loop.
		    while (!shell.isDisposed()) {
		      if (!disp.readAndDispatch()) {
		        // If no more entries in event queue
		        disp.sleep();
		      }
		    }

		    disp.dispose();*/
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
		
		showMessage();
		
		//Set layout forum of parent composite
		parent.setLayout(new FormLayout());
		
		FormData formdata = new FormData();
		formdata.top=new FormAttachment(0,5);
		formdata.left = new FormAttachment(0,10);
		formdata.right = new FormAttachment(0,200);
		
		//Create title label
		Label titleLabel = new Label(parent,SWT.SINGLE);
		titleLabel.setText("Requirements View:");
		titleLabel.setLayoutData(formdata);
		
		//Create text area
		Text indicesText = new Text(parent,SWT.MULTI|SWT.V_SCROLL|SWT.READ_ONLY);
		indicesText.setText("This is a sample result.");
		formdata = new FormData();
		formdata.top = new FormAttachment(titleLabel,10);
		formdata.bottom = new FormAttachment(titleLabel,230);
		formdata.left = new FormAttachment(0,10);
		formdata.right = new FormAttachment(0,800);
		indicesText.setLayoutData(formdata);
		
		
		Button manageButton = new Button(parent,SWT.PUSH);
		manageButton.setText("Manage...");
		formdata = new FormData();
		formdata.top = new FormAttachment(indicesText,10);
		formdata.left = new FormAttachment(0,730);
		manageButton.setLayoutData(formdata);
		
		manageButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				showMessage();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
