package viewmodel;
import model.Model;
public class ViewModelFactory {
    private StartViewModel startViewModel;

    public ViewModelFactory(Model model) {
        ViewModelState viewModelState = new ViewModelState();

        startViewModel = new StartViewModel(model, viewModelState);
    }

    public StartViewModel getStartViewModel() {
        return startViewModel;
    }
}
