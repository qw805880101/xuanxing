package com.xuanxing.tc.game;

import com.alibaba.fastjson.JSON;
import com.xuanxing.tc.game.bean.LoginInfo;
import com.xuanxing.tc.game.bean.MemberInfo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String s = "{\n" +
                "    \"attentionNum\": \"0\",\n" +
                "    \"collectNum\": \"0\",\n" +
                "    \"fansNum\": \"0\",\n" +
                "    \"p_token\": \"b145b1ca99b35515580473b75743f72d\"\n" +
                "}";

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setAttentionNum("123");
        loginInfo.setCollectNum("13");
        loginInfo.setFansNum("123");
        loginInfo.setP_token("123123");
        loginInfo.setMemberInfo(new MemberInfo());

        String ss = JSON.toJSONString(loginInfo);
        System.out.println(ss);
        LoginInfo loginInfos = (LoginInfo) JSON.parseObject(ss, LoginInfo.class);
    }

    @Test
    public void testDate() {
//        String s = "";
//        SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//        //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
//        java.util.Date dt = new Date(Long.parseLong("1504345756000"));
//        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
//        System.out.println(sDateTime);


        // 存放内容的集合
        ArrayList<String> items = new ArrayList<>();

        // 存放乱序结果的集合
        ArrayList<String> result = new ArrayList<>();

        // 初始化存有内容的集合
        items.add("aaa");
        items.add("bbb");
        items.add("ccc");
        items.add("ddd");
        items.add("eee");
        items.add("fff");
        items.add("ggg");

        // 初始化随机数
        Random rand = new Random();

        // 取得集合的长度，for循环使用
        int size = items.size();

        // 遍历整个items数组
        for (int i = 0; i < size; i++) {
            // 任意取一个0~size的整数，注意此处的items.size()是变化的，所以不能用前面的size会发生数组越界的异常
            int myRand = rand.nextInt(items.size());
            //将取出的这个元素放到存放结果的集合中
            result.add(items.get(myRand));
            //从原始集合中删除该元素防止重复。注意，items数组大小发生了改变
            items.remove(myRand);
        }

        //便利输出结果
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
        }
    }

    @Test
    public void maqin() {
        List<String> list1 = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list1.add("test" + i);
            list2.add("test" + i * 2);
        }
        getDiffrent(list1, list2);
        getDiffrent2(list1, list2);
        getDiffrent3(list1, list2);
        List<String> list3 = getDiffrent4(list1, list2);
//        getDiffrent total times 2789492240
//        getDiffrent2 total times 3324502695
//        getDiffrent3 total times 24710682
//        getDiffrent4 total times 15627685
    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent4(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            Integer cc = map.get(string);
            if (cc != null) {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent4 total times " + (System.nanoTime() - st));
        return diff;

    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent3(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
        List<String> diff = new ArrayList<String>();
        for (String string : list1) {
            map.put(string, 1);
        }
        for (String string : list2) {
            Integer cc = map.get(string);
            if (cc != null) {
                map.put(string, ++cc);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("getDiffrent3 total times " + (System.nanoTime() - st));
        return diff;
    }

    /**
     * 获取连个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent2(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        list1.retainAll(list2);
        System.out.println("getDiffrent2 total times " + (System.nanoTime() - st));
        return list1;
    }

    /**
     * 获取两个List的不同元素
     *
     * @param list1
     * @param list2
     * @return
     */
    private static List<String> getDiffrent(List<String> list1, List<String> list2) {
        long st = System.nanoTime();
        List<String> diff = new ArrayList<String>();
        for (String str : list1) {
            if (!list2.contains(str)) {
                diff.add(str);
            }
        }
        System.out.println("getDiffrent total times " + (System.nanoTime() - st));
        return diff;
    }
}
