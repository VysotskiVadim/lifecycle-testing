package dev.vadzimv.lifecycle.testing.ui.main

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dev.vadzimv.lifecycle.testing.DialogExample
import dev.vadzimv.lifecycle.testing.FormFragment
import dev.vadzimv.lifecycle.testing.NotSavedState
import dev.vadzimv.lifecycle.testing.R

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    private val tabs = listOf(
        Tab(R.string.tab_state_loss) { NotSavedState() },
        Tab(R.string.tab_dialog_loss) { DialogExample() },
        Tab(R.string.tab_input_loss) { FormFragment() }
    )

    override fun getItem(position: Int): Fragment {
        return tabs[position].factory()
    }

    override fun getPageTitle(position: Int): CharSequence? =
        context.resources.getString(tabs[position].nameResource)

    override fun getCount(): Int = tabs.size

    private data class Tab(@StringRes val nameResource: Int, val factory: () -> Fragment)
}