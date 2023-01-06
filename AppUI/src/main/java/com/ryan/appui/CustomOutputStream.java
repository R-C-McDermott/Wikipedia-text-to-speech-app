package com.ryan.appui;

import javafx.scene.control.TextArea;

import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {
    private TextArea textArea;
    public CustomOutputStream(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b){
        textArea.appendText(String.valueOf((char)b));
    }
}
