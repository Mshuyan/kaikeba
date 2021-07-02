package com.supergo.example.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName OrderTransactionListener
 * @Description
 * @Author hubin
 * @Date 2020/7/6 18:35
 * @Version V1.0
 **/
public class OrderTransactionListener implements TransactionListener{

    private static final Map<String, Boolean> results = new ConcurrentHashMap<>();

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String orderId = (String) arg;
        //记录本地事务执行结果
        boolean success = persistTransactionResult(orderId);
        System.err.println("订单服务执行本地事务下单,orderId: " + orderId + ", result: " + success);
        return success ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String orderId = msg.getKeys();
        System.err.println("执行事务消息回查,orderId: " + orderId);
        return Boolean.TRUE.equals(results.get(orderId)) ? LocalTransactionState.COMMIT_MESSAGE : LocalTransactionState.ROLLBACK_MESSAGE;
    }

    private boolean persistTransactionResult(String orderId) {
        boolean success = Math.abs(Objects.hash(orderId)) % 2 == 0;
        results.put(orderId, success);
        return success;
    }

}

