package StarRail.ui;

import StarRail.module.guide.PhysicalPower;
import StarRail.module.guide.daily.DailyPractice;
import StarRail.module.initiate.StartStarRail;
import StarRail.module.menu.Menu;
import StarRail.module.quit.QuitStarRail;
import StarRail.module.reward.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

import static StarRail.util.basic.random.RandomGenerator.randomWait;

/**
 * 简单的UI
 *
 * @author : 真寻
 * 2023/11/21
 */
@Component
public class SimpleInterface {
    @Autowired
    private StartStarRail starRail;
    @Autowired
    private Menu menu;
    @Autowired
    private DailyPractice dailyPractice;
    @Autowired
    private PhysicalPower physicalPower;
    @Autowired
    private QuitStarRail quitStar;
    @Autowired
    private Reward reward;

    public void simpleInterface() {
        // 创建窗口
        JFrame frame = new JFrame(" 寻 ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // 创建面板
        JPanel panel = new JPanel();
        frame.add(panel);

        /*
         * 启动星轨
         */
        JButton button1 = new JButton("启动星轨");
        button1.addActionListener(e -> starRail.startStarRail());
        panel.add(button1);

        /*
         * 清理委托、领取邮件
         */
        JButton button2 = new JButton("清理委托、领取邮件");
        button2.addActionListener(e -> new Thread(() -> {
            randomWait(2000);
            menu.menu();
        }).start());
        panel.add(button2);

        /*
         * 每日实训
         */
        JButton button3 = new JButton("每日实训");
        button3.addActionListener(e -> new Thread(() -> {
            randomWait(2000);
            dailyPractice.cleanDailyPractice();
        }).start());
        panel.add(button3);

        /*
         * 清体力
         */
        JButton button4 = new JButton("清体力");
        button4.addActionListener(e -> new Thread(() -> {
            randomWait(2000);
            physicalPower.cleanPhysicalPower();
        }).start());
        panel.add(button4);

        /*
         * 领取无名勋礼奖励
         */
        JButton button5 = new JButton("领取无名勋礼奖励");
        button5.addActionListener(e -> new Thread(() -> {
            randomWait(2000);
            reward.unknownReward();
        }).start());
        panel.add(button5);

        /*
         * 退出星铁
         */
        JButton button6 = new JButton("退出星铁");
        button6.addActionListener(e -> new Thread(() -> {
            randomWait(2000);
            quitStar.quitStarRail();
        }).start());
        panel.add(button6);

        JButton exitButton = new JButton("退出 寻");
        exitButton.addActionListener(e -> {
//            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
//            if (result == JOptionPane.OK_OPTION) {
            System.exit(0);
//            }
        });
        panel.add(exitButton);

        /*
         * 一键完成
         */
        JButton goButton = new JButton("go");
        goButton.addActionListener(e -> new Thread(() -> {
            starRail.startStarRail();
            randomWait();
            menu.menu();
            randomWait();
            dailyPractice.cleanDailyPractice();
            randomWait();
            reward.unknownReward();
            randomWait();
            quitStar.quitStarRail();
        }).start());
        panel.add(goButton);

        // 显示窗口
        frame.setVisible(true);
    }
}
