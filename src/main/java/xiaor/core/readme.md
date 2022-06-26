# CORE

这个包是游戏逻辑的核心部分，包含了

* charas: 存储角色类,skillParser也在这里
* damageCal: 计算伤害的逻辑
* logger: 日志
* skillBuilder: 构建技能的类，本身是一个内部DSL
* Tools: 工具类
* msgpack: 消息发送时会用到的包
* tools: 调用这个包需要的工具
* excel: excel读取与写入相关
* trigger: 事件注册中心
* common: 一些常量
* tools: 一些代码经常用的工具

Cores是一个很稳定的部分，很少发生修改