package configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Witold on 2016-01-20.
 */
public class TemporaryFileBuilder {
    public static String build(String parentFile, String tempName) {
        StringBuilder builder = new StringBuilder();
        Path tempFile = Paths.get(parentFile);
        builder.append(tempFile.getParent());
        builder.append("\\");
        builder.append(tempName);
        return builder.toString();
    }
}
