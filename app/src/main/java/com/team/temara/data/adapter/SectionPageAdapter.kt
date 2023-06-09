package com.team.temara.data.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.team.temara.ui.main.fragment.ArticleFragment
import com.team.temara.ui.main.fragment.ChatFragment
import com.team.temara.ui.main.fragment.ForumFragment
import com.team.temara.ui.main.fragment.MainFragment
import com.team.temara.ui.main.fragment.ProfileFragment

class SectionPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 5

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MainFragment()
            1 -> fragment = ArticleFragment()
            2 -> fragment = ChatFragment()
            3 -> fragment = ForumFragment()
            4 -> fragment = ProfileFragment()
        }
        return fragment as Fragment
    }
}