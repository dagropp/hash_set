import java.util.*;

/**
 * Has a main method that measures the run-times requested in the "Performance Analysis" section.
 */
public class SimpleSetPerformanceAnalyzer {
    /* UI menu constant variables */
    private static final char[] INPUT_OPTIONS = {'1', '2', '3', '4', '5', '6', 'a', 'q'}; // Valid input characters.
    // User message at beginning of UI.
    private static final String OPTIONS_MENU =
            "CHOOSE WHICH TEST\\S TO PERFORM: TO CHOOSE TESTS PRESS THEIR NUMBERS (NO SPACES), TO CHOOSE ALL TESTS " +
                    "PRESS 'a', TO QUIT PRESS 'q'\n*****************************************";
    // User message that asks for input.
    private static final String INPUT_HELPER = "*****************************************\n" +
            "YOUR INPUT <<EXAMPLE:  145  WILL PERFORM TESTS 1, 4, 5>> ";
    private static final String INPUT_ERROR = "INVALID INPUT\n"; // User message that alerts invalid input.
    /* General tests constant variables */
    private static final int NANO_TO_MS = 1000000; // Conversion unit between nanoseconds and milliseconds.
    // Number of times 'contains' method is tested on each data-struct test (except LinkedList).
    private static final int CONTAINS_ITERATION_GENERAL = 70000;
    // Number of times 'contains' method is tested on each LinkedList data-struct test.
    private static final int CONTAINS_ITERATION_LINKED_LIST = 7000;
    private static final String MILLISECONDS = " ms."; // Milliseconds string representation.
    private static final String NANOSECONDS = " ns."; // Nanoseconds string representation.
    // Index of all data-structs and their tests numbers.
    private static final String DATA_STRUCTS_INDEX =
            "(1) OpenHashSet (2) ClosedHashSet (3) Java TreeSet (4) Java LinkedList (5) Java HashSet";
    /* Test 1 constant variables */
    private static final String TEST1_MSG = "TEST 1: add('data1') to all data-structures."; // Test 1 message.
    /* Test 2 constant variables */
    private static final String TEST2_MSG = "TEST 2: add('data2') to all data-structures."; // Test 2 message.
    /* Test 3 constant variables */
    private static final String TEST3_MSG = "TEST 3: contains(\"hi\") in 'data1'."; // Test 3 message.
    private static final String TEST3_VALUE = "hi"; // Test 3 and 6 value.
    /* Test 4 constant variables */
    private static final String TEST4_MSG = "TEST 4: contains(\"-13170890158\") in 'data1'."; // Test 4 message.
    private static final String TEST4_VALUE = "-13170890158"; // Test 4 value.
    /* Test 5 constant variables */
    private static final String TEST5_MSG = "TEST 5: contains(\"23\") in 'data2'."; // Test 5 message.
    private static final String TEST5_VALUE = "23"; // Test 5 value.
    /* Test 6 constant variables */
    private static final String TEST6_MSG = "TEST 6: contains(\"hi\") in 'data2'."; // Test 6 message.
    /* Data structures SimpleSet array */
    private static SimpleSet[] dataStructs = initDataStructsArray(); // Array holding all relevant data-structs.
    private static final int LINKED_LIST_INDEX = 3; // Index in said array were LinkedList is stored.
    /* Data lists arrays */
    private static final String[] data1 = Ex4Utils.file2array("data1.txt"); // Array holding data1.txt values.
    private static final String[] data2 = Ex4Utils.file2array("data2.txt"); // Array holding data2.txt values.

    /**
     * Main method that initializes the user menu to choose between tests.
     *
     * @param args Command line args - not used.
     */
    public static void main(String[] args) {
        printMenu(); // Calls method that prints user menu.
    }

    /**
     * Prints menu to choose between tests. After given input, validates it and if ok performs specified tests.
     */
    private static void printMenu() {
        Scanner input = new Scanner(System.in); // New scanner object to collect user input.
        // Assign array to hold all tests messages.
        String[] testsMsg = {TEST1_MSG, TEST2_MSG, TEST3_MSG, TEST4_MSG, TEST5_MSG, TEST6_MSG};
        System.out.println(OPTIONS_MENU); // Prints UI explanation message.
        for (String msg : testsMsg)
            System.out.println(msg); // Prints each test explanation message.
        System.out.print(INPUT_HELPER);
        String choice = input.nextLine(); // Assign input to string.
        // If input is valid, run specified tests.
        if (validateInput(choice)) {
            for (int i = 0; i < choice.length(); i++)
                performTests(choice.charAt(i));
            // Input not valid, prints error message and ask for input again.
        } else {
            System.out.println(INPUT_ERROR);
            printMenu();
        }
        System.out.println(DATA_STRUCTS_INDEX);
    }

    /**
     * Checks if user input contains only valid characters (based on INPUT_OPTIONS const array). Also, if contains 'a'
     * (performs all tests) or 'q' (quit tester), checks that it's the only input characters.
     *
     * @param input User input to validate.
     * @return True if input valid, false otherwise.
     */
    private static boolean validateInput(String input) {
        for (int i = 0; i < input.length(); i++) { // Goes over input's characters.
            // Calls dedicated method that checks if input char doesn't exist in options array. If so return false.
            if (!inOptions(input.charAt(i)))
                return false;
            // Also, check if input contains 'a' or 'q' and any other characters. If so return false.
            if ((input.charAt(i) == 'a' || input.charAt(i) == 'q') && input.length() > 1)
                return false;
        }
        return true; // If no problems detected.
    }

    /**
     * Helps validateInput method: checks if given character exists in input options array.
     *
     * @param val Character to check if exists in options array.
     * @return True if exists, false otherwise.
     */
    private static boolean inOptions(char val) {
        for (char c : INPUT_OPTIONS)
            if (val == c)
                return true;
        return false;
    }

    /**
     * Performs tests based on user input. If char not found does nothing (shouldn't happen due to validation method).
     *
     * @param test Input char taken from string input.
     */
    private static void performTests(char test) {
        switch (test) {
            case '1':
                runTest1();
                break;
            case '2':
                runTest2();
                break;
            case '3':
                runTest3();
                break;
            case '4':
                runTest4();
                break;
            case '5':
                runTest5();
                break;
            case '6':
                runTest6();
                break;
            case 'a':
                runAllTests();
                break;
            case 'q':
                System.exit(0);
        }
    }

    /**
     * Prints specified test results.
     *
     * @param testMsg    This test explanation message.
     * @param testResult Given test time result.
     * @param unit       Time unit (Milliseconds/Nanoseconds) test was performed at.
     */
    private static void printTest(String testMsg, long[] testResult, String unit) {
        System.out.println(testMsg); // Prints test explanation message.
        // Prints each data-struct test result.
        for (int i = 0; i < testResult.length; i++)
            System.out.print("(" + (i + 1) + ") " + testResult[i] + unit + " ");
        System.out.println(); // Prints empty line between tests.
    }

    /**
     * Runs test1 as specified in the exercise description.
     */
    private static void runTest1() {
        testAdd(data1, TEST1_MSG);
    }

    /**
     * Runs test2 as specified in the exercise description.
     */
    private static void runTest2() {
        testAdd(data2, TEST2_MSG);
    }

    /**
     * Runs test3 as specified in the exercise description.
     */
    private static void runTest3() {
        testContains(data1, TEST3_VALUE, TEST3_MSG);
    }

    /**
     * Runs test4 as specified in the exercise description.
     */
    private static void runTest4() {
        testContains(data1, TEST4_VALUE, TEST4_MSG);
    }

    /**
     * Runs test5 as specified in the exercise description.
     */
    private static void runTest5() {
        testContains(data2, TEST5_VALUE, TEST5_MSG);
    }

    /**
     * Runs test6 as specified in the exercise description.
     */
    private static void runTest6() {
        testContains(data2, TEST3_VALUE, TEST6_MSG);
    }

    /**
     * Runs all tests (1-6).
     */
    private static void runAllTests() {
        runTest1();
        runTest2();
        runTest3();
        runTest4();
        runTest5();
        runTest6();
    }

    /**
     * Tests 'add' method run-time, relevant for tests 1-2.
     *
     * @param dataList The specified array to add to data-structs (data1/data2).
     * @param testMsg  This test explanation message.
     */
    private static void testAdd(String[] dataList, String testMsg) {
        dataStructs = initDataStructsArray(); // Re-initializes data-structs array.
        long[] result = new long[dataStructs.length]; // Assign long array to hold test results.
        // Measure run-time for 'add' method for each data-struct, and assign result to result array.
        for (int i = 0; i < dataStructs.length; i++)
            result[i] = measureAdd(dataList, dataStructs[i]);
        printTest(testMsg, result, MILLISECONDS); // Prints test result with dedicated method.
    }

    /**
     * Measures run-time for 'add' method for specified data-struct.
     *
     * @param dataList   The specified array to add to data-struct (data1/data2).
     * @param dataStruct The specified data-struct to add data to.
     * @return Number of milliseconds process took.
     */
    private static long measureAdd(String[] dataList, SimpleSet dataStruct) {
        long timeBefore = System.nanoTime(); // Measure time before process started.
        addAll(dataList, dataStruct); // Adds array elements to data-struct with dedicated method.
        // Returns the time difference, converted to milliseconds.
        return (System.nanoTime() - timeBefore) / NANO_TO_MS;
    }

    /**
     * Adds all elements in data array to specified data-struct.
     *
     * @param dataList   The specified array to add to data-struct (data1/data2).
     * @param dataStruct The specified data-struct to add data to.
     */
    private static void addAll(String[] dataList, SimpleSet dataStruct) {
        for (String item : dataList)
            dataStruct.add(item);
    }

    /**
     * Tests 'contains' method run-time, relevant for tests 3-6.
     *
     * @param dataList The specified array to add to data-structs (data1/data2).
     * @param item     Item to check if contains in the data-structs.
     * @param testMsg  This test explanation message.
     */
    private static void testContains(String[] dataList, String item, String testMsg) {
        dataStructs = initDataStructsArray(); // Re-initializes data-structs array.
        long[] result = new long[dataStructs.length]; // Assign long array to hold test results.
        for (int i = 0; i < dataStructs.length; i++) {
            addAll(dataList, dataStructs[i]); // Adds data-list items to each data-struct.
            // If data-struct is LinkedList, call dedicated method that measures LinkedList 'contains' run-time.
            if (i == LINKED_LIST_INDEX)
                result[i] = measureContainsLinkedList(dataStructs[i], item); // Assign result to result array.
                // For all other data-structs, call dedicated method that measures general 'contains' run-time.
            else
                result[i] = measureContainsGeneral(dataStructs[i], item); // Assign result to result array.
        }
        printTest(testMsg, result, NANOSECONDS); // Prints test result with dedicated method.
    }

    /**
     * Measures run-time for 'contains' method for specified data-struct (except for LinkedList, according to
     * exercise description).
     *
     * @param dataStruct The specified data-struct to check if contains item.
     * @param item       Item to check if contains in the data-struct.
     * @return Number of nanoseconds process took.
     */
    private static long measureContainsGeneral(SimpleSet dataStruct, String item) {
        // Perform 'contains' method 70,000 times before measuring, according to exercise description.
        for (int preTest = 0; preTest < CONTAINS_ITERATION_GENERAL; preTest++)
            dataStruct.contains(item);
        long timeBefore = System.nanoTime(); // Measure time before process started.
        for (int test = 0; test < CONTAINS_ITERATION_GENERAL; test++)
            dataStruct.contains(item); // Perform 'contains' method 70,000 times.
        // Returns the time difference, divided by number of iterations (70,000).
        return (System.nanoTime() - timeBefore) / CONTAINS_ITERATION_GENERAL;

    }

    /**
     * Measures run-time for 'contains' method for LinkedList data-struct (according to exercise description).
     *
     * @param dataStruct The LinkedList to check if contains item.
     * @param item       Item to check if contains in the data-struct.
     * @return Number of nanoseconds process took.
     */
    private static long measureContainsLinkedList(SimpleSet dataStruct, String item) {
        long timeBefore = System.nanoTime(); // Measure time before process started.
        for (int test = 0; test < CONTAINS_ITERATION_LINKED_LIST; test++)
            dataStruct.contains(item); // Perform 'contains' method 7,000 times.
        // Returns the time difference, divided by number of iterations (7,000).
        return (System.nanoTime() - timeBefore) / CONTAINS_ITERATION_LINKED_LIST;
    }

    /**
     * @return Reinitialized SimpleSet array that holds all relevant data-structs.
     */
    private static SimpleSet[] initDataStructsArray() {
        return new SimpleSet[]{
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<>()),
                new CollectionFacadeSet(new LinkedList<>()),
                new CollectionFacadeSet(new HashSet<>())
        };
    }
}
