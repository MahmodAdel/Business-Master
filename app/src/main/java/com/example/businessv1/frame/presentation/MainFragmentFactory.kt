package com.example.businessv1.frame.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.businessv1.frame.presentation.business.BusinessFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainFragmentFactory
@Inject
constructor(
    private val someString: String
): FragmentFactory(){

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {

            BusinessFragment::class.java.name -> {
                val fragment =
                    BusinessFragment()
                fragment
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}