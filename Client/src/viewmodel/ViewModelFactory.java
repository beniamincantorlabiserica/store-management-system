package viewmodel;
import model.Model;
public class ViewModelFactory {
    private final StartViewModel startViewModel;
    private final DashboardViewModel dashboardViewModel;

    public ViewModelFactory(Model model) {
        ViewModelState viewModelState = new ViewModelState();
        startViewModel = new StartViewModel(model, viewModelState);
        dashboardViewModel = new DashboardViewModel(model, viewModelState);
    }

    public StartViewModel getStartViewModel() {
        return startViewModel;
    }

    public DashboardViewModel getDashboardViewModel() {
        return dashboardViewModel;
    }
}
