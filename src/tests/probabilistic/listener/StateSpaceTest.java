/*
 * Copyright (C) 2020  Zainab Fatmi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You can find a copy of the GNU General Public License at
 * <http://www.gnu.org/licenses/>.
 */

package probabilistic.listener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import gov.nasa.jpf.JPF;
import gov.nasa.jpf.util.test.TestJPF;
import probabilistic.Choice;

/**
 * Tests the StateSpaceText and StateSpaceDot listeners.
 * 
 * @author Zainab Fatmi
 */
public class StateSpaceTest extends TestJPF {

	/**
	 * Private static inner class for the fields and methods monitored/labeled in
	 * the test cases.
	 */
	private static class Tester {

		/** Field used in staticBooleanFieldTest */
		private static boolean condition;

		/** Field used in multipleTest */
		private static boolean x;

		/** Field used in other tests */
		private static boolean attribute;

		/** Method used in invokedMethodTest and multipleTest */
		public static void M() {
			x = true;
		}

		/** Method used in returnedMethodTest and multipleTest */
		public static void N() {
			x = true;
		}

		/** Method used in synchronizedStaticMethodTest */
		public static synchronized void S() {
			x = true;
		}
	}

	/**
	 * JPF's application properties for testing a single label-maker.
	 */
	private static String[] singleLabelMakerProperties = { "+cg.enumerate_random=true",
			"+listener=probabilistic.listener.StateSpaceText;probabilistic.listener.StateLabelVisitor;probabilistic.listener.StateSpaceDot",
			"+probabilistic.listener.StateSpaceDot.precision=6",
			"", "" };

	/**
	 * JPF's application properties used in multipleTest and fieldAndVarTest.
	 */
	private static String[] multipleLabelMakerProperties = { "+cg.enumerate_random=true",
			"+listener=probabilistic.listener.StateSpaceText;probabilistic.listener.StateLabelVisitor;probabilistic.listener.StateSpaceDot",
			"+probabilistic.listener.StateSpaceDot.precision=6",
			"+label.class = label.Initial; label.End; label.IntegerLocalVariable;"
					+ "label.InvokedMethod; label.ReturnedVoidMethod; label.BooleanStaticField",
			"+label.BooleanStaticField.field = probabilistic.listener.StateSpaceTest$Tester.x; probabilistic.listener.StateSpaceTest$Tester.condition",
			"+label.InvokedMethod.method = probabilistic.listener.StateSpaceTest$Tester.M(); probabilistic.listener.StateSpaceTest$Tester.N()",
			"+label.ReturnedVoidMethod.method = probabilistic.listener.StateSpaceTest$Tester.M(); probabilistic.listener.StateSpaceTest$Tester.N()",
			"+label.IntegerLocalVariable.variable = probabilistic.listener.StateSpaceTest.multipleTest():y;"
					+ "probabilistic.listener.StateSpaceTest.fieldAndVarTest():a; probabilistic.listener.StateSpaceTest.fieldAndVarTest():b" };

	/**
	 * The path to the directory containing the test files/resources.
	 */
	private static String path;

	/**
	 * The name of the transition file (i.e. the default file name is the signature
	 * of the target class).
	 */
	private static String transitionFileName = StateSpaceTest.class.getName() + ".tra";

	/**
	 * The name of the graph file (i.e. the default file name is the signature of
	 * the target class).
	 */
	private static String dottyFileName = StateSpaceTest.class.getName() + ".dot";

	/**
	 * Defines the paths.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		path = JPF.createConfig(new String[] {}).getString("jpf-probabilistic") + "/src/tests/resources/";
	}

	/**
	 * Removes the label file created in the test cases.
	 */
	@AfterClass
	public static void tearDownAfterClass() {
		File transitionFile = new File(transitionFileName);
		transitionFile.delete();

		File dottyFile = new File(dottyFileName);
		dottyFile.delete();

		File legendFile = new File(StateSpaceTest.class.getName() + "_legend.dot");
		legendFile.delete();
	}

	/**
	 * Tests the listeners with no labeling classes.
	 */
	@Test
	public void emptyTest() {
		singleLabelMakerProperties[3] = "";
		singleLabelMakerProperties[4] = "";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			// do nothing
		} else {
			assertTrue(filesEqual(transitionFileName, path + "empty.tra"));
			assertTrue(filesEqual(dottyFileName, path + "empty.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling the initial state.
	 */
	@Test
	public void initialTest() {
		singleLabelMakerProperties[3] = "+label.class=label.Initial";
		singleLabelMakerProperties[4] = "";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.attribute = true;
			} else {
				Tester.attribute = false;
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "noTransitionLabels.tra"));
			assertTrue(filesEqual(dottyFileName, path + "initial.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling the final states.
	 */
	@Test
	public void finalTest() {
		singleLabelMakerProperties[3] = "+label.class=label.End";
		singleLabelMakerProperties[4] = "";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.attribute = true;
			} else {
				Tester.attribute = false;
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "noTransitionLabels.tra"));
			assertTrue(filesEqual(dottyFileName, path + "end.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling the static boolean attribute "condition".
	 */
	@Test
	public void booleanStaticFieldTest() {
		singleLabelMakerProperties[3] = "+label.class=label.BooleanStaticField";
		singleLabelMakerProperties[4] = "+label.BooleanStaticField.field = probabilistic.listener.StateSpaceTest$Tester.condition";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			int i = 0;
			Tester.condition = true;
			double[] choices = { 0.5, 0.5 };
			while (Tester.condition && Math.abs(i) < 5) {
				if (Choice.make(choices) == 0) {
					i++;
				} else {
					i--;
					Tester.condition = !Tester.condition;
				}
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "booleanStaticField.tra"));
			assertTrue(filesEqual(dottyFileName, path + "booleanStaticField.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling local variables.
	 */
	@Test
	public void integerLocalVariableTest() {
		singleLabelMakerProperties[3] = "+label.class=label.IntegerLocalVariable";
		singleLabelMakerProperties[4] = "+label.IntegerLocalVariable.variable = probabilistic.listener.StateSpaceTest.integerLocalVariableTest():variable";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			int variable = 0;
			variable++; // 1	
			variable -= 3; // -2	
			variable--; // -3	
			variable = 5;	
			variable /= 2; // 2	
			variable %= 14; // 2 -> no break	
			variable = (int) -12.0;	
			variable *= 2; // -24	
			variable += 92; // 68	
			variable = variable - 110; // -42
		} else {
			assertTrue(filesEqual(transitionFileName, path + "integerLocalVariable.tra"));
			assertTrue(filesEqual(dottyFileName, path + "integerLocalVariable.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling the invoke of a static method.
	 */
	@Test
	public void invokedMethodTest() {
		singleLabelMakerProperties[3] = "+label.class=label.InvokedMethod";
		singleLabelMakerProperties[4] = "+label.InvokedMethod.method = probabilistic.listener.StateSpaceTest$Tester.M()";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.attribute = true;
			} else {
				Tester.attribute = false;
			}
			if (Tester.attribute) {
				Tester.M();
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "staticMethod.tra"));
			assertTrue(filesEqual(dottyFileName, path + "staticMethod.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling the return of a static method.
	 */
	@Test
	public void returnedVoidMethodTest() {
		singleLabelMakerProperties[3] = "+label.class=label.ReturnedVoidMethod";
		singleLabelMakerProperties[4] = "+label.ReturnedVoidMethod.method = probabilistic.listener.StateSpaceTest$Tester.N()";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.attribute = true;
			} else {
				Tester.attribute = false;
			}
			if (Tester.attribute) {
				Tester.N();
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "staticMethod.tra"));
			assertTrue(filesEqual(dottyFileName, path + "staticMethod.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling the locking and unlocking of a synchronized
	 * static method.
	 */
	@Test
	public void synchronizedStaticMethodTest() {
		singleLabelMakerProperties[3] = "+label.class=label.SynchronizedStaticMethod";
		singleLabelMakerProperties[4] = "+label.SynchronizedStaticMethod.method = probabilistic.listener.StateSpaceTest$Tester.S()";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.attribute = true;
			} else {
				Tester.attribute = false;
			}
			if (Tester.attribute) {
				Tester.S();
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "synchronizedStaticMethod.tra"));
			assertTrue(filesEqual(dottyFileName, path + "synchronizedStaticMethod.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling thrown exceptions and errors.
	 */
	@Test
	public void throwableTest() {
		singleLabelMakerProperties[3] = "+label.class=label.ThrownException";
		singleLabelMakerProperties[4] = "+label.ThrownException.type = java.io.FileNotFoundException;"
				+ "java.lang.IllegalArgumentException;java.lang.AssertionError";

		if (verifyNoPropertyViolation(singleLabelMakerProperties)) {
			try {
				throw new AssertionError();
			} catch (Error e) {
				System.out.println(e.toString());
			}
			try {
				throw new FileNotFoundException("Message");
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			try {
				throw new IllegalArgumentException();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "throwable.tra"));
			assertTrue(filesEqual(dottyFileName, path + "throwable.dot"));
		}
	}

	/**
	 * Tests the listeners with multiple labeling functions.
	 */
	@Test
	public void multipleTest() {
		if (verifyNoPropertyViolation(multipleLabelMakerProperties)) {
			Tester.x = false;
			if (!Tester.x) {
				Tester.x = false;
			}
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.M();
			} else {
				Tester.N();
			}
			int y = 5;
			if (y > 3) {
				Tester.x = false;
			}
			Tester.x = true;
			Tester.attribute = true;
		} else {
			assertTrue(filesEqual(transitionFileName, path + "multiple.tra"));
			assertTrue(filesEqual(dottyFileName, path + "multiple.dot"));
		}
	}

	/**
	 * Tests the listeners with labeling multiple fields and variables, as well as
	 * initial and final states and different labels for each state.
	 */
	@Test
	public void fieldAndVarTest() {
		if (verifyNoPropertyViolation(multipleLabelMakerProperties)) {
			Tester.x = false;
			Tester.condition = false;
			Tester.attribute = false; // not labeled
			int a = 1;
			int b = -1;
			int c = -2; // not labeled

			Tester.x = false;
			a = -4;
			for (int i = 0; i < 3; i++) {
				b++;
			}
			double[] choices = { 0.5, 0.5 };
			if (Choice.make(choices) == 0) {
				Tester.attribute = true;
			} else {
				Tester.condition = true;
				Tester.x = true;
			}
		} else {
			assertTrue(filesEqual(transitionFileName, path + "fieldAndVar.tra"));
			assertTrue(filesEqual(dottyFileName, path + "fieldAndVar.dot"));
		}
	}

	/**
	 * Compares two files and determines whether have the same contents.
	 * 
	 * @param actual   Name of the actual file.
	 * @param expected Name of the expected file.
	 * @return true if the files have the same contents, false otherwise.
	 */
	private boolean filesEqual(String actual, String expected) {
		try {
			Scanner actualFile = new Scanner(new File(actual));
			Scanner expectedFile = new Scanner(new File(expected));
			while (expectedFile.hasNextLine()) {
				if (!actualFile.hasNextLine()) {
					System.out.println("Expected file has more lines than actual file");
					actualFile.close();
					expectedFile.close();
					return false;
				} else {
					String actualLine = actualFile.nextLine();
					String expectedLine = expectedFile.nextLine();
					if (!actualLine.equals(expectedLine)) {
						System.out.println("Line of actual file: " + actualLine);
						System.out.println("Line of expected file: " + expectedLine);
						actualFile.close();
						expectedFile.close();
						return false;
					}
				}
			}
			if (actualFile.hasNextLine()) {
				System.out.println("Actual file has more lines than expected file");
				actualFile.close();
				expectedFile.close();
				return false;
			} else {
				actualFile.close();
				expectedFile.close();
				return true;
			}
		} catch (FileNotFoundException e) {
			System.out.println("The file could not be read.");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Runs the test methods with the given names. If no names are given, all test
	 * methods are run.
	 *
	 * @param testMethods the names of the test methods to be run.
	 */
	public static void main(String[] testMethods) {
		runTestsOfThisClass(testMethods);
	}
}
