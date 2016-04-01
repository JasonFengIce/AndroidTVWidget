package com.open.androidtvwidget.view;

import com.open.androidtvwidget.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class ReflectItemView extends FrameLayout {

	private static final String TAG = "ReflectItemView";
	private static final int DEFUALT_REFHEIGHT = 80;

	private Paint mRefPaint = null;
	private Bitmap mReflectBitmap;
	private Canvas mReflectCanvas;
	private View mContentView;
	private int mRefHeight = DEFUALT_REFHEIGHT;

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

	// @Override
	// public boolean performClick() {
	// if (mContentView != null) {
	// return mContentView.performClick();
	// } else {
	// return super.performClick();
	// }
	// }

	private boolean mIsReflection = true;

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

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		drawRefleBitmap(canvas);
	}
	
	/**
	 * 绘制倒影.
	 */
	private void drawRefleBitmap(Canvas canvas) {
		if (mIsReflection) {
			// 创建一个画布.
			if (mContentView != null) {
				if (mReflectBitmap == null) {
					mReflectBitmap = Bitmap.createBitmap(mContentView.getWidth(), mRefHeight, Bitmap.Config.ARGB_8888);
					mReflectCanvas = new Canvas(mReflectBitmap);
				}
				// 绘制倒影.
				drawReflection(mReflectCanvas);
				canvas.save();
				int dy = mContentView.getBottom();
				int dx = mContentView.getLeft();
				canvas.translate(dx, dy);
				canvas.drawBitmap(mReflectBitmap, 0, 0, null);
				canvas.restore();
			}
		}
	}

	public Bitmap getReflectBitmap() {
		return mReflectBitmap;
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
		mContentView.draw(canvas);
		canvas.restore();
		canvas.drawRect(0, 0, mContentView.getWidth(), mRefHeight, mRefPaint);
		canvas.restore();
	}
}
