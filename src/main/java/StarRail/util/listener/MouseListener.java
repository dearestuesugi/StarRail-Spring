package StarRail.util.listener;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * 鼠标监听器
 *
 * @author : 真寻
 * 2023/7/26
 */
public class MouseListener extends JFrame {
    private static final long serialVersionUID = 1L;

    /**
     * 用于存储鼠标操作的列表
     */
    private ArrayList<MouseEvent> mouseEvents = new ArrayList<>();
    // 用于模拟鼠标操作的Robot对象
    private Robot robot = new Robot();
    //
    /**
     * 是否正在录制鼠标操作
     */
    private boolean isRecording = false;

    public MouseListener() throws AWTException {
        super("鼠标监听");

        // 创建一个面板，用于放置按钮
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.SOUTH);

        // 创建一个“开始录制”按钮，用于开始录制鼠标操作
        JButton recordButton = new JButton("开始录制");
        recordButton.addActionListener(e -> {
            if (isRecording) {
                isRecording = false;

                recordButton.setText("开始录制");

            } else {
                isRecording = true;

                // 清空列表
                mouseEvents.clear();
                recordButton.setText("停止录制");

            }

            new Thread(new MouseRecorderThread()).start();
        });
        panel.add(recordButton);

        // 创建一个“复现”按钮，用于复现鼠标操作
        JButton playbackButton = new JButton("复现");
        playbackButton.addActionListener(e -> {
            isRecording = false;
            for (MouseEvent event : mouseEvents) {
                robot.mouseMove(event.x, event.y);
                robot.delay(event.delay);
            }
        });
        panel.add(playbackButton);

        // 创建一个“保存”按钮，用于将鼠标操作保存到文件中
        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> {
            isRecording = false;
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("mouseEvents.txt"))) {
                for (MouseEvent event : mouseEvents) {
                    writer.write(event.x + "," + event.y + "," + event.delay);
                    writer.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(saveButton);

        // 创建一个“加载”按钮，用于从文件中加载保存的鼠标操作
        JButton loadButton = new JButton("加载");
        loadButton.addActionListener(e -> {
            isRecording = false;
            try (BufferedReader reader = new BufferedReader(new FileReader("mouseEvents.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    int delay = Integer.parseInt(parts[2]);
                    mouseEvents.add(new MouseEvent(x, y, delay));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(loadButton);

        // 设置窗口的大小并显示窗口
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MouseRecorderThread类，用于在后台记录鼠标操作
    private class MouseRecorderThread implements Runnable {
        @Override
        public void run() {
            while (isRecording) {
                int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
                int y = (int) MouseInfo.getPointerInfo().getLocation().getY();
                mouseEvents.add(new MouseEvent(x, y, 100));
                robot.delay(100);
            }
        }
    }

    // MouseEvent类，用于存储鼠标操作
    private static class MouseEvent {
        public int x, y, delay;

        public MouseEvent(int x, int y, int delay) {
            this.x = x;
            this.y = y;
            this.delay = delay;
        }
    }

    public static void main(String[] args) throws AWTException {
        new MouseListener();
    }
}
