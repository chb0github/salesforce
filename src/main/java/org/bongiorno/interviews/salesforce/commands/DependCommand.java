package org.bongiorno.interviews.salesforce.commands;


import org.bongiorno.interviews.salesforce.dependency.Module;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 */
public class DependCommand implements Command {


    @Override
    public Map<String, Object> execute(List<String> args) {
        String depName = args.get(0);

        Module current = Module.getInstance(depName);

        for (String strDependency : args.subList(1, args.size())) {
            Module dependency = Module.getInstance(strDependency);
            current.addDependency(dependency);
            dependency.addDependent(current);
        }
        return Collections.emptyMap();
    }

}
