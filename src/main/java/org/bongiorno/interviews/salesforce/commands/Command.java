package org.bongiorno.interviews.salesforce.commands;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: christian
 */
public interface Command {
    
    Map<String, Object> execute(List<String> args);
}
