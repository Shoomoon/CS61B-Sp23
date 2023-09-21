import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {
    private Percolation p;
    @BeforeEach
    void init() {
        p = new Percolation(4);
        p.open(0, 0);
        p.open(1, 1);
        p.open(2, 2);
        p.open(0, 1);
        p.open(1, 2);
        p.open(2, 3);
        p.open(3, 3);
        try {
            Percolation q = new Percolation(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Size N must be > 0!", e.getMessage());
        }
    }
    @Test
    void percolation() {
        try {
            Percolation q = new Percolation(-1);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Size N must be > 0!", e.getMessage());
        }
    }
    @Test
    void open() {
        assertThat(p.isOpen(1, 0)).isFalse();
        p.open(1, 0);
        assertThat(p.isOpen(1, 0)).isTrue();
    }
    @Test
    void isOpen() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j || j == i + 1) {
                    assertThat(p.isOpen(i, j)).isTrue();
                } else {
                    assertThat(p.isOpen(i, j)).isFalse();
                }
            }
        }
        try {
            p.isOpen(-1, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        try {
            p.isOpen(4, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        try {
            p.isOpen(0, -1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        try {
            p.isOpen(0, 4);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
    }

    @Test
    void isFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j || j == i + 1) {
                    assertThat(p.isFull(i, j)).isTrue();
                } else {
                    assertThat(p.isFull(i, j)).isFalse();
                }
            }
        }
        try {
            p.isFull(-1, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        try {
            p.isFull(4, 0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        try {
            p.isFull(0, -1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        try {
            p.isFull(0, 4);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("0 <= row < N and 0 <= col < N needed!", e.getMessage());
        }
        assertThrows(IndexOutOfBoundsException.class, () -> p.isFull(0, 4));
    }

    @Test
    void numberOfOpenSites() {
        assertThat(p.numberOfOpenSites()).isEqualTo(7);
    }

    @Test
    void percolates() {
        assertThat(p.percolates()).isTrue();
    }
}