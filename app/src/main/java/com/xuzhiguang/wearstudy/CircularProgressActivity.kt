package com.xuzhiguang.wearstudy

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import kotlinx.android.synthetic.main.activity_circular_progress.*
import android.support.wearable.activity.ConfirmationActivity
import android.content.Intent


class CircularProgressActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circular_progress)
        // Enables Always-on
        setAmbientEnabled()
        circular_progress.totalTime = 6000
        circular_progress.startTimer()
        circular_progress.setOnTimerFinishedListener {
            val intent = Intent(this, ConfirmationActivity::class.java)
            intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION)
            intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, "发送成功")
            startActivity(intent)
        }
        circular_progress.setOnClickListener {
            if (it.id === R.id.circular_progress) {
                circular_progress.stopTimer()
            }
        }
    }
}
