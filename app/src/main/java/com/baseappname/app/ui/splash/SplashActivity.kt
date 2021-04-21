package com.baseappname.app.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.baseappname.app.BR
import com.baseappname.app.R
import com.baseappname.app.base.BaseActivity
import com.baseappname.app.base.BaseViewActor
import com.baseappname.app.base.ViewModelFactory
import com.baseappname.app.databinding.ActivitySplashBinding
import com.baseappname.app.ui.landing.LandingActivity
import com.baseappname.app.utils.getViewModelFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), BaseViewActor {

    //region Variables
    private var mFactory: ViewModelFactory = getViewModelFactory()
    private var mBinding: ActivitySplashBinding? = null
    private var mViewModel: SplashViewModel? = null
    private var mHandler: Handler? = null
    //endregion

    //region Base Class Methods
    override val viewModel: SplashViewModel
        get() {
            mViewModel = ViewModelProviders.of(this, mFactory).get(SplashViewModel::class.java)
            return mViewModel!!
        }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_splash

    override val isMakeStatusBarTransparent: Boolean
        get() = true
    //endregion


    //region Life Cycle Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set binding
        mBinding = viewDataBinding
        // set view actor
        mViewModel!!.setViewActor(this)
        //set handler
        mHandler = Handler()
        getSHA1ForFacebook()
    }

    private fun getSHA1ForFacebook() {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash===============>", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        } catch (ignored: NoSuchAlgorithmException) {
        }
    }

    override fun onPause() {
        super.onPause()
        if (mHandler != null)
            mHandler!!.removeCallbacks(splashRunnable)//removing callback
    }

    override fun onResume() {
        super.onResume()
        if (mHandler != null) {
            mHandler!!.postDelayed(splashRunnable, 2000)//adding callback
        }
    }
    //endregion

    private val splashRunnable = {
        //check user session is already available
        if (mViewModel!!.getSharedPreference().accessToken.isEmpty()) {
            //USER SESSION NOT AVAILABLE
            //navigate user to landing screen
            startActivity(
                Intent(
                    this@SplashActivity,
                    LandingActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finish()
        } else {
            //USER SESSION IS AVAILABLE
            //navigate user to home screen
            startActivity(Intent(this@SplashActivity, LandingActivity::class.java))
            finish()
        }
    }

}