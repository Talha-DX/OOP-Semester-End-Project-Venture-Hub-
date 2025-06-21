import javax.swing.*;
import gui.LoadingScreen;
import gui.LoginFrame;

public class App {
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> {
             LoadingScreen splash = new LoadingScreen();

             splash.showSplashThenRun(() -> {
                LoginFrame dashboard = new LoginFrame();
                dashboard.setVisible(true);
            });
         });        
    }
}