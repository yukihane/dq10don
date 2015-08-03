package yukihane.dq10don.bosscard.view;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;

/**
 * Created by yuki on 15/08/03.
 */
public class BossCard {

    @Getter
    private final String name;

    @Getter
    private final String storageName;

    private final Date lastUpdateDate;
    private final int leftMinutes;

    public BossCard(String name, String storageName, Date lastUpdateDate, int leftMinutes) {
        this.name = name;
        this.storageName = storageName;
        this.lastUpdateDate = lastUpdateDate;
        this.leftMinutes = leftMinutes;
    }

    public int getCalculatedLeftMinites() {
        Calendar now = Calendar.getInstance();

        Calendar limitDate = getLimitDate();
        if (limitDate.getTimeInMillis() < now.getTimeInMillis()) {
            return 0;
        } else {
            return (int) ((limitDate.getTimeInMillis() - now.getTimeInMillis()) / 1000 / 60);
        }
    }


    public String getLimitDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd '('E')' HH:mm");
        return sdf.format(getLimitDate().getTime());
    }

    @NonNull
    private Calendar getLimitDate() {
        Calendar limitDate = Calendar.getInstance();
        limitDate.setTime(lastUpdateDate);
        limitDate.add(Calendar.MINUTE, leftMinutes);
        return limitDate;
    }
}
