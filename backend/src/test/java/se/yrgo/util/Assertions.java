package se.yrgo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public final class Assertions {
    private Assertions() {}
    
    public static void assertListEqualsOrdered(List<?> l1, List<?> l2) {
        assertEquals(l1.size(), l2.size(), "List sizes do not match");

        for (int i = 0; i < l1.size(); ++i) {
            assertEquals(l1.get(i), l2.get(i), "List element do not match");
        }
    }
}
