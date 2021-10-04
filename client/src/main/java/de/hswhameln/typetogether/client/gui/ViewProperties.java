package de.hswhameln.typetogether.client.gui;

import java.awt.*;

public interface ViewProperties {
    // Color palette
    Color GREY_BUTTON_COLOR = new Color(195, 215, 223);
    Color BACKGROUND_COLOR = new Color(255, 255, 255);
    Color FONT_COLOR = new Color(52, 73, 94);
    Color CONTRAST_COLOR = new Color(105, 145, 172);
    Font SUBHEADLINE_FONT = new Font("Arial", Font.PLAIN, 20);
    Font HEADLINE_FONT = new Font("Bauhaus 93", Font.BOLD, 60);
    float BUTTON_FONT_SIZE = 16f;
    float MENU_BUTTON_FONT_SIZE = 12f;
    int DEFAULT_HEIGHT = 950;
    int DEFAULT_WIDTH = 1200;
    int HEADLINE_HEIGHT = 200;
    long NOTIFICATION_TIMER = 4;
    long RESIZING_TIMER = 100;

    String LOGIN = "login";
    String MENU = "menu";
    String EDITOR = "editor";
    String HEADLINE = "TypeTogether";

    Dimension EDITOR_SIZE = new Dimension(1000, 550);
    Font EDITOR_FONT = new Font("Calibri", Font.PLAIN, 20);
    Dimension BTN_SIZE = new Dimension(550, 50);
}
