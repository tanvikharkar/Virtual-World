public class Animation extends Action {
    private Entity entity;
    private int repeatCount;

    public Animation(Entity entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        ((AnimatedEntity)entity).nextImage();
        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity,
                    Functions.createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1,
                                    0)),
                    ((AnimatedEntity)this.entity).getAnimationPeriod());
        }
    }
}
