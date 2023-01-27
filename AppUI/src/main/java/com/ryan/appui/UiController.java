package com.ryan.appui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

public class UiController {

    private final String AUDIO_FILE_PATH = "AppUI/temp/";
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    @FXML
    private TextField URLInput;
    @FXML
    private Button convertWikiToAudio;
    @FXML
    private TextArea terminalOutput;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;

    private StringBuilder textOutput;

//    private CustomOutputStream customOutputStream = new CustomOutputStream(outputTextArea);

    public void initialize() {
        textOutput = new StringBuilder();
        textOutput.append("Welcome to the Wikipedia text-to-voice application." +
                "\nPlease copy & paste your desired wikipedia URL to be converted.\n");
        terminalOutput.setText(textOutput.toString());
    }

    @FXML
    protected void runPythonScript() {
        Task<Integer> processingTask = new Task<>() {
            @Override
            protected Integer call() {
                ProcessBuilder p = new ProcessBuilder("python", "./AppUI/python_scripts/Main.py", URLInput.getText())
                        .redirectErrorStream(true);
                Process process;
                try {
                    process = p.start();
                    process.waitFor();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return process.exitValue();
            }
        };

        new Thread(processingTask).start();

        processingTask.setOnRunning(e -> {
            convertWikiToAudio.setDisable(true);
            textOutput.append("Processing page to audiofile...\n");
            terminalOutput.setText(textOutput.toString());
        });
        processingTask.setOnSucceeded(e -> {
            if (processingTask.getValue() != 0) {
                textOutput.append("...Invalid URL used.\n");
            }
            else {
                File file = new File(AUDIO_FILE_PATH).listFiles()[0];
                textOutput.append("Audiofile created: " + file.getAbsolutePath() + "\n");

                setMediaPlayer(new Media(file.toURI().toString()));
                setMediaView(this.mediaPlayer);

                playButton.setDisable(false);
                pauseButton.setDisable(false);
                textOutput.append("...File ready.\n");
            }
            terminalOutput.setText(textOutput.toString());
            convertWikiToAudio.setDisable(false);

        });
    }

    @FXML
    protected void playAudio() { mediaView.getMediaPlayer().play(); }

    @FXML
    protected void pauseAudio() {
        mediaView.getMediaPlayer().pause();
    }

    public void setMediaPlayer(Media media) {
        this.mediaPlayer = new MediaPlayer(media);
    }
    public void setMediaView(MediaPlayer player) { this.mediaView = new MediaView(player); }
}