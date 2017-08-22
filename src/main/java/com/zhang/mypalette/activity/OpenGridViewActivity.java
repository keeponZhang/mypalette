package com.zhang.mypalette.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.keepon.pallette.R;
import com.zhang.mypalette.contral.FileOper;


public class OpenGridViewActivity extends Activity {
	private  GridView my_gridview ;
	private  GridImageAdapter myImageViewAdapter ;
	private FileOper fileOper = new FileOper();
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.open_dialog);
		my_gridview = (GridView) findViewById(R.id.grid);
		myImageViewAdapter = new GridImageAdapter(OpenGridViewActivity.this);
		my_gridview.setAdapter(myImageViewAdapter);
		
		my_gridview.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bitmap bmp = myImageViewAdapter.getcheckedImageIDPostion(arg2);
				if (null != bmp) {
					Intent intent = getIntent();
				/*	intent = intent.setClass(OpenGridViewActivity.this,
							SketchpadMainActivity.class);*/
					Bundle bundle = new Bundle();
					bundle.putParcelable("bmp", bmp);
					intent.putExtras(bundle);
					OpenGridViewActivity.this.setResult(RESULT_OK, intent); // RESULT_OK是返回状态码
					OpenGridViewActivity.this.finish(); // 会触发onDestroy();
				}
			}
		});

	}
	

	public class GridImageAdapter extends BaseAdapter{

		private Context myContext ;
		private ImageView the_imageView ;

		private Bitmap[] mImageIds = fileOper.getStrokeFilePaths();
		private Bitmap[] mImageResources = null ;
		public GridImageAdapter(Context myContext) {
			this.myContext = myContext;
		}

		
		public int getCount() {
			
			for(int i=0; i<mImageIds.length; i++){
				if(mImageIds[i] == null){
					mImageResources = new Bitmap[i];
					break;
				}
			}
			for(int i=0; i<mImageResources.length; i++){
				mImageResources[i] = mImageIds[i];
			}
			return mImageResources.length;
		}

		
		public Object getItem(int position) {
			return position;
		}

		
		public long getItemId(int position) {
			return position;
		}

		
		public View getView(int position, View convertView, ViewGroup parent) {

			the_imageView = new  ImageView( myContext );

			the_imageView .setImageBitmap(mImageResources[position]);

			the_imageView .setAdjustViewBounds(true );

			the_imageView .setBackgroundResource(android.R.drawable. picture_frame );
			return the_imageView ;
		}

		/* 自定义获取对应位置的图片 */
		public Bitmap getcheckedImageIDPostion( int theindex) {
			return mImageResources [theindex];
		}
	}
}
