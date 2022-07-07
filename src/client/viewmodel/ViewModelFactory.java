package client.viewmodel;
import client.model.ILocalModel;
public class ViewModelFactory {
    private StartViewModel startViewModel;

    public ViewModelFactory(ILocalModel model) {
        ViewModelState viewModelState = new ViewModelState();

        startViewModel = new StartViewModel(model, viewModelState);
    }

    public StartViewModel getStartViewModel() {
        return startViewModel;
    }
}
