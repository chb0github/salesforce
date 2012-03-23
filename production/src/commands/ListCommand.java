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
public class ListCommand implements Command {


    @Override
    public void execute(List<String> args) {
        System.out.println(Module.getInstalled());

    }

}
