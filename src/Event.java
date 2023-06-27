/**
 * An event is made up of an Entity that is taking an
 * Action a specified time.
 */
public final class Event
{
    private Action action;
    private long time;
    private Entity entity;

    public Event(Action action, long time, Entity entity) {
        this.action = action;
        this.time = time;
        this.entity = entity;
    }

    // Getters and setters
    public Action getAction() {
        return action;
    }

    public long getTime() {
        return time;
    }

    public Entity getEntity() {
        return entity;
    }
}