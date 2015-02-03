package dialogs;

//import tracing.views.RequirementsIndicesView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
/*import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;*/

public class GreetingMsg extends Dialog {

	protected Object result;
	protected Shell shell;
	private String dependantStr;
	private Text dirText;
	private static Display display;
	private String directory;
	private Text acText;
	private Text stopText;
	private String acronymStr;
	private String stopStr;
	private String[] optionList = new String[5];
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
		
		DirectoryDialog dirDialog = new DirectoryDialog(shell);
		FileDialog fileDialog = new FileDialog(shell);
		//String test = DirDialog.open();
		
		dirText = new Text(shell, SWT.BORDER | SWT.SEARCH);
		dirText.setBounds(20, 10, 288, 21);
		dirText.setText("(path name)");
		
		acText = new Text(shell, SWT.BORDER);
		acText.setBounds(186, 65, 161, 21);
		acText.setText("(file name)");
		acText.setEnabled(false);
		
		stopText = new Text(shell, SWT.BORDER);
		stopText.setBounds(186, 109, 161, 21);
		stopText.setText("(file name)");
		stopText.setEnabled(false);
		
		Button btnGetAcFile = new Button(shell, SWT.PUSH);
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
		
		Button btnGetStopFile = new Button(shell, SWT.PUSH);
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
		
		Button btnGetDir = new Button(shell, SWT.PUSH);
		btnGetDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				directory = dirDialog.open();
				if (directory != null) {
					//text.setText("");
					dirText.setText(directory);
					optionList[0] = directory;
					System.out.println(directory);
				}
			}
		});
		btnGetDir.setBounds(321, 10, 107, 21);
		btnGetDir.setText("Browse...");
		
		Button btnCheckTok = new Button(shell, SWT.CHECK);
		btnCheckTok.setBounds(20, 45, 139, 16);
		btnCheckTok.setText("Tokenizing");
		
		Button btnCheckAc = new Button(shell, SWT.CHECK);
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
		
		Button btnCheckStop = new Button(shell, SWT.CHECK);
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
		
		Button btnCheckStem = new Button(shell, SWT.CHECK);
		btnCheckStem.setBounds(20, 144, 139, 16);
		btnCheckStem.setText("Stemming");
		
		Button btnOK = new Button(shell, SWT.PUSH);
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//OK button listener
				//display.sleep();
				shell.close();
			}
		});
		btnOK.setBounds(20, 225, 75, 25);
		btnOK.setText("OK");
		
	}
	
	public static void main(String[] args) {
		Shell shell = new Shell();
		GreetingMsg msg = new GreetingMsg(shell, SWT.BORDER | SWT.WRAP);
		msg.open();
	}
}