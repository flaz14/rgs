# rgs

**rgs** stands for "Resize, Convert, Scale". This program is not production-ready. 
On the contrary, it's a solution of yet another coding interview task. The original
description of the task is the following: 

> Написать консольное приложение на Java, которое принимает на вход три параметра:
> 
> URL изображения (формата http://host/path или file:/path)
> 
> width
> 
> heights
> 
> Результатом выполнения должно быть черно белое изображение (grayscale) приведенное к размерам width x heights
> 
> Изображение можно сохранить в рабочую директорию, выводить путь к изображению.
> 
> Приветствуется:
> 
> тесты
> 
> описание какие форматы изображений поддерживаются.
> 

## Theory

I strove for minimalism and simplicity: avoid usage of third-party libraries as 
much as possible and stay away of problems.

One of such a problem is quick, memory-efficient and pretty upscaling and downscaling 
of an image. Quite good explanation can be found in Oracle blog: 
[The Perils of Image.getScaledInstance()](https://community.oracle.com/docs/DOC-983611). 
Obviously, implementation of resizing at its best requires a huge amount of time and 
effort. The same is for pure cross-platform execution, multi-language URLs and file 
names, etc. However, I covered the code by Unit tests mostly. And I tried to make 
Clean Code :)

## Practice

In order to compile and run the application you need Java 10 and Maven installed.

For compiling execute:

```
mvn clean package
```

Then go to `target` directory and run executable jar. For example, this will print 
embedded documentation of the program:

```
java -jar rgs.jar -h
```

You can find detailed usage information there.

If you would like to quickly re-compile the code and see how it works, just appeal
to Maven Exec plugin:   

```
mvn clean package exec:java -Dexec.args="-h"
```

Have a good day :)