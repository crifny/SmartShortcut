package edu.buaa.sem.smartshortcut;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public class Preference2 extends PreferencePage {

	private ConfigAndRefresh configAndRefresh;

	private Shell s;
	private Display d;

	private Group g1;
	private Button button1, button2, button3, button4;
	private Composite composite1;
	private Label label1;

	/**
	 * Create the preference page.
	 */
	public Preference2(Shell s, Display d, ConfigAndRefresh configAndRefresh) {
		this.s = s;
		this.d = d;
		this.configAndRefresh = configAndRefresh;

		setTitle("Label");
		setMessage("Label Options");
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		g1 = new Group(container, SWT.NONE);
		g1.setText("Style");
		g1.setBounds(0, 5, 400, 250);

		button1 = new Button(g1, SWT.NONE);
		button1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				ColorDialog cd = new ColorDialog(s, SWT.APPLICATION_MODAL);
				cd.setText("Select Foreground Color");
				cd.setRGB(new RGB(0, 0, 0));
				RGB newColor = cd.open();
				if (newColor == null)
					return;
				configAndRefresh.labelForegroundColorRed = newColor.red;
				configAndRefresh.labelForegroundColorGreen = newColor.green;
				configAndRefresh.labelForegroundColorBlue = newColor.blue;

				refreshContents();
			}
		});
		button1.setBounds(105, 211, 115, 27);
		button1.setText("Foreground Color");

		composite1 = new Composite(g1, SWT.BORDER);
		composite1.setBackground(new Color(d, 255, 255, 255));
		composite1.setBounds(10, 25, 380, 180);

		label1 = new Label(composite1, SWT.NONE);
		label1.setBounds(10, 10, 89, 17);
		label1.setText("SmartShortcut \u5BFC\u822A");

		button2 = new Button(g1, SWT.NONE);
		button2.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				ColorDialog cd = new ColorDialog(s, SWT.APPLICATION_MODAL);
				cd.setText("Select Background Color");
				cd.setRGB(new RGB(0, 0, 0));
				RGB newColor = cd.open();
				if (newColor == null)
					return;
				configAndRefresh.labelBackgroundColorRed = newColor.red;
				configAndRefresh.labelBackgroundColorGreen = newColor.green;
				configAndRefresh.labelBackgroundColorBlue = newColor.blue;

				refreshContents();
			}
		});
		button2.setBounds(225, 211, 115, 27);
		button2.setText("Background Color");

		button3 = new Button(g1, SWT.NONE);
		button3.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				FontDialog fnd = new FontDialog(s, SWT.APPLICATION_MODAL);
				fnd.setText("Select Label Font");
				fnd.setRGB(label1.getForeground().getRGB());
				FontData[] defaultFont = label1.getFont().getFontData();
				fnd.setFontList(defaultFont);
				FontData newFont = fnd.open();
				if (newFont == null)
					return;
				configAndRefresh.labelFontName = newFont.getName();
				configAndRefresh.labelFontSize = newFont.getHeight();
				configAndRefresh.labelFontStyle = newFont.getStyle();

				refreshContents();
			}
		});
		button3.setBounds(345, 211, 47, 27);
		button3.setText("Font");

		button4 = new Button(g1, SWT.CHECK);
		button4.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.getSource()).getSelection()) {
					configAndRefresh.labelBackgroundTransparent = true;
				} else {
					configAndRefresh.labelBackgroundTransparent = false;
				}

				refreshContents();

				configAndRefresh.refreshWindowBackground();
			}
		});
		button4.setBounds(10, 214, 95, 21);
		button4.setText("Transparent");

		refreshContents();

		return container;
	}

	/**
	 * Refresh the preference page, not refresh the main window.
	 */
	private void refreshContents() {
		if (configAndRefresh.labelBackgroundTransparent) {
			label1.setBackground(new Color(d, 255, 255, 255));
			button4.setSelection(true);
			button2.setEnabled(false);
		} else {
			label1.setBackground(new Color(d,
					configAndRefresh.labelBackgroundColorRed,
					configAndRefresh.labelBackgroundColorGreen,
					configAndRefresh.labelBackgroundColorBlue));
			button4.setSelection(false);
			button2.setEnabled(true);
		}

		label1.setFont(new Font(d,
				new FontData(configAndRefresh.labelFontName,
						configAndRefresh.labelFontSize,
						configAndRefresh.labelFontStyle)));
		label1.setForeground(new Color(d,
				configAndRefresh.labelForegroundColorRed,
				configAndRefresh.labelForegroundColorGreen,
				configAndRefresh.labelForegroundColorBlue));
		label1.setSize(CalculateText.getTextWidth(label1),
				CalculateText.getTextHeight(label1));
	}

	public void performHelp() {
	}

	protected void performDefaults() {
		configAndRefresh.labelBackgroundTransparent = true;
		configAndRefresh.labelBackgroundColorRed = 240;
		configAndRefresh.labelBackgroundColorGreen = 240;
		configAndRefresh.labelBackgroundColorBlue = 240;
		configAndRefresh.labelForegroundColorRed = 0;
		configAndRefresh.labelForegroundColorGreen = 0;
		configAndRefresh.labelForegroundColorBlue = 0;
		configAndRefresh.labelFontName = "\u65B0\u5B8B\u4F53";
		configAndRefresh.labelFontSize = 14;
		configAndRefresh.labelFontStyle = 0;

		refreshContents();

		configAndRefresh.refreshWindowBackground();
	}

	protected void performApply() {

		configAndRefresh.saveConfig();

		configAndRefresh.refreshWindowBackground();
		configAndRefresh.getOpinionStatus();

	}

	public boolean performOk() {
		performApply();
		return true;
	}

	public boolean performCancel() {
		configAndRefresh.labelBackgroundColorRed = configAndRefresh.labelBackgroundColorRed0;
		configAndRefresh.labelBackgroundColorGreen = configAndRefresh.labelBackgroundColorGreen0;
		configAndRefresh.labelBackgroundColorBlue = configAndRefresh.labelBackgroundColorBlue0;
		configAndRefresh.labelForegroundColorRed = configAndRefresh.labelForegroundColorRed0;
		configAndRefresh.labelForegroundColorGreen = configAndRefresh.labelForegroundColorGreen0;
		configAndRefresh.labelForegroundColorBlue = configAndRefresh.labelForegroundColorBlue0;
		configAndRefresh.labelFontName = configAndRefresh.labelFontName0;
		configAndRefresh.labelFontSize = configAndRefresh.labelFontSize0;
		configAndRefresh.labelFontStyle = configAndRefresh.labelFontStyle0;
		configAndRefresh.labelBackgroundTransparent = configAndRefresh.labelBackgroundTransparent0;

		configAndRefresh.refreshWindowBackground();
		return true;
	}

}
