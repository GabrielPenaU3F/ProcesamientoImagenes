package presentation.presenter;

import core.action.channels.semaphore.ChannelSemaphore;
import core.action.edit.ModifyPixelAction;
import core.action.figure.semaphore.FigureSemaphore;
import core.action.gradient.semaphore.GradientSemaphore;
import core.action.image.GetImageAction;
import core.action.image.LoadImageAction;
import core.action.image.SaveImageAction;
import core.action.modifiedimage.GetModifiedImageAction;
import core.action.modifiedimage.PutModifiedImageAction;
import domain.Channel;
import domain.Figure;
import domain.Gradient;
import domain.customimage.CustomImage;
import io.reactivex.functions.Action;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import presentation.controller.MainSceneController;
import presentation.scenecreator.ChannelSceneCreator;
import presentation.scenecreator.ImageFigureSceneCreator;
import presentation.scenecreator.ImageGradientSceneCreator;
import presentation.scenecreator.ImageInformSceneCreator;
import presentation.util.InsertValuePopup;
import presentation.view.CustomImageView;

import java.awt.image.BufferedImage;

public class MainPresenter {

    private static final String EMPTY = "";
    public static final int INDEX_OUT_OF_BOUND = -1;
    private final MainSceneController view;
    private final LoadImageAction loadImageAction;
    private GetImageAction getImageAction;
    private final GetModifiedImageAction getModifiedImageAction;
    private ModifyPixelAction modifyPixelAction;
    private final SaveImageAction saveImageAction;
    private PutModifiedImageAction putModifiedImageAction;

    public MainPresenter(MainSceneController view,
                         LoadImageAction loadImageAction,
                         GetImageAction getImageAction,
                         GetModifiedImageAction getModifiedImageAction,
                         PutModifiedImageAction putModifiedImageAction,
                         ModifyPixelAction modifyPixelAction,
                         SaveImageAction saveImageAction) {

        this.view = view;

        this.loadImageAction = loadImageAction;
        this.getImageAction = getImageAction;
        this.getModifiedImageAction = getModifiedImageAction;
        this.modifyPixelAction = modifyPixelAction;
        this.saveImageAction = saveImageAction;
        this.putModifiedImageAction = putModifiedImageAction;
    }

    public void initialize() {
        view.customImageView = new CustomImageView(view.groupImageView, view.imageView)
                .withSetPickOnBounds(true)
                .withOnPixelClick(this::onPixelClick)
                .withSelectionMode();
    }

    private Action onPixelClick(Integer x, Integer y) {
        return () -> {
            view.pixelX.setText(x.toString());
            view.pixelY.setText(y.toString());
            onCalculatePixelValue();
        };
    }

    public void onOpenImage() {
        BufferedImage bufferedImage = this.loadImageAction.execute().getBufferedImage();
        view.customImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
    }

    public void onSaveImage() {
        String filename = InsertValuePopup.show("Insert name", "file").get();
        this.getModifiedImageAction.execute()
                .ifPresent(image -> saveImageAction.execute(image, filename));
    }

    public void onShowGreyGradient() {
        GradientSemaphore.setValue(Gradient.GREY);
        new ImageGradientSceneCreator().createScene();
    }

    public void onShowColorGradient() {
        GradientSemaphore.setValue(Gradient.COLOR);
        new ImageGradientSceneCreator().createScene();
    }

    public void onShowRGBImageRedChannel() {
        ChannelSemaphore.setValue(Channel.RED);
        new ChannelSceneCreator().createScene();
    }

    public void onShowRGBImageGreenChannel() {
        ChannelSemaphore.setValue(Channel.GREEN);
        new ChannelSceneCreator().createScene();
    }

    public void onShowRGBImageBlueChannel() {
        ChannelSemaphore.setValue(Channel.BLUE);
        new ChannelSceneCreator().createScene();
    }

    public void onShowImageWithQuadrate() {
        FigureSemaphore.setValue(Figure.QUADRATE);
        new ImageFigureSceneCreator().createScene();
    }

    public void onShowImageWithCircle() {
        FigureSemaphore.setValue(Figure.CIRCLE);
        new ImageFigureSceneCreator().createScene();
    }

    public void onShowHueHSVChannel() {
        ChannelSemaphore.setValue(Channel.HUE);
        new ChannelSceneCreator().createScene();
    }

    public void onShowSaturationHSVChannel() {
        ChannelSemaphore.setValue(Channel.SATURATION);
        new ChannelSceneCreator().createScene();
    }

    public void onShowValueHSVChannel() {
        ChannelSemaphore.setValue(Channel.VALUE);
        new ChannelSceneCreator().createScene();
    }

    public void onCalculatePixelValue() {
        if (this.validatePixelCoordinates()) {

            int pixelX = Integer.parseInt(view.pixelX.getText());
            int pixelY = Integer.parseInt(view.pixelY.getText());

            this.getImageAction.execute()
                    .map(customImage -> {
                        try {
                            return customImage.getPixelValue(pixelX, pixelY);
                        } catch (IndexOutOfBoundsException e) {
                            return INDEX_OUT_OF_BOUND;
                        }
                    })
                    .ifPresent(rgb -> {
                        view.pixelValue.setText(rgb.toString());
                    });

        } else {
            view.pixelValue.setText("Error");
        }
    }

    private boolean validatePixelCoordinates() {
        return (!view.pixelX.getText().equals(EMPTY) && !view.pixelY.getText().equals(EMPTY));
    }

    public void onModifyPixelValue() {
        if (this.validatePixelCoordinates()) {

            Integer pixelX = Integer.parseInt(view.pixelX.getText());
            Integer pixelY = Integer.parseInt(view.pixelY.getText());

            String newValue = InsertValuePopup.show("Insertar valor", "0").get();

            Image modifiedImage = modifyPixelAction.execute(pixelX, pixelY, newValue);

            view.modifiedImageView.setImage(modifiedImage);

        } else {
            view.pixelValue.setText("Seleccione pixel");
        }
    }

    public void onShowReport() {
        new ImageInformSceneCreator().createScene();
    }

    public void onCutPartialImage() {
        Image image = view.customImageView.cutPartialImage();
        view.modifiedImageView.setImage(image);
        this.putModifiedImageAction.execute(new CustomImage(SwingFXUtils.fromFXImage(image, null), "png"));
    }
}
