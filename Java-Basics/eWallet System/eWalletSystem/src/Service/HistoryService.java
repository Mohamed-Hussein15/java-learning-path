package service;

import model.Account;
import model.History;
import java.util.List;

public interface HistoryService {

        void log(String username, String action, boolean status);

        List<History> getHistory(String username);


}
