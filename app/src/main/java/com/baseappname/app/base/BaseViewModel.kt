package com.baseappname.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baseappname.app.data.local.pref.PrefManager
import com.baseappname.app.data.remote.APICallMethods
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
abstract class BaseViewModel<N : BaseViewActor>(
    private val apiCallMethods: APICallMethods,
    private val prefManager: PrefManager
) : ViewModel() {

    //region variables
    private var mMessage: MutableLiveData<String>? = null
    private var mLoading: MutableLiveData<Boolean>? = null
    private val mCompositeDisposable = CompositeDisposable()
    private lateinit var mViewActor: WeakReference<N>
    //endregion

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()

    }

    fun getMessage(): MutableLiveData<String> {
        if (mMessage == null) mMessage = MutableLiveData()
        return mMessage!!
    }

    fun getLoading(): MutableLiveData<Boolean> {
        if (mLoading == null) mLoading = MutableLiveData()
        return mLoading!!
    }

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    /**
     * Get shared preference instance to
     * perform action on shared preference
     *
     * @return [prefManager] shared preference
     */
    fun getSharedPreference(): PrefManager {
        return prefManager
    }

    /**
     * Get view actor instance
     *
     * @return [N] type of view actor
     */
    fun getViewActor(): N {
        return mViewActor.get().let { it as N }
    }

    /**
     * Set view actor
     *
     * @param viewActor type of [N]
     */
    fun setViewActor(viewActor: N) {
        this.mViewActor = WeakReference(viewActor)
    }

}