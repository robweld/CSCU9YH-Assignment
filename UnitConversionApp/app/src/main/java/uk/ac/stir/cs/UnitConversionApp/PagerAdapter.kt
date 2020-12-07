package uk.ac.stir.cs.UnitConversionApp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

internal class PagerAdapter (fragmentManager: FragmentManager?, private val mNumOfTabs: Int) : FragmentStatePagerAdapter (fragmentManager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    /**
     *gets the psostion that the tab is on and launches the fragment related to that position
     * @param position of the tab selected
     * @return Fragment is returned depending on the specified tab
     */
    override fun getItem(position: Int) : Fragment {
        return when (position) {
            0 -> Page1Fragment()
            1 -> Page2Fragment()
            2 -> Page3Fragment()
            else -> Fragment()
        }
    }

    /**
     * overides method with the number of tabs created in this this app
     * @return returns the number of tabs
     */
    override fun getCount(): Int {
        return mNumOfTabs
    }

}