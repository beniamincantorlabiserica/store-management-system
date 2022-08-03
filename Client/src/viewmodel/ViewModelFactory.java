package viewmodel;
import model.Model;
public class ViewModelFactory {
    private final StartViewModelInterface startViewModel;
    private final DashBoardViewModelInterface dashboardViewModel;
    private final InventoryViewModelInterface inventoryViewModel;
    private final SettingsViewModelInterface settingsViewModel;
    private final CashRegisterViewModelInterface cashRegisterViewModel;

    public ViewModelFactory(Model model) {

        ViewModelState viewModelState = new ViewModelState();
        inventoryViewModel = new InventoryViewModel(model, viewModelState);
        startViewModel = new StartViewModel(model, viewModelState);
        dashboardViewModel = new DashboardViewModel(model, viewModelState);
        settingsViewModel = new SettingsViewModel(model, viewModelState);
        cashRegisterViewModel = new CashRegisterViewModel(model);
    }

    public InventoryViewModelInterface getInventoryViewModel() {
        return inventoryViewModel;
    }

    public StartViewModelInterface getStartViewModel() {
        return startViewModel;
    }

    public DashBoardViewModelInterface getDashboardViewModel() {
        return dashboardViewModel;
    }

    public SettingsViewModelInterface getSettingsViewModel() {
        return settingsViewModel;
    }

    public CashRegisterViewModelInterface getCashRegisterViewModel() {return cashRegisterViewModel;}
}
