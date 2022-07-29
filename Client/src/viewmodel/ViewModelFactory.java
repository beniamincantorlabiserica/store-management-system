package viewmodel;
import model.Model;
public class ViewModelFactory {
    private final StartViewModel startViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final InventoryViewModel inventoryViewModel;
    private final SettingsViewModel settingsViewModel;
    private final CashRegisterViewModel cashRegisterViewModel;

    public ViewModelFactory(Model model) {

        ViewModelState viewModelState = new ViewModelState();
        inventoryViewModel = new InventoryViewModel(model, viewModelState);
        startViewModel = new StartViewModel(model, viewModelState);
        dashboardViewModel = new DashboardViewModel(model, viewModelState);
        settingsViewModel = new SettingsViewModel(model, viewModelState);
        cashRegisterViewModel = new CashRegisterViewModel(model);
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

    public CashRegisterViewModel getCashRegisterViewModel() {return cashRegisterViewModel;}
}
