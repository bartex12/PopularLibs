package com.example.popularlibs_homrworks

import moxy.MvpView
import moxy.viewstate.strategy.SingleStateStrategy
import moxy.viewstate.strategy.StateStrategyType

//Так как всё, что будет на экране — просто список, интерфейс содержит всего два метода —
// init() для первичной инициализации списка, который мы будем вызывать при присоединении
// View к Presenter и updateList() для обновления содержимого списка.
@StateStrategyType(SingleStateStrategy::class)
interface MainView: MvpView {
}