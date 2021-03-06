package com.example.bookadmin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.bookadmin.R;

import java.util.List;

public class TextAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private List<String> mListData;
	private String[] mArrayData;
	private int selectedDrawble;
	private int normalDrawbleId;
	private int selectedPos = -1;
	private String selectedText = "";
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;
	private float textSize;
	private int colorRes;
	private int type = 0;//1:����ѡ��  2��������ַ  3�����
	/**
	 *
	 * @param context
	 * @param listData
	 * @param sId //����ѡ�еı���ͼƬ
	 * @param nId //����δѡ��״̬����ͼƬ
	 */
	public TextAdapter(Context context, List<String> listData, int sId, int nId,int type) {
		super(context, R.string.app_name, listData);
		mContext = context;
		mListData = listData;
		this.type = type;
		if(sId!=0){
			selectedDrawble = sId;
		}
		normalDrawbleId = nId;
		init();
	}

	public TextAdapter(Context context, String[] arrayData, int sId, int nId,int type) {
		super(context, R.string.app_name, arrayData);
		mContext = context;
		mArrayData = arrayData;
		this.type = type;
		if(sId!=0){
			selectedDrawble = sId;
		}
		normalDrawbleId = nId;
		init();
	}

	private void init(){
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag(R.id.position);
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos);
			notifyDataSetChanged();
		}else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos];
			notifyDataSetChanged();
		}
	}

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos);
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos];
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		if (mArrayData != null && selectedPos < mArrayData.length) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	public void setTextColor(int res){
		colorRes = res;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.view_area_popuwindow_item, parent, false);
			holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
			holder.vLine = convertView.findViewById(R.id.vLine);
			holder.vDivider = convertView.findViewById(R.id.vDivider);
			holder.vBottomDivider = convertView.findViewById(R.id.vBottomDivider);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setTag(R.id.position, position);
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()){
				mString = mListData.get(position);
			}
			holder.vDivider.setVisibility(View.VISIBLE);
		}else if (mArrayData != null) {
			if (position < mArrayData.length) {
				mString = mArrayData[position];
			}
			holder.vLine.setVisibility(View.GONE);
			holder.vDivider.setVisibility(View.GONE);
		}

		if (mString.contains("不限"))
			holder.tvContent.setText("不限");
		else
			holder.tvContent.setText(mString);
		if(textSize!=0){
			holder.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
		}
		if(colorRes!=0){
			holder.tvContent.setTextColor(colorRes);
		}
		if (selectedText != null && selectedText.equals(mString)) {
			if(type == 1){
				holder.vLine.setVisibility(View.VISIBLE);
			}else{
				holder.vLine.setVisibility(View.GONE);
			}
			convertView.setBackgroundResource(selectedDrawble);//设置选中的背景图片
		}else{
			holder.vLine.setVisibility(View.GONE);
			convertView.setBackgroundResource(normalDrawbleId);//设置未选中状态背景图片
		}
		if(type==3&&mArrayData != null
				&&mArrayData.length>0&&(position == mArrayData.length-1)){
			holder.vBottomDivider.setVisibility(View.VISIBLE);
		}else{
			holder.vBottomDivider.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(onClickListener);
		return convertView;
	}

	private class ViewHolder{
		TextView tvContent;
		View vLine,vDivider,vBottomDivider;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}

}
