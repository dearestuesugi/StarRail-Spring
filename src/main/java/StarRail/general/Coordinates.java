package StarRail.general;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 此类封装所有操作中使用的坐标——按模块划分
 *
 * @author : 真寻
 * 2023/11/9
 */

@Component
public class Coordinates {
    /**
     * 启动星轨
     * StarRail.module.initiate.StartStarRail
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.start-star-rail")
    public static class StartStarRailCoordinates {
        /**
         * 开始游戏按钮
         */
        private int[] startArr1;

        /**
         * 更新按钮
         */
        private int[] startArr2;

        /**
         * 打开启动器按钮
         */
        private int[] startArr3;

        /**
         * 账号密码
         */
        private int[] startArr4;

        /**
         * 账号区
         */
        private int[] startArr5;

        /**
         * 密码区
         */
        private int[] startArr6;

        /**
         * 同意用户协议按钮
         */
        private int[] startArr7;

        /**
         * 进入游戏
         */
        private int[] startArr8;

        /**
         * 点击进入
         */
        private int[] startArr9;

        /**
         * 列车补给
         */
        private int[] startArr10;
    }

    /**
     * 通用
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.general")
    public static class GeneralCoordinates{
        /**
         * 导航
         */
        private int[] generalArr1;

        /**
         * 战技点
         */
        private int[] generalArr2;

        /**
         * 地图 界面检测
         */
        private int[] generalArr3;

        /**
         * 点击空白处关闭
         * 适用范围：无名勋礼, 邮件, 模拟宇宙, 背包
         */
        private int[] generalArr4;

        /**
         * 地图-传送按钮
         */
        private int[] generalArr5;

    }
    /**
     * 菜单
     * StarRail.module.menu.Menu
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.menu")
    public static class MenuCoordinates {
        /**
         * 委托
         */
        private int[] menuArr1;

        /**
         * 领取
         */
        private int[] menuArr2;

        /**
         * 再次派遣
         */
        private int[] menuArr3;

        /**
         * 返回功能页面
         */
        private int[] menuArr4;

        /**
         * 信用点委托
         */
        private int[] menuArr5;

        /**
         * 邮箱
         */
        private int[] menuArr6;

        /**
         * 全部领取
         */
        private int[] menuArr7;

        /**
         * 关闭邮箱
         */
        private int[] menuArr8;

        /**
         * 点击相机
         */
        private int[] menuArr9;

        /**
         * 关闭相机
         */
        private int[] menuArr10;

        /**
         * 菜单界面检测
         */
        private int[] menuArr11;

        /**
         * 邮件领取
         */
        private int[] menuArr12;

        /**
         * 点击合成台
         */
        private int[] menuArr13;

        /**
         * 切换到材料合成
         */
        private int[] menuArr14;

        /**
         * 点击合成按钮
         */
        private int[] menuArr15;

        /**
         * 确认按钮
         */
        private int[] menuArr16;

        /**
         * 选择消耗品
         */
        private int[] menuArr17;

        /**
         * 使用
         */
        private int[] menuArr18;

        /**
         * 确认使用
         */
        private int[] menuArr19;

        /**
         * 选择遗器界面
         */
        private int[] menuArr20;

        /**
         * 选择遗器
         */
        private int[] menuArr21;

        /**
         * 点击详情
         */
        private int[] menuArr22;

        /**
         * 点击强化
         */
        private int[] menuArr23;

        /**
         * 添加
         */
        private int[] menuArr24;

        /**
         *
         */
        private int[] menuArr25;

    }

    /**
     * 退出星轨
     * StarRail.module.quit.QuitStarRail
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.quit-star-rail")
    public static class QuitStarRailCoordinates {
        /**
         * 退出按钮
         */
        private int[] quitArr1;

        /**
         * 确认退出
         */
        private int[] quitArr2;

        /**
         * 确认退出游戏
         */
        private int[] quitArr3;

        /**
         * 确定
         */
        private int[] quitArr4;

        /**
         * 关闭启动器
         */
        private int[] quitArr5;
    }

    /**
     * 挑战
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.challenge")
    public static class ChallengeCoordinates{
        /**
         * 再来一次按钮
         */
        private int[] challengeArr1;

        /**
         * 退出关卡按钮
         */
        private int[] challengeArr2;

        /**
         * 退出关卡
         */
        private int[] challengeArr3;

        /**
         * 挑战按钮
         */
        private int[] challengeArr4;

        /**
         * 支援按钮
         */
        private int[] challengeArr5;

        /**
         * 入队按钮
         */
        private int[] challengeArr6;

        /**
         * 开始挑战按钮
         */
        private int[] challengeArr7;

        /**
         * 挑战次数选择初始坐标(次数一坐标)
         */
        private int[] challengeArr8;

        /**
         * 挑战失败
         */
        private int[] challengeArr9;

    }

    /**
     * 生存索引
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.guide")
    public static class GuideCoordinates{
        /**
         * 模板匹配x轴左区间
         */
        private int leftX;

        /**
         * 模板匹配x轴右区间
         */
        private int rightX;

        /**
         * 传送按钮x轴左坐标固定值
         */
        private int x1;

        /**
         * 传送按钮x轴右坐标固定值
         */
        private int x2;

        /**
         * 传送按钮y轴上坐标偏移值
         */
        private int y1;

        /**
         * 传送按钮y轴下坐标偏移值
         */
        private int y2;

        /**
         * 卡片拖动区间-上
         */
        private int[] cardUp;

        /**
         * 卡片拖动区间-下
         */
        private int[] cardDown;

        /**
         * 卡片拖动区间-左
         */
        private int[] cardLeft;

        /**
         * 卡片拖动区间-右
         */
        private int[] cardRight;

        /**
         * 选择经验材料
         */
        private int[] empirical;

        /**
         * 选择行迹材料
         */
        private int[] trace;

        /**
         * 选择凝滞虚影
         */
        private int[] breakthrough;

        /**
         * 选择侵蚀隧洞
         */
        private int[] tunnel;

        /**
         * 选择历战余响
         */
        private int[] weekly;

        /**
         * 体力值识别区间-1
         */
        private int[] physicalPowerValue1;

        /**
         * 体力值识别区间-2
         */
        private int[] physicalPowerValue2;

        /**
         * 每日实训 界面检测
         */
        private int[] guideUi1;

        /**
         * 打开生存索引面板
         */
        private int[] guideUi2;

        /**
         * 生存索引-左-侧标题栏拖动-起始区间-上
         */
        private int[] LCardUp;

        /**
         * 生存索引-左-侧标题栏拖动-起始区间-下
         */
        private int[] LCardDown;

        /**
         * 生存索引-右-侧标题栏拖动-起始区间-上
         */
        private int[] RCardUp;

        /**
         * 生存索引-右-侧标题栏拖动-起始区间-下
         */
        private int[] RCardDown;

    }

    /**
     * 无名勋礼
     */
    @Getter
    @Setter
    @Component
    @ConfigurationProperties(prefix = "coordinates.guide")
    public static class RewardCoordinates{
        /**
         * 无名勋礼界面检测
         */
        private int[] rewardArr1;
        /**
         * 切换任务界面
         */
        private int[] rewardArr2;
        /**
         * 领取任务进度
         */
        private int[] rewardArr3;
        /**
         * 切换奖励界面
         */
        private int[] rewardArr4;
        /**
         * 领取所有奖励
         */
        private int[] rewardArr5;
    }

}
