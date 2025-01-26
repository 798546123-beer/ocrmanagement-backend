package main.java;

import org.opencv.highgui.HighGui;
import org.testng.annotations.Test;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class test1 {
    public static void main(String[] args) {
        System.out.println(1);
    }
    @Test
    public void printOS(){
        System.out.println(System.getProperty("os.arch"));
    }
    @Test
    public void test2() {
        String dllPath = "C:\\Users\\MECHREVO\\Desktop\\OCR验收管理平台\\OCR-backend\\backend\\test\\lib\\opencv_java455.dll";
        try {
            System.load(dllPath);
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Failed to load OpenCV library: " + e.getMessage());
            return;
        }
        String imagePath = "D:\\46_74.png";
        Mat image = imread(imagePath);
        // 创建一个窗口用于显示图片
        HighGui.namedWindow(" Image", HighGui.WINDOW_NORMAL);
        HighGui.resizeWindow(" Image", 180, 230);
        // 在窗口中显示图片
        HighGui.imshow(" Image", image);

        // 等待按键事件，按任意键关闭窗口
        HighGui.waitKey(0);

        // 销毁所有窗口
        HighGui.destroyAllWindows();
    }

}
