package core.service;

import domain.customimage.Format;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class OpenFileService {

    private final FileChooser fileChooser;

    public OpenFileService(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public Optional<File> open() {
        return Optional.ofNullable(fileChooser.showOpenDialog(null))
                .filter(File::exists)
                .filter(File::isFile)
                .filter(File::canRead)
                .filter(File::canWrite)
                .filter(File::isAbsolute)
                .filter(this::hasValidExtension);
    }

    public Optional<List<File>> openMultiple() {
        return Optional.ofNullable(fileChooser.showOpenMultipleDialog(null));
    }

    private boolean hasValidExtension(File file) {
        String absolutePath = file.getAbsolutePath();
        String[] splitSlash = absolutePath.split("/");
        String[] splitDot = splitSlash[splitSlash.length - 1].split("\\.");
        return Format.isValid(splitDot[splitDot.length - 1].toLowerCase());
    }
}
