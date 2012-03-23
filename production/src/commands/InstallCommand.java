package commands;

import dependency.Module;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: 3/22/12
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstallCommand implements Command {

    @Override
    public void execute(List<String> args) {

        String depName = args.get(0);
        Module dep = Module.getInstance(depName);
        install(dep);
        System.out.println();


    }

    private void install(Module current) {
        current.setInstalled(true);
        for (Module dependency : current.getDependencies()) {
            if (!dependency.isInstalled()) { // not entirely necessary
                install(dependency);
            }
        }

    }
}
