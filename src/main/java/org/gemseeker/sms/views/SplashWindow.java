package org.gemseeker.sms.views;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.gemseeker.sms.AppMain;
import org.gemseeker.sms.Utils;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class SplashWindow extends AbstractWindow {

    @FXML private VBox vbox;
    @FXML private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    public SplashWindow() {
        super("", SplashWindow.class.getResource("splash.fxml"), null, null);
    }

    @Override
    protected void initWindow(Stage stage) {
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("logo_v3.png"))));
    }

    @Override
    protected void onFxmlLoaded() {

    }

    @Override
    protected void onShow() {
        if (mediaPlayer == null) {
            File file = new File(Utils.SPLASH_MEDIA_PATH);
            if (file.exists()) {
                vbox.setVisible(false);
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setMute(true);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.play();
            } else {
                mediaView.setVisible(false);
            }
        }
    }

    @Override
    protected void onClose() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }
}
