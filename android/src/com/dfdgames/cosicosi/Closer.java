package com.dfdgames.cosicosi;
import android.app.Activity;
public class Closer implements Runnable {
  private final Activity act;
  public Closer(Activity a){act=a;}
  public void run(){act.finish();}
}
