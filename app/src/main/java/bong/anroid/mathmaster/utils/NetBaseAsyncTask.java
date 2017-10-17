package bong.anroid.mathmaster.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.os.AsyncTask;
import android.util.Log;
import bong.anroid.mathmaster.R;

public class NetBaseAsyncTask extends AsyncTask<String, Integer, Boolean> {

	private static int BUFFER_SIZE = 1024;
	private int timeout = 10000;

	private static final String TAG = NetBaseAsyncTask.class.getSimpleName();
	private NetReturn result = new NetReturn();

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		return null;
	}

	public static interface NetBaseAsyncProgressListener {
		void setProgress(int progress);
	}

	public NetReturn getHTTPGETData(String _url, HashMap<String, String> params) {
		return getHTTPGETData(_url, params, null);
	}
	
	
	
	public NetReturn getHTTPGETData(String _url,
			HashMap<String, String> params,
			NetBaseAsyncProgressListener listener,
			int timeout) {
		this.timeout = timeout;
		return getHTTPGETData(_url, params,listener);
	}
	

	

	public NetReturn getHTTPGETData(String _url,
			HashMap<String, String> params,
			NetBaseAsyncProgressListener listener) {
		int responseCode = 0;

		URLConnection con = null;
		HttpURLConnection httpConnection = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream buffer = null;

		URL url;
		int countTry = 0;
		
		Log.i(TAG, "========> Http get countTry : " + countTry);

		try {
			url = new URL(_url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + _url);
		}
		Log.w("BONGTEST ", "getHTTPGETData--- ? & ?? " + _url);
		if (params != null) {
			Iterator<Entry<String, String>> iterator = params.entrySet()
					.iterator();
			//?openAgent 일경우는 ?를 붙이면 안된다는데... 누가 전부 ?를 붙이게 해놓건가?????
			//저리 해서 되는 URL도 있고 안되는 URL도 있음.
			//거의 모든 네트워 로직이 이쪽을 타고 있어 무작정 수정은 못하고 
			//일단 위의 텍스트일 경우는 &를 붙인다 더 좋은 방법 있음 고치시던가
			int questMarkIndex  = _url.toString().indexOf("?");
			//&이 들어와야 하는데 ?이 들어오는 경우가 있음.
			if(questMarkIndex >= 0)
			{
				_url += "&";	
			}
			else
			{
				_url += "?";		
			}
			
			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				_url += (param.getKey()) + "=" + param.getValue();
				if (iterator.hasNext()) {
					_url += "&";
				}
			}
		}
		Log.d("MATHMASTER", "_url : " + _url);
		while( countTry++<3 && responseCode==0) {
			try {
				CookieManager manager = new CookieManager();
				manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
				CookieHandler.setDefault(manager);

				url = new URL(_url);
				Log.d(TAG, "requested URL:" + _url);
				Log.d("BONGTEST", "requested URL:" + _url);
//				con = url.openConnection();
//				con.setConnectTimeout(timeout);
//				con.setReadTimeout(timeout);

				// add softk. 20141212. for https
				if (url.getProtocol().toLowerCase().equals("https")) {
					trustAllHosts();
					HttpsURLConnection https = (HttpsURLConnection)url.openConnection();
					https.setHostnameVerifier(DO_NOT_VERIFY);
					httpConnection = https;
				} else {
					httpConnection = (HttpURLConnection) url.openConnection();
				}
				
				httpConnection.setConnectTimeout(timeout);
				httpConnection.setReadTimeout(timeout);
				
				
//				httpConnection = (HttpURLConnection) con;
				httpConnection.setRequestMethod("GET");
				//httpConnection.setRequestProperty("Cookie", cookie);
				
				
				httpConnection.connect();
				
				// handle the response
				try {
					responseCode = httpConnection.getResponseCode();

				} catch (Exception e) {
					// EOFException 발생시 다시 시도
					Log.d(TAG, " getResponseCode exception -------------");
					e.printStackTrace();
					continue;
				}

				Log.d(TAG, "responseCode (getResponseCode) : " + responseCode);

				// status code 검사
				if (responseCode >= 400) {
					throw new IOException("Post failed with error code "
							+ responseCode);
				}
				
				

				bis = new BufferedInputStream(httpConnection.getInputStream(),
						4096);

				buffer = new ByteArrayOutputStream(4096);

				byte[] bData = new byte[BUFFER_SIZE];
				int nRead;
				float hasRead = 0;
				float contentLength = httpConnection.getContentLength();
				while ((nRead = bis.read(bData, 0, BUFFER_SIZE)) != -1) {
					hasRead += nRead;
					buffer.write(bData, 0, nRead);
					if (contentLength != -1 && listener != null) {
						int percent = (int) ((hasRead / contentLength) * 100.0f);
						listener.setProgress(percent);
					}
				}

				if (listener != null) {
					listener.setProgress(100);
				}

				buffer.flush();

				//테스트 쿠키값 조회 s
				/*List<String> cookies = httpConnection.getHeaderFields().get("set-cookie");
//				List<String> cookies = httpConnection.getHeaderFields().get("Set-Cookie");
				Log.d(TAG, " cookies " + cookies); 
				if (cookies != null) {
				    for (String cookie : cookies) {
				        Log.d(TAG, " cookie " + cookie.split(";\\s*")[0]);
				    }
				}*/
				//테스트 쿠키값 조회 e
				
				// 리다이렉션 케이스에 대한 고려가 필요
				if (responseCode >= 200 && responseCode < 400) {
					result.mErrNo = 0;
				} else {
					result.mErrNo = responseCode;
				}
				result.mReturnStr = buffer.toString();
				
				break;
				
			} catch (Exception e) {
				result.mException = e;
				result.mErrNo = responseCode;
				result.mReturnStr = null;
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (Exception e) {
					}
				}

				if (buffer != null) {
					try {
						buffer.close();
					} catch (IOException e) {
					}
				}
				con = null;
				httpConnection = null;
			}
		}
		return result;
	}

	public NetReturn getHTTPPOSTData(String _url, HashMap<String, String> params) {
		return getHTTPPOSTData(_url, params, null);
	}

	public NetReturn getHTTPPOSTData(String _url,
			HashMap<String, String> params,
			NetBaseAsyncProgressListener listener) {

		HttpURLConnection conn = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream buffer = null;
		NetReturn result = new NetReturn();

		int responseCode = 0;

		URL url;

		int countTry = 0;
		Log.i(TAG, "========> Http post countTry : " + countTry);

		try {
			url = new URL(_url);
			Log.d(TAG, "requested URL:" + _url);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + _url);
		}
		
		String body = "";
		StringBuilder bodyBuilder = new StringBuilder();
		if (params != null) {
			Iterator<Entry<String, String>> iterator = params.entrySet()
					.iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				Log.d(TAG, "param.getValue() : " + param.getValue());
				if (param.getValue().equals(""))
					continue;
				bodyBuilder.append(param.getKey()).append('=')
						.append(param.getValue());
				if (iterator.hasNext()) {
					bodyBuilder.append('&');
				}
			}

			bodyBuilder.append("&__click=0");			
			body = bodyBuilder.toString();

			if (body.contains("&&")) {
				body = bodyBuilder.toString().replace("&&", "&");
			}
		}
		
		while( countTry++<3 && responseCode==0) {
			
			Log.d(TAG, "body : " + body);
			byte[] bytes = null;
			try {
				bytes = body.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				CookieManager manager = new CookieManager();
				manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
				CookieHandler.setDefault(manager);

//				conn = (HttpURLConnection) url.openConnection();
				// add softk. 20141212. for https
				if (url.getProtocol().toLowerCase().equals("https")) {
					trustAllHosts();
					HttpsURLConnection https = (HttpsURLConnection)url.openConnection();
					https.setHostnameVerifier(DO_NOT_VERIFY);
					conn = https;
				} else {
					conn = (HttpURLConnection) url.openConnection();
				}
				
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				conn.setConnectTimeout(timeout);
				conn.setReadTimeout(timeout);
				conn.setFixedLengthStreamingMode(bytes.length);
				conn.setRequestMethod("POST");

				//conn.setRequestProperty("Cookie", cookie);
				
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				conn.connect();

				// post the request
				OutputStream out = conn.getOutputStream();
				out.write(bytes);
				out.close();

				// handle the response
				try {
					responseCode = conn.getResponseCode();

				} catch (Exception e) {
					// EOFException 발생시 다시 시도
					Log.d(TAG, " getResponseCode exception -------------");
					e.printStackTrace();
					continue;
				}

				Log.d(TAG, "status (getResponseCode) : " + responseCode);

				// status code 검사
				if (responseCode >= 400) {
					throw new IOException("Post failed with error code "
							+ responseCode);
				}

				bis = new BufferedInputStream(conn.getInputStream(), 4096);

				buffer = new ByteArrayOutputStream(4096);

				byte[] bData = new byte[BUFFER_SIZE];
				int nRead;
				float hasRead = 0;
				float contentLength = conn.getContentLength();
				while ((nRead = bis.read(bData, 0, BUFFER_SIZE)) != -1) {
					hasRead += nRead;
					buffer.write(bData, 0, nRead);
					if (contentLength != -1 && listener != null) {
						int percent = (int) ((hasRead / contentLength) * 100.0f);
						listener.setProgress(percent);
					}
				}

				if (listener != null) {
					listener.setProgress(100);
				}

				buffer.flush();

				StringBuilder sb = new StringBuilder();
				CookieStore cookieJar = manager.getCookieStore();
				List<HttpCookie> cookies = cookieJar.getCookies();
				Log.d(TAG, " cookies1 " + cookies); 
				for (HttpCookie cookie : cookies) {
					Log.d(TAG, "CookieHandler retrieved cookie: " + cookie);
					sb.append(cookie.getName() + "=" + cookie.getValue() + "; ");
				}
				//Cookie 변경 테스트 삭제 후 추가 S 테스트 S
//				String cookie1 = MainActivity.getCookie();
//				Log.d("BONGTEST", " cookie1 " + cookie1);
//				String cookie2 = "LtpaToken2=gCUXOOv3u1dOL/yVm11+bwpYgDi7uBGqicBgyq7Ir+/MBc4QsVVnBgj8xguoqmpr3P0cV+QUkDBUBYiKKOdlOG5NdsWsRrJCfDdrTjKENZU6KyjT5vyuaMb5KJe0KlJtEhRPn4UVIgrUtBeO1hlsVKheMSlPG4BMEjZInE1LFr+z6q3Ka6F1dWhSKRTgXtb/3zuKjpNyLliOoYGGDgP81KtBgwth4PgZ8bTR6ZFX+iYAAPTDmJuMV8rXYMOFhAkAqOJwmbZTCqEkUBeP2WGPP93/H2K6v1hH9rGD/xXOI++VdOWaLB2RX5no+uVJIVVHWhBXIVjWlxHESNGbtbTTMYArwJbifD7fME8iDeruuDj3R0aP1UhElt1HXFKNaChR; LtpaToken=ylRlQXl5rfydL+e0FOwNjZOzzPGqGInOa83A4PgPXDIiUKF+aP/4BA5rA74wbLU6zYKrwFVOoGQ3AW2bXZTvW3EfHDEE1RGrhb9mU9nydgJkbpe60P+ZRtkF61qO8tzwThmpOTzXkl1S/UbcJWBRgvVzSev/JpKI7/RSqoYNcH6W/4GEccnU/O/zjhdphP+ei5Ss97wK/yaLD4bwNdALXjf4WTj9a3oX34LUlKZrsazqqt0qinYnXoh/bTs4knK/1c6MQy97PTS6AVZypx0Wor44EaXyNKIRAOH6KfT8TMWsKO1Xt44S4jTKuydJM7mFUznIy1ntjXDMHwfBQv3BWQ==; MobileInfo=CC=1&HS=amorepacific.com&MS=APHQMAIL04/APG&MP=aphqmailv02/mail/ko2.nsf&EL=1&AL=2&HT=1&DF=yyyy%2FMM%2Fdd&GMT=9&LT=9&EX=; ck_PageId=listPage; ck_Docinfo=none";
//				//MainActivity.cookieManager.removeAllCookie();
//				MainActivity.setCookie(cookie2);	
//				cookie1 = MainActivity.getCookie();
//				Log.d("BONGTEST", " cookie1 " + cookie1);
				//Cookie 변경 테스트 삭제 후 추가 E 테스트 E
				
				//테스트 쿠키값 조회 s
				/*List<String> cookies2 = conn.getHeaderFields().get("set-cookie");
//				List<String> cookies2 = conn.getHeaderFields().get("Set-Cookie");
				Log.d(TAG, " cookies2 " + cookies2); 
				if (cookies2 != null) {
				    for (String cookie : cookies2) {
				        Log.d(TAG, " cookie " + cookie.split(";\\s*")[0]);
				    }
				}*/
				//테스트 쿠키값 조회 e
				
				
				
				// 리다이렉션 케이스에 대한 고려가 필요
				if (responseCode >= 200 && responseCode < 400) {
					result.mErrNo = 0;
				} else {
					result.mErrNo = responseCode;
				}
				result.mReturnStr = buffer.toString();
				break;

			} catch (Exception e) {

				result.mException = e;
				result.mErrNo = responseCode;
				result.mReturnStr = null;

			} finally {
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			}
		}
		return result;
	}
	
	

	/////////////////////////////////////////////////////////////////////////////
	// add softk. 20141212. for https. 
	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return new java.security.cert.X509Certificate[] {};
		}
	
		@Override
		public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub    
		}
	
		@Override
		public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
			}
		}};
	
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	/////////////////////////////////////////////////////////////////////////////


	// comment softk. 20130527. 오류가 없는 경우에도 getErrString()함수를 호출하면
	// 오류로 설정되는 이상한 class 임.
	// 향후 개선필요
	public class NetReturn {
		public String mReturnStr = "";
		public Exception mException = null; 
		public int mErrNo = 0;

		public int getErrString() {
			if (mException == null) {
				mErrNo = 999;
				return R.string.alert_error_occurred_while_connection_to_server_try_later;
			} else if (mException instanceof SocketTimeoutException
					|| mException instanceof UnknownHostException
					|| mException instanceof SocketException
					|| mException instanceof ConnectException) {
				mException.printStackTrace();

				return R.string.alert_unable_to_connect_to_network_try_again;
			} else {
				return R.string.alert_error_occurred_while_connection_to_server_try_later;
			}
		}
	}
}
