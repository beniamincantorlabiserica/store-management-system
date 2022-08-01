package view;

public enum View {
    START("fxml/StartView.fxml"),
    DASHBOARD("fxml/DashboardView.fxml"),
    INVENTORY("fxml/InventoryView.fxml"),
    SETTINGS("fxml/SettingsView.fxml"),
    CASH_REGISTER("fxml/CashRegisterView.fxml");

    private final String fxmlFile;

    View(String fxmlFile) {
        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
