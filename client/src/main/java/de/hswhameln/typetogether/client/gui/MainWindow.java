package de.hswhameln.typetogether.client.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import de.hswhameln.typetogether.client.runtime.ClientRuntime;
import de.hswhameln.typetogether.client.runtime.SessionStorage;
import de.hswhameln.typetogether.networking.api.exceptions.UnknownUserException;
import de.hswhameln.typetogether.networking.api.exceptions.InvalidDocumentIdException.DocumentDoesNotExistException;
import de.hswhameln.typetogether.networking.util.ExceptionHandler;
import de.hswhameln.typetogether.networking.util.ObjectDestructor;


/**
 * {@link JFrame} that displays an, swapping between ViewPanels by using a {@link CardLayout}.
 */
public class MainWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private final ExceptionHandler exceptionHandler = ExceptionHandler.getExceptionHandler();

    private final JPanel mainContainer;
    private final Map<String, AbstractPanel> availableViews;
    private final CardLayout cardLayout;
    private final SessionStorage sessionStorage;

    private String activeView;

    public MainWindow(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        this.mainContainer = new JPanel();
        this.cardLayout = new CardLayout();
        this.availableViews = new HashMap<>();
        this.mainContainer.setLayout(cardLayout);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(Exception e) {
            exceptionHandler.handle(e, Level.WARNING, "Failed to initialize lookAndFeel. Using default lookAndFeel.", this.getClass());
        }
        this.add(mainContainer);
        this.setResizable(false);
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setMinimumSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("TypeTogether");
        this.setBackground(Color.PINK);
        this.registerViews();
        // center
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("./resources/favicon.jpg").getImage());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MainWindow.this.availableViews.get(activeView).windowClosed();
                ObjectDestructor.destroyAll();
                // give the other Threads some time to finish their work, but forcefully interrupt them after said time has passed.
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });

        this.activeView = ViewProperties.LOGIN;
        this.cardLayout.show(mainContainer, ViewProperties.LOGIN); //TODO: Changed from LOGIN for debugging
    }

    private void registerViews() {
        CommandPanel commandPanal = new CommandPanel(this, this.sessionStorage);
        this.registerSingleView(commandPanal, ViewProperties.EDITOR);
        this.registerSingleView(new LoginPanel(this, this.sessionStorage), ViewProperties.LOGIN);
        this.registerSingleView(new MenuPanel(this, this.sessionStorage), ViewProperties.MENU);
        this.sessionStorage.getCommandInvoker().setCommandPanel(commandPanal);
    }

    private void registerSingleView(AbstractPanel panel, String viewId) {
        this.mainContainer.add(panel);
        panel.setVisible(true);
        availableViews.put(viewId, panel);
        cardLayout.addLayoutComponent(panel, viewId);
    }

    /**
	 * Creates a pop-up with a notification of the given type. If the given type is
	 * a QUESTION_MESSAGE the pop-up will have Options to choose.
	 * 
	 * @param message     Message shown in the pop-up
	 * @param messageType Must be part of the {@link JOptionPane} values
	 * @return Chosen option or an open confirmation if the messageType is not
	 *         QUESTION_MESSAGE
	 */
	public int alert(String message, int messageType) {
		if (messageType == JOptionPane.QUESTION_MESSAGE) {
			return JOptionPane.showConfirmDialog(this, message, "thesisSpace", JOptionPane.YES_NO_OPTION);
		} else {
			JOptionPane.showMessageDialog(this, message, "thesisSpace", messageType);
			return JOptionPane.CLOSED_OPTION;
		}
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
        this.activeView = viewId;
        this.availableViews.get(viewId).initialize();
        cardLayout.show(mainContainer, viewId);
    }
}