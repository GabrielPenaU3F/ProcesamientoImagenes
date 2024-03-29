package core.provider;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

public class PublishSubjectProvider {

    private static PublishSubject<Image> onModifiedImagePublishSubject;
    private static PublishSubject<Image> onNoiseImagePublishSubject;
    private static PublishSubject<Image> onEqualizeImagePublishSubject;
    private static PublishSubject<Image> cannyPublishSubject;

    public static PublishSubject<Image> provideOnModifiedImagePublishSubject() {
        if (onModifiedImagePublishSubject == null) {
            onModifiedImagePublishSubject = PublishSubject.create();
        }
        return onModifiedImagePublishSubject;
    }

    public static PublishSubject<Image> provideOnNoiseImagePublishSubject() {
        if (onNoiseImagePublishSubject == null) {
            onNoiseImagePublishSubject = PublishSubject.create();
        }
        return onNoiseImagePublishSubject;
    }

    public static PublishSubject<Image> provideOnEqualizeImagePublishSubject() {
        if (onEqualizeImagePublishSubject == null) {
            onEqualizeImagePublishSubject = PublishSubject.create();
        }
        return onEqualizeImagePublishSubject;
    }

    public static PublishSubject<Image> provideCannyPublishSubject() {
        if (cannyPublishSubject == null) {
            cannyPublishSubject = PublishSubject.create();
        }
        return cannyPublishSubject;
    }
}
