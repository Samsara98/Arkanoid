import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import java.awt.*;

public class Lottery extends GraphicsProgram {
    // 成员变量randomGenerator，用来生成随机数
    RandomGenerator randomGenerator = RandomGenerator.getInstance();

    public void run() {
        // 产生一个10000-20000之间的数字
        int number = randomGenerator.nextInt(10000, 20000);

        GLabel label = new GLabel("本次获奖编号为：" + number);

        // 添加到画布上(100,100)的位置
        add(label, 100, 100);
    }
}
