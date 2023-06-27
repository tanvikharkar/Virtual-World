import java.util.*;
import processing.core.PImage;

public class Tree extends Plant{
    public Tree(String id,
                Point position,
                List<PImage> images,
                int imageIndex,
                int actionPeriod,
                int animationPeriod,
                int health)
    {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod, health);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        if (!this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Functions.createActivityAction(this, world, imageStore), this.getActionPeriod());
        }
    }

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.getHealth() <= 0) {
            Entity stump = Functions.createStump(this.getId(),
                    this.getPosition(),
                    imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);
            world.addEntity(stump);
            ((ActiveEntity)stump).scheduleActions(scheduler, world, imageStore);
            return true;
        }
        return false;
    }
}
