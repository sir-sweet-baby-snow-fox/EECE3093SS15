package overriddenProtected;

import org.eclipse.swt.widgets.Button;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class MDialog extends MessageDialog {

	public MDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, int dialogImageType,
			String[] dialogButtonLabels, int defaultIndex) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				dialogImageType, dialogButtonLabels, defaultIndex);
		// TODO Auto-generated constructor stub
	}
	
	public Button getButton(int index) {
		return super.getButton(index);
	}
	
	public void setButtons(Button[] buttons) {
		super.setButtons(buttons);
	}
	
	public void setButtonLabels(String[] labels) {
		super.setButtonLabels(labels);
	}

}