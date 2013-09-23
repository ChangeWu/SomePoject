
package com.bobinfo.projecttimeviewers.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import com.bobinfo.projecttimeviewers.app.Constants;
import com.bobinfo.projecttimeviewers.util.MD5;

/**
 * ImageView extended class allowing easy downloading
 * of remote images
 * 
 * @author cws
 */
public class RemoteImageView extends ImageView{
	
	/*
     * Cache-related fields and methods.
     * 
     * We use a hard and a soft cache. A soft reference cache is too aggressively cleared by the
     * Garbage Collector.
     */
    
    private static final int HARD_CACHE_CAPACITY = 10;
    private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds
	
	 // Hard cache, with a fixed maximum capacity and a life duration
    private final static HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
            if (size() > HARD_CACHE_CAPACITY) {
                // Entries push-out of hard reference cache are transferred to soft reference cache
            	sSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                return true;
            } else
                return false;
        }
    };

	
	
	 // Soft cache for bitmaps kicked out of hard cache
    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache =
        new ConcurrentHashMap<String, SoftReference<Bitmap>>();
	
	/**
	 * Maximum number of unsuccesful tries of downloading an image
	 */
	private static int MAX_FAILURES = 3;
	
	private Context mContext;

	public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public RemoteImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public RemoteImageView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	/**
	 * Sharable code between constructors
	 */
	private void init(){
	}
	
	/**
	 * Remote image location
	 */
	private String mUrl;
	
	/**
	 * Currently successfully grabbed url
	 */
	private String mCurrentlyGrabbedUrl;
	
	/**
	 * Remote image download failure counter
	 */
	private int mFailure;

	/**
	 * Position of the image in the mListView
	 */
	private int mPosition;

	/**
	 * ListView containg this image
	 */
	private ListView mListView;
	
	/**
	 * Default image shown while loading or on url not found
	 */
	private Integer mDefaultImage;

	/**
	 * Loads image from remote location
	 * 
	 * @param url eg. http://random.com/abz.jpg
	 */
	public void setImageUrl(String url){
		
		if(mListView == null && mCurrentlyGrabbedUrl != null && mCurrentlyGrabbedUrl.equals(url)){
			// do nothing image is grabbed & loaded, we are golden
			return;
		}
		
		if(mUrl != null && mUrl.equals(url)){
//			mFailure++;
			//modify by cws , 2011-11-17
//			Log.e("TAG", mFailure+"--->Failed to download "+url+", falling back to default image");
			if(mFailure > MAX_FAILURES){
//				Log.e("TAG", "Failed to download "+url+", falling back to default image");
				loadDefaultImage();
				return;
			}
		} else {
			mUrl = url;
			mFailure = 0;
		}

		Bitmap bitmap = getBitmapFromCache(url); 
		if(bitmap != null){
			this.setImageBitmap(bitmap);
		}
		else {
			try{
				new DownloadTask().execute(url);
			} catch (RejectedExecutionException e) {
				// do nothing, just don't crash
			}
		}
	}
	
	/**
	 * Sets default local image shown when remote one is unavailable
	 * 
	 * @param resid
	 */
	public void setDefaultImage(Integer resid){
		mDefaultImage = resid;
	}
	
	/**
	 * Loads default image
	 */
	private void loadDefaultImage(){
		if(mDefaultImage != null)
			setImageResource(mDefaultImage);
	}
	
	/**
	 * Loads image from remote location in the ListView
	 * 
	 * @param url eg. http://random.com/abz.jpg
	 * @param position ListView position where the image is nested
	 * @param listView ListView to which this image belongs
	 */
	public void setImageUrl(String url, int position, ListView listView){
		mPosition = position;
		mListView = listView;
		setImageUrl(url);
	}

	/**
	 * Asynchronous image download task
	 * 
	 * @author cws
	 */
	class DownloadTask extends AsyncTask<String, Void, String>{
		
		private String mTaskUrl;

		@Override
		public void onPreExecute() {
			loadDefaultImage();
			super.onPreExecute();
		}

		@Override
		public String doInBackground(String... params) {

			mTaskUrl = params[0];
			Bitmap bmp = null;
			
			 try {		
					File file = new File(mContext.getCacheDir() , MD5.crypt(mTaskUrl));
					if(file.exists()){
						BitmapFactory.Options options = new Options();
						options.inSampleSize = 2;
						bmp = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
						try {
							if(bmp != null){							
								addBitmapToCache(mTaskUrl, bmp);
								Log.d("TAG", "Image cached "+mTaskUrl);
								return mTaskUrl;
							} else {
//								Log.w("TAG", "Failed to cache "+mTaskUrl);
							}
						} catch (Exception e) {
//							Log.w("TAG", "Failed to cache "+mTaskUrl);
						}
						
					}
			} catch (Exception e1) {		
					e1.printStackTrace();
			}

			//modify by cws , 2011-11-17
			mFailure++;
			
			InputStream stream = null;
//			URL imageUrl;
			AndroidHttpClient androidHttpClient = null;
			  try {
				  androidHttpClient = AndroidHttpClient.newInstance("RemoteImageView",mContext);
					HttpGet request = new HttpGet(mTaskUrl);
					HttpResponse response = androidHttpClient.execute(request);
					if(response.getStatusLine().getStatusCode() == 200){
						stream = response.getEntity().getContent();
						String fileUrl = saveBitmap(mTaskUrl, new FlushedInputStream(stream));
						BitmapFactory.Options options = new Options();
						options.inSampleSize = 2;
						bmp = BitmapFactory.decodeFile(fileUrl,options);
							try {
								if(bmp != null){							
									addBitmapToCache(mTaskUrl, bmp);
//									Log.d("TAG", "Image cached "+mTaskUrl);								
								} else {
//									Log.w("TAG", "Failed to cache "+mTaskUrl);
								}
							} catch (NullPointerException e) {
//								Log.w("TAG", "Failed to cache "+mTaskUrl);
							}						
						
//					}				
					}
				} catch (Exception e) {
//					Log.w("TAG", "Couldn't load bitmap from url: " + mTaskUrl);
				} finally {
					androidHttpClient.close();
					try {						
						if(stream != null){							
							stream.close();
						}
					} catch (IOException e) {}
				}
			
			  return mTaskUrl;
		}
		@Override
		public void onPostExecute(String url) {
			super.onPostExecute(url);
			
			// target url may change while loading
			if(!mTaskUrl.equals(mUrl))
				return;
			
			Bitmap bmp = getBitmapFromCache(url);
			if(bmp == null){
//				Log.w("TAG", "Trying again to download " + url);
				RemoteImageView.this.setImageUrl(url);
			} else {
				
				// if image belongs to a list update it only if it's visible
				if(mListView != null)
					if(mPosition < mListView.getFirstVisiblePosition() || mPosition > mListView.getLastVisiblePosition())
						return;
				
				RemoteImageView.this.setImageBitmap(bmp);
				mCurrentlyGrabbedUrl = url;
			}
		}

	};
	
	
	 /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }
    
    
    /**
     * Adds this bitmap to the cache.
     * @param bitmap The newly downloaded bitmap.
     */
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (sHardBitmapCache) {
                sHardBitmapCache.put(url, bitmap);
            }
        }
    }

    /**
     * @param url The URL of the image that will be retrieved from the cache.
     * @return The cached bitmap or null if it was not found.
     */
    private Bitmap getBitmapFromCache(String url) {
        // First try the hard reference cache
        synchronized (sHardBitmapCache) {
            final Bitmap bitmap = sHardBitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in hard cache
                // Move element to first position, so that it is removed last
                sHardBitmapCache.remove(url);
                sHardBitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        // Then try the soft reference cache
        SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
        if (bitmapReference != null) {
            final Bitmap bitmap = bitmapReference.get();
            if (bitmap != null) {
                // Bitmap found in soft cache
                return bitmap;
            } else {
                // Soft reference has been Garbage Collected
                sSoftBitmapCache.remove(url);
            }
        }

        return null;
    }
 
    
    private String saveBitmap(String url , InputStream inputStream){
    	File file = null;
    	try {
    		File fileDir = null;
    		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) &&  Environment
					.getExternalStorageDirectory()
					.exists() ) {
				fileDir = new File(
						Environment
								.getExternalStorageDirectory()
								 , Constants.FILEDIRSTRING);
			}else{
				fileDir = mContext.getCacheDir();
			}
    		if(!fileDir.exists()){
    			fileDir.mkdirs();
    		}
		    file = new File(fileDir, MD5.crypt(url));
		    FileOutputStream fileOutputStream = new FileOutputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = inputStream.read(buffer)) != -1) {
				fileOutputStream.write(buffer, 0, len);
			}
			inputStream.close();
			fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getAbsolutePath();
    }
    
    public void deleteCache(){
    	for (File file : mContext.getCacheDir().listFiles()) {			
				//Log.i("t", "fffffffffffffffff");
				if(file.exists()){
					file.delete();		
				}
		}
    }

}
