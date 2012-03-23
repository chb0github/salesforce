package commands;

import dependency.Module;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 */
public class RemoveCommand implements Command {

    @Override
    public void execute(List<String> args) {
        Module d = Module.getInstance(args.get(0));
        uninstall(d);
    }

    private void uninstall(Module parent) {
        parent.setInstalled(false);
        if(!parent.hasDependents()) {
            for (Module dependency : parent.getDependencies()) {
                uninstall(dependency);
            }
        }

    }
}
