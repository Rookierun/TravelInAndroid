package com.rookie.travelinandroid.super_ui.animator;

import java.util.Arrays;
import java.util.List;

public class ProvinceUtil {
    static List<String> cityList;
    public static void init(){
        String citys = "北京市--北京 ；上海市 --上海 ；" +
                "" +
                "天津市 --天津 ；重庆市 --重庆 ；" +
                "" +
                "黑龙江省 --哈尔滨 ；吉林省 --长春； " +
                "" +
                "辽宁省 --沈阳 ；内蒙古自治区--呼和浩特 ；" +
                "" +
                "河北省 --石家庄 ；新疆维吾尔自治区--乌鲁木齐 ；" +
                "" +
                "甘肃省-- 兰州 ；青海省--西宁 ；" +
                "" +
                "陕西省 --西安 ；宁夏回族自治区--银川 ；" +
                "" +
                "河南省-- 郑州 ；山东省--济南 ；" +
                "" +
                "山西省--太原 ；安徽省--合肥 ；" +
                "" +
                "湖南省--长沙；湖北省--武汉 ；" +
                "" +
                "江苏省--南京 ；四川省--成都； " +
                "" +
                "贵州省--贵阳 ；云南省--昆明 ；" +
                "" +
                "广西壮族自治区--南宁 ；西藏自治区-- 拉萨 ；" +
                "" +
                "浙江省--杭州；江西省--南昌 ；" +
                "" +
                "广东省--广州 ；福建省--福州 ；" +
                "" +
                "台湾省--台北 ；海南省 --海口 ；" +
                "" +
                "香港特别行政区-- 香港 ； 澳门特别行政区-- 澳门";
        cityList = Arrays.asList(citys.split("；"));
    }



}
