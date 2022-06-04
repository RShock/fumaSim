![fumaSim](https://github.com/RShock/fumaSim/actions/workflows/maven.yml/badge.svg?branch=main)

# 布魔SIM

`布魔sim`用于模拟游戏《天下布魔》中角色的输出，目前已经完成了以下角色伤害的模拟，

风队 梦露 春忍 剑圣 凯萨 圣女 圣诞 兔姬 巴尔

重要的，还未完成的角色：花巴 店长 煌星 
重要的，还未完成的机制：触发技增伤 dot 异常状态

模拟精度为<b>完全精确</b>，就算是游戏bug也完全模拟。

代码本身架构较为精巧，整体代码未超过<del>2k</del>3k行

因为只考虑打桩，目前来说，异常状态，大招CD等均未支持。

目前输出示例：
![满配队测试](https://github.com/RShock/fumaSim/blob/main/示例图片/满配队测试.png)
![单个队伍测试](https://github.com/RShock/fumaSim/blob/main/示例图片/单个队伍测试.jpg)

# 基本架构

程序通过读取excel中的外部DSL在ParseSkill类中转化为内部DSL类以完成技能的写入。

程序基础架构为触发器模式，将游戏中所有buff与技能的结算全部由触发来完成。

所有技能，角色数据由excel管理。

![单个队伍测试](https://github.com/RShock/fumaSim/blob/main/示例图片/通过填写excel添加角色技能.png)

# 如何添加角色

如果你想添加新角色，请参考[此处](https://github.com/RShock/fumaSim/blob/sean_zou/Editorhelp.md)

# 运行

这个项目基于JAVA17，为了编译它，你需要IDEA2022以上的版本

大致上看完所有测试就知道代码是怎么跑的了。

如果有看不懂的地方欢迎来`天下布魔ma吧`找我，我是吧主。
