package com.eaibot.running.constants;

/**
 * @author Yist
 */
public class VersionTexts {

    public static final String version1_0 = "版本号:V1.0\n" +
            "更新时间:2018年06月19日\n" +
            "更新内容:\n" +
            "1.更新界面。\n";

    public static final String E_version1_0 = "Version Code:V1.0\n" +
            "Update Time:2018-06-19\n" +
            "Update Content:\n" +
            "1.New views。\n";

    public static final String version1_1 = "版本号:V1.1\n" +
            "更新时间:2018年08月07日\n" +
            "更新内容:\n" +
            "1.地图上传下载删除的优化。\n" +
            "2.目标点和充电桩自动保存。\n" +
            "3.可以将机器人当前位置添加为目标点。\n";

    public static final String E_version1_1 = "Version Code:V1.1\n" +
            "Update Time:2018-08-07\n" +
            "Update Content:\n" +
            "1.Upload, download, delete of maps are optimized. \n" +
            "2.Target and charge pose save automatically. \n" +
            "3.Can set robot's pose to be target. \n";

    public static final String version1_4 = "版本号:V1.4.230425\n" +
            "更新时间:2023年04月25日\n" +
            "更新内容:\n" +
            "1.修复已知的bug。\n";

    public static final String E_version1_2 = "Version Code:V1.2\n" +
            "Update Time:2019-07-09\n" +
            "Update Content:\n" +
            "1.Can switch between English and Chinese. \n";

    public static String getVersionText(int language) {
        if (language == 0) {
            return version1_4 + "\n\n";
//                    version1_1 + "\n\n" +
//                    version1_0;
        } else {
            return E_version1_2 + "\n\n" +
                    E_version1_1 + "\n\n" +
                    E_version1_0;
        }
    }

}
