package yukihane.dq10don.account;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

@DatabaseTable
public class BgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BgService.class);

    /**
     * 最長で基準時刻(6:01)の5分後まで処理を遅らせます.
     * サーバーの負荷対策です.
     */
    private static final int MAX_DELAY = (5 * 60 * 1000);

    @DatabaseField(generatedId = true)
    private Long id;

    /**
     * 最後に{@link #reset()} を呼んだ時刻が設定されます.
     * これは乱数生成のseedとして用いられます.
     */
    @DatabaseField
    @Setter
    @Getter
    private long seed;

    public BgService() {
    }

    public void reset() {
        seed = Calendar.getInstance().getTimeInMillis();
    }

    public long getNextAlarmTime() {
        int delay = getDelay();

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN);
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        if (h >= 6 || (h == 5 && m >= 55)) {
            // 5:55 以降なら今日は実行しない(翌日に実行する).
            LOGGER.info("Alarm set next date");
            cal.add(Calendar.DATE, 1);
        }

        // 基準時刻 6:01 にセット
        cal.set(Calendar.HOUR_OF_DAY, 6);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTimeInMillis() + delay;
    }

    /**
     * resetせずに使い続けていると処理時間がかかるようになるので,
     * リセットしたほうが良い頃合いを提示します.
     *
     * @return リセットしたほうが良ければtrue.
     */
    public boolean needsReset() {
        return getPassing() > 365;
    }

    private int getDelay() {

        long passing = getPassing();

        Random rand = new Random(seed);
        int res = rand.nextInt(MAX_DELAY);
        for (long i = 0; i < passing; i++) {
            res = rand.nextInt(MAX_DELAY);
        }
        LOGGER.debug("delay is {}", res);
        return res;
    }

    /**
     * 最後にリセットしてからおおよそ何日経過したかを求めます.
     */
    private long getPassing() {
        if (seed == 0L) {
            throw new IllegalStateException("must be reset once");
        }

        long now = Calendar.getInstance().getTimeInMillis();
        long res = (now - seed) / (24 * 60 * 60 * 1000) + 1;
        LOGGER.debug("passing is {}, now:{}, last reset:{}", res, now, seed);
        return res;
    }


}
