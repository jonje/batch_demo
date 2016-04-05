package gov.utah.dts;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by jjensen on 3/30/16.
 */
public class MyJob implements InteruptableRunnable, Job {
    private static final Logger LOG = LoggerFactory.getLogger(MyJob.class);
    private String jarPath;
    private String startingMethodName;
    private String stopMethodName;
    private String className;

    private Method method;
    private Object obj;

    /**
     *
     * @param jarPath Example("file:///home/jjensen/Projects/TestJar/out/artifacts/TestJar_jar/TestJar.jar")
     * @param className Example("com.example.Bean")
     * @param startingMethodName Example("run")
     */
    public MyJob(String jarPath, String className, String startingMethodName, String stopMethodName) {
        this.jarPath = jarPath;
        this.startingMethodName = startingMethodName;
        this.stopMethodName = stopMethodName;
        this.className = className;
    }


    @Override
    public void run() {
        try {


            URL[] classLoderUrls = new URL[] {
                    new URL(jarPath)
            };

            URLClassLoader urlClassLoader = new URLClassLoader(classLoderUrls);

            // Load the target class
            Class<?> beanClass = urlClassLoader.loadClass(className);

            // Create a new instance from the loaded class
            Constructor<?> constructor = beanClass.getConstructor();
            obj = constructor.newInstance();

            // Getting a method from the loaded class and invoke it
            Method runMethod = beanClass.getMethod(startingMethodName);
            method = beanClass.getMethod(stopMethodName);
            runMethod.invoke(obj);


        } catch (MalformedURLException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopJob() throws InterruptedException {

        try {
            method.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOG.info("Job " + jobExecutionContext.getJobDetail().getKey().toString() + " executed at " + jobExecutionContext.getFireTime());
    }
}
