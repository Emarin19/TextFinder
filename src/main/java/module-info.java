module TextFinder.main {
    requires  javafx.fxml;
    requires  javafx.controls;
    requires poi;
    requires poi.ooxml;
    requires org.apache.pdfbox;
    requires java.desktop;
    opens  cr.ac.tec.TextFinder to javafx.fxml;
    exports cr.ac.tec.TextFinder;
    exports cr.ac.tec.TextFinder.documents;
}