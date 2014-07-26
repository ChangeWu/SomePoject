package com.change.delegateforadapterdemo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * 委托者模式运用在adapter中
 * @author Change
 *
 */
public class DelegateForAdapter extends BaseAdapter {
	private List<String> names;
	private LayoutInflater mInflater;
	private DataControlDelegate mDelegate;
	public DelegateForAdapter(Context ctx,List<String> _names) {
		this.names = _names;
		mInflater = LayoutInflater.from(ctx);
	}
	/**
	 * 委托接口，用于对adapter中的数据进行操作，此处只提供了一个删除单条数据的方法。
	 * @author Change
	 *
	 */
	public static interface DataControlDelegate{
		/**
		 * 删除数据
		 * @param name
		 */
		public void removeItem(String name);
	}
	
	@Override
	public int getCount() {
		return names.size();
	}

	@Override
	public Object getItem(int arg0) {
		return names.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder holder = null;
		if(null==arg1){
			arg1 = mInflater.inflate(R.layout.item_name, null);
			holder = new ViewHolder();
			holder.name = (TextView)arg1.findViewById(R.id.name);
			holder.remove = (Button)arg1.findViewById(R.id.remove);
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder)arg1.getTag();
		}
		final String name = names.get(arg0);
		holder.remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(null!=mDelegate)
					mDelegate.removeItem(name);//删除行为
			}
		});
		holder.name.setText(name);
		return arg1;
	}
	static class ViewHolder{
		public TextView name;
		public Button remove;
	}
	/**
	 * 设置委托者
	 * @param _delegate
	 */
	public void setDataControlDelegate(DataControlDelegate _delegate){
		this.mDelegate = _delegate;
	}

}
