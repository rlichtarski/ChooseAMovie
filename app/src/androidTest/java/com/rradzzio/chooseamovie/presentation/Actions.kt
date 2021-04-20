package com.rradzzio.chooseamovie.presentation

import android.os.IBinder
import android.view.View
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Root
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.BoundedMatcher
import com.rradzzio.chooseamovie.presentation.ui.adapters.MovieListAdapter
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import org.hamcrest.TypeSafeMatcher

fun clickRecyclerViewFabAction(@IdRes id: Int) = object : ViewAction {

    override fun getConstraints(): Matcher<View> = any(View::class.java)

    override fun getDescription(): String = "Click on a fab inside recycler view"

    override fun perform(uiController: UiController?, view: View?) = click().perform(uiController, view?.findViewById(id))

}

fun hasItemAtPosition(position: Int, matcher: Matcher<View>) = object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

    override fun describeTo(description: Description?) {
        description?.appendText("has item at position $position ")
        matcher.describeTo(description)
    }

    override fun matchesSafely(item: RecyclerView?): Boolean {
        val viewHolder = item?.findViewHolderForAdapterPosition(position)
        return matcher.matches(viewHolder?.itemView)
    }

}

//inspired by: https://stackoverflow.com/a/63568323/8505977
class ToastMatcher : TypeSafeMatcher<Root?>() {

    override fun describeTo(description: Description?) {
        description?.appendText("is toast")
    }

    override fun matchesSafely(item: Root?): Boolean {
        val type: Int? = item?.windowLayoutParams?.get()?.type
        if(type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = item.decorView.windowToken
            val appToken: IBinder = item.decorView.applicationWindowToken
            if(windowToken === appToken) {
                return true
            }
        }
        return false
    }

}