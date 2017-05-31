package test.quxing.com.getnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jin on 2017/5/7.
 */

public class RollViewAdapter extends LoopPagerAdapter {
    private List<String> imageIdList;
    private List<String> titleList;
    private DisplayImageOptions options;
    private Context context;
    private ImageLoader imageLoader;

    public RollViewAdapter(Context context, RollPagerView viewPager, ArrayList<String> imageUrlList, ArrayList<String> titleList) {
        super(viewPager);
        this.context = context;
        this.imageIdList = imageUrlList;
        this.titleList = titleList;

        // 初始化imageLoader 否则会报错
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();
    }

    @Override
    public View getView(ViewGroup container, int position) {
        /*ImageView view = new ImageView(container.getContext());
        imageLoader.displayImage(
                (String) this.imageIdList.get(position),
                view, options);

        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));*/
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpage, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image);

        TextView page = (TextView)view.findViewById(R.id.page);
        TextView title = (TextView)view.findViewById(R.id.title);

        imageLoader.displayImage(
                (String) this.imageIdList.get(position),
                imageView, options);
        page.setText(position+1+"/"+imageIdList.size());
        title.setText(titleList.get(position));


        return view;
    }

    @Override
    public int getRealCount() {
        return imageIdList.size();
    }
}
