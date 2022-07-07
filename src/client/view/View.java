package client.view;

public enum View {
    START("fxml/StartView.fxml");

    private String fxmlFile;

    private View(String fxmlFile) {

        this.fxmlFile = fxmlFile;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
