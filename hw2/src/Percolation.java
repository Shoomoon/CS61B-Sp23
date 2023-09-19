import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] sites;
    private final WeightedQuickUnionUF ufVirtualTopBottom;
    private final WeightedQuickUnionUF ufVirtualTopOnly;
    private int openSites;

    public Percolation(int N) {
        // index 0 is virtual top, and N * N + 1 is virtual bottom
        if (N <= 0) {
            throw new IllegalArgumentException("Size N must be > 0!");
        }
        ufVirtualTopBottom = new WeightedQuickUnionUF(N * N + 2);
        ufVirtualTopOnly = new WeightedQuickUnionUF(N * N + 1);
        sites = new boolean[N][N];
        openSites = 0;
    }

    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            sites[row][col] = true;
            openSites += 1;
            int curIndex = toIndex(row, col);
            // connect cell to virtual top
            if (row == 0) {
                ufVirtualTopBottom.union(0, curIndex);
                ufVirtualTopOnly.union(0, curIndex);
            }
            // connect cell to virtual bottom
            if (row == sites.length - 1) {
                ufVirtualTopBottom.union(sites.length * sites.length + 1, curIndex);
            }
            connectNeighbours(row, col, -1, 0);
            connectNeighbours(row, col, 1, 0);
            connectNeighbours(row, col, 0, -1);
            connectNeighbours(row, col, 0, 1);
        }
    }

    public boolean isOpen(int row, int col) {
        validIndex(row, col);
        return sites[row][col];
    }

    public boolean isFull(int row, int col) {
        validIndex(row, col);
        return ufVirtualTopOnly.connected(toIndex(row, col), 0);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return ufVirtualTopBottom.connected(0, sites.length * sites.length + 1);
    }
    private void validIndex(int row, int col) {
        if (row < 0 || row >= sites.length || col < 0 || col >= sites.length) {
            throw new IndexOutOfBoundsException("0 <= row < N and 0 <= col < N needed!");
        }
    }

    // leave index 0 as virtual top
    private int toIndex(int row, int col) {
        return row * sites.length + col + 1;
    }
    private void connectNeighbours(int row, int col, int dr, int dc) {
        int i = row + dr;
        int j = col + dc;
        if (i >= 0 && i < sites.length && j >= 0 && j < sites.length && sites[i][j]) {
            ufVirtualTopBottom.union(toIndex(row, col), toIndex(i, j));
            ufVirtualTopOnly.union(toIndex(row, col), toIndex(i, j));
        }
    }

}
