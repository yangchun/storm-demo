package com.storm;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import java.util.Map;

public class SentenceSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private String[] sentences = {
            "Apache Storm is a free and open source distributed realtime computation system",
            "Storm makes it easy to reliably process unbounded streams of data",
            "doing for realtime processing what Hadoop did for batch processing",
            "Storm is simple", "can be used with any programming language",
            "and is a lot of fun to use" };

    public static String string = null;
    private int index = 0;
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector collector) {
        this.collector = collector;
    }

    public void nextTuple() {
        if(index >= sentences.length){
            return;
        }
        //发送字符串
//        System.out.println("发送字符串"+string);
//        this.collector.emit(new Values(string));
        this.collector.emit(new Values(sentences[index]));
        index++;
        Utils.sleep(1000);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sentence"));
    }
}

