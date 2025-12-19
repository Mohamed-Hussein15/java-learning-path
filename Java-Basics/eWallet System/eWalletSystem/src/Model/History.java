package Model;

import java.time.LocalDateTime;

public class History {

    private LocalDateTime actionTime;
    private String action;
    private Boolean status;

    public History(String action, Boolean status) {
        this.action = action;
        this.status = status;
        this.actionTime = LocalDateTime.now();
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public String getAction() {
        return action;
    }

    public Boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return actionTime + " | " + action + " | " + (status ? "SUCCESS" : "FAILED");
    }
}
