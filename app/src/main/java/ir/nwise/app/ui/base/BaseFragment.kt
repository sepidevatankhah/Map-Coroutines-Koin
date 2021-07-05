package ir.nwise.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<ViewState, ViewModel : BaseViewModel<ViewState>, Binding : ViewDataBinding> :
    Fragment() {
    protected lateinit var viewModel: ViewModel
    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreateViewCompleted(savedInstanceState)
        setHasOptionsMenu(true)
        if (callObserverFromOnViewCreated)
            startObserving()
    }

    /**
     * return your layout here
     */
    @LayoutRes
    abstract fun getLayout(): Int

    /**
     * add your code here every thing injected and view
     */
    abstract fun onCreateViewCompleted(savedInstanceState: Bundle?)

    /**
     * add all functionality of page after rendering the view state
     */
    abstract fun render(state: ViewState)

    protected fun startObserving() {
        viewModel.liveData.observe(viewLifecycleOwner, { state ->
            render(state)
        })
    }

    open var callObserverFromOnViewCreated: Boolean = true

}