package org.bongiorno.interviews.salesforce.commands;

import org.bongiorno.interviews.salesforce.dependency.Module;

import java.util.*;

import static java.util.stream.Collectors.toSet;


/**
 * Created by IntelliJ IDEA.
 * User: christian
 */
public class RemoveCommand implements Command {

    @Override
    public Map<String, Object> execute(List<String> args) {
        Module d = Module.getInstance(args.get(0));
        if(d != null)
            return uninstall(d);
        Map<String,Object> result = new LinkedHashMap<>();
        result.put(args.get(0),"is not installed");
        return result;
    }

    private Map<String, Object> uninstall(Module parent) {
        Map<String, Object> result = new HashMap<>();
        Set<Module> installedDependents = parent.getDependents().stream().filter(Module::isInstalled).collect(toSet());
        if(installedDependents.isEmpty()) {
            result.put(parent.getName(),"successfully removed");
            parent.setInstalled(false);

            for (Module dependency : parent.getDependencies()) {
                if(dependency.isInstalled()) {
                    result.putAll(uninstall(dependency));
                }
            }
        }
        else {

            result.put(parent.getName(),"is still needed.");
        }
        return result;
    }
}
