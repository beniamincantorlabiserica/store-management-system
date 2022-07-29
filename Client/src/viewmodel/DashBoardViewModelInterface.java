package viewmodel;

public interface DashBoardViewModelInterface {
    /**
     * logs out the user
     */
    void logout();

    /**
     * performs viewModel reset()
     */
    void reset();

    /**
     * starts a thread that updates the time, date and day of week labels
     * with the correct values each second
     */
    void startUpdateThread();
}
