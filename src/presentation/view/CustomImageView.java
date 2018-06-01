package presentation.view;

import domain.activecontour.SelectionSquare;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class CustomImageView {

    private final ImageView imageView;
    private final Group group;
    private Double mouseAnchorX;
    private Double mouseAnchorY;
    private Rectangle rect;
    private BiFunction<Integer, Integer, Action> onPixelClick;

    public CustomImageView(Group group, ImageView imageView) {
        this.imageView = imageView;
        this.group = group;
    }

    public CustomImageView withSelectionMode() {

        rect = new Rectangle(0, 0, 0, 0);
        rect.setStroke(Color.LIGHTGREEN);
        rect.setStrokeWidth(1);
        rect.setStrokeLineCap(StrokeLineCap.ROUND);
        rect.setFill(Color.color(0.5, 0.9, 0.5, 0.6));

        this.group.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        this.group.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        this.group.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

        return this;
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            if (event.isSecondaryButtonDown()) {
                return;
            }

            group.getChildren().remove(rect);

            mouseAnchorX = event.getX();
            mouseAnchorY = event.getY();

            rect.setX(mouseAnchorX);
            rect.setY(mouseAnchorY);
            rect.setWidth(0);
            rect.setHeight(0);

            group.getChildren().add(rect);

            try {
                onPixelClick.apply(mouseAnchorX.intValue(), mouseAnchorY.intValue()).run();
            } catch (Exception ignored) {
            }
        }
    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            if (event.isSecondaryButtonDown()) {
                return;
            }

            double offsetX = event.getX() - mouseAnchorX;
            double offsetY = event.getY() - mouseAnchorY;

            if (offsetX > 0) {
                rect.setWidth(offsetX);
            } else {
                rect.setX(event.getX());
                rect.setWidth(mouseAnchorX - rect.getX());
            }

            if (offsetY > 0) {
                rect.setHeight(offsetY);
            } else {
                rect.setY(event.getY());
                rect.setHeight(mouseAnchorY - rect.getY());
            }
        }
    };

    private final EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            if (event.isSecondaryButtonDown()) {
                return;
            }
        }
    };

    public CustomImageView withSetPickOnBounds(boolean b) {
        this.imageView.setPickOnBounds(b);
        return this;
    }

    public CustomImageView withOnPixelClick(BiFunction<Integer, Integer, Action> onPixelClick) {
        this.onPixelClick = onPixelClick;
        return this;
    }

    public Image cutPartialImage() {
        Bounds bounds = rect.getBoundsInParent();
        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

        return imageView.snapshot(parameters, new WritableImage(width, height));
    }

    public void setImage(Image image) {
        this.imageView.setImage(image);
    }

    public SelectionSquare getSelectionSquare() {
        Bounds bounds = rect.getBoundsInParent();

        return new SelectionSquare((int) bounds.getMinX(), (int) bounds.getMaxX(), (int) bounds.getMinY(), (int) bounds.getMaxY());
    }
}
