![fumaSim](https://github.com/RShock/fumaSim/actions/workflows/maven.yml/badge.svg?branch=main)

# 布魔SIM

布魔sim致力于模拟游戏《天下布魔》中角色的输出，目前已经完成了风队伤害的模拟。

游戏本身架构较为精巧，整体代码未超过2k行

因为只考虑打桩，治疗与血量等属性在目前是不存在的

# 基本架构

程序通过读取excel中的外部DSL在ParseSkill类中转化为内部DSL类以完成技能的写入。

程序基础架构为触发器模式，将游戏中所有buff与技能的结算全部由触发来完成。

