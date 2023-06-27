import processing.core.PImage;
import java.util.List;

public class Obstacle extends AnimatedEntity{
    public Obstacle(String id,
                    Point position,
                    List<PImage> images,
                    int imageIndex,
                    int actionPeriod,
                    int animationPeriod)
    {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
    }
}
