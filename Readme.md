![fumaSim](https://github.com/RShock/fumaSim/actions/workflows/maven.yml/badge.svg?branch=main)

# 布魔SIM

`布魔sim`致力于模拟游戏《天下布魔》中角色的输出，目前已经完成了风队伤害的模拟。

代码本身架构较为精巧，整体代码未超过2k行

因为只考虑打桩，治疗与血量等属性在目前是不存在的

目前输出示例（未完成）：
![example](https://github.com/RShock/fumaSim/blob/main/example.jpg)

# 基本架构

程序通过读取excel中的外部DSL在ParseSkill类中转化为内部DSL类以完成技能的写入。

程序基础架构为触发器模式，将游戏中所有buff与技能的结算全部由触发来完成。

# 如何添加角色

如果你想添加新角色，请参考[此处](https://github.com/RShock/fumaSim/blob/sean_zou/Editorhelp.md)

# 运行

这个项目实验性的基于最新的java版本(JAVA18)，为了编译它，你需要IDEA2022以上的版本

JAVA18目前存在一个打印中文的bug，请于-VM option内添加参数`-Dfile.encoding=gbk`来解决它。

![example](https://github.gitcom/RShock/fumaSim/blob/main/IDEA无法打印中文的配置教程.jpg)

修正： 添加`-ea -Dfile.encoding="UTF-8" -Dsun.stdout.encoding="UTF-8" -Dsun.stderr.encoding="UTF-8"`
是一个更普适的做法

bug参考

https://youtrack.jetbrains.com/issue/IDEA-291006

https://stackoverflow.com/questions/71685543/java-jdk-18-in-intellij-prints-question-mark-when-i-tried-to-print-unicode-l

大致上看完所有测试就知道代码是怎么跑的了。

如果有看不懂的地方欢迎来`天下布魔ma吧`找我，我是吧主。
