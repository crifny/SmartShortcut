package edu.buaa.sem.smartshortcut;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

public class Preference3 extends PreferencePage {

	private ConfigAndRefresh configAndRefresh;

	@SuppressWarnings("unused")
	private Shell s;
	private Display d;

	private Group g1;
	private Composite composite1;
	private Button button;
	private Label label1, label2, label3, label4;
	private Text text1, text2, text3, text4;
	private Slider slider1, slider2, slider3, slider4;

	/**
	 * Create the preference page.
	 */
	public Preference3(Shell s, Display d, ConfigAndRefresh configAndRefresh) {
		this.s = s;
		this.d = d;
		this.configAndRefresh = configAndRefresh;

		setTitle("Button");
		setMessage("Button Options");
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		g1 = new Group(container, SWT.NONE);
		g1.setText("Style(Pixel)");
		g1.setBounds(0, 5, 400, 321);

		composite1 = new Composite(g1, SWT.NONE);
		composite1.setBackground(new Color(d, 255, 255, 255));
		composite1.setBounds(10, 25, 380, 180);

		button = new Button(composite1, SWT.NONE);
		button.setImage(new Image(d, Navigator.CURRENT_PATH
				+ "/image/navigationIcon.png"));
		button.setBounds(10, 10, 48, 48);

		label1 = new Label(g1, SWT.NONE);
		label1.setBounds(10, 211, 80, 17);
		label1.setText("Button Width");

		label2 = new Label(g1, SWT.NONE);
		label2.setBounds(10, 238, 80, 17);
		label2.setText("Button Height");

		label3 = new Label(g1, SWT.NONE);
		label3.setBounds(10, 265, 80, 17);
		label3.setText("Icon Width");

		label4 = new Label(g1, SWT.NONE);
		label4.setBounds(10, 292, 80, 17);
		label4.setText("Icon Height");

		text1 = new Text(g1, SWT.BORDER);
		text1.setEditable(false);
		text1.setBounds(95, 211, 44, 20);

		text2 = new Text(g1, SWT.BORDER);
		text2.setEditable(false);
		text2.setBounds(95, 237, 44, 20);

		text3 = new Text(g1, SWT.BORDER);
		text3.setEditable(false);
		text3.setBounds(95, 264, 44, 20);

		text4 = new Text(g1, SWT.BORDER);
		text4.setEditable(false);
		text4.setBounds(95, 290, 44, 20);

		slider1 = new Slider(g1, SWT.NONE);
		slider1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.buttonWidth = slider1.getSelection();

				refreshContents();
			}
		});
		slider1.setPageIncrement(5);
		slider1.setMaximum(350);
		slider1.setMinimum(1);
		slider1.setBounds(140, 211, 250, 17);

		slider2 = new Slider(g1, SWT.NONE);
		slider2.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.buttonHeight = slider2.getSelection();

				refreshContents();
			}
		});
		slider2.setPageIncrement(5);
		slider2.setMaximum(160);
		slider2.setMinimum(1);
		slider2.setBounds(140, 238, 250, 17);

		slider3 = new Slider(g1, SWT.NONE);
		slider3.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.buttonImageWidth = slider3.getSelection();

				refreshContents();
			}
		});
		slider3.setPageIncrement(5);
		slider3.setMaximum(350);
		slider3.setMinimum(1);
		slider3.setBounds(140, 265, 250, 17);

		slider4 = new Slider(g1, SWT.NONE);
		slider4.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.buttonImageHeight = slider4.getSelection();

				refreshContents();
			}
		});
		slider4.setPageIncrement(5);
		slider4.setMaximum(160);
		slider4.setMinimum(1);
		slider4.setBounds(140, 292, 250, 17);

		refreshContents();

		return container;
	}

	/**
	 * Refresh the preference page, not refresh the main window.
	 */
	private void refreshContents() {
		text1.setText(String.valueOf(configAndRefresh.buttonWidth));
		text2.setText(String.valueOf(configAndRefresh.buttonHeight));
		text3.setText(String.valueOf(configAndRefresh.buttonImageWidth));
		text4.setText(String.valueOf(configAndRefresh.buttonImageHeight));
		slider1.setSelection(configAndRefresh.buttonWidth);
		slider2.setSelection(configAndRefresh.buttonHeight);
		slider3.setSelection(configAndRefresh.buttonImageWidth);
		slider4.setSelection(configAndRefresh.buttonImageHeight);

		button.setSize(configAndRefresh.buttonWidth,
				configAndRefresh.buttonHeight);
		button.setImage(new Image(d, new ImageData(Navigator.CURRENT_PATH
				+ "/image/navigationIcon.png").scaledTo(
				configAndRefresh.buttonImageWidth,
				configAndRefresh.buttonImageHeight)));
	}

	public void performHelp() {
	}

	protected void performDefaults() {
		configAndRefresh.buttonWidth = 48;
		configAndRefresh.buttonHeight = 48;
		configAndRefresh.buttonImageWidth = 32;
		configAndRefresh.buttonImageHeight = 32;

		refreshContents();
	}

	protected void performApply() {
		configAndRefresh.saveConfig();

		configAndRefresh.getOpinionStatus();
	}

	public boolean performOk() {
		performApply();
		return true;
	}

	public boolean performCancel() {
		configAndRefresh.buttonWidth = configAndRefresh.buttonWidth0;
		configAndRefresh.buttonHeight = configAndRefresh.buttonHeight0;
		configAndRefresh.buttonImageWidth = configAndRefresh.buttonImageWidth0;
		configAndRefresh.buttonImageHeight = configAndRefresh.buttonImageHeight0;
		configAndRefresh.buttonBackgroundColorRed = configAndRefresh.buttonBackgroundColorRed0;
		configAndRefresh.buttonBackgroundColorGreen = configAndRefresh.buttonBackgroundColorGreen0;
		configAndRefresh.buttonBackgroundColorBlue = configAndRefresh.buttonBackgroundColorBlue0;

		return true;
	}

}
