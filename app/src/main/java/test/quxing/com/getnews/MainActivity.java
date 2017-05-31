package test.quxing.com.getnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RollPagerView rollPagerView;
    private ArrayList<String> imageUrlList ;
    private ArrayList<String> linkUrlArray;
    private ArrayList<String> titleList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ProgressAsyncTask asyncTask=new ProgressAsyncTask();
            asyncTask.execute(10000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        rollPagerView = (RollPagerView) findViewById(R.id.view_rollpager);

    }

    public void getData(List<Map<String, String>> map) {
        imageUrlList = new ArrayList<>();
        linkUrlArray= new ArrayList<>();
        titleList = new ArrayList<>();

        for (int i=0;i<map.size();i++){
            imageUrlList.add(map.get(i).get("img"));
            linkUrlArray.add(map.get(i).get("url"));
            titleList.add(map.get(i).get("title"));
        }

        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //   Toast.makeText(getActivity().getApplicationContext(),"Item "+position+" clicked", Toast.LENGTH_SHORT).show();
                String url = linkUrlArray.get(position);
                if(url==null){
                    Toast.makeText(MainActivity.this, "暂无该链接",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();

                    bundle.putString("url", url);
                    Intent intent = new Intent(MainActivity.this, BaseWebActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
        rollPagerView.setAdapter(new RollViewAdapter(MainActivity.this,rollPagerView,imageUrlList,titleList));

        // initBanner(imageUrlList,linkUrlArray,titleList);
    }

    class ProgressAsyncTask extends AsyncTask<Integer, Integer,  List<Map<String,String>>> {

        private List<Map<String,String>> map;
        public ProgressAsyncTask() {
            super();
            map=new ArrayList<>();
        }

        @Override
        protected  List<Map<String,String>> doInBackground(Integer... params) {
            Document doc = null;

            try {
                doc = Jsoup.connect("http://www.educubeglobal.com/").get();
                Elements elements1 = doc.select("[class=da-slider][id=da-slider]");
                Elements elements2 = elements1.select("h2");
                Elements elements3 = elements1.select("img");
                Elements elements4 = elements1.select("a");
                for(int i=0;i<elements2.size();i++){
                    Map<String,String> m=new HashMap<>();
                    m.put("title",elements2.get(i).text());
                    m.put("img","http://www.educubeglobal.com"+elements3.get(i).attr("src"));
                    m.put("url",elements4.get(i).attr("href"));
                    map.add(m);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return map ;
            //return test();
        }

        @Override
        protected void onPostExecute( List<Map<String,String>> result) {

            getData(result);

        }

        // 该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置
        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }


}
