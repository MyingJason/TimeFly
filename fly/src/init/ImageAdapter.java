package init;
import com.example.fly.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ImageAdapter extends BaseAdapter
{
	private Context	mContext;		

	private Integer[] mImageIds = 
	{ 						
			R.drawable.help1, 
			R.drawable.help2, 
	};
	public ImageAdapter(Context c)
	{
		mContext = c;
	}
	public int getCount()
	{
		return mImageIds.length;
	}
	public Object getItem(int position)
	{
		return position;
	}
	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView imageview = new ImageView(mContext);

		imageview.setImageResource(mImageIds[position]);

		imageview.setLayoutParams(new Gallery.LayoutParams(300, 300));

		imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		return imageview;
	}
}

