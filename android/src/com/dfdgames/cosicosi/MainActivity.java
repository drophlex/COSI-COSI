package com.dfdgames.cosicosi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class MainActivity extends Activity {
  private WebView web;
  @Override protected void onCreate(Bundle b){
    super.onCreate(b);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    if(Build.VERSION.SDK_INT>=28){WindowManager.LayoutParams lp=getWindow().getAttributes();lp.layoutInDisplayCutoutMode=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;getWindow().setAttributes(lp);}
    web=new WebView(this);
    web.setBackgroundColor(0xFF050508);
    WebSettings s=web.getSettings();
    s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setDatabaseEnabled(true);
    s.setMediaPlaybackRequiresUserGesture(false);s.setAllowFileAccess(true);
    s.setSupportMultipleWindows(false);s.setUseWideViewPort(true);s.setLoadWithOverviewMode(true);
    s.setSupportZoom(false);s.setBuiltInZoomControls(false);s.setDisplayZoomControls(false);
    s.setTextZoom(100);
    web.setWebChromeClient(new WebChromeClient());
    web.setWebViewClient(new LinkClient(this));
    web.addJavascriptInterface(new Bridge(this),"AndroidApp");
    setContentView(web);
    hideBars();
    if(b!=null) web.restoreState(b); else web.loadUrl("file:///android_asset/index.html");
  }
  private void hideBars(){
    View d=getWindow().getDecorView();
    d.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
  }
  @Override public void onWindowFocusChanged(boolean f){super.onWindowFocusChanged(f);if(f)hideBars();}
  @Override protected void onSaveInstanceState(Bundle o){super.onSaveInstanceState(o);web.saveState(o);}
  @Override protected void onPause(){super.onPause();web.evaluateJavascript("document.dispatchEvent(new Event('visibilitychange'))",null);web.onPause();}
  @Override protected void onResume(){super.onResume();web.onResume();hideBars();}
  @Override public boolean onKeyDown(int code,KeyEvent e){
    if(code==KeyEvent.KEYCODE_BACK){
      web.evaluateJavascript("(function(){var p=document.getElementById('sPause'),t=document.getElementById('sTitle');if(t&&t.classList.contains('on')){AndroidApp.exit();return 'exit';}window.dispatchEvent(new KeyboardEvent('keydown',{key:'Escape'}));setTimeout(function(){window.dispatchEvent(new KeyboardEvent('keyup',{key:'Escape'}))},80);return 'ok';})()",
        null);
      return true;
    }
    return super.onKeyDown(code,e);
  }
}
