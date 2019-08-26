package com.example.anotherdaggerapp.mvpcontent

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anotherdaggerapp.App
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.mvpcontent.model.ContentItemDb
import com.example.anotherdaggerapp.utils.cast
import com.example.anotherdaggerapp.utils.toast
import com.example.anotherdaggerapp.view.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.activity_content.*
import javax.inject.Inject

/**
 * Created by vitaly on 2019-07-30.
 */
class ContentActivity : BaseActivity<IContentView, ContentPresenter>(), IContentView {

    @Inject
    lateinit var recyclerAdapter: BaseRecyclerAdapter<ContentItemDb>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_content)

        applicationContext.cast<App>().appComponent
            .newMvpContentComponent(ContentModule(this))
            .inject(this)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = recyclerAdapter
    }

    override fun showContent(content: List<ContentItemDb>) {
        recyclerAdapter.replaceItems(content)
    }

    override fun showProgress(isVisible: Boolean) {
        progress.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(throwable: Throwable) {
        toast("ERROR")
        throwable.printStackTrace()
    }

    internal fun onItemClick(position: Int) {
        toast("ItemClicked: ${recyclerAdapter.items[position].title}")
    }
}