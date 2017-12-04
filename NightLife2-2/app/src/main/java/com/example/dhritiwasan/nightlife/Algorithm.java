package com.example.dhritiwasan.nightlife;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import com.google.common.collect.Collections2;

/**
 * Created by dhritiwasan on 29/11/17.
 */

public class Algorithm {

    public class Route implements Comparable<Route>{
        String from;
        String to;
        double time;
        String mode;
        double price;

        public Route(String from, String to, double price, double time, String mode) {
            this.from = from;
            this.to = to;
            this.price = price;
            this.time = time;
            this.mode = mode;
        }

        @Override
        public int compareTo(@NonNull Route route) {
            if (this.price > route.price) {
                return -1;
            } else if (this.price < route.price) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    HashMap<String, ArrayList<Route>> placesRoute = new HashMap<>();
    Route[] routes;
    double totalPay;
    double totalTime;
    public Algorithm(double credit, String curr, boolean[] selectedPlaces) {

        ArrayList<Route> routesforMBS = new ArrayList<Route>();
        routesforMBS.add(new Route("Marina Bay Sands", "Singapore Flyer", 0.83, 17,"Public Transport"));
        routesforMBS.add(new Route("Marina Bay Sands", "Vivocity Singapore", 1.18, 26,"Public Transport"));
        routesforMBS.add(new Route("Marina Bay Sands", "Resorts World Sentosa", 4.03, 35,"Public Transport"));
        routesforMBS.add(new Route("Marina Bay Sands", "Orchard Road", 1.07, 25,"Public Transport"));
        routesforMBS.add(new Route("Marina Bay Sands", "Zouk Singapore", 0.77, 16,"Public Transport"));
        ArrayList<Route> routesforFLY = new ArrayList<Route>();
        routesforFLY.add(new Route("Singapore Flyer", "Marina Bay Sands", 0.83, 17,"Public Transport"));
        routesforFLY.add(new Route("Singapore Flyer", "Vivocity Singapore", 1.26, 31,"Public Transport"));
        routesforFLY.add(new Route("Singapore Flyer", "Resorts World Sentosa", 4.03, 38,"Public Transport"));
        routesforFLY.add(new Route("Singapore Flyer", "Orchard Road", 0.97, 28,"Public Transport"));
        routesforFLY.add(new Route("Singapore Flyer", "Zouk Singapore", 0.97, 24,"Public Transport"));
        ArrayList<Route> routesforVIV = new ArrayList<Route>();
        routesforVIV.add(new Route("Vivocity Singapore", "Marina Bay Sands", 1.18, 24,"Public Transport"));
        routesforVIV.add(new Route("Vivocity Singapore", "Singapore Flyer", 1.26, 29,"Public Transport"));
        routesforVIV.add(new Route("Vivocity Singapore", "Resorts World Sentosa", 2, 10,"Public Transport"));
        routesforVIV.add(new Route("Vivocity Singapore", "Orchard Road", 1.16, 29,"Public Transport"));
        routesforVIV.add(new Route("Vivocity Singapore", "Zouk Singapore", 0.87, 18,"Public Transport"));
        ArrayList<Route> routesforRWS = new ArrayList<Route>();
        routesforRWS.add(new Route("Resorts World Sentosa", "Marina Bay Sands", 1.18, 33,"Public Transport"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Singapore Flyer", 1.26, 38,"Public Transport"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Vivocity Singapore", 0, 10,"Public Transport"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Orchard Road", 1.07, 35,"Public Transport"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Zouk Singapore", 3.87, 26,"Public Transport"));
        ArrayList<Route> routesforORC = new ArrayList<Route>();
        routesforORC.add(new Route("Orchard Road", "Marina Bay Sands", 1.07, 25,"Public Transport"));
        routesforORC.add(new Route("Orchard Road", "Singapore Flyer", 0.97, 28,"Public Transport"));
        routesforORC.add(new Route("Orchard Road", "Vivocity Singapore", 1.16, 30,"Public Transport"));
        routesforORC.add(new Route("Orchard Road", "Resorts World Sentosa", 4.07, 35,"Public Transport"));
        routesforORC.add(new Route("Orchard Road", "Zouk Singapore", 0.77, 24,"Public Transport"));
        ArrayList<Route> routesforCLK = new ArrayList<Route>();
        routesforCLK.add(new Route("Zouk Singapore", "Marina Bay Sands", 0.77, 16,"Public Transport"));
        routesforCLK.add(new Route("Zouk Singapore", "Singapore Flyer", 0.97, 23,"Public Transport"));
        routesforCLK.add(new Route("Zouk Singapore", "Vivocity Singapore", 0.87, 18,"Public Transport"));
        routesforCLK.add(new Route("Zouk Singapore", "Resorts World Sentosa", 3.87, 26,"Public Transport"));
        routesforCLK.add(new Route("Zouk Singapore", "Orchard Road", 0.87, 22,"Public Transport"));
        routesforMBS.add(new Route("Marina Bay Sands", "Singapore Flyer", 3.22, 3,"Taxi"));
        routesforMBS.add(new Route("Marina Bay Sands", "Vivocity Singapore", 6.96, 14,"Taxi"));
        routesforMBS.add(new Route("Marina Bay Sands", "Resorts World Sentosa", 8.5, 19,"Taxi"));
        routesforMBS.add(new Route("Marina Bay Sands", "Orchard Road", 1.07, 25,"Taxi"));
        routesforMBS.add(new Route("Marina Bay Sands", "Zouk Singapore", 0.87, 30,"Taxi"));
        routesforFLY.add(new Route("Singapore Flyer", "Marina Bay Sands", 4.32, 6,"Taxi"));
        routesforFLY.add(new Route("Singapore Flyer", "Vivocity Singapore", 7.84, 13,"Taxi"));
        routesforFLY.add(new Route("Singapore Flyer", "Resorts World Sentosa", 9.38, 18,"Taxi"));
        routesforFLY.add(new Route("Singapore Flyer", "Orchard Road", 0.97, 28,"Taxi"));
        routesforFLY.add(new Route("Singapore Flyer", "Zouk Singapore", 1.16, 36,"Taxi"));
        routesforVIV.add(new Route("Vivocity Singapore", "Marina Bay Sands", 8.3, 12,"Taxi"));
        routesforVIV.add(new Route("Vivocity Singapore", "Singapore Flyer", 7.96, 14,"Taxi"));
        routesforVIV.add(new Route("Vivocity Singapore", "Resorts World Sentosa", 4.54, 9,"Taxi"));
        routesforVIV.add(new Route("Vivocity Singapore", "Orchard Road", 1.16, 29,"Taxi"));
        routesforVIV.add(new Route("Vivocity Singapore", "Zouk Singapore", 0.87, 29,"Taxi"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Marina Bay Sands", 8.74, 13,"Taxi"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Singapore Flyer", 8.4, 14,"Taxi"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Vivocity Singapore", 3.2, 4,"Taxi"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Orchard Road", 1.07, 35,"Taxi"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Zouk Singapore", 0.97, 39,"Taxi"));
        routesforORC.add(new Route("Orchard Road", "Marina Bay Sands", 13.31, 11,"Taxi"));
        routesforORC.add(new Route("Orchard Road", "Singapore Flyer", 12.79, 10,"Taxi"));
        routesforORC.add(new Route("Orchard Road", "Vivocity Singapore", 11.68, 13,"Taxi"));
        routesforORC.add(new Route("Orchard Road", "Resorts World Sentosa", 4.07, 35,"Taxi"));
        routesforORC.add(new Route("Orchard Road", "Zouk Singapore", 0.87, 23,"Taxi"));
        routesforCLK.add(new Route("Zouk Singapore", "Marina Bay Sands", 9.98, 8,"Taxi"));
        routesforCLK.add(new Route("Zouk Singapore", "Singapore Flyer", 9.65, 7,"Taxi"));
        routesforCLK.add(new Route("Zouk Singapore", "Vivocity Singapore", 12.74, 12,"Taxi"));
        routesforCLK.add(new Route("Zouk Singapore", "Resorts World Sentosa", 14.89, 22,"Taxi"));
        routesforCLK.add(new Route("Zouk Singapore", "Orchard Road", 9.06, 6,"Taxi"));
        routesforMBS.add(new Route("Marina Bay Sands", "Singapore Flyer",0, 13,"Foot"));
        routesforMBS.add(new Route("Marina Bay Sands", "Vivocity Singapore",0, 75,"Foot"));
        routesforMBS.add(new Route("Marina Bay Sands", "Resorts World Sentosa",0, 88,"Foot"));
        routesforMBS.add(new Route("Marina Bay Sands", "Orchard Road",0, 60,"Foot"));
        routesforMBS.add(new Route("Marina Bay Sands", "Zouk Singapore",0, 32,"Foot"));
        placesRoute.put("Marina Bay Sands", routesforMBS);
        routesforFLY.add(new Route("Singapore Flyer", "Marina Bay Sands",0, 16,"Foot"));
        routesforFLY.add(new Route("Singapore Flyer", "Vivocity Singapore",0, 81,"Foot"));
        routesforFLY.add(new Route("Singapore Flyer", "Resorts World Sentosa",0, 93,"Foot"));
        routesforFLY.add(new Route("Singapore Flyer", "Orchard Road",0, 56,"Foot"));
        routesforFLY.add(new Route("Singapore Flyer", "Zouk Singapore",0, 31,"Foot"));
        placesRoute.put("Singapore Flyer", routesforFLY);
        routesforVIV.add(new Route("Vivocity Singapore", "Marina Bay Sands",0, 76,"Foot"));
        routesforVIV.add(new Route("Vivocity Singapore", "Singapore Flyer",0, 88,"Foot"));
        routesforVIV.add(new Route("Vivocity Singapore", "Resorts World Sentosa",0, 20,"Foot"));
        routesforVIV.add(new Route("Vivocity Singapore", "Orchard Road",0, 73,"Foot"));
        routesforVIV.add(new Route("Vivocity Singapore", "Zouk Singapore",0, 64,"Foot"));
        placesRoute.put("Vivocity Singapore", routesforVIV);
        routesforRWS.add(new Route("Resorts World Sentosa", "Marina Bay Sands",0, 89,"Foot"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Singapore Flyer",0, 95,"Foot"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Vivocity Singapore",0, 20,"Foot"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Orchard Road",0, 86,"Foot"));
        routesforRWS.add(new Route("Resorts World Sentosa", "Zouk Singapore",0, 34,"Foot"));
        placesRoute.put("Resorts World Sentosa", routesforRWS);
        routesforORC.add(new Route("Orchard Road", "Marina Bay Sands",0, 59,"Foot"));
        routesforORC.add(new Route("Orchard Road", "Singapore Flyer",0, 55,"Foot"));
        routesforORC.add(new Route("Orchard Road", "Vivocity Singapore",0, 72,"Foot"));
        routesforORC.add(new Route("Orchard Road", "Resorts World Sentosa",0, 86,"Foot"));
        routesforORC.add(new Route("Orchard Road", "Zouk Singapore",0, 34,"Foot"));
        placesRoute.put("Orchard Road", routesforORC);
        routesforCLK.add(new Route("Zouk Singapore", "Marina Bay Sands",0, 32,"Foot"));
        routesforCLK.add(new Route("Zouk Singapore", "Singapore Flyer",0, 31,"Foot"));
        routesforCLK.add(new Route("Zouk Singapore", "Vivocity Singapore",0, 64,"Foot"));
        routesforCLK.add(new Route("Zouk Singapore", "Resorts World Sentosa",0, 77,"Foot"));
        routesforCLK.add(new Route("Zouk Singapore", "Orchard Road",0, 35,"Foot"));
        placesRoute.put("Zouk Singapore", routesforCLK);
        HashSet<String> names = new HashSet<>();
        String[] locations = {"Marina Bay Sands", "Singapore Flyer", "Vivocity Singapore", "Resorts World Sentosa", "Orchard Road", "Zouk Singapore"};
        for (int i = 0; i < selectedPlaces.length; i++) {
            if (selectedPlaces[i]) {
                names.add(locations[i]);
            }
        }
        routes = bruteforce(credit, curr, names);
    }

    public Route[] optimalRoute(double credit, String curr, HashSet<String> listOfPlaces) {
        listOfPlaces.remove(curr);
        System.out.println(listOfPlaces.contains(curr));
        System.out.println(listOfPlaces.size());
        LinkedList<String> order = placesOrder(curr, listOfPlaces);
        System.out.println(order.size());
        LinkedList<PriorityQueue<Route>> routes = new LinkedList<>();
        String prev = curr;
        for (String ord: order) {
            routes.add(findRoutes(prev, ord));
            prev = ord;
        }
        totalPay = 0;
        Route[] routeList = new Route[order.size()];
        int i = 0;
        for (PriorityQueue<Route> route : routes) {
            routeList[i++] = route.poll();

        }
        for (Route r: routeList) {
            totalPay+=r.price;
        }
        System.out.println("earlier pay is "+totalPay);
        while (totalPay > credit) {
            totalPay = 0;
            totalTime = 0;
            int toChange = findMax(routeList);
            int count = 0;
            for (PriorityQueue<Route> route: routes) {

                if (toChange == count) {
                    routeList[toChange] = route.poll();
                }
                count++;
            }
            for (Route r: routeList) {
                System.out.println("mode of transport is "+r.mode);
                totalPay+=r.price;
                totalTime+=r.time;
            }
            System.out.println("total pay is "+totalPay);

        }
        System.out.println("the total pay is "+totalPay);
        return routeList;
    }

    public int findMax(Route[] routes) {
        double max = routes[0].price;
        int i = 0;
        int count = 0;
        for (Route e: routes) {
            if (e.price > max) {
                count = i;
                max = e.price;
            }
            i++;
        }
        return count;
    }

    public LinkedList<String> placesOrder(String curr, HashSet<String> listOfPlaces) {
        String temp = findClosestPlace(curr, listOfPlaces);
        LinkedList<String> order = new LinkedList<>();
        order.add(temp);
        while (!listOfPlaces.isEmpty()) {
            temp = findClosestPlace(temp, listOfPlaces);

            order.add(temp);

        }
        order.add(curr);
        return order;
    }

    public String findClosestPlace(String from, HashSet<String> listOfPlaces) {
        ArrayList<Route> routes = placesRoute.get(from);
        Route opt = routes.get(0);
        for (Route e: routes) {
            /*System.out.println("next "+e.to);*/
            if ((e.time < opt.time || !listOfPlaces.contains(opt.to))&& listOfPlaces.contains(e.to) ) {
//                System.out.println("nearest"+e.to);
                opt = e;
            }
        }
        listOfPlaces.remove(opt.to);
        return opt.to;
    }

    public PriorityQueue<Route> findRoutes(String from, String to) {

        ArrayList<Route> routes = placesRoute.get(from);
        System.out.println(routes.size());
        System.out.println(from);
        System.out.println(to);
        ArrayList<Route> listOfRoute = new ArrayList<>();

        PriorityQueue<Route> toReturn = new PriorityQueue<>();
        for (Route e: routes) {
            if (e.to.equals(to)) {
                System.out.println(e.mode);
                toReturn.add(e);
            }
        }
        toReturn.addAll(listOfRoute);
        return toReturn;
    }

    public Route[] bruteforce(double credit, String current, HashSet<String> next) {
        next.remove(current);
        System.out.println(next.size());
        Collection<List<String>> name = Collections2.permutations(Arrays.asList(next.toArray(new String[next.size()])));
        System.out.println(name.size());
        String prev = current;

        Route[] routeList = new Route[next.size()+1];

        for (List<String> routes2 :
                name) {
            LinkedList<PriorityQueue<Route>> routesPriori = new LinkedList<>();
            LinkedList<String> routes3 = new LinkedList<String>();
            routes3.addAll(routes2);
            routes3.addLast(current);
            int i = 0;
            for (String place : routes3) {
                routesPriori.add(findRoutes(prev, place));
                prev = place;
            }
            for (PriorityQueue<Route> route : routesPriori) {
                System.out.println(i);
                System.out.println(routeList.length);
                routeList[i++] = route.poll();

            }
            for (Route r : routeList) {
                System.out.println(r == null);
                totalPay += r.price;
            }
            System.out.println("earlier pay is " + totalPay);
            while (totalPay > credit) {
                totalPay = 0;
                totalTime = 0;
                int toChange = findMax(routeList);
                int count = 0;
                for (PriorityQueue<Route> route : routesPriori) {

                    if (toChange == count) {
                        routeList[toChange] = route.poll();
                    }
                    count++;
                }
                for (Route r : routeList) {
                    System.out.println("mode of transport is " + r.mode);
                    totalPay += r.price;
                    totalTime += r.time;
                }
                System.out.println("total pay is " + totalPay);
            }
        }
        return routeList;
    }

    /*public static void main(String[] args) {
        Algorithm algo = new Algorithm(0.0, "Marina Bay Sands", new boolean[] {false, false, false, true, true, true});
        System.out.println(algo.routes);
    }*/

}
