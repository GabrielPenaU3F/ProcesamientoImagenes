package domain.Loaders;

import domain.Image;

import java.io.FileInputStream;

public interface ImgLoader {

    public Image loadImg(String path);
    Integer getImageWidth(String path);
    Integer getImageHeight(String path);
}
