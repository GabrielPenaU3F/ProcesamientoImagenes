package presentation.presenter;

import core.action.edit.space_domain.NormalizeImageAction;
import core.action.edit.space_domain.operations.MultiplyImageWithScalarNumberAction;
import core.action.edit.space_domain.operations.MultiplyImagesAction;
import core.action.edit.space_domain.operations.SumImagesAction;
import core.action.image.LoadImageAction;
import domain.customimage.CustomImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import presentation.util.InsertValuePopup;

public class ImagesOperationsPresenter {

    private final LoadImageAction loadImageAction;
    private final NormalizeImageAction normalizeImageAction;
    private final SumImagesAction sumImagesAction;
    private final MultiplyImagesAction multiplyImagesAction;
    private final MultiplyImageWithScalarNumberAction multiplyImageWithScalarNumberAction;
    private CustomImage image1;
    private CustomImage image2;

    public ImagesOperationsPresenter(LoadImageAction loadImageAction, NormalizeImageAction normalizeImageAction,
                                     SumImagesAction sumImagesAction, MultiplyImagesAction multiplyImagesAction,
                                     MultiplyImageWithScalarNumberAction multiplyImageWithScalarNumberAction) {
        this.loadImageAction = loadImageAction;
        this.normalizeImageAction = normalizeImageAction;
        this.sumImagesAction = sumImagesAction;
        this.multiplyImagesAction = multiplyImagesAction;
        this.multiplyImageWithScalarNumberAction = multiplyImageWithScalarNumberAction;
    }

    public Image onMakeImagesSum() {
        Image normalizedImage1 = this.normalizeImageAction.execute(image1,image2);
        Image normalizedImage2 = this.normalizeImageAction.execute(image2,image1);
        return this.sumImagesAction.execute(normalizedImage1,normalizedImage2);
    }

    public Image onMakeImagesMultiplication() {
        Image normalizedImage1 = this.normalizeImageAction.execute(image1,image2);
        Image normalizedImage2 = this.normalizeImageAction.execute(image2,image1);
        return this.multiplyImagesAction.execute(normalizedImage1,normalizedImage2);
    }

    public Image onMakeImageMultiplicationWithScalarNumber() {
        String receivedValue = InsertValuePopup.show("Insert scalar number (Remember that the image #1 will " +
                "participate in the multiplication)" , "0").get();
        int scalarNumber = Integer.parseInt(receivedValue);
        return this.multiplyImageWithScalarNumberAction.execute(image1, scalarNumber);
    }

    public Image onloadImage1() {
        this.image1 = this.loadImageAction.execute();
        return SwingFXUtils.toFXImage(this.image1.getBufferedImage(), null );
    }

    public Image onloadImage2() {
        this.image2 = this.loadImageAction.execute();
        return SwingFXUtils.toFXImage(this.image2.getBufferedImage(), null );
    }



}
