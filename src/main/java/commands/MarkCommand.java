package commands;

import functional.DukeException;
import functional.TaskList;
import functional.Ui;
import tasks.Task;

/**
 * A command to mark a task as done.
 */
public class MarkCommand extends Command {

    /**
     * Constructs a `MarkCommand` object
     */
    public MarkCommand() {
        super();
    }

    /**
     * Marks the specified task as done.
     *
     * @param tasks  The task list containing the tasks.
     * @param ui     The user interface to display the result.
     * @param status always false - intended for use by AddCommand
     */

    public String execute(TaskList<Task> tasks, Ui ui, boolean... status) throws DukeException {
        int index;
        try {
            index = Integer.parseInt(ui.get(1)) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("NumberFormatException");
        }
        Task job;
        try {
            job = tasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("IndexOutOfBoundsException");
        }
        job = job.mark();
        tasks.set(index, job);
        return ui.showLine() + "\n"
                + "OK, I've marked this task as done:\n" + job.toString() + "\n"
                + ui.showLine();
    }
}
