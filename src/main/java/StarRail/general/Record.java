package StarRail.general;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 全局变量模块
 * 用于更新和记录全局使用的临时变量
 *
 * @author : 真寻
 * 2023/8/2
 */
@Getter
@Setter
@Component
public class Record {
    /**
     * 体力值
     */
    private int physicalPowerValue = 0;

    /**
     * 是否再来一次
     */
    private boolean tryAgain = false;

    /**
     * 挑战次数
     */
    private int challengeCount = 0;

    /**
     * 当前挑战单次体力消耗
     */
    private int challengeCost = 0;


    /**
     * 今日委托进度
     */
    private boolean entrustProgress = false;
    /**
     * 使用支援角色并获得战斗胜利
     */
    private boolean usingSupportRolesAndWin = false;



}
