package gb.com.rx

import io.reactivex.Scheduler

interface ISchedulerProvider {

    fun ui() : Scheduler
    fun io() : Scheduler
}