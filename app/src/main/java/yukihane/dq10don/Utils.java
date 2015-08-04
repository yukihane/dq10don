package yukihane.dq10don;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * Created by yuki on 15/07/08.
 */
public class Utils {
    public static final int RESULTCODE_OK = 0;

    public static final int TOBATSU_NOTIFICATION_ID = 1;
    public static final int BOSS_CARD_NOTIFICATION_ID = 2;


    private Utils() {
    }

    public static void copy(File srcFile, File dstDir) throws IOException {
        if (!srcFile.isFile()) {
            throw new IOException(srcFile + " is not a file.");
        }
        if (!dstDir.isDirectory()) {
            throw new IOException(dstDir + " is not a directory");
        }

        InputStream in = null;
        OutputStream out = null;

        try {
            String fileName = srcFile.getName();
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(new File(dstDir, fileName));

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public static String join(String separator, Collection<?> list) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object i : list) {
            String item = i.toString();
            if (first)
                first = false;
            else
                sb.append(separator);
            sb.append(item);
        }
        return sb.toString();
    }
}
