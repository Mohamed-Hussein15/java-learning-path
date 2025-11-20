public class InvalidAgeException extends Throwable{
    private String failMessage;
    private String failCode;

    public InvalidAgeException(String failMessage, String failCode) {
        this.failMessage = failMessage;
        this.failCode = failCode;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }
}
