package org.gemseeker.sms.views.panels;

import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import io.reactivex.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.gemseeker.sms.data.Database;
import org.gemseeker.sms.data.controllers.TowerController;
import org.gemseeker.sms.views.MainWindow;
import org.gemseeker.sms.views.panels.maps.LineLayer;
import org.gemseeker.sms.views.panels.maps.SourceLayer;
import org.gemseeker.sms.views.panels.maps.TowerLayer;

public class MapsPanel extends AbstractPanel {

    @FXML private StackPane stackPane;
    private MapView mapView;

    private final MainWindow mainWindow;
    private final TowerController towerController;
    private final CompositeDisposable disposables;

    private SourceLayer sourceLayer;
    private TowerLayer towerLayer;
    private LineLayer lineLayer;

    public MapsPanel(MainWindow mainWindow, Database database) {
        super(MapsPanel.class.getResource("maps.fxml"));
        this.mainWindow = mainWindow;
        towerController = new TowerController(database);
        disposables = new CompositeDisposable();
    }

    @Override
    protected void onFxmlLoaded() {

    }

    @Override
    public void onResume() {
        showProgress("Retrieving Tower entries...");
        disposables.add(Single.fromCallable(towerController::getAll)
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(list -> {
                    hideProgress();
                    if (mapView == null) {
                        mapView = new MapView();
                        mapView.setZoom(15);
                        mapView.setCenter(new MapPoint(6.3414027, 124.7205275));
                        stackPane.getChildren().clear();
                        stackPane.getChildren().add(mapView);
                    }

                    if (sourceLayer == null) {
                        sourceLayer = new SourceLayer();
                    }

                    // clear layers
                    mapView.removeLayer(lineLayer);
                    mapView.removeLayer(towerLayer);
                    mapView.removeLayer(sourceLayer);

                    lineLayer = new LineLayer(list);
                    towerLayer = new TowerLayer(list);
                    mapView.addLayer(lineLayer);
                    mapView.addLayer(towerLayer);
                    mapView.addLayer(sourceLayer);

                }, err -> {
                    hideProgress();
                    showErrorDialog("Database Error", "Error while retrieving Tower entries.\n" + err);
                }));
    }


    @Override
    public void onPause() {

    }

    private void showProgress(String text) {
        mainWindow.showProgress(-1, text);
    }

    private void hideProgress() {
        mainWindow.hideProgress();
    }

    @Override
    public void onDispose() {

    }
}
