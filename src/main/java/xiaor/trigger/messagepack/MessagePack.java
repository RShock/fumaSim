package xiaor.trigger.messagepack;

public record MessagePack<T extends PackageAble>(T pack) {

    public T getPack() {
        return pack;
    }
}
