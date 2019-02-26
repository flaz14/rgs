package com.github.flaz14;

import com.github.flaz14.io.Loader;
import com.github.flaz14.io.Writer;
import com.github.flaz14.limit.Limits;
import com.github.flaz14.limit.wrapper.PermissibleDimensions;
import com.github.flaz14.limit.wrapper.PermissibleUrl;
import com.github.flaz14.processor.Grayscaler;
import com.github.flaz14.processor.Resizer;
import com.github.flaz14.util.Image;
import picocli.CommandLine;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Parameters;

/**
 * <a href="https://github.com/remkop/picocli/issues/292">Validation of parameters</a>
 */
@Command(
        name = "rgs",
        description = "Rgs - the stupid image processing tool." +
                "\n\n" +
                "The tool resizes given image according to desired dimensions and converts it to grayscale. The tool" +
                "doesn't keep aspect ratio automatically. So if you would like to SCALE an image please calculate " +
                "appropriate dimensions in advance and pass them to the tool explicitly." +
                "\n\n" +
                "Resultant image will be saved into current working directory under the name as of original image " +
                "(including file extension). Absolute path to output image will printed on the screen. If a same-titled " +
                "file already exists in the directory it will be overwritten without any notice. Output file will have " +
                "the same format as input one." +
                "\n\n" +
                "Maximum size of input image is " + Limits.IMAGE_WIDTH_IN_PIXELS + "x" + Limits.IMAGE_HEIGHT_IN_PIXELS +
                " pixels. Probably, larger images will be supported in future versions of the application." +
                "\n\n" +
                "Currently only JPEG format is supported. Probably, more formats will be handled in future versions of " +
                "the tool." +
                "\n\n" +
                "For example, you would like to grab JPEG from the Web, downscale it and save to your computer, " +
                "just invoke the tool like this: " +
                "\n\n" +
                "java -jar rgs.jar flaz14.github.io/blob/master/draft/mtbank.jpg 100 100" +
                "java -jar rgs.jar flaz14.github.io/blob/master/draft/mtbank.jpg 100 100" +
                "https://github.com/flaz14/flaz14.github.io/raw/master/draft/mtbank.jpg"
                "\n\n" +
                "",
        mixinStandardHelpOptions = true,
        version = "1.0")
public class Application implements Runnable {
    @Parameters(
            paramLabel = "URL",
            index = "0",
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
            description = "Similar to WIDTH parameter. Please note, that HEIGHT is not tied to WIDTH. You can specify them " +
                    "independently. This makes harder to keep aspect ratio. Probably, this will be fixed in future release of the tool. " +
                    "" +
                    "\n" +
                    "This parameter is mandatory.")
    private int height;

    public void run() {
        PermissibleUrl permissibleUrl = new PermissibleUrl(url);
        PermissibleDimensions permissibleDimensions = new PermissibleDimensions(width, height);
        Image originalImage = new Loader(permissibleUrl).load();
        Image resizedImage = new Resizer(originalImage, permissibleDimensions).process();
        Image grayscaledImage = new Grayscaler(resizedImage).process();
        new Writer(grayscaledImage).write();
    }

    public static void main(String args[]) {
        CommandLine.run(new Application(), args);
    }
}


