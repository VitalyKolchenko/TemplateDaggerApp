package com.example.anotherdaggerapp.content

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anotherdaggerapp.App
import com.example.anotherdaggerapp.R
import com.example.anotherdaggerapp.Status
import com.example.anotherdaggerapp.data.ContentItem
import com.example.anotherdaggerapp.utils.cast
import com.example.anotherdaggerapp.utils.toast
import com.example.anotherdaggerapp.view.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.activity_content.*
import javax.inject.Inject

/**
 * Created by vitaly on 2019-07-30.
 */
class ContentActivity : AppCompatActivity() {

    @Inject
    lateinit var recyclerAdapter: BaseRecyclerAdapter<ContentItem>

    @Inject
    lateinit var contentViewModel: IContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationContext.cast<App>().appComponent
            .newContentComponent(ContentModule(this))
            .inject(this)

        setContentView(R.layout.activity_content)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = recyclerAdapter

        contentViewModel.content.observe(this, Observer {
            progress.visibility = View.INVISIBLE
            when (it.status) {
                Status.SUCCESS -> {
                    recyclerAdapter.replaceItems(it.data!!)
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                    list.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    recyclerAdapter.clear()
                }
            }
        })
    }

    internal fun onItemClick(position: Int) {
        toast("ItemClicked: ${recyclerAdapter.items[position].text}")
    }
}