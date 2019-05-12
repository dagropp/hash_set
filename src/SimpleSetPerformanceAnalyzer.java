import java.util.HashSet;

/**
 * Has a main method that measures the run-times requested in the "Performance Analysis" section.
 */
public class SimpleSetPerformanceAnalyzer {
    private static final float NANO_TO_MS = 1000000;

    public static void main(String[] args) {
        System.out.println(measureTime());
    }

    private static float measureTime() {
        String[] array = new String[]{
                "daniel", "shira", "astar", "shaked", "ella", "amos", "yonatan", "shulamit lapid", "bibi",
                "daniel gropp", "gropp daniel", "sadaf", "asd", "moseg", "sdfvx", "wed3", "sdfxz3",
                "csdmk3", "dsni", "ecsdc", "23eddc", "2314", "dfa", "f3ff", "kkkkk", "arva", "oop", "scasdc",
                "sdfvs", "24f", "cxv", "dvsdwe", "csdv", "x g", "   d", "sdf ", "cvd", "cwewfw", "csdg", "scdg",
                "awww", ",,il", "y8o", "thfh", "vdebr", "3333rf", "vdsv", "c fddghr", "fw2r3", "vsl", "dvdvdv", "dvc",
                "ccc", "sfsfsf", "24rfdscs", "lppk", "[][][]", "gfvr4", "kkkfg", "ok0", "0004"
        };
        ClosedHashSet closedHashSet = new ClosedHashSet(array);
        OpenHashSet openHashSet = new OpenHashSet(array);
        HashSet<String> javaHashSet = new HashSet<>();
        for (String item : array)
            javaHashSet.add(item);
        long timeBefore = System.nanoTime();
        for (String item : array)
            javaHashSet.remove(item);
        return (System.nanoTime() - timeBefore) / NANO_TO_MS;
    }
}
