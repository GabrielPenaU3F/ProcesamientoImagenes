package presentation.presenter;

import core.action.edgedetector.ApplyDirectionalDerivativeOperatorAction;
import core.action.image.GetImageAction;
import domain.FilterSemaphore;
import domain.customimage.CustomImage;
import domain.mask.Mask;
import domain.mask.directional_derivative_operator.kirsh.KirshHorizontalStraightMask;
import domain.mask.directional_derivative_operator.kirsh.KirshMainDiagonalMask;
import domain.mask.directional_derivative_operator.kirsh.KirshSecondaryDiagonalMask;
import domain.mask.directional_derivative_operator.kirsh.KirshVerticalStraightMask;
import domain.mask.directional_derivative_operator.prewitt.PrewittHorizontalStraightMask;
import domain.mask.directional_derivative_operator.prewitt.PrewittMainDiagonalMask;
import domain.mask.directional_derivative_operator.prewitt.PrewittSecondaryDiagonalMask;
import domain.mask.directional_derivative_operator.prewitt.PrewittVerticalStraightMask;
import domain.mask.directional_derivative_operator.sobel.SobelHorizontalStraightMask;
import domain.mask.directional_derivative_operator.sobel.SobelMainDiagonalMask;
import domain.mask.directional_derivative_operator.sobel.SobelSecondaryDiagonalMask;
import domain.mask.directional_derivative_operator.sobel.SobelVerticalStraightMask;
import domain.mask.directional_derivative_operator.standard.StandardHorizontalStraightMask;
import domain.mask.directional_derivative_operator.standard.StandardMainDiagonalMask;
import domain.mask.directional_derivative_operator.standard.StandardSecondaryDiagonalMask;
import domain.mask.directional_derivative_operator.standard.StandardVerticalStraightMask;

public class DirectionalDerivativeOperatorPresenter {

    private final GetImageAction getImageAction;
    private final ApplyDirectionalDerivativeOperatorAction applyDirectionalDerivativeOperatorAction;

    public DirectionalDerivativeOperatorPresenter(GetImageAction getImageAction,
                                                  ApplyDirectionalDerivativeOperatorAction applyDirectionalDerivativeOperatorAction) {

        this.getImageAction = getImageAction;
        this.applyDirectionalDerivativeOperatorAction = applyDirectionalDerivativeOperatorAction;
    }

    public void onInitialize() {
        this.getImageAction.execute()
                .ifPresent(customImage -> {
                    if (FilterSemaphore.is(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_STANDARD)) {
                        this.applyStandardMask(customImage);
                    }

                    if (FilterSemaphore.is(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_KIRSH)) {
                        this.applyKirshMask(customImage);
                    }

                    if (FilterSemaphore.is(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_PREWITT)) {
                        this.applyPrewittMask(customImage);
                    }

                    if (FilterSemaphore.is(Mask.Type.DERIVATE_DIRECTIONAL_OPERATOR_SOBEL)) {
                        this.applySobelMask(customImage);
                    }
                });
    }

    private void applyStandardMask(CustomImage customImage) {
        Mask horizontalStraightMask = new StandardHorizontalStraightMask();
        Mask verticalStraightMask = new StandardVerticalStraightMask();
        Mask mainDiagonalMask = new StandardMainDiagonalMask();
        Mask secondaryDiagonalMask = new StandardSecondaryDiagonalMask();

        applyDirectionalDerivativeOperatorAction.execute(customImage,
                horizontalStraightMask, verticalStraightMask,
                mainDiagonalMask, secondaryDiagonalMask);
    }

    private void applyKirshMask(CustomImage customImage) {
        Mask horizontalStraightMask = new KirshHorizontalStraightMask();
        Mask verticalStraightMask = new KirshVerticalStraightMask();
        Mask mainDiagonalMask = new KirshMainDiagonalMask();
        Mask secondaryDiagonalMask = new KirshSecondaryDiagonalMask();

        applyDirectionalDerivativeOperatorAction.execute(customImage,
                horizontalStraightMask, verticalStraightMask,
                mainDiagonalMask, secondaryDiagonalMask);
    }

    private void applyPrewittMask(CustomImage customImage) {
        Mask horizontalStraightMask = new PrewittHorizontalStraightMask();
        Mask verticalStraightMask = new PrewittVerticalStraightMask();
        Mask mainDiagonalMask = new PrewittMainDiagonalMask();
        Mask secondaryDiagonalMask = new PrewittSecondaryDiagonalMask();

        applyDirectionalDerivativeOperatorAction.execute(customImage,
                horizontalStraightMask, verticalStraightMask,
                mainDiagonalMask, secondaryDiagonalMask);
    }

    private void applySobelMask(CustomImage customImage) {
        Mask horizontalStraightMask = new SobelHorizontalStraightMask();
        Mask verticalStraightMask = new SobelVerticalStraightMask();
        Mask mainDiagonalMask = new SobelMainDiagonalMask();
        Mask secondaryDiagonalMask = new SobelSecondaryDiagonalMask();

        applyDirectionalDerivativeOperatorAction.execute(customImage,
                horizontalStraightMask, verticalStraightMask,
                mainDiagonalMask, secondaryDiagonalMask);
    }
}
