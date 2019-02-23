package com.github.flaz14;

import picocli.CommandLine;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

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
        description = "Rgs - The stupid image processing tool.",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class ResizeGrayscaleSave implements Runnable {
    @Option(
            names = {"-v", "--verbose"},
            description = "Verbose mode. This option is helpful if you would like an interactive session or " +
                    "troubleshoot the application. If you plan to use the application in non-interactive mode (for " +
                    "example, inside a shell-script) you probably don't need to use this option. " +
                    "Multiple `-v' options increase the verbosity. Default mode is \"off\".")
    private boolean[] verbose = new boolean[0];



    public void run() {
    }

    public static void main(String args[]) {
        CommandLine.run(new ResizeGrayscaleSave(), args);
    }


}


