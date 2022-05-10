package com.mbg.mbgsupport.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.mbg.mbgsupport.databinding.FragmentSvgaDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.module.common.core.manager.CoroutineManager.Companion.get
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import com.opensource.svgaplayer.*
import com.opensource.svgaplayer.SVGAParser.Companion.shareParser
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled
import java.net.MalformedURLException
import java.net.URL

class SvgaDemoFragment : MvpFragment<DemoPresenter,FragmentSvgaDemoBinding>() {

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        setLogEnabled(true)
       /* SVGASoundManager.INSTANCE.init()*/
        initView()
    }

    override fun initView() {
        mViewBinding?.run {
            shareParser().init(requireContext())
            loadAnimation(svagView)
            loadAnimationFromHttp(svagView1)
            loadAnimationHttp2(svagView2)
        }

    }

    private fun loadAnimation(view:SVGAImageView) {
        val svgaParser = shareParser() //        String name = this.randomSample();
        //asset jojo_audio.svga  cannot callback
        val name = "svga/rose.svga"
        Log.d("SVGA", "## name $name")
        svgaParser.setFrameSize(50, 50)
        svgaParser.decodeFromAssets(name, object : SVGAParser.ParseCompletion{
            override fun onComplete(videoItem: SVGAVideoEntity) {
                Log.e("zzzz", "onComplete: ")
                view.setVideoItem(videoItem)
                view.stepToFrame(0, true)
                view.startAnimation()
            }

            override fun onError() {
                Log.e("zzzz", "onComplete: ")
            }
        })
    }

    private fun loadAnimationFromHttp(view: SVGAImageView) {
        try { // new URL needs try catch.
            val svgaParser = shareParser()
            svgaParser.setFrameSize(100, 100)
            svgaParser.decodeFromURL(URL("https://github.com/yyued/SVGA-Samples/blob/master/posche.svga?raw=true"),
                object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        Log.d("##", "## FromNetworkActivity load onComplete")
                        view.setVideoItem(videoItem)
                        view.startAnimation()
                    }

                    override fun onError() {}
                })
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    private fun loadAnimationHttp2(view: SVGAImageView) {
        try { // new URL needs try catch.
            val parser = SVGAParser(context)
            parser.decodeFromURL(URL("https://github.com/yyued/SVGA-Samples/blob/master/kingset.svga?raw=true"),
                object : SVGAParser.ParseCompletion {
                    override fun onComplete(videoItem: SVGAVideoEntity) {
                        val dynamicEntity = SVGADynamicEntity()
                        dynamicEntity.setDynamicImage("https://github.com/PonyCui/resources/blob/master/svga_replace_avatar.png?raw=true",
                            "99") // Here is the KEY implementation.
                        val drawable = SVGADrawable(videoItem, dynamicEntity)
                        view.setImageDrawable(drawable)
                        view.startAnimation()
                    }

                   override fun onError() {}
                })
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        get().clear()
    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, SvgaDemoFragment::class.java, null)
        }
    }
}