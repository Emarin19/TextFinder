module TextFinder.main {
    requires  javafx.fxml;
    requires  javafx.controls;
    opens  cr.ac.tec.TextFinder to javafx.fxml;
    exports cr.ac.tec.TextFinder;
}