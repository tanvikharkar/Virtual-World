import processing.core.PImage;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

abstract class Dude extends MoveableEntity {
    private final int resourceLimit;
    private int resourceCount;

    public Dude(String id,
                Point position,
                List<PImage> images,
                int imageIndex,
                int actionPeriod,
                int animationPeriod,
                int resourceLimit,
                int resourceCount)
    {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public int getResourceLimit(){
        return this.resourceLimit;
    }

    public int getResourceCount(){
        return this.resourceCount;
    }

    public void setResourceCount(){
        this.resourceCount += 1;
    }

    abstract boolean transform(WorldModel worldModel, EventScheduler scheduler, ImageStore imageStore);

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy path = new AStarPathingStrategy();
        Predicate<Point> canPass = x -> (!world.isOccupied(x)) || (world.getOccupancyCell(x).getClass() == Stump.class && world.withinBounds(x));
        BiPredicate<Point, Point> inReach = Point::adjacent;

        List<Point> points = path.computePath(this.getPosition(), destPos, canPass, inReach, PathingStrategy.CARDINAL_NEIGHBORS);

        if (points.size() == 0){
            return this.getPosition();
        }
        return points.get(0);
    }
}
