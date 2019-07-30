package com.example.anotherdaggerapp.content

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anotherdaggerapp.App
import com.example.anotherdaggerapp.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationContext.cast<App>().appComponent
            .newContentComponent(ContentModule(this))
            .inject(this)

        setContentView(R.layout.activity_content)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = recyclerAdapter

        recyclerAdapter.replaceItems((0..50).map { ContentItem("Item $it") })
    }

    internal fun onItemClick(position: Int) {
        toast("ItemClicked: ${recyclerAdapter.items[position].text}")
    }
}