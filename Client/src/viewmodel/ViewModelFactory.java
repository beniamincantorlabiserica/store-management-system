package viewmodel;
import model.Model;
public class ViewModelFactory {
    private final StartViewModel startViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final SettingsViewModel settingsViewModel;

    public ViewModelFactory(Model model) {

        ViewModelState viewModelState = new ViewModelState();
        inventoryViewModel = new InventoryViewModel(model, viewModelState);
        startViewModel = new StartViewModel(model, viewModelState);
        dashboardViewModel = new DashboardViewModel(model, viewModelState);
        settingsViewModel = new SettingsViewModel(model, viewModelState);
    }

    public InventoryViewModel getInventoryViewModel() {
        return inventoryViewModel;
    }

    public StartViewModel getStartViewModel() {
        return startViewModel;
    }

    public DashboardViewModel getDashboardViewModel() {
        return dashboardViewModel;
    }

    public SettingsViewModel getSettingsViewModel() {
        return settingsViewModel;
    }
}
