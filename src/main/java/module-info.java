module TextFinder.main {
    requires  javafx.fxml;
    requires  javafx.controls;
    requires poi;
    requires poi.ooxml;
    requires java.desktop;
    opens  cr.ac.tec.TextFinder to javafx.fxml;
    exports cr.ac.tec.TextFinder;
}