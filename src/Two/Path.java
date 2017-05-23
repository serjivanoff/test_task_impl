package Two;
/*Path is representing an edge of graph
 cost is representing weight of edge
 fromIndex, toIndex are indexes of vertices belongs to the edge
* */
public class Path {
    private final int cost;
    private final int fromIndex;
    private final int toIndex;

   Path(int from, int to, int cost) {
        this.fromIndex = from;
        this.toIndex = to;
        this.cost = cost;
    }

     int getFromIndex() {
        return fromIndex;
    }

     int getTo() {
        return toIndex;
    }

     int getCost() {
        return cost;
    }
}
