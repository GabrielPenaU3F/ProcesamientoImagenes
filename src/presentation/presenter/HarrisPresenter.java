package presentation.presenter;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.action.characteristic_points.ApplyHarrisDetectorAction;
import core.action.image.LoadImageAction;
import domain.XYPoint;
import domain.customimage.CustomImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import presentation.controller.HarrisSceneController;
import presentation.util.InsertValuePopup;

public class HarrisPresenter {

    private final HarrisSceneController view;
    private final LoadImageAction loadImageAction;
    private final ApplyHarrisDetectorAction applyHarrisDetectorAction;
    private CustomImage image1;
    private CustomImage image2;

    public HarrisPresenter(HarrisSceneController harrisSceneController, LoadImageAction loadImageAction,
            ApplyHarrisDetectorAction applyHarrisDetectorAction) {
        this.view = harrisSceneController;
        this.loadImageAction = loadImageAction;
        this.applyHarrisDetectorAction = applyHarrisDetectorAction;
    }

    public void onSelectImage1() {
        this.image1 = this.loadImageAction.execute();
        this.view.imageView1.setImage(this.image1.toFXImage());
    }

    public void onSelectImage2() {
        this.image2 = this.loadImageAction.execute();
        this.view.imageView2.setImage(this.image2.toFXImage());
    }

    public void onApply() {

        if (bothImagesArePresent()) {

            double tolerance = Double.parseDouble(InsertValuePopup.show("Insert maximum-percent for filtering fake corners", "0").get());

            List<XYPoint> image1Corners = this.applyHarrisDetectorAction.execute(this.image1, tolerance);
            List<XYPoint> image2Corners = this.applyHarrisDetectorAction.execute(this.image2, tolerance);

            this.markImage1Corners(image1Corners);
            this.markImage2Corners(image2Corners);

            int largerSize = Math.max(image1Corners.size(), image2Corners.size());
            int shorterSize = Math.min(image1Corners.size(), image2Corners.size());

            String diffPercentString = new DecimalFormat("#.##").format((largerSize - shorterSize) * 100 / (double) largerSize);
            this.view.resultLabel.setText("Images differ on a " + diffPercentString + "% of characteristic points");

        }
    }

    private void markImage1Corners(List<XYPoint> image1Corners) {

        WritableImage image = new WritableImage(image1.getWidth(), image1.getHeight());
        PixelWriter writer = image.getPixelWriter();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                XYPoint xyPoint = new XYPoint(i, j);
                if (image1Corners.contains(xyPoint)) {
                    this.markPoint(writer, i, j);
                } else {
                    writer.setColor(i, j, image1.getColor(i, j));
                }

            }
        }
        this.view.imageView1.setImage(image);
    }

    private void markImage2Corners(List<XYPoint> image2Corners) {
        WritableImage image = new WritableImage(image2.getWidth(), image2.getHeight());
        PixelWriter writer = image.getPixelWriter();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {

                XYPoint xyPoint = new XYPoint(i, j);
                if (image2Corners.contains(xyPoint)) {
                    this.markPoint(writer, i, j);
                } else {
                    writer.setColor(i, j, image2.getColor(i, j));
                }

            }
        }

        this.view.imageView2.setImage(image);
    }

    private void markPoint(PixelWriter writer, int i, int j) {
        //TODO: Estaria bueno que aca pintara un circulo rojo que sea mas visible que los puntintos chotos
        writer.setColor(i, j, Color.RED);
    }

    private boolean bothImagesArePresent() {
        return this.image1 != null && this.image2 != null;
    }
}
