package cn.zk520.zk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private long lastTime;
    //            网络错误后，提示网页无法打开
    private String errorHtml = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //            网络错误后，提示网页无法打开
        errorHtml = "<html><body><h1 style=\"width:100%;text-align:center;\">.</h1></body></html><html><body><h1 style=\"width:100%;text-align:center;\">.</h1></body></html><html><body><h1 style=\"width:100%;text-align:center;\">.</h1></body></html><html><html><body><h1 style=\"width:100%;text-align:center;\">No network</h1></body></html><html><body><h1 style=\"width:100%;text-align:center;\">please try again</h1></body></html><html><body><h1 style=\"width:100%;text-align:center;\">.</h1></body></html><html><body><h1 style=\"width:100%;text-align:center;\">.</h1></body></html><html><body><h1 style=\"width:100%;text-align:center;\">.</h1></body></html>";




        mWebView= (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http://www.zk520.cn/");

//        开启JS支持
        mWebView.getSettings().setJavaScriptEnabled(true);
//        开启webview缓存
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        在webview中打开链接
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//             识别URL开头，如果开头不为"http"or"https"则跳转转打开淘宝
                if(url.startsWith("http:") || url.startsWith("https:") ) {
                    view.loadUrl(url);
                    return true;
                }else{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            }




//            网络错误后，提示网页无法打开
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
                view.loadData(errorHtml, "text/html", "UTF-8");
            }




        });
    }


//             改写系统物理按揭返回逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (mWebView.canGoBack()){
//                返回上一页面
                mWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


//            双击返回键退出
    @Override
    public void onBackPressed() {
        long currentTime=System.currentTimeMillis();
        if (currentTime - lastTime<2*1000) {
            super.onBackPressed();
        }else{
            Toast.makeText(this,"双击返回退出",Toast.LENGTH_SHORT).show();
            lastTime=currentTime;
        }
    }
}





