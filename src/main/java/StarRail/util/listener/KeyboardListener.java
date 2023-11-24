package StarRail.util.listener;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 键盘监听器
 *
 * @author : 真寻
 * 2023/7/25
 */
public class KeyboardListener extends JFrame implements NativeKeyListener {
    /**
     * 存储记录的按键事件
     */
    private final List<NativeKeyEvent> keyEvents = new ArrayList<>();
    /**
     * 存储按键时间戳
     */
    private final List<Long> timestamps = new ArrayList<>();

    /**
     * 存储按键按下间隔时间戳
     */
    private final List<Long> intervalTimestamps = new ArrayList<>();
    /**
     * 按键是否被按下
     */
    private boolean isRecording = false;
    /**
     * 按键间隔时间戳
     */
    private long initialTimestamp = -1;

    KeyboardListener() {
        // 禁用JNativeHook日志记录器
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // 注册JNativeHook
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // 添加原生键监听器
        GlobalScreen.addNativeKeyListener(this);

        // 创建用户界面
        JPanel panel = new JPanel();
        JButton recordButton = new JButton("开始记录");
        JButton replayButton = new JButton("复现");
        JButton saveButton = new JButton("保存到文件");
        JButton loadButton = new JButton("从文件加载");
        panel.add(recordButton);
        panel.add(replayButton);
        panel.add(saveButton);
        panel.add(loadButton);
        add(panel);

        // 添加按钮监听器
        recordButton.addActionListener(e -> {
            if (isRecording) {
                // Stop recording
                isRecording = false;
                recordButton.setText("开始记录");
            } else {
                // Start recording
                isRecording = true;
                // 清空列表
                keyEvents.clear();
                timestamps.clear();
                intervalTimestamps.clear();
                initialTimestamp = -1;
                recordButton.setText("停止记录");
            }
        });
        replayButton.addActionListener(e -> {
            try {
                replay();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        saveButton.addActionListener(e -> {
            try {
                saveToFile("keyevents.txt");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        loadButton.addActionListener(e -> {
            try {
                loadFromFile("keyevents.txt");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Set the frame properties
        setTitle("键盘监听");
        setSize(400, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * 监听按键按下
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (isRecording) {
            // 记录键值和时间戳
            keyEvents.add(e);
            long currentTime = System.currentTimeMillis();
            // 初始化按键间隔开始时间戳
            if (initialTimestamp == -1) {
                initialTimestamp = currentTime;
            }
            // 存储按键按下的时间戳
            timestamps.add(currentTime - initialTimestamp);
            // 存储按键间隔时间戳
            intervalTimestamps.add(currentTime - initialTimestamp);
            // 更新按键间隔开始时间
            initialTimestamp = currentTime;
        }
    }

    /**
     * 监听按键释放
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (isRecording) {
            // 记录键值和时间戳
            keyEvents.add(e);
            long currentTime = System.currentTimeMillis();
            // 存储按键释放时间
            timestamps.add(currentTime - initialTimestamp);
            // 更新按键间隔开始时间
            initialTimestamp = currentTime;
        }
    }

    /**
     * 复现键盘操作
     */
    public void replay() throws Exception {
        Thread.sleep(2000);
        Robot robot = new Robot();

        // 复现按键操作
        int k = 0;
        for (int i = 0; i < keyEvents.size(); i++) {
            // 获取按键事件
            NativeKeyEvent e = keyEvents.get(i);

            // 将本机键代码转换为Java键代码
            int keyCode = e.getRawCode();

            // 模拟按键按下
            if (e.getID() == NativeKeyEvent.NATIVE_KEY_PRESSED) {
                robot.keyPress(keyCode);
                // 获取按键持续时间
                long timestamp = timestamps.get(i);
//                Thread.sleep(timestamp);
                robot.delay((int) timestamp);
            } else if (e.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
                robot.keyRelease(keyCode);
                // 等待记录的时间间隔
                long intervalTimestamp = intervalTimestamps.get(k);
//                Thread.sleep(intervalTimestamp);
                k++;
                robot.delay((int) intervalTimestamp);
            }
        }
        System.out.println("复现完毕");
    }

    /**
     * 保存键盘操作
     */
    public void saveToFile(String filename) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            long intervalTimestamp;
            int k = 0;
            for (int i = 0; i < keyEvents.size(); i++) {
                NativeKeyEvent e = keyEvents.get(i);
                long timestamp = timestamps.get(i);

                if (e.getID() == NativeKeyEvent.NATIVE_KEY_RELEASED) {
                    intervalTimestamp = intervalTimestamps.get(k);
                    k++;
                    writer.write(e.getID() + " " + e.getRawCode() + " " + timestamp + " " + intervalTimestamp);
                } else {
                    // Write the key event and timestamp to the file
                    writer.write(e.getID() + " " + e.getRawCode() + " " + timestamp);
                }

                writer.newLine();
            }
        }
        System.out.println("已保存");
    }

    /**
     * 加载键盘操作
     */
    public void loadFromFile(String filename) throws Exception {
        Thread.sleep(1000);
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            keyEvents.clear();
            timestamps.clear();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                int keyCode = Integer.parseInt(parts[1]);
                long timestamp = Long.parseLong(parts[2]);

                if (id == NativeKeyEvent.NATIVE_KEY_RELEASED) {
                    long intervalTimestamp = Long.parseLong(parts[3]);
                    intervalTimestamps.add(intervalTimestamp);
                }

                // Create a new key event and add it to the list
                NativeKeyEvent e = new NativeKeyEvent(id, 0, keyCode, 0, (char) keyCode, NativeKeyEvent.KEY_LOCATION_STANDARD);
                keyEvents.add(e);
                timestamps.add(timestamp);
            }
            System.out.println("已加载");
        }
    }

    /**
     * 测试方法
     */
    public static void main(String[] args) {
        KeyboardListener keyboardListener = new KeyboardListener();
        keyboardListener.setVisible(true);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // This method is called when a key is typed (pressed and released)
        // You can add your own code here to handle key typed events
    }

}
