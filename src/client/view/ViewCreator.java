package client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class ViewCreator {
    private Map<View, ViewController> controllers;

    public ViewCreator() {
        controllers = new HashMap<>();
    }

    public ViewController getViewController(View view) {
        ViewController controller = controllers.get(view);
        if (controller == null) {
            controller = loadFromFXML(view.getFxmlFile());
            controllers.put(view, controller);
        } else {
            controllers.get(view).reset();
        }

        return controller;
    }

    private ViewController loadFromFXML(String fxmlFile) {
        ViewController controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = getClass().getResource(fxmlFile);
            loader.setLocation(url);
            Region root = loader.load();
            controller = loader.getController();
            initViewController(controller, root);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return controller;
    }

    protected abstract void initViewController(ViewController controller, Region root);
}
