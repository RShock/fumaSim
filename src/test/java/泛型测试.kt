import org.junit.jupiter.api.Test
import java.util.function.Consumer

class 泛型测试 {
    class Observer<T>(var acceptType: String, var doSth: Consumer<T>) {
        fun doSth(type: T) {
            doSth.accept(type)
        }
    }

    class A类消息 : 消息 {
        var fruitName = "苹果"
    }

    class B类消息 : 消息 {
        var animalName = "鸟"
    }

    interface 消息
    class ObRegister {
        var watchers: MutableList<Observer<*>> = ArrayList()
        fun acceptMsg(msg: 消息) {
            watchers.forEach(Consumer { observer: Observer<*> ->
                if (msg.javaClass.name.contains(observer.acceptType)) {
//                    observer.doSth(msg)
                }
            })
        }

        fun registerObserver(watcher: Observer<*>) {
            watchers.add(watcher)
        }
    }

    @Test
    fun should_pass() {
        val a = A类消息()
        val b = B类消息()
        val msgRegister = ObRegister()
        val aObserver = Observer("A类消息") { a1: A类消息 -> println("水果盘里的水果是" + a1.fruitName) }
        val bObserver = Observer("B类消息") { b1: B类消息 -> println("动物园里有" + b1.animalName) }
        msgRegister.registerObserver(aObserver)
        msgRegister.registerObserver(bObserver)
        msgRegister.acceptMsg(b)
        msgRegister.acceptMsg(a)
    }
}