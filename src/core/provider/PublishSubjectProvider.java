package core.provider;

import io.reactivex.subjects.PublishSubject;
import javafx.scene.image.Image;

public class PublishSubjectProvider {

    private static PublishSubject<Image> onModifiedImagePublishSubject;

    public static PublishSubject<Image> provideOnModifiedImagePublishSubject() {
        if (onModifiedImagePublishSubject == null) {
            onModifiedImagePublishSubject = PublishSubject.create();
        }
        return onModifiedImagePublishSubject;
    }
}
