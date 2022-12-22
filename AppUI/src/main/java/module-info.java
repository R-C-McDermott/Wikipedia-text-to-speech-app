module com.ryan.appui {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.ryan.appui to javafx.fxml;
    exports com.ryan.appui;
}