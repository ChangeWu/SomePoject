package com.bobinfo.projecttimeviewers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobinfo.projecttimeviewers.app.Constants;
import com.bobinfo.projecttimeviewers.app.DLog;
import com.bobinfo.projecttimeviewers.modle.ProjectTimes;
import com.bobinfo.projecttimeviewers.view.RemoteImageView;

public class ShowListAdapter extends BaseAdapter {
	private ProjectTimes project;
	private Context m_ctx;
	private static final String TAG = "ShowListAdapter";
	public ShowListAdapter(Context ctx,ProjectTimes project) {
		this.m_ctx = ctx;
		this.project = project;
	}
	@Override
	public int getCount() {
		return project.projects!=null?project.projects.size():0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold hold = null;
		if(convertView==null){
			convertView = View.inflate(m_ctx, R.layout.showlist_item, null);
			hold = new ViewHold();
			convertView.setTag(hold);
		}else{
			hold = (ViewHold)convertView.getTag();
		}
		hold.misstime = (TextView)convertView.findViewById(R.id.misstime);
		hold.name = (TextView)convertView.findViewById(R.id.name);
		hold.time = (TextView)convertView.findViewById(R.id.time);
		hold.pic = (RemoteImageView)convertView.findViewById(R.id.pic);
		hold.name.setText("工程名称："+project.projects.get(position).name);
		hold.time.setText("预计结束时间："+project.projects.get(position).time);
		hold.misstime.setText("超期："+project.projects.get(position).misstime+"天");
		hold.pic.setDefaultImage(R.drawable.default_pic);
		hold.pic.setImageUrl(Constants.SERVER_URL+"head/"+project.projects.get(position).picpath);
		DLog.v(TAG, Constants.SERVER_URL+"head/"+project.projects.get(position).picpath);
		hold.pic.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return convertView;
	}
	class ViewHold{
		TextView name,time,misstime;
		RemoteImageView pic;
	}

}
