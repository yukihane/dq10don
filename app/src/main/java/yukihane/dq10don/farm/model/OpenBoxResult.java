package yukihane.dq10don.farm.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import rx.Observable;
import yukihane.dq10don.communication_game.dto.farm.openalltresurebox.Data;

/**
 * Created by yuki on 15/08/26.
 */
public class OpenBoxResult {

    public static final OpenBoxResult EMPTY = new OpenBoxResult(0, 0, new ArrayList<>(0));

    @Getter
    private final int successCount;

    @Getter
    private final int failCount;

    private final List<String> successMessages;

    OpenBoxResult(int successCount, int failCount, List<String> successMessages) {
        this.successCount = successCount;
        this.failCount = failCount;
        this.successMessages = new ArrayList<>(successMessages);
    }

    public static OpenBoxResult from(Data data) {
        int successCount = data.getSuccessList().size();
        int failCount = data.getFailList().size();


        List<String> messages = new ArrayList<>();
        Observable.from(data.getSuccessList())
                .map(succ -> succ.getMessageText())
                .subscribe(text -> messages.add(text));

        return new OpenBoxResult(successCount, failCount, messages);
    }

    public List<String> getSuccessMessages() {
        return new ArrayList<>(successMessages);
    }
}
