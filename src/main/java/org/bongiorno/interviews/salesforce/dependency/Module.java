package org.bongiorno.interviews.salesforce.dependency;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 *
 * @author christian bongiorno
 */
public class Module {
    protected static Map<String, Module> dependencyMap = new HashMap<String, Module>();

    private String name;
    private Set<Module> dependencies = new HashSet<Module>();
    private Set<Module> dependents = new HashSet<Module>();

    private boolean installed;

    private Module(String name) {
        this.name = name;
    }



    public static Module getInstance(String name) {
        Module target = dependencyMap.get(name);
        if (target == null) {
            target = new Module(name);
            dependencyMap.put(name, target);
        }
        return target;
    }

    public String getName() {
        return name;
    }

    public boolean isInstalled() {
        return installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
    }

    public Set<Module> getDependents() {
        return dependents;
    }

    public boolean hasDependents() {
        return !dependents.isEmpty();
    }

    public boolean hasDependencies() {
        return !dependencies.isEmpty();
    }

    public Set<Module> getDependencies() {
        return dependencies;
    }

    public boolean addDependency(Module d) {
        return dependencies.add(d);
    }

    public boolean addDependent(Module d) {
        return dependents.add(d);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Module that = (Module) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }


    public static Collection<Module> getAll() {
        return dependencyMap.values();
    }

    public static Set<Module> getInstalled() {
        Set<Module> installed = new HashSet<Module>();
        for (Module module : dependencyMap.values()) {
            if (module.isInstalled())
                installed.add(module);
        }
        return installed;
    }
}
