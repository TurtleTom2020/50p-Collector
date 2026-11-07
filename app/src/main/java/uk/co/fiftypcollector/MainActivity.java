package uk.co.fiftypcollector;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import org.json.JSONObject;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {
 private WebView web;
 private final ExecutorService pool=Executors.newFixedThreadPool(3);
 @Override public void onCreate(Bundle b) { super.onCreate(b);
  web=new WebView(this); setContentView(web);
  WebSettings s=web.getSettings(); s.setJavaScriptEnabled(true); s.setDomStorageEnabled(true);
  s.setAllowFileAccess(true); web.setWebViewClient(new WebViewClient());
  web.addJavascriptInterface(new ImageBridge(),"CoinImages");
  web.loadUrl("file:///android_asset/index.html");
 }
 public class ImageBridge {
  @JavascriptInterface public void search(final String id,final String query){
   if(id==null||query==null||id.length()>150||query.length()>180)return;
   pool.execute(()->{
    String result="{}";
    HttpURLConnection connection=null;
    try{
     String url="https://commons.wikimedia.org/w/api.php?action=query&generator=search&gsrnamespace=6&gsrlimit=20&gsrsearch="+URLEncoder.encode(query,"UTF-8")+"&prop=imageinfo&iiprop=url%7Cextmetadata&iiurlwidth=360&format=json";
     connection=(HttpURLConnection)new URL(url).openConnection();
     connection.setRequestProperty("User-Agent","FiftyPCollector/0.2 (personal coin catalogue; Wikimedia Commons)");
     connection.setConnectTimeout(9000);connection.setReadTimeout(12000);
     try(InputStream in=connection.getInputStream();ByteArrayOutputStream out=new ByteArrayOutputStream()){
      byte[] buf=new byte[8192];int n;while((n=in.read(buf))!=-1&&out.size()<400000)out.write(buf,0,n);
      result=out.toString("UTF-8");
     }
    }catch(Exception e){}
    finally{if(connection!=null)connection.disconnect();}
    final String payload=result;
    runOnUiThread(()->{if(web!=null)web.evaluateJavascript("window.onNativeCoinImages("+JSONObject.quote(id)+","+JSONObject.quote(payload)+")",null);});
   });
  }
 }
 @Override public void onBackPressed(){if(web.canGoBack())web.goBack();else super.onBackPressed();}
 @Override protected void onDestroy(){pool.shutdownNow();if(web!=null)web.destroy();super.onDestroy();}
}