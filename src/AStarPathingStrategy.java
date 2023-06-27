import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {
    public List<Point> computePath(
        Point start, Point end,
        Predicate<Point> canPassThrough,
        BiPredicate<Point, Point> withinReach,
        Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();
        HashMap<Point, Node> openList = new HashMap<>();
        HashMap<Point, Node> closedList = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>();

        Node startN = new Node(start, 0);
        startN.setH(getDistance(start, end));
        openList.put(start, startN);
        queue.add(startN);

        while (!queue.isEmpty()){
            Node curNode = queue.peek();
            if (withinReach.test(curNode.getCurPoint(), end)){  // tests if current location is the end point
                while (curNode.getPrevNode() != null) {
                    path.add(curNode.getCurPoint());
                    curNode = curNode.getPrevNode();
                }
                Collections.reverse(path);  // returns path from start to end
                return path;
            }

            List<Point> neighbors = potentialNeighbors.apply(curNode.getCurPoint())
                                    .filter(canPassThrough).toList();

            for (Point p : neighbors){
                if (!closedList.containsKey(p)){
                    Node next = new Node(p, curNode.getG() + 1);
                    if (openList.containsKey(p)){
                        if (openList.get(p).getG() > curNode.getG() + 1){
                            next.setPrevNode(curNode);
                            next.setF(next.getG() + next.getH());
                            openList.put(p, next);
                            queue.add(next);
                        }
                    }
                    else {
                        next.setH(getDistance(p, end));
                        next.setF(next.getG() + next.getH());
                        next.setPrevNode(curNode);
                        if (openList.containsKey(p)){
                            queue.remove(openList.get(p));
                            openList.remove(p);
                    }
                        openList.put(p, next);
                        queue.add(next);
                    }
                }
            }
            queue.remove(curNode);
            closedList.put(curNode.getCurPoint(), curNode);
        }
        return path;
    }

    public double getDistance(Point start, Point end){
        // distance formula: sqrt((x2 - x1)^2 + (y2 - y1)^2)
        return Math.sqrt(Math.pow(end.getX() - start.getX(), 2)
                + Math.pow(end.getY() - start.getY(), 2));
    }
}
