package swiftsolutions.input;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import swiftsolutions.exceptions.InputException;
import swiftsolutions.interfaces.input.InputParser;
import swiftsolutions.taskscheduler.Task;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Parses the input dot file into  a mapping from task id to task information to be used by the scheduler.
 */
public class DOTInputParser implements InputParser {

    private GraphParser _parser;
    private Map<Integer, Task> _allTasks;
    private Map<Integer, Integer> _offsetID;
    private Map<String, GraphNode> _inputNodes;
    private Map<String, GraphEdge> _inputEdges;

    /**
     * This is a method which check if the file exist in the location supplied in the constructor or setter.
     * @return A set of object representing each of the task needed to be scheduled.
     * @throws InputException if the input DOT file was malformed.
     */
    @Override
    public Map<Integer, Task> parse(String filename) throws InputException {
        _allTasks = new LinkedHashMap<>();
        _offsetID = new HashMap<>();
        try {
            // Using the digraph _parser tool to parse the file.
            _parser = new GraphParser(new FileInputStream(filename));
            _inputNodes = _parser.getNodes();
            _inputEdges = _parser.getEdges();
        } catch (FileNotFoundException e) {
            throw new InputException("Input graph file not found");
        }

        parseNodes();
        parseEdges();

        return _allTasks;
    }

    /**
     * This is a method which parses the GraphNode objects from the input .dot file.
     * @throws InputException if the node information was malformed.
     */
    private void parseNodes() throws InputException {
        int nodeID = 0;
        // Looping through each of the nodes
        for (String nodeName : _inputNodes.keySet()) {
            Task task;
            try {
                int id = Integer.parseInt(nodeName);
                int weight = Integer.parseInt(_inputNodes.get(nodeName).getAttribute("Weight").toString());
                // Creating a new Task object and appending to the map with id as the key.
                task = new Task(id, weight);
                _allTasks.put(nodeID, task);
                _offsetID.put(id, nodeID);
                nodeID++;
            } catch (NumberFormatException e) {
                throw new InputException("Input graph could not be parsed correctly");
            }
        }
    }

    /**
     * This is a method which parses the GraphEdge objects from the input .dot file.
     * @throws InputException if the edge information was malformed.
     */
    private void parseEdges() throws InputException {
        // Looping through each of the nodes
        for (GraphEdge edge : _inputEdges.values()) {
            try {
                int sourceNode = _offsetID.get(Integer.parseInt(edge.getNode1().getId()));
                int dstNode = _offsetID.get(Integer.parseInt(edge.getNode2().getId()));
                if ((_allTasks.keySet().contains(sourceNode))
                        && (_allTasks.keySet().contains(dstNode))) {
                    Task parent = _allTasks.get(sourceNode);
                    Task child = _allTasks.get(dstNode);
                    // Inserting the parent to child dependency.
                    int weight = Integer.parseInt(edge.getAttribute("Weight").toString());
                    parent.addChild(dstNode);
                    child.addParent(sourceNode, weight);
                } else {
                    throw new InputException("Input graph could not be parsed correctly");
                }
            } catch (NumberFormatException e) {
                throw new InputException("Input graph could not be parsed correctly");
            }
        }
    }
}
