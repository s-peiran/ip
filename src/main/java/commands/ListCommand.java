package commands;

import java.time.LocalDate;
import java.util.Collections;

import functional.DukeException;
import functional.TaskList;
import functional.Ui;
import tasks.Task;

/**
 * A command to list the tasks.
 */
public class ListCommand extends Command {
    private static final long daysInAWeek = 7;

    /**
     * Constructs a `ListCommand` object.
     */
    public ListCommand() {
        super();
    }

    /**
     * Executes the list command, which displays all the tasks in the task list.
     *
     * @param tasks  The task list to retrieve tasks from.
     * @param ui     The user interface to display the list of tasks.
     * @param status always false - intended for use by AddCommand
     */
    public String execute(TaskList<Task> tasks, Ui ui, boolean... status) throws DukeException {
        StringBuilder sb = new StringBuilder();
        sb.append(ui.showLine() + "\n"
                + "Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task job = tasks.get(i);
            boolean expiredRecurringTask = job.isRecurring() && job.getDateTime().isBefore(LocalDate.now());
            if (expiredRecurringTask) {
                LocalDate localDate = job.getDateTime();
                while (localDate.isBefore(LocalDate.now())) {
                    localDate = localDate.plusDays(daysInAWeek);
                }
                job = job.update(localDate);
                tasks.set(i, job);
            }
        }
        Collections.sort(tasks);
        for (int i = 0; i < tasks.size(); i++) {
            Task job = tasks.get(i);
            sb.append(String.format("%d. %s", i + 1, job.toString()) + "\n");
        }
        sb.append(ui.showLine());
        return sb.toString();
    }
}
