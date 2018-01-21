package core.provider;

import ij.io.Opener;

public class CommonProvider {

    public static Opener provideOpener() {
        return new Opener();
    }
}
