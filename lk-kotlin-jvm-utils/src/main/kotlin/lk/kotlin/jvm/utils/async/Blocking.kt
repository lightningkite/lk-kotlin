package lk.kotlin.jvm.utils.async

import java.util.concurrent.Executor

object Blocking : Executor {
    override fun execute(command: Runnable) = command.run()
}