package de.hswhameln.typetogether.client.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;


import de.hswhameln.typetogether.networking.util.ExceptionHandler;


/**
 * {@link JFrame} that displays an, swapping between ViewPanels by using a {@link CardLayout}.
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private ExceptionHandler exceptionHandler = ExceptionHandler.getExceptionHandler();

    private JPanel mainContainer;
    private Map<String, JPanel> availableViews;
    private CardLayout cardLayout;

    public MainWindow() {
        this.mainContainer = new JPanel();
        this.cardLayout = new CardLayout();
        this.availableViews = new HashMap<>();
        this.mainContainer.setLayout(cardLayout);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(Exception e) {
            exceptionHandler.handle(e, "Failed to initialize look&feel", this.getClass());
        }
        this.add(mainContainer);
        this.setResizable(false);
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setMinimumSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        /*
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension dimension = MainWindow.this.getSize();
                Dimension minDimension = MainWindow.this.getMinimumSize();
                if(dimension.width<minDimension.width) {
                    dimension.width=minDimension.width;
                }
                if(dimension.height<minDimension.height) {
                    dimension.height=minDimension.height;
                }
                ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
                scheduler.schedule(() -> MainWindow.this.setSize(dimension), ViewProperties.RESIZING_TIMER, TimeUnit.MILLISECONDS);
            }
        });
        */
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("TypeTogether");
        this.setBackground(Color.PINK);
        this.registerViews();
        // center
        this.setLocationRelativeTo(null);
        //this.setIconImage(getApplicationIcon());
        this.cardLayout.show(mainContainer, ViewProperties.LOGIN); //TODO: Changed from LOGIN for debugging
    }

    //TODO Create Logo
    private Image getApplicationIcon() {
        try {
            URL resource = this.getClass().getResource("resource/images/diploma-icon.png");
            return ImageIO.read(resource);
        } catch (IOException e) {
            exceptionHandler.handle(e, "Could not load application icon", this.getClass());
            return null;
        }
    }

    private void registerViews() {
        this.registerSingleView(new EditorPanel(), ViewProperties.EDITOR);
        this.registerSingleView(new LoginPanel(this), ViewProperties.LOGIN);
        this.registerSingleView(new MenuPanel(this), ViewProperties.MENU);
    }

    private void registerSingleView(JPanel panel, String viewId) {
        this.mainContainer.add(panel);
        panel.setVisible(true);
        availableViews.put(viewId, panel);
        cardLayout.addLayoutComponent(panel, viewId);
    }

    /**
     * Switch to a view that has already been registered.
     * 
     * @param viewId Id of the view that shall be shown.
     */
    public void switchToView(String viewId) {
        if (!this.availableViews.containsKey(viewId)) {
            throw new IllegalArgumentException(String.format("View %s not registered for this window.", viewId));
        }
        cardLayout.show(mainContainer, viewId);
    }
}