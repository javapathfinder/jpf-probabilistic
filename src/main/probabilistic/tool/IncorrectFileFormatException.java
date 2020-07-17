package probabilistic.tool;

/**
 * Indicates that the given file has an incorrect format.
 * 
 * @author Zainab Fatmi
 */
@SuppressWarnings("serial")
public class IncorrectFileFormatException extends Exception {

		/**
		 * Constructs an IncorrectFileFormatException with the specified detail message.
		 * 
		 * @param errorMessage the detail message.
		 */
		public IncorrectFileFormatException(String errorMessage) {
			super(errorMessage);
		}
}
