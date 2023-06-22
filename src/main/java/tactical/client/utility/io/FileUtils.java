package tactical.client.utility.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Gavin
 * @since 1.0.0
 */
public class FileUtils {

    /**
     * Reads from a file
     * @param file the {@link File}
     * @return the contents of the file
     * @throws IOException if the file could not be read from
     */
    public static String read(File file) throws IOException {
        InputStream is = Files.newInputStream(file.toPath());

        StringBuilder builder = new StringBuilder();
        int b;
        while ((b = is.read()) != -1) {
            builder.append((char) b);
        }

        is.close();
        return builder.toString();
    }

    /**
     * Writes to a file
     * @param file the {@link File}
     * @param text the content to write to the file
     * @throws IOException if the file could be written to
     */
    public static void write(File file, String text) throws IOException {
        OutputStream os = Files.newOutputStream(file.toPath());
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        os.write(bytes, 0, bytes.length);
        os.close();
    }
}
