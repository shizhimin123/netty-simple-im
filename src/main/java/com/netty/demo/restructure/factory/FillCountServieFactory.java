package com.netty.demo.restructure.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: FillCountServieFactory
 * @Author: shizhimin
 * @Date: 2020/5/9
 * @Version: 1.0
 */
public class FillCountServieFactory {

    private static Map<Integer, FillCountService> fillCountServiceMap = new HashMap<>();

    static {
        fillCountServiceMap.put(1, (countRecoder, count) -> countRecoder.setCountOfFirstStage(count));
        fillCountServiceMap.put(2, (countRecoder, count) -> countRecoder.setCountOfSecondStage(count));
        fillCountServiceMap.put(3, (countRecoder, count) -> countRecoder.setCountOfThirdtage(count));
        fillCountServiceMap.put(4, (countRecoder, count) -> countRecoder.setCountOfForthtage(count));
        fillCountServiceMap.put(5, (countRecoder, count) -> countRecoder.setCountOfFirthStage(count));
        fillCountServiceMap.put(6, (countRecoder, count) -> countRecoder.setCountOfSixthStage(count));
    }

    public static FillCountService getFillCountStrategy(int statusCode) {
        return fillCountServiceMap.get(statusCode);
    }
}
