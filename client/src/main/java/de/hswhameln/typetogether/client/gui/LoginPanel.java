package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import de.hswhameln.typetogether.client.businesslogic.ClientUser;
import de.hswhameln.typetogether.client.runtime.SessionStorage;

public class LoginPanel extends AbstractPanel {

    private JTextField text;

    public LoginPanel(MainWindow window, SessionStorage sessionStorage) {
        super(window, sessionStorage);
      
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setAlignmentY(CENTER_ALIGNMENT);

        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setVisible(true);
        this.setBackground(Color.WHITE);

        this.createBody();

    }

    private void createGrid() {
        JPanel headline = new TypeTogetherPanel();
        this.add(headline);
        headline.setVisible(true);
        headline.setBorder(BorderFactory.createEmptyBorder());

    }

    private void createBody() {
        this.add(Box.createVerticalStrut(50));
        JLabel lable = new JLabel("Benutzername");
        Dimension sizeTitle = new Dimension(200, 70);
        lable.setMaximumSize(sizeTitle);
        lable.setVisible(true);
        lable.setFont(ViewProperties.SUBHEADLINE_FONT);
        lable.setForeground(ViewProperties.FONT_COLOR);
        lable.setBackground(ViewProperties.BACKGROUND_COLOR);
        lable.setHorizontalAlignment(SwingConstants.CENTER);
        lable.setAlignmentX(CENTER_ALIGNMENT);

        this.add(lable);

        this.text = new JTextField();
        Dimension sizeText = new Dimension(200, 30);
        text.setMaximumSize(sizeText);
        text.setVisible(true);
        text.setFont(ViewProperties.SUBHEADLINE_FONT);
        text.setForeground(ViewProperties.FONT_COLOR);
        text.setBackground(ViewProperties.BACKGROUND_COLOR);
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.addActionListener(a-> this.login());
        getText(text.getText());
        this.add(text);

        this.add(Box.createVerticalStrut(50));

        JButton button = new JButton("Anmelden");
        button.setForeground(ViewProperties.BACKGROUND_COLOR);
        button.setBackground(ViewProperties.CONTRAST_COLOR);
        button.setMaximumSize(new Dimension(100, 50));
        button.addActionListener(a -> this.login());
        button.setAlignmentX(CENTER_ALIGNMENT);
        this.add(button);

    }

    private void login() {
        System.out.println("Login");
        this.sessionStorage.setCurrentUser(new ClientUser(this.text.getText()));
        this.window.switchToView(ViewProperties.MENU);
    }

    private void getText(String username) {
        System.out.println(username);
    }
}