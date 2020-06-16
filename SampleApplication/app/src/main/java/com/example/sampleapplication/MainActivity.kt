package com.example.sampleapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.sdklibrary.IActivitySpanProvider
import com.google.android.material.snackbar.Snackbar
import com.microsoft.device.display.DisplayMask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IActivitySpanProvider {
    override fun getActivityContext(): Activity {
        return this
    }

    override fun getActivityViewHeight(): Int {
        return -1
    }

    override fun getHinge(): Rect {
        val displayMask: DisplayMask = DisplayMask.fromResourcesRect(this)
        val boundings: List<Rect> = displayMask.getBoundingRectsForRotation(getRotation())
        return if (boundings.size == 0) {
            Rect(0, 0, 0, 0)
        } else boundings[0]
    }

    /**
     * Get the device's rotation.
     *
     * @return Surface.ROTATION_0, Surface.ROTATION_90, Surface.ROTATION_180 or Surface.ROTATION_270
     */
    fun getRotation(): Int {
        val wm =
            this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var rotation = 0
        if (wm != null) {
            rotation = wm.defaultDisplay.rotation
        }
        return rotation
    }

    override fun getActivityViewWidth(): Int {
        return -1
    }

    override fun getActivityViewTopPoint(): Point {
        return Point(0,0)
    }

    override fun onResume() {
        super.onResume()
        (applicationContext as SampleApplication).setActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(Intent("sample"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
