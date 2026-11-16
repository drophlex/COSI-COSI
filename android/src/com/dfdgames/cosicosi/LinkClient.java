package com.dfdgames.cosicosi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
public class LinkClient extends WebViewClient {
  private final Activity act;
  public LinkClient(Activity a){act=a;}
  @Override public boolean shouldOverrideUrlLoading(WebView v,String url){
    if(url.startsWith("file:")) return false;
    try{act.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));}catch(Exception e){}
    return true;
  }
}
