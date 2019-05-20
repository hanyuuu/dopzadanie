package base;

public class problem {
	int number;
	String description;
	solution solution;
	rating rating;
	problem parent;

	public problem(int number, String description, solution solution, rating rating) {
		this.number = number;
		this.description = description;
		this.solution = solution;
		this.rating = rating;
	}

	public void printProblem() {

	}

	public void setParentProblem(problem parentProblem) {
		this.parent = parentProblem;
	}
}
