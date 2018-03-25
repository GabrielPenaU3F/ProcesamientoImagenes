package core.provider;

import ij.io.Opener;

class CommonProvider {

    public static Opener provideOpener() {
        return new Opener();
    }
}
