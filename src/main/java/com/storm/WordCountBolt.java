package com.storm;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseBasicBolt {
    private Map<String, Long> counts = null;

    public void prepare(Map stormConf, TopologyContext context) {
        this.counts = new HashMap<String, Long>();
    }

    public void execute(Tuple input, BasicOutputCollector basicOutputCollector) {
        String word = input.getStringByField("word");
        Long count = this.counts.get(word);
        if (count == null) {
            count = 0L;
        }
        count++;
        this.counts.put(word, count);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    public void cleanup() {
        //拓扑结束执行
        for (String key : counts.keySet()) {
            System.out.println(key + " : " + this.counts.get(key));
        }
    }
}
