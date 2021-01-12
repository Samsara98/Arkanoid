import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;

public class DrawBricks extends GraphicsProgram {

    // 窗口宽400、高600
    public static final int APPLICATION_WIDTH = 400;
    public static final int APPLICATION_HEIGHT = 600;

    // 砖颜色是黑色
    public static final Color BRICK_COLOR = Color.BLACK;

    // 砖的实心部分宽18,高8
    public static final int BRICK_WIDTH = 18;
    public static final int BRICK_HEIGHT = 8;

    // 每块实心砖的上下左右各有宽度为1的空白。
    // 注意：当两块砖挨着的时候，中间的空白有两遍，加起来就是2了。
    public static final int BRICK_MARGIN = 1;

    public void run() {
        // 一块蓝色的砖
        GRect brick1 = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
        brick1.setFilled(true);
        brick1.setColor(Color.BLUE);
        add(brick1, 10, 10);

        // 一块红色的空心砖
        GRect brick2 = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
        brick2.setColor(Color.RED);
        add(brick2, 200, 70);

        // 一堆黑色的砖块
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
                brick.setFilled(true);
                brick.setColor(BRICK_COLOR);
                int x = j * (BRICK_MARGIN * 2 + BRICK_WIDTH) + BRICK_MARGIN;
                int y = 300 + i * (BRICK_MARGIN * 2 + BRICK_HEIGHT) + BRICK_MARGIN;
                add(brick, x, y);
            }
        }
    }
}
