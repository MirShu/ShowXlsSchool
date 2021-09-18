package com.example.showxlsuniversities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.showxlsuniversities.recycler.RecyclerAdapter;
import com.example.showxlsuniversities.recycler.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @ClassName MainActivity
 * @Description TODO
 * @Author SeanLim
 * @Date 2021-9-17 10:04
 * @E-mail linlin.1016@qq.com
 * @Version 1.0
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<UniversitiesModel> universitiesModels = new ArrayList();
    private ArrayList<SchoolModel> searchModels = new ArrayList();

    private SchoolAdapter schoolAdapter;
    private RecyclerAdapter searcheAdapter;

    private ListView listView;
    private RecyclerView recyclerSearcheList;

    private EditText edSearch;

    private SideBar sideBar;

    private ContentLoadingProgressBar loadingProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        listView = findViewById(R.id.listView);
        sideBar = findViewById(R.id.side_bar);
        edSearch = findViewById(R.id.ed_search);
        recyclerSearcheList = findViewById(R.id.recycler_searche_list);
        loadingProgress = findViewById(R.id.loading_progress);
        sideBar.setOnStrSelectCallBack((index, selectStr) -> {
            for (int i = 0; i < universitiesModels.size(); i++) {
                if (selectStr.equalsIgnoreCase(universitiesModels.get(i).getFirstLetter())) {
                    listView.setSelection(i); // 选择到首字母出现的位置
                    return;
                }
            }
        });

        /**
         * 线程获取xls学校数据
         */
        new Thread(() -> {
            AssetManager localAssetManager = getAssets();
            try {
                Workbook localWorkbook = Workbook.getWorkbook(localAssetManager.open("universities.xls"));
                Sheet localSheet = localWorkbook.getSheet(0);    //下游标取值
                int j = localSheet.getRows();
                for (int m = 0; m < j; m++) {
                    universitiesModels.add(new UniversitiesModel(localSheet.getCell(1, m).getContents() + "id" + localSheet.getCell(0, m).getContents()));
                }
                localWorkbook.close();
            } catch (Exception localException) {
            }

            // 获取完之后更新UI界面
            runOnUiThread(() -> {
                Collections.sort(universitiesModels);
                schoolAdapter = new SchoolAdapter(this, universitiesModels);
                listView.setDivider(null);
                listView.setAdapter(schoolAdapter);
                loadingProgress.setVisibility(View.GONE);
            });
        }).start();
        listView.setOnItemClickListener(this);
        showSearch();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this, this.universitiesModels.get(i).getUniversitiesName(), Toast.LENGTH_SHORT).show();
    }


    /**
     * 搜索
     */
    private void showSearch() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(edSearch.getText().toString())) {
                    searcheAdapter = new RecyclerAdapter<UniversitiesModel>(MainActivity.this, search(editable.toString(), universitiesModels),
                            R.layout.item_search_universities) {
                        @Override
                        public void convert(RecyclerViewHolder helper, UniversitiesModel item, int position) {
                            String a[] = item.getUniversitiesName().split("id");
                            try {
                                if (a[0].contains(editable.toString())) {
                                    helper.setText(R.id.tv_university_name, getColorString(a[0],
                                            getResources().getColor(R.color.color_red),
                                            a[0].indexOf(editable.toString()),
                                            a[0].indexOf(editable.toString())
                                                    + editable.toString().length()));
                                }
                            } catch (Exception e) {
                            }
                        }
                    };
                    recyclerSearcheList.setHasFixedSize(true);
                    recyclerSearcheList.setLayoutManager(new GridLayoutManager(MainActivity.this, 1,
                            LinearLayoutManager.VERTICAL, false));
                    recyclerSearcheList.setAdapter(searcheAdapter);

                    listView.setVisibility(View.GONE);
                    sideBar.setVisibility(View.GONE);
                    recyclerSearcheList.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.VISIBLE);
                    sideBar.setVisibility(View.VISIBLE);
                    recyclerSearcheList.setVisibility(View.GONE);
                }
                searcheAdapter.setOnItemClickListener((parent, position) -> {
                    Toast.makeText(MainActivity.this, searchModels.get(position).getSchoolName(), Toast.LENGTH_SHORT).show();
                });

            }
        });
    }

    /**
     * 模糊搜索数据
     */
    public List search(String name, List list) {
        List results = new ArrayList();
        Pattern pattern = Pattern.compile(name);
        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = pattern.matcher(universitiesModels.get(i).getUniversitiesName());
            if (matcher.find()) {
                results.add(list.get(i));
            }
        }
        searchModels.addAll(results);
        return results;
    }


    private SpannableString getColorString(String str, int color, int start, int end) {
        SpannableString ss = new SpannableString(str);
        ss.setSpan(new ForegroundColorSpan(color), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

}