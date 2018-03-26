package presentation.presenter;

import core.action.image.LoadImageAction;

public class ImagesSumPresenter {

    private final LoadImageAction loadImageAction;

    public ImagesSumPresenter(LoadImageAction loadImageAction){
        this.loadImageAction = loadImageAction;
    }

    public void onloadImage1(){

        System.out.println("a");//para prueba
    }

    public void onloadImage2(){
        System.out.println("b");//para prueba
    }

    public void onMakeImagesSum(){
        System.out.println("c");//para prueba
    }
}
