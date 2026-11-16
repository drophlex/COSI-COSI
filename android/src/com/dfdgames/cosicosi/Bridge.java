package com.dfdgames.cosicosi;
import android.app.Activity;
import android.webkit.JavascriptInterface;
public class Bridge {
  private final Activity act;
  public Bridge(Activity a){act=a;}
  @JavascriptInterface public void exit(){act.runOnUiThread(new Closer(act));}
}
