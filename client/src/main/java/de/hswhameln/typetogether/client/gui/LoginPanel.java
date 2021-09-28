package de.hswhameln.typetogether.client.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LoginPanel extends AbstractPanel {

   private JPanel headline;
   private JPanel body;
   
    

    public LoginPanel() {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.setSize(ViewProperties.DEFAULT_WIDTH,ViewProperties.DEFAULT_HEIGHT);
        this.createGrid();
        
        
        
        
        this.setSize(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT);
        this.setPreferredSize(new Dimension(ViewProperties.DEFAULT_WIDTH, ViewProperties.DEFAULT_HEIGHT));
        this.setVisible(true);
        this.body.setBackground(Color.WHITE);
        this.body.setLayout(new BoxLayout(this.body, BoxLayout.Y_AXIS));
        this.createBody();
        
        
    }
    private void createGrid() {
        this.headline = new TypeTogetherPanel();
        this.add(this.headline);
        headline.setVisible(true);
        headline.setBorder(BorderFactory.createEmptyBorder());
        this.body = new JPanel();

        Dimension size = new Dimension(ViewProperties.DEFAULT_WIDTH / 2 - 6, ViewProperties.DEFAULT_HEIGHT - ViewProperties.HEADLINE_HEIGHT);
        this.body.setSize(size);
        this.body.setPreferredSize(size);
        body.setVisible(true);
        body.setBorder(BorderFactory.createEmptyBorder());
        this.add(body);
        
    }
    private void createBody(){
        JLabel lable = new JLabel("Benutzername");
        Dimension sizeTitle = new Dimension(200, 70);
        lable.setMaximumSize(sizeTitle);
        lable.setVisible(true);
        lable.setFont(ViewProperties.SUBHEADLINE_FONT);
        lable.setForeground(ViewProperties.FONT_COLOR);
        lable.setBackground(ViewProperties.BACKGROUND_COLOR);
       this.body.add(lable);
        
        JTextField text = new JTextField();
        Dimension sizeText = new Dimension(200, 70);
        text.setMaximumSize(sizeText);
        text.setVisible(true);
        text.setFont(ViewProperties.SUBHEADLINE_FONT);
        text.setForeground(ViewProperties.FONT_COLOR);
        text.setBackground(ViewProperties.BACKGROUND_COLOR);
        getText(text.getText());
        this.body.add(text);
        


         JButton button = new JButton("Anmelden");
         button.setForeground(ViewProperties.BACKGROUND_COLOR);
         button.setBackground(ViewProperties.GREY_BUTTON_COLOR);
         button.setMaximumSize(new Dimension(120, 70));
         button.addActionListener(a -> this.anmelden());
         button.setAlignmentX(CENTER_ALIGNMENT);
         this.body.add(button);
    }
    private void anmelden(){
        System.out.println("Anmelden");
    }
    private void getText(String username){
        System.out.println(username);
    }
}