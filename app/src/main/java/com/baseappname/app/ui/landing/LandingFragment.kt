package com.baseappname.app.ui.landing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.baseappname.app.BR
import com.baseappname.app.R
import com.baseappname.app.base.BaseFragment
import com.baseappname.app.databinding.FragmentLandingBinding
import com.baseappname.app.utils.getViewModelFactory


/**
 * @AUTHOR Amandeep Singh
 * @date 06/04/2021
 * */
class LandingFragment : BaseFragment<FragmentLandingBinding, LandingFragmentViewModel>(),
    LandingFragmentViewActor{


    //region Variables
    private val mViewModel by viewModels<LandingFragmentViewModel> { getViewModelFactory() }
    private var mBinding: FragmentLandingBinding? = null
    //endregion

    //region Base Class Methods
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_landing

    override val viewModel: LandingFragmentViewModel
        get() = mViewModel
    //endregion

    //region Life Cycle Method
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set view actor
        mViewModel.setViewActor(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set binding variables
        mBinding = viewDataBinding
        //logout from previous instance if available
        baseActivity!!.logOutFacebookInstance()

    }
    //endregion



    //region View Actor Methods
    override fun moveToLoginScreen() {

    }

    override fun moveToSignUpScreen() {
        //create bundle
      /*  val bundle = bundleOf(
            Constants.BundleKey.KEY_PARAM_APPLE_ID to "",
            Constants.BundleKey.KEY_PARAM_FACEBOOK_ID to "",
            Constants.BundleKey.KEY_PARAM_IS_SOCIAL_LOGIN to false
        )*/

    }
    //endregion

}