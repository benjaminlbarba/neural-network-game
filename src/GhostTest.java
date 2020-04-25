import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostTest {
    Ghost ghostTest = new Ghost(212, 145, 22, false, 0);

    @Test
    void rest() {
        ghostTest.rest();
        assertEquals(ghostTest.getX(), ghostTest.getInitialX());
        assertEquals(ghostTest.getY(), ghostTest.getInitialY());
    }
}