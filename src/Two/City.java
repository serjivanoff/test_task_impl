package Two;
/*
City is representing the node of weighted graph.
- isVisited indicates if this instance of City was ever chosen as the current node with minimal currentCost
value among other nodes.
- isDestination is raised for Destination city and prevents rising of isVisited flag.
- currentCost indicates the minimum known cost to trip from Source city to Destination city.
In the moment of creation this value is set to Integer.MAX_VALUE as value of infinity.
- from  contains reference to the city we've gone from.
- paths is an array of all available for this city directions

* */
public class City{
    private final String name;
    private boolean isVisited;
    private boolean isDestination;
    private int currentCost;
    private City from;
    private final Path[]paths;

    City(String name, Path[] paths) {
        this.isVisited=false;
        this.currentCost = Integer.MAX_VALUE;
        this.from = null;
        this.name = name;
        this.paths = paths;
    }

    String getName() {
        return name;
    }

    boolean isVisited() {
        return isVisited;
    }

    void setVisited(boolean visited) {
        isVisited = visited;
    }

    int getCurrentCost() {
        return currentCost;
    }

    void setCurrentCost(int currentCost) {
        this.currentCost = currentCost;
    }

    public City getFrom() {
        return from;
    }

    void setFrom(City from) {
        this.from = from;
    }

    boolean isDestination() {
        return isDestination;
    }

    void setDestination(boolean destination) {
        isDestination = destination;
    }

    Path[] getPaths() {
        return paths;
    }
}