package com.bartex.states.presenter

import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import io.reactivex.rxjava3.core.Single

//презентер для работы с фрагментом StatesFragment,  Router для навигации
class StatesPresenter():BasePresenter() {

    companion object{
        const val TAG = "33333"
    }

    override fun getListData(): Single<List<State>> = statesRepo.getStates()

    override fun navigateToScreen(state:State) {
        router.navigateTo(Screens.DetailsScreen(state))
    }

    override fun init() {
        viewState.init()
    }

    override fun updateList() {
      viewState.updateList()
    }

//    override fun onFirstViewAttach() {
//        super.onFirstViewAttach()
//        viewState.init()
//        loadData()
//
//        //здесь присваиваем значение  слушателю щелчка по списку - ранее он был null
//        statesListPresenter.itemClickListener = { itemView ->
//            //переход на экран пользователя
//            val state =  statesListPresenter.states[itemView.pos]
//            helper.savePosition(itemView.pos) //сохраняем позицию
//            Log.d(TAG, "StatesPresenter itemClickListener state name =${state.name}")
//            router.navigateTo(Screens.DetailsScreen(state))
//
//        }
//    }

//    //грузим данные и делаем сортировку в соответствии с настройками
//    fun loadData() {
//        val isSorted = helper.isSorted()
//        val getSortCase = helper.getSortCase()
//        var f_st:List<State>?= null
//        Log.d(TAG, "StatesPresenter  loadData isSorted = $isSorted getSortCase = $getSortCase")
//        statesRepo.getStates()
//            .observeOn(Schedulers.computation())
//            .flatMap {st->
//                if(isSorted){
//                    if(getSortCase == 1){
//                         f_st = st.filter {it.population!=null}.sortedByDescending {it.population}
//                    }else if(getSortCase == 2){
//                        f_st = st.filter {it.population!=null}.sortedBy {it.population}
//                    }else if(getSortCase == 3){
//                        f_st = st.filter {it.area!=null}.sortedByDescending {it.area}
//                    }else if(getSortCase == 4){
//                        f_st = st.filter {it.area!=null}.sortedBy {it.area}
//                    }
//                    return@flatMap Single.just(f_st)
//                }else{
//                    return@flatMap Single.just(st)
//                }
//            }
//            .observeOn(mainThreadScheduler)
//            .subscribe ({states->
//                states?. let{Log.d(TAG, "StatesPresenter  loadData states.size = ${it.size}")}
//                listPresenter.states.clear()
//                states?. let{listPresenter.states.addAll(it)}
//                viewState.updateList()
//            }, {error -> Log.d(TAG, "StatesPresenter onError ${error.message}")
//            })
//    }

    fun getPosition(): Int{
        return helper.getPosition()
    }

    fun savePosition(position: Int){
        helper.savePosition(position)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}