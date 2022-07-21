package viewmodel;

import model.Model;

public class SettingsViewModel implements SettingsViewModelInterface {
    private final Model model;

    public SettingsViewModel(Model model, ViewModelState viewModelState) {
        this.model = model;
    }

    @Override
    public void setOpeningHours(String s) throws RuntimeException {
        model.setOpeningHours(s);
    }

    @Override
    public void setClosingHours(String s) throws RuntimeException {
        model.setClosingHours(s);
    }

    @Override
    public String getOpeningHours() {
        return model.getOpeningHours();
    }

    @Override
    public String getClosingHours() {
        return model.getClosingHours();
    }

    @Override
    public boolean masterCheck(String s) {
        return model.masterCheck(s);
    }

    @Override
    public void updatePassword(String role, String password) {
        model.updatePassword(role, password);
    }

    @Override
    public void setLockedState(boolean b) {
        model.setLockedState(b);
        if(b) {
            model.logout();
        }
    }
}
