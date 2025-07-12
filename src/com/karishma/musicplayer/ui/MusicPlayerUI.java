package com.karishma.musicplayer.ui;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class MusicPlayerUI extends JFrame {
    private JLabel trackLabel;
    private JButton playBtn, pauseBtn, stopBtn, nextBtn, prevBtn;
    private JLabel gifLabel;

    private ArrayList<File> playlist = new ArrayList<>();
    private int currentIndex = 0;
    private Clip clip;
    private boolean isPaused = false;

    public MusicPlayerUI() {
        setTitle("🎵 MiniSpotify");

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        initUI();
        loadPlaylist();
        loadTrack(currentIndex);
    }

    private void initUI() {
        // Top: Track name label
        trackLabel = new JLabel("Now Playing: ", SwingConstants.CENTER);
        trackLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(trackLabel, BorderLayout.NORTH);

        // Center: GIF label
        gifLabel = new JLabel();
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gifLabel.setOpaque(true);
        gifLabel.setBackground(Color.BLACK);
        gifLabel.setVisible(false); // Show only while playing
        add(gifLabel, BorderLayout.CENTER);

        // Bottom: Control buttons
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        prevBtn = new JButton("⏮ Prev");
        playBtn = new JButton("▶ Play");
        pauseBtn = new JButton("⏸ Pause");
        stopBtn = new JButton("⏹ Stop");
        nextBtn = new JButton("⏭ Next");

        controls.add(prevBtn);
        controls.add(playBtn);
        controls.add(pauseBtn);
        controls.add(stopBtn);
        controls.add(nextBtn);

        add(controls, BorderLayout.SOUTH);

        // Button actions
        playBtn.addActionListener(e -> playTrack());
        pauseBtn.addActionListener(e -> pauseTrack());
        stopBtn.addActionListener(e -> stopTrack());
        nextBtn.addActionListener(e -> nextTrack());
        prevBtn.addActionListener(e -> previousTrack());
    }

    private void loadPlaylist() {
        File musicDir = new File("src/music");
        if (!musicDir.exists() || !musicDir.isDirectory()) {
            JOptionPane.showMessageDialog(this, "Music folder not found!");
            return;
        }

        File[] files = musicDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(this, "No .wav files found in music folder.");
            return;
        }

        for (File f : files) playlist.add(f);
    }

    private void loadTrack(int index) {
        if (index < 0 || index >= playlist.size()) return;

        stopTrack();

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(playlist.get(index));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            trackLabel.setText("Now Playing: " + playlist.get(index).getName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading audio: " + e.getMessage());
        }
    }

    private void playTrack() {
    if (clip == null) return;

    if (isPaused) {
        clip.start();
        isPaused = false;
    } else {
        clip.setFramePosition(0);
        clip.start();
    }

    /// Create a File pointing to your gif
File gifFile = new File("src/assets/playing.gif");

// Check if file exists before using
if (gifFile.exists()) {
    // ✅ This uses the correct constructor: ImageIcon(String)
    ImageIcon gifIcon = new ImageIcon(gifFile.getAbsolutePath());

    // ✅ This works — JLabel accepts an ImageIcon
    gifLabel.setIcon((Icon) gifIcon);
    gifLabel.setVisible(true);
} else {
    System.out.println("GIF file not found: " + gifFile.getAbsolutePath());
}

}


    private void pauseTrack() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;

            // Optional: show static image on pause
            
        }
    }

    private void stopTrack() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            gifLabel.setVisible(false); // Hide gif when stopped
        }
    }

    private void nextTrack() {
        currentIndex = (currentIndex + 1) % playlist.size();
        loadTrack(currentIndex);
        playTrack();
    }

    private void previousTrack() {
        currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        loadTrack(currentIndex);
        playTrack();
    }
}
