package Two;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/*
I've decided to use Dijkstra's algorithm (vs Floyd's ) because
number of paths to find is <=100 and number of cities is <=10 000, so it would be faster
O(100*(10 000)^2) = O(10E+10) if it needs to find 100 paths in 10 000 cities with Dijkstra's (the worst case)
O(10 000^3) = O(10E+12) - finding 1 path

 */
public class PathFinder {
    private final int TESTS_MAX_VALUE = 10;
    private final int CITIES_MAX_VALUE = 10000;
    private final int COST_MAX_VALUE = 200000;
    private final int PATHS_MAX_VALUE = 100;

    private City[] cities;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static PathFinder pathFinder =  new PathFinder();


    public static void main(String[] args)throws IOException{
    pathFinder.go();
    pathFinder.reader.close();
    }

/*
    User's console input processing, initializing etc.
    Number of tests  falls to main loop, one iteration per test.
    Then, in main loop reading number of cities, initializing
    array of cities, reading number of paths to find, and so on.
    After all data has been collected,
*/
   private void go(){
        int numberOfTests = parseToInt(TESTS_MAX_VALUE);
            for(int i = 0; i < numberOfTests; i++){
                int numberOfCities = parseToInt(CITIES_MAX_VALUE);
                cities = readAllAboutCities(numberOfCities);
                int numberOfPathsToFind = parseToInt(PATHS_MAX_VALUE);
/*
 Matrix "sourcesAndDestinations" will contents pairs of cities FROM and TO like this:
                        0               1         ...
                   _____________________________
    FROM         0|  gdansk           bydgoszcz
     TO          1|  warzawa          warzawa
*/
                String[][]sourcesAndDestinations = new String[numberOfPathsToFind][2];
                for(int j = 0; j < numberOfPathsToFind; j++) {
                    String line = readString().trim();
                    sourcesAndDestinations[j][0] = line.split(" ")[0];
                    sourcesAndDestinations[j][1] = line.split(" ")[1];
                  }
                System.out.println("Output:");
                for(int j = 0; j < sourcesAndDestinations.length; j++){
                    System.out.println(dijkstraProcessing(sourcesAndDestinations[j][0], sourcesAndDestinations[j][1]));
                    reInit();
                }
                System.out.println();
            }
    }

//Resetting method's implementation

    private void reInit(){
        for(City city : cities){
//            city.setFrom(null);
            city.setCurrentCost(Integer.MAX_VALUE);
            city.setVisited(false);
        }
    }

//Returns instance of City with given name from array of cities

    private City getByName(String name){
        City result = null;
        for (City city : cities) {
            if (name.equals(city.getName())) {
                result = city;
                break;
            }
        }
        return result;
    }

//Returns the city the trip to which will be the cheapest from the source city.

    private City getCityWithMinimalCost(){
        City result = null;
        int cost = Integer.MAX_VALUE;
        for (City city : cities) {
            if (city.isVisited()) continue;
            if (cost > city.getCurrentCost()) {
                cost = city.getCurrentCost();
                result = city;
//after city is chosen, it's flag "isVisited" will be raised unless this city is a destination city
//"isVisited" also indicates that optimal path to this point was found
                if(!city.isDestination())city.setVisited(true);
            }
        }
        return result;
    }
/*
Flag "isDestination" is need to have opportunity to reach the destination city with optimal way after it was reached before
 */
    private int dijkstraProcessing(String source, String destination){
        int result = 0;
            City sourceCity = getByName(source);
            City destinationCity = getByName(destination);
            destinationCity.setDestination(true);
//Price to travel to city from itself is ZERO
            sourceCity.setCurrentCost(0);
            sourceCity.setVisited(true);
// Beginning from source city
            City currentCity = sourceCity;
            while(true) {
//Exit the loop if we've reached the destination city
                if(currentCity.equals(destinationCity)){break;}
/*
Dijkstra's algorithm implementation. https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
Notation in comments: Q is current cost to reach THIS (current) city from the SOURCE city.
                      C is cost of trip to one of available neighbours from CURRENT position.

As we're standing in current city (with solved Q, given paths and number of available neighbours),
let's find out the C - cost of trip to each of them. "C" consists from Q and path's cost.
If the cost C is less than already known one, then that neighbour's value Q will be overwritten with C and
"from" field will set to current city.
After this we're choosing the city in which we haven't already been and with minimal cost of trip to it.
The loop will end if the chosen city becomes destination city.
* */
                Path[] paths = currentCity.getPaths();
                    for(int j = 0; j < paths.length; j++){
                        int cost = currentCity.getCurrentCost() + paths[j].getCost();
                        if(cost < cities[paths[j].getTo()].getCurrentCost()){
                            cities[paths[j].getTo()].setCurrentCost(cost);
                            cities[paths[j].getTo()].setFrom(currentCity);
                        }
                    }
                    City temp = getCityWithMinimalCost();
                    if(temp!=null)currentCity = temp;
            }
            result = currentCity.getCurrentCost();
            currentCity.setDestination(false);
        return result;
    }
// Returns the String, entered from console
    private String readString(){
        String result="";
        while(result.length()==0)
        try {
           result = reader.readLine();
        }catch (IOException e){System.out.println("Something goes wrong. Try again");}
        return result;
    }
//Reading from console, parsing to int, comparing with RANGE
    private int parseToInt(int range){
        int result = 0;
        while(!(result > 0 && result < range)) {
            try {
                result = Integer.parseInt(readString());
            } catch (NumberFormatException e) {
                System.out.println("Enter the number between 1 and " + range + ", please");
            }
        }
        return result;
    }
//Parsing to int, comparing with RANGE
    private int parseToInt(int range, String line){
        int result = 0;
             try {
                result = Integer.parseInt(line);
                 if(result > range) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Enter the number between 1 and " + range + ", please");
                }
        return result;
    }
    /*
    Entering cities from console.
    All entered cities are instantiating there and storing in an array

    * */
    private City[] readAllAboutCities(int numberOfCities){
        City[] cities = new City[numberOfCities];
        for(int i = 0; i < numberOfCities; i++){
            String cityName = readString();
            if(cityName.length()>10){
                cityName=cityName.substring(0,10);
            }
            int numberOfNeighbours = parseToInt(Integer.MAX_VALUE);
            Path[]paths = new Path[numberOfNeighbours];
            for(int j = 0; j < numberOfNeighbours; j++) {
                String nrCost = readString().trim();
//Index in array begins with 0, and the cities indexing in user's input begins with 1.
                int index = parseToInt(CITIES_MAX_VALUE, nrCost.split(" ")[0]) - 1;
                int cost = parseToInt(COST_MAX_VALUE, nrCost.split(" ")[1]);
                paths[j]=new Path(i, index, cost);
            }
            cities[i] = new City(cityName, paths);
         }
         return cities;
    }
  }