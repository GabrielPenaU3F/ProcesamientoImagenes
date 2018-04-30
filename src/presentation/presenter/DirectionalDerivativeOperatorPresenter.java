package presentation.presenter;

import core.action.edgedetector.ApplyDirectionalDerivativeOperatorAction;
import core.action.image.GetImageAction;
import domain.FilterSemaphore;
import domain.customimage.CustomImage;
import domain.mask.Mask;
import domain.mask.derivativedirectionaloperator.kirsh.KirshHorizontalStraightMask;
import domain.mask.derivativedirectionaloperator.kirsh.KirshMainDiagonalMask;
import domain.mask.derivativedirectionaloperator.kirsh.KirshSecondaryDiagonalMask;
import domain.mask.derivativedirectionaloperator.kirsh.KirshVerticalStraightMask;
import domain.mask.derivativedirectionaloperator.standard.StandardHorizontalStraightMask;
import domain.mask.derivativedirectionaloperator.standard.StandardMainDiagonalMask;
import domain.mask.derivativedirectionaloperator.standard.StandardSecondaryDiagonalMask;
import domain.mask.derivativedirectionaloperator.standard.StandardVerticalStraightMask;

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
}
