package com.dream;

import com.sun.jndi.toolkit.url.Uri;
import jdk.nashorn.internal.parser.JSONParser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.standard.DateTimeContext;
import org.springframework.web.context.request.WebRequest;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.naming.spi.DirectoryManager;
import javax.print.DocFlavor;
import javax.print.attribute.SetOfIntegerSyntax;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class BingPic {
    static String url0 = "http://cn.bing.com/";
    static String url = "http://cn.bing.com/HPImageArchive.aspx?format=js&idx=-1&n=30";// ConfigurationSettings.AppSettings.GetValues("url").ToString();
   // Thread threadOfSetDeskBackGround = new Thread(SetDeskBackGround);
   // Thread threadOfDownloadWallpaperToDisk = new Thread(DownloadWallpaperToDisk);
  
    public void Start() {

//
//        if (!threadOfSetDeskBackGround.IsAlive)
//        {
//            threadOfSetDeskBackGround.Start();
//
//        }
//        if (!threadOfDownloadWallpaperToDisk.IsAlive)
//        {
//            threadOfDownloadWallpaperToDisk.Start();
//
//        }
        while (true) {
            DownloadWallpaperToDisk();
            SetDeskBackGround();
        }
    }

    public static ArrayList GetUrls()
    {
        ArrayList list = new ArrayList();
        try
        {
            String postContent = GetPostInfo(url);

            JSONObject jo =  JSONObject.fromObject(postContent);
            //获取第一个json数组
            JSONArray jArray= jo.getJSONArray("images");
          //  String[] urls = new String[jArray.size()];
            for (int i=0;i<jArray.size();i++) {
                JSONObject item=jArray.getJSONObject(i);
                ImageInfor imageInfo = new ImageInfor();
                imageInfo.setUrl(url0 + item.getString("url"));
                imageInfo.setStartdate(item.getString("startdate"));
                imageInfo.setCopyright(item.getString("copyright"));
                list.add(imageInfo);
            }
        }
        catch (Exception e) {
            e.toString();
        }
        return list;
    }
    public static String GetPostInfo(String url)
    {
        StringBuffer strBuff = new StringBuffer();
        try {
            //访问http方法  
            URL httpURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpURL.openConnection();
            //这里需要注意打开连接之后，这个函数返回的是不是httpUrlconnection类型而是 URLConnection类型，可以直接强转
            conn.setRequestMethod("GET");   //设置本次请求的方式 ， 默认是GET方式， 参数要求都是大写字母
            conn.setConnectTimeout(5000*10);//设置连接超时
            conn.setDoInput(true);//是否打开输入流 ， 此方法默认为true
            conn.setDoOutput(true);//是否打开输出流， 此方法默认为false
            conn.setUseCaches(false);
            conn.connect();//表示连接
            //int code = conn.getResponseCode();//200链接成功
          //  if (code == 200) {
                InputStream is = null;
                try {
                    is = conn.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(is, "utf-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String str = null;
                    while ((str = bufferedReader.readLine()) != null) {
                        strBuff.append(str);
                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    is.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
           // }//IF
        }//TRY
        catch (Exception E){
            E.toString();
        }
        return strBuff.toString();
    }

    public static void SaveWallpaperToDisk()
    {
        //要保存的路径，路径不存在可以使用下面的Directory.CreateDirectory(path)生成文件夹 
        String ImageSavePath = "C:\\Users\\Long\\Pictures\\BingWallpaper1";  //保存墙纸路径
        if(!new File(ImageSavePath).exists())
            new File(ImageSavePath).mkdirs();
        ArrayList list = GetUrls();
        if (list.size() != 0)
        {
            try
            {
                for (int i=0;i<list.size();i++)
                {
                    ImageInfor item=(ImageInfor) list.get(i);
                    URL httpUrl = new URL(item.getUrl());
                    DataInputStream dataInputStream = new DataInputStream(httpUrl.openStream());
                    String imageName =  "Bing" + item.getStartdate().toString() + ".jpg";
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(ImageSavePath,"\\"+imageName));
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = dataInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, length);
                    }
                    dataInputStream.close();
                    fileOutputStream.close();
                }
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
   }
    public static void DownloadWallpaperToDisk()
    {
        //要保存的路径，路径不存在可以使用下面的Directory.CreateDirectory(path)生成文件夹 
        String  path = "C:\\Users\\Long\\Pictures\\BingWallpaper1";
            if (!new File(path + "\\Bing" + new SimpleDateFormat("yyyyMMdd").format(new Date())+ ".jpg").exists()) {
                SaveWallpaperToDisk();
            } else
            {

            }//else
    }

    public  static void SetDeskBackGround()
    {
        //利用系统的用户接口设置壁纸
        while (true)
        {
            String[] URL = GetFilePath();
            //Random random = new Random(DateTime.Now.Day);
            int i = new Random().nextInt( URL.length-1);
           // SystemParametersInfo(20, 1,URL[i], 1);

        }
    }

    public static String [] GetFilePath()
    {
        String ImageSavePath = "C:\\Users\\Long\\Pictures\\BingWallpaper1";//保存图片位置
        File file=new File(ImageSavePath);
        File[] tempList = file.listFiles();
        StringBuilder StringBuilder = new StringBuilder();
        for (int i=0;i<tempList.length;i++)
        {
            File file1=tempList[i];
            StringBuilder.append(file1.getName() + ",");
        }
        String[] url = StringBuilder.toString().split(",");
        return url;

    }
}
