package view;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

public class ViewHandler extends ViewCreator {
    private Stage stage;
    private Scene scene;
    private final ViewModelFactory viewModelFactory;

    public ViewHandler(ViewModelFactory viewModelFactory) {
        super();
        this.viewModelFactory = viewModelFactory;
    }

    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(e -> System.exit(0));
        scene = new Scene(new Region());
        openView(View.START);
    }

    public void openView(View view) {
        ViewController controller = getViewController(view);
        Region root = controller.getRoot();
        scene.setRoot(root);

        String title = "";
        if (root.getUserData() != null) {
            title += root.getUserData();
        }

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setWidth(root.getPrefWidth());
        stage.setHeight(root.getPrefHeight());
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    @Override
    protected void initViewController(ViewController controller, Region root) {
        controller.init(this, viewModelFactory, root);
    }
}
