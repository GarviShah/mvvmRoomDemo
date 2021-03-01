package com.example.mymvvmroomdemo.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.view.Window
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AlertDialog
import com.example.mymvvmroomdemo.R
import com.example.mymvvmroomdemo.data.APIService
import com.example.mymvvmroomdemo.data.dataSource.PostDataSource
import com.example.mymvvmroomdemo.data.database.AppDatabase
import java.util.*

object util {

    lateinit var dialog: Dialog
    var cache: Hashtable<String, Typeface>? = null


    fun getFont(context: Context, tag: String?): Typeface? {
        FontLib(context)
        return if (!cache?.containsKey(tag)!!) Typeface.DEFAULT else cache!!.get(
            tag
        )
    }

    fun getAppRepository(context: Context): PostDataSource {
        return PostDataSource.getInstance(APIService.getApiService(),AppDatabase.getInstance(context).getData())
    }

    fun FontLib(context: Context) {
        if (cache == null) {
            cache = Hashtable<String, Typeface>()
            cache!!.put(
                "310", Typeface.createFromAsset(
                    context.assets,
                    "lato_thin.ttf"
                )
            )
            cache!!.put(
                "311", Typeface.createFromAsset(
                    context.assets,
                    "lato_thinitalic.ttf"
                )
            )
            cache!!.put(
                "312", Typeface.createFromAsset(
                    context.assets,
                    "lato_light.ttf"
                )
            )
            cache!!.put(
                "313", Typeface.createFromAsset(
                    context.assets,
                    "lato_lightitalic.ttf"
                )
            )
            cache!!.put(
                "314", Typeface.createFromAsset(
                    context.assets,
                    "lato_regular.ttf"
                )
            )
            cache!!.put(
                "315", Typeface.createFromAsset(
                    context.assets,
                    "lato_italic.ttf"
                )
            )
            cache!!.put(
                "316", Typeface.createFromAsset(
                    context.assets,
                    "lato_bold.ttf"
                )
            )
            cache!!.put(
                "317", Typeface.createFromAsset(
                    context.assets,
                    "lato_bolditalic.ttf"
                )
            )
            cache!!.put(
                "318", Typeface.createFromAsset(
                    context.assets,
                    "lato_black.ttf"
                )
            )
            cache!!.put(
                "319", Typeface.createFromAsset(
                    context.assets,
                    "lato_blackitalic.ttf"
                )
            )
        }
    }

    fun checkNetwork(context: Context): Boolean {
        val connManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return networkInfo!!.isConnected
    }

    fun alertDialog(
        mContext: Context?,
        title: String?,
        message: String?,
        postiveButton: String?,
        isNagation: Boolean,
        nagativeButton: String?
    ) {
        val builder: AlertDialog.Builder
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
//        } else {
        builder = AlertDialog.Builder(mContext!!)
        //        }
        builder.setCancelable(false)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                postiveButton
            ) { dialog, which ->
                // continue with delete
            }
        if (isNagation) {
            builder.setNegativeButton(
                nagativeButton
            ) { dialog, which ->
                // do nothing
            }
        }
        //      .setIcon(android.R.drawable.ic_dialog_alert)
        builder.show()
    }

    fun showProgressDialog(context: Context?, canclable: Boolean?) {
        try {
            dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.progress_layout)
            dialog.setCancelable(canclable!!)
            Objects.requireNonNull(dialog.getWindow())
                ?.setBackgroundDrawable(
                    ColorDrawable(
                        Color.TRANSPARENT
                    )
                )

            dialog.show()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dismissProgressDialog(activity: Activity?) {
        try {
            if (dialog.isShowing() && activity != null && !activity.isFinishing) {
                dialog.dismiss()
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}