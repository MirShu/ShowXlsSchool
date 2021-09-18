package com.example.showxlsuniversities;

/**
 * @ClassName UniversitiesModel
 * @Description TODO
 * @Author SeanLim
 * @Date 2021-9-17 10:04
 * @E-mail linlin.1016@qq.com
 * @Version 1.0
 */
public class UniversitiesModel implements Comparable<UniversitiesModel> {
    private String universitiesName;
    private String pinyin;
    private String firstLetter;
    public UniversitiesModel(String UniversitiesName) {
        this.universitiesName = UniversitiesName;
        pinyin = PYHelper.getPinYin(UniversitiesName); // 学校名拼音
        firstLetter = pinyin.substring(0, 1).toUpperCase(); // 获取拼音首字母并转成大写
        if (!firstLetter.matches("[A-Z]")) { // 如果不在A-Z中则默认为“#”
            firstLetter = "#";
        }
    }

    public String getUniversitiesName() {
        return universitiesName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    @Override
    public int compareTo(UniversitiesModel another) {
        if (firstLetter.equals("#") && !another.getFirstLetter().equals("#")) {
            return 1;
        } else if (!firstLetter.equals("#") && another.getFirstLetter().equals("#")){
            return -1;
        } else {
            return pinyin.compareToIgnoreCase(another.getPinyin());
        }
    }
}
