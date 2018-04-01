package presentation.presenter;

import core.action.UpdateMainViewAction;
import core.action.edit.space_domain.ApplyContrastAction;
import core.action.image.GetImageAction;
import core.provider.PresenterProvider;
import core.service.GrayLevelStatisticsService;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import presentation.controller.ContrastSceneController;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class ContrastScenePresenter {

    private static final String EMPTY = "";

    private final ContrastSceneController view;
    private final ApplyContrastAction applyContrastAction;
    private final GetImageAction getImageAction;
    private final GrayLevelStatisticsService grayLevelStatisticsService;
    private final UpdateMainViewAction updateMainViewAction;

    public ContrastScenePresenter(ContrastSceneController contrastSceneController, ApplyContrastAction applyContrastAction, GetImageAction getImageAction, UpdateMainViewAction updateMainViewAction, GrayLevelStatisticsService grayLevelStatisticsService) {
        this.view = contrastSceneController;
        this.applyContrastAction = applyContrastAction;
        this.getImageAction = getImageAction;
        this.updateMainViewAction = updateMainViewAction;
        this.grayLevelStatisticsService = grayLevelStatisticsService;
    }

    public void onApplyContrast() {

        Optional<CustomImage> customImageOptional = this.getImageAction.execute();
        if (customImageOptional.isPresent()) {

            Image image = SwingFXUtils.toFXImage(customImageOptional.get().getBufferedImage(), null);
            double mean = this.grayLevelStatisticsService.calculateGrayLevelMean(image);
            double standardDeviation = this.grayLevelStatisticsService.calculateGrayLevelStantardDeviation(image);

            int r1 = ((int)mean) - ((int)standardDeviation);
            int r2 = ((int)mean) + ((int)standardDeviation);

            if (!(this.view.s1Field.getText().equals(EMPTY) || this.view.s2Field.getText().equals(EMPTY))) {
                int s1 = Integer.parseInt(this.view.s1Field.getText());
                int s2 = Integer.parseInt(this.view.s2Field.getText());

                if (s1 < r1) {
                    image = this.applyContrastAction.execute(s1, s2, r1, r2);
                    this.updateMainViewAction.execute(image);
                    this.closeWindow();
                } else {
                    this.view.s1ValidationLabel.setText(String.format("Must be lesser than %d", r1));
                }
            }
        }

    }

    private void closeWindow() {
        Stage stage = (Stage) this.view.s1Field.getScene().getWindow();
        stage.close();
    }
}
