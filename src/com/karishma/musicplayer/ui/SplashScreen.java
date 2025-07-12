package  com.karishma.musicplayer.ui;
import java.awt.Color;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.Font;
import java.awt.BorderLayout;

public class SplashScreen extends JWindow {
    public SplashScreen() {
        JLabel logo = new JLabel(new ImageIcon("src/assets/logo.png"));
        JLabel title = new JLabel("🎵 MiniSpotify 🎵", SwingConstants.CENTER);
        title.setFont(new Font("Century Gothic", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JPanel content = new JPanel();
        content.setBackground(new Color(30, 30, 30));
        content.setLayout(new BorderLayout());
        content.add(logo, BorderLayout.CENTER);
        content.add(title, BorderLayout.SOUTH);

        this.setContentPane(content);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // ⛔ Fix: Fire only once
        Timer timer = new Timer(3000, e -> {
            this.setVisible(false);
            this.dispose();
            SwingUtilities.invokeLater(() -> new MusicPlayerUI().setVisible(true));
        });
        timer.setRepeats(false); // ✅ Prevent infinite window creation
        timer.start();
    }
}
