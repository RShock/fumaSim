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

这个项目基于JAVA17，为了编译它，你需要IDEA2022以上的版本

大致上看完所有测试就知道代码是怎么跑的了。

如果有看不懂的地方欢迎来`天下布魔ma吧`找我，我是吧主。
