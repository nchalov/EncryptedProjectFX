package Directories;
import java.io.File;

public final class Directories {

    private final File origin;
    private final File destiny;

    public Directories(String originPath, String destinyPath) {
        origin = new File(originPath);
        destiny = new File(destinyPath);
    }

    public File getOrigin() {
        return origin;
    }

    public File getDestiny() {
        return destiny;
    }

}
