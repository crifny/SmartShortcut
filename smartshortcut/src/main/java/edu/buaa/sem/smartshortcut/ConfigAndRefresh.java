package edu.buaa.sem.smartshortcut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ConfigAndRefresh {

	public Shell shell;
	public Display display;
	public int screenWidth, screenHeight;

	// configuration variables-->
	// a variable with a suffix "0" is an initial value
	public String navigationIcon0, navigationIcon;
	public String backgroundImage0, backgroundImage;
	public int backgroundImageStatus0, backgroundImageStatus;// 0:none;1:tile;2:stretch
	public int backgroundColorRed0, backgroundColorRed, backgroundColorGreen0,
			backgroundColorGreen, backgroundColorBlue0, backgroundColorBlue;
	public int labelForegroundColorRed0, labelForegroundColorRed,
			labelForegroundColorGreen0, labelForegroundColorGreen,
			labelForegroundColorBlue0, labelForegroundColorBlue;
	public int labelBackgroundColorRed0, labelBackgroundColorRed,
			labelBackgroundColorGreen0, labelBackgroundColorGreen,
			labelBackgroundColorBlue0, labelBackgroundColorBlue;
	public boolean labelBackgroundTransparent0, labelBackgroundTransparent;
	public String labelFontName0, labelFontName;
	public int labelFontSize0, labelFontSize, labelFontStyle0, labelFontStyle;
	public int buttonWidth0, buttonWidth, buttonHeight0, buttonHeight;
	public int buttonImageWidth0, buttonImageWidth, buttonImageHeight0,
			buttonImageHeight;
	public int buttonBackgroundColorRed0, buttonBackgroundColorRed,
			buttonBackgroundColorGreen0, buttonBackgroundColorGreen,
			buttonBackgroundColorBlue0, buttonBackgroundColorBlue;
	public boolean autoExit0, autoExit;
	public int width0, width, height0, height;
	public boolean maximized0, maximized;
	public boolean isCenter0, isCenter;
	public int transparency0, transparency;

	// configuration variables--<

	public ConfigAndRefresh(Shell shell, Display display) {
		this.shell = shell;
		this.display = display;
		screenWidth = Display.getDefault().getBounds().width;
		screenHeight = Display.getDefault().getBounds().height;
	}

	/**
	 * load the configuration file
	 */
	protected void loadConfig() {
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(Navigator.CURRENT_PATH
					+ "/config.properties"));
			Properties newProperties = new Properties();
			newProperties.load(in);

			backgroundImage = newProperties.getProperty("backgroundImage");
			navigationIcon = newProperties.getProperty("navigationIcon");
			backgroundImageStatus = Integer.parseInt(newProperties
					.getProperty("backgroundImageStatus"));
			labelBackgroundTransparent = Boolean.parseBoolean(newProperties
					.getProperty("labelBackgroundTransparent"));
			backgroundColorRed = Integer.parseInt(newProperties
					.getProperty("backgroundColorRed"));
			backgroundColorGreen = Integer.parseInt(newProperties
					.getProperty("backgroundColorGreen"));
			backgroundColorBlue = Integer.parseInt(newProperties
					.getProperty("backgroundColorBlue"));
			labelBackgroundColorRed = Integer.parseInt(newProperties
					.getProperty("labelBackgroundColorRed"));
			labelBackgroundColorGreen = Integer.parseInt(newProperties
					.getProperty("labelBackgroundColorGreen"));
			labelBackgroundColorBlue = Integer.parseInt(newProperties
					.getProperty("labelBackgroundColorBlue"));
			labelForegroundColorRed = Integer.parseInt(newProperties
					.getProperty("labelForegroundColorRed"));
			labelForegroundColorGreen = Integer.parseInt(newProperties
					.getProperty("labelForegroundColorGreen"));
			labelForegroundColorBlue = Integer.parseInt(newProperties
					.getProperty("labelForegroundColorBlue"));
			labelFontName = newProperties.getProperty("labelFontName");
			labelFontSize = Integer.parseInt(newProperties
					.getProperty("labelFontSize"));
			labelFontStyle = Integer.parseInt(newProperties
					.getProperty("labelFontStyle"));
			buttonWidth = Integer.parseInt(newProperties
					.getProperty("buttonWidth"));
			buttonHeight = Integer.parseInt(newProperties
					.getProperty("buttonHeight"));
			buttonImageWidth = Integer.parseInt(newProperties
					.getProperty("buttonImageWidth"));
			buttonImageHeight = Integer.parseInt(newProperties
					.getProperty("buttonImageHeight"));
			buttonBackgroundColorRed = Integer.parseInt(newProperties
					.getProperty("buttonBackgroundColorRed"));
			buttonBackgroundColorGreen = Integer.parseInt(newProperties
					.getProperty("buttonBackgroundColorGreen"));
			buttonBackgroundColorBlue = Integer.parseInt(newProperties
					.getProperty("buttonBackgroundColorBlue"));
			autoExit = Boolean.parseBoolean(newProperties
					.getProperty("autoExit"));
			width = Integer.parseInt(newProperties.getProperty("width"));
			height = Integer.parseInt(newProperties.getProperty("height"));
			maximized = Boolean.parseBoolean(newProperties
					.getProperty("maximized"));
			isCenter = Boolean.parseBoolean(newProperties
					.getProperty("isCenter"));
			transparency = Integer.parseInt(newProperties
					.getProperty("transparency"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * refresh the configuration file
	 */
	protected void refreshConfig() {
		backgroundImage = Navigator.CURRENT_PATH + "/image/backgroundImage.png";
		navigationIcon = Navigator.CURRENT_PATH + "/image/navigationIcon.png";
	}

	/**
	 * save the configuration file
	 */
	protected void saveConfig() {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(Navigator.CURRENT_PATH
					+ "/config.properties"));
			Properties properties = new Properties();

			properties.setProperty("backgroundImage", backgroundImage);
			properties.setProperty("navigationIcon", navigationIcon);
			properties.setProperty("backgroundImageStatus",
					String.valueOf(backgroundImageStatus));
			properties.setProperty("labelBackgroundTransparent",
					String.valueOf(labelBackgroundTransparent));
			properties.setProperty("backgroundColorRed",
					String.valueOf(backgroundColorRed));
			properties.setProperty("backgroundColorGreen",
					String.valueOf(backgroundColorGreen));
			properties.setProperty("backgroundColorBlue",
					String.valueOf(backgroundColorBlue));
			properties.setProperty("labelBackgroundColorRed",
					String.valueOf(labelBackgroundColorRed));
			properties.setProperty("labelBackgroundColorGreen",
					String.valueOf(labelBackgroundColorGreen));
			properties.setProperty("labelBackgroundColorBlue",
					String.valueOf(labelBackgroundColorBlue));
			properties.setProperty("labelForegroundColorRed",
					String.valueOf(labelForegroundColorRed));
			properties.setProperty("labelForegroundColorGreen",
					String.valueOf(labelForegroundColorGreen));
			properties.setProperty("labelForegroundColorBlue",
					String.valueOf(labelForegroundColorBlue));
			properties.setProperty("labelFontName", labelFontName);
			properties.setProperty("labelFontSize",
					String.valueOf(labelFontSize));
			properties.setProperty("labelFontStyle",
					String.valueOf(labelFontStyle));
			properties.setProperty("buttonWidth", String.valueOf(buttonWidth));
			properties
					.setProperty("buttonHeight", String.valueOf(buttonHeight));
			properties.setProperty("buttonImageWidth",
					String.valueOf(buttonImageWidth));
			properties.setProperty("buttonImageHeight",
					String.valueOf(buttonImageHeight));
			properties.setProperty("buttonBackgroundColorRed",
					String.valueOf(buttonBackgroundColorRed));
			properties.setProperty("buttonBackgroundColorGreen",
					String.valueOf(buttonBackgroundColorGreen));
			properties.setProperty("buttonBackgroundColorBlue",
					String.valueOf(buttonBackgroundColorBlue));
			properties.setProperty("autoExit", String.valueOf(autoExit));
			properties.setProperty("width", String.valueOf(width));
			properties.setProperty("height", String.valueOf(height));
			properties.setProperty("maximized", String.valueOf(maximized));
			properties.setProperty("isCenter", String.valueOf(isCenter));
			properties
					.setProperty("transparency", String.valueOf(transparency));
			properties.store(out, "config");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * refresh the window's size
	 */
	protected void refreshWindowSize() {
		if (maximized) {
			shell.setMaximized(true);
		} else {
			shell.setBounds((screenWidth - width) / 2,
					(screenHeight - height) / 2, width, height);
		}
	}

	/**
	 * refresh the window's icon image
	 */
	protected void refreshWindowIcon() {
		if (!new File(navigationIcon).exists()) {
			navigationIcon = Navigator.CURRENT_PATH
					+ "/image/navigationIcon.png";
		}
		shell.setImage(new Image(display, navigationIcon));

	}

	/**
	 * refresh the window's background image or color
	 */
	protected void refreshWindowBackground() {
		if (!new File(backgroundImage).exists()) {
			backgroundImage = Navigator.CURRENT_PATH
					+ "/image/backgroundImage.png";
		}

		if (backgroundImageStatus == 1) {
			shell.setBackgroundImage(new Image(display, backgroundImage));
		} else if (backgroundImageStatus == 2) {
			if (maximized) {
				shell.setBackgroundImage(new Image(display, new ImageData(
						backgroundImage).scaledTo(screenWidth, screenHeight)));
			} else {
				shell.setBackgroundImage(new Image(display, new ImageData(
						backgroundImage).scaledTo(width, height)));
			}
		} else {
			shell.setBackgroundImage(null);
			shell.setBackground(new Color(display, backgroundColorRed,
					backgroundColorGreen, backgroundColorBlue));
		}

		if (labelBackgroundTransparent) {
			shell.setBackgroundMode(SWT.INHERIT_DEFAULT);
		} else {
			shell.setBackgroundMode(SWT.INHERIT_NONE);
		}
	}

	/**
	 * refresh the window's transparency.
	 */
	protected void refreshTransparency() {
		shell.setAlpha(transparency);
	}

	/**
	 * get the opinions' status
	 */
	protected void getOpinionStatus() {
		backgroundImage0 = backgroundImage;
		navigationIcon0 = navigationIcon;
		width0 = width;
		height0 = height;
		maximized0 = maximized;
		backgroundImageStatus0 = backgroundImageStatus;
		labelBackgroundTransparent0 = labelBackgroundTransparent;
		backgroundColorRed0 = backgroundColorRed;
		backgroundColorGreen0 = backgroundColorGreen;
		backgroundColorBlue0 = backgroundColorBlue;
		labelBackgroundColorRed0 = labelBackgroundColorRed;
		labelBackgroundColorGreen0 = labelBackgroundColorGreen;
		labelBackgroundColorBlue0 = labelBackgroundColorBlue;
		labelForegroundColorRed0 = labelForegroundColorRed;
		labelForegroundColorGreen0 = labelForegroundColorGreen;
		labelForegroundColorBlue0 = labelForegroundColorBlue;
		labelFontName0 = labelFontName;
		labelFontSize0 = labelFontSize;
		labelFontStyle0 = labelFontStyle;
		buttonWidth0 = buttonWidth;
		buttonHeight0 = buttonHeight;
		buttonImageWidth0 = buttonImageWidth;
		buttonImageHeight0 = buttonImageHeight;
		buttonBackgroundColorRed0 = buttonBackgroundColorRed;
		buttonBackgroundColorGreen0 = buttonBackgroundColorGreen;
		buttonBackgroundColorBlue0 = buttonBackgroundColorBlue;
		autoExit0 = autoExit;
		isCenter0 = isCenter;
		transparency0 = transparency;
	}
}
