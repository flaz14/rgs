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
 *
 *
 * <a href="https://github.com/remkop/picocli/issues/292">Validation of parameters</a>
 */
@Command(
        name = "rgs",
        description = "Rgs - the stupid image processing tool." +
                "\n" +
                "The tool resizes given image according to desired dimensions and converts it to grayscale. The tool" +
                "doesn't keep aspect ratio. So if you would like to SCALE an image please calculate appropriate dimensions" +
                "in advance and pass them to the tool explicitly. Maximum image size is 4096x4096 pixels. Тут еще будет написано, " +
                "что обработка гигантских изображений - отдельная задача, требующая времени, что фича будет реализована в будущих" +
                "версиях программы, а сейчас лучше пусть приложение откажется работать сразу, нежели внезапно упадет посередине" +
                "из-за нехватки памяти." +
                "\n" +
                "Resultant image will be saved into current directory with hard-coded name `a.out' (like GSS compiler does for " +
                "output executable). " +
                "Absolute path to output file will be printed (please see `--verbose' switch for more details)." +
                " It's quite ugly behaviour" +
                "Если файл с именем a.out уже существует в текущей директории, он перезаписывается безо всяких предупреждений." +
                "Because typical user would like to keep the same extension as for input image (however, in Unix-like operating" +
                "systems file extension doesn't matter). Perhaps, this will be improved in future release of the application " +
                "(with a bunch of optional switches for fine-grained tuning of output file name)." +
                "\n" +
                "For example, you would like to grab Google logo from the Web, downscale it in ten times and save to our computer." +
                "Just invoke the tool like this: " +
                "\n" +
                "rgs https://www.google.by/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png 27 9\n" +
                "\n" +
                "\n" +
                "Currently only JPEG and PNG formats are supported." +
                "",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class Application implements Runnable {
    @Option(
            names = {"-v", "--verbose"},
            description = "Verbose mode. This option is helpful if you would like an interactive session or " +
                    "troubleshoot the application. If you plan to use the application in non-interactive mode (for " +
                    "example, inside a shell-script) you probably don't need to use this option. " +
                    "Multiple `-v' options increase the verbosity. " +
                    "\n" +
                    "By default only path to output image is printed." +
                    "" +
                    "\n" +
                    "This switch is optional.")
    private boolean[] verbose = new boolean[0];

    @Parameters(
            paramLabel = "URL",
            index = "0",
//            arity = "1",
            description = "URL to image to be processed. The should include protocol name. " +
                    "Currently two protocols are supported: local files (e.g. `file://path/to/image') and HTTP (e.g. " +
                    "`http://mysite.com/image')." +
                    "\n" +
                    "The application doesn't limit length of an URL (obviously, the only limitation is the " +
                    "amount of available memory). But your shell may have its own restriction on command line length." +
                    "The same situation is for HTTP protocol, certain file system, etc."
                    +
                    "\n" +
                    "This parameter is mandatory.")
    private String url;

    @Parameters(
            paramLabel = "WIDTH",
            index = "1",
//            arity = "1",
            description = "Width of the output image in pixels. Should be greater than zero. Maximum value of this parameter is limited to " +
                    "4096. Well, it's quite nasty limitation. But processing of large images requires enormous amount of memory which " +
                    "depends of user's computer configuration. Such a configuration is not predictable. So in case of vast dimensions " +
                    "it will be better to " +
                    "stop resizing in the very beginning rather that let the application fail suddenly right in the middle." +
                    "\n" +
                    "" +
                    "" +
                    "" +
                    "This parameter is mandatory."
    )
    private int width;

    @Parameters(
            paramLabel = "HEIGHT",
            index = "2",
//            arity = "1",
            description = "Similar to WIDTH parameter. Please note, that HEIGHT is not tied to WIDTH. You can specify them " +
                    "independently. This makes harder to keep aspect ratio. Probably, this will be fixed in future release of the tool. " +
                    "" +
                    "\n" +
                    "This parameter is mandatory."
    )
    private int height;

    public void run() {
        Image originalImage = new Loader(url).load();
        Image resizedImage = new Resizer(originalImage).resize(width, height);
        new Writer(resizedImage).write();
    }

    public static void main(String args[]) {
        CommandLine.run(new Application(), args);
    }
}


