//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.eaibot.running.test;

import android.text.TextUtils;
import android.util.Base64;


import org.java_websocket.WebSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZWebSocket {
//    public static final String TAG = ZWebSocket.class.getSimpleName();
//    private String a;
//    private URI b;
//    private int c;
//    private static WebSocketFactory d = (new WebSocketFactory()).setConnectionTimeout(0);
//    private WebSocket e;
//    private OnWebSocketListener f;
//
//    public void addListener(OnWebSocketListener var1) {
//        this.f = var1;
//    }
//
//    public ZWebSocket() {
//        this("192.168.31.200");
//    }
//
//    public ZWebSocket(String var1) {
//        this.a = var1;
//        this.initWebSocket();
//    }
//
//    public WebSocket getWebSocket() {
//        return this.e;
//    }
//
//    public void initWebSocket() {
//        try {
//            this.b = new URI(this.a);
//        } catch (URISyntaxException var3) {
//            var3.printStackTrace();
//        }
//
//        this.e = d.createSocket(this.b);
//        this.e.setExtended(true);
//        this.e.setMissingCloseFrameAllowed(false);
//        this.e.addListener(new WebSocketListener() {
//            @Override
//            public void onStateChanged(WebSocket var1, WebSocketState var2) {
//            }
//
//            @Override
//            public void onConnected(WebSocket var1, Map<String, List<String>> var2) {
//                ZWebSocket.this.c = 1;
//                ZWebSocket.this.f.onOpened();
//            }
//
//            @Override
//            public void onConnectError(WebSocket var1, WebSocketException var2) {
//                ZWebSocket.this.c = -1;
//                ZWebSocket.this.f.onError(var2);
//            }
//
//            @Override
//            public void onDisconnected(WebSocket var1, WebSocketFrame var2, WebSocketFrame var3, boolean var4) {
//                ZWebSocket.this.c = 0;
//                ZWebSocket.this.f.onClosed();
//            }
//
//            @Override
//            public void onFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onContinuationFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onTextFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onBinaryFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onCloseFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onPingFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onPongFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onTextMessage(WebSocket var1, String var2) {
////                JSONObject var3 = null;
////                try {
////                    var3 = new JSONObject(var2);
////                    ZWebSocket.this.f.onTextMessage(var3.getString("type"), var3.getString("result"));
////                } catch (JSONException e1) {
////                    e1.printStackTrace();
////                }
//                String var3 = "";
//
//                try {
//                    ByteArrayInputStream var4 = new ByteArrayInputStream(Base64.decode(var2, 0));
//                    GZIPInputStream var5 = new GZIPInputStream(var4);
//                    BufferedReader var6 = null;
//                    var6 = new BufferedReader(new InputStreamReader(var5, "utf-8"));
//
//                    for(String var7 = ""; (var7 = var6.readLine()) != null; var3 = var3 + var7) {
//                        ;
//                    }
//                } catch (Exception var8) {
//                    var8.printStackTrace();
//                }
//
//                JSONObject var9 = null;
//                try {
//                    var9 = new JSONObject(var3);
//                    ZWebSocket.this.f.onTextMessage(var9.getString("type"), var9.getString("result"));
//                } catch (JSONException e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onBinaryMessage(WebSocket var1, byte[] var2) {
//                String var3 = "";
//
//                try {
//                    ByteArrayInputStream var4 = new ByteArrayInputStream(Base64.decode(var2, 0));
//                    GZIPInputStream var5 = new GZIPInputStream(var4);
//                    BufferedReader var6 = null;
//                    var6 = new BufferedReader(new InputStreamReader(var5, "utf-8"));
//
//                    for(String var7 = ""; (var7 = var6.readLine()) != null; var3 = var3 + var7) {
//                        ;
//                    }
//                } catch (Exception var8) {
//                    var8.printStackTrace();
//                }
//
//                JSONObject var9 = null;
//                try {
//                    var9 = new JSONObject(var3);
//                    ZWebSocket.this.f.onTextMessage(var9.getString("type"), var9.getString("result"));
//                } catch (JSONException e1) {
//                    e1.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onSendingFrame(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onFrameSent(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onFrameUnsent(WebSocket var1, WebSocketFrame var2) {
//            }
//
//            @Override
//            public void onThreadCreated(WebSocket var1, ThreadType var2, Thread var3) {
//            }
//
//            @Override
//            public void onThreadStarted(WebSocket var1, ThreadType var2, Thread var3) {
//            }
//
//            @Override
//            public void onThreadStopping(WebSocket var1, ThreadType var2, Thread var3) {
//            }
//
//            @Override
//            public void onError(WebSocket var1, WebSocketException var2) {
//                ZWebSocket.this.c = -1;
//                ZWebSocket.this.f.onError(var2);
//            }
//
//            @Override
//            public void onFrameError(WebSocket var1, WebSocketException var2, WebSocketFrame var3) {
//            }
//
//            @Override
//            public void onMessageError(WebSocket var1, WebSocketException var2, List<WebSocketFrame> var3) {
//            }
//
//            @Override
//            public void onMessageDecompressionError(WebSocket var1, WebSocketException var2, byte[] var3) {
//            }
//
//            @Override
//            public void onTextMessageError(WebSocket var1, WebSocketException var2, byte[] var3) {
//            }
//
//            @Override
//            public void onSendError(WebSocket var1, WebSocketException var2, WebSocketFrame var3) {
//            }
//
//            @Override
//            public void onUnexpectedError(WebSocket var1, WebSocketException var2) {
//            }
//
//            @Override
//            public void handleCallbackError(WebSocket var1, Throwable var2) {
//            }
//
//            @Override
//            public void onSendingHandshake(WebSocket var1, String var2, List<String[]> var3) {
//            }
//        });
//
//    }
//
//    public void connect() throws WebSocketException {
//        if(null != this.e) {
//            this.e.connect();
//        }
//
//    }
//
//    public void connectAsynchronously() {
//        if(null != this.e) {
//            this.e.connectAsynchronously();
//        }
//
//    }
//
//    public void close() {
//        if(null != this.e) {
//            this.e.disconnect();
//        }
//
//    }
//
//    public void sendText(String var1) {
//        if(null != this.e && this.c == 1) {
//            this.e.sendText(var1);
//        }
//
//    }
//
//    public void sendBinary(String var1) {
//        if(null != this.e && this.c == 1) {
//            if(TextUtils.isEmpty(var1)) {
//                return;
//            }
//
//            try {
//                ByteArrayOutputStream var2 = new ByteArrayOutputStream();
//                GZIPOutputStream var3 = new GZIPOutputStream(var2);
//                var3.write(var1.getBytes());
//                var3.close();
//                byte[] var4 = var2.toByteArray();
//                var2.flush();
//                var2.close();
//                this.e.sendBinary(Base64.encode(var4, 0));
//            } catch (UnsupportedEncodingException var5) {
//                var5.printStackTrace();
//            } catch (IOException var6) {
//                var6.printStackTrace();
//            }
//        }
//
//    }
}
