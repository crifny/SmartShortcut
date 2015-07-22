package edu.buaa.sem.smartshortcut;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Preference4 extends PreferencePage {

	private ConfigAndRefresh configAndRefresh;

	@SuppressWarnings("unused")
	private Shell s;
	@SuppressWarnings("unused")
	private Display d;

	private Button button1, button2;

	/**
	 * Create the preference page.
	 */
	public Preference4(Shell s, Display d, ConfigAndRefresh configAndRefresh) {
		this.s = s;
		this.d = d;
		this.configAndRefresh = configAndRefresh;

		setTitle("Other");
		setMessage("Other Options");
	}

	/**
	 * Create contents of the preference page.
	 * 
	 * @param parent
	 */
	public Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		button1 = new Button(container, SWT.CHECK);
		button1.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.getSource()).getSelection()) {
					configAndRefresh.autoExit = true;
				} else {
					configAndRefresh.autoExit = false;
				}
			}
		});
		button1.setBounds(10, 10, 219, 17);
		button1.setText("Auto exit after startup a program");

		button2 = new Button(container, SWT.CHECK);
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (((Button) e.getSource()).getSelection()) {
					configAndRefresh.isCenter = true;
				} else {
					configAndRefresh.isCenter = false;
				}
			}
		});
		button2.setBounds(10, 33, 219, 17);
		button2.setText("Center the window");

		refreshContents();

		return container;
	}

	/**
	 * Refresh the preference page, not refresh the main window.
	 */
	private void refreshContents() {
		if (configAndRefresh.autoExit) {
			button1.setSelection(true);
		} else {
			button1.setSelection(false);
		}
		if (configAndRefresh.isCenter) {
			button2.setSelection(true);

		} else {
			button2.setSelection(false);
		}
	}

	public void performHelp() {
	}

	protected void performDefaults() {
		configAndRefresh.autoExit = true;
		configAndRefresh.isCenter = true;

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
		configAndRefresh.autoExit = configAndRefresh.autoExit0;
		configAndRefresh.isCenter = configAndRefresh.isCenter0;

		return true;
	}
}
