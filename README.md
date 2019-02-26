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


yura@Desktop ~/Desktop/interview tasks/rgs $ mvn clean package  exec:java -Dexec.args="https://www.google.by/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"
