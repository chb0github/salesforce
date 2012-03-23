package commands;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 * Date: 3/22/12
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Command {
    
    public void execute(List<String> args);
}
