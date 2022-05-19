package xiaor.logger

import com.google.gson.Gson
import xiaor.Common
import xiaor.tools.Tools
import java.io.File

object Logger {
    private class Log(var type: LogType, var msg: String)

    private var logList = arrayListOf<Log>()

    public var logLevel = LogLevel.ALL

    enum class LogLevel {
        DEBUG,      //超全日志
        ALL,        //全日志
        SIMPLE,     //简易日志
        NONE        //无日志
    }

    enum class LogColor {
        RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE, GREY, DEFAULT
    }

    private const val RESET = "\u001b[0m"

    fun printColorful(color: LogColor, str: String) {
        var ansiColor = ""
        when (color) {
            LogColor.WHITE -> ansiColor = "\u001b[0;30m"
            LogColor.RED -> ansiColor = "\u001b[0;31m"
            LogColor.GREEN -> ansiColor = "\u001b[0;32m"
            LogColor.YELLOW -> ansiColor = "\u001b[0;33m"
            LogColor.BLUE -> ansiColor = "\u001b[0;34m"
            LogColor.PURPLE -> ansiColor = "\u001b[0;35m"
            LogColor.CYAN -> ansiColor = "\u001b[0;36m"
            LogColor.GREY -> ansiColor = "\u001b[0;37m"
            LogColor.DEFAULT -> {}
        }
        println("$ansiColor$str$RESET")
    }

    fun log(type: LogType, msg: String) {
        val color = when (type) {
            LogType.回合开始 -> LogColor.BLUE
            LogType.角色行动 -> LogColor.YELLOW
            LogType.触发BUFF -> LogColor.DEFAULT
            LogType.其他 -> LogColor.GREEN
            LogType.造成伤害 -> LogColor.RED
            LogType.测试用 -> LogColor.GREY
        }
        logList.add(Log(type, msg))
        when (logLevel) {
            LogLevel.DEBUG -> printColorful(color, msg)
            LogLevel.ALL -> if (type != LogType.测试用) printColorful(color, msg)
            LogLevel.SIMPLE -> if (type == LogType.造成伤害 || type == LogType.回合开始) printColorful(color, msg)
            LogLevel.NONE -> {}
        }
    }

    fun exportHtmlLog() {
        val h0 = Export("root")
        var h1 = h0
        var h2:Export = h0
        var h3:Export
        var currentH = h0

        logList.forEach {
            it.msg = it.msg.replace(Regex("[-=]"),"")
            when (it.type) {
                LogType.回合开始 -> {
                    h1 = Export(it.msg)
                    h0.children.add(h1)
                    currentH = h1
                    h2 = h1
                    h3 = h1
                }
                LogType.角色行动 -> {
                    h2 = Export(it.msg)
                    h1.children.add(h2)
                    currentH = h2
                    h3 = h2
                }
                LogType.触发BUFF, LogType.造成伤害 -> {
                    h3 = Export(it.msg)
                    h2.children.add(h3)
                    currentH = h3
                }
                LogType.其他 -> {
                    val tmpH = Export(it.msg)
                    currentH.children.add(tmpH)
                }
                LogType.测试用 -> {
                }
            }
        }
        val resourcePath = Common.getResourcePath(this.javaClass, Common.目录型输出path)
        val s = Tools.readFileAsString(resourcePath)
        val replace = s.replace("{**data**}", Gson().toJson(h0.children))
//        val exportPath = "output/" + SimpleDateFormat("MM_dd_HH_mm_ss").format(Date()) + ".html"
        val exportPath = "output/战报.html"
        Tools.writeToFile(exportPath, replace)
        println("成功输出战斗日志至：${File(exportPath).absolutePath}")
    }

    fun reset() {
        logList.clear()
    }
}
