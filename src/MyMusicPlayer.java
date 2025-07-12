import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MyMusicPlayer {
    public static void main(String[] args) {
        System.out.println("Welcome to Spotify Music Player!");

        String filepath = "src\\Golden Hour.wav";
        File file = new File(filepath);
        if (!file.exists()) {
            System.out.println("File not found: " + filepath);
            return;
        }

        try (Scanner scanner = new Scanner(System.in);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)) {

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            String response = "";

            System.out.println("P = Play ");
            System.out.println("S = Stop ");
            System.out.println("R = Reset ");
            System.out.println("Q = Quit ");

            while (!response.equals("Q")) {
                System.out.print("Enter your choice: ");
                response = scanner.next().toUpperCase();

                switch (response) {
                    case "P":
                        if (!clip.isRunning()) {
                            clip.start();
                            System.out.println("Playing music...");
                        } else {
                            System.out.println("Music is already playing.");
                        }
                        break;

                    case "S":
                        if (clip.isRunning()) {
                            clip.stop();
                            System.out.println("Music stopped.");
                        } else {
                            System.out.println("Music is not playing.");
                        }
                        break;

                    case "R":
                        clip.setFramePosition(0);
                        System.out.println("Music reset to the beginning.");
                        break;

                    case "Q":
                        if (clip.isRunning()) {
                            clip.stop();
                        }
                        System.out.println("Exiting the music player. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter P, Q, R, or S.");
                }
            }

            clip.close();
        } catch (LineUnavailableException e) {
            System.out.println("Audio line for playing back is unavailable: " + e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading the audio file: " + e.getMessage());
        }
    }
}
