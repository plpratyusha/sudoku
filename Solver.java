package sudoku;

import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
	
	// Returns fast if grid is illegal or is a complete legal solution, otherwise recurses on 
	// all possible extensions from the arg.
	private void solveRecurse(Grid grid)
	{				
		Evaluation eval = evaluate(grid);
		
		// The standard 3 cases of backtracking.
		if (eval == Evaluation.ABANDON)
			return;
		else if (eval == Evaluation.ACCEPT)
			solutions.add(grid);
		else
		{
			for (Grid next: grid.next9Grids())
				solveRecurse(next);
		}
	}
	
	
	// Converts state of a grid to an Evaluation instance.
	public Evaluation evaluate(Grid grid)
	{
		if (!grid.isLegal())
			return Evaluation.ABANDON;
		else if (grid.isFull())
			return Evaluation.ACCEPT;
		else
			return Evaluation.CONTINUE;
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	//
	// Prints a date-and-time stamp before and after solving, so you can see
	// how long it took to compute the solution.
	//
	public static void main(String[] args)
	{
		System.out.println(new Date());		
		Grid g = TestGridSupplier.getInkala();
		System.out.println(g);
		Solver solver = new Solver(g);
		solver.solve();
		System.out.println("===========================");
		System.out.println("      SOLUTION");
		System.out.println("===========================");
		System.out.println(solver.getSolutions().get(0));
		System.out.println(new Date());
	}
}
