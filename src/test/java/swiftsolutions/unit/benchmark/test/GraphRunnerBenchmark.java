package swiftsolutions.unit.benchmark.test;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import swiftsolutions.unit.benchmark.BenchmarkAppRunner;
import swiftsolutions.unit.benchmark.BenchmarkParser;

/**
 *Test the ability of the benchmarkParser to generate and catagorise files
 */
public class GraphRunnerBenchmark {
    

	/*
	 * Used to run all all lecturer provided graphs
	 */
	@Test
    public void runShortName() {
		
		BenchmarkAppRunner _runner = new BenchmarkAppRunner(1);
		BenchmarkParser _benchmarkParser = new BenchmarkParser("src/test/resources/test_graphs/");
		
		
		ArrayList<File> allGraphs = _benchmarkParser.getAllGraphs();
		_runner.addList(allGraphs);
		_runner.runAll();
		assertEquals(allGraphs.size() , _runner.getOutputs().size());
		assertEquals(0, _runner.getTimedOutGraphs().size());
		
		//test each graph gave correct outputs
		System.out.println(_runner.getOutputs().keySet());
		
		
    }
	
	/*
	 * Used to run all full name graphs
	 */
	@Test
    public void runFullNameGraphs() {
		
		BenchmarkAppRunner _runner = new BenchmarkAppRunner(2);
		//"C:\\Users\\Harith\\Desktop\\UoA Resources\\2018\\2018_semester_2\\SOFTENG 306\\projects\\project-1\\SOFTENG306_Project1\\src\\test\\resources"

		BenchmarkParser _benchmarkParser = new BenchmarkParser("C:\\Users\\Harith\\Desktop\\UoA Resources\\2018\\2018_semester_2\\SOFTENG 306\\projects\\project-1\\output_mode1"); //"C:/Users/User/Documents/uni/306/output_mode1/output_mode1"
		_benchmarkParser.catagoriseFiles();
		
		ArrayList<File> nodeGraphs10 = _benchmarkParser.getNodesCatagory().get("10");
		//_runner.addSingle(new File("C:/Users/User/Documents/uni/306/output_mode1/output_mode1/8p_Stencil_Nodes_10_CCR_1.97_WeightType_Random.dot"));
		//_runner.addSingle(new File("src/test/resources/test_graphs_full_name/16p_Fork_Join_Nodes_21_CCR_0.10_WeightType_Random.dot"));
		ArrayList<File> allGraphs = _benchmarkParser.getAllGraphs();
		_runner.addList(nodeGraphs10);
		_runner.runAll();
		System.out.println("Passed graphs: "+ _runner.getOutputs().toString());
		System.out.println("Timed Out Graphs: " + _runner.getTimedOutGraphs());
    }
}
