package viewmodel;

import model.Model;
import util.logger.Logger;
import util.logger.LoggerType;

import java.time.format.DateTimeFormatter;

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
        return model.getOpeningHours().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public String getClosingHours() {
        return model.getClosingHours().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public boolean masterCheck(String s) {
        return model.masterCheck(s);
    }

    @Override
    public void updatePassword(String role, String password) {
        model.updatePassword(role, password);
    }

    /**
     * validates the password sent as a string parameter
     *
     * @param s expects the password to be validated
     */
    @Override
    public void validateNewPassword(String s) {
        if (s.length() < 4) {
            Logger.getInstance().log(LoggerType.ERROR, "The new password should contain more than 3 characters");
            throw new RuntimeException("PASSWORD_INVALID");
        }
    }

    @Override
    public void setLockedState(boolean b) {
        model.setLockedState(b);
        if (b) {
            model.logout();
        }
    }
}
