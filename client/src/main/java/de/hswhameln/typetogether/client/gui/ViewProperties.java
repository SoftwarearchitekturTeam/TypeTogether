package de.hswhameln.typetogether.client.gui;

import java.awt.Color;

/**
 * Stores constant properties relevant for the {@link View}
 *
 */
public interface ViewProperties {
	// Color palette
	public static final Color BUTTON_COLOR = new Color(62, 76, 84);
	public static final Color BACKGROUND_COLOR = new Color(36, 50, 63);
	public static final Color FONT_COLOR = new Color(244, 243, 239);
	public static final float BUTTON_FONT_SIZE = 16f;
	public static final float MENU_BUTTON_FONT_SIZE = 12f;
	public static final int DEFAULT_HEIGHT = 950;
	public static final int DEFAULT_WIDTH = 1200;
	public static final long NOTIFICATION_TIMER = 4;
	public static final long RESIZING_TIMER = 100;

	public static final String LOGIN = "login";
	public static final String MENU = "menu";
	public static final String EDITOR = "editor";
	
}
