module org.gemseeker.sms {
    requires java.sql;
    requires java.xml;
    requires java.desktop;

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;

    requires org.apache.commons.io;
    requires com.h2database;
    requires org.reactivestreams;
    requires io.reactivex.rxjava2;
    requires rxjavafx;

    requires feathericons;

    opens org.gemseeker.sms.views to javafx.fxml;
    opens org.gemseeker.sms.views.panels to javafx.fxml;
    opens org.gemseeker.sms.views.forms to javafx.fxml;
    //opens org.gemseeker.sms.views.fonts to javafx.fxml;

    opens org.gemseeker.sms.data to javafx.base;
    opens org.gemseeker.sms.data.controllers.models to javafx.base;

    exports org.gemseeker.sms;
    exports org.gemseeker.sms.data;
}