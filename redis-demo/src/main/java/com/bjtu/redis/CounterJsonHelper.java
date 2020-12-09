package com.bjtu.redis;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjtu.redis.Counter;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class CounterJsonHelper {
    Counter counter;
    public CounterJsonHelper(Counter counter){
        this.counter=counter;
        initCounter();
    }

    private void initCounter(){
        try {
            String counterName = counter.counterName;

            ClassLoader loader = FileUtil.class.getClassLoader();
            InputStream stream = loader.getResourceAsStream("counters.json");
            assert stream != null;
            String text = IOUtils.toString(stream, "utf8");
            JSONObject jsonObject = JSONObject.parseObject(text);
            JSONArray array = jsonObject.getJSONArray("counters");

            for (int i = 0; i < array.size(); i++) {
                JSONObject jo = array.getJSONObject(i);
                String an = jo.getString("countername");
                if (an.equals(counterName)) {
                    counter.setKey(jo.getString("keyfields"));
                    counter.setType(jo.getString("type"));
                    if(jo.getInteger("valuefields")!=null) {
                        counter.setValueField(jo.getInteger("valuefields"));
                    }
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
