package com.baseappname.app.base

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.messaging.FirebaseMessaging
import com.baseappname.app.R
import com.baseappname.app.utils.Constants
import com.baseappname.app.utils.easyPrinter
import com.baseappname.app.utils.showToast


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<out BaseViewActor>> :
    AppCompatActivity() {
    lateinit var viewDataBinding: T
    var mBaseViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    abstract val isMakeStatusBarTransparent: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        if (Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT) {
            val window = window
            if (isMakeStatusBarTransparent) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorTransparent)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                window.statusBarColor = ContextCompat.getColor(
                    this,
                    R.color.colorPrimaryDark
                )
            }
        }
        // observeBaseValues
        observeCommonData()
    }

    /**
     *Binding Operations and Layout inflation
     *
     * Perform basic binding operations after inflating content view
     * set viewModel variable to layout
     */
    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mBaseViewModel = if (mBaseViewModel == null) viewModel else mBaseViewModel
        viewDataBinding.setVariable(bindingVariable, mBaseViewModel)
        viewDataBinding.executePendingBindings()
    }


    /**
     * Observe common data
     *
     * Attach observers for listening common data.
     * like messages loader
     */
    private fun observeCommonData() {
        // observe general error
        viewModel.getMessage().observe(this, Observer {
            if (null != it) {
                showToast(message = it)
            }
        })
    }


    /**
     * One step back
     *
     * Function to remove fragment from current activity
     * in order to move user to previous screen
     */
    fun oneStepBack() {
        val fts = supportFragmentManager.beginTransaction()
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount >= 1) {
            fragmentManager.popBackStackImmediate()
            fts.commit()
        } else {
            supportFinishAfterTransition()
        }
    }


    /**
     * Get google map direction uri
     *
     * Create simple string uri to open desired location on google map App in phone
     *
     * @param lat latitude of location
     * @param lng longitude of location
     * @return
     */
    fun getGoogleMapDirectionUri(lat: Double, lng: Double): String {
        return "http://maps.google.com/maps?daddr=${lat},${lng}"
    }

    /**
     * CLear notification on user exit from application
     *
     * Function to clear all notification currently active in system tray
     */
    private fun clearNotifications() {
        val notificationManager =
            this@BaseActivity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()

    }


    /**
     * Clear user data
     *
     * clear stored user data from shared preference
     */
    private fun clearUserData() {
        viewModel.getSharedPreference().clearPrefData()
    }

    /**
     * Clear back stack
     * pop all fragment with help of for loop
     *
     */
    protected fun clearBackStack() {
        for (i in 0..supportFragmentManager.fragments.size) {
            supportFragmentManager.popBackStack()
        }
    }

    /**
     * Get current fragment opened only on home activity container
     *
     * @return current fragment from fl_landing_container
     */
    protected fun getCurrentFragmentLanding(): Fragment? {
        val frag = supportFragmentManager.findFragmentById(R.id.fl_landing_container)
        return if (frag == null) return null else frag as BaseFragment<ViewDataBinding, BaseViewModel<BaseViewActor>>
    }

    /**
     * Unsubscribe from topic
     *
     * Unsubscribe from firebase topic so that user can not get notification
     */
    fun unsubscribeFromTopic(patientId: Int) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(getFCMTopic(patientId))
    }

    /**
     * Unsubscribe from news topic
     *
     */
    fun unsubscribeFromNewsTopic(patientId: Int) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(getNewsFCMTopic(patientId))
    }

    /**
     * Subscribe from topic
     *
     * Subscribe to firebase topic so that user can get notification
     */
    fun subscribeToTopic(patientId: Int) {
        FirebaseMessaging.getInstance().subscribeToTopic(getFCMTopic(patientId))
    }

    /**
     * Subscribe to news topic
     *
     */
    fun subscribeToNewsTopic(patientId: Int) {
        FirebaseMessaging.getInstance().subscribeToTopic(getNewsFCMTopic(patientId))
    }

    /**
     * Create FCM topic
     *
     * Create FCM topic with help for app nem as prefix and user id as suffix to string
     * @return the FCM topic
     */
    private fun getFCMTopic(patientId: Int): String {
        easyPrinter(
            "${Constants.FCM_TOPIC_PREFIX}${patientId}",
            "FCM TOKEN"
        )
        return "${Constants.FCM_TOPIC_PREFIX}${patientId}"
    }

    /**
     * Get news FCM topic
     *
     * @return the news fcm topic
     */

    private fun getNewsFCMTopic(patientId: Int): String {
        return Constants.FCM_TOPIC_NEWS
    }


    /**
     * Log out facebook instance
     *
     *  Log out user from app's facebook social login
     */
    fun logOutFacebookInstance() {
        try {
            /*FacebookLoginController.logOutFacebook()*/
        } catch (e: Exception) {

        }
    }

    /**
     * Execute navigation
     *
     * Execute fragment navigation based on [fragmentNavigationBuilder] options.
     * [fragmentNavigationBuilder] contains several options like fragment name,
     * Container name, add or replace fragment.
     *
     * @param fragmentNavigationBuilder
     */
    fun executeNavigation(fragmentNavigationBuilder: FragmentNavigationBuilder) {
        val currentFragment: Fragment = fragmentNavigationBuilder.fragment
        val fts = supportFragmentManager.beginTransaction()
        currentFragment.arguments = fragmentNavigationBuilder.bundle
        if (fragmentNavigationBuilder.isAddFragment)
            fts.add(
                fragmentNavigationBuilder.container!!,
                currentFragment,
                currentFragment.javaClass.simpleName
            )
        else
            fts.replace(
                fragmentNavigationBuilder.container!!,
                currentFragment,
                currentFragment.javaClass.simpleName
            )

        if (fragmentNavigationBuilder.isBackStack)
            fts.addToBackStack(currentFragment.javaClass.simpleName)
        fts.commit()
    }
}

data class FragmentNavigationBuilder(
    var fragment: Fragment,
    var container: Int? = null,
    var isAddFragment: Boolean = false,
    var isBackStack: Boolean = false,
    var bundle: Bundle? = null
) {
    fun container(container: Int) = apply { this.container = container }
    fun isAddFragment(isAddFragment: Boolean) = apply { this.isAddFragment = isAddFragment }
    fun isBackStack(isBackStack: Boolean) = apply { this.isBackStack = isBackStack }
    fun bundle(bundle: Bundle?) = apply { this.bundle = bundle }
    fun build() = FragmentNavigationBuilder(fragment, container, isAddFragment, isBackStack, bundle)
}



