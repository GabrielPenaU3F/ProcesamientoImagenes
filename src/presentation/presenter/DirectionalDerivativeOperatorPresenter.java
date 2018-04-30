package presentation.presenter;

import core.action.edgedetector.ApplyDirectionalDerivativeOperatorAction;
import core.action.image.GetImageAction;
import domain.FilterSemaphore;
import domain.customimage.CustomImage;
import domain.mask.Mask;
import domain.mask.derivativedirectionaloperator.standard.HorizontalStraightMask;
import domain.mask.derivativedirectionaloperator.standard.MainDiagonalMask;
import domain.mask.derivativedirectionaloperator.standard.SecondaryDiagonalMask;
import domain.mask.derivativedirectionaloperator.standard.VerticalStraightMask;

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
                        this.applyDerivateDirectionalOperatorStandard(customImage);
                    }
                });
    }

    private void applyDerivateDirectionalOperatorStandard(CustomImage customImage) {
        Mask horizontalStraightMask = new HorizontalStraightMask();
        Mask verticalStraightMask = new VerticalStraightMask();
        Mask mainDiagonalMask = new MainDiagonalMask();
        Mask secondaryDiagonalMask = new SecondaryDiagonalMask();

        applyDirectionalDerivativeOperatorAction.execute(customImage, horizontalStraightMask, verticalStraightMask, mainDiagonalMask, secondaryDiagonalMask);
    }
}
