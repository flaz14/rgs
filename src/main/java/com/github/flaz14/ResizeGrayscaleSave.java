package com.github.flaz14;

import picocli.CommandLine;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

/**
 * Написать консольное приложение на Java, которое принимает на вход три параметра:
 * <p>
 * URL изображения (формата http://host/path или file:/path)
 * <p>
 * width
 * <p>
 * heights
 * <p>
 * Результатом выполнения должно быть черно белое изображение (grayscale) приведенное к размерам width x heights
 * <p>
 * Изображение можно сохранить в рабочую директорию, выводить путь к изображению.
 * <p>
 * Приветствуется:
 * <p>
 * тесты
 * <p>
 * описание какие форматы изображений поддерживаются.
 */
@Command(
        name = "rgs",
        description = "Rgs - the stupid image processing tool." +
                "\n" +
                "The tool resizes given image according to desired dimensions and converts it to grayscale. Obviously, the tool" +
                "doesn't keep aspect ratio. So if you would like to SCALE an image please calculate appropriate dimensions" +
                "in advance and pass them to the tool explicitly. ",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class ResizeGrayscaleSave implements Runnable {
    @Option(
            names = {"-v", "--verbose"},
            description = "Verbose mode. This option is helpful if you would like an interactive session or " +
                    "troubleshoot the application. If you plan to use the application in non-interactive mode (for " +
                    "example, inside a shell-script) you probably don't need to use this option. " +
                    "Multiple `-v' options increase the verbosity. " +
                    "\n" +
                    "This switch is optional. Default mode is \"off\".")
    private boolean[] verbose = new boolean[0];

    @Parameters(
            arity = "1",
            paramLabel = "URL",
            description = "URL to image to be processed. The should include protocol name. " +
                    "Currently two protocols are supported: local files (e.g. `file://path/to/image') and HTTP (e.g. " +
                    "`http://mysite.com/image')." +
                    "\n" +
                    "This parameter is mandatory.")
    private String url;

    @Parameters(
            arity="1",
            paramLabel = "WIDTH",
            description = "Width of the output image"
    )
    private int width;

    private int height;

    public void run() {
    }

    public static void main(String args[]) {
        CommandLine.run(new ResizeGrayscaleSave(), args);
    }


}


