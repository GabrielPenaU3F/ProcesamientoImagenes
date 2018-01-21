package domain.loaders;

import domain.Image;

public interface ImgLoader {

    public Image loadImg(String path);
    Integer getImageWidth(String path);
    Integer getImageHeight(String path);
}
