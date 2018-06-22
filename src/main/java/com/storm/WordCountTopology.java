package com.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {
    public static void main(String[] args) throws Exception {

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new SentenceSpout(), 1);
        builder.setBolt("split", new SplitSentenceBolt(), 2).shuffleGrouping("spout");
        builder.setBolt("count", new WordCountBolt(), 2).fieldsGrouping("split", new Fields("word"));

        Config conf = new Config();
        conf.setDebug(true);

//        if (args != null && args.length > 0) {
//            // 集群模式
//            conf.setNumWorkers(2);
//            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
//        } else {
            // 本地模式
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", conf, builder.createTopology());
            Thread.sleep(10000);
            cluster.shutdown();           //如果不关闭，一直保持运行，接受数据处理数据
//        }
    }
}
