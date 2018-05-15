package presentation.presenter;

import core.action.image.GetImageAction;
import core.action.noise.ApplySaltAndPepperNoiseAction;
import domain.customimage.CustomImage;
import presentation.controller.SaltAndPepperNoiseController;

import java.util.Optional;

public class SaltAndPepperNoisePresenter {

    private static final String EMPTY = "";
    private final SaltAndPepperNoiseController view;
    private final GetImageAction getImageAction;
    private final ApplySaltAndPepperNoiseAction applySaltAndPepperNoiseAction;

    public SaltAndPepperNoisePresenter(
            SaltAndPepperNoiseController view,
            GetImageAction getImageAction,
            ApplySaltAndPepperNoiseAction applySaltAndPepperNoiseAction) {

        this.view = view;
        this.getImageAction = getImageAction;
        this.applySaltAndPepperNoiseAction = applySaltAndPepperNoiseAction;
    }

    public void onInitializeView() {
        view.p0ValidationLabel.setText("P0 must be lesser than 1 | E(0,1)");
        view.p1ValidationLabel.setText("P1 must be greater than P0 | E(0,1)");
        view.percentValidationLabel.setText("Percent must be greater than 0 and lesser than 100");
    }

    public void onApplyNoise() {

        Optional<CustomImage> optionalImage = getImageAction.execute();

        if (!optionalImage.isPresent()) {
            view.closeWindow();
            return;
        }

        CustomImage customImage = optionalImage.get();

        if (validateFields()) {
            Double percentToContaminate = Double.parseDouble(view.percentField.getText());
            Double p0 = Double.parseDouble(view.p0Field.getText());
            Double p1 = Double.parseDouble(view.p1Field.getText());

            if (percentToContaminate >= 0 && percentToContaminate <= 100) {

                if (p0 < 1 && p1 < 1 && (p0 + p1 == 1)) {

                    if (p1 >= p0) {

                        applySaltAndPepperNoiseAction.execute(customImage, percentToContaminate, p0, p1);
                        view.closeWindow();

                    } else {
                        view.p0ValidationLabel.setText("Validation error: p1 must be > p0");
                    }

                } else if (p0 > 1){
                    view.p0ValidationLabel.setText("Validation error: p0 must be < 1");
                } else if (p1 > 1){
                    view.p0ValidationLabel.setText("Validation error: p1 must be < 1");
                } else if (p0 + p1 != 1){
                    view.p0ValidationLabel.setText("Validation error: p0 + p1 must equal 1");
                }

            } else {
                view.p0ValidationLabel.setText("Validation error: invalid percent");
            }
        }
    }

    private boolean validateFields() {
        return !view.p0Field.getText().equals(EMPTY) &&
                !view.p1Field.getText().equals(EMPTY) &&
                !view.percentField.getText().equals(EMPTY);
    }
}
