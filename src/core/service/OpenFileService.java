package core.service;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.Optional;

public class OpenFileService {

    private FileChooser fileChooser;

    public OpenFileService(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    //TODO: Implementar la validacion del archivo y el boton Cancelar!
    public File open() {
        Optional<File> file = Optional.ofNullable(fileChooser.showOpenDialog(null));
        if (!file.isPresent()) {
            open();
        }

        return file.get();
    }
}
