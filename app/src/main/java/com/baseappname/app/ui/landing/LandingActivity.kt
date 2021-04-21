package com.baseappname.app.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.baseappname.app.BR
import com.baseappname.app.R
import com.baseappname.app.base.BaseActivity
import com.baseappname.app.base.BaseViewActor
import com.baseappname.app.base.FragmentNavigationBuilder
import com.baseappname.app.base.ViewModelFactory
import com.baseappname.app.databinding.ActivityLandingBinding
import com.baseappname.app.utils.getViewModelFactory


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 */
class LandingActivity : BaseActivity<ActivityLandingBinding, LandingViewModel>(),
    BaseViewActor {

    //region Variables
    private var mFactory: ViewModelFactory = getViewModelFactory()
    private var mBinding: ActivityLandingBinding? = null
    private var mViewModel: LandingViewModel? = null
    //endregion

    //region Base Class Methods
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_landing

    override val viewModel: LandingViewModel
        get() {
            mViewModel = ViewModelProviders.of(this, mFactory).get(LandingViewModel::class.java)
            return mViewModel!!
        }

    override val isMakeStatusBarTransparent: Boolean
        get() = false
    //endregion

    //region Life Cycle Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set binding
        mBinding = viewDataBinding
        //set view actor
        mViewModel!!.setViewActor(this)
        // open landing fragment default
        executeNavigation(FragmentNavigationBuilder(LandingFragment())
            .container(R.id.fl_landing_container)
            .isAddFragment(false)
            .isBackStack(false)
            .bundle(null)
            .build())


    }
    //endregion

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.findFragmentById(R.id.fl_landing_container)!!
            .onActivityResult(requestCode, resultCode, data)
    }

}
