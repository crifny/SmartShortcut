package edu.buaa.sem.smartshortcut;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Slider;

public class Preference1 extends PreferencePage {

	private ConfigAndRefresh configAndRefresh;

	private Shell s;
	private Display d;

	private Group g1, g2, g3, g4;
	private Label labe1, label2, label3, label4, label5, label6, label7,
			label8;
	private Combo combo1, combo2;
	private Button button1, button2, button3, button4, button5, button6,
			button7;
	private Text text1, text2, text3;
	private Slider slider1;

	/**
	 * Create the preference page.
	 */
	public Preference1(Shell s, Display d, ConfigAndRefresh configAndRefresh) {
		this.s = s;
		this.d = d;
		this.configAndRefresh = configAndRefresh;

		setTitle("General");
		setMessage("General Options");
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		g1 = new Group(container, SWT.SHADOW_ETCHED_IN);
		g1.setText("Window Size (pixel)");
		g1.setBounds(0, 5, 400, 80);

		labe1 = new Label(g1, SWT.NONE);
		labe1.setBounds(10, 52, 90, 17);
		labe1.setText("Width(1-2560)");

		combo1 = new Combo(g1, SWT.NONE);
		combo1.setItems(new String[] { "800", "1024", "1028", "1440", "1600",
				"1920", "2560" });
		combo1.setBounds(99, 49, 90, 25);

		label2 = new Label(g1, SWT.NONE);
		label2.setBounds(214, 52, 90, 17);
		label2.setText("Height(1-1600)");

		combo2 = new Combo(g1, SWT.NONE);
		combo2.setItems(new String[] { "600", "768", "800", "900", "1200",
				"1600" });
		combo2.setBounds(302, 49, 90, 25);

		button1 = new Button(g1, SWT.CHECK);
		button1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.getSource()).getSelection()) {
					configAndRefresh.maximized = true;
				} else {
					configAndRefresh.maximized = false;
				}

				// Refresh the preference page
				refreshContents();

				// Refresh the main window
				configAndRefresh.refreshWindowSize();
				configAndRefresh.refreshWindowBackground();
			}
		});
		button1.setBounds(10, 22, 85, 20);
		button1.setText("Maximized");

		g2 = new Group(container, SWT.NONE);
		g2.setText("Window Background");
		g2.setBounds(0, 90, 400, 114);

		button2 = new Button(g2, SWT.RADIO);
		button2.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.backgroundImageStatus = 1;
				refreshContents();
				configAndRefresh.refreshWindowBackground();
			}
		});
		button2.setBounds(52, 20, 97, 17);
		button2.setText("Tile");

		button3 = new Button(g2, SWT.RADIO);
		button3.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.backgroundImageStatus = 2;
				refreshContents();
				configAndRefresh.refreshWindowBackground();
			}
		});
		button3.setBounds(150, 20, 97, 17);
		button3.setText("Stretch");

		button4 = new Button(g2, SWT.RADIO);
		button4.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.backgroundImageStatus = 0;
				refreshContents();
				configAndRefresh.refreshWindowBackground();
			}
		});
		button4.setBounds(262, 20, 97, 17);
		button4.setText("None");

		label3 = new Label(g2, SWT.NONE);
		label3.setBounds(10, 53, 40, 17);
		label3.setText("Path");

		text1 = new Text(g2, SWT.BORDER);
		text1.setEditable(false);
		text1.setBounds(52, 50, 292, 23);

		button5 = new Button(g2, SWT.NONE);
		button5.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(s, SWT.OPEN);
				fd.setText("Select Background Image");
				fd.setFilterPath(Navigator.LAST_APPLY_PATH);
				String[] filterExt = { "*.png;*.jpg;*.bmp", "*.*" };
				fd.setFilterExtensions(filterExt);
				if ((configAndRefresh.backgroundImage = fd.open()) != null) {
					String sub = configAndRefresh.backgroundImage.substring(
							configAndRefresh.backgroundImage.length() - 3,
							configAndRefresh.backgroundImage.length())
							.toLowerCase();
					if (sub.equals("png") || sub.equals("jpg")
							|| sub.equals("bmp")) {
						refreshContents();
						configAndRefresh.refreshWindowBackground();
					}
				} else {
					configAndRefresh.backgroundImage = configAndRefresh.backgroundImage0;
				}
				Navigator.LAST_APPLY_PATH = configAndRefresh.backgroundImage;
			}
		});
		button5.setBounds(345, 48, 50, 27);
		button5.setText("Browse");

		label4 = new Label(g2, SWT.NONE);
		label4.setBounds(10, 20, 40, 17);
		label4.setText("Image");

		label5 = new Label(g2, SWT.NONE);
		label5.setBounds(10, 87, 40, 17);
		label5.setText("Color");

		label6 = new Label(g2, SWT.BORDER);
		label6.setBounds(52, 82, 61, 25);

		button6 = new Button(g2, SWT.NONE);
		button6.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				ColorDialog cd = new ColorDialog(s, SWT.APPLICATION_MODAL);
				cd.setText("Select Background Color");
				cd.setRGB(new RGB(0, 0, 0));
				RGB newColor = cd.open();
				if (newColor == null) {
					return;
				}
				configAndRefresh.backgroundColorRed = newColor.red;
				configAndRefresh.backgroundColorGreen = newColor.green;
				configAndRefresh.backgroundColorBlue = newColor.blue;

				refreshContents();
				configAndRefresh.refreshWindowBackground();
			}
		});
		button6.setBounds(115, 80, 50, 27);
		button6.setText("Browse");

		g3 = new Group(container, SWT.NONE);
		g3.setText("Window Icon");
		g3.setBounds(0, 210, 400, 50);

		label7 = new Label(g3, SWT.NONE);
		label7.setBounds(10, 25, 40, 17);
		label7.setText("Path");

		text2 = new Text(g3, SWT.BORDER);
		text2.setEditable(false);
		text2.setBounds(52, 21, 292, 23);

		button7 = new Button(g3, SWT.NONE);
		button7.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(s, SWT.OPEN);
				fd.setText("Select Window Icon");
				fd.setFilterPath(Navigator.LAST_APPLY_PATH);
				String[] filterExt = { "*.ico;*.png;*.jpg;*.bmp", "*.*" };
				fd.setFilterExtensions(filterExt);
				if ((configAndRefresh.navigationIcon = fd.open()) != null) {
					String sub = configAndRefresh.navigationIcon.substring(
							configAndRefresh.navigationIcon.length() - 3,
							configAndRefresh.navigationIcon.length())
							.toLowerCase();
					if (sub.equals("ico") || sub.equals("png")
							|| sub.equals("jpg") || sub.equals("bmp")) {
						refreshContents();
						configAndRefresh.refreshWindowIcon();
					}
				} else {
					configAndRefresh.navigationIcon = configAndRefresh.navigationIcon0;
				}
				Navigator.LAST_APPLY_PATH = configAndRefresh.navigationIcon;
			}
		});
		button7.setBounds(345, 18, 50, 27);
		button7.setText("Browse");

		g4 = new Group(container, SWT.NONE);
		g4.setText("Window Transparency");
		g4.setBounds(0, 270, 400, 50);

		label8 = new Label(g4, SWT.NONE);
		label8.setBounds(10, 23, 40, 17);
		label8.setText("Value");

		text3 = new Text(g4, SWT.BORDER);
		text3.setEditable(false);
		text3.setBounds(52, 19, 44, 23);

		slider1 = new Slider(g4, SWT.NONE);
		slider1.setPageIncrement(5);
		slider1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				configAndRefresh.transparency = slider1.getSelection();

				refreshContents();

				configAndRefresh.refreshTransparency();
			}
		});
		slider1.setThumb(5);
		slider1.setMaximum(260);
		slider1.setMinimum(1);
		slider1.setBounds(97, 21, 293, 17);

		refreshContents();

		return container;
	}

	/**
	 * Refresh the preference page, not refresh the main window.
	 */
	private void refreshContents() {
		if (configAndRefresh.maximized) {
			button1.setSelection(true);
			combo1.setEnabled(false);
			combo2.setEnabled(false);
		} else {
			button1.setSelection(false);
			combo1.setEnabled(true);
			combo2.setEnabled(true);
		}
		combo1.setText(String.valueOf(configAndRefresh.width));
		combo2.setText(String.valueOf(configAndRefresh.height));

		if (configAndRefresh.backgroundImageStatus == 1) {
			button2.setSelection(true);
			button3.setSelection(false);
			button4.setSelection(false);
			button6.setEnabled(false);
		} else if (configAndRefresh.backgroundImageStatus == 2) {
			button2.setSelection(false);
			button3.setSelection(true);
			button4.setSelection(false);
			button6.setEnabled(false);
		} else {
			button2.setSelection(false);
			button3.setSelection(false);
			button4.setSelection(true);
			button6.setEnabled(true);
		}

		text1.setText(configAndRefresh.backgroundImage);
		text2.setText(configAndRefresh.navigationIcon);

		label6.setBackground(new Color(d, configAndRefresh.backgroundColorRed,
				configAndRefresh.backgroundColorGreen,
				configAndRefresh.backgroundColorBlue));

		text3.setText(String.valueOf(configAndRefresh.transparency));
		slider1.setSelection(configAndRefresh.transparency);
	}

	public void performHelp() {
	}

	protected void performDefaults() {
		configAndRefresh.width = 800;
		configAndRefresh.height = 600;
		configAndRefresh.backgroundImage = Navigator.CURRENT_PATH
				+ "/image/backgroundImage.png";
		configAndRefresh.navigationIcon = Navigator.CURRENT_PATH
				+ "/image/navigationIcon.png";
		configAndRefresh.maximized = true;
		configAndRefresh.backgroundImageStatus = 1;
		configAndRefresh.backgroundColorRed = 240;
		configAndRefresh.backgroundColorGreen = 240;
		configAndRefresh.backgroundColorBlue = 240;
		configAndRefresh.transparency = 255;

		refreshContents();

		configAndRefresh.refreshWindowSize();
		configAndRefresh.refreshWindowIcon();
		configAndRefresh.refreshWindowBackground();
		configAndRefresh.refreshTransparency();
	}

	protected void performApply() {
		validateData();
		configAndRefresh.width = Integer.parseInt(combo1.getText());
		configAndRefresh.height = Integer.parseInt(combo2.getText());

		if (configAndRefresh.width > configAndRefresh.screenWidth) {
			configAndRefresh.width = configAndRefresh.screenWidth;
		}
		if (configAndRefresh.height > configAndRefresh.screenHeight) {
			configAndRefresh.height = configAndRefresh.screenHeight;
		}

		configAndRefresh.saveConfig();
		configAndRefresh.getOpinionStatus();

		configAndRefresh.refreshWindowSize();
	}

	public boolean performOk() {
		performApply();
		return true;
	}

	public boolean performCancel() {
		configAndRefresh.backgroundImage = configAndRefresh.backgroundImage0;
		configAndRefresh.navigationIcon = configAndRefresh.navigationIcon0;
		configAndRefresh.width = configAndRefresh.width0;
		configAndRefresh.height = configAndRefresh.height0;
		configAndRefresh.maximized = configAndRefresh.maximized0;
		configAndRefresh.backgroundImageStatus = configAndRefresh.backgroundImageStatus0;
		configAndRefresh.backgroundColorRed = configAndRefresh.backgroundColorRed0;
		configAndRefresh.backgroundColorGreen = configAndRefresh.backgroundColorGreen0;
		configAndRefresh.backgroundColorBlue = configAndRefresh.backgroundColorBlue0;
		configAndRefresh.transparency = configAndRefresh.transparency0;

		configAndRefresh.refreshWindowSize();
		configAndRefresh.refreshWindowIcon();
		configAndRefresh.refreshWindowBackground();
		configAndRefresh.refreshTransparency();

		return true;
	}

	/**
	 * Validate the data of the components.
	 */
	private void validateData() {
		try {
			int iw = Integer.parseInt(combo1.getText());
			if (iw < 1 || iw > 2560) {
				MessageBox messageBox = new MessageBox(s, SWT.ICON_WARNING
						| SWT.OK);
				messageBox.setMessage("Invalid number of width");
				messageBox.setText("Warn");
				if (messageBox.open() == SWT.OK)
					;
				combo1.setText(String.valueOf(configAndRefresh.width));
			}
		} catch (Exception ee) {
			MessageBox messageBox = new MessageBox(s, SWT.ICON_WARNING | SWT.OK);
			messageBox.setMessage("Invalid number of width");
			messageBox.setText("Warn");
			if (messageBox.open() == SWT.OK)
				;
			combo1.setText(String.valueOf(configAndRefresh.width));
		}
		try {
			int ih = Integer.parseInt(combo2.getText());
			if (ih < 1 || ih > 1600) {
				MessageBox messageBox = new MessageBox(s, SWT.ICON_WARNING
						| SWT.OK);
				messageBox.setMessage("Invalid number of height");
				messageBox.setText("Warn");
				if (messageBox.open() == SWT.OK)
					;
				combo2.setText(String.valueOf(configAndRefresh.height));
			}
		} catch (Exception ee) {
			MessageBox messageBox = new MessageBox(s, SWT.ICON_WARNING | SWT.OK);
			messageBox.setMessage("Invalid number of height");
			messageBox.setText("Warn");
			if (messageBox.open() == SWT.OK)
				;
			combo2.setText(String.valueOf(configAndRefresh.height));
		}

		try {
			new Image(d, text1.getText());
		} catch (Exception ee) {
			MessageBox messageBox = new MessageBox(s, SWT.ICON_WARNING | SWT.OK);
			messageBox.setMessage("Path of background image is wrong");
			messageBox.setText("Warn");
			if (messageBox.open() == SWT.OK)
				;
			text1.setText(configAndRefresh.backgroundImage);
		}
		try {
			new Image(d, text2.getText());
		} catch (Exception ee) {
			MessageBox messageBox = new MessageBox(s, SWT.ICON_WARNING | SWT.OK);
			messageBox.setMessage("Path of window icon is wrong");
			messageBox.setText("Warn");
			if (messageBox.open() == SWT.OK)
				;
			text2.setText(configAndRefresh.navigationIcon);
		}
	}
}
