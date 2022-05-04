package xiaor.logger

import com.google.gson.Gson

object Logger {
    private class Log(var type:LogType,var msg:String)

    private var logList = arrayListOf<Log>()

    fun log(type: LogType, msg: String) {
        logList.add(Log(type, msg))
    }

    fun exportHtmlLog() {
    }
}