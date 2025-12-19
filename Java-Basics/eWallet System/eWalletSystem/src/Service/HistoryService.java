package Service;

import Model.Account;
import Model.History;
import java.util.List;

public interface HistoryService {

        void log(String username, String action, boolean status);

        List<History> getHistory(String username);


}
