package edu.buaa.sem.smartshortcut;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

public class Navigator {

	public static String LAST_APPLY_PATH;
	public static String CURRENT_PATH;

	private ConfigAndRefresh configAndRefresh;

	private int screenWidth, screenHeight;
	private Label[] label = new Label[100];
	private String[] labelName = new String[100];
	private Button[] button = new Button[100];
	private String[] buttonName = new String[100];
	private String[] buttonPath = new String[100];
	private String[] buttonBackgroundImagePath = new String[100];
	private int labelCount = 0, buttonCount = 0;

	private Control currentSelectedControl;
	private Label initialCurrentSelectedControl;

	private String jarPath;
	private String jarName;

	private ControlMouseTotalListener controlMouseTotalListener;
	private ShellWindowCloseListener shellWindowCloseListener;

	private Display display;
	private Shell shell;
	private Menu menuBar;
	private MenuItem submenuItem, submenuItem1, submenuItem2, submenuItem3;
	private Menu submenu, submenu1, submenu2, submenu3;
	private Menu popmenu;
	private MenuItem push1, push2, push3, push4, push5, push6, push7, push8,
			push9, push10, push11, push12, push13, push14, push15, push16;
	private MenuItem poppush1;

	/**
	 * create the Navigator window
	 */
	public Navigator() {
		CURRENT_PATH = System.getProperty("user.dir");
		LAST_APPLY_PATH = CURRENT_PATH;

		screenWidth = Display.getDefault().getBounds().width;
		screenHeight = Display.getDefault().getBounds().height;
		display = Display.getDefault();
		createContents();
		initContens();
		shell.open();
		shell.layout();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * create contents of the window
	 */
	protected void createContents() {
		controlMouseTotalListener = new ControlMouseTotalListener();
		shellWindowCloseListener = new ShellWindowCloseListener();

		shell = new Shell();
		shell.setText("SmartShortcut v1.2");
		shell.setLayout(null);

		shell.addListener(SWT.Close, shellWindowCloseListener);

		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		submenuItem = new MenuItem(menuBar, SWT.CASCADE);
		submenuItem.setText("File(&F)");

		submenu = new Menu(submenuItem);
		submenuItem.setMenu(submenu);

		push1 = new MenuItem(submenu, SWT.NONE);
		push1.setImage(new Image(display, CURRENT_PATH + "/image/open.png"));
		push1.setText("Open(&O)...\tCtrl+O");
		push1.setAccelerator(SWT.CTRL + 'O');

		push2 = new MenuItem(submenu, SWT.NONE);
		push2.setImage(new Image(display, CURRENT_PATH + "/image/save.png"));
		push2.setEnabled(false);
		push2.setText("Save(&S)\tCtrl+S");
		push2.setAccelerator(SWT.CTRL + 'S');

		push3 = new MenuItem(submenu, SWT.NONE);
		push3.setImage(new Image(display, CURRENT_PATH + "/image/saveAs.png"));
		push3.setText("Save As(&A)...");

		new MenuItem(submenu, SWT.SEPARATOR);

		push4 = new MenuItem(submenu, SWT.NONE);
		push4.setImage(new Image(display, CURRENT_PATH + "/image/exit.png"));
		push4.setText("Exit(&X)\tCtrl+Q");
		push4.setAccelerator(SWT.CTRL + 'Q');

		submenuItem1 = new MenuItem(menuBar, SWT.CASCADE);
		submenuItem1.setText("Edit(&E)");

		submenu1 = new Menu(submenuItem1);
		submenuItem1.setMenu(submenu1);

		push5 = new MenuItem(submenu1, SWT.NONE);
		push5.setImage(new Image(display, CURRENT_PATH + "/image/label.png"));
		push5.setText("Add Label(&L)\tCtrl+L");
		push5.setAccelerator(SWT.CTRL + 'L');

		push6 = new MenuItem(submenu1, SWT.NONE);
		push6.setImage(new Image(display, CURRENT_PATH + "/image/button.png"));
		push6.setText("Add Button(&B)\tCtrl+B");
		push6.setAccelerator(SWT.CTRL + 'B');

		push7 = new MenuItem(submenu1, SWT.NONE);
		push7.setImage(new Image(display, CURRENT_PATH + "/image/refresh.png"));
		push7.setText("Refresh(&R)\tF5");
		push7.setAccelerator(SWT.F5);

		push8 = new MenuItem(submenu1, SWT.NONE);
		push8.setImage(new Image(display, CURRENT_PATH + "/image/delete.png"));
		push8.setEnabled(false);
		push8.setText("Delete(&D)\tDelete");
		push8.setAccelerator(SWT.DEL);

		new MenuItem(submenu1, SWT.SEPARATOR);

		push9 = new MenuItem(submenu1, SWT.NONE);
		push9.setImage(new Image(display, CURRENT_PATH + "/image/labelFont.png"));
		push9.setEnabled(false);
		push9.setText("Label Font(&F)\tCtrl+F");
		push9.setAccelerator(SWT.CTRL + 'F');

		push10 = new MenuItem(submenu1, SWT.NONE);
		push10.setImage(new Image(display, CURRENT_PATH
				+ "/image/labelColor.png"));
		push10.setEnabled(false);
		push10.setText("Label Color(&C)\tCtrl+C");
		push10.setAccelerator(SWT.CTRL + 'C');

		push11 = new MenuItem(submenu1, SWT.NONE);
		push11.setImage(new Image(display, CURRENT_PATH
				+ "/image/buttonIcon.png"));
		push11.setEnabled(false);
		push11.setText("Button Icon(&I)\tCtrl+I");
		push11.setAccelerator(SWT.CTRL + 'I');

		push12 = new MenuItem(submenu1, SWT.NONE);
		push12.setImage(new Image(display, CURRENT_PATH
				+ "/image/buttonPath.png"));
		push12.setEnabled(false);
		push12.setText("Button Path(&P)\tCtrl+P");
		push12.setAccelerator(SWT.CTRL + 'P');

		push13 = new MenuItem(submenu1, SWT.NONE);
		push13.setImage(new Image(display, CURRENT_PATH + "/image/rename.png"));
		push13.setEnabled(false);
		push13.setText("Rename(&M)\tCtrl+M");
		push13.setAccelerator(SWT.CTRL + 'M');

		submenuItem2 = new MenuItem(menuBar, SWT.CASCADE);
		submenuItem2.setText("Window(&W)");

		submenu2 = new Menu(submenuItem2);
		submenuItem2.setMenu(submenu2);

		push14 = new MenuItem(submenu2, SWT.NONE);
		push14.setImage(new Image(display, CURRENT_PATH
				+ "/image/preference.png"));
		push14.setText("Preference(&P)");

		submenuItem3 = new MenuItem(menuBar, SWT.CASCADE);
		submenuItem3.setText("Help(&H)");

		submenu3 = new Menu(submenuItem3);
		submenuItem3.setMenu(submenu3);

		push15 = new MenuItem(submenu3, SWT.NONE);
		push15.setImage(new Image(display, CURRENT_PATH + "/image/help.png"));
		push15.setText("Instructiont(&N)\tF1");
		push15.setAccelerator(SWT.F1);

		new MenuItem(submenu3, SWT.SEPARATOR);

		push16 = new MenuItem(submenu3, SWT.NONE);
		push16.setImage(new Image(display, CURRENT_PATH + "/image/about.png"));
		push16.setText("About(&A)");

		push1.addSelectionListener(new OpenListener());
		push2.addSelectionListener(new SaveListener());
		push3.addSelectionListener(new SaveAsListener());
		push4.addSelectionListener(new ExitListener());
		push5.addSelectionListener(new AddLabelListener());
		push6.addSelectionListener(new AddButtonListener());
		push7.addSelectionListener(new RefreshListener());
		push8.addSelectionListener(new DeleteListener());
		push9.addSelectionListener(new FontListener());
		push10.addSelectionListener(new ForeColorListener());
		push11.addSelectionListener(new ButtonIconListener());
		push12.addSelectionListener(new ButtonPathListener());
		push13.addSelectionListener(new RenameListener());
		push14.addSelectionListener(new PreferenceListener());
		push15.addSelectionListener(new HelpListener());
		push16.addSelectionListener(new AboutListener());

		popmenu = new Menu(shell, SWT.POP_UP);

		poppush1 = new MenuItem(popmenu, SWT.NONE);
		poppush1.setText("Refresh");

		shell.setMenu(popmenu);

		poppush1.addSelectionListener(new RefreshListener());
	}

	/**
	 * initialize contents of the window
	 */
	protected void initContens() {
		configAndRefresh = new ConfigAndRefresh(shell, display);
		configAndRefresh.loadConfig();
		configAndRefresh.refreshConfig();
		configAndRefresh.saveConfig();
		configAndRefresh.getOpinionStatus();

		configAndRefresh.refreshWindowSize();
		configAndRefresh.refreshWindowIcon();
		configAndRefresh.refreshWindowBackground();
		configAndRefresh.refreshTransparency();

		initialCurrentSelectedControl = new Label(shell, SWT.BORDER);
		currentSelectedControl = (Control) initialCurrentSelectedControl;

	}

	/**
	 * save the window as an executable jar file
	 */
	protected void saveJarFile(String info, String path, String fileName) {
		// prompt message during saving
		Shell describe = new Shell(shell, SWT.TITLE);
		describe.setBounds(screenWidth / 2 - 100, screenHeight / 2 - 75, 200,
				150);
		describe.setText("info");
		Label describeLabel = new Label(describe, SWT.FLAT);
		describeLabel.setText(info);
		describeLabel.setBounds(40, 50, 150, 100);
		describe.open();

		// assemble java String
		FileOutputStream f1 = null;
		Writer writer1 = null;
		try {
			f1 = new FileOutputStream(new File(CURRENT_PATH + "/Runner1.java"));
			writer1 = new OutputStreamWriter(f1);
			String content1 = "import org.eclipse.swt.events.MouseEvent;\nimport org.eclipse.swt.even"
					+ "ts.MouseListener;\nimport org.eclipse.swt.graphics.Color;\nimport org.eclipse.swt.gr"
					+ "aphics.Cursor;\nimport org.eclipse.swt.graphics.Font;\nimport org.eclipse.swt.grap"
					+ "hics.FontData;\nimport org.eclipse.swt.graphics.Image;\nimport org.eclipse.swt.graph"
					+ "ics.ImageData;\nimport org.eclipse.swt.widgets.Button;\nimport org.eclipse.swt.widg"
					+ "ets.Control;\nimport org.eclipse.swt.widgets.Display;\nimport org.eclipse.swt.widget"
					+ "s.Label;\nimport org.eclipse.swt.widgets.Shell;\nimport org.eclipse.swt.SWT;\nimport or"
					+ "g.eclipse.swt.widgets.MessageBox;\nimport java.io.File;\n";// import
			String content2 = "public class Runner1 {\npublic static void main(String[] args) {\n"
					+ "new N_swt();\n}\n}\n";// main class
			String content3 = "class N_swt {\nprivate Display d;\nprivate Shell s;\n;\nprivate String[] buttonPath"
					+ "=new String[100];\nprivate Label[] label=new Label[100];\nprivate Button[] button=new Button"
					+ "[100];\nint ln,bn;\nprivate MyMouseListener mml;\nboolean autoExit="
					+ configAndRefresh.autoExit
					+ ";\nN_swt() {\nmml=new MyMouseListener();\nd=new Display();\ns=new Shell(d);\ns.set"
					+ "Layout(null);\ns.setText(\""
					+ fileName.substring(0, fileName.length() - 4)
					+ "\");\nif(new File(\""
					+ configAndRefresh.navigationIcon.replace('\\', '/')
					+ "\").exists())s.setImage(new Image(d,\""
					+ configAndRefresh.navigationIcon.replace('\\', '/')
					+ "\"));\n";
			String content4a = "s.setSize(" + configAndRefresh.width + ","
					+ configAndRefresh.height + ");\n";
			String content4aa = "s.setLocation("
					+ (screenWidth - configAndRefresh.width) / 2 + ","
					+ (screenHeight - configAndRefresh.height) / 2 + ");\n";
			String content4b = "s.setMaximized(true);\n";
			String content5a = "s.setBackgroundImage(null);\ns.setBackground(new Color(d,"
					+ configAndRefresh.backgroundColorRed
					+ ","
					+ configAndRefresh.backgroundColorGreen
					+ ","
					+ configAndRefresh.backgroundColorBlue + "));\n";
			String content5b = "if(new File(\""
					+ configAndRefresh.backgroundImage.replace('\\', '/')
					+ "\").exists())s.setBackgroundImage(new Image(d,\""
					+ configAndRefresh.backgroundImage.replace('\\', '/')
					+ "\"));\n";
			String content5c = "if("
					+ configAndRefresh.maximized
					+ "==false) {\nif(new File(\""
					+ configAndRefresh.backgroundImage.replace('\\', '/')
					+ "\").exists())s.setBackgroundImage(new Image(d,new ImageData(\""
					+ configAndRefresh.backgroundImage.replace('\\', '/')
					+ "\").scaledTo("
					+ configAndRefresh.width
					+ ","
					+ configAndRefresh.height
					+ ")));\n}\nelse {\nif(new File(\""
					+ configAndRefresh.backgroundImage.replace('\\', '/')
					+ "\").exists())s.setBackgroundImage(new Image(d,new ImageData(\""
					+ configAndRefresh.backgroundImage.replace('\\', '/')
					+ "\").scaledTo(" + screenWidth + "," + screenHeight
					+ ")));\n}\n";
			String content6a = "s.setBackgroundMode(SWT.INHERIT_DEFAULT);\n";
			String content6b = "s.setBackgroundMode(SWT.INHERIT_NONE);\n";
			String content7 = "s.setAlpha(" + configAndRefresh.transparency
					+ ");\n";
			String contentlast = "s.open();\nwhile(!s.isDisposed()) {\nif(!d.readAndDispatch())\nd.sleep();\n}\n"
					+ "d.dispose();\n}\nclass MyMouseListener implements MouseListener{ \npublic void mo"
					+ "useDoubleClick(MouseEvent e) {\nint i=0;\nControl c=(Control)e.getSource();\nfor(i="
					+ "0;i<100;i++) {\nif(c.equals(button[i]))break;\n}\nif((buttonPath[i].substring(buttonPath[i].length()-3))"
					+ ".toLowerCase().equals(\"exe\")) {\ntry {\nRuntime.getRuntime().exec(buttonPath[i]);\nif(autoExit"
					+ ")s.dispose();\n}catch(Exception e1) {\nMessageBox messageBox=new MessageBox"
					+ "(s,SWT.ICON_ERROR|SWT.OK);\nmessageBox.setMessage(\"Error! Please check the path"
					+ "\");\nmessageBox.setText(\"Error\");\nif(messageBox.open()==SWT.OK);\n}\n}\ne"
					+ "lse if((buttonPath[i].substring(buttonPath[i].length()-3)).toLowerCase().equals(\"jar\"))"
					+ "{\ntry {\nString strcmd[]=new String []{\"cmd\",\"/c\",buttonPath[i]};\nRuntime.getRuntime().exec(strcmd);\n"
					+ "if(autoExit)s.dispose();\n}catch(Exception e1) {\nMessageBox messageBox=new M"
					+ "essageBox(s,SWT.ICON_ERROR|SWT.OK);\nmessageBox.setMessage(\"Error! Please check the path"
					+ "\");\nmessageBox.setText(\"Error\");\nif(messageBox.open()==SWT.OK); \n}"
					+ "\n}\nelse if((buttonPath[i].substring(buttonPath[i].length()-3)).toLowerCase().equals(\"bat\")) {\ntry"
					+ " {\nString strcmd[]=new String []{\"cmd\",\"/c\",\"start\",buttonPath[i]};\nRuntime.getRuntime().exec(strcm"
					+ "d);\nif(autoExit)s.dispose();\n}catch(Exception e1) {\nMessageBox mes"
					+ "sageBox=new MessageBox(s,SWT.ICON_ERROR|SWT.OK);\nmessageBox.setMessage(\""
					+ "Error! Please check the path\");\nmessageBox.setText(\"Error\");\nif(messageBox.ope"
					+ "n()==SWT.OK); \n}\n}\n}\npublic void mouseDown(MouseEvent e) {}\npublic void mous"
					+ "eUp(MouseEvent e) {} \n}\n}\n";
			// generate java file
			writer1.write(content1);
			writer1.write(content2);
			writer1.write(content3);
			if (!configAndRefresh.maximized) {
				writer1.write(content4a);
				if (configAndRefresh.isCenter) {
					writer1.write(content4aa);
				}
			} else {
				writer1.write(content4b);
			}
			if (configAndRefresh.backgroundImageStatus == 0) {
				writer1.write(content5a);
			} else if (configAndRefresh.backgroundImageStatus == 1) {
				writer1.write(content5b);
			} else {
				writer1.write(content5c);
			}
			if (configAndRefresh.labelBackgroundTransparent) {
				writer1.write(content6a);
				for (int ii = 0; ii < labelCount; ii++) {
					writer1.write("label["
							+ ii
							+ "]=new Label(s,SWT.FLAT|SWT.CENTER);\nlabel["
							+ ii
							+ "].setText(\""
							+ labelName[ii]
							+ "\");\nlabel["
							+ ii
							+ "].setSize("
							+ label[ii].getSize().x
							+ ","
							+ label[ii].getSize().y
							+ ");\nlabel["
							+ ii
							+ "].setLocation("
							+ label[ii].getLocation().x
							+ ","
							+ label[ii].getLocation().y
							+ ");\nlabel["
							+ ii
							+ "].setForeground(new Color(d,"
							+ label[ii].getForeground().getRed()
							+ ","
							+ label[ii].getForeground().getGreen()
							+ ","
							+ label[ii].getForeground().getBlue()
							+ "));\nlabel["
							+ ii
							+ "].setFont(new Font(d,new FontData(\""
							+ (label[ii].getFont().getFontData())[0].getName()
							+ "\","
							+ (label[ii].getFont().getFontData())[0]
									.getHeight() + ","
							+ (label[ii].getFont().getFontData())[0].getStyle()
							+ ")));\n");
				}
			} else {
				writer1.write(content6b);
				for (int ii = 0; ii < labelCount; ii++) {
					writer1.write("label["
							+ ii
							+ "]=new Label(s,SWT.FLAT|SWT.CENTER);\nlabel["
							+ ii
							+ "].setText(\""
							+ labelName[ii]
							+ "\");\nlabel["
							+ ii
							+ "].setSize("
							+ label[ii].getSize().x
							+ ","
							+ label[ii].getSize().y
							+ ");\nlabel["
							+ ii
							+ "].setLocation("
							+ label[ii].getLocation().x
							+ ","
							+ label[ii].getLocation().y
							+ ");\nlabel["
							+ ii
							+ "].setBackground(new Color(d,"
							+ label[ii].getBackground().getRed()
							+ ","
							+ label[ii].getBackground().getGreen()
							+ ","
							+ label[ii].getBackground().getBlue()
							+ "));\nlabel["
							+ ii
							+ "].setForeground(new Color(d,"
							+ label[ii].getForeground().getRed()
							+ ","
							+ label[ii].getForeground().getGreen()
							+ ","
							+ label[ii].getForeground().getBlue()
							+ "));\nlabel["
							+ ii
							+ "].setFont(new Font(d,new FontData(\""
							+ (label[ii].getFont().getFontData())[0].getName()
							+ "\","
							+ (label[ii].getFont().getFontData())[0]
									.getHeight() + ","
							+ (label[ii].getFont().getFontData())[0].getStyle()
							+ ")));\n");
				}
			}
			for (int jj = 0; jj < buttonCount; jj++) {
				writer1.write("button[" + jj
						+ "]=new Button(s,SWT.FLAT);\nbutton[" + jj
						+ "].setToolTipText(\"" + buttonName[jj]
						+ "\");\nbutton[" + jj + "].setSize("
						+ button[jj].getSize().x + "," + button[jj].getSize().y
						+ ");\nbutton[" + jj + "].setLocation("
						+ button[jj].getLocation().x + ","
						+ button[jj].getLocation().y + ");\nbutton[" + jj
						+ "].addMouseListener(mml);\nbuttonPath[" + jj + "]=\""
						+ buttonPath[jj].replace('\\', '/') + "\";\n");
				if (button[jj].getImage() != null)
					writer1.write("button[" + jj
							+ "].setImage(new Image(d,new ImageData(\""
							+ buttonBackgroundImagePath[jj].replace('\\', '/')
							+ "\").scaledTo("
							+ configAndRefresh.buttonImageWidth + ","
							+ configAndRefresh.buttonImageHeight + ")));\n");
			}
			if (configAndRefresh.transparency < 255) {
				writer1.write(content7);
			}
			writer1.write(contentlast);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer1 != null) {
				try {
					writer1.close();
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}
		}

		// compile java file
		JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
		javaCompiler.run(null, null, null, CURRENT_PATH + "/Runner1.java");

		FileOutputStream o = null;
		try {
			o = new FileOutputStream(new File(CURRENT_PATH
					+ "/feature.properties"));
			Properties feature = new Properties();

			feature.setProperty("backgroundImage",
					configAndRefresh.backgroundImage);
			feature.setProperty("navigationIcon",
					configAndRefresh.navigationIcon);
			feature.setProperty("width", String.valueOf(configAndRefresh.width));
			feature.setProperty("height",
					String.valueOf(configAndRefresh.height));
			feature.setProperty("maximized",
					String.valueOf(configAndRefresh.maximized));
			feature.setProperty("backgroundImageStatus",
					String.valueOf(configAndRefresh.backgroundImageStatus));
			feature.setProperty("labelBackgroundTransparent",
					String.valueOf(configAndRefresh.labelBackgroundTransparent));
			feature.setProperty("backgroundColorRed",
					String.valueOf(configAndRefresh.backgroundColorRed));
			feature.setProperty("backgroundColorGreen",
					String.valueOf(configAndRefresh.backgroundColorGreen));
			feature.setProperty("backgroundColorBlue",
					String.valueOf(configAndRefresh.backgroundColorBlue));
			feature.setProperty("autoExit",
					String.valueOf(configAndRefresh.autoExit));
			feature.setProperty("labelCount", String.valueOf(labelCount));
			feature.setProperty("buttonCount", String.valueOf(buttonCount));
			feature.setProperty("transparency",
					String.valueOf(configAndRefresh.transparency));

			for (int ii = 0; ii < labelCount; ii++) {
				feature.setProperty("labelName[" + ii + "]", labelName[ii]);
				feature.setProperty("lx[" + ii + "]",
						String.valueOf(label[ii].getLocation().x));
				feature.setProperty("ly[" + ii + "]",
						String.valueOf(label[ii].getLocation().y));
				feature.setProperty("lxx[" + ii + "]",
						String.valueOf(label[ii].getSize().x));
				feature.setProperty("lyy[" + ii + "]",
						String.valueOf(label[ii].getSize().y));
				feature.setProperty("labelBackgroundColorRed[" + ii + "]",
						String.valueOf(label[ii].getBackground().getRed()));
				feature.setProperty("labelBackgroundColorGreen[" + ii + "]",
						String.valueOf(label[ii].getBackground().getGreen()));
				feature.setProperty("labelBackgroundColorBlue[" + ii + "]",
						String.valueOf(label[ii].getBackground().getBlue()));
				feature.setProperty("labelForegroundColorRed[" + ii + "]",
						String.valueOf(label[ii].getForeground().getRed()));
				feature.setProperty("labelForegroundColorGreen[" + ii + "]",
						String.valueOf(label[ii].getForeground().getGreen()));
				feature.setProperty("labelForegroundColorBlue[" + ii + "]",
						String.valueOf(label[ii].getForeground().getBlue()));
				feature.setProperty("labelFontName[" + ii + "]",
						(label[ii].getFont().getFontData())[0].getName());
				feature.setProperty("labelFontSize[" + ii + "]", String
						.valueOf((label[ii].getFont().getFontData())[0]
								.getHeight()));
				feature.setProperty("labelFontStyle[" + ii + "]", String
						.valueOf((label[ii].getFont().getFontData())[0]
								.getStyle()));
			}
			for (int jj = 0; jj < buttonCount; jj++) {
				feature.setProperty("buttonName[" + jj + "]", buttonName[jj]);
				feature.setProperty("bx[" + jj + "]",
						String.valueOf(button[jj].getLocation().x));
				feature.setProperty("by[" + jj + "]",
						String.valueOf(button[jj].getLocation().y));
				feature.setProperty("bxx[" + jj + "]",
						String.valueOf(button[jj].getSize().x));
				feature.setProperty("byy[" + jj + "]",
						String.valueOf(button[jj].getSize().y));
				feature.setProperty("buttonPath[" + jj + "]", buttonPath[jj]);
				if (button[jj].getImage() != null) {
					feature.setProperty(
							"buttonBackgroundImagePath[" + jj + "]",
							buttonBackgroundImagePath[jj]);
				} else {
					feature.setProperty(
							"buttonBackgroundImagePath[" + jj + "]", "");
				}
			}

			feature.store(o, "feature");
		} catch (IOException ee) {
			ee.printStackTrace();
		} finally {
			if (o != null) {
				try {
					o.close();
				} catch (IOException eee) {
					eee.printStackTrace();
				}
			}
		}

		// generate manifest.mf
		FileOutputStream f2 = null;
		Writer writer2 = null;
		try {
			f2 = new FileOutputStream(new File(CURRENT_PATH + "/MANIFEST.MF"));
			writer2 = new OutputStreamWriter(f2);
			String content1 = "Manifest-Version: 1.0\nMain-Class: Runner1\nCreated-By: Crifny123";
			writer2.write(content1);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer2 != null) {
				try {
					writer2.close();
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}
		}

		boolean success = false;
		File tempJar = null;
		JarFile jar = null;
		JarOutputStream newJar = null;
		try {
			tempJar = File.createTempFile(CURRENT_PATH + "/embed.jar", null);
			jar = new JarFile(CURRENT_PATH + "/embed.jar");
			newJar = new JarOutputStream(new FileOutputStream(tempJar));

			byte buffer[] = new byte[2048];
			int bytesRead = 0;
			Enumeration<?> entries = jar.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = (JarEntry) entries.nextElement();
				InputStream is = jar.getInputStream(entry);
				newJar.putNextEntry(entry);
				while ((bytesRead = is.read(buffer)) != -1) {
					newJar.write(buffer, 0, bytesRead);
				}
			}

			// add compiled file
			FileInputStream fis1 = new FileInputStream(new File(CURRENT_PATH
					+ "/N_swt.class"));
			JarEntry entry = new JarEntry("N_swt.class");
			newJar.putNextEntry(entry);
			while ((bytesRead = fis1.read(buffer)) != -1) {
				newJar.write(buffer, 0, bytesRead);
			}
			fis1.close();

			FileInputStream fis2 = new FileInputStream(new File(CURRENT_PATH
					+ "/Runner1.class"));
			entry = new JarEntry("Runner1.class");
			newJar.putNextEntry(entry);
			while ((bytesRead = fis2.read(buffer)) != -1) {
				newJar.write(buffer, 0, bytesRead);
			}
			fis2.close();

			FileInputStream fis3 = new FileInputStream(new File(CURRENT_PATH
					+ "/N_swt$MyMouseListener.class"));
			entry = new JarEntry("N_swt$MyMouseListener.class");
			newJar.putNextEntry(entry);
			while ((bytesRead = fis3.read(buffer)) != -1) {
				newJar.write(buffer, 0, bytesRead);
			}
			fis3.close();

			FileInputStream fis4 = new FileInputStream(CURRENT_PATH
					+ "/feature.properties");
			entry = new JarEntry("feature.properties");
			newJar.putNextEntry(entry);
			while ((bytesRead = fis4.read(buffer)) != -1) {
				newJar.write(buffer, 0, bytesRead);
			}
			fis4.close();

			FileInputStream fis5 = new FileInputStream(new File(CURRENT_PATH
					+ "/MANIFEST.MF"));
			entry = new JarEntry("META-INF/MANIFEST.MF");
			newJar.putNextEntry(entry);
			while ((bytesRead = fis5.read(buffer)) != -1) {
				newJar.write(buffer, 0, bytesRead);
			}
			fis5.close();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (newJar != null) {
				try {
					newJar.close();
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}
			if (jar != null) {
				try {
					jar.close();
				} catch (IOException ee) {
					ee.printStackTrace();
				}
			}
			describe.dispose();
		}

		if (!success) {
			tempJar.delete();
			MessageBox messageBox2 = new MessageBox(shell, SWT.ICON_ERROR);
			messageBox2.setMessage("Saving failed");
			messageBox2.setText("Error");
			if (messageBox2.open() == SWT.CANCEL)
				;
		} else {
			MessageBox messageBox3 = new MessageBox(shell, SWT.ICON_INFORMATION);
			messageBox3.setMessage("Save successful");
			messageBox3.setText("Info");
			if (messageBox3.open() == SWT.CANCEL)
				;

			jarPath = path;
			jarName = fileName;
			shell.setText("SmartShortcut v1.2" + jarName);
		}

		// remove temp file
		File tempFile1 = new File(CURRENT_PATH + "/Runner1.java");
		File tempFile2 = new File(CURRENT_PATH + "/N_swt.class");
		File tempFile3 = new File(CURRENT_PATH + "/Runner1.class");
		File tempFile4 = new File(CURRENT_PATH + "/N_swt$MyMouseListener.class");
		File tempFile5 = new File(CURRENT_PATH + "/feature.properties");
		File tempFile6 = new File(CURRENT_PATH + "/MANIFEST.MF");
		if (tempFile1.exists()) {
			tempFile1.delete();
		}
		if (tempFile2.exists()) {
			tempFile2.delete();
		}
		if (tempFile3.exists()) {
			tempFile3.delete();
		}
		if (tempFile4.exists()) {
			tempFile4.delete();
		}
		if (tempFile5.exists()) {
			tempFile5.delete();
		}
		if (tempFile6.exists()) {
			tempFile6.delete();
		}
		File newJarFile = new File(path);
		if (newJarFile.exists()) {
			newJarFile.delete();
		}
		tempJar.renameTo(newJarFile);
	}

	/**
	 * change the imageIcon to a png picture and save
	 */
	protected String savePng(ImageIcon icon, String name) {
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics g = bufferedImage.getGraphics();
		g.drawImage(icon.getImage(), 0, 0, icon.getImageObserver());
		File saveFolder = new File(System.getProperties().getProperty(
				"user.home")
				+ "/smartshortcut");
		if (!saveFolder.exists()) {
			saveFolder.mkdirs();
		}

		String path = saveFolder + "/" + name + ".png";
		try {
			ImageIO.write(bufferedImage, "png", new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	/************************************ Inner Listener *********************************/
	class OpenListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			if ((labelCount != 0 || buttonCount != 0) && push2.getEnabled()) {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
						| SWT.YES | SWT.NO | SWT.CANCEL);
				messageBox.setMessage("Save the current window?");
				messageBox.setText("Info");
				int swt = messageBox.open();
				if (swt == SWT.YES) {
					FileDialog fd = new FileDialog(shell, SWT.SAVE);
					fd.setText("Please select a directory");
					fd.setFilterPath(LAST_APPLY_PATH);
					String[] filterExt = { "*.jar" };
					fd.setFilterExtensions(filterExt);
					fd.setFileName("mynavigator");
					String selected;
					if ((selected = fd.open()) == null) {
						return;
					}
					String fileName = fd.getFileName();

					saveJarFile("Saving...", selected, fileName);
				} else if (swt == SWT.CANCEL) {
					return;
				}
			}
			// open window jar
			FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText("Open window file");
			fd.setFilterPath(LAST_APPLY_PATH);
			String[] filterExt = { "*.jar" };
			fd.setFilterExtensions(filterExt);
			String path;
			if ((path = fd.open()) != null) {
				for (int j = 0; j < labelCount; j++) {
					label[j].dispose();
				}
				labelCount = 0;
				for (int j = 0; j < buttonCount; j++) {
					button[j].dispose();
				}
				buttonCount = 0;

				JarFile jar = null;
				InputStream input = null;
				Properties newFeature = new Properties();
				try {
					jar = new JarFile(path);
					Enumeration<?> enume = jar.entries();
					while (enume.hasMoreElements()) {
						JarEntry entry = (JarEntry) enume.nextElement();
						String name = entry.getName();
						if (name.equals("feature.properties")) {
							input = jar.getInputStream(entry);
							break;
						}
					}
					if (input == null) {
						MessageBox messageBox = new MessageBox(shell,
								SWT.ICON_ERROR);
						messageBox.setMessage("Load Error");
						messageBox.setText("Error");
						if (messageBox.open() == SWT.CANCEL)
							;
						return;
					} else {
						newFeature.load(input);
						configAndRefresh.backgroundImage = newFeature
								.getProperty("backgroundImage");
						configAndRefresh.navigationIcon = newFeature
								.getProperty("navigationIcon");
						configAndRefresh.width = Integer.parseInt(newFeature
								.getProperty("width"));
						configAndRefresh.height = Integer.parseInt(newFeature
								.getProperty("height"));
						configAndRefresh.maximized = Boolean
								.parseBoolean(newFeature
										.getProperty("maximized"));
						configAndRefresh.backgroundImageStatus = Integer
								.parseInt(newFeature
										.getProperty("backgroundImageStatus"));
						configAndRefresh.labelBackgroundTransparent = Boolean
								.parseBoolean(newFeature
										.getProperty("labelBackgroundTransparent"));
						configAndRefresh.backgroundColorRed = Integer
								.parseInt(newFeature
										.getProperty("backgroundColorRed"));
						configAndRefresh.backgroundColorGreen = Integer
								.parseInt(newFeature
										.getProperty("backgroundColorGreen"));
						configAndRefresh.backgroundColorBlue = Integer
								.parseInt(newFeature
										.getProperty("backgroundColorBlue"));
						labelCount = Integer.parseInt(newFeature
								.getProperty("labelCount"));
						buttonCount = Integer.parseInt(newFeature
								.getProperty("buttonCount"));
						configAndRefresh.autoExit = Boolean
								.parseBoolean(newFeature
										.getProperty("autoExit"));
						configAndRefresh.transparency = Integer
								.parseInt(newFeature
										.getProperty("transparency"));

						configAndRefresh.getOpinionStatus();

						configAndRefresh.refreshWindowIcon();
						configAndRefresh.refreshWindowBackground();
						configAndRefresh.refreshWindowSize();
						configAndRefresh.refreshTransparency();

						// add label
						for (int ii = 0; ii < labelCount; ii++) {
							label[ii] = new Label(shell, SWT.FLAT | SWT.CENTER);
							labelName[ii] = newFeature.getProperty("labelName["
									+ ii + "]");
							label[ii].setText(labelName[ii]);
							int lx, ly, lxx, lyy;
							lx = Integer.parseInt(newFeature.getProperty("lx["
									+ ii + "]"));
							ly = Integer.parseInt(newFeature.getProperty("ly["
									+ ii + "]"));
							lxx = Integer.parseInt(newFeature
									.getProperty("lxx[" + ii + "]"));
							lyy = Integer.parseInt(newFeature
									.getProperty("lyy[" + ii + "]"));
							label[ii].setLocation(lx, ly);
							label[ii].setSize(lxx, lyy);
							int labelBackgroundColorRed, labelBackgroundColorGreen, labelBackgroundColorBlue, labelForegroundColorRed, labelForegroundColorGreen, labelForegroundColorBlue;
							labelBackgroundColorRed = Integer
									.parseInt(newFeature
											.getProperty("labelBackgroundColorRed["
													+ ii + "]"));
							labelBackgroundColorGreen = Integer
									.parseInt(newFeature
											.getProperty("labelBackgroundColorGreen["
													+ ii + "]"));
							labelBackgroundColorBlue = Integer
									.parseInt(newFeature
											.getProperty("labelBackgroundColorBlue["
													+ ii + "]"));
							labelForegroundColorRed = Integer
									.parseInt(newFeature
											.getProperty("labelForegroundColorRed["
													+ ii + "]"));
							labelForegroundColorGreen = Integer
									.parseInt(newFeature
											.getProperty("labelForegroundColorGreen["
													+ ii + "]"));
							labelForegroundColorBlue = Integer
									.parseInt(newFeature
											.getProperty("labelForegroundColorBlue["
													+ ii + "]"));
							if (!configAndRefresh.labelBackgroundTransparent) {
								label[ii].setBackground(new Color(display,
										labelBackgroundColorRed,
										labelBackgroundColorGreen,
										labelBackgroundColorBlue));
							}
							label[ii].setForeground(new Color(display,
									labelForegroundColorRed,
									labelForegroundColorGreen,
									labelForegroundColorBlue));
							String labelFontName;
							int labelFontSize, labelFontStyle;
							labelFontName = newFeature
									.getProperty("labelFontName[" + ii + "]");
							labelFontSize = Integer.parseInt(newFeature
									.getProperty("labelFontSize[" + ii + "]"));
							labelFontStyle = Integer.parseInt(newFeature
									.getProperty("labelFontStyle[" + ii + "]"));
							label[ii].setFont(new Font(display, new FontData(
									labelFontName, labelFontSize,
									labelFontStyle)));
							label[ii]
									.addMouseListener(controlMouseTotalListener);
							label[ii]
									.addMouseMoveListener(controlMouseTotalListener);
							label[ii]
									.addMouseTrackListener(controlMouseTotalListener);
						}

						// add button
						for (int jj = 0; jj < buttonCount; jj++) {
							button[jj] = new Button(shell, SWT.FLAT);
							buttonName[jj] = newFeature
									.getProperty("buttonName[" + jj + "]");
							button[jj].setToolTipText(buttonName[jj]);
							int bx, by, bxx, byy;
							bx = Integer.parseInt(newFeature.getProperty("bx["
									+ jj + "]"));
							by = Integer.parseInt(newFeature.getProperty("by["
									+ jj + "]"));
							bxx = Integer.parseInt(newFeature
									.getProperty("bxx[" + jj + "]"));
							byy = Integer.parseInt(newFeature
									.getProperty("byy[" + jj + "]"));
							button[jj].setLocation(bx, by);
							button[jj].setSize(bxx, byy);
							String icpath;
							icpath = newFeature
									.getProperty("buttonBackgroundImagePath["
											+ jj + "]");
							if (!icpath.equals("")) {
								buttonBackgroundImagePath[jj] = icpath;
								button[jj]
										.setImage(new Image(
												display,
												new ImageData(
														buttonBackgroundImagePath[jj])
														.scaledTo(
																configAndRefresh.buttonImageWidth,
																configAndRefresh.buttonImageHeight)));
							}
							buttonPath[jj] = newFeature
									.getProperty("buttonPath[" + jj + "]");
							button[jj]
									.addMouseListener(controlMouseTotalListener);
							button[jj]
									.addMouseMoveListener(controlMouseTotalListener);
							button[jj]
									.addMouseTrackListener(controlMouseTotalListener);
						}
					}

					jarPath = path;
					jarName = fd.getFileName();
					shell.setText("SmartShortcut v1.2-" + jarName);
					LAST_APPLY_PATH = path;
					push2.setEnabled(false);
				} catch (IOException e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR);
					messageBox.setMessage("Load Error");
					messageBox.setText("Error");
					if (messageBox.open() == SWT.CANCEL)
						;
					return;
				} finally {
					if (jar != null) {
						try {
							jar.close();
						} catch (IOException ee) {
							ee.printStackTrace();
						}
					}
				}
			}
		}
	}

	class SaveListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			String selected = null;
			if (jarPath == null || jarName == null) {
				// preparation
				FileDialog fd = new FileDialog(shell, SWT.SAVE);
				fd.setText("Please select a directory");
				fd.setFilterPath(LAST_APPLY_PATH);
				String[] filterExt = { "*.jar" };
				fd.setFilterExtensions(filterExt);
				fd.setFileName("mynavigator");
				if ((selected = fd.open()) == null) {
					return;
				}
				String fileName = fd.getFileName();

				saveJarFile("Saving...", selected, fileName);
			} else {
				saveJarFile("Saving...", jarPath, jarName);
			}
			LAST_APPLY_PATH = selected;
			push2.setEnabled(false);
		}
	}

	class SaveAsListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			// preparation
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			fd.setText("Please select a directory");
			fd.setFilterPath(LAST_APPLY_PATH);
			String[] filterExt = { "*.jar" };
			fd.setFilterExtensions(filterExt);
			fd.setFileName("mynavigator");
			String selected;
			if ((selected = fd.open()) == null)
				return;
			String fileName = fd.getFileName();

			saveJarFile("Saving..", selected, fileName);
			LAST_APPLY_PATH = selected;
			push2.setEnabled(false);
		}
	}

	class ExitListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			if ((labelCount != 0 || buttonCount != 0) && push2.getEnabled()) {
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
						| SWT.YES | SWT.NO | SWT.CANCEL);
				messageBox.setMessage("Save the current window?");
				messageBox.setText("Info");
				int swt = messageBox.open();
				if (swt == SWT.YES) {
					// preparation
					FileDialog fd = new FileDialog(shell, SWT.SAVE);
					fd.setText("Please select a directory");
					fd.setFilterPath(LAST_APPLY_PATH);
					String[] filterExt = { "*.jar" };
					fd.setFilterExtensions(filterExt);
					fd.setFileName("mynavigator");
					String selected;
					if ((selected = fd.open()) == null)
						return;
					String fileName = fd.getFileName();
					saveJarFile("Saving..", selected, fileName);

					System.exit(0);
				} else if (swt == SWT.NO) {
					System.exit(0);
				} else {
					return;
				}
			} else
				System.exit(0);
		}
	}

	class AddLabelListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			InputDialog inputDialog = new InputDialog(shell, "Label Name",
					"Please input a label name", "myLabel", null);
			if (inputDialog.open() == InputDialog.OK) {
				labelName[labelCount] = inputDialog.getValue();
				if (labelName[labelCount] != "") {
					label[labelCount] = new Label(shell, SWT.FLAT | SWT.CENTER);
					label[labelCount].setText(labelName[labelCount]);
					label[labelCount].setLocation(0, 0);
					System.out
							.println(configAndRefresh.labelBackgroundTransparent);
					if (!configAndRefresh.labelBackgroundTransparent) {
						label[labelCount].setBackground(new Color(display,
								configAndRefresh.labelBackgroundColorRed,
								configAndRefresh.labelBackgroundColorGreen,
								configAndRefresh.labelBackgroundColorBlue));
					}
					label[labelCount].setForeground(new Color(display,
							configAndRefresh.labelForegroundColorRed,
							configAndRefresh.labelForegroundColorGreen,
							configAndRefresh.labelForegroundColorBlue));
					label[labelCount].setFont(new Font(display, new FontData(
							configAndRefresh.labelFontName,
							configAndRefresh.labelFontSize,
							configAndRefresh.labelFontStyle)));

					label[labelCount].setSize(
							CalculateText.getTextWidth(label[labelCount]),
							CalculateText.getTextHeight(label[labelCount]));

					label[labelCount]
							.addMouseListener(controlMouseTotalListener);
					label[labelCount]
							.addMouseMoveListener(controlMouseTotalListener);
					label[labelCount]
							.addMouseTrackListener(controlMouseTotalListener);
					labelCount++;

					push2.setEnabled(true);
				}
			}
		}
	}

	class AddButtonListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText("Add Program");
			fd.setFilterPath(LAST_APPLY_PATH);
			String[] filterExt = { "*.exe;*.jar;*.bat", "*.*" };
			fd.setFilterExtensions(filterExt);
			if ((buttonPath[buttonCount] = fd.open()) != null) {// prevent
																// cancel error
				button[buttonCount] = new Button(shell, SWT.FLAT);
				buttonName[buttonCount] = fd.getFileName();
				button[buttonCount].setToolTipText(buttonName[buttonCount]);
				button[buttonCount].setBounds(0, 0,
						configAndRefresh.buttonWidth,
						configAndRefresh.buttonHeight);
				sun.awt.shell.ShellFolder sf = null;
				try {
					sf = sun.awt.shell.ShellFolder.getShellFolder(new File(
							buttonPath[buttonCount]));
				} catch (FileNotFoundException ee) {
					ee.printStackTrace();
				}
				ImageIcon icon = new ImageIcon(sf.getIcon(true));
				buttonBackgroundImagePath[buttonCount] = savePng(icon,
						buttonName[buttonCount].substring(0,
								buttonName[buttonCount].length() - 4));
				button[buttonCount].setImage(new Image(display, new ImageData(
						buttonBackgroundImagePath[buttonCount]).scaledTo(
						configAndRefresh.buttonImageWidth,
						configAndRefresh.buttonImageHeight)));
				button[buttonCount].addMouseListener(controlMouseTotalListener);
				button[buttonCount]
						.addMouseMoveListener(controlMouseTotalListener);
				button[buttonCount]
						.addMouseTrackListener(controlMouseTotalListener);
				label[labelCount] = new Label(shell, SWT.FLAT | SWT.CENTER);
				labelName[labelCount] = buttonName[buttonCount].substring(0,
						buttonName[buttonCount].length() - 4);
				label[labelCount].setText(labelName[labelCount]);
				label[labelCount].setLocation(0,
						configAndRefresh.buttonHeight + 20);
				if (!configAndRefresh.labelBackgroundTransparent) {
					label[labelCount].setBackground(new Color(display,
							configAndRefresh.labelBackgroundColorRed,
							configAndRefresh.labelBackgroundColorGreen,
							configAndRefresh.labelBackgroundColorBlue));
				}
				label[labelCount].setForeground(new Color(display,
						configAndRefresh.labelForegroundColorRed,
						configAndRefresh.labelForegroundColorGreen,
						configAndRefresh.labelForegroundColorBlue));
				label[labelCount].setFont(new Font(display, new FontData(
						configAndRefresh.labelFontName,
						configAndRefresh.labelFontSize,
						configAndRefresh.labelFontStyle)));

				label[labelCount].setSize(
						CalculateText.getTextWidth(label[labelCount]),
						CalculateText.getTextHeight(label[labelCount]));

				label[labelCount].addMouseListener(controlMouseTotalListener);
				label[labelCount]
						.addMouseMoveListener(controlMouseTotalListener);
				label[labelCount]
						.addMouseTrackListener(controlMouseTotalListener);
				LAST_APPLY_PATH = buttonPath[buttonCount];
				labelCount++;
				buttonCount++;

				push2.setEnabled(true);
			}
		}
	}

	class RefreshListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			currentSelectedControl = initialCurrentSelectedControl;

			push2.setEnabled(true);
			push8.setEnabled(false);
			push9.setEnabled(false);
			push10.setEnabled(false);
			push11.setEnabled(false);
			push12.setEnabled(false);
			push13.setEnabled(false);

			shell.redraw();
		}
	}

	class DeleteListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			if (currentSelectedControl == (Control) initialCurrentSelectedControl)
				;
			else {
				int i;
				for (i = 0; i < 100; i++)
					// label
					if (currentSelectedControl.equals(label[i])) {
						currentSelectedControl.dispose();
						label[i] = label[--labelCount];
						labelName[i] = labelName[labelCount];
						currentSelectedControl = (Control) initialCurrentSelectedControl;

						push2.setEnabled(true);
						push8.setEnabled(false);
						push9.setEnabled(false);
						push10.setEnabled(false);
						push11.setEnabled(false);
						push13.setEnabled(false);
						push12.setEnabled(false);

						return;
					}
				for (i = 0; i < 100; i++)
					// button
					if (currentSelectedControl.equals(button[i])) {
						currentSelectedControl.dispose();
						button[i] = button[--buttonCount];
						buttonPath[i] = buttonPath[buttonCount];
						buttonBackgroundImagePath[i] = buttonBackgroundImagePath[buttonCount];
						buttonName[i] = buttonName[buttonCount];
						currentSelectedControl = (Control) initialCurrentSelectedControl;

						push2.setEnabled(true);
						push8.setEnabled(false);
						push9.setEnabled(false);
						push10.setEnabled(false);
						push11.setEnabled(false);
						push13.setEnabled(false);
						push12.setEnabled(false);

						return;
					}
			}
		}
	}

	class FontListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			FontDialog fnd = new FontDialog(shell, SWT.APPLICATION_MODAL);
			fnd.setText("Please select a font");
			fnd.setRGB(currentSelectedControl.getForeground().getRGB());
			FontData[] defaultFont = currentSelectedControl.getFont()
					.getFontData();
			fnd.setFontList(defaultFont);
			FontData newFont = fnd.open();
			if (newFont == null)
				return;
			currentSelectedControl.setFont(new Font(display, newFont));
			currentSelectedControl.setForeground(new Color(display, fnd
					.getRGB()));
			currentSelectedControl.setSize(
					CalculateText.getTextWidth(currentSelectedControl),
					CalculateText.getTextHeight(currentSelectedControl));

			push2.setEnabled(true);
		}
	}

	class ForeColorListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			ColorDialog cd = new ColorDialog(shell, SWT.APPLICATION_MODAL);
			cd.setText("Please select a color");
			cd.setRGB(new RGB(0, 0, 0));
			RGB newColor = cd.open();
			if (newColor == null)
				return;
			currentSelectedControl.setForeground(new Color(display, newColor));

			push2.setEnabled(true);
		}
	}

	class ButtonIconListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			int i = 0;
			for (i = 0; i < 100; i++) {
				if (currentSelectedControl.equals(button[i]))
					break;
			}
			FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText("Please select an icon");
			fd.setFilterPath(buttonBackgroundImagePath[i]);
			String[] filterExt = { "*.ico;*.png" };
			fd.setFilterExtensions(filterExt);
			String ImagePath = null;
			if ((ImagePath = fd.open()) != null) {// prevent cancel error
				String sub = ImagePath.substring(ImagePath.length() - 3,
						ImagePath.length()).toLowerCase();
				if (sub.equals("png") || sub.equals("ico")) {
					buttonBackgroundImagePath[i] = ImagePath;
					((Button) currentSelectedControl)
							.setImage(new Image(display, new ImageData(
									buttonBackgroundImagePath[i]).scaledTo(
									configAndRefresh.buttonImageWidth,
									configAndRefresh.buttonImageHeight)));
					LAST_APPLY_PATH = ImagePath;
				} else {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_WARNING | SWT.OK);
					messageBox.setMessage("Please select a right type");
					messageBox.setText("Warn");
					if (messageBox.open() == SWT.OK)
						;
					return;
				}
			}

			push2.setEnabled(true);
		}
	}

	class ButtonPathListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			int i;
			for (i = 0; i < 100; i++) {
				if (currentSelectedControl.equals(button[i]))
					break;
			}
			FileDialog fd = new FileDialog(shell, SWT.OPEN);
			fd.setText("Change Path");
			String str = buttonPath[i];
			fd.setFilterPath(str);
			String[] filterExt = { "*.exe;*.jar;*.bat", "*.*" };
			fd.setFilterExtensions(filterExt);
			if ((buttonPath[i] = fd.open()) != null) {

				buttonName[i] = fd.getFileName();
				currentSelectedControl.setToolTipText(buttonName[i]);
				sun.awt.shell.ShellFolder sf = null;
				try {
					sf = sun.awt.shell.ShellFolder.getShellFolder(new File(
							buttonPath[i]));
				} catch (FileNotFoundException ee) {
					ee.printStackTrace();
				}
				ImageIcon icon = new ImageIcon(sf.getIcon(true));
				buttonBackgroundImagePath[i] = savePng(icon,
						buttonName[i].substring(0, buttonName[i].length() - 4));
				button[i].setImage(new Image(display, new ImageData(
						buttonBackgroundImagePath[i]).scaledTo(
						configAndRefresh.buttonImageWidth,
						configAndRefresh.buttonImageHeight)));
			} else
				buttonPath[i] = str;

			push2.setEnabled(true);
		}
	}

	class RenameListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			for (int i = 0; i < 100; i++)
				// label
				if (currentSelectedControl.equals(label[i])) {
					InputDialog inputDialog = new InputDialog(shell, "Rename",
							"Please input a new name",
							((Label) currentSelectedControl).getText(), null);
					if (inputDialog.open() == InputDialog.OK) {
						String newName = inputDialog.getValue();
						if (newName != "") {
							((Label) currentSelectedControl).setText(newName);
							labelName[i] = newName;
							currentSelectedControl
									.setSize(
											CalculateText
													.getTextWidth(currentSelectedControl),
											CalculateText
													.getTextHeight(currentSelectedControl));
							return;
						}
					}
				}
			for (int i = 0; i < 100; i++)
				if (currentSelectedControl.equals(button[i])) {
					InputDialog inputDialog = new InputDialog(shell, "Rename",
							"Please input a new name",
							((Button) currentSelectedControl).getToolTipText(),
							null);
					if (inputDialog.open() == InputDialog.OK) {
						String newName = inputDialog.getValue();
						if (newName != "") {
							((Button) currentSelectedControl)
									.setToolTipText(newName);
							buttonName[i] = newName;
							return;
						}
					}
				}

			push2.setEnabled(true);
		}
	}

	class PreferenceListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			PreferenceManager preferenceManager = new PreferenceManager();

			PreferenceNode preferenceNode1 = new PreferenceNode("General");
			PreferenceNode preferenceNode2 = new PreferenceNode("Label");
			PreferenceNode preferenceNode3 = new PreferenceNode("Button");
			PreferenceNode preferenceNode4 = new PreferenceNode("Other");
			preferenceNode1.setPage(new Preference1(shell, display,
					configAndRefresh));
			preferenceNode2.setPage(new Preference2(shell, display,
					configAndRefresh));
			preferenceNode3.setPage(new Preference3(shell, display,
					configAndRefresh));
			preferenceNode4.setPage(new Preference4(shell, display,
					configAndRefresh));
			preferenceManager.addToRoot(preferenceNode1);
			preferenceManager.addToRoot(preferenceNode2);
			preferenceManager.addToRoot(preferenceNode3);
			preferenceManager.addToRoot(preferenceNode4);
			PreferenceDialog preferenceDialog = new PreferenceDialog(shell,
					preferenceManager);
			preferenceDialog.setSelectedNode("General");
			PreferenceDialog.setDefaultImage(new Image(display, CURRENT_PATH
					+ "/image/navigationIcon.png"));
			preferenceDialog.open();

			push2.setEnabled(true);

		}
	}

	class HelpListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			Shell child = new Shell(shell);
			child.setBounds(screenWidth / 2 - 250, screenHeight / 2 - 200, 500,
					400);
			child.setText("Instruction");

			Text ta = new Text(child, SWT.NONE | SWT.MULTI | SWT.SCROLL_LINE);
			ta.setBounds(5, 5, 490, 399);
			ta.setEditable(false);
			ta.setBackground(new Color(display, 255, 255, 255));
			ta.setText(" SmartShortcut for Windows\n\n This program is used to generate a customized shortcut window in \".jar\" format."
					+ "\n JRE/JDK is needed."
					+ "\n\n 1. A generated jar file is not recommended to be placed directly on desktop."
					+ "\n    Shortcut to the jar file with a customized icon is a beautiful way."
					+ "\n 2. Do not remove the image files linked with jar files."
					+ "\n 3. \".bat\" files should be placed at the same directory of their linked jar file."
					+ "\n 4. \"embed.jar\" is a subset of \"org.eclipse.swt.win32.win32.x86-4.3.jar\"."
					+ "\n 5. Button icons are stored at $USRER_HOME$/smartshortcut/.");

			child.open();
		}
	}

	class AboutListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
		}

		public void widgetSelected(SelectionEvent e) {
			Shell child = new Shell(shell);
			child.setBounds(screenWidth / 2 - 250, screenHeight / 2 - 200, 500,
					400);
			child.setText("About SmartShortcut");

			TabFolder tf = new TabFolder(child, SWT.BORDER);
			tf.setBounds(0, 0, 500, 400);
			TabItem tbi1 = new TabItem(tf, SWT.BORDER);
			tbi1.setText("About");
			Composite cm1 = new Composite(tf, SWT.SHADOW_ETCHED_IN);
			tbi1.setControl(cm1);
			cm1.setBackground(new Color(display, 240, 240, 240));
			Label l1 = new Label(cm1, SWT.NONE);
			l1.setBounds(210, 82, 36, 36);
			l1.setBackgroundImage(new Image(display, CURRENT_PATH
					+ "/image/navigationIcon.png"));
			Label l2 = new Label(cm1, SWT.NONE);
			l2.setBounds(158, 130, 200, 50);
			l2.setText("SmartShortcut v1.2 by Crifny");
			l2.setBackground(new Color(display, 240, 240, 240));
			Label l3 = new Label(cm1, SWT.NONE);
			l3.setBounds(200, 200, 400, 50);
			l3.setText("2009-2015");
			l3.setBackground(new Color(display, 240, 240, 240));

			TabItem tbi2 = new TabItem(tf, SWT.BORDER);
			tbi2.setText("History");
			Composite cm2 = new Composite(tf, SWT.SHADOW_ETCHED_IN);
			tbi2.setControl(cm2);
			Text ta = new Text(cm2, SWT.NONE | SWT.MULTI | SWT.SCROLL_LINE);
			ta.setBounds(5, 5, 490, 399);
			ta.setEditable(false);
			ta.setBackground(new Color(display, 255, 255, 255));
			ta.setText("*2015.07 SmartShortcut v1.2\n\tbased on the SWT/JFace framework in maven repository"
					+ "\n\tcode optimization\n\tinterface optimization"
					+ "\n\n*2009.12 SmartShortcut v1.1\n\tbased on the SWT/JFace framework of Eclipse3.5"
					+ "\n\n*2009.03 SmartShortcut v1.0\n\tbased on the SWT/JFace framework of Eclipse3.2");
			child.open();
		}
	}

	class ControlMouseTotalListener implements MouseListener,
			MouseMoveListener, MouseTrackListener {

		int x0, y0;

		public void mouseEnter(MouseEvent e) {
			Control c = (Control) e.getSource();
			c.setCursor(new Cursor(display, SWT.CURSOR_HAND));
		}

		public void mouseExit(MouseEvent e) {

		}

		public void mouseHover(MouseEvent e) {

		}

		public void mouseDoubleClick(MouseEvent e) {
			currentSelectedControl.setBackground(null);
			Control c = (Control) e.getSource();
			currentSelectedControl = c;
			int i;
			for (i = 0; i < 100; i++) {
				if (c.equals(label[i]))
					return;
			}
			for (i = 0; i < 100; i++) {
				if (c.equals(button[i]))
					break;
			}
			if ((buttonName[i].substring(buttonName[i].length() - 3))
					.toLowerCase().equals("exe")) {
				try {
					Runtime.getRuntime().exec(buttonPath[i]);
				} catch (Exception e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR | SWT.OK);
					messageBox.setMessage("Error! Please check the path");
					messageBox.setText("ERROR");
					if (messageBox.open() == SWT.OK)
						;
					return;
				}
			} else if ((buttonName[i].substring(buttonName[i].length() - 3))
					.toLowerCase().equals("jar")) {
				try {
					String strcmd[] = new String[] { "cmd", "/c", buttonPath[i] };
					Runtime.getRuntime().exec(strcmd);
				} catch (Exception e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR | SWT.OK);
					messageBox.setMessage("Error! Please check the path");
					messageBox.setText("Error");
					if (messageBox.open() == SWT.OK)
						;
					return;
				}
			} else if ((buttonName[i].substring(buttonName[i].length() - 3))
					.toLowerCase().equals("bat")) {
				try {
					String strcmd[] = new String[] { "cmd", "/c", "start",
							buttonPath[i] };
					Runtime.getRuntime().exec(strcmd);
				} catch (Exception e1) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_ERROR | SWT.OK);
					messageBox.setMessage("Error! Please check the path");
					messageBox.setText("Error");
					if (messageBox.open() == SWT.OK)
						;
					return;
				}
			}
		}

		public void mouseDown(MouseEvent e) {
			Control c = (Control) e.getSource();
			currentSelectedControl = c;
			x0 = e.x;
			y0 = e.y;

			int i;
			for (i = 0; i < 100; i++)
				if (c.equals(label[i])) {
					push8.setEnabled(true);
					push9.setEnabled(true);
					push10.setEnabled(true);
					push11.setEnabled(false);
					push13.setEnabled(true);
					push12.setEnabled(false);
				}
			for (i = 0; i < 100; i++)
				if (c.equals(button[i])) {
					push8.setEnabled(true);
					push9.setEnabled(false);
					push10.setEnabled(false);
					push11.setEnabled(true);
					push13.setEnabled(true);
					push12.setEnabled(true);
				}
		}

		public void mouseUp(MouseEvent e) {

		}

		public void mouseMove(MouseEvent e) {
			Control c = (Control) e.getSource();
			if (e.stateMask == SWT.BUTTON1) {// drag
				int xx = c.getLocation().x + e.x - x0;
				int yy = c.getLocation().y + e.y - y0;
				Point sSize = shell.getSize();
				Point cSize = c.getSize();
				int mx = sSize.x - cSize.x - 16;
				int my = sSize.y - cSize.y - 56;

				if (xx <= 0) {
					if (yy <= 0)
						c.setLocation(0, 0);
					else if (yy > 0 && yy < my)
						c.setLocation(0, yy);
					else
						c.setLocation(0, my);
				} else if (xx > 0 && xx < mx) {
					if (yy <= 0)
						c.setLocation(xx, 0);
					else if (yy > 0 && yy < my)
						c.setLocation(xx, yy);
					else
						c.setLocation(xx, my);
				} else {
					if (yy <= 0)
						c.setLocation(mx, 0);
					else if (yy > 0 && yy < my)
						c.setLocation(mx, yy);
					else
						c.setLocation(mx, my);
				}

				push2.setEnabled(true);
			}
		}
	}

	class ShellWindowCloseListener implements Listener {

		public void handleEvent(Event event) {
			if ((labelCount != 0 || buttonCount != 0) && push2.getEnabled()) {

				MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION
						| SWT.YES | SWT.NO | SWT.CANCEL);
				messageBox.setMessage("Save the current window?");
				messageBox.setText("Info");
				int swt = messageBox.open();
				if (swt == SWT.YES) {
					// preparation
					FileDialog fd = new FileDialog(shell, SWT.SAVE);
					fd.setText("Please select a directory");
					fd.setFilterPath(LAST_APPLY_PATH);
					String[] filterExt = { "*.jar" };
					fd.setFilterExtensions(filterExt);
					fd.setFileName("mynavigator");
					String selected;
					if ((selected = fd.open()) == null)
						return;
					String fileName = fd.getFileName();

					saveJarFile("Saving...", selected, fileName);

				}

				event.doit = (swt == SWT.YES || swt == SWT.NO);
			}
		}
	}

	/************************************ Inner Listener *********************************/

}