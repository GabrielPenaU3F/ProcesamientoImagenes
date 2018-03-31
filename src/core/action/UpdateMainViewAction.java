package core.action;

import javafx.scene.image.Image;
import presentation.presenter.MainPresenter;

public class UpdateMainViewAction {

    private final MainPresenter mainPresenter;

    public UpdateMainViewAction(MainPresenter presenter) {
        this.mainPresenter = presenter;
    }
    
    public void execute(Image image) {
        this.mainPresenter.getView().modifiedImageView.setImage(image);
        System.out.println("Hola puto");
    }
    
}
