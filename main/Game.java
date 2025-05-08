package main;

import javax.swing.JFrame;

public class Game {
    public static void main (String[] args) {
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("DATING FREAKSHOW");
        
        window.add(gamePanel);
        window.pack(); // MAKES THE WINDOW FIT THE PREFERRED SIZE/CHILDREN COMPONENTS
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}