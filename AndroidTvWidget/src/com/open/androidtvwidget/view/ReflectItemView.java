package com.open.androidtvwidget.view;

import com.open.androidtvwidget.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v4.util.LruCache;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ReflectItemView extends FrameLayout {

	private static final String TAG = "ReflectItemView";

	private static final int DEFUALT_REFHEIGHT = 80;
	private final static String CACHE_REFLECT_BITMAP_KEY = "reflectBitmap";
	private final static String CACHE_ROUND_BITMAP_KEY = "roundBitmap";

	private Paint mRefPaint = null;
	private View mContentView;
	private int mRefHeight = DEFUALT_REFHEIGHT;

	private LruCache<String, Bitmap> mMemoryCache;

	private boolean isDrawRound = false;
	private boolean mIsReflection = true;

	private float mRadius = 20;
	private float mTopLeftRadius = mRadius;
	private float mTopRightRadius = mRadius;
	private float mBottomLeftRadius = mRadius;
	private float mBottomRightRadius = mRadius;

	private Path mPath;
	private Paint mPaint;

	public ReflectItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public ReflectItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public ReflectItemView(Context context) {
		super(context);
		init(context, null);
	}

	private void init(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray tArray = context.obtainStyledAttributes(attrs, R.styleable.reflectItemView);// 获取配置属性
			boolean isReflect = tArray.getBoolean(R.styleable.reflectItemView_isReflect, false);
			mRefHeight = (int) tArray.getDimension(R.styleable.reflectItemView_reflect_height, DEFUALT_REFHEIGHT);
			setReflection(isReflect);
		}
		//
		if (mRefPaint == null) {
			mRefPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
			// 倒影渐变.
			mRefPaint.setShader(
					new LinearGradient(0, 0, 0, mRefHeight, new int[] { 0x77000000, 0x66AAAAAA, 0x0500000, 0x00000000 },
							new float[] { 0.0f, 0.1f, 0.9f, 1.0f }, Shader.TileMode.CLAMP));
			mRefPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
		}
		setClipChildren(false);
		setClipToPadding(false);
		setWillNotDraw(false);
		//
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// 重写此方法来衡量每张图片的大小，默认返回图片数量。
				return bitmap.getByteCount() / 1024;
			}
		};
		//
		mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		mPaint.setAntiAlias(true);
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
		mPath = new Path();
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			mContentView = getChildAt(0);
		}
	}

	@Override
	public void addView(View child) {
		if (mContentView == null)
			mContentView = child;
		super.addView(child);
	}

	public View getContentView() {
		return mContentView;
	}

	/**
	 * 设置是否倒影.
	 */
	public void setReflection(boolean ref) {
		mIsReflection = ref;
		invalidate();
	}

	public boolean isReflection() {
		return this.mIsReflection;
	}

	/**
	 * 倒影高度.
	 */
	public void setRefHeight(int height) {
		this.mRefHeight = height;
	}

	public int getRefHeight() {
		return this.mRefHeight;
	}

	public Bitmap getReflectBitmap() {
		return getBitmapFromMemCache(CACHE_REFLECT_BITMAP_KEY);
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}

	@Override
	public void draw(Canvas canvas) {
		if (!isDrawRound)
			super.draw(canvas);
		// 绘制圆角矩形控件.
		drawRoundBitmap(canvas);
		// 绘制倒影.
		drawRefleBitmap(canvas);
	}

	/**
	 * 绘制圆角控件.
	 */
	private void drawRoundBitmap(Canvas canvas) {
		if (isDrawRound) {
			int width = getWidth();
			int height = getHeight();
			// 从缓存中取图片.
			Bitmap roundBitmap = getBitmapFromMemCache(CACHE_ROUND_BITMAP_KEY);
			if (roundBitmap == null) {
				roundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				addBitmapToMemoryCache(CACHE_ROUND_BITMAP_KEY, roundBitmap);
			}
			Canvas canvas2 = new Canvas(roundBitmap);
			int count = canvas.save();
			int count2 = canvas2.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
			addRoundPath(width, height);
			super.draw(canvas2);
			canvas2.drawPath(mPath, mPaint);
			canvas.drawBitmap(roundBitmap, 0, 0, null);
			canvas2.restoreToCount(count2);
			canvas.restoreToCount(count);
		}
	}

	/**
	 * 绘制倒影.
	 */
	private void drawRefleBitmap(Canvas canvas) {
		if (mIsReflection) {
			// 创建一个画布.
			if (mContentView != null) {
				Bitmap reflectBitmap = getBitmapFromMemCache(CACHE_REFLECT_BITMAP_KEY);
				if (reflectBitmap == null) {
					reflectBitmap = Bitmap.createBitmap(mContentView.getWidth(), mRefHeight, Bitmap.Config.ARGB_8888);
					addBitmapToMemoryCache(CACHE_REFLECT_BITMAP_KEY, reflectBitmap);
				}
				Canvas reflectCanvas = new Canvas(reflectBitmap);
				// 绘制倒影.
				drawReflection(reflectCanvas);
				canvas.save();
				int dy = mContentView.getBottom();
				int dx = mContentView.getLeft();
				canvas.translate(dx, dy);
				canvas.drawBitmap(reflectBitmap, 0, 0, null);
				canvas.restore();
			}
		}
	}

	/**
	 * 绘制倒影.
	 */
	public void drawReflection(Canvas canvas) {
		canvas.save();
		canvas.clipRect(0, 0, mContentView.getWidth(), mRefHeight);
		canvas.save();
		canvas.scale(1, -1);
		canvas.translate(0, -mContentView.getHeight());
		super.draw(canvas);
		canvas.restore();
		canvas.drawRect(0, 0, mContentView.getWidth(), mRefHeight, mRefPaint);
		canvas.restore();
	}

	private void addRoundPath(int width, int height) {

		// topleft path
		if (mTopLeftRadius > 0) {
			Path topLeftPath = new Path();
			topLeftPath.moveTo(0, mTopLeftRadius);
			topLeftPath.lineTo(0, 0);
			topLeftPath.lineTo(mTopLeftRadius, 0);
			RectF arc = new RectF(0, 0, mTopLeftRadius * 2, mTopLeftRadius * 2);
			topLeftPath.arcTo(arc, -90, -90);
			topLeftPath.close();
			mPath.addPath(topLeftPath);
		}

		// topRight path

		if (mTopRightRadius > 0) {
			Path topRightPath = new Path();
			topRightPath.moveTo(width, mTopRightRadius);
			topRightPath.lineTo(width, 0);
			topRightPath.lineTo(width - mTopRightRadius, 0);
			RectF arc = new RectF(width - mTopRightRadius * 2, 0, width, mTopRightRadius * 2);
			topRightPath.arcTo(arc, -90, 90);
			topRightPath.close();
			mPath.addPath(topRightPath);
		}

		// bottomLeft path
		if (mBottomLeftRadius > 0) {
			Path bottomLeftPath = new Path();
			bottomLeftPath.moveTo(0, height - mBottomLeftRadius);
			bottomLeftPath.lineTo(0, height);
			bottomLeftPath.lineTo(mBottomLeftRadius, height);
			RectF arc = new RectF(0, height - mBottomLeftRadius * 2, mBottomLeftRadius * 2, height);
			bottomLeftPath.arcTo(arc, 90, 90);
			bottomLeftPath.close();
			mPath.addPath(bottomLeftPath);
		}

		// bottomRight path
		if (mBottomRightRadius > 0) {
			Path bottomRightPath = new Path();
			bottomRightPath.moveTo(width - mBottomRightRadius, height);
			bottomRightPath.lineTo(width, height);
			bottomRightPath.lineTo(width, height - mBottomRightRadius);
			RectF arc = new RectF(width - mBottomRightRadius * 2, height - mBottomRightRadius * 2, width, height);
			bottomRightPath.arcTo(arc, 0, 90);
			bottomRightPath.close();
			mPath.addPath(bottomRightPath);
		}

	}

}
