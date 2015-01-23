package tracing.views;

import java.io.IOException;

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
	private MessageDialog wDial;
	private String[] buttonArr;
	private Shell shell;
	private Button cancelButton, browseButton;
	private String fileFilterPath;
	private Button[] buttons;
	
	public RequirementsIndicesView(){
		shell = new Shell();
		cancelButton = new Button(shell, SWT.PUSH);
		browseButton = new Button(shell, SWT.PUSH);
		buttons = new Button[2];
		buttons[0] = browseButton;
		buttons[1] = cancelButton;
		fileFilterPath = "C:/";
		/*buttonArr = new String[2];
		buttonArr[0] = "browse";
		buttonArr[1] = "cancel";*/
		wDial = new MessageDialog(shell, "Welcome", null, "Message, fill in later", 0, null, 1 ) {
			protected Button getButton(int index) {
				return super.getButton(index);
			}
		};
	}
	
	private void showMessage(){
		MessageDialog.openInformation(new Shell(),
				"Welcome",
				"Please make your selections.");
		/*try {*/
			wDial.open();
			/*browseButton.addListener(SWT.Selection, new Listener() {
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
			        }
				}
			});*/
	}
	
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// TODO Auto-generated method stub
		//MessageDialog.openInformation(new Shell(), "test", "test1111111");
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
		//MessageDialog.openInformation(new Shell(), "test", "test");
		
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
				//MessageDialog.openInformation(new Shell(), "test", "test");
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
