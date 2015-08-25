package yukihane.dq10don;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yuki on 15/07/08.
 */
public class Utils {
    public static final int RESULTCODE_OK = 0;

    public static final int TOBATSU_NOTIFICATION_ID = 1;
    public static final int BOSS_CARD_NOTIFICATION_ID = 2;

    private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private static final TimeZone TIMEZONE_JAPAN = TimeZone.getTimeZone("Asia/Tokyo");

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

    public static TimeZone getJapenTimeZone() {
        return TIMEZONE_JAPAN;
    }

    /**
     * 2015-08-18 21:27:06 型の日付時刻文字列をDate型に変換します.
     */
    public static Date parseDate(String dateText) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(getJapenTimeZone());

        try {
            return sdf.parse(dateText);
        } catch (ParseException e) {
            // 発生しないはず
            LOGGER.error("date/time parse error: " + dateText, e);
            return null;
        }
    }
}
