import model.ILocalModel;
import model.LocalModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ILocalModel model = new LocalModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(stage);
    }
}