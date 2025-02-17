package org.gemseeker.sms.views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.gemseeker.sms.views.icons.FolderIcon;
import org.gemseeker.sms.views.icons.ZoomInIcon;
import org.gemseeker.sms.views.icons.ZoomOutIcon;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ShowAttachmentWindow extends AbstractWindow {

    @FXML private Button btnZoomIn;
    @FXML private Button btnZoomOut;
    @FXML private Button btnShowInFolder;
    @FXML private ImageView imageView;

    private String directory;
    private Image imageFile;

    public ShowAttachmentWindow(MainWindow mainWindow) {
        super("Attachment", ShowAttachmentWindow.class.getResource("show_attachment.fxml"), null, mainWindow.getStage());
    }

    @Override
    protected void onFxmlLoaded() {
        btnZoomIn.setGraphic(new ZoomInIcon(14));
        btnZoomIn.setOnAction(evt -> {
            if (imageFile != null) {
                imageView.setFitWidth(imageView.getFitWidth() + (imageView.getFitWidth() * 0.2));
                imageView.setFitHeight(imageView.getFitHeight() + (imageView.getFitHeight() * 0.2));
            }
        });

        btnZoomOut.setGraphic(new ZoomOutIcon(14));
        btnZoomOut.setOnAction(evt -> {
            if (imageFile != null) {
                imageView.setFitWidth(imageView.getFitWidth() - (imageView.getFitWidth() * 0.2));
                imageView.setFitHeight(imageView.getFitHeight() - (imageView.getFitHeight() * 0.2));
            }
        });

        btnShowInFolder.setGraphic(new FolderIcon(14));
        btnShowInFolder.setOnAction(evt -> {
            if (directory != null) {
                File folder = new File(directory);
                try {
                    Desktop.getDesktop().open(folder);
                } catch (IOException e) {
                    showErrorDialog("IOException", "Failed to open folder.\n" + e);
                }
            }
        });
    }

    public void show(Image image, String directory) {
        if (image == null && directory == null) {
            showWarningDialog("Invalid Action", "No image file.");
            return;
        }
        this.directory = directory;
        imageFile = image;
        super.show();
    }

    @Override
    protected void onShow() {
        if (imageFile == null) return;
        imageView.setImage(imageFile);
    }

    @Override
    protected void onClose() {
        imageView.setImage(null);
        imageFile = null;
    }

}
