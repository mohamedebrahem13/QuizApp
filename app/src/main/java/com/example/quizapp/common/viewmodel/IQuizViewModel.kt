package com.example.quizapp.common.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Object that will subscribes to a MviView's [ViewAction]s, process it and emit a [ViewState] back.
 *
 * @param Action Top class of the [ViewAction] that the [IQuizViewModel] will be subscribing to.
 * @param State Top class of the [ViewState] the [IQuizViewModel] will be emitting.
 * @param Event Top class of the [ViewEvent] that the [IQuizViewModel] will be emitting.
 */
interface IQuizViewModel<Action : ViewAction, Event : ViewEvent, State : ViewState> {

    val singleEvent: Flow<Event>

    val viewState: StateFlow<State>

    /**
     * Must be called in [kotlinx.coroutines.Dispatchers.Main], otherwise it will throw an exception.
     *
     * If you want to process an intent from other [kotlinx.coroutines.CoroutineDispatcher],
     * use `withContext(Dispatchers.Main.immediate) { processIntent(intent) }`.
     */
    fun processIntent(action: Action)
}