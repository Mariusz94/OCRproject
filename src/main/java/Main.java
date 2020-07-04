import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Main {
    JFrame frame;
    JButton jButton;
    JButton jButton2;
    JButton jButton3;

    File imageFile;
    File lastReadFile;
    ITesseract instance;
    String outputOCR;

    public Main() {
        instance = new Tesseract();
        instance.setDatapath("E:\\Glowny folder MojaJava\\Moje programy\\1.Projekty\\OCRproject\\src\\main\\resources\\tessdata");

        frame = new JFrame();
        frame.setSize(300, 100);
        frame.setLocation(0, 0);

        initComponents();

        Runnable searchFile = () -> searchLastScreen();
        Thread threadSearch = new Thread(searchFile);
        threadSearch.start();

        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void initComponents() {
        Container container = frame.getContentPane();
        jButton = new JButton();
        jButton.setText("Mobby2");
        jButton.addActionListener((s) -> readOCRPicture(new File("E:\\Glowny folder MojaJava\\Moje programy\\1.Projekty\\OCRproject\\src\\main\\resources\\moby2.jpg")));
        jButton2 = new JButton();
        jButton2.setText("Mobby");
        jButton2.addActionListener((s) -> readOCRPicture(new File("E:\\Glowny folder MojaJava\\Moje programy\\1.Projekty\\OCRproject\\src\\main\\resources\\moby.png")));
        jButton3 = new JButton();
        jButton3.setText("screenshot");
        jButton3.addActionListener((s) -> {
            try {
                takeScreenshot();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        });

        //container.add(jButton, BorderLayout.SOUTH);
        //container.add(jButton2, BorderLayout.NORTH);
        container.add(jButton3, BorderLayout.CENTER);
    }

    private void searchLastScreen() {

        while (true) {
            File file = new File("C:\\Users\\Unit\\AppData\\Roaming\\.minecraft_pokemine_dark_294\\screenshots");
            File[] files = file.listFiles();

            if (files != null && !files[files.length - 1].getName().equals(imageFile != null ? imageFile.getName() : "")) {
                imageFile = new File(String.valueOf(files[files.length - 1].getAbsoluteFile()));
                System.out.println("Pobrałem ostatni screen");
                try {
                    readPartOfPicture(imageFile,0,0,753,150);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readPartOfPicture(File file, int x, int y, int width, int height) throws IOException {
            if (file != null && !file.getName().equals(lastReadFile != null ? lastReadFile.getName() : "")) {
                File cutFile = cutImage(ImageIO.read(file), x, y, width, height);
                lastReadFile = new File(String.valueOf(file.getAbsoluteFile()));
                readOCRPicture(cutFile);
            }
    }


    public static void main(String[] args) {
        new Main();
    }

    public String readOCRPicture(File imageFile) {
        try {
            outputOCR = instance.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        printTextFromOCR(outputOCR);
        return outputOCR;
    }

    public void printTextFromOCR(String text) {
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println(text);
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////////");
    }

    public void takeScreenshot() throws IOException, AWTException {
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        File file = new File("E:\\Glowny folder MojaJava\\Moje programy\\1.Projekty\\OCRproject\\src\\main\\resources\\screenshot" + System.currentTimeMillis() + ".png");
        ImageIO.write(image, "png", file);
        System.out.println("Zdjęcie zrobione");
        cutImage(image, 0,0,100, 100);
    }

    public File cutImage(BufferedImage src, int x, int y, int width, int height) throws IOException {
        BufferedImage dest = src.getSubimage(x, y, width, height);
        File file = new File("E:\\Glowny folder MojaJava\\Moje programy\\1.Projekty\\OCRproject\\src\\main\\resources\\screenshot" + System.currentTimeMillis() + "_cut.png");
        ImageIO.write(dest, "png", file);
        System.out.println("Wycinek został wykonany");
        return file;
    }

}

// C:\Users\Unit\AppData\Roaming\.minecraft_pokemine_dark_294\screenshots
// 2020-07-04_18.26.30.png