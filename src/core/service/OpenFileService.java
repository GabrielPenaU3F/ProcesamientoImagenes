package core.service;

import domain.Format;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class OpenFileService {

    private FileChooser fileChooser;

    public OpenFileService(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    //TODO: Implementar la validacion del archivo y el boton Cancelar!
    public Optional<File> open() {
        return Optional.ofNullable(fileChooser.showOpenDialog(null))
                .filter(File::exists)
                .filter(File::isFile)
                .filter(File::canRead)
                .filter(File::canWrite)
                .filter(File::isAbsolute)
                .filter(this::hasValidExtension);
    }

    private boolean hasValidExtension(File file) {
        String absolutePath = file.getAbsolutePath();
        String[] splitSlash = absolutePath.split("/");
        String[] splitDot = splitSlash[splitSlash.length - 1].split("\\.");
        return Format.isValid(splitDot[splitDot.length - 1]);
    }
}
