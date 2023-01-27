module com.ryan.appui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
            
                            
    opens com.ryan.appui to javafx.fxml;
    exports com.ryan.appui;
}