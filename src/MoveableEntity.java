import processing.core.PImage;
import java.util.List;

abstract class MoveableEntity extends AnimatedEntity{
    public MoveableEntity(String id,
                          Point position,
                          List<PImage> images,
                          int imageIndex,
                          int actionPeriod,
                          int animationPeriod){
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    abstract Point nextPosition(WorldModel world, Point destPos);

    abstract boolean moveTo(WorldModel worldModel, Entity target, EventScheduler scheduler);
}
