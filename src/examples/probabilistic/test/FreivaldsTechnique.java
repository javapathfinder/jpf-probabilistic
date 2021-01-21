package probabilistic.test;

/**
 * Calls verify on FreivaldsTechnique.
 * 
 * @author Franck van Breugel
 */
public class FreivaldsTechnique {
	private FreivaldsTechnique() {}

	/**
	 * Calls verify on FreivaldsTechnique with the given command 
	 * line arguments.
	 * 
	 * @param args[0] the number of trials of the algorithm
	 * @param args[1] the size of the square matrices
	 * @param args[2..] the elements of the three matrices
	 */
	public static void main(String[] args) {
		int trials = Integer.parseInt(args[0]);
		int size = Integer.parseInt(args[1]);
		int index = 2;
		int[][] first = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				first[i][j] = Integer.parseInt(args[index]);
				index++;
			}
		}
		int[][] second = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				second[i][j] = Integer.parseInt(args[index]);
				index++;
			}
		}
		int[][] product = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				product[i][j] = Integer.parseInt(args[index]);
				index++;
			}
		}
		probabilistic.examples.FreivaldsTechnique freivalds = new probabilistic.examples.FreivaldsTechnique(first, second, product);
		freivalds.verify(trials);
	}
}
