package swiftsolutions.taskscheduler;

import java.io.Serializable;
import java.util.*;

/**
 * This class represent a single task which needs to be scheduled and is parsed from nodes of an input graph.
 */
public class Task implements Serializable{
    private int _taskID;
    private int _processTime;
    private Map<Integer, Integer> _communicationCosts;
    private Set<Integer> _parentTasks;
    private Set<Integer> _childTasks; // Refers to map communication cost with its children
    private int _bottomLevel;

    /**
     * Constructor which takes an unique ID identifying this task and the processing time of the task as input.
     * @param processTime
     */
    public Task(int taskID, int processTime){
        _taskID = taskID;
        _processTime = processTime;
        _communicationCosts = new HashMap<>();
        _parentTasks = new HashSet<>();
        _childTasks = new HashSet<>();
    }

    /**
     * Method to add a child task to the task
     * @param task The child task to be added
     */
    public void addChild(Integer task){
        _childTasks.add(task);
    }

    /**
     * Method for initialising dependency with a communication cost in the child instance.
     */
    public void addParent(Integer task, int communicationCost) {
        _communicationCosts.put(task, communicationCost);
        _parentTasks.add(task);
    }

    /**
     * Getter for obtaining the communication cost with its parent.
     * @param parentID
     * @return The cost of communication, defaults to 0.
     */
    public int getCommunicationCosts(Integer parentID) {
        if (_communicationCosts.keySet().contains(parentID)) {
            return _communicationCosts.get(parentID);
        }
        return 0;
    }

    /**
     * @return the time that it takes to process task.
     */
    public int getProcessTime(){
        return this._processTime;
    }

    /**
     * @return the unique task ID.
     */
    public int getTaskID() {
        return this._taskID;
    }

    /**
     * @return the number of dependencies.
     */
    public int getNumDependency(){
        return _parentTasks.size();
    }

    /**
     * @return the set of parent tasks ids.
     */
    public Set<Integer> getParentTasks() {
        return _parentTasks;
    }

    /**
     * @return the set of child tasks ids.
     */
    public Set<Integer> getChildTasks() { return _childTasks; }

    /**
     * @return the set of communication tasks keys by "child task id" and value is corresponding to the communication
     * cost to that child.
     */
    public Map<Integer, Integer> getCommunicationCosts() { return _communicationCosts; }

    /**
     * @param bottomLevel the bottom level of said node.
     */
    public void updateBottomLevel(int bottomLevel) {
        if (bottomLevel > _bottomLevel) {
            _bottomLevel = bottomLevel;
        }
    }

    /**
     * @return the bottom level of node
     */
    public int getBottomLevel() {
        return _bottomLevel;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return ((Task)obj)._taskID == _taskID;
    }

    @Override
    public int hashCode() {
        return _taskID;
    }
}
