package com.change.likeyixin;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MyTemplateHeader extends LinearLayout {

	private LayoutInflater mInflater;
	private Context mContext;
	private ImageButton Ib_Back,Ib_Menu;
	private TextView Tv_Title;
	private View mView;
	
	private onClickHeaderListener mClickHeaderListener;
	
	public MyTemplateHeader(Context context) {
		super(context);
		this.mContext = mContext;
		init();
	}
	public MyTemplateHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}
	
	private void init()
	{
		this.mInflater = LayoutInflater.from(this.mContext);
		this.mView = this.mInflater.inflate(R.layout.template_header,this,true);
		this.Ib_Back = (ImageButton) this.mView.findViewById(R.id.template_ib_back);
		this.Ib_Menu = (ImageButton) this.mView.findViewById(R.id.template_ib_menu);
		this.Tv_Title = (TextView)this.mView.findViewById(R.id.template_tv_title);
		
		Ib_Back.setTag(1);
		Ib_Back.setOnClickListener(OnClickListener);
		Ib_Menu.setTag(2);
		Ib_Menu.setOnClickListener(OnClickListener);
	}
	
	View.OnClickListener OnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int tag = (Integer) v.getTag();
			switch(tag)
			{
			case 1:
				if(MyTemplateHeader.this.mClickHeaderListener != null){
					MyTemplateHeader.this.mClickHeaderListener.OnClickLeftButton();
				}
				
				break;
			case 2:
				if(MyTemplateHeader.this.mClickHeaderListener != null){
					MyTemplateHeader.this.mClickHeaderListener.OnClickRightButton();
				}
				break;
			};
		}
	};
	
	public void setClickHeaderListener(onClickHeaderListener l)
	{
		this.mClickHeaderListener = l;
	}
	
	public interface onClickHeaderListener
	{
		public void OnClickLeftButton();
		public void OnClickRightButton();
	}
	
	public void setHeaderTitleText(String text)
	{
		this.Tv_Title.setText(text);
	}
}
