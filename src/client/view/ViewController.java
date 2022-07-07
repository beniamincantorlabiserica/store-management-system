package client.view;
import client.viewmodel.ViewModelFactory;
import javafx.scene.layout.Region;

public abstract class ViewController {
    private ViewHandler viewHandler;
    private ViewModelFactory viewModelFactory;
    private Region root;

    public void init(ViewHandler viewHandler, ViewModelFactory viewModelFactory, Region root) {
        this.viewHandler = viewHandler;
        this.viewModelFactory = viewModelFactory;
        this.root = root;

        init();
    }

    protected abstract void init();
    public abstract void reset();

    public ViewHandler getViewHandler() {

        return viewHandler;
    }

    public ViewModelFactory getViewModelFactory() {

        return viewModelFactory;
    }

    public Region getRoot() {

        return root;
    }
}
