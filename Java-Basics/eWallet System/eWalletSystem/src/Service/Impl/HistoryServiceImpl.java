package Service.Impl;

import Model.EWalletSystem;
import Model.History;
import Service.HistoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {

    private final EWalletSystem system = AccountServiceImpl.getSystem();

    @Override
    public void log(String username, String action, boolean status) {
        History history = new History(action, status);

        system.getHistories()
                .computeIfAbsent(username, k -> new ArrayList<>())
                .add(history);
    }

    @Override
    public List<History> getHistory(String username) {
        return system.getHistories()
                .getOrDefault(username, Collections.emptyList());
    }
}
