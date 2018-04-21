package presentation.presenter.space_domain_operations;

import core.action.edit.space_domain.ApplyContrastAction;
import core.action.image.GetImageAction;
import core.service.statistics.GrayLevelStatisticsService;
import domain.customimage.CustomImage;
import io.reactivex.subjects.PublishSubject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import presentation.controller.ContrastSceneController;

import java.util.Optional;

public class ContrastScenePresenter {

    private static final String EMPTY = "";

    private final ContrastSceneController view;
    private final ApplyContrastAction applyContrastAction;
    private final GetImageAction getImageAction;
    private final GrayLevelStatisticsService grayLevelStatisticsService;
    private final PublishSubject<Image> modifiedImagePublishSubject;

    private int r1;
    private int r2;
    private CustomImage customImage;

    public ContrastScenePresenter(ContrastSceneController contrastSceneController,
                                  ApplyContrastAction applyContrastAction,
                                  GetImageAction getImageAction,
                                  GrayLevelStatisticsService grayLevelStatisticsService,
                                  PublishSubject<Image> modifiedImagePublishSubject) {

        this.view = contrastSceneController;
        this.applyContrastAction = applyContrastAction;
        this.getImageAction = getImageAction;
        this.grayLevelStatisticsService = grayLevelStatisticsService;
        this.modifiedImagePublishSubject = modifiedImagePublishSubject;
    }

    public void onInitializeView() {
        Optional<CustomImage> customImageOptional = this.getImageAction.execute();
        if (!customImageOptional.isPresent()) {
            this.view.closeWindow();
            return;
        }

        customImage = customImageOptional.get();
        Image image = SwingFXUtils.toFXImage(customImage.getBufferedImage(), null);
        double mean = this.grayLevelStatisticsService.calculateGrayLevelMean(image);
        double standardDeviation = this.grayLevelStatisticsService.calculateGrayLevelStantardDeviation(image);

        r1 = ((int) mean) - ((int) standardDeviation);
        r2 = ((int) mean) + ((int) standardDeviation);

        if (r2 > 255) r2 = 255; //This is to prevent cases where r2 exceeds the gray value limit. See for instance the Cameraman image mean + sd.

        view.s1ValidationLabel.setText("S1 must be lesser than R1: " + r1);
        view.s2ValidationLabel.setText("S2 must be greater than R2: " + r2);
    }

    public void onApplyContrast() {
        if (!areFieldsEmpty()) {
            int s1 = Integer.parseInt(this.view.s1Field.getText());
            int s2 = Integer.parseInt(this.view.s2Field.getText());

            if ((s1 < r1) && (s2 > r2) && (s1 < s2)) {
                sendModifiedImageToMainView(this.applyContrastAction.execute(customImage, s1, s2, r1, r2));
                this.view.closeWindow();
            } else if (s1 > r1){
                this.view.s1ValidationLabel.setText("S1 must be lesser than " + r1);
            } else if (s2 < r2) {
                this.view.s2ValidationLabel.setText("S2 must be greater than " + r2);
            }
        }
    }

    private boolean areFieldsEmpty() {
        return this.view.s1Field.getText().equals(EMPTY) || this.view.s2Field.getText().equals(EMPTY);
    }

    private void sendModifiedImageToMainView(Image image) {
        this.modifiedImagePublishSubject.onNext(image);
    }
}
