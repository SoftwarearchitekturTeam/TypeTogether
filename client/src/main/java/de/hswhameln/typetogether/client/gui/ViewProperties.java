package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

public interface ViewProperties {
	// Color palette
	public static final Color GREY_BUTTON_COLOR = new Color(236, 240, 241);
	public static final Color BACKGROUND_COLOR = new Color(255, 255, 255);
	public static final Color FONT_COLOR = new Color(52, 73, 94);
	public static final Color CONTRAST_COLOR = new Color(154, 198, 197);
	public static final Font SUBHEADLINE_FONT = new Font("Arial", Font.PLAIN, 9);
	public static final Font HEADLINE_FONT = new Font("Calibri", Font.BOLD, 40);
	public static final float BUTTON_FONT_SIZE = 16f;
	public static final float MENU_BUTTON_FONT_SIZE = 12f;
	public static final int DEFAULT_HEIGHT = 950;
	public static final int DEFAULT_WIDTH = 1200;
	public static final int HEADLINE_HEIGHT = 200;
	public static final long NOTIFICATION_TIMER = 4;
	public static final long RESIZING_TIMER = 100;

	public static final String LOGIN = "login";
	public static final String MENU = "menu";
	public static final String EDITOR = "editor";
	public static final String HEADLINE = "TypeTogether";
	
	public static final Dimension EDITOR_SIZE = new Dimension(1000, 550);
	public static final Font EDITOR_FONT = new Font("Calibri", Font.PLAIN, 20);
}
