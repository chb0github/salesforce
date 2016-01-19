package org.bongiorno.interviews.salesforce.commands;

import org.bongiorno.interviews.salesforce.dependency.Module;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


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
        if(!parent.hasDependents()) {
            result.put(parent.getName(),"successfully removed");
            parent.setInstalled(false);

            for (Module dependency : parent.getDependencies()) {
                result.putAll(uninstall(dependency));
            }
        }
        else {

            result.put(parent.getName(),"is still needed.");
        }
        return result;
    }
}
