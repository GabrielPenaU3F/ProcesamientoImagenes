package presentation.controller;

import core.provider.PresenterProvider;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import presentation.presenter.DiffusionPresenter;

public class DiffusionSceneController {

    private final DiffusionPresenter diffusionPresenter;

    @FXML
    public RadioButton isotropic;
    @FXML
    public RadioButton anisotropic;
    @FXML
    public RadioButton lorentz;
    @FXML
    public RadioButton leclerc;
    @FXML
    public TextField iterationsTextField;
    @FXML
    public TextField sigmaTextField;

    public DiffusionSceneController() {
        this.diffusionPresenter = PresenterProvider.provideDiffusionPresenter(this);
    }

    public void initialize() {
        disableAnisotropic();
        if (anisotropic.isSelected()) {
            this.onAnisotropicSelection();
        }
    }

    @FXML
    public void onIsotropicSelection() {
        this.disableAnisotropic();
    }

    @FXML
    public void onAnisotropicSelection() {
        this.disableIsotropic();
    }

    private void disableIsotropic() {
        lorentz.setDisable(false);
        leclerc.setDisable(false);
        sigmaTextField.setDisable(false);
        sigmaTextField.setDisable(false);
    }

    @FXML
    public void onApply() {
        this.diffusionPresenter.onApplyDiffusion();
    }

    private void disableAnisotropic() {
        lorentz.setDisable(true);
        leclerc.setDisable(true);
        sigmaTextField.setDisable(true);
        sigmaTextField.setDisable(true);
        lorentz.selectedProperty().setValue(false);
        leclerc.selectedProperty().setValue(false);
    }

    public void closeWindow() {
        Stage stage = (Stage) this.sigmaTextField.getScene().getWindow();
        stage.close();
    }

    public boolean isIsotropicSelected() {
        return isotropic.isSelected();
    }

    public boolean isAnisotropicSelected() {
        return anisotropic.isSelected();
    }

    public boolean isLorentzSelected() {
        return lorentz.isSelected();
    }

    public boolean isLeclercSelected() {
        return leclerc.isSelected();
    }

    public double getSigma() {
        return Double.parseDouble(sigmaTextField.getText());
    }

    public int getIterations() {
        return Integer.parseInt(iterationsTextField.getText());
    }
}
