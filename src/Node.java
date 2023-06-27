public class Node implements Comparable<Node> {
    private final Point curPoint;
    private Node prevNode;
    private final double g;   // distance from start
    private double h;         // heuristic distance
    private double f;         // total distance (g + h)

    public Node(Point curPoint, double g){
        this.curPoint = curPoint;
        this.g = g;
    }

    public Point getCurPoint(){
        return this.curPoint;
    }

    public double getG(){
        return this.g;
    }

    public double getH(){
        return this.h;
    }

    public void setH(double h){
        this.h = h;
    }

    public void setF(double f){
        this.f = f;
    }

    public Node getPrevNode(){
        return this.prevNode;
    }

    public void setPrevNode(Node prevNode){
        this.prevNode = prevNode;
    }

    public int compareTo(Node node){
        return Double.compare(f, node.f);
    }
}
