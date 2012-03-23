package commands;

import dependency.Module;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 */
public class DependCommand implements Command {


    @Override
    public void execute(List<String> args) {
        String depName = args.get(0);

        Module current = Module.getInstance(depName);

        for (String strDependency : args.subList(1, args.size())) {
            Module dependency = Module.getInstance(strDependency);
            current.addDependency(dependency);
            dependency.addDependent(current);
        }

    }

}
