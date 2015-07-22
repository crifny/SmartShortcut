package edu.buaa.sem.smartshortcut;

import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * calculate the pixel width and pixel height of a text label
 */
public class CalculateText {

	public static int getTextWidth(Control control) {
		Label lable = (Label) control;
		GC gc = new GC(lable);
		int textWidth = 5;// buffer value
		for (int i = 0; i < lable.getText().length(); i++) {
			char c = lable.getText().charAt(i);
			textWidth += gc.getAdvanceWidth(c);
		}
		gc.dispose();
		return textWidth;
	}

	public static int getTextHeight(Control control) {
		Label label = (Label) control;
		GC gc = new GC(label);
		int textHeight = 2;// buffer value
		FontMetrics fontMetrics = gc.getFontMetrics();
		textHeight += fontMetrics.getHeight();
		gc.dispose();
		return textHeight;
	}
}