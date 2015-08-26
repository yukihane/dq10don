package yukihane.dq10don.farm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;
import rx.Observable;
import yukihane.dq10don.Utils;
import yukihane.dq10don.communication_game.dto.farm.openalltresurebox.Data;
import yukihane.dq10don.communication_game.dto.farm.openalltresurebox.ItemList;

/**
 * Created by yuki on 15/08/26.
 */
public class OpenBoxResult {

    public static final OpenBoxResult EMPTY = new OpenBoxResult(0, 0, new ArrayList<>(0), new ArrayList<>(0));

    @Getter
    private final int successCount;

    @Getter
    private final int failCount;

    private final List<String> successMessages;

    private final List<Long> successTickets;

    OpenBoxResult(int successCount, int failCount, List<String> successMessages, List<Long> successTickets) {
        this.successCount = successCount;
        this.failCount = failCount;
        this.successMessages = new ArrayList<>(successMessages);
        this.successTickets = new ArrayList<>(successTickets);
    }

    public static OpenBoxResult from(Data data) {
        int successCount = data.getSuccessList().size();
        int failCount = data.getFailList().size();


        List<String> messages = new ArrayList<>();
        List<Long> tickets = new ArrayList<>();
        Observable.from(data.getSuccessList())
                .forEach(succ -> {
                    // 赤箱にはメッセージがない
                    // プレセント箱にはある
                    final String msg;
                    String containedMsg = succ.getMessageText();
                    if (containedMsg == null || containedMsg.isEmpty() || containedMsg.equals("null")) {
                        Map<String, Integer> gainedItems = new TreeMap<>();
                        for (ItemList i : succ.getItemList()) {
                            Integer count = gainedItems.get(i.getName());
                            if (count == null) {
                                count = i.getCount();
                            } else {
                                count += i.getCount();
                            }
                            gainedItems.put(i.getName(), count);
                        }

                        List<String> items = new ArrayList<>();
                        for (String name : gainedItems.keySet()) {
                            items.add(name + " " + gainedItems.get(name));
                        }
                        String itemText = Utils.join(", ", items);
                        msg = "[" + itemText + "]";
                    } else {
                        msg = succ.getMessageText();
                    }
                    messages.add(msg);
                    tickets.add(succ.getTreasureboxTicket());
                });

        return new OpenBoxResult(successCount, failCount, messages, tickets);
    }

    public List<String> getSuccessMessages() {
        return new ArrayList<>(successMessages);
    }

    public List<Long> getSuccessTickets() {
        return new ArrayList<>(successTickets);
    }
}
