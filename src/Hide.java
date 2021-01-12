import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Hide extends GraphicsProgram {

    GRect rect;

    public void run() {
        // 创建一个宽200、长100的长方形
        rect = new GRect(200, 100);
        // 设置成实心
        rect.setFilled(true);
        // 填充成红色
        rect.setColor(Color.RED);
        // 添加到画布(300,300)的位置
        add(rect, 300, 300);
        // 启用鼠标
        addMouseListeners();
    }

    // 通过addMouseListeners启用鼠标后，每当检测到鼠标移动时，都会自动调用这个函数
    public void mouseMoved(MouseEvent e) {
        // getElementAt(x, y)可以获取画布上(x,y)位置的图形
        GObject obj = getElementAt(e.getX(), e.getY());

        if(obj == null) {
            // 如果获取到的是null，也就是没有图形，那么就让矩形显示出来
            rect.setVisible(true);
        } else if (obj == rect) {
            // 如果获取了矩形，说明鼠标当前位置在矩形上，那么把它隐藏起来
            rect.setVisible(false);
        }
    }

}
