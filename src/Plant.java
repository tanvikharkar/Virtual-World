import processing.core.PImage;
import java.util.List;

abstract class Plant extends AnimatedEntity {
    private int health;

    public Plant(String id,
                 Point position,
                 List<PImage> images,
                 int imageIndex,
                 int actionPeriod,
                 int animationPeriod,
                 int health) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(){
        this.health++;
    }

    public void decreaseHealth(){
        this.health--;
    }

    abstract boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}