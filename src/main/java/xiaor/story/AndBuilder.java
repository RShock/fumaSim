package xiaor.story;

public class AndBuilder extends ThenBuilder {
    public AndBuilder(BaseBuilder builder) {
        super(builder);
        this.thenId = builder.preBuilder.thenId;
        this.trigger = builder.preBuilder.trigger;
        this.name = builder.preBuilder.name;
        this.lasted = builder.preBuilder.lasted;
    }
}
