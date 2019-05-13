import java.util.*;

/**
 * Has a main method that measures the run-times requested in the "Performance Analysis" section.
 */
public class SimpleSetPerformanceAnalyzer {
    /* General tests constant variables */
    private static final int NANO_TO_MS = 1000000;
    private static final int CONTAINS_ITERATION_GENERAL = 70000;
    private static final int CONTAINS_ITERATION_LINKED_LIST = 7000;
    private static final int LINKED_LIST_INDEX = 3;
    private static final String TESTS_INDEX =
            "(1) OpenHashSet (2) ClosedHashSet (3) Java TreeSet (4) Java LinkedList (5) Java HashSet";
    private static final String MILLISECONDS = " ms.";
    private static final String NANOSECONDS = " ns.";
    /* Test 1 constant variables */
    private static final String TEST1_MSG =
            "TEST 1:\nThese values correspond to the time it takes (in ms) to insert data1 to all data structures";
    /* Test 2 constant variables */
    private static final String TEST2_MSG =
            "TEST 2:\nThese values correspond to the time it takes (in ms) to insert data2 to all data structures";
    /* Test 3 constant variables */
    private static final String TEST3_MSG =
            "TEST 3:\nThese values correspond to the time it takes (in ns) to check if \"hi\" is contained in the " +
                    "data structures initialized with data1";
    private static final String TEST3_VALUE = "hi";
    /* Test 4 constant variables */
    private static final String TEST4_MSG =
            "TEST 4:\nThese values correspond to the time it takes (in ns) to check if \"-13170890158\" is contained in " +
                    "the data structures initialized with data1";
    private static final String TEST4_VALUE = "-13170890158";
    /* Test 5 constant variables */
    private static final String TEST5_MSG =
            "TEST 5:\nThese values correspond to the time it takes (in ns) to check if \"23\" is contained in the " +
                    "data structures initialized with data2";
    private static final String TEST5_VALUE = "23";
    /* Test 6 constant variables */
    private static final String TEST6_MSG =
            "TEST 6:\nThese values correspond to the time it takes (in ns) to check if \"hi\" is contained in the " +
                    "data structures initialized with data2";
    /* Data structures SimpleSet array */
    private static SimpleSet[] sets = initSetsArray();
    /* Data lists arrays */
    private static final String[] data1 = Ex4Utils.file2array("data1.txt");
    private static final String[] data2 = Ex4Utils.file2array("data2.txt");

    /**
     * @param args Command line args - not used.
     */
    public static void main(String[] args) {
        testAdd(data1, TEST1_MSG);
        testAdd(data2, TEST2_MSG);
        testContains(data1, TEST3_VALUE, TEST3_MSG);
        testContains(data1, TEST4_VALUE, TEST4_MSG);
        testContains(data2, TEST5_VALUE, TEST5_MSG);
        testContains(data2, TEST3_VALUE, TEST6_MSG);
    }

    private static void testAdd(String[] dataList, String testMsg) {
        sets = initSetsArray();
        float[] result = new float[sets.length];
        for (int i = 0; i < sets.length; i++)
            result[i] = measureAdd(dataList, sets[i]);
        printTest(testMsg, result, MILLISECONDS);
    }

    private static float measureAdd(String[] dataList, SimpleSet dataStruct) {
        long timeBefore = System.nanoTime();
        addAll(dataList, dataStruct);
        return (float) (System.nanoTime() - timeBefore) / NANO_TO_MS;
    }

    private static void testContains(String[] dataList, String item, String testMsg) {
        sets = initSetsArray();
        float[] result = new float[sets.length];
        for (int i = 0; i < sets.length; i++) {
            addAll(dataList, sets[i]);
            if (i == LINKED_LIST_INDEX)
                result[i] = measureContainsLinkedList(sets[i], item);
            else
                result[i] = measureContainsGeneral(sets[i], item);
        }
        printTest(testMsg, result, NANOSECONDS);
    }

    private static float measureContainsGeneral(SimpleSet dataStruct, String item) {
        for (int i = 0; i < CONTAINS_ITERATION_GENERAL; i++)
            dataStruct.contains(item);
        long timeBefore = System.nanoTime();
        for (int i = 0; i < CONTAINS_ITERATION_GENERAL; i++)
            dataStruct.contains(item);
        return (float) (System.nanoTime() - timeBefore) / CONTAINS_ITERATION_GENERAL;

    }

    private static float measureContainsLinkedList(SimpleSet dataStruct, String item) {
        long timeBefore = System.nanoTime();
        for (int i = 0; i < CONTAINS_ITERATION_LINKED_LIST; i++)
            dataStruct.contains(item);
        return (float) (System.nanoTime() - timeBefore) / CONTAINS_ITERATION_LINKED_LIST;
    }

    private static void printTest(String testMsg, float[] testResult, String unit) {
        System.out.println(testMsg);
        System.out.println(TESTS_INDEX);
        for (int i = 0; i < testResult.length; i++)
            System.out.println("(" + (i + 1) + ") " + testResult[i] + unit);
    }

    private static SimpleSet[] initSetsArray() {
        return new SimpleSet[]{
                new OpenHashSet(),
                new ClosedHashSet(),
                new CollectionFacadeSet(new TreeSet<>()),
                new CollectionFacadeSet(new LinkedList<>()),
                new CollectionFacadeSet(new HashSet<>())
        };
    }

    private static void addAll(String[] dataList, SimpleSet dataStruct) {
        for (String item : dataList)
            dataStruct.add(item);
    }
}
