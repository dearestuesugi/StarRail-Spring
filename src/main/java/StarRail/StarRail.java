package StarRail;

import StarRail.ui.SimpleInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author : 真寻
 * 2023/11/21
 */
@SpringBootApplication(scanBasePackages = "StarRail")
public class StarRail implements CommandLineRunner {
    @Autowired
    private SimpleInterface simpleInterface;

    public static void main(String[] args) {
        // 关闭无头模式,允许使用图形化界面,若关闭则报java.awt类错
        SpringApplicationBuilder builder = new SpringApplicationBuilder(StarRail.class);
        builder.headless(false).run(args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        // 启动
        simpleInterface.simpleInterface();
    }
}
