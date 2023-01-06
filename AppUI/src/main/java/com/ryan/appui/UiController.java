package com.ryan.appui;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintStream;

public class UiController {


    @FXML
    private TextField URLInput;
    @FXML
    private Button convertWikiToAudio;
    @FXML
    private TextArea terminalOutput;

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
        Task<Void> processingTask = new Task<>() {
            @Override
            protected Void call() {
                ProcessBuilder p = new ProcessBuilder("python", "./AppUI/python_scripts/Main.py").redirectErrorStream(true);
                Process process;
                try {
                    process = p.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    process.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        };

        new Thread(processingTask).start();

        processingTask.setOnRunning(e -> {
            convertWikiToAudio.setDisable(true);
            textOutput.append("Processing page to audiofile...\n");
            terminalOutput.setText(textOutput.toString());
        });
        processingTask.setOnSucceeded(e -> {
            textOutput.append("...File ready.\n");
            terminalOutput.setText(textOutput.toString());
            convertWikiToAudio.setDisable(false);
        });
    }

}