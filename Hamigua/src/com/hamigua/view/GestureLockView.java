package com.hamigua.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
/**
 * �Զ�������View
 * 
 * @author rxx
 *
 * 2014��12��11��  ����5:59:32
 */
public class GestureLockView extends View{
	/**��������key*/
	private String key="";
	private OnGestureFinishListener onGestureFinishListener;
	
	/**����Բ������*/
	private LockCircle[] cycles;
	/**�洢����Բ������*/
	private List<Integer> linedCycles = new ArrayList<Integer>();
	
	//����
	/**������Բ*/
	private Paint paintNormal;
	/**������ڲ�Բ*/
	private Paint paintInnerCycle;
	/**��·��*/
	private Paint paintLines;
	private Path linePath = new Path();
	
	/**��ǰ��ָX,Yλ��*/
	private int eventX, eventY;
	
	/**�ܷ�ٿؽ���滭*/
	private boolean canContinue = true;
	/**��֤���*/
	private boolean result;
	private Timer timer;
	
	/**δѡ����ɫ*/
	private final int NORMAL_COLOR = Color.parseColor("#959BB4"); 
	/**������ɫ*/
	private final int ERROE_COLOR = Color.parseColor("#FF2525"); // ������Բ��ɫ
	/**ѡ��ʱ��ɫ*/
	private final int TOUCH_COLOR = Color.parseColor("#409DE5"); // ѡ����Բ��ɫ
	
	//=================================start=���췽��========================
	public GestureLockView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	public GestureLockView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	
	public GestureLockView(Context context) {
		this(context,null);
	}
	//===============================end=���췽��========================
	
	/**��ʼ��*/
	public void init()
	{
		paintNormal = new Paint();
		paintNormal.setAntiAlias(true);
		paintNormal.setStrokeWidth(5);
		paintNormal.setStyle(Paint.Style.STROKE);
		
		paintInnerCycle=new Paint();
		paintInnerCycle.setAntiAlias(true);
		paintInnerCycle.setStyle(Paint.Style.FILL);
		
		
		paintLines = new Paint();
		paintLines.setAntiAlias(true);
		paintLines.setStyle(Paint.Style.STROKE);
		paintLines.setStrokeWidth(10);
		
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specMode=MeasureSpec.getMode(widthMeasureSpec);
		int spceSize=MeasureSpec.getSize(widthMeasureSpec);
		heightMeasureSpec=MeasureSpec.makeMeasureSpec((int) (spceSize*0.85+0.5f), specMode);
		super.onMeasure(widthMeasureSpec,heightMeasureSpec);
	}
	
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int perWidthSize= getWidth() / 7;
		int perHeightSize=getHeight()/6;
		/**��ʼ��Բ�Ĳ���*/
		if(cycles==null&&(perWidthSize>0)&&(perHeightSize>0))
		{
			cycles=new LockCircle[9];
			for(int i=0;i<3;i++)
			{
				for(int j=0;j<3;j++)
				{
					LockCircle lockCircle=new LockCircle();
					lockCircle.setNum(i*3+j);
					lockCircle.setOx(perWidthSize*(j*2+1.5f)+0.5f);
					lockCircle.setOy(perHeightSize*(i*2+1)+0.5f);
					lockCircle.setR(perWidthSize*0.6f);
					cycles[i*3+j]=lockCircle;
				}
			}
		}
		
	}
	
	public void setKey(String key)
	{
		this.key=key;
	}
	
	public void setOnGestureFinishListener(OnGestureFinishListener onGestureFinishListener) {
		this.onGestureFinishListener = onGestureFinishListener;
	}
	
	/**����������ɺ�ص��ӿ�*/
	public interface OnGestureFinishListener
	{
		/**����������ɺ�ص�����*/
		public void OnGestureFinish(boolean success,String key);
	}
	
	/**��������*/
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(canContinue)
		{
			switch(event.getAction())
			{
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
			eventX=(int) event.getX();
			eventY=(int) event.getY();
			for(int i=0;i<cycles.length;i++)
			{
				if(cycles[i].isPointIn(eventX, eventY))
				{
					cycles[i].setOnTouch(true);
					if(!linedCycles.contains(cycles[i].getNum()))
					{
						linedCycles.add(cycles[i].getNum());
					}
				}
			}
				break;
			case MotionEvent.ACTION_UP:
				//��ָ�뿪��ͣ����
				canContinue=false;
				StringBuffer stringBuffer=new StringBuffer();
				for(int i=0;i<linedCycles.size();i++)
				{
					stringBuffer.append(linedCycles.get(i));
				}
				result=key.equals(stringBuffer.toString());
				if(onGestureFinishListener!=null&&linedCycles.size()>0)
				{
					onGestureFinishListener.OnGestureFinish(result, stringBuffer.toString());
				}
				timer=new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						eventX = eventY = 0;
						for(int i=0;i<9;i++)
						{
							cycles[i].setOnTouch(false);
						}
						linedCycles.clear();
						linePath.reset();
						canContinue = true;
						postInvalidate();//�ڷ�ui�߳�ˢ�½���
					}
				}, 1000);
				break;
			}
			invalidate();
		}
		return true;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int cycleSize = cycles.length;
		for (int i = 0; i < cycleSize; i++) {
			// ���겢�Ҵ���
			if (!canContinue && !result)
			{
				if (cycles[i].isOnTouch()) {
					drawInnerCycle(cycles[i], canvas, ERROE_COLOR);
					drawOutsideCycle(cycles[i], canvas, ERROE_COLOR);
				} else
					drawOutsideCycle(cycles[i], canvas, NORMAL_COLOR);
			}
			//�滭��
			else {
				if (cycles[i].isOnTouch()) {
					drawInnerCycle(cycles[i], canvas, TOUCH_COLOR);
					drawOutsideCycle(cycles[i], canvas, TOUCH_COLOR);
				} else
					drawOutsideCycle(cycles[i], canvas, NORMAL_COLOR);
			}
		}

		if (!canContinue && !result) {
			drawLine(canvas, ERROE_COLOR);
		} else {
			drawLine(canvas, TOUCH_COLOR);
		}

	}
	
	/**������Բ*/
	private void drawOutsideCycle(LockCircle lockCircle, Canvas canvas,int color)
	{
		paintNormal.setColor(color);
		canvas.drawCircle(lockCircle.getOx(), lockCircle.getOy(),
				lockCircle.getR(), paintNormal);
	}
	
	/**������*/
	private void drawLine(Canvas canvas,int color)
	{
		//����·��
		linePath.reset();
		if (linedCycles.size() > 0) {
			int size=linedCycles.size();
			for (int i = 0; i < size; i++) {
				int index = linedCycles.get(i);
				float x = cycles[index].getOx();
				float y = cycles[index].getOy();
				if (i == 0) {
					linePath.moveTo(x,y);
				} else {
					linePath.lineTo(x,y);
				}
			}
			if (canContinue) {
				linePath.lineTo(eventX, eventY);
			}else {
				linePath.lineTo(cycles[linedCycles.get(linedCycles.size()-1)].getOx(), cycles[linedCycles.get(linedCycles.size()-1)].getOy());
			}
			paintLines.setColor(color);
			canvas.drawPath(linePath, paintLines);
		}
	}
	
	/**������ԲԲ*/
	private void drawInnerCycle(LockCircle myCycle, Canvas canvas,int color) {
		paintInnerCycle.setColor(color);
		canvas.drawCircle(myCycle.getOx(), myCycle.getOy(), myCycle.getR() / 3f,
				paintInnerCycle);
	}
	
	/**
	 * ÿ��Բ����
	 * 
	 * @author rxx
	 *
	 * 2014��12��12��  ����10:05:48
	 */
	class LockCircle {
		/**Բ�ĺ�����*/
		private float ox;
		/**Բ��������*/
		private float oy;
		/**�뾶����*/
		private float r;
		/**������ֵ*/
		private Integer num;
		/**�Ƿ�ѡ��:false=δѡ��*/
		private boolean onTouch;
		
		public float getOx() {
			return ox;
		}
		public void setOx(float ox) {
			this.ox = ox;
		}
		public float getOy() {
			return oy;
		}
		public void setOy(float oy) {
			this.oy = oy;
		}
		public void setOy(int oy) {
			this.oy = oy;
		}
		public float getR() {
			return r;
		}
		public void setR(float r) {
			this.r = r;
		}
		public Integer getNum() {
			return num;
		}
		public void setNum(Integer num) {
			this.num = num;
		}
		public boolean isOnTouch() {
			return onTouch;
		}
		public void setOnTouch(boolean onTouch) {
			this.onTouch = onTouch;
		}
		
		/**�ж�����λ���Ƿ���Բ���ڲ�*/
		public boolean isPointIn(int x, int y) {
			double distance = Math.sqrt((x - ox) * (x - ox) + (y - oy) * (y - oy));
			return distance < r;
		}
	}
}


