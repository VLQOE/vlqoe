
package org.videolan.vlc;

import org.videolan.libvlc.LibVLC;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import java.io.DataOutputStream;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.File;



public class PowerConnectionReceiver extends BroadcastReceiver {
    FileOutputStream fos;

    @Override
    public void onReceive(Context context, Intent intent) { 
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                            status == BatteryManager.BATTERY_STATUS_FULL;
    
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        writeToFile("vlc_battery",isCharging+",USB,"+usbCharge+",AC,"+acCharge+","+getClock()+"\n");   //write MOS to file;            }

    }
 public void writeToFile(String filename, String data){
    if(!data.contentEquals("null")){
    try{
        File file = new File("/sdcard/vlcplayer/"+filename);
        boolean exist = file.createNewFile();
        if(!exist){
           fos=new FileOutputStream(file,true);
           fos.write(data.getBytes());
           fos.close();
        }
    }
    catch(FileNotFoundException ex){
           ex.printStackTrace();
    }
    catch(IOException e){
           e.printStackTrace();
    }
    }
    }
  public String getClock(){
        //Date now=new Date();
        //SimpleDateFormat dateFormatter=new SimpleDateFormat("H:m:s");
        Long times=System.currentTimeMillis();
        String timeString = String.valueOf(times);
        String parsedTimeString = timeString.substring(0,timeString.length()-3)+"."+timeString.substring(timeString.length()-3,timeString.length());
        return parsedTimeString;//dateFormatter.format(now).toString();
}


}
