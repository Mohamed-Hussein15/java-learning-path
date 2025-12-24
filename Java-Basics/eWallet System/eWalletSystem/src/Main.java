import service.ApplicationService;
import service.impl.EWalletServiceImpl;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ApplicationService app = new EWalletServiceImpl();
        app.startApp();
    }
}