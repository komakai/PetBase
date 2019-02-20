package net.telepathix.petbase.ui.info

import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pet_info.*
import net.telepathix.petbase.R
import kotlinx.android.synthetic.main.toolbar_pet_info.*

const val EXTRA_PET_TITLE = "PET_TITLE"
const val EXTRA_PET_INFO_URL = "PET_INFO_URL"
const val ERROR_HTML = "file:///android_asset/error.html"

class PetInfoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_info)
        petInfoTitle.text = intent.getStringExtra(EXTRA_PET_TITLE)
        webView.webViewClient = object : WebViewClient() {
            @Suppress("OverridingDeprecatedMember", "DEPRECATION")
            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                webView.loadUrl(ERROR_HTML)
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                webView.loadUrl(ERROR_HTML)
            }
        }
        webView.loadUrl(intent.getStringExtra(EXTRA_PET_INFO_URL))
        backButton.setOnClickListener { finish() }
    }
}
