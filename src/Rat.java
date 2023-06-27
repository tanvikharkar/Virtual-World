import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Rat extends MoveableEntity{
    public Rat(
            String id,
            Point position,
            List<PImage> images,
            int imageIndex,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                occupant.ifPresent(scheduler::unscheduleAllEvents);
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        PathingStrategy path = new AStarPathingStrategy();
        Predicate<Point> canPass = x -> !world.isOccupied(x) && world.withinBounds(x);
        BiPredicate<Point, Point> inReach = Point::adjacent;

        List<Point> points = path.computePath(this.getPosition(), destPos, canPass, inReach, PathingStrategy.CARDINAL_NEIGHBORS);

        if (points.size() == 0){
            return this.getPosition();
        }
        return points.get(0);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> ratTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (ratTarget.isPresent()) {
            Point tgtPos = ratTarget.get().getPosition();

            if (this.moveTo(world, ratTarget.get(), scheduler)) {
                Entity fairy = Functions.createFairy("fairy_" + this.getId(), tgtPos,
                        Functions.FAIRY_ACTION_PERIOD, Functions.FAIRY_ANIMATION_PERIOD,
                        imageStore.getImageList(Functions.FAIRY_KEY));
                Entity house = Functions.createHouse("house_" + this.getId(), tgtPos,
                        imageStore.getImageList(Functions.HOUSE_KEY));

                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
                world.addEntity(fairy);
                ((ActiveEntity)fairy).scheduleActions(scheduler, world, imageStore);
                world.addEntity(house);
            }
        }

        scheduler.scheduleEvent(this,
                Functions.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }
}
